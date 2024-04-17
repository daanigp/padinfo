package com.daanigp.padinfo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class ActivityEditarUsuario extends AppCompatActivity {

    EditText txtNombre, txtApellidos, txtEmail;
    Button btnGuardar, btnCancelar;
    ImageView imgPerfilUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_usuario);

        txtNombre = (EditText) findViewById(R.id.editTxtNombre);
        txtApellidos = (EditText) findViewById(R.id.editTxtApellidos);
        txtEmail = (EditText) findViewById(R.id.editTxtEmail);
        btnGuardar = (Button) findViewById(R.id.btnGuardarInfo);
        btnCancelar = (Button) findViewById(R.id.btnVolverM);
        imgPerfilUsuario = (ImageView) findViewById(R.id.imgPerfil);

        imgPerfilUsuario.setImageResource(R.drawable.icono_img);

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "No has hehco ningún cambio.", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Has guardado los cambios.", Toast.LENGTH_SHORT).show();
            }
        });

    }
}