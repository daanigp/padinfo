package com.daanigp.padinfo;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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
import android.widget.TextView;
import android.widget.Toast;

import com.daanigp.padinfo.Entity.UserEntity;
import com.daanigp.padinfo.Interface_API.IPadinfo_API;
import com.daanigp.padinfo.Retrofit.RetrofitClient;
import com.daanigp.padinfo.SharedPreferences.SharedPreferencesManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityPerfilUsuario extends AppCompatActivity {

    private static int EDIT_USER = 2;
    private static final String TAG = "ActivityPerfilUsuario";
    TextView txtNombre, txtApellidos, txtEmail;
    Button btnVolver, btnEditar;
    ImageView imgPerfil;
    String token;
    long userId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_usuario);

        txtNombre = (TextView) findViewById(R.id.txtNombreUsuario);
        txtApellidos = (TextView) findViewById(R.id.txtApellidos);
        txtEmail = (TextView) findViewById(R.id.txtEmail);
        btnVolver = (Button) findViewById(R.id.btnVolverM);
        btnEditar = (Button) findViewById(R.id.btnEditar);
        imgPerfil = (ImageView) findViewById(R.id.imgPerfil);

        imgPerfil.setImageResource(R.drawable.icono_img);

        userId = SharedPreferencesManager.getInstance(ActivityPerfilUsuario.this).getUserId();
        token = SharedPreferencesManager.getInstance(ActivityPerfilUsuario.this).getToken();

        autocompleteUserInfo();

        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentEditarUsuario = new Intent(ActivityPerfilUsuario.this, ActivityEditarUsuario.class);
                startActivityForResult(intentEditarUsuario, EDIT_USER);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == EDIT_USER) {
            if (resultCode == RESULT_OK) {
                autocompleteUserInfo();
            }
        }
    }

    private void autocompleteUserInfo() {
        IPadinfo_API padinfoApi = RetrofitClient.getPadinfoAPI();
        Call<UserEntity> call = padinfoApi.getUserInfoByUserID(token, userId);

        call.enqueue(new Callback<UserEntity>() {
            @Override
            public void onResponse(Call<UserEntity> call, Response<UserEntity> response) {
                if(!response.isSuccessful()) {
                    Log.v(TAG, "No va (autocompleteUserInfo) -> response" + response);
                    Toast.makeText(ActivityPerfilUsuario.this, "Código error: " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }

                UserEntity user = response.body();

                if (user != null) {
                    txtNombre.setText(user.getName());
                    txtApellidos.setText(user.getLastname());
                    txtEmail.setText(user.getEmail());
                } else {
                    Toast.makeText(ActivityPerfilUsuario.this, "Error en la respuesta del servidor", Toast.LENGTH_SHORT).show();
                    txtNombre.setText("vacío");
                    txtApellidos.setText("vacío");
                    txtEmail.setText("vacío");
                }

            }

            @Override
            public void onFailure(Call<UserEntity> call, Throwable t) {
                Log.e(TAG, "Error en la llamada Retrofit - (autocompleteUserInfo)", t);
                Toast.makeText(ActivityPerfilUsuario.this, "Código error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}