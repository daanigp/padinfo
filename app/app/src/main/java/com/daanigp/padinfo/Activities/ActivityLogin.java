package com.daanigp.padinfo.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;

import com.daanigp.padinfo.Entity.Respone.ResponseEntity;
import com.daanigp.padinfo.Entity.Security.CreateUser;
import com.daanigp.padinfo.Entity.UserEntity;
import com.daanigp.padinfo.Interfaces.InterfaceCallbacks.ConnectivityCallback;
import com.daanigp.padinfo.Interfaces.Interface_API.IPadinfo_API;
import com.daanigp.padinfo.Interfaces.Interface_API.ISecurityPadinfo_API;
import com.daanigp.padinfo.R;
import com.daanigp.padinfo.Retrofit.RetrofitClient;
import com.daanigp.padinfo.Retrofit.RetrofitSecurityClient;
import com.daanigp.padinfo.Entity.Security.LoginUser;
import com.daanigp.padinfo.SharedPreferences.SharedPreferencesManager;
import com.daanigp.padinfo.Toast.Toast_Personalized;

import java.util.Collections;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityLogin extends AppCompatActivity {

    private static final String TAG = "ActivityLogin";
    Button btnInicioSesion, btnRegistrarse, btnInicioInvitado;
    EditText txtUsuario, txtPassword;
    View message_layout;
    private boolean isFirstTime = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setAppTheme();
        checkUserIsAlreadyConnected();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_2);

        btnInicioSesion = (Button) findViewById(R.id.btnIniciarSesion);
        btnRegistrarse = (Button) findViewById(R.id.btnRegistrarse);
        btnInicioInvitado = (Button) findViewById(R.id.btnInicioInvitado);
        txtUsuario = (EditText) findViewById(R.id.editTxtUsuario_login);
        txtPassword = (EditText) findViewById(R.id.editTxtContrasenya_login);
        message_layout = getLayoutInflater().inflate(R.layout.toast_customized, null);

        btnInicioSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user, pwd;
                user = txtUsuario.getText().toString();
                pwd = txtPassword.getText().toString();
                if (user.isEmpty() || pwd.isEmpty()) {
                    showToast("Debes rellenar todos los campos");
                } else {
                    getIdUser(user, new ConnectivityCallback() {
                        @Override
                        public void onConnectivityChecked(boolean isConnected) {
                            if (isConnected) {
                                showToast("El usuario " + user + " ya está logueado");
                            } else {
                                Log.e(TAG, "USUARIO NO CONECTADO");
                                login(user, pwd);
                            }
                        }
                    });
                }
            }
        });

        btnRegistrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtUsuario.setText("");
                txtPassword.setText("");
                Intent intentRegistro = new Intent(ActivityLogin.this, ActivitySignupUser.class);
                startActivity(intentRegistro);
            }
        });

        btnInicioInvitado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast("Has iniciado sesión como invitado");
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
                //Intent intentAppInicio = new Intent(ActivityLogin.this, ActivityMain.class);
                //startActivity(intentAppInicio);
            }
        });
    }

    /* @Override
    protected void onPause() {
        super.onPause();
        putUserDisconnected();
    }

    @Override
    protected void onStop() {
        super.onStop();
        putUserDisconnected();
    } */

    private int randomNum(int num1) {
        Random rnd = new Random();
        return rnd.nextInt(num1) + 1;
    }

    private void checkUserIsAlreadyConnected() {
        String token = SharedPreferencesManager.getInstance(ActivityLogin.this).getUsername();
        if (token != null) {
            if (!token.equalsIgnoreCase("")) {
                Intent intentAppInicio = new Intent(ActivityLogin.this, Activity_Main.class);
                intentAppInicio.putExtra("token", token);
                startActivity(intentAppInicio);
            }
        }
    }

    public void setAppTheme() {
        boolean isDarkTheme = SharedPreferencesManager.getInstance(this).getTheme();
        AppCompatDelegate.setDefaultNightMode(isDarkTheme ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO);
    }

    private void login(String user, String pwd) {
        ISecurityPadinfo_API securityPadinfoApi = RetrofitSecurityClient.getSecurityPadinfoAPI();
        Call<String> call = securityPadinfoApi.loginUser( new LoginUser(user, pwd) );
        Log.e(TAG, "método login");
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(!response.isSuccessful()) {
                    if (response.code() == 401) {
                        Log.e(TAG, "Error login 401 -> " + response.message());
                        showToast("Usuario o contraseña incorrectos.");
                    } else if (response.code() == 404) {
                        Log.e(TAG, "ERROR 404");
                        showToast("Usuario o contraseña incorrectos.");
                    } else if (response.code() == 502) {
                        showToast("El servidor no funciona");
                    } else {
                        showToast("1-Código error (login): " + response.code());
                    }
                    txtUsuario.setText("");
                    txtPassword.setText("");
                    return;
                }

                String token = response.body();

                if (token != null) {
                    token = "Bearer " + token;
                    SharedPreferencesManager.getInstance(ActivityLogin.this).saveToken(token);


                    Intent intentAppInicio = new Intent(ActivityLogin.this, Activity_Main.class);
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

    /*private void getUserInfo(String username, String password) {
        IPadinfo_API padinfoApi = RetrofitClient.getPadinfoAPI();
        Call<UserEntity> call = padinfoApi.getUserByUsername(username);

        call.enqueue(new Callback<UserEntity>() {
            @Override
            public void onResponse(Call<UserEntity> call, Response<UserEntity> response) {
                if(!response.isSuccessful()) {
                    if (response.code() == 500) {
                        showToast("Usuario o contraseña incorrectos");
                    } else {
                        Log.v(TAG, "No va (getUserInfo) -> response");
                        showToast("Código error: " + response.code());
                    }
                    return;
                }

                UserEntity user = response.body();

                if (user != null) {
                    long id = user.getId();

                    SharedPreferencesManager.getInstance(ActivityLogin.this).saveUserID(id);
                    SharedPreferencesManager.getInstance(ActivityLogin.this).saveEmail(user.getEmail());
                    SharedPreferencesManager.getInstance(ActivityLogin.this).saveImage(user.getImageURL());
                    SharedPreferencesManager.getInstance(ActivityLogin.this).saveUsername(username);
                    //SharedPreferencesManager.getInstance(ActivityLogin.this).saveUserID(3);
                    //SharedPreferencesManager.getInstance(ActivityLogin.this).saveEmail("pablo@gmail.com");
                    //SharedPreferencesManager.getInstance(ActivityLogin.this).saveImage("admin_img");
                    Log.v(TAG, "INICIO SESION - id -> " + id);
                    login(username, password);
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
    }*/

    private void getIdUser(String username, ConnectivityCallback callback) {
        IPadinfo_API padinfoApi = RetrofitClient.getPadinfoAPI();
        Call<UserEntity> call = padinfoApi.getUserByUsername(username);

        call.enqueue(new Callback<UserEntity>() {
            @Override
            public void onResponse(Call<UserEntity> call, Response<UserEntity> response) {
                if(!response.isSuccessful()) {
                    if (response.code() == 500) {
                        showToast("Usuario o contraseña incorrectos");
                    } else {
                        Log.v(TAG, "No va (getIdUser) -> response");
                        showToast("Código error: " + response.code());
                    }
                    return;
                }

                UserEntity user = response.body();

                if (user != null) {
                    long id = user.getId();

                    SharedPreferencesManager.getInstance(ActivityLogin.this).saveUserID(id);
                    SharedPreferencesManager.getInstance(ActivityLogin.this).saveEmail(user.getEmail());
                    SharedPreferencesManager.getInstance(ActivityLogin.this).saveImage(user.getImageURL());
                    SharedPreferencesManager.getInstance(ActivityLogin.this).saveUsername(username);

                    Log.v(TAG, "INICIO SESION - id -> " + id);
                    checkUserConnectivity(id, callback);
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

    private void checkUserConnectivity(long id, ConnectivityCallback callback) {
        IPadinfo_API padinfoApi = RetrofitClient.getPadinfoAPI();
        Call<Integer> call = padinfoApi.getUserConnectivityByUserId(id);

        call.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if(!response.isSuccessful()) {
                    Log.v(TAG, "No va (checkUserConnectivity) -> response");
                    showToast("Código error - (checkUserConnectivity): " + response.code());
                    return;
                }

                Integer isConnectedAPI = response.body();

                if (isConnectedAPI == null) {
                    showToast("Error en la respuesta del servidor");
                } else if (isConnectedAPI == 0) {
                    Log.v(TAG, "CONECTADO : FALSE");
                    callback.onConnectivityChecked(false);
                } else {
                    Log.v(TAG, "CONECTADO : TRUE");
                    callback.onConnectivityChecked(true);
                }

            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                Log.e(TAG, "Error en la llamada Retrofit - (checkUserConnectivity)", t);
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
                    getIdUser(createUser.getUsername(), new ConnectivityCallback() {
                        @Override
                        public void onConnectivityChecked(boolean isConnected) {
                            if (!isConnected) {
                                SharedPreferencesManager.getInstance(ActivityLogin.this).saveEmail(createUser.getEmail());
                                SharedPreferencesManager.getInstance(ActivityLogin.this).saveUsername(createUser.getUsername());
                                login(createUser.getUsername(), createUser.getPassword());
                            } else {
                                showToast("Vuelve a intentarlo, el usuario " + createUser.getUsername() + " ya está logueado.");
                            }
                        }
                    });
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

    private void putUserDisconnected() {
        long id = SharedPreferencesManager.getInstance(ActivityLogin.this).getUserId();
        IPadinfo_API padinfoApi = RetrofitClient.getPadinfoAPI();
        Call<ResponseEntity> call = padinfoApi.updateIsConnected(id);
        call.enqueue(new Callback<ResponseEntity>() {
            @Override
            public void onResponse(Call<ResponseEntity> call, Response<ResponseEntity> response) {
                if (!response.isSuccessful()) {
                    showToast("1-Código error - (putUserDisconnected): " + response.code());
                    return;
                }

                ResponseEntity res = response.body();

                if (res == null || !res.getMessege().equalsIgnoreCase("IsConnected actualizado correctamente")) {
                    showToast("Error en la respuesta del servidor");
                } else {
                    SharedPreferencesManager.getInstance(ActivityLogin.this).clear();
                }
            }

            @Override
            public void onFailure(Call<ResponseEntity> call, Throwable t) {
                Log.e(TAG, "Error en la llamada Retrofit - (putUserDisconnected)", t);
                showToast("2- Código error - (putUserDisconnected): " + t.getMessage());
                t.printStackTrace();
            }
        });
    }

    private void showToast(String message) {
        Toast_Personalized toast = new Toast_Personalized(message, ActivityLogin.this, message_layout);
        toast.CreateToast();
    }
}