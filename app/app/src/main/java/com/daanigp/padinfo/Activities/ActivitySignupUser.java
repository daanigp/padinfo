package com.daanigp.padinfo.Activities;

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
import com.daanigp.padinfo.Interfaces.InterfaceCallbacks.UserExistanceCallback;
import com.daanigp.padinfo.Interfaces.Interface_API.IPadinfo_API;
import com.daanigp.padinfo.Interfaces.Interface_API.ISecurityPadinfo_API;
import com.daanigp.padinfo.R;
import com.daanigp.padinfo.Retrofit.RetrofitClient;
import com.daanigp.padinfo.Retrofit.RetrofitSecurityClient;
import com.daanigp.padinfo.SharedPreferences.SharedPreferencesManager;
import com.daanigp.padinfo.Toast.Toast_Personalized;

import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;

public class ActivitySignupUser extends AppCompatActivity {
    private static final String EMAIL_REGEX = "^[a-zA-Z0-9_\\-\\.~]{2,}@[a-zA-Z0-9_\\-\\.~]{2,}\\.[a-z]{2,4}$";
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);
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
        btnCancelar = (Button) findViewById(R.id.btnCancelRegistro);
        btnRegistrar = (Button) findViewById(R.id.btnRegistroNuevoUsuario);
        txtUsuario = (EditText) findViewById(R.id.editTxtUser_signup);
        txtPassword = (EditText) findViewById(R.id.editTxtPassword_signup);
        txtName = (EditText) findViewById(R.id.editTxtName_signup);
        txtLastName = (EditText) findViewById(R.id.editTxtLastName_signup);
        txtEmail = (EditText) findViewById(R.id.editTxtEmail_signup);

        imgApp.setImageResource(R.drawable.img_profile_frog);
        message_layout = getLayoutInflater().inflate(R.layout.toast_customized, null);

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
                    showToast("Recuerda: para que el email sea válido debe ser algo similar a:\n" +
                            "nombre@nombreDominio.es o nombre123@nombreDominio.com");
                }
            }
        });

    }

    private boolean validationEmail(String email) {
        if (email == null) {
            return false;
        }

        Matcher matcher = EMAIL_PATTERN.matcher(email);
        return matcher.matches();
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