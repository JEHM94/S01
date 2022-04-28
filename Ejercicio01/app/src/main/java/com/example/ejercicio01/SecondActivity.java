package com.example.ejercicio01;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class SecondActivity extends AppCompatActivity {

    //Elementos de la UI
    private SeekBar seekBarAge;
    private TextView textViewAge;
    private Button buttonNext;
    private RadioButton radioButtonGreeter;
    private RadioButton radioButtonFarewell;

    //Otros valores
    private String name = "";
    private int age = 18;
    private final int MIN_AGE = 16;
    private final int MAX_AGE = 60;

    //Para compartir
    public static final int GREETER_OPTION = 1;
    public static final int FAREWELL_OPTION = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        // Activar la flecha para volver al activity principal
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Recogemos el nombre del activity anterior
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            name = bundle.getString("name");
        }

        // Instanciamos los elementos de la UI con sus referencias
        seekBarAge = (SeekBar) findViewById(R.id.seekBarAge);
        textViewAge = (TextView) findViewById(R.id.textViewAge);
        buttonNext = (Button) findViewById(R.id.buttonNext);
        radioButtonFarewell = (RadioButton) findViewById(R.id.radioButtonFarewell);
        radioButtonGreeter = (RadioButton) findViewById(R.id.radioButtonGreeter);

        // Evento change para el SeekBar
        seekBarAge.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                age = i;
                textViewAge.setText(String.valueOf(age));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // Aunque no lo sobreescribamos con alguna funcionalidad, OnSeekBarChangeListener es una interfaz
                // y como interfaz que es, necesitamos sobreescribir todos sus métodos, aunque lo dejemos vacío.
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (age < MIN_AGE || age > MAX_AGE) {
                    Toast.makeText(SecondActivity.this, "Age must be between " + MIN_AGE + " and " + MAX_AGE + " years old.", Toast.LENGTH_LONG).show();
                    buttonNext.setVisibility(View.INVISIBLE);
                } else {
                    buttonNext.setVisibility(View.VISIBLE);
                }

            }
        });

        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SecondActivity.this, ThirdActivity.class);
                intent.putExtra("name", name);
                intent.putExtra("age", age);
                int saluteType = (radioButtonGreeter.isChecked()) ? GREETER_OPTION : FAREWELL_OPTION;
                intent.putExtra("saluteType", saluteType);

                startActivity(intent);

            }
        });

    }
}