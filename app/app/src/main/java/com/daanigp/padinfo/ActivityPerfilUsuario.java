package com.daanigp.padinfo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class ActivityPerfilUsuario extends AppCompatActivity {

    TextView txtNombre, txtApellidos, txtEmail;
    Button btnVolver, btnEditar;
    ImageView imgPerfil;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_usuario);

        txtNombre = (TextView) findViewById(R.id.txtNombreUsuario);
        txtApellidos = (TextView) findViewById(R.id.txtApellidos);
        txtEmail = (TextView) findViewById(R.id.txtEmail);
        btnVolver = (Button) findViewById(R.id.btnVolverM);
        btnEditar = (Button) findViewById(R.id.btnEditar);
        imgPerfil = (ImageView) findViewById(R.id.imgPerfil);

        imgPerfil.setImageResource(R.drawable.icono_img);

        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentEditarUsuario = new Intent(ActivityPerfilUsuario.this, ActivityEditarUsuario.class);
                startActivity(intentEditarUsuario);
            }
        });

    }
}