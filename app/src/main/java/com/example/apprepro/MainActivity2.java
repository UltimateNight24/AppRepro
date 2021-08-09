package com.example.apprepro;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;

public class MainActivity2 extends Activity {

    private TextView mTextView;
    private int value;
    private ImageView imageView;

    reproductor repro=reproductor.obtenerConexion();
    conexiones con=conexiones.obtenerConexion();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        mTextView = findViewById(R.id.text);
        imageView=findViewById(R.id.imageView);

        Bundle b = getIntent().getExtras();
        value = -1; // or other values
        if(b != null)
            value = b.getInt("key");
        con.obtenerCancion(this,value,repro,imageView);
    }

    public <error> void siguienteCancion (View view){
        value+=1;
        con.obtenerCancion(this,value,repro,imageView);

    }

    public void pausar (View view){
        repro.pausar();
    }

    public void cancionAnterior(View view){
        value--;
        con.obtenerCancion(this,value,repro,imageView);
    }

    @Override
    protected void onDestroy() {
        repro.clear();
        super.onDestroy();
    }
}