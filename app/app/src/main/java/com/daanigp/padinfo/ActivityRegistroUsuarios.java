package com.daanigp.padinfo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class ActivityRegistroUsuarios extends AppCompatActivity {
    SQLiteDatabase db, db2;
    ImageView imgApp;
    Button btnCancelar, btnRegistrar;
    EditText txtUsuario, txtPassword, txtName, txtLastName, txtEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_usuarios);

        imgApp = (ImageView) findViewById(R.id.imgApp2);
        btnCancelar = (Button) findViewById(R.id.btnCancel);
        btnRegistrar = (Button) findViewById(R.id.btnNewRegistro);
        txtUsuario = (EditText) findViewById(R.id.editTxtNuevoUsuario);
        txtPassword = (EditText) findViewById(R.id.editTxtNuevaContrasenya);
        txtName = (EditText) findViewById(R.id.editTxtNombreRegistro);
        txtLastName = (EditText) findViewById(R.id.editTxtApellidosRegistro);
        txtEmail = (EditText) findViewById(R.id.editTxtEmailRegistro);

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
                String user, pwd, name, lastName, email;
                user = txtUsuario.getText().toString();
                pwd = txtPassword.getText().toString();
                name = txtName.getText().toString();
                lastName = txtLastName.getText().toString();
                email = txtEmail.getText().toString();
                signup(user, pwd, name, lastName, email);
            }
        });

        db = openOrCreateDatabase("UsersPadinfo", Context.MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS users(User VARCHAR, Password VARCHAR, Isconnected INTEGER);");
        db.execSQL("CREATE TABLE IF NOT EXISTS userinfo(User VARCHAR, Name VARCHAR, Lastname VARCHAR, Email VARCHAR);");

    }

    private void signup(String user, String pwd, String name, String lastName, String email){
        Cursor c = db.rawQuery("SELECT * FROM users WHERE User = '" + user + "' AND Password = '" + pwd + "'", null);
        if (c.getCount() == 0){
            saveUserInfo(user, name, lastName, email);
            db.execSQL("INSERT INTO users VALUES('" + user + "', '" + pwd + "', 0);");
            c.close();
            Toast.makeText(getApplicationContext(), "Usuario registrado correctamente", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(getApplicationContext(), "Ya existe un usuario con ese nombre y contraseña (" + user + ", " + pwd + ")", Toast.LENGTH_SHORT).show();
            c.close();
            txtUsuario.setText("");
            txtPassword.setText("");
            txtName.setText("");
            txtLastName.setText("");
            txtEmail.setText("");
        }
    }

    private void saveUserInfo(String user, String name, String lastName, String email){
        db.execSQL("INSERT INTO userinfo VALUES('" + user + "', '" + name + "', '" + lastName + "', '" + email + "');");
    }
}