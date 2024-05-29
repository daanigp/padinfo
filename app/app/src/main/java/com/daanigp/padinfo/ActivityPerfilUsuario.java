package com.daanigp.padinfo;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.daanigp.padinfo.Entity.UserEntity;
import com.daanigp.padinfo.Interface_API.IPadinfo_API;
import com.daanigp.padinfo.Retrofit.RetrofitClient;
import com.daanigp.padinfo.SharedPreferences.SharedPreferencesManager;
import com.daanigp.padinfo.Toast.Toast_Personalized;

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
    View message_layout;
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

        //imgPerfil.setImageResource(R.drawable.imgperfil_basic);

        userId = SharedPreferencesManager.getInstance(ActivityPerfilUsuario.this).getUserId();
        token = SharedPreferencesManager.getInstance(ActivityPerfilUsuario.this).getToken();
        message_layout = getLayoutInflater().inflate(R.layout.toast_customized, null);

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
                    showToast("Código error: " + response.code());
                    return;
                }

                UserEntity user = response.body();

                if (user != null) {
                    txtNombre.setText(user.getName());
                    txtApellidos.setText(user.getLastname());
                    txtEmail.setText(user.getEmail());

                    int imageResourceId = ActivityPerfilUsuario.this.getResources().getIdentifier(user.getImageURL(), "drawable", ActivityPerfilUsuario.this.getPackageName());
                    imgPerfil.setImageResource(imageResourceId);
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
            }
        });
    }

    private void showToast(String message) {
        Toast_Personalized toast = new Toast_Personalized(message, ActivityPerfilUsuario.this, message_layout);
        toast.CreateToast();
    }
}