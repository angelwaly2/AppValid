package co.myappvalid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    /******OBJECTS******/
    CompositeDisposable desechable = new CompositeDisposable();
    String url = null;
    VisorPaginas vp;
    VpAdapter miAdapter;
    int posicion_anterior_global = -1;
    int destino;
    String pais_actual = "";
    private static final int paginacion = 50;
    ArrayList<Integer> posiciones_actualizadas = new ArrayList<>();
    TextView txtCargando,txtinfo;
    int totalPages = 1;
    ArrayList<ImageView> listaContenedoresImagenes = new ArrayList<>();
    /******CLOSE******/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent i = getIntent();
        Bundle bundle = i.getExtras();
        url = bundle.getString("url");
        destino = bundle.getInt("destino"); // 0 para artistas; 1 para canciones
        pais_actual = bundle.getString("pais");
        txtCargando = (TextView) findViewById(R.id.txtCargando);
        txtinfo = (TextView) findViewById(R.id.txtinfo);
        Button btnAtras = (Button) findViewById(R.id.btnAtras);
        Button btnAdelante = (Button) findViewById(R.id.btnAdelante);
        vp = (VisorPaginas) findViewById(R.id.vistaPrincipal);
        vp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                cambio(position);
            }
            @Override
            public void onPageScrolled(int arg0, float arg1, int position) {
            }
            @Override
            public void onPageScrollStateChanged(int arg0) {
            }
        });
        if(isOnline()) {
            Negocio.mostarProgreso(true,this,vp,txtCargando);
            // Actualiza las dos primeras paginas de las vp
            actualizar(1);
            actualizar(2);
        }
        if(destino==0) {
            // Seleccionar la cantidad de registros en el sqlite
            String query = "SELECT COUNT(*) FROM tbl_artistas";
            totalPages = Integer.parseInt(Negocio.listarString(query,this)) / paginacion;
        }else{
            String query = "SELECT COUNT(*) FROM tbl_canciones";
            totalPages = Integer.parseInt(Negocio.listarString(query,this)) / paginacion;
        }
        if(totalPages>0) {
            Negocio.mostarProgreso(false,this,vp,txtCargando);
            miAdapter = new VpAdapter(totalPages, getApplicationContext(), pais_actual, destino,listaContenedoresImagenes);
            vp.setAdapter(miAdapter);
        }else{
            Toast.makeText(this,"Para El Primer ingreso debe contar con acceso a internet", Toast.LENGTH_SHORT).show();
        }
        btnAtras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                retroceder();
            }
        });
        btnAdelante.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adelantar();
            }
        });
        establecer_cabecera();
    }

    void retroceder(){
        if(vp.getCurrentItem()>0){
            vp.setCurrentItem(vp.getCurrentItem()-1);
            establecer_cabecera();
        }
    }
    void adelantar(){
        if(totalPages>vp.getCurrentItem()){
            vp.setCurrentItem(vp.getCurrentItem()+1);
            establecer_cabecera();
        }
    }

    void establecer_cabecera(){
        txtinfo.setText("Pagina " + String.valueOf(vp.getCurrentItem() + 1) + " de " + String.valueOf(totalPages));
    }

    public boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(this.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        } else {
            return false;
        }
    }

    void cambio(int posicion){
        int pagina = posicion + 2;
        if(isOnline()) {
            actualizar(pagina);
            actualizar(pagina+1);
        }
    }

    private void actualizar(final int pagina){
        Observable <List<Object>> ejecutador =Observable.fromCallable(new Callable<List<Object>>() {
            @Override
            public List<Object> call() throws Exception {
                int posicion = pagina -1;
                List<Object> lista = new ArrayList<>();
                for(int i=0;i<posiciones_actualizadas.size();i++) if (posicion== posiciones_actualizadas.get(i)) return lista;
                String urlJson = url + "&page="+String.valueOf(pagina);
                if(destino==0) {
                    if(pagina<=totalPages) {
                        lista = new RestClient().getArtists(urlJson);
                        ingresoSQLITE(lista, posicion);
                    }
                    return lista;
                }
                else {
                    if(pagina<=totalPages) {
                        //lista = new RestClient().getTracks(urlJson); // Pendiente de revision JSON
                    }
                    return lista;
                }
            }
        });
        Disposable subscriptor = ejecutador.
                subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(new Consumer<List<Object>>() {
                    @Override
                    public void accept(@NonNull List<Object> lista) throws Exception {
                        retorno(lista);
                    }
                });
        desechable.add(subscriptor);
    }

    public void retorno(List<Object> lista) {
        if(lista!=null){
            if(lista.size()>0) {
                Ubicacion ubicacion = (Ubicacion) lista.get(1);
                int pagina = ubicacion.getPage();
                totalPages = ubicacion.getTotalPages();
                if (pagina == 1) {
                    listaContenedoresImagenes = new ArrayList<>();
                    miAdapter = new VpAdapter(totalPages,getApplicationContext(),pais_actual,destino,listaContenedoresImagenes);
                    vp.setAdapter(miAdapter);
                    vp.setCurrentItem(0);
                    establecer_cabecera();
                }
            }
        }
        Negocio.mostarProgreso(false,this,vp,txtCargando);
    }

    private void descargar_imagenes(final Imagenes img){
        CompositeDisposable desechable2 = new CompositeDisposable();
        if(img.getTamano().equals("small")){
            final String url_image = img.getImagen();
            Observable <Bitmap> ejecutador = Observable.fromCallable(new Callable<Bitmap>() {
                @Override
                public Bitmap call() throws Exception {
                    Bitmap bitmap = null;
                    try {
                        Drawable d = Negocio.LoadImageFromWebOperations(url_image);
                        if (d != null) {
                            bitmap = ((BitmapDrawable) d).getBitmap();
                        }
                    }catch (Exception e){}
                    return bitmap;
                }
            });
            Disposable subscriptor = ejecutador.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<Bitmap>() {
                @Override
                public void accept(Bitmap bitmap) throws Exception {
                    retorno_descarga_imagen(bitmap,img);
                }
            });
            desechable2.add(subscriptor);
        }
    }

    void retorno_descarga_imagen(Bitmap bitmap,Imagenes img){
        String nombre = img.getArtista();
        String query = "DELETE FROM tbl_imageness_artist WHERE nombre_artista = '"+nombre+"'";
        Negocio.ejecutarSQL(query,this);
        String url_image = img.getImagen();
        String size = img.getTamano();
        Negocio.guardarImagenArtista(nombre, url_image, bitmap, size, getApplicationContext());
    }

    public void ingresoSQLITE(List<Object> lista,int posicion){
        if(lista!=null){
            List<Artista> list = (ArrayList) lista.get(0);
            if(list!=null) {
                posiciones_actualizadas.add(posicion);
                if(list.size()>0) {
                    Ubicacion ubicacion = (Ubicacion) lista.get(1);
                    int pagina = ubicacion.getPage();
                    String query = "DELETE FROM tbl_artistas WHERE pais = '"+pais_actual+"' AND pagina = "+String.valueOf(pagina);
                    Negocio.ejecutarSQL(query,getApplication());
                    // Ingresar a la base de datos y presentar solo cuando haya cambiado
                    for (Artista a:list) {
                        final String nombre = a.getNombre();
                        Integer listeners = a.getListeners();
                        String mibd = a.getMbid();
                        String aUrl = a.getUrl();
                        Integer streamable = a.getStreamable();
                        ArrayList<Imagenes> images = a.getImages();
                        Negocio.guardarartista(nombre,listeners,mibd,aUrl,streamable,pais_actual,pagina,getApplication());
                        Imagenes img = images.get(0);
                        descargar_imagenes(img);
                    }
                }
            }
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(desechable != null && !desechable.isDisposed()){
            desechable.dispose();
        }
    }
}
