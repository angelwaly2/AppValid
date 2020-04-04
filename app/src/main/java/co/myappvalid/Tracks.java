package co.myappvalid;

import java.util.ArrayList;

public class Tracks {
    String nombre;
    Integer duration;
    Integer listeners;
    String url;
    private ArrayList<Imagenes> images;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Integer getListeners() {
        return listeners;
    }

    public void setListeners(Integer listeners) {
        this.listeners = listeners;
    }

    public ArrayList<Imagenes> getImages() {
        return images;
    }

    public void setImages(ArrayList<Imagenes> images) {
        this.images = images;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
