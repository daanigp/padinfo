package com.daanigp.padinfo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.daanigp.padinfo.Entity.UpdateUserInfo;
import com.daanigp.padinfo.Entity.UserEntity;
import com.daanigp.padinfo.Interface_API.IPadinfo_API;
import com.daanigp.padinfo.Retrofit.RetrofitClient;
import com.daanigp.padinfo.SharedPreferences.SharedPreferencesManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityEditarUsuario extends AppCompatActivity {

    private static final String TAG = "ActivityEditarUsuario";
    EditText txtNombre, txtApellidos, txtEmail;
    Button btnGuardar, btnCancelar;
    ImageView imgPerfilUsuario;
    String token;
    long userId;

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

        imgPerfilUsuario.setImageResource(R.drawable.icono_img);

        userId = SharedPreferencesManager.getInstance(ActivityEditarUsuario.this).getUserId();
        token = SharedPreferencesManager.getInstance(ActivityEditarUsuario.this).getToken();

        autocompleteUserInfo();

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "No has hehco ningún cambio.", Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(ActivityEditarUsuario.this, "Debes rellenar todos los datos", Toast.LENGTH_SHORT).show();
                } else {
                    UpdateUserInfo updateUser = new UpdateUserInfo();
                    updateUser.setName(nombre);
                    updateUser.setLastname(apellidos);
                    updateUser.setEmail(email);
                    updateUser.setImageURL(String.valueOf(R.drawable.icono_img));
                    saveChanges(updateUser);
                }
            }
        });

    }

    private boolean isEmptyOrNull(String str){
        return str == null || str.isEmpty();
    }
    private void autocompleteUserInfo() {
        IPadinfo_API padinfoApi = RetrofitClient.getPadinfoAPI();
        Call<UserEntity> call = padinfoApi.getUserInfoByUserID(token, userId);

        call.enqueue(new Callback<UserEntity>() {
            @Override
            public void onResponse(Call<UserEntity> call, Response<UserEntity> response) {
                if(!response.isSuccessful()) {
                    Log.v(TAG, "No va (autocompleteUserInfo) -> response");
                    Toast.makeText(ActivityEditarUsuario.this, "Código error: " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }

                UserEntity user = response.body();

                if (user != null) {
                    txtNombre.setText(user.getName());
                    txtApellidos.setText(user.getLastname());
                    txtEmail.setText(user.getEmail());

                    int imageResourceId = ActivityEditarUsuario.this.getResources().getIdentifier(user.getImageURL(), "drawable", ActivityEditarUsuario.this.getPackageName());
                    imgPerfilUsuario.setImageResource(imageResourceId);
                } else {
                    Toast.makeText(ActivityEditarUsuario.this, "Error en la respuesta del servidor", Toast.LENGTH_SHORT).show();
                    txtNombre.setText("vacío");
                    txtApellidos.setText("vacío");
                    txtEmail.setText("vacío");
                }

            }

            @Override
            public void onFailure(Call<UserEntity> call, Throwable t) {
                Log.e(TAG, "Error en la llamada Retrofit - (autocompleteUserInfo)", t);
                Toast.makeText(ActivityEditarUsuario.this, "Código error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
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
                    Log.v(TAG, "No va (saveChanges) -> response");
                    Toast.makeText(ActivityEditarUsuario.this, "Código error: " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }

                UserEntity userAPI = response.body();

                if (userAPI != null) {
                    Toast.makeText(ActivityEditarUsuario.this, "Has guardado los cambios.", Toast.LENGTH_SHORT).show();

                    setResult(RESULT_OK);
                    finish();
                } else {
                    Toast.makeText(ActivityEditarUsuario.this, "Error en la respuesta del servidor", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UserEntity> call, Throwable t) {
                Log.e(TAG, "Error en la llamada Retrofit - (saveChanges)", t);
                Toast.makeText(ActivityEditarUsuario.this, "Código error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}