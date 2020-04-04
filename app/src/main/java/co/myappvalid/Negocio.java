package co.myappvalid;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Negocio {
    public static String listarString(String SQL, Activity mAct){
        String dato = "";
        SQLite objc = new SQLite(mAct);
        objc.abrir();
        dato = objc.listarString(SQL);
        objc.cerrar();
        return dato;
    }
    public static void ejecutarSQL(String SQL, Context c){
        try{
            SQLite objc = new SQLite(c);
            objc.abrir();
            objc.ejecutar(SQL);
            objc.cerrar();
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
    public static void guardarartista(String nombre,Integer listeners,String mibd,String url,Integer streamable,String pais,Integer pagina,Context c){
        try{
            SQLite objc = new SQLite(c);
            objc.abrir();
            objc.guardarartista(nombre,listeners,mibd,url,streamable,pais,pagina);
            objc.cerrar();
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
    public static List<List<String>> devolverDataTable(String SQL, Context c){
        List<List<String>> lista = new ArrayList<List<String>>();
        try{
            SQLite objc = new SQLite(c);
            objc.abrir();
            lista = objc.devolver_datatable(SQL);
            objc.cerrar();
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return lista;
    }

    public static Drawable LoadImageFromWebOperations(String url) {
        try {
            InputStream is = (InputStream) new URL(url).getContent();
            Drawable d = Drawable.createFromStream(is, "src name");
            return d;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void guardarImagenArtista(String nombre_artista,String url_image,Bitmap bitmap,String size,Context c){
        try{
            SQLite objc = new SQLite(c);
            objc.abrir();
            objc.guardarImagenArtista(nombre_artista,url_image,bitmap,size);
            objc.cerrar();
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }

    public static Bitmap devolverImagen(String tabla,String campo,String valor,Context c){
        Bitmap bm = null;
        try{
            SQLite objc = new SQLite(c);
            objc.abrir();
            bm = objc.devolver_foto(tabla,campo,valor);
            objc.cerrar();
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return bm;
    }

    public static void mostarProgreso(final boolean show, Context c, final View contenido,final View progreso) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = c.getResources().getInteger(android.R.integer.config_shortAnimTime);

            contenido.setVisibility(show ? View.GONE : View.VISIBLE);
            contenido.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    contenido.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            progreso.setVisibility(show ? View.VISIBLE : View.GONE);
            progreso.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    progreso.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            progreso.setVisibility(show ? View.VISIBLE : View.GONE);
            contenido.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }
}
