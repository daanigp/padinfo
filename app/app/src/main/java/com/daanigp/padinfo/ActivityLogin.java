package com.daanigp.padinfo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.daanigp.padinfo.Entity.Respone.ResponseEntity;
import com.daanigp.padinfo.Entity.Security.CreateUser;
import com.daanigp.padinfo.Entity.UserEntity;
import com.daanigp.padinfo.InterfaceCallbacks.ConnectivityCallback;
import com.daanigp.padinfo.Interface_API.IPadinfo_API;
import com.daanigp.padinfo.Interface_API.ISecurityPadinfo_API;
import com.daanigp.padinfo.Retrofit.RetrofitClient;
import com.daanigp.padinfo.Retrofit.RetrofitSecurityClient;
import com.daanigp.padinfo.Entity.Security.LoginUser;
import com.daanigp.padinfo.SharedPreferences.SharedPreferencesManager;
import com.daanigp.padinfo.Toast.Toast_Personalized;

import java.util.Collections;
import java.util.Locale;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityLogin extends AppCompatActivity {

    private static final String TAG = "ActivityLogin";
    Button btnInicioSesion, btnRegistrarse, btnInicioInvitado;
    EditText txtUsuario, txtPassword;
    ImageView imgApp;
    Switch switchTema;
    View message_layout;
    private boolean isFirstTime = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnInicioSesion = (Button) findViewById(R.id.btnIniciarSesion);
        btnRegistrarse = (Button) findViewById(R.id.btnRegistrarse);
        btnInicioInvitado = (Button) findViewById(R.id.btnInicioInvitado);
        txtUsuario = (EditText) findViewById(R.id.editTxtUsuario);
        txtPassword = (EditText) findViewById(R.id.editTxtContrasenya);
        imgApp = (ImageView) findViewById(R.id.imgApp);
        switchTema = findViewById(R.id.swithTema);
        message_layout = getLayoutInflater().inflate(R.layout.toast_customized, null);

        imgApp.setImageResource(R.drawable.padinfo_logo);

        int theme = SharedPreferencesManager.getInstance(this).getTheme();
        if (theme==1) {
            switchTema.setChecked(false);
        } else {
            switchTema.setChecked(true);
        }
        setDayNight();

        switchTema.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (switchTema.isChecked()) {
                    SharedPreferencesManager.getInstance(ActivityLogin.this).saveTheme(0);
                } else {
                    SharedPreferencesManager.getInstance(ActivityLogin.this).saveTheme(1);
                }
                setDayNight();
            }
        });

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
                /*CreateUser createUser = new CreateUser();
                int num = randomNum(10000);
                createUser.setUsername("guest" + num);
                createUser.setPassword("1234");
                createUser.setEmail("guest" + num + "@gmail.com");
                createUser.setName("Guest");
                createUser.setLastname("" + num);
                createUser.setRolIds(Collections.singletonList(3L));
                signUpGuest(createUser);*/
                Intent intentAppInicio = new Intent(ActivityLogin.this, MainActivity.class);
                startActivity(intentAppInicio);
            }
        });
    }

    private int randomNum(int num1) {
        Random rnd = new Random();
        return rnd.nextInt(num1) + 1;
    }

    public void setDayNight() {
        int theme = SharedPreferencesManager.getInstance(this).getTheme();
        if (theme == 0) {
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }

    private void login(String user, String pwd) {
        ISecurityPadinfo_API securityPadinfoApi = RetrofitSecurityClient.getSecurityPadinfoAPI();
        Call<String> call = securityPadinfoApi.loginUser( new LoginUser(user, pwd) );

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(!response.isSuccessful()) {
                    if (response.code() == 401) {
                        showToast("Usuario o contraseña incorrectos.");
                    } else if (response.code() == 404) {
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
                    SharedPreferencesManager.getInstance(ActivityLogin.this).saveUsername(user);

                    Intent intentAppInicio = new Intent(ActivityLogin.this, Activity_Initiate.class);
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
                    callback.onConnectivityChecked(false);
                } else {
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

    private void showToast(String message) {
        Toast_Personalized toast = new Toast_Personalized(message, ActivityLogin.this, message_layout);
        toast.CreateToast();
    }
}