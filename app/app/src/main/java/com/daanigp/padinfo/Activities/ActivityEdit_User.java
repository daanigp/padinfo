package com.daanigp.padinfo.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.daanigp.padinfo.Entity.UpdateUserInfo;
import com.daanigp.padinfo.Entity.UserEntity;
import com.daanigp.padinfo.Interfaces.Interface_API.IPadinfo_API;
import com.daanigp.padinfo.R;
import com.daanigp.padinfo.Retrofit.RetrofitClient;
import com.daanigp.padinfo.SharedPreferences.SharedPreferencesManager;
import com.daanigp.padinfo.Toast.Toast_Personalized;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityEdit_User extends AppCompatActivity {

    private static final String EMAIL_REGEX = "^[a-zA-Z0-9_\\-\\.~]{2,}@[a-zA-Z0-9_\\-\\.~]{2,}\\.[a-z]{2,4}$";
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);
    private static final String TAG = "ActivityEdit_User";
    EditText txtNombre, txtApellidos, txtEmail;
    Button btnGuardar, btnCancelar;
    ImageView imgPerfilUsuario;
    String token, image;
    long userId;
    View message_layout;
    Spinner spinnerImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);

        txtNombre = (EditText) findViewById(R.id.editTxtNombre);
        txtApellidos = (EditText) findViewById(R.id.editTxtApellidos);
        txtEmail = (EditText) findViewById(R.id.editTxtEmail);
        btnGuardar = (Button) findViewById(R.id.btnGuardarInfo);
        btnCancelar = (Button) findViewById(R.id.btnVolverM);
        imgPerfilUsuario = (ImageView) findViewById(R.id.imgPerfil);
        spinnerImg = findViewById(R.id.spinnerImage);
        message_layout = getLayoutInflater().inflate(R.layout.toast_customized, null);

        userId = SharedPreferencesManager.getInstance(ActivityEdit_User.this).getUserId();
        token = SharedPreferencesManager.getInstance(ActivityEdit_User.this).getToken();

        autocompleteUserInfo();

        spinnerImg.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String img = parent.getItemAtPosition(position).toString();
                Log.e("TAG", "IMAGEN -> " + img);
                putImage(selectImageNameByString(img));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast("No has hecho ningún cambio.");
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
                        updateUser.setImageURL(selectImageName());
                        saveChanges(updateUser);
                    } else {
                        txtEmail.setText("");
                        showToast("Recuerda: para que el email sea válido debe ser algo similar a:\n" +
                                "nombre@nombreDominio.es o nombre123@nombreDominio.com");
                    }
                }
            }
        });

    }

    private String selectImageNameByString(String image) {
        switch (image) {
            case "Rana":
                return "img_profile_frog";
            case "Gato 1":
                return "img_profile_cat1";
            case "Gato 2":
                return "img_profile_cat2";
            case "Normal 1":
                return "img_basic_1";
            case "Nutria":
                return "img_profile_otter";
            case "Perro":
                return "img_profile_dog";
            default:
                return "img_profile_rat";
        }
    }

    private boolean isEmptyOrNull(String str){
        return str == null || str.isEmpty();
    }

    private boolean validationEmail(String email) {
        if (email == null) {
            return false;
        }

        Matcher matcher = EMAIL_PATTERN.matcher(email);
        return matcher.matches();
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
                    //image = user.getImageURL();
                    putImage(user.getImageURL());
                    spinnerImg.setSelection(getIdImageSpinner(user.getImageURL()));
                } else {
                    showToast("Error en la respuesta del servidor");
                    txtNombre.setText("vacío");
                    txtApellidos.setText("vacío");
                    txtEmail.setText("vacío");
                    spinnerImg.setSelection(0);
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

    private int getIdImageSpinner(String img) {
        switch (img) {
            case "img_profile_frog":
                return 0;
            case "img_profile_cat1":
                return 1;
            case "img_profile_cat2":
                return 2;
            case "img_basic_1":
                return 3;
            case "img_profile_otter":
                return 4;
            case "img_profile_dog":
                return 5;
            default:
                return 6;
        }
    }

    private void putImage(String img) {
        int imageResourceId = getResources().getIdentifier(img, "drawable", getPackageName());
        imgPerfilUsuario.setImageResource(imageResourceId);
    }

    private String selectImageName() {

        int posImg = spinnerImg.getSelectedItemPosition();

        switch (posImg) {
            case 0:
                return "img_profile_frog";
            case 1:
                return "img_profile_cat1";
            case 2:
                return "img_profile_cat2";
            case 3:
                return "img_basic_1";
            case 4:
                return "img_profile_otter";
            case 5:
                return "img_profile_dog";
            default:
                return "img_profile_rat";
        }
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
                    SharedPreferencesManager.getInstance(ActivityEdit_User.this).saveImage(updateUser.getImageURL());
                    SharedPreferencesManager.getInstance(ActivityEdit_User.this).saveEmail(updateUser.getEmail());

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
        Toast_Personalized toast = new Toast_Personalized(message, ActivityEdit_User.this, message_layout);
        toast.CreateToast();
    }
}