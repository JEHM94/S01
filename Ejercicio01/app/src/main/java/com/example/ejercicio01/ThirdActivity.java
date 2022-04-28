package com.example.ejercicio01;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class ThirdActivity extends AppCompatActivity {

    //Elementos UI
    private Button buttonConfirm;
    private Button buttonShare;

    //Otros valores
    private String name;
    private int age;
    private int saluteType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        // Activar la flecha para volver al activity principal
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Recogemos los datos del activity anterior
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            name = bundle.getString("name");
            age = bundle.getInt("age");
            saluteType = bundle.getInt("saluteType");
        }

        // Instanciamos los elementos de la UI con sus referencias
        buttonConfirm = (Button) findViewById(R.id.buttonConfirm);
        buttonShare = (Button) findViewById(R.id.buttonShare);

        buttonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ThirdActivity.this, CustomMessage(name, age, saluteType), Toast.LENGTH_LONG).show();
                buttonConfirm.setVisibility(View.INVISIBLE);
                buttonShare.setVisibility(View.VISIBLE);
            }


        });

        buttonShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, CustomMessage(name, age, saluteType));
                startActivity(intent);
            }
        });
    }

    private String CustomMessage(String name, int age, int saluteType) {
        if (saluteType == SecondActivity.GREETER_OPTION) {
            return "Hola " + name + ", ¿Cómo llevas esos " + age + " años? #MyForm";
        } else {
            return "Espero verte pronto " + name + ", antes que cumplas " + (age + 1) + ".. #MyForm";
        }
    }
}