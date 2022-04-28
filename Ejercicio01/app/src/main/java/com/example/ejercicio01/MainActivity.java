package com.example.ejercicio01;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText personName;
    private Button buttonNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Forzar el logo, en todas las versiones android
        getSupportActionBar().setLogo(R.mipmap.ic_launcher);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        // Instanciamos los elementos de la UI con sus referencias
        personName = (EditText) findViewById(R.id.TextPersonName);
        buttonNext = (Button) findViewById(R.id.buttonNext);

        // Evento click del botón para pasar al siguiente Activity
        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = personName.getText().toString();
                if (!name.isEmpty()) {
                    Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                    intent.putExtra("name", name);
                    startActivity(intent);
                } else {
                    Toast.makeText(MainActivity.this, "Name cannot be blank, please type a name", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}