package com.daanigp.padinfo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.daanigp.padinfo.Entity.CreateUpdateTournament;
import com.daanigp.padinfo.Entity.Tournament;
import com.daanigp.padinfo.Interface_API.IPadinfo_API;
import com.daanigp.padinfo.Retrofit.RetrofitClient;
import com.daanigp.padinfo.SharedPreferences.SharedPreferencesManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityEdit_CreateTournament extends AppCompatActivity {

    private static final String TAG = "ActivityEdit_CreateTournament";
    Button btnSave, btnCancel;
    EditText txtNameT, txtCityT;
    ImageView imgTournament;
    String token, image;
    long idTournament;
    boolean edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_create_tournament);

        btnSave = (Button) findViewById(R.id.btnSaveP);
        btnCancel = (Button) findViewById(R.id.btnCancelP);
        txtNameT = (EditText) findViewById(R.id.editTxtTournamentName);
        txtCityT = (EditText) findViewById(R.id.editTextTournamentCity);
        imgTournament = (ImageView) findViewById(R.id.imgTournament);

        // Para los partidos que se quieren editar
        idTournament = getIntent().getIntExtra("idTournament", 0);
        if (idTournament != 0) {
            autocompleteTournamentInfo();
            edit = true;
        }

        token = SharedPreferencesManager.getInstance(ActivityEdit_CreateTournament.this).getToken();

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
                String name, city;
                name = txtNameT.getText().toString();
                city = txtCityT.getText().toString();

                if (isEmptyOrNull(name) || isEmptyOrNull(city)) {
                    Toast.makeText(ActivityEdit_CreateTournament.this, "Debes rellenar todos los datos", Toast.LENGTH_SHORT).show();
                } else {
                    CreateUpdateTournament tournament = new CreateUpdateTournament();
                    tournament.setName(name);
                    tournament.setCity(city);
                    tournament.setImageURL(image);
                    if (edit) {
                        updateTournament(tournament);
                    } else {
                        saveNewTournament(tournament);
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

    private void updateTournament(CreateUpdateTournament tournament) {
        IPadinfo_API padinfoApi = RetrofitClient.getPadinfoAPI();
        Call<Tournament> call = padinfoApi.updateTournament(token, idTournament, tournament);

        call.enqueue(new Callback<Tournament>() {
            @Override
            public void onResponse(Call<Tournament> call, Response<Tournament> response) {
                if(!response.isSuccessful()) {
                    Log.v(TAG, "No va (updateTournament) -> response");
                    Toast.makeText(ActivityEdit_CreateTournament.this, "Código error: " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }

                Tournament tournamentAPI = response.body();

                if (tournamentAPI != null) {
                    Toast.makeText(ActivityEdit_CreateTournament.this, "Has guardado el torneo.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ActivityEdit_CreateTournament.this, "Error en la respuesta del servidor", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<Tournament> call, Throwable t) {
                Log.e(TAG, "Error en la llamada Retrofit - (saveNewTournament)", t);
                Toast.makeText(ActivityEdit_CreateTournament.this, "Código error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void saveNewTournament(CreateUpdateTournament tournament) {
        IPadinfo_API padinfoApi = RetrofitClient.getPadinfoAPI();
        Call<Tournament> call = padinfoApi.createTournament(token, tournament);

        call.enqueue(new Callback<Tournament>() {
            @Override
            public void onResponse(Call<Tournament> call, Response<Tournament> response) {
                if(!response.isSuccessful()) {
                    Log.v(TAG, "No va (saveNewTournament) -> response");
                    Toast.makeText(ActivityEdit_CreateTournament.this, "Código error: " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }

                Tournament tournamentAPI = response.body();

                if (tournamentAPI != null) {
                    Toast.makeText(ActivityEdit_CreateTournament.this, "Has creado un nuevo torneo.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ActivityEdit_CreateTournament.this, "Error en la respuesta del servidor", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<Tournament> call, Throwable t) {
                Log.e(TAG, "Error en la llamada Retrofit - (saveNewTournament)", t);
                Toast.makeText(ActivityEdit_CreateTournament.this, "Código error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void autocompleteTournamentInfo() {
        IPadinfo_API padinfoApi = RetrofitClient.getPadinfoAPI();
        Call<Tournament> call = padinfoApi.findTournamentById(token, idTournament);

        call.enqueue(new Callback<Tournament>() {
            @Override
            public void onResponse(Call<Tournament> call, Response<Tournament> response) {
                if(!response.isSuccessful()) {
                    Log.v(TAG, "No va (autocompleteTournamentInfo) -> response");
                    Toast.makeText(ActivityEdit_CreateTournament.this, "Código error: " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }

                Tournament tournamentAPI = response.body();

                if (tournamentAPI != null) {
                    txtNameT.setText(tournamentAPI.getName());
                    txtCityT.setText(tournamentAPI.getCity());

                    int imageResourceId = ActivityEdit_CreateTournament.this.getResources().getIdentifier(tournamentAPI.getImageURL(), "drawable", ActivityEdit_CreateTournament.this.getPackageName());
                    imgTournament.setImageResource(imageResourceId);
                    image = tournamentAPI.getImageURL();
                } else {
                    Toast.makeText(ActivityEdit_CreateTournament.this, "Error en la respuesta del servidor", Toast.LENGTH_SHORT).show();
                    txtNameT.setText("vacío");
                    txtCityT.setText("vacío");
                    imgTournament.setImageResource(R.drawable.icono_img);
                }
            }

            @Override
            public void onFailure(Call<Tournament> call, Throwable t) {
                Log.e(TAG, "Error en la llamada Retrofit - (autocompleteTournamentInfo)", t);
                Toast.makeText(ActivityEdit_CreateTournament.this, "Código error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                txtNameT.setText("vacío");
                txtCityT.setText("vacío");
                imgTournament.setImageResource(R.drawable.icono_img);
            }
        });
    }
}