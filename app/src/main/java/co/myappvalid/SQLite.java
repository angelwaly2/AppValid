package co.myappvalid;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class SQLite extends SQLiteOpenHelper {

    SQLiteDatabase base_de_datos = this.getWritableDatabase();

    public SQLite(Context ctx) {
        super(ctx, "tops", (SQLiteDatabase.CursorFactory)null, 1);
    }

    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE IF NOT EXISTS tbl_pais_seleccionado(pais VARCHAR(500));";
        db.execSQL(query);
        query = "CREATE TABLE IF NOT EXISTS tbl_artistas(nombre VARCHAR(500),listeners INTEGER,mibd VARCHAR(500),url VARCHAR(5000),streamable INTEGER,pais VARCHAR(500),pagina INTEGER);";
        db.execSQL(query);
        query = "CREATE TABLE IF NOT EXISTS tbl_imageness_artist(nombre_artista VARCHAR(500),url_image VARCHAR(5000),imagen BLOB,size VARCHAR(50))";
        db.execSQL(query);
        query = "CREATE TABLE IF NOT EXISTS tbl_canciones(nombre VARCHAR(500),duration INTEGER,listeners INTEGER,mibd VARCHAR(500),url VARCHAR(5000),pais VARCHAR(500),pagina INTEGER,rank INTEGER)";
        db.execSQL(query);
        query = "CREATE TABLE IF NOT EXISTS tbl_streamable(nombre_cancion VARCHAR(500),texto VARCHAR(250),fulltrack VARCHAR(250))";
        db.execSQL(query);
        query = "CREATE TABLE IF NOT EXISTS tbl_imageness_traks(nombre_cancion VARCHAR(500),url_image VARCHAR(5000),imagen BLOB,size VARCHAR(50))";
        db.execSQL(query);
    }

    public void ejecutar(String sql) {
        try {
            this.getWritableDatabase().execSQL(sql);
        } catch (Exception var3) {
            var3.printStackTrace();
        }

    }

    public String listarString(String sql) {
        Cursor c = this.base_de_datos.rawQuery(sql, (String[])null);
        String str = "";
        if(c != null && c.moveToFirst()) {
            do {
                str = c.getString(0);
            } while(c.moveToNext());
        }
        this.cerrar();
        c.close();
        return str;
    }

    public void guardarartista(String nombre,Integer listeners,String mibd,String url,Integer streamable,String pais,Integer pagina){
        ContentValues values = new ContentValues();
        values.put("nombre",nombre);
        values.put("listeners",listeners);
        values.put("mibd",mibd);
        values.put("url",url);
        values.put("pais",pais);
        values.put("streamable",streamable);
        values.put("pais",pais);
        values.put("pagina",pagina);
        base_de_datos.insert("tbl_artistas",null,values);
        this.cerrar();
    }

    public void guardarImagenArtista(String nombre_artista,String url_image,Bitmap bitmap,String size){
        ByteArrayOutputStream baos = new ByteArrayOutputStream(20480);
        bitmap.compress(Bitmap.CompressFormat.PNG, 0 , baos);
        byte[] blob = baos.toByteArray();
        ContentValues values = new ContentValues();
        values.put("nombre_artista",nombre_artista);
        values.put("url_image",url_image);
        values.put("imagen",blob);
        values.put("size",size);
        base_de_datos.insert("tbl_imageness_artist",null,values);
        this.cerrar();
    }

    public List<List<String>> devolver_datatable(String SQL) {
        ArrayList lista = new ArrayList();
        Cursor c = this.base_de_datos.rawQuery(SQL, (String[])null);
        if(c != null && c.moveToFirst()) {
            do {
                try {
                    ArrayList exp = new ArrayList();
                    for(int i = 0; i < c.getColumnCount(); ++i) {
                        String dato = c.getString(i);
                        exp.add(dato);
                    }
                    lista.add(exp);
                } catch (Exception var7) {
                    var7.printStackTrace();
                }
            } while(c.moveToNext());
        }
        return lista;
    }

    public Bitmap devolver_foto(String tabla,String campo,String valor){
        Bitmap imagen = null;
        SQLiteDatabase db = this.getReadableDatabase();

        String sql = String.format("SELECT imagen FROM " + tabla + " WHERE " + campo + " = '" + valor + "' AND size = 'small'");
        Cursor cursor = db.rawQuery(sql, new String[] {});
        if(cursor.moveToFirst()){
            byte[] blob = cursor.getBlob(0);
            ByteArrayInputStream bais = new ByteArrayInputStream(blob);
            imagen = BitmapFactory.decodeStream(bais);
        }
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
        db.close();
        return imagen;
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public void abrir() {
        this.getWritableDatabase();
    }

    public void cerrar() {
        this.close();
    }

}
