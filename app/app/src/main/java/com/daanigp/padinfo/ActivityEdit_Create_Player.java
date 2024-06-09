package com.daanigp.padinfo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.daanigp.padinfo.Entity.CreateUpdatePlayer;
import com.daanigp.padinfo.Entity.Player;
import com.daanigp.padinfo.Interface_API.IPadinfo_API;
import com.daanigp.padinfo.Retrofit.RetrofitClient;
import com.daanigp.padinfo.SharedPreferences.SharedPreferencesManager;
import com.daanigp.padinfo.Toast.Toast_Personalized;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityEdit_Create_Player extends AppCompatActivity {

    private static final String TAG = "ActivityEdit_Create_Player";
    ImageView imgPlayer;
    EditText txtName, txtRankingPos, txtPoints;
    Spinner spinner;
    Button btnCancel, btnSave;
    long idPlayer;
    boolean edit;
    String image, token;
    View message_layout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_create_player);

        imgPlayer = (ImageView) findViewById(R.id.imagePlayer);
        txtName = (EditText) findViewById(R.id.editTxtPlayerName);
        txtPoints = (EditText) findViewById(R.id.editTxtPlayerPoints);
        txtRankingPos = (EditText) findViewById(R.id.editTxtRankingPosition);
        spinner = (Spinner) findViewById(R.id.spinnerGenderP);
        btnCancel = (Button) findViewById(R.id.btnCancelP);
        btnSave = (Button) findViewById(R.id.btnSaveP);
        message_layout = getLayoutInflater().inflate(R.layout.toast_customized, null);
        token = SharedPreferencesManager.getInstance(ActivityEdit_Create_Player.this).getToken();
        imgPlayer.setImageResource(R.drawable.player_img);

        idPlayer = getIntent().getLongExtra("idPlayer", 0);
        if (idPlayer != 0) {
            autocompletePlayerInfo();
            edit = true;
        }

        setDayNight();

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast("No has guardado nada.");
                setResult(RESULT_CANCELED);
                finish();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name, points, rankingPos, gender;
                name = txtName.getText().toString();
                points = txtPoints.getText().toString() + " puntos";
                rankingPos = txtRankingPos.getText().toString();
                gender = selectGender();
                image = "player_img";

                if (isEmptyOrNull(name) || isEmptyOrNull(points) || isEmptyOrNull(rankingPos)) {
                    showToast("Debes rellenar todos los datos");
                } else {
                    CreateUpdatePlayer player = new CreateUpdatePlayer();
                    player.setName(name);
                    player.setGender(gender);
                    player.setPoints(points);
                    player.setRankingPosition(Integer.parseInt(rankingPos));
                    player.setImageURL(image);
                    if (edit) {
                        updatePlayer(player);
                    } else {
                        saveNewPlayer(player);
                    }

                    setResult(RESULT_OK);
                    finish();
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

    private boolean isEmptyOrNull(String str){
        return str == null || str.isEmpty();
    }

    private String selectGender() {
        int posGender = spinner.getSelectedItemPosition();

        if (posGender == 0) {
            return "masc";
        } else {
            return "fem";
        }
    }

    private int selectGenderSpinner(String gender) {
        if (gender.equalsIgnoreCase("fem")) {
            return 1;
        } else {
            return 0;
        }
    }

    private void updatePlayer(CreateUpdatePlayer player) {
        IPadinfo_API padinfoApi = RetrofitClient.getPadinfoAPI();
        Call<Player> call = padinfoApi.updatePlayer(token, idPlayer, player);

        call.enqueue(new Callback<Player>() {
            @Override
            public void onResponse(Call<Player> call, Response<Player> response) {
                if(!response.isSuccessful()) {
                    Log.v(TAG, "No va (updatePlayer) -> response");
                    showToast("Código error: " + response.code());
                    return;
                }

                Player playerAPI = response.body();

                if (playerAPI != null) {
                    showToast("Has guardado el jugador/a.");
                } else {
                    showToast("Error en la respuesta del servidor");
                }

            }

            @Override
            public void onFailure(Call<Player> call, Throwable t) {
                Log.e(TAG, "Error en la llamada Retrofit - (updatePlayer)", t);
                showToast("Código error: " + t.getMessage());
            }
        });
    }

    private void saveNewPlayer(CreateUpdatePlayer player) {
        IPadinfo_API padinfoApi = RetrofitClient.getPadinfoAPI();
        Call<Player> call = padinfoApi.createPlayer(token, player);

        call.enqueue(new Callback<Player>() {
            @Override
            public void onResponse(Call<Player> call, Response<Player> response) {
                if(!response.isSuccessful()) {
                    Log.v(TAG, "No va (saveNewPlayer) -> response");
                    showToast("Código error: " + response.code());
                    return;
                }

                Player playerAPI = response.body();

                if (playerAPI != null) {
                    showToast("Has creado el jugador/a.");
                } else {
                    showToast("Error en la respuesta del servidor");
                }
            }

            @Override
            public void onFailure(Call<Player> call, Throwable t) {
                Log.e(TAG, "Error en la llamada Retrofit - (saveNewPlayer)", t);
                showToast("Código error: " + t.getMessage());
            }
        });
    }

    private void autocompletePlayerInfo() {
        IPadinfo_API padinfoApi = RetrofitClient.getPadinfoAPI();
        Call<Player> call = padinfoApi.findPlayerById(token, idPlayer);

        call.enqueue(new Callback<Player>() {
            @Override
            public void onResponse(Call<Player> call, Response<Player> response) {
                if(!response.isSuccessful()) {
                    Log.v(TAG, "No va (autocompletePlayerInfo) -> response");
                    showToast("Código error: " + response.code());
                    return;
                }

                Player playerAPI = response.body();

                if (playerAPI != null) {
                    txtName.setText(playerAPI.getName());

                    String points = playerAPI.getPoints().replaceAll("[^\\d.]", "");
                    txtPoints.setText(points);

                    txtRankingPos.setText(String.valueOf(playerAPI.getRankingPosition()));
                    spinner.setSelection(selectGenderSpinner(playerAPI.getGender()));

                    int imageResourceId = ActivityEdit_Create_Player.this.getResources().getIdentifier(playerAPI.getImageURL(), "drawable", ActivityEdit_Create_Player.this.getPackageName());
                    imgPlayer.setImageResource(imageResourceId);
                    image = playerAPI.getImageURL();
                } else {
                    showToast("Error en la respuesta del servidor");
                    txtName.setText("vacío");
                    txtPoints.setText("0");
                    txtRankingPos.setText("0");
                    spinner.setSelection(0);
                    imgPlayer.setImageResource(R.drawable.icono_img);
                }

            }

            @Override
            public void onFailure(Call<Player> call, Throwable t) {
                Log.e(TAG, "Error en la llamada Retrofit - (autocompletePlayerInfo)", t);
                showToast("Código error: " + t.getMessage());
                txtName.setText("vacío");
                txtPoints.setText("0");
                txtRankingPos.setText("0");
                spinner.setSelection(0);
                imgPlayer.setImageResource(R.drawable.icono_img);
            }
        });
    }

    private void showToast(String message) {
        Toast_Personalized toast = new Toast_Personalized(message, ActivityEdit_Create_Player.this, message_layout);
        toast.CreateToast();
    }

}