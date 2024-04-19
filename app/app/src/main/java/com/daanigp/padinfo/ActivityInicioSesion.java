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

public class ActivityInicioSesion extends AppCompatActivity {

    SQLiteDatabase db;
    Button btnInicioSesion, btnRegistrarse, btnVolver;
    EditText txtUsuario, txtPassword;
    ImageView imgApp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio_sesion);

        btnInicioSesion = (Button) findViewById(R.id.btnIniciarSesion);
        btnRegistrarse = (Button) findViewById(R.id.btnRegistrarse);
        btnVolver = (Button) findViewById(R.id.btnVolverInicio);
        txtUsuario = (EditText) findViewById(R.id.editTxtUsuario);
        txtPassword = (EditText) findViewById(R.id.editTxtContrasenya);
        imgApp = (ImageView) findViewById(R.id.imgApp);

        imgApp.setImageResource(R.drawable.padinfo_logo);

        btnInicioSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user, pwd;
                user = txtUsuario.getText().toString();
                pwd = txtPassword.getText().toString();
                signIn(user, pwd);
            }
        });

        btnRegistrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtUsuario.setText("");
                txtPassword.setText("");
                Toast.makeText(getApplicationContext(), "Registro", Toast.LENGTH_SHORT).show();
                Intent intentRegistro = new Intent(ActivityInicioSesion.this, ActivityRegistroUsuarios.class);
                startActivity(intentRegistro);
            }
        });

        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        db = openOrCreateDatabase("UsersPadinfo", Context.MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS users(User VARCHAR, Password VARCHAR, Isconnected INTEGER);");
    }

    private void signIn(String user, String pwd){
        Cursor c = db.rawQuery("SELECT * FROM users WHERE User = '" + user + "' AND Password = '" + pwd + "'", null);
        if (c.getCount() == 0) {
            Toast.makeText(getApplicationContext(), "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show();
            txtUsuario.setText("");
            txtPassword.setText("");
            c.close();
        } else {
            //Valores a actualizar
            ContentValues valores = new ContentValues();
            valores.put("Isconnected", 1);

            // Definir la cláusula where para identificar el usuario a actualizar
            String whereClause = "User = ?";
            String[] whereArgs = { user };
            db.update("users", valores, whereClause, whereArgs);

            Toast.makeText(getApplicationContext(), "Usuario logueado correctamente", Toast.LENGTH_SHORT).show();

            Intent intentAppInicio = new Intent(ActivityInicioSesion.this, Activity_Inicio.class);
            intentAppInicio.putExtra("username", user);
            setResult(RESULT_OK, intentAppInicio);
            finish();
        }
    }


}