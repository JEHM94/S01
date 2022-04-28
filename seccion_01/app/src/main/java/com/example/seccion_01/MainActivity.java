package com.example.seccion_01;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button btn;
    private final String GREETER = "Hello From the other side!";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //forzar y cargar icono en el action bar
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_myicon);

        btn = (Button) findViewById(R.id.buttonMain);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(MainActivity.this, "Hola desde código", Toast.LENGTH_SHORT).show();
                //Acceder al segundo activity y mandarle un string
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                intent.putExtra("greeter", GREETER);
                startActivity(intent);
            }
        });
    }
}