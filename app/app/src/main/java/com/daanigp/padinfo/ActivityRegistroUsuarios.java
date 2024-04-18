package com.daanigp.padinfo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class ActivityRegistroUsuarios extends AppCompatActivity {
    ImageView imgApp;
    Button btnCancelar, btnRegistrar;
    EditText txtUsuario, txtPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_usuarios);

        imgApp = (ImageView) findViewById(R.id.imgApp2);
        btnCancelar = (Button) findViewById(R.id.btnCancel);
        btnRegistrar = (Button) findViewById(R.id.btnNewRegistro);
        txtUsuario = (EditText) findViewById(R.id.editTxtNuevoUsuario);
        txtPassword = (EditText) findViewById(R.id.editTxtNuevaContrasenya);

        imgApp.setImageResource(R.drawable.padinfo_logo);

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "CAncelar", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "RegistrAO", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}