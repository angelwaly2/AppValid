package co.myappvalid;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.List;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

public class RestClient {
    private OkHttpClient client;

    /*todo****** metodo de lectura del Json */
    public List<Object> getArtists(String urlJSON) throws IOException, JSONException {
        ArrayList<Object> lista_def = new ArrayList<>();
        HttpUrl.Builder urlBuilder=  HttpUrl.parse(urlJSON).newBuilder();
        Request request = new Request.Builder().url(urlBuilder.build()).build();
        Response response = getClient().newCall(request).execute();
        if(response.isSuccessful()){
            List<Artista> list = new ArrayList<>();
            String cuerpo = response.body().string();
            JSONObject jsonObject = new JSONObject(cuerpo);
            double total = jsonObject.length();
            JSONArray columnas = jsonObject.names();
            for (int j = 0; j < total; j++) {
                String columna = columnas.getString(j);
                JSONObject jsonarraychild = jsonObject.getJSONObject(columna);
                JSONArray columnas2 = jsonarraychild.names();
                double total2 = jsonarraychild.length();
                for (int i = 0; i < total2; i++) {
                    String columna2 = columnas2.getString(i);
                    try {
                        JSONArray jsonarraychild2 = jsonarraychild.getJSONArray(columna2);
                        double totaljsonarraychild2 = jsonarraychild2.length();
                        for (int x = 0; x < totaljsonarraychild2; x++) {
                            JSONObject jsonarraychild3 = jsonarraychild2.getJSONObject(x);
                            JSONArray columnas3 = jsonarraychild3.names();
                            String nombre = null;
                            Integer listeners = 0;
                            String mbid = null;
                            String url = null;
                            int streamable = 0;
                            ArrayList<Imagenes> images = new ArrayList<>();
                            for (int k = 0; k < columnas3.length(); k++) {
                                String columna3 = columnas3.getString(k);

                                if (columna3.equals("name")) {
                                    nombre = jsonarraychild3.getString(columna3);
                                } else if (columna3.equals("listeners")) {
                                    listeners = jsonarraychild3.getInt(columna3);
                                } else if (columna3.equals("mbid")) {
                                    mbid = jsonarraychild3.getString(columna3);
                                } else if (columna3.equals("url")) {
                                    url = jsonarraychild3.getString(columna3);
                                } else if (columna3.equals("streamable")) {
                                    streamable = jsonarraychild3.getInt(columna3);
                                } else if (columna3.equals("image")) {
                                    String temp = jsonarraychild3.getString(columna3);
                                    JSONArray listArray = new JSONArray(temp);
                                    for (int y = 0; y < listArray.length(); y++) {
                                        JSONObject jsonImagenes = listArray.getJSONObject(y);
                                        JSONArray cols4 = jsonImagenes.names();
                                        Imagenes imagenes = new Imagenes();
                                        for (int l = 0; l < cols4.length(); l++) {
                                            String col4 = cols4.getString(l);
                                            if (col4.equals("#text")) {
                                                imagenes.setImagen(jsonImagenes.getString(col4));
                                            } else if (col4.equals("size")) {
                                                imagenes.setTamano(jsonImagenes.getString(col4));
                                            }
                                        }
                                        imagenes.setArtista(nombre);
                                        images.add(imagenes);
                                    }
                                }

                            }
                            Artista artista = new Artista();
                            artista.setNombre(nombre);
                            artista.setListeners(listeners);
                            artista.setMbid(mbid);
                            artista.setUrl(url);
                            artista.setStreamable(streamable);
                            artista.setImages(images);
                            list.add(artista);
                        }
                        lista_def.add(list);
                    }catch (Exception exp){
                        Ubicacion ubicacion = new Ubicacion();
                        JSONObject jsonObject2 = jsonarraychild.getJSONObject(columna2);
                        JSONArray cols4 = jsonObject2.names();
                        String country = null;
                        int page = 0;
                        Double perPage = Double.valueOf(0);
                        int totalPages = 0;
                        Double totalReg = Double.valueOf(0);
                        for (int l = 0; l < cols4.length(); l++) {
                            String col4 = cols4.getString(l);
                            if(col4.equals("country")){
                                country = jsonObject2.getString(col4);
                            }else if(col4.equals("page")){
                                page = jsonObject2.getInt(col4);
                            }else if(col4.equals("perPage")){
                                perPage = jsonObject2.getDouble(col4);
                            }else if(col4.equals("totalPages")){
                                totalPages = jsonObject2.getInt(col4);
                            }else if(col4.equals("total")){
                                totalReg = jsonObject2.getDouble(col4);
                            }
                        }
                        ubicacion.setCountry(country);
                        ubicacion.setPage(page);
                        ubicacion.setPerPage(perPage);
                        ubicacion.setTotalPages(totalPages);
                        ubicacion.setTotalReg(totalReg);
                        lista_def.add(ubicacion);
                    }
                }
            }
            return lista_def;
        }else {
            throw new IOException();
        }
    }

