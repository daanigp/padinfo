package com.daanigp.padinfo;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ActivityPerfilUsuario extends AppCompatActivity {

    private static int EDIT_USER = 2;
    SQLiteDatabase db;
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

        db = openOrCreateDatabase("UsersPadinfo", Context.MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS users(User VARCHAR, Password VARCHAR, Isconnected INTEGER);");
        db.execSQL("CREATE TABLE IF NOT EXISTS userinfo(User VARCHAR, Name VARCHAR, Lastname VARCHAR, Email VARCHAR);");

        imgPerfil.setImageResource(R.drawable.icono_img);

        autocompleteUserInfo();

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
                startActivityForResult(intentEditarUsuario, EDIT_USER);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == EDIT_USER) {
            if (resultCode == RESULT_OK) {
                autocompleteUserInfo();
            }
        }
    }

    private void autocompleteUserInfo() {
        String user = getUserConnected();

        Cursor c = db.rawQuery("SELECT * FROM userinfo WHERE User = '" + user + "'", null);

        if (c.moveToFirst()){
            String nombre, apellidos, email;
            int indexNombre, indexApellidos, indexEmail;

            indexNombre = c.getColumnIndex("Name");
            indexApellidos = c.getColumnIndex("Lastname");
            indexEmail = c.getColumnIndex("Email");

            nombre = c.getString(indexNombre);
            apellidos = c.getString(indexApellidos);
            email = c.getString(indexEmail);

            if (!nombre.isEmpty() || nombre != null){
                txtNombre.setText(nombre);
            }

            if (!apellidos.isEmpty() || apellidos != null){
                txtApellidos.setText(apellidos);
            }

            if (!email.isEmpty() || email != null){
                txtEmail.setText(email);
            }
        } else {
            txtNombre.setText("vacío");
            txtApellidos.setText("vacío");
            txtEmail.setText("vacío");
        }

        c.close();
    }

    private String getUserConnected() {
        Cursor c = db.rawQuery("SELECT User FROM users WHERE Isconnected = 1", null);

        String user = null;
        if (c.moveToFirst()) {
            int index = c.getColumnIndex("UserEntity");
            user = c.getString(index);
        }

        c.close();

        return user;
    }
}