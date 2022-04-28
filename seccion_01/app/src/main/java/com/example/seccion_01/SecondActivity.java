package com.example.seccion_01;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class SecondActivity extends AppCompatActivity {

    private TextView textView;
    private Button buttonNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        // Activar flecha ir atrás
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        textView = (TextView) findViewById(R.id.textViewMain);
        buttonNext = (Button) findViewById(R.id.buttonGoSharing);

        //Tomar los datos
        Bundle bundle = getIntent().getExtras();
        if(bundle != null && bundle.getString("greeter") != null ){
            String greeter = bundle.getString("greeter");
            textView.setText(greeter);
            Toast.makeText(SecondActivity.this, greeter, Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(SecondActivity.this, "It is empty", Toast.LENGTH_SHORT).show();
        }

        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SecondActivity.this, ThirdActivity.class);
                startActivity(intent);
            }
        });
    }
}