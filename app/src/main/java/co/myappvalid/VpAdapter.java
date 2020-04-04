package co.myappvalid;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;
import java.util.List;

public class VpAdapter extends PagerAdapter {

    Context c;
    int total_paginas;
    String pais_actual;
    private ViewGroup contenido;
    private int destino;
    ArrayList<ImageView> listaContenedoresImagenes;

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((RelativeLayout)object);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager)container).removeView((RelativeLayout)object);
    }

    @Override
    public int getItemPosition(Object object) {
        return super.getItemPosition(object);
    }


    @SuppressLint("InflateParams") public Object instantiateItem(ViewGroup container, int position) {
        LayoutInflater inflater = (LayoutInflater)container.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.modelo_lista,null);
        contenido = (ViewGroup) v.findViewById(R.id.contenido);
        if(destino==0) {
            registrar_hijos_artista(v, position);
        }
        ((ViewPager)container).addView(v, 0);
        return v;
    }

    void registrar_hijos_artista(View v,int posicion){
        contenido = (ViewGroup) v.findViewById(R.id.contenido);
        contenido.removeAllViews();
        int pagina = posicion + 1;
        String query = "SELECT nombre,listeners,mibd,url,streamable FROM tbl_artistas WHERE pais = '"+pais_actual+"' AND pagina = " + pagina;
        List<List<String>> lista = Negocio.devolverDataTable(query, c);
        if (!lista.isEmpty()) {
            for (List<String> l : lista) {
                String nombre = l.get(0);
                String listeners = l.get(1);
                String aUrl = l.get(2);
                agregar_item(nombre,listeners,aUrl);
            }
        }
    }

    private void agregar_item(String nombre,String listeners,String aUrl) {
        LayoutInflater inflater = LayoutInflater.from(c);
        final int id = R.layout.item_artista;
        LinearLayout linearLayout = (LinearLayout) inflater.inflate(id, null, false);
        final TextView txtNombre = (TextView) linearLayout.findViewById(R.id.txtNombre);
        final TextView txtListeners = (TextView) linearLayout.findViewById(R.id.txtListeners);
        final TextView txtUrl = (TextView) linearLayout.findViewById(R.id.txtURL);
        ImageView img = (ImageView) linearLayout.findViewById(R.id.imgArtista);
        Bitmap bitmap = Negocio.devolverImagen("tbl_imageness_artist","nombre_artista",nombre, c);
        img.setImageBitmap(bitmap);
        listaContenedoresImagenes.add(img);
        txtNombre.setText(nombre);
        txtListeners.setText(listeners);
        txtUrl.setText(aUrl);
        contenido.addView(linearLayout);
    }

    public VpAdapter(int total_paginas,Context c,String pais_actual,int destino,ArrayList<ImageView> listaContenedoresImagenes){
        this.listaContenedoresImagenes = listaContenedoresImagenes;
        this.destino = destino;
        this.pais_actual = pais_actual;
        this.c = c;
        this.total_paginas = total_paginas;
    }

    @Override
    public int getCount() {
        return total_paginas;
    }
}
