package com.daanigp.padinfo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.daanigp.padinfo.Entity.Respone.Response;
import com.daanigp.padinfo.Entity.Security.CreateUser;
import com.daanigp.padinfo.Interface_API.IPadinfo_API;
import com.daanigp.padinfo.Interface_API.ISecurityPadinfo_API;
import com.daanigp.padinfo.Retrofit.RetrofitClient;
import com.daanigp.padinfo.Retrofit.RetrofitSecurityClient;

import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class ActivityRegistroUsuarios extends AppCompatActivity {
    private static final String TAG = "MyActivity";
    SQLiteDatabase db, db2;
    ImageView imgApp;
    Button btnCancelar, btnRegistrar;
    EditText txtUsuario, txtPassword, txtName, txtLastName, txtEmail;
    boolean exists;

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

        exists = true;
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
                String user, pwd, name, lastName, email;
                user = txtUsuario.getText().toString();
                pwd = txtPassword.getText().toString();
                name = txtName.getText().toString();
                lastName = txtLastName.getText().toString();
                email = txtEmail.getText().toString();
                //register(user, pwd, name, lastName, email);
                CreateUser createUser = new CreateUser(user, pwd, name, lastName, email, Collections.singletonList(2L));
                signup(user, createUser);
            }
        });

        db = openOrCreateDatabase("UsersPadinfo", Context.MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS users(User VARCHAR, Password VARCHAR, Isconnected INTEGER);");
        db.execSQL("CREATE TABLE IF NOT EXISTS userinfo(User VARCHAR, Name VARCHAR, Lastname VARCHAR, Email VARCHAR);");

    }

    private void register(String user, String pwd, String name, String lastName, String email){
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

    private void signup(String user, CreateUser createUser) {
        userExists(user);
        if (!exists) {
            ISecurityPadinfo_API securityPadinfoApi = RetrofitSecurityClient.getSecurityPadinfoAPI();

            Call<Response> call = securityPadinfoApi.registerUser(createUser);

            call.enqueue(new Callback<Response>() {
                @Override
                public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                    if (!response.isSuccessful()) {
                        Log.v(TAG, "No va (signup) -> response");
                        Toast.makeText(ActivityRegistroUsuarios.this, "Código error: " + response.code(), Toast.LENGTH_SHORT).show();
                        return;
                    }

                    Response res = response.body();
                    Toast.makeText(ActivityRegistroUsuarios.this, res.getMessege(), Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<Response> call, Throwable t) {
                    Log.e(TAG, "Error en la llamada Retrofit - (signup)", t);
                    Toast.makeText(ActivityRegistroUsuarios.this, "Código error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void userExists(String username) {

        IPadinfo_API padinfoApi = RetrofitClient.getPadinfoAPI();

        Call<Response> call = padinfoApi.checkUserExistsByUsername(username);

        call.enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                if (!response.isSuccessful()) {
                    Log.v(TAG, "No va (userExists) -> response");
                    Toast.makeText(ActivityRegistroUsuarios.this, "Código error: " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }

                Response res = response.body();

                if (res.getMessege().equalsIgnoreCase("No existe")) {
                    exists = false;
                } else {
                    Toast.makeText(ActivityRegistroUsuarios.this, "El usuario '" + username + "' ya está registrado.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {
                Log.e(TAG, "Error en la llamada Retrofit - (userExists)", t);
                Toast.makeText(ActivityRegistroUsuarios.this, "Código error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}