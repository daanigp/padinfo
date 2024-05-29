package com.daanigp.padinfo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.daanigp.padinfo.Entity.Respone.ResponseEntity;
import com.daanigp.padinfo.Entity.Security.CreateUser;
import com.daanigp.padinfo.Entity.UserEntity;
import com.daanigp.padinfo.Interface_API.IPadinfo_API;
import com.daanigp.padinfo.Interface_API.ISecurityPadinfo_API;
import com.daanigp.padinfo.Retrofit.RetrofitClient;
import com.daanigp.padinfo.Retrofit.RetrofitSecurityClient;
import com.daanigp.padinfo.Entity.Security.LoginUser;
import com.daanigp.padinfo.SharedPreferences.SharedPreferencesManager;
import com.daanigp.padinfo.Toast.Toast_Personalized;

import java.util.Collections;
import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityInicioSesion extends AppCompatActivity {

    private static final String TAG = "ActivityInicioSesion";
    Button btnInicioSesion, btnRegistrarse, btnInicioInvitado;
    EditText txtUsuario, txtPassword;
    ImageView imgApp;
    boolean isConnected;
    View message_layout;
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
        message_layout = getLayoutInflater().inflate(R.layout.toast_customized, null);

        imgApp.setImageResource(R.drawable.padinfo_logo);

        btnInicioSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user, pwd;
                user = txtUsuario.getText().toString();
                pwd = txtPassword.getText().toString();
                getIdUser(user);
                if (isConnected) {
                    showToast("El usuario ya está logueado");
                } else {
                    login(user, pwd);
                }
            }
        });

        btnRegistrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtUsuario.setText("");
                txtPassword.setText("");
                showToast("Registro");
                Intent intentRegistro = new Intent(ActivityInicioSesion.this, ActivityRegistroUsuarios.class);
                startActivity(intentRegistro);
            }
        });

        btnInicioInvitado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast("INICIO DE SESIÓN COMO INVITADO");
                txtUsuario.setText("");
                txtPassword.setText("");
                CreateUser createUser = new CreateUser();
                int num = randomNum(10000);
                createUser.setUsername("guest" + num);
                createUser.setPassword("1234");
                createUser.setEmail("guest" + num + "@gmail.com");
                createUser.setName("Guest");
                createUser.setLastname("" + num);
                createUser.setRolIds(Collections.singletonList(3L));
                signUpGuest(createUser);
            }
        });
    }

    private int randomNum(int num1) {
        Random rnd = new Random();
        return rnd.nextInt(num1) + 1;
    }

    private void login(String user, String pwd) {
        ISecurityPadinfo_API securityPadinfoApi = RetrofitSecurityClient.getSecurityPadinfoAPI();
        Call<String> call = securityPadinfoApi.loginUser( new LoginUser(user, pwd) );

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(!response.isSuccessful()) {
                    showToast("1-Código error (login): " + response.code());
                    txtUsuario.setText("");
                    txtPassword.setText("");
                    return;
                }

                String token = response.body();

                if (token != null) {
                    // Save the token and username in SharedPreferences
                    token = "Bearer " + token;
                    SharedPreferencesManager.getInstance(ActivityInicioSesion.this).saveToken(token);
                    SharedPreferencesManager.getInstance(ActivityInicioSesion.this).saveUsername(user);

                    Intent intentAppInicio = new Intent(ActivityInicioSesion.this, Activity_Inicio.class);
                    intentAppInicio.putExtra("token", token);
                    startActivity(intentAppInicio);
                } else {
                    showToast("Error en la respuesta del servidor");
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                txtUsuario.setText("");
                txtPassword.setText("");
                showToast("2- Código error (login): " + t.getMessage());
                Log.e(TAG, "Error en la llamada Retrofit - (login)", t);
            }
        });
    }

    private void getIdUser(String username) {
        IPadinfo_API padinfoApi = RetrofitClient.getPadinfoAPI();
        Call<UserEntity> call = padinfoApi.getUserByUsername(username);

        call.enqueue(new Callback<UserEntity>() {
            @Override
            public void onResponse(Call<UserEntity> call, Response<UserEntity> response) {
                if(!response.isSuccessful()) {
                    Log.v(TAG, "No va (getIdUser) -> response");
                    showToast("Código error: " + response.code());
                    return;
                }

                UserEntity user = response.body();

                if (user != null) {
                    long id = user.getId();

                    // Save the userID and in SharedPreferences
                    SharedPreferencesManager.getInstance(ActivityInicioSesion.this).saveUserID(id);
                    Log.v(TAG, "INICIO SESION - id -> " + id);
                    checkUserConnectivity(id);
                } else {
                    showToast("Error en la respuesta del servidor");
                }
            }

            @Override
            public void onFailure(Call<UserEntity> call, Throwable t) {
                Log.e(TAG, "Error en la llamada Retrofit - (getIdUser)", t);
                showToast("Código error: " + t.getMessage());
            }
        });
    }

    private void signUpGuest(CreateUser createUser) {
        ISecurityPadinfo_API securityPadinfoApi = RetrofitSecurityClient.getSecurityPadinfoAPI();
        Call<ResponseEntity> call = securityPadinfoApi.registerUser(createUser);

        call.enqueue(new Callback<ResponseEntity>() {
            @Override
            public void onResponse(Call<ResponseEntity> call, Response<ResponseEntity> response) {
                if (!response.isSuccessful()) {
                    Log.v(TAG, "No va (signupGUEST) -> response");
                    showToast("Código error: " + response.code());
                    return;
                }

                ResponseEntity res = response.body();

                if (res != null && res.getMessege().equalsIgnoreCase("Usuario registrado correctamente")) {
                    getIdUser(createUser.getUsername());
                    login(createUser.getUsername(), createUser.getPassword());
                } else {
                    showToast("Error en la respuesta del servidor");
                }

            }

            @Override
            public void onFailure(Call<ResponseEntity> call, Throwable t) {
                Log.e(TAG, "Error en la llamada Retrofit - (signupGUEST)", t);
                showToast("Código error: " + t.getMessage());
            }
        });
    }

    private void checkUserConnectivity(long id) {
        IPadinfo_API padinfoApi = RetrofitClient.getPadinfoAPI();
        Call<Integer> call = padinfoApi.getUserConnectivityByUserId(id);

        call.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if(!response.isSuccessful()) {
                    Log.v(TAG, "No va (checkUserConnectivity) -> response");
                    showToast("Código error: " + response.code());
                    return;
                }

                Integer isConnectedAPI = response.body();

                if (isConnectedAPI == null) {
                    showToast("Error en la respuesta del servidor");
                } else if (isConnectedAPI == 0) {
                    isConnected = false;
                } else {
                    isConnected = true;
                }

            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                Log.e(TAG, "Error en la llamada Retrofit - (checkUserConnectivity)", t);
                showToast("Código error: " + t.getMessage());
            }
        });
    }

    private void putUserIsConnected(long id) {
        IPadinfo_API padinfoApi = RetrofitClient.getPadinfoAPI();
        Call<ResponseEntity> call = padinfoApi.updateIsConnected(id);
        call.enqueue(new Callback<ResponseEntity>() {
            @Override
            public void onResponse(Call<ResponseEntity> call, Response<ResponseEntity> response) {
                if(!response.isSuccessful()) {
                    showToast("1-Código error - (putUserIsConnected): " + response.code());
                    return;
                }

                ResponseEntity res = response.body();

                if (res == null || !res.getMessege().equalsIgnoreCase("IsConnected actualizado correctamente")) {
                    showToast("Error en la respuesta del servidor");
                }

            }

            @Override
            public void onFailure(Call<ResponseEntity> call, Throwable t) {
                Log.e(TAG, "Error en la llamada Retrofit - (putUserIsConnected)", t);
                showToast("2- Código error - (putUserIsConnected): " + t.getMessage());
                t.printStackTrace();
            }
        });
    }

    private void showToast(String message) {
        Toast_Personalized toast = new Toast_Personalized(message, ActivityInicioSesion.this, message_layout);
        toast.CreateToast();
    }
}