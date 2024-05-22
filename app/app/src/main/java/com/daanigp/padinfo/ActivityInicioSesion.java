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

import com.daanigp.padinfo.Interface_API.ISecurityPadinfo_API;
import com.daanigp.padinfo.Retrofit.RetrofitSecurityClient;
import com.daanigp.padinfo.Security.Entity.Login;
import com.daanigp.padinfo.Security.Entity.Token;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ActivityInicioSesion extends AppCompatActivity {

    SQLiteDatabase db;
    Button btnInicioSesion, btnRegistrarse, btnInicioInvitado;
    EditText txtUsuario, txtPassword;
    ImageView imgApp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio_sesion);

        btnInicioSesion = (Button) findViewById(R.id.btnIniciarSesion);
        btnRegistrarse = (Button) findViewById(R.id.btnRegistrarse);
        btnInicioInvitado = (Button) findViewById(R.id.btnInicioInvitado);
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
                //signIn(user, pwd);

                login(user, pwd);
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

        btnInicioInvitado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ActivityInicioSesion.this, "INICIO DE SESIÓN COMO INVITADO", Toast.LENGTH_SHORT).show();
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

    private void login(String user, String pwd) {
        /*Gson gson = new GsonBuilder()
                .setLenient()  // Permitir JSON mal formado, si es necesario
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://7145-2-142-11-49.ngrok-free.app/auth/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        ISecurityPadinfo_API securityPadinfoApi = retrofit.create(ISecurityPadinfo_API.class);*/

        ISecurityPadinfo_API securityPadinfoApi = RetrofitSecurityClient.getSecurityPadinfoAPI();

        Call<String> call = securityPadinfoApi.loginUser( new Login("dani", "1234") );
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(!response.isSuccessful()) {
                    Toast.makeText(ActivityInicioSesion.this, "1-Código error: " + response.code(), Toast.LENGTH_SHORT).show();
                    txtUsuario.setText("");
                    txtPassword.setText("");
                    return;
                }

                String token = response.body();

                Intent intentAppInicio = new Intent(ActivityInicioSesion.this, Activity_Inicio.class);
                intentAppInicio.putExtra("token", token);
                //setResult(RESULT_OK, intentAppInicio);
                startActivity(intentAppInicio);
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                txtUsuario.setText("");
                txtPassword.setText("");
                Toast.makeText(ActivityInicioSesion.this, "2- Código error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }
        });
    }


}