    public List<Object> getTracks(String urlJSON) throws IOException, JSONException {
        ArrayList<Object> lista_def = new ArrayList<>();
        HttpUrl.Builder urlBuilder=  HttpUrl.parse(urlJSON).newBuilder();
        Request request = new Request.Builder().url(urlBuilder.build()).build();
        Response response = getClient().newCall(request).execute();
        if(response.isSuccessful()){
            List<Tracks> list = new ArrayList<>();
            String cuerpo = response.body().string();
            JSONObject jsonObject = new JSONObject(cuerpo);
            double total = jsonObject.length();
            JSONArray columnas = jsonObject.names();
            for (int j = 0; j < total; j++) {
                String columna = columnas.getString(j);
                JSONObject jsonarraychild = jsonObject.getJSONObject(columna);
                JSONArray columnas2 = jsonarraychild.names();
                double total2 = jsonarraychild.length();
                for (int i = 0; i < total2; i++) {
                    String columna2 = columnas2.getString(i);
                    try {
                        JSONArray jsonarraychild2 = jsonarraychild.getJSONArray(columna2);
                        double totaljsonarraychild2 = jsonarraychild2.length();
                        for (int x = 0; x < totaljsonarraychild2; x++) {
                            JSONObject jsonarraychild3 = jsonarraychild2.getJSONObject(x);
                            JSONArray columnas3 = jsonarraychild3.names();
                            String nombre = null;
                            Integer duration = 0;
                            int listeners = 0;
                            String url = null;
                            int streamable = 0;
                            ArrayList<Imagenes> images = new ArrayList<>();
                            for (int k = 0; k < columnas3.length(); k++) {
                                String columna3 = columnas3.getString(k);

                                if (columna3.equals("name")) {
                                    nombre = jsonarraychild3.getString(columna3);
                                } else if (columna3.equals("duration")) {
                                    duration = jsonarraychild3.getInt(columna3);
                                } else if (columna3.equals("listeners")) {
                                    listeners = jsonarraychild3.getInt(columna3);
                                } else if (columna3.equals("url")) {
                                    url = jsonarraychild3.getString(columna3);
                                } else if (columna3.equals("streamable")) {
                                    streamable = jsonarraychild3.getInt(columna3);
                                } else if (columna3.equals("image")) {
                                    String temp = jsonarraychild3.getString(columna3);
                                    JSONArray listArray = new JSONArray(temp);
                                    for (int y = 0; y < listArray.length(); y++) {
                                        JSONObject jsonImagenes = listArray.getJSONObject(y);
                                        JSONArray cols4 = jsonImagenes.names();
                                        Imagenes imagenes = new Imagenes();
                                        for (int l = 0; l < cols4.length(); l++) {
                                            String col4 = cols4.getString(l);
                                            if (col4.equals("#text")) {
                                                imagenes.setImagen(jsonImagenes.getString(col4));
                                            } else if (col4.equals("size")) {
                                                imagenes.setTamano(jsonImagenes.getString(col4));
                                            }
                                        }
                                        images.add(imagenes);
                                    }
                                }

                            }
                            Tracks track = new Tracks();
                            track.setNombre(nombre);
                            track.setDuration(duration);
                            track.setListeners(listeners);
                            track.setUrl(url);
                            track.setImages(images);
                            list.add(track);
                        }
                        lista_def.add(list);
                    }catch (Exception exp){
                        Ubicacion ubicacion = new Ubicacion();
                        JSONObject jsonObject2 = jsonarraychild.getJSONObject(columna2);
                        JSONArray cols4 = jsonObject2.names();
                        String country = null;
                        int page = 0;
                        Double perPage = Double.valueOf(0);
                        int totalPages = 0;
                        Double totalReg = Double.valueOf(0);
                        for (int l = 0; l < cols4.length(); l++) {
                            String col4 = cols4.getString(l);
                            if(col4.equals("country")){
                                country = jsonObject2.getString(col4);
                            }else if(col4.equals("page")){
                                page = jsonObject2.getInt(col4);
                            }else if(col4.equals("perPage")){
                                perPage = jsonObject2.getDouble(col4);
                            }else if(col4.equals("totalPages")){
                                totalPages = jsonObject2.getInt(col4);
                            }else if(col4.equals("total")){
                                totalReg = jsonObject2.getDouble(col4);
                            }
                        }
                        ubicacion.setCountry(country);
                        ubicacion.setPage(page);
                        ubicacion.setPerPage(perPage);
                        ubicacion.setTotalPages(totalPages);
                        ubicacion.setTotalReg(totalReg);
                        lista_def.add(ubicacion);
                    }
                }
            }
            return lista_def;
        }else {
            throw new IOException();
        }
    }

    private OkHttpClient getClient() {
        if(client == null) {
            try {
                client = createClient();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (KeyManagementException e) {
                e.printStackTrace();
            }
        }
        return client;
    }

    private OkHttpClient createClient() throws NoSuchAlgorithmException, KeyManagementException {
        OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();
        final TrustManager[] trustAllCerts = new TrustManager[] {
                new X509TrustManager() {
                    @Override
                    public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                    }

                    @Override
                    public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                    }

                    @Override
                    public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                        return new java.security.cert.X509Certificate[]{};
                    }
                }
        };
        final SSLContext sslContext = SSLContext.getInstance("SSL");
        sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
        final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
        okHttpClientBuilder.sslSocketFactory(sslSocketFactory, (X509TrustManager)trustAllCerts[0]);
        okHttpClientBuilder.hostnameVerifier(new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        });

        okHttpClientBuilder.addInterceptor(
                new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        );
        return okHttpClientBuilder.build();
    }
}
