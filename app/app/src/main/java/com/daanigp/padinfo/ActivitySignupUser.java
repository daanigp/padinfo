package com.daanigp.padinfo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.daanigp.padinfo.Entity.Respone.ResponseEntity;
import com.daanigp.padinfo.Entity.Security.CreateUser;
import com.daanigp.padinfo.InterfaceCallbacks.UserExistanceCallback;
import com.daanigp.padinfo.Interface_API.IPadinfo_API;
import com.daanigp.padinfo.Interface_API.ISecurityPadinfo_API;
import com.daanigp.padinfo.Retrofit.RetrofitClient;
import com.daanigp.padinfo.Retrofit.RetrofitSecurityClient;
import com.daanigp.padinfo.SharedPreferences.SharedPreferencesManager;
import com.daanigp.padinfo.Toast.Toast_Personalized;

import java.util.Collections;

import retrofit2.Call;
import retrofit2.Callback;

public class ActivitySignupUser extends AppCompatActivity {
    private static final String TAG = "ActivitySignupUser";
    ImageView imgApp;
    Button btnCancelar, btnRegistrar;
    EditText txtUsuario, txtPassword, txtName, txtLastName, txtEmail;
    boolean exists;
    View message_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_user);

        imgApp = (ImageView) findViewById(R.id.imgApp2);
        btnCancelar = (Button) findViewById(R.id.btnCancel);
        btnRegistrar = (Button) findViewById(R.id.btnNewRegistro);
        txtUsuario = (EditText) findViewById(R.id.editTxtNuevoUsuario);
        txtPassword = (EditText) findViewById(R.id.editTxtNuevaContrasenya);
        txtName = (EditText) findViewById(R.id.editTxtNombreRegistro);
        txtLastName = (EditText) findViewById(R.id.editTxtApellidosRegistro);
        txtEmail = (EditText) findViewById(R.id.editTxtEmailRegistro);

        imgApp.setImageResource(R.drawable.padinfo_logo);
        message_layout = getLayoutInflater().inflate(R.layout.toast_customized, null);

        setDayNight();
        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast("No has registrado ningún usuario");
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
                if (validationEmail(email)) {
                    userExists(user, new UserExistanceCallback() {
                        @Override
                        public void onExistanceChecked(boolean userExists) {
                            if (userExists) {
                                showToast("El usuario " + user + " ya existe :)");
                                txtUsuario.setText("");
                            }  else {
                                CreateUser createUser = new CreateUser(user, pwd, name, lastName, email, Collections.singletonList(2L));
                                signup(createUser);
                            }
                        }
                    });
                } else {
                    txtEmail.setText("");
                    showToast("Recuerda: los emails válidos son:\n" +
                            "'@gmail.com', '@gmail.es', @hotmail.com', @hotmail.es'.");
                }
            }
        });

    }

    public void setDayNight() {
        int theme = SharedPreferencesManager.getInstance(this).getTheme();
        if (theme == 0) {
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }

    private boolean validationEmail(String email) {
        if (email.endsWith("@gmail.com") || email.endsWith("@gmail.es") || email.endsWith("@hotmail.com") || email.endsWith("@hotmail.es")) {
            return true;
        } else {
            return false;
        }
    }

    private void signup(CreateUser createUser) {
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

                    finish();
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
    }

    private void userExists(String username, UserExistanceCallback callback) {

        IPadinfo_API padinfoApi = RetrofitClient.getPadinfoAPI();

        Call<ResponseEntity> call = padinfoApi.checkUserExistsByUsername(username);

        call.enqueue(new Callback<ResponseEntity>() {
            @Override
            public void onResponse(Call<ResponseEntity> call, retrofit2.Response<ResponseEntity> response) {
                if (!response.isSuccessful()) {
                    if (response.code() == 502) {
                        showToast("El servidor no funciona");
                    } else {
                        Log.v(TAG, "No va (userExists) -> response");
                        showToast("Código error: " + response.code());
                    }
                    return;
                }

                ResponseEntity res = response.body();

                if (res.getMessege().equalsIgnoreCase("No existe")) {
                    callback.onExistanceChecked(false);
                } else {
                    callback.onExistanceChecked(true);
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
        Toast_Personalized toast = new Toast_Personalized(message, ActivitySignupUser.this, message_layout);
        toast.CreateToast();
    }
}