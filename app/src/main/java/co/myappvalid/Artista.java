package co.myappvalid;

import android.media.Image;

import java.util.ArrayList;
import java.util.List;

public class Artista {
    private String nombre;
    private Integer Listeners;
    private String mbid;
    private String url;
    private int streamable;
    private ArrayList<Imagenes> images;

    public String getMbid() {
        return mbid;
    }

    public void setMbid(String mbid) {
        this.mbid = mbid;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getStreamable() {
        return streamable;
    }

    public void setStreamable(int streamable) {
        this.streamable = streamable;
    }

    public ArrayList<Imagenes> getImages() {
        return images;
    }

    public void setImages(ArrayList<Imagenes> images) {
        this.images = images;
    }

    public void setListeners(Integer listeners) {
        Listeners = listeners;
    }



    public Integer getListeners() {
        return Listeners;
    }


    public void setNombre(String nombre){
        this.nombre = nombre;
    }
    public String getNombre(){
        return this.nombre;
    }

}
