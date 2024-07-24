package com.daanigp.padinfo.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.daanigp.padinfo.DataSource.RankingDataSource;
import com.daanigp.padinfo.Entity.Player;
import com.daanigp.padinfo.Interfaces.Interface_API.IPadinfo_API;
import com.daanigp.padinfo.R;
import com.daanigp.padinfo.Retrofit.RetrofitClient;
import com.daanigp.padinfo.SharedPreferences.SharedPreferencesManager;
import com.daanigp.padinfo.Toast.Toast_Personalized;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityPlayer extends AppCompatActivity {

    private static final String TAG = "ActivityPlayer";
    ImageView img;
    TextView rankPos, name, points, gender;
    Button btnVolver;

    String token;
    long idPlayer;
    View message_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        img = findViewById(R.id.imagePlayer_View);
        rankPos = findViewById(R.id.txtRankingPos_View);
        name = findViewById(R.id.txtNamePlayer_View);
        points = findViewById(R.id.txtPoints_View);
        gender = findViewById(R.id.txtGender_View);
        btnVolver = findViewById(R.id.btnVolver_List_P);

        message_layout = getLayoutInflater().inflate(R.layout.toast_customized, null);
        token = SharedPreferencesManager.getInstance(ActivityPlayer.this).getToken();

        idPlayer = getIntent().getLongExtra("idPlayer", 0);
        if (idPlayer != 0) {
            autoCompletePlayerInfo();
            //completeInfo();
        } else {
            img.setImageResource(R.drawable.player_img);
            showToast("Fallo en el sistema - No se ha podido cargar ningún jugador");
        }

        setDayNight();

        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
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

    private String selectGenderSpinner(String gender) {
        if (gender.equalsIgnoreCase("fem")) {
            return "Femenino";
        } else {
            return "Masculino";
        }
    }

    public void completeInfo() {
        ArrayList<Player> players = RankingDataSource.rankingFem;
        Player p = null;

        for(Player player: players) {
            if (player.getId() == idPlayer) {
                p = player;
            }
        }

        if (p != null) {
            String positionRanking = p.getRankingPosition() + "ghgiggiii";
            name.setText(p.getName());
            gender.setText(p.getGender());
            points.setText(p.getPoints());
            rankPos.setText(positionRanking);
            int imageResourceId = getResources().getIdentifier(p.getImageURL(), "drawable", getPackageName());
            img.setImageResource(imageResourceId);
        }
    }

    private void autoCompletePlayerInfo() {
        IPadinfo_API padinfoApi = RetrofitClient.getPadinfoAPI();
        Call<Player> call = padinfoApi.findPlayerById(token, idPlayer);

        call.enqueue(new Callback<Player>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<Player> call, Response<Player> response) {
                if(!response.isSuccessful()) {
                    Log.v(TAG, "No va (autocompletePlayerInfo) -> response");
                    showToast("Código error: " + response.code());
                    name.setText("vacío");
                    points.setText("0");
                    rankPos.setText("0");
                    gender.setText("vacío");
                    img.setImageResource(R.drawable.player_img);
                    return;
                }

                Player playerAPI = response.body();

                if (playerAPI != null) {
                    name.setText(playerAPI.getName());

                    String pointsAPI = playerAPI.getPoints().replaceAll("[^\\d.]", "");
                    points.setText(pointsAPI);

                    rankPos.setText(playerAPI.getRankingPosition() + "º");
                    gender.setText(selectGenderSpinner(playerAPI.getGender()));

                    int imageResourceId = ActivityPlayer.this.getResources().getIdentifier(playerAPI.getImageURL(), "drawable", ActivityPlayer.this.getPackageName());
                    img.setImageResource(imageResourceId);
                } else {
                    showToast("Error en la respuesta del servidor");
                    name.setText("vacío");
                    points.setText("0");
                    rankPos.setText("0");
                    gender.setText("vacío");
                    img.setImageResource(R.drawable.player_img);
                }

            }

            @Override
            public void onFailure(Call<Player> call, Throwable t) {
                Log.e(TAG, "Error en la llamada Retrofit - (autocompletePlayerInfo)", t);
                showToast("Error: " + t.getMessage());
                name.setText("vacío");
                points.setText("0");
                rankPos.setText("0");
                gender.setText("vacío");
                img.setImageResource(R.drawable.player_img);
            }
        });
    }

    private void showToast(String message) {
        Toast_Personalized toast = new Toast_Personalized(message, ActivityPlayer.this, message_layout);
        toast.CreateToast();
    }
}