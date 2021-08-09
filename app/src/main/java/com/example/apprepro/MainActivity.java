package com.example.apprepro;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.wearable.activity.WearableActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class MainActivity extends WearableActivity {

    private TextView mTextView;
    Button boton;
    ListView myListView;
    Context contexto;
    conexiones conexion;
    reproductor repro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myListView = (ListView) findViewById(R.id.canciones);
        conexion = conexiones.obtenerConexion();
        repro=reproductor.obtenerConexion();
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        contexto=this;
        conexion.obtenerCanciones(this,myListView);

        //imagen.setImageBitmap(repro.getImage(""));


        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = (String) parent.getItemAtPosition(position);
                Log.e("xd",position+"");
                Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                Bundle b = new Bundle();
                b.putInt("key", position+1); //Your id
                intent.putExtras(b); //Put your id to your next Intent
                startActivity(intent);

            }
        });
        // Enables Always-on
        setAmbientEnabled();


    }
}
