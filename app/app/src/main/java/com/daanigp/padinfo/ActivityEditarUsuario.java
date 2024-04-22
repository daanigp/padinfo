package com.daanigp.padinfo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class ActivityEditarUsuario extends AppCompatActivity {

    SQLiteDatabase db;
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

        db = openOrCreateDatabase("UsersPadinfo", Context.MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS users(User VARCHAR, Password VARCHAR, Isconnected INTEGER);");
        db.execSQL("CREATE TABLE IF NOT EXISTS userinfo(User VARCHAR, Name VARCHAR, Lastname VARCHAR, Email VARCHAR);");

        imgPerfilUsuario.setImageResource(R.drawable.icono_img);

        autocompleteUserInfo();

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "No has hehco ningún cambio.", Toast.LENGTH_SHORT).show();
                setResult(RESULT_CANCELED);
                finish();
            }
        });

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombre, apellidos, email;
                nombre = txtNombre.getText().toString();
                apellidos = txtApellidos.getText().toString();
                email = txtEmail.getText().toString();
                saveChanges(nombre, apellidos, email);
            }
        });

    }

    private void autocompleteUserInfo(){
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
            int index = c.getColumnIndex("User");
            user = c.getString(index);
        }

        c.close();

        return user;
    }

    private void saveChanges(String nombre, String apellidos, String email){
        String user = getUserConnected();

        Cursor c = db.rawQuery("SELECT * FROM userinfo WHERE User = '" + user + "'", null);

        if (c.getCount() != 0) {
            //Valores a actualizar
            ContentValues valores = new ContentValues();
            valores.put("Name", nombre);
            valores.put("Lastname", apellidos);
            valores.put("Email", email);

            // Definir la cláusula where para identificar el usuario a actualizar
            String whereClause = "User = ?";
            String[] whereArgs = { user };
            db.update("userinfo", valores, whereClause, whereArgs);

            Toast.makeText(getApplicationContext(), "Has guardado los cambios.", Toast.LENGTH_SHORT).show();
        }

        c.close();
        setResult(RESULT_OK);
        finish();
    }
}