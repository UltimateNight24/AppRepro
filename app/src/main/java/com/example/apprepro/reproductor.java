package com.example.apprepro;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.media.Image;
import android.media.MediaPlayer;
import android.util.Log;
import android.widget.ImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;

public class reproductor {

    private static reproductor instancia=null;

    private int prueba;
    private MediaPlayer mediaPlayer;
    private boolean reproduciendo;
    private int posicion;
    private reproductor() {

    }

    public static reproductor obtenerConexion(){
        if(instancia==null){
            instancia=new reproductor();
        }
        return instancia;
    }

    public Bitmap getImage( JSONObject objeto) throws IOException {
        URL url= null;
        try {


            url = new URL(objeto.getJSONObject("data").getString("Imagen"));

        } catch (JSONException e) {
            e.printStackTrace();
        }
        Bitmap bmp= BitmapFactory.decodeStream(url.openConnection().getInputStream());

        return bmp;
    }

    public void setCancion(String ul){
        if(reproduciendo==true){
            mediaPlayer.stop();
            mediaPlayer.release();
            reproduciendo=false;
        }
        String url = ul; // your URL here
        try {
            mediaPlayer=new MediaPlayer();
            mediaPlayer.setDataSource(url);
            mediaPlayer.prepare(); // might take long! (for buffering, etc)
            mediaPlayer.start();
            reproduciendo=true;
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void clear(){
        mediaPlayer.stop();
        mediaPlayer.release();
        reproduciendo=false;
    }

    public void pausar(){
        if(reproduciendo==true){
            posicion= mediaPlayer.getCurrentPosition();
            mediaPlayer.pause();
            reproduciendo=false;
        }else{
            mediaPlayer.seekTo(posicion);
            mediaPlayer.start();
            reproduciendo=true;
        }
    }





}
