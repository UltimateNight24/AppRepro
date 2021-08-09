package com.example.apprepro;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class conexiones {

    private static conexiones instancia=null;

    public String link="http://dd1575c7564a.ngrok.io/";
    private conexiones() {

    }

    public static conexiones obtenerConexion(){
        if(instancia==null){
            instancia=new conexiones();
        }
        return instancia;
    }

    public void obtenerCanciones(final Context contexto, final ListView listView){

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(contexto);
        String url =link+"api/musica";

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        //textView.setText("Response is: "+ response.substring(0,500));
                        Log.e("Hola",response);
                        try {
                            JSONObject objeto=new JSONObject(response);
                            List<String> lista = new ArrayList<>();
                            // this-The current activity context.
                            // Second param is the resource Id for list layout row item
                            // Third param is input array

                            for (int i=0; i<objeto.getJSONArray("data").length();i++){
                                lista.add(objeto.getJSONArray("data").getJSONObject(i).getString("Nombre"));
                                Log.e("Muestra",objeto.getJSONArray("data").getJSONObject(i).getString("Nombre"));
                            }

                            ArrayAdapter arrayAdapter = new ArrayAdapter(contexto, android.R.layout.simple_list_item_1,lista );
                            listView.setAdapter(arrayAdapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //textView.setText("That didn't work!");
                Log.e("Hola",error.toString());
            }
        });

        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

    public void obtenerCancion(Context contexto, final int cancion, final reproductor repro, final ImageView imange){
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(contexto);
        String url =link+"api/musica/"+cancion;



        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        //textView.setText("Response is: "+ response.substring(0,500));

                        try {
                            JSONObject object=new JSONObject(response);
                            Log.e("No se que pasa",cancion+"");
                            repro.setCancion(object.getJSONObject("data").getString("Direccion"));
                            try {
                                URL url = new URL(object.getJSONObject("data").getString("Imagen"));
                                Bitmap bmp= null;
                                try {
                                    bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                imange.setImageBitmap(bmp);
                            } catch (MalformedURLException e) {
                                e.printStackTrace();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //textView.setText("That didn't work!");
                Log.e("Hola",error.toString());
            }
        });

        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

}
