package com.daanigp.padinfo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.daanigp.padinfo.Entity.CreateUpdatePlayer;
import com.daanigp.padinfo.Entity.Player;
import com.daanigp.padinfo.Interface_API.IPadinfo_API;
import com.daanigp.padinfo.Retrofit.RetrofitClient;
import com.daanigp.padinfo.SharedPreferences.SharedPreferencesManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityEdit_CreatePlayer extends AppCompatActivity {

    private static final String TAG = "ActivityEdit_CreatePlayer";
    ImageView imgPlayer;
    EditText txtName, txtRankingPos, txtPoints;
    Spinner spinner;
    Button btnCancel, btnSave;
    long idPlayer;
    boolean edit;
    String image, token;
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

        idPlayer = getIntent().getIntExtra("idPlayer", 0);
        if (idPlayer != 0) {
            autocompletePlayerInfo();
            edit = true;
        }

        token = SharedPreferencesManager.getInstance(ActivityEdit_CreatePlayer.this).getToken();

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "No has guardado nada.", Toast.LENGTH_SHORT).show();
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

                if (isEmptyOrNull(name) || isEmptyOrNull(points) || isEmptyOrNull(rankingPos)) {
                    Toast.makeText(ActivityEdit_CreatePlayer.this, "Debes rellenar todos los datos", Toast.LENGTH_SHORT).show();
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
        if (gender.equalsIgnoreCase("masc")) {
            return 0;
        } else {
            return 1;
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
                    Toast.makeText(ActivityEdit_CreatePlayer.this, "Código error: " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }

                Player playerAPI = response.body();

                if (playerAPI != null) {
                    Toast.makeText(ActivityEdit_CreatePlayer.this, "Has guardado el jugador/a.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ActivityEdit_CreatePlayer.this, "Error en la respuesta del servidor", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<Player> call, Throwable t) {
                Log.e(TAG, "Error en la llamada Retrofit - (updatePlayer)", t);
                Toast.makeText(ActivityEdit_CreatePlayer.this, "Código error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(ActivityEdit_CreatePlayer.this, "Código error: " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }

                Player playerAPI = response.body();

                if (playerAPI != null) {
                    Toast.makeText(ActivityEdit_CreatePlayer.this, "Has creado el jugador/a.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ActivityEdit_CreatePlayer.this, "Error en la respuesta del servidor", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Player> call, Throwable t) {
                Log.e(TAG, "Error en la llamada Retrofit - (saveNewPlayer)", t);
                Toast.makeText(ActivityEdit_CreatePlayer.this, "Código error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(ActivityEdit_CreatePlayer.this, "Código error: " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }

                Player playerAPI = response.body();

                if (playerAPI != null) {
                    txtName.setText(playerAPI.getName());

                    String points = playerAPI.getPoints().replaceAll("[^\\d.]", "");
                    txtPoints.setText(points);

                    txtRankingPos.setText(String.valueOf(playerAPI.getRankingPosition()));
                    spinner.setSelection(selectGenderSpinner(playerAPI.getGender()));

                    int imageResourceId = ActivityEdit_CreatePlayer.this.getResources().getIdentifier(playerAPI.getImageURL(), "drawable", ActivityEdit_CreatePlayer.this.getPackageName());
                    imgPlayer.setImageResource(imageResourceId);
                    image = playerAPI.getImageURL();
                } else {
                    Toast.makeText(ActivityEdit_CreatePlayer.this, "Error en la respuesta del servidor", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(ActivityEdit_CreatePlayer.this, "Código error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                txtName.setText("vacío");
                txtPoints.setText("0");
                txtRankingPos.setText("0");
                spinner.setSelection(0);
                imgPlayer.setImageResource(R.drawable.icono_img);
            }
        });
    }

}