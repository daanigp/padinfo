package com.daanigp.padinfo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.daanigp.padinfo.Entity.UpdateUserInfo;
import com.daanigp.padinfo.Entity.UserEntity;
import com.daanigp.padinfo.Interface_API.IPadinfo_API;
import com.daanigp.padinfo.Retrofit.RetrofitClient;
import com.daanigp.padinfo.SharedPreferences.SharedPreferencesManager;
import com.daanigp.padinfo.Toast.Toast_Personalized;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityEditarUsuario extends AppCompatActivity {

    private static final String TAG = "ActivityEditarUsuario";
    EditText txtNombre, txtApellidos, txtEmail;
    Button btnGuardar, btnCancelar;
    ImageView imgPerfilUsuario;
    String token, image;
    long userId;
    View message_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_usuario);

        txtNombre = (EditText) findViewById(R.id.editTxtNombre);
        txtApellidos = (EditText) findViewById(R.id.editTxtApellidos);
        txtEmail = (EditText) findViewById(R.id.editTxtEmail);
        btnGuardar = (Button) findViewById(R.id.btnGuardarInfo);
        btnCancelar = (Button) findViewById(R.id.btnVolverM);
        imgPerfilUsuario = (ImageView) findViewById(R.id.imgPerfil);
        message_layout = getLayoutInflater().inflate(R.layout.toast_customized, null);

       // imgPerfilUsuario.setImageResource(R.drawable.icono_img);

        userId = SharedPreferencesManager.getInstance(ActivityEditarUsuario.this).getUserId();
        token = SharedPreferencesManager.getInstance(ActivityEditarUsuario.this).getToken();

        autocompleteUserInfo();

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast("No has hehco ningún cambio.");
                setResult(RESULT_CANCELED);
                finish();
            }
        });

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombre, apellidos, email;
                nombre = txtNombre.getText().toString();
                apellidos = txtApellidos.getText().toString();
                email = txtEmail.getText().toString();
                if (isEmptyOrNull(nombre) || isEmptyOrNull(apellidos) || isEmptyOrNull(email)) {
                    showToast("Debes rellenar todos los datos");
                } else {
                    if (validationEmail(email)) {
                        UpdateUserInfo updateUser = new UpdateUserInfo();
                        updateUser.setName(nombre);
                        updateUser.setLastname(apellidos);
                        updateUser.setEmail(email);
                        updateUser.setImageURL(image);
                        saveChanges(updateUser);
                    } else {
                        txtEmail.setText("");
                        showToast("Recuerda: los emails válidos son:\n" +
                                "'@gmail.com', '@gmail.es', @hotmail.com', @hotmail.es'.");
                    }
                }
            }
        });

    }

    private boolean isEmptyOrNull(String str){
        return str == null || str.isEmpty();
    }

    private boolean validationEmail(String email) {
        if (email.endsWith("@gmail.com") || email.endsWith("@gmail.es") || email.endsWith("@hotmail.com") || email.endsWith("@hotmail.es")) {
            return true;
        } else {
            return false;
        }
    }

    private void autocompleteUserInfo() {
        IPadinfo_API padinfoApi = RetrofitClient.getPadinfoAPI();
        Call<UserEntity> call = padinfoApi.getUserInfoByUserID(token, userId);

        call.enqueue(new Callback<UserEntity>() {
            @Override
            public void onResponse(Call<UserEntity> call, Response<UserEntity> response) {
                if(!response.isSuccessful()) {
                    Log.v(TAG, "No va (autocompleteUserInfo) -> response");
                    showToast("Código error: " + response.code());
                    return;
                }

                UserEntity user = response.body();

                if (user != null) {
                    txtNombre.setText(user.getName());
                    txtApellidos.setText(user.getLastname());
                    txtEmail.setText(user.getEmail());
                    image = user.getImageURL();
                    int imageResourceId = ActivityEditarUsuario.this.getResources().getIdentifier(user.getImageURL(), "drawable", ActivityEditarUsuario.this.getPackageName());
                    imgPerfilUsuario.setImageResource(imageResourceId);
                } else {
                    showToast("Error en la respuesta del servidor");
                    txtNombre.setText("vacío");
                    txtApellidos.setText("vacío");
                    txtEmail.setText("vacío");
                }

            }

            @Override
            public void onFailure(Call<UserEntity> call, Throwable t) {
                Log.e(TAG, "Error en la llamada Retrofit - (autocompleteUserInfo)", t);
                showToast("Código error: " + t.getMessage());
                txtNombre.setText("vacío");
                txtApellidos.setText("vacío");
                txtEmail.setText("vacío");
            }
        });
    }

    private void saveChanges(UpdateUserInfo updateUser) {
        IPadinfo_API padinfoApi = RetrofitClient.getPadinfoAPI();
        Call<UserEntity> call = padinfoApi.updateUserInfo(token, userId, updateUser);

        call.enqueue(new Callback<UserEntity>() {
            @Override
            public void onResponse(Call<UserEntity> call, Response<UserEntity> response) {
                if(!response.isSuccessful()) {
                    Log.v(TAG, "No va (saveChanges) -> response -> " + response.body().toString());
                    showToast("Código error: " + response.code());
                    return;
                }

                UserEntity userAPI = response.body();

                if (userAPI != null) {
                    showToast("Has guardado los cambios.");

                    setResult(RESULT_OK);
                    finish();
                } else {
                    showToast("Error en la respuesta del servidor");
                }
            }

            @Override
            public void onFailure(Call<UserEntity> call, Throwable t) {
                Log.e(TAG, "Error en la llamada Retrofit - (saveChanges)", t);
                showToast("Código error: " + t.getMessage());
            }
        });
    }

    private void showToast(String message) {
        Toast_Personalized toast = new Toast_Personalized(message, ActivityEditarUsuario.this, message_layout);
        toast.CreateToast();
    }
}