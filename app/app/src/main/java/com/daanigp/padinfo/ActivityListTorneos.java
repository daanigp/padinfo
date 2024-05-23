package com.daanigp.padinfo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.daanigp.padinfo.Adapter.TorneoAdapter;
import com.daanigp.padinfo.Entity.Torneo;
import com.daanigp.padinfo.Interface_API.IPadinfo_API;
import com.daanigp.padinfo.Retrofit.RetrofitClient;
import com.daanigp.padinfo.SharedPreferences.SharedPreferencesManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityListTorneos extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private static final String TAG = "ActivityListTorneos";
    Button btnVolver;
    ArrayList<Torneo> tournamnets;
    String token;
    TorneoAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_torneos);

        tournamnets = new ArrayList<>();

        token = SharedPreferencesManager.getInstance(ActivityListTorneos.this).getToken();

        Log.v(TAG, "TOKEN -> " + token);

        getTournaments();

        ListView lista = (ListView) findViewById(R.id.listaTorneos);
        adapter = new TorneoAdapter(this, R.layout.item_torneo, tournamnets);

        lista.setAdapter(adapter);
        lista.setOnItemClickListener(this);

        btnVolver = (Button) findViewById(R.id.btnVolver);
        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(this, "HAS PULSADO SOBRE -> " + tournamnets.get(position).getName(), Toast.LENGTH_LONG).show();
    }


    private void getTournaments(){
        IPadinfo_API padinfoApi = RetrofitClient.getPadinfoAPI();
        Call<List<Torneo>> call = padinfoApi.getTournaments(token);

        call.enqueue(new Callback<List<Torneo>>() {
            @Override
            public void onResponse(Call<List<Torneo>> call, Response<List<Torneo>> response) {
                if(!response.isSuccessful()) {
                    Toast.makeText(ActivityListTorneos.this, "Código error: " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }

                List<Torneo> tournamentsApi = response.body();

                if (tournamentsApi != null) {
                    for (Torneo t : tournamentsApi) {
                        Torneo tor = new Torneo();
                        tor.setId(t.getId());
                        tor.setName(t.getName());
                        tor.setCity(t.getCity());
                        tor.setImageURL((t.getImageURL()));
                        tournamnets.add(tor);
                    }

                    // Notificar al adapter que los datos han cambiado
                    adapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(ActivityListTorneos.this, "Error en la respuesta del servidor", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Torneo>> call, Throwable t) {
                Log.e(TAG, "Error en la llamada Retrofit", t);
                Toast.makeText(ActivityListTorneos.this, "Código error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}