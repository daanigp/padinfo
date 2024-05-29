package com.daanigp.padinfo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.daanigp.padinfo.Entity.Respone.ResponseEntity;
import com.daanigp.padinfo.Entity.Security.CreateUser;
import com.daanigp.padinfo.Interface_API.IPadinfo_API;
import com.daanigp.padinfo.Interface_API.ISecurityPadinfo_API;
import com.daanigp.padinfo.Retrofit.RetrofitClient;
import com.daanigp.padinfo.Retrofit.RetrofitSecurityClient;
import com.daanigp.padinfo.Toast.Toast_Personalized;

import java.util.Collections;

import retrofit2.Call;
import retrofit2.Callback;

public class ActivityRegistroUsuarios extends AppCompatActivity {
    private static final String TAG = "ActivityRegistroUsuarios";
    ImageView imgApp;
    Button btnCancelar, btnRegistrar;
    EditText txtUsuario, txtPassword, txtName, txtLastName, txtEmail;
    boolean exists;
    View message_layout;

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
                showToast("Cancelar");
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
                CreateUser createUser = new CreateUser(user, pwd, name, lastName, email, Collections.singletonList(2L));
                signup(user, createUser);
            }
        });

    }

    private void signup(String user, CreateUser createUser) {
        userExists(user);
        if (!exists) {
            ISecurityPadinfo_API securityPadinfoApi = RetrofitSecurityClient.getSecurityPadinfoAPI();

            Call<ResponseEntity> call = securityPadinfoApi.registerUser(createUser);

            call.enqueue(new Callback<ResponseEntity>() {
                @Override
                public void onResponse(Call<ResponseEntity> call, retrofit2.Response<ResponseEntity> response) {
                    if (!response.isSuccessful()) {
                        Log.v(TAG, "No va (signup) -> response");
                        showToast("Código error: " + response.code());
                        return;
                    }

                    ResponseEntity res = response.body();

                    if (res != null && res.getMessege().equalsIgnoreCase("Usuario registrado correctamente")) {
                        showToast("Usuario registrado correctamente");
                    } else {
                        showToast("Error en la respuesta del servidor");
                    }
                }

                @Override
                public void onFailure(Call<ResponseEntity> call, Throwable t) {
                    Log.e(TAG, "Error en la llamada Retrofit - (signup)", t);
                    showToast("Código error: " + t.getMessage());
                }
            });

            finish();
        } else {
            txtUsuario.setText("");
            txtPassword.setText("");
            txtName.setText("");
            txtLastName.setText("");
            txtEmail.setText("");
        }
    }

    private void userExists(String username) {

        IPadinfo_API padinfoApi = RetrofitClient.getPadinfoAPI();

        Call<ResponseEntity> call = padinfoApi.checkUserExistsByUsername(username);

        call.enqueue(new Callback<ResponseEntity>() {
            @Override
            public void onResponse(Call<ResponseEntity> call, retrofit2.Response<ResponseEntity> response) {
                if (!response.isSuccessful()) {
                    Log.v(TAG, "No va (userExists) -> response");
                    showToast("Código error: " + response.code());
                    return;
                }

                ResponseEntity res = response.body();

                if (res.getMessege().equalsIgnoreCase("No existe")) {
                    exists = false;
                } else {
                    showToast("El usuario '" + username + "' ya está registrado.");
                }
            }

            @Override
            public void onFailure(Call<ResponseEntity> call, Throwable t) {
                Log.e(TAG, "Error en la llamada Retrofit - (userExists)", t);
                showToast("Código error: " + t.getMessage());
            }
        });

    }

    private void showToast(String message) {
        Toast_Personalized toast = new Toast_Personalized(message, ActivityRegistroUsuarios.this, message_layout);
        toast.CreateToast();
    }
}