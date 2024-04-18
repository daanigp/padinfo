package com.daanigp.padinfo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class ActivityInicioSesion extends AppCompatActivity {

    Button btnInicioSesion, btnRegistrarse;
    EditText txtUsuario, txtPassword;
    ImageView imgApp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio_sesion);

        btnInicioSesion = (Button) findViewById(R.id.btnIniciarSesion);
        btnRegistrarse = (Button) findViewById(R.id.btnRegistrarse);
        txtUsuario = (EditText) findViewById(R.id.editTxtUsuario);
        txtPassword = (EditText) findViewById(R.id.editTxtContrasenya);
        imgApp = (ImageView) findViewById(R.id.imgApp);

        imgApp.setImageResource(R.drawable.padinfo_logo);

        btnInicioSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentAppInicio = new Intent(ActivityInicioSesion.this, Activity_Inicio.class);
                startActivity(intentAppInicio);
            }
        });

        btnRegistrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Registro", Toast.LENGTH_SHORT).show();
                Intent intentRegistro = new Intent(ActivityInicioSesion.this, ActivityRegistroUsuarios.class);
                startActivity(intentRegistro);
            }
        });
    }
}