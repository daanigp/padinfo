package com.daanigp.padinfo.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.daanigp.padinfo.DataSource.TournamentDataSource;
import com.daanigp.padinfo.Entity.Tournament;
import com.daanigp.padinfo.Interfaces.Interface_API.IPadinfo_API;
import com.daanigp.padinfo.R;
import com.daanigp.padinfo.Retrofit.RetrofitClient;
import com.daanigp.padinfo.SharedPreferences.SharedPreferencesManager;
import com.daanigp.padinfo.Toast.Toast_Personalized;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityTournament extends AppCompatActivity {

    private static final String TAG = "ActivityTournament";
    TextView city, nameTournament;
    ImageView img;
    Button btnVolver;
    String token;
    long idTournament;
    View message_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tournament);

        city = findViewById(R.id.txtCity);
        nameTournament = findViewById(R.id.txtTournamentName);
        img = findViewById(R.id.imgTournament);
        btnVolver = findViewById(R.id.btnVolver_List_T);

        message_layout = getLayoutInflater().inflate(R.layout.toast_customized, null);
        token = SharedPreferencesManager.getInstance(ActivityTournament.this).getToken();

        idTournament = getIntent().getLongExtra("idTournament", 0);
        if (idTournament != 0) {
            autoCompleteTournamentInfo();
            //completeInfo();
        } else {
            img.setImageResource(R.drawable.campo_padel);
            showToast("Fallo en el sistema - No se ha podido cargar ningún torneo");
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

    public void completeInfo() {
        ArrayList<Tournament> tournaments = TournamentDataSource.tournaments;
        Tournament t = null;

        for(Tournament tourn: tournaments) {
            if (tourn.getId() == idTournament) {
                t = tourn;
            }
        }

        nameTournament.setText(t.getName());
        city.setText(t.getCity());
        int imageResourceId = getResources().getIdentifier(t.getImageURL(), "drawable", getPackageName());
        img.setImageResource(imageResourceId);
    }

    private void autoCompleteTournamentInfo(){
        IPadinfo_API padinfoApi = RetrofitClient.getPadinfoAPI();
        Call<Tournament> call = padinfoApi.findTournamentById(token, idTournament);

        call.enqueue(new Callback<Tournament>() {
            @Override
            public void onResponse(Call<Tournament> call, Response<Tournament> response) {
                if(!response.isSuccessful()) {
                    Log.v(TAG, "No va (autocompleteTournamentInfo) -> response");
                    showToast("Código error: " + response.code());
                    nameTournament.setText("vacío");
                    city.setText("vacío");
                    img.setImageResource(R.drawable.campo_padel);
                    return;
                }

                Tournament tournamentAPI = response.body();

                if (tournamentAPI != null) {
                    nameTournament.setText(tournamentAPI.getName());
                    city.setText(tournamentAPI.getCity());

                    int imageResourceId = ActivityTournament.this.getResources().getIdentifier(tournamentAPI.getImageURL(), "drawable", ActivityTournament.this.getPackageName());
                    img.setImageResource(imageResourceId);
                } else {
                    showToast("Error en la respuesta del servidor");
                    nameTournament.setText("vacío");
                    city.setText("vacío");
                    img.setImageResource(R.drawable.campo_padel);
                }
            }

            @Override
            public void onFailure(Call<Tournament> call, Throwable t) {
                Log.e(TAG, "Error en la llamada Retrofit - (autocompleteTournamentInfo)", t);
                showToast("Código error: " + t.getMessage());
                nameTournament.setText("vacío");
                city.setText("vacío");
                img.setImageResource(R.drawable.campo_padel);
            }
        });
    }

    private void showToast(String message) {
        Toast_Personalized toast = new Toast_Personalized(message, ActivityTournament.this, message_layout);
        toast.CreateToast();
    }
}