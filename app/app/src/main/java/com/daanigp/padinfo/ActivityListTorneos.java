package com.daanigp.padinfo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.daanigp.padinfo.Interface_API.IPadinfo_API;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ActivityListTorneos extends AppCompatActivity implements AdapterView.OnItemClickListener {

    Button btnVolver;
    ArrayList<Torneo> tournamnets;
    String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_torneos);

        //TorneoDataSource.Initialize();

        Intent intent = getIntent();
        token = intent.getStringExtra("token");

        getTournaments();

        ListView lista = (ListView) findViewById(R.id.listaTorneos);
        TorneoAdapter adapter = new TorneoAdapter(this, R.layout.item_torneo, tournamnets);

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
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.18.4:8080/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        IPadinfo_API padinfoApi = retrofit.create(IPadinfo_API.class);

        Call<List<Torneo>> call = padinfoApi.getTournaments(token);

        call.enqueue(new Callback<List<Torneo>>() {
            @Override
            public void onResponse(Call<List<Torneo>> call, Response<List<Torneo>> response) {
                if(!response.isSuccessful()) {
                    Toast.makeText(ActivityListTorneos.this, "Código error: " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }

                List<Torneo> tournamentsApi = response.body();

                for(Torneo t: tournamentsApi) {
                    tournamnets.add(new Torneo(t.getId(), t.getName(), t.getCity(), t.getImageURL()));
                }

            }

            @Override
            public void onFailure(Call<List<Torneo>> call, Throwable t) {
                Toast.makeText(ActivityListTorneos.this, "Código error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}