package com.daanigp.padinfo.Activities;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.daanigp.padinfo.Entity.Game;
import com.daanigp.padinfo.Interfaces.Interface_API.IPadinfo_API;
import com.daanigp.padinfo.R;
import com.daanigp.padinfo.Retrofit.RetrofitClient;
import com.daanigp.padinfo.SharedPreferences.SharedPreferencesManager;
import com.daanigp.padinfo.Toast.Toast_Personalized;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityMatch extends AppCompatActivity {

    private static final String TAG = "ActivityMatch";

    TextView namesTeam1, namesTeam2, ptsS1T1, ptsS1T2, ptsS2T1, ptsS2T2, ptsS3T1, ptsS3T2;
    View message_layout;
    Button btnBack;

    String token;
    long idGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_match);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        namesTeam1 = findViewById(R.id.namePlayersTeam1);
        namesTeam2 = findViewById(R.id.namePlayersTeam2);
        ptsS1T1 = findViewById(R.id.pointsSet1Team1);
        ptsS2T1 = findViewById(R.id.pointsSet2Team1);
        ptsS3T1 = findViewById(R.id.pointsSet3Team1);
        ptsS1T2 = findViewById(R.id.pointsSet1Team2);
        ptsS2T2 = findViewById(R.id.pointsSet2Team2);
        ptsS3T2 = findViewById(R.id.pointsSet3Team2);
        btnBack = findViewById(R.id.btnVolver_List_M);

        message_layout = getLayoutInflater().inflate(R.layout.toast_customized, null);
        token = SharedPreferencesManager.getInstance(ActivityMatch.this).getToken();

        idGame = getIntent().getLongExtra("idMatch", 0);
        if (idGame != 0) {
            autoCompleteMatchInfo();
        } else {
            Log.v(TAG, "El idGame es == 0");
            showToast("No se ha podido cargar el partido");
            namesTeam1.setText(" - ");
            namesTeam2.setText(" - ");
            ptsS1T1.setText("0");
            ptsS2T1.setText("0");
            ptsS3T1.setText("0");
            ptsS1T2.setText("0");
            ptsS2T2.setText("0");
            ptsS3T2.setText("0");
        }

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void autoCompleteMatchInfo() {
        IPadinfo_API padinfoApi = RetrofitClient.getPadinfoAPI();
        Call<Game> call = padinfoApi.getGameByIdGame(token, idGame);

        call.enqueue(new Callback<Game>() {
            @Override
            public void onResponse(Call<Game> call, Response<Game> response) {
                if(!response.isSuccessful()) {
                    showToast("No se ha podido cargar el partido");
                    Log.v(TAG, "No va (autoCompleteMatchInfo) -> response" + response.code());
                    namesTeam1.setText(" - ");
                    namesTeam2.setText(" - ");
                    ptsS1T1.setText("0");
                    ptsS2T1.setText("0");
                    ptsS3T1.setText("0");
                    ptsS1T2.setText("0");
                    ptsS2T2.setText("0");
                    ptsS3T2.setText("0");
                    return;
                }

                Game gameAPI = response.body();

                if (gameAPI != null) {

                    if (gameAPI.getWinnerTeam() == 1) {
                        namesTeam1.setTextColor(Color.argb(255, 0 ,0, 0));
                        namesTeam1.setTypeface(null, Typeface.BOLD);
                        namesTeam1.setTextSize(18);
                    } else {
                        namesTeam2.setTextColor(Color.argb(255, 0 ,0, 0));
                        namesTeam2.setTypeface(null, Typeface.BOLD);
                        namesTeam2.setTextSize(18);
                    }

                    String namesT1 = gameAPI.getNamePlayer1() + "\n" + gameAPI.getNamePlayer2();
                    String namesT2 = gameAPI.getNamePlayer3() + "\n" + gameAPI.getNamePlayer4();
                    namesTeam1.setText(namesT1);
                    namesTeam2.setText(namesT2);

                    if (gameAPI.getSet1PointsT1() > gameAPI.getSet1PointsT2()) {
                        ptsS1T1.setTypeface(null, Typeface.BOLD);
                    } else {
                        ptsS1T2.setTypeface(null, Typeface.BOLD);
                    }
                    ptsS1T1.setText(String.valueOf(gameAPI.getSet1PointsT1()));
                    ptsS1T2.setText(String.valueOf(gameAPI.getSet1PointsT2()));

                    if (gameAPI.getSet2PointsT1() > gameAPI.getSet2PointsT2()) {
                        ptsS2T1.setTypeface(null, Typeface.BOLD);
                    } else {
                        ptsS2T2.setTypeface(null, Typeface.BOLD);
                    }
                    ptsS2T1.setText(String.valueOf(gameAPI.getSet2PointsT1()));
                    ptsS2T2.setText(String.valueOf(gameAPI.getSet2PointsT2()));

                    if (gameAPI.getSet3PointsT1() == 0 && gameAPI.getSet3PointsT2() == 0) {
                        ptsS3T1.setText("-");
                        ptsS3T2.setText("-");
                    } else {
                        if (gameAPI.getSet3PointsT1() > gameAPI.getSet3PointsT2()) {
                            ptsS3T1.setTypeface(null, Typeface.BOLD);
                        } else {
                            ptsS3T2.setTypeface(null, Typeface.BOLD);
                        }
                        ptsS3T1.setText(String.valueOf(gameAPI.getSet3PointsT1()));
                        ptsS3T2.setText(String.valueOf(gameAPI.getSet3PointsT2()));
                    }


                } else {
                    showToast("No se ha podido cargar el partido debido a un fallo en el servidor");
                    namesTeam1.setText(" - ");
                    namesTeam2.setText(" - ");
                    ptsS1T1.setText("0");
                    ptsS2T1.setText("0");
                    ptsS3T1.setText("0");
                    ptsS1T2.setText("0");
                    ptsS2T2.setText("0");
                    ptsS3T2.setText("0");
                }
            }

            @Override
            public void onFailure(Call<Game> call, Throwable t) {
                showToast("Error: " + t.getMessage());
                Log.e(TAG, "Error en la llamada Retrofit - (autoCompleteMatchInfo)", t);
                namesTeam1.setText(" - ");
                namesTeam2.setText(" - ");
                ptsS1T1.setText("0");
                ptsS2T1.setText("0");
                ptsS3T1.setText("0");
                ptsS1T2.setText("0");
                ptsS2T2.setText("0");
                ptsS3T2.setText("0");
            }
        });
    }

    private void showToast(String message) {
        Toast_Personalized toast = new Toast_Personalized(message, ActivityMatch.this, message_layout);
        toast.CreateToast();
    }
}