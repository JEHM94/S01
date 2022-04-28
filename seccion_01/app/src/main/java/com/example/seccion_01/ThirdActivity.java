package com.example.seccion_01;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class ThirdActivity extends AppCompatActivity {

    private EditText editTextPhone;
    private EditText editTextWeb;
    private ImageButton imageButtonPhone, imageButtonWeb, imageButtonCamera, imageButtonContacts, imageButtonMail, imageButtonFullMail;

    private final int PHONE_CALL_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        // Activar flecha ir atrás
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        editTextPhone = (EditText) findViewById(R.id.editTextPhone);
        editTextWeb = (EditText) findViewById(R.id.editTextTextWeb);
        imageButtonPhone = (ImageButton) findViewById(R.id.imageButtonPhone);
        imageButtonWeb = (ImageButton) findViewById(R.id.imageButtonWeb);
        imageButtonCamera = (ImageButton) findViewById(R.id.imageButtonCamera);
        imageButtonContacts = (ImageButton) findViewById(R.id.imageButtonContacts);
        imageButtonMail = (ImageButton) findViewById(R.id.imageButtonMail);
        imageButtonFullMail = (ImageButton) findViewById(R.id.imageButtonFullMail);

        imageButtonPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phoneNumber = editTextPhone.getText().toString();
                if (phoneNumber != null && !phoneNumber.isEmpty()) {

                    //Telefono 2, sin permisos requeridos
                    //Intent intentPhone = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phoneNumber));
                    //startActivity(intentPhone);

                    //comprobar version actual de android que estamos corriendo
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                        //Comprobar si ha aceptado, no ha aceptado o nunca se le ha preguntado
                        if (checkPermission(Manifest.permission.CALL_PHONE)) {
                            //Ha aceptado
                            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel: " + phoneNumber));
                            startActivity(intent);
                        } else {
                            //Ha denegado o es la primera vez que se pregunta
                            if (!shouldShowRequestPermissionRationale(Manifest.permission.CALL_PHONE)) {
                                //No se le ha preguntado aún
                                requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, PHONE_CALL_CODE);
                            } else {
                                //Ha denegado / FLAGS PARA VOLVER ATRAS (LOGING)
                                Toast.makeText(ThirdActivity.this, "Please enable the request permission", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                intent.addCategory(Intent.CATEGORY_DEFAULT);
                                intent.setData(Uri.parse("package:" + getPackageName()));
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                                intent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                                startActivity(intent);
                            }
                        }
                    } else {
                        OlderVersions(phoneNumber);
                    }
                } else {
                    Toast.makeText(ThirdActivity.this, "Insert a Phone Number", Toast.LENGTH_SHORT).show();
                }
            }

            private void OlderVersions(String phoneNumber) {
                Intent intentCall = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phoneNumber));
                if (checkPermission(Manifest.permission.CALL_PHONE)) {
                    startActivity(intentCall);
                } else {
                    Toast.makeText(ThirdActivity.this, "You declined the access", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //Boton para la direccion web
        imageButtonWeb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = editTextWeb.getText().toString();
                if (url != null && !url.isEmpty()) {
                    //Intent intentWeb = new Intent(Intent.ACTION_VIEW, Uri.parse("http://" + url));
                    //otra forma de hacerlo
                    Intent intentWeb = new Intent();
                    intentWeb.setAction(Intent.ACTION_VIEW);
                    intentWeb.setData(Uri.parse("http://" + url));
                    startActivity(intentWeb);
                }
            }
        });

        //Boton Contactos
        imageButtonContacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentContacts = new Intent(Intent.ACTION_VIEW, Uri.parse("content://contacts/people"));
                startActivity(intentContacts);
            }
        });

        //Boton email rapido
        String email = "jesus.e.hamel@gmail.com";
        imageButtonMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent emailRapido = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto: " + email));
                startActivity(emailRapido);
            }
        });

        //Boton email completo
        String mailCc[] = {"at@gmail.com", "2ta@gmail.com"};
        imageButtonFullMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentFullmail = new Intent(Intent.ACTION_SEND, Uri.parse(email));
                intentFullmail.setClassName("com.google.android.gm", "com.google.android.gm.ComposeActivityGmail");
                intentFullmail.setType("plain/text");
                intentFullmail.putExtra(Intent.EXTRA_SUBJECT, "Mail's tittle");
                intentFullmail.putExtra(Intent.EXTRA_TEXT, "Hi there, I love myForm app, but... ");
                intentFullmail.putExtra(Intent.EXTRA_EMAIL, mailCc);
                startActivity(Intent.createChooser(intentFullmail, "Elige cliente de correo"));
            }
        });

        imageButtonCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Boton Camara
                Intent intentCamera = new Intent("android.media.action.IMAGE_CAPTURE");
                startActivity(intentCamera);
            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //En caso de Telefono
        switch (requestCode) {
            case PHONE_CALL_CODE:

                String permission = permissions[0];
                int result = grantResults[0];

                if (permission.equals(Manifest.permission.CALL_PHONE)) {
                    //Comprobar si ha sido aceptado o denegado la peticion de permiso
                    if (result == PackageManager.PERMISSION_GRANTED) {
                        //Concedio su permiso
                        String phoneNumber = editTextPhone.getText().toString();
                        Intent intentCall = new Intent(Intent.ACTION_CALL, Uri.parse("tel: " + phoneNumber));
                        startActivity(intentCall);
                    } else {
                        //No concedio su permiso
                        Toast.makeText(ThirdActivity.this, "You declined the access", Toast.LENGTH_SHORT).show();
                    }
                }

                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
                break;
        }
    }

    private boolean checkPermission(String permission) {
        int result = this.checkCallingOrSelfPermission(permission);
        return result == PackageManager.PERMISSION_GRANTED;
    }

}