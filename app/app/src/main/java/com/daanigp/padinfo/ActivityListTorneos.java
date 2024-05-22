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

import com.daanigp.padinfo.Interface_API.IPadinfo_API;
import com.daanigp.padinfo.Retrofit.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ActivityListTorneos extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private static final String TAG = "MyActivity";
    Button btnVolver;
    ArrayList<Torneo> tournamnets;
    String token;
    TorneoAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_torneos);

        //TorneoDataSource.Initialize();
        tournamnets = new ArrayList<>();

        Intent intent = getIntent();
        token = intent.getStringExtra("token");

        Log.v(TAG, "TOKEN -> " + token);

        getTournaments();

        Log.v(TAG, "Size -> " + tournamnets.size());

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
        /*Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://7145-2-142-11-49.ngrok-free.app/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        IPadinfo_API padinfoApi = retrofit.create(IPadinfo_API.class);*/

        IPadinfo_API padinfoApi = RetrofitClient.getPadinfoAPI();

        Call<List<Torneo>> call = padinfoApi.getTournaments(token);

        call.enqueue(new Callback<List<Torneo>>() {
            @Override
            public void onResponse(Call<List<Torneo>> call, Response<List<Torneo>> response) {
                if(!response.isSuccessful()) {
                    Log.v(TAG, "No va -> response");
                    Toast.makeText(ActivityListTorneos.this, "Código error: " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }

                List<Torneo> tournamentsApi = response.body();

                Log.v(TAG, "Size api -> " + tournamentsApi.size());


                for(Torneo t: tournamentsApi) {
                    Torneo tor = new Torneo();
                    tor.setId(t.getId());
                    tor.setName(t.getName());
                    tor.setCity(t.getCity());

                    // Obtener el ID del recurso drawable basado en el nombre de la imagen
                    //int imageResourceId = getResourceIdByName(t.getImageURL());
                    //tor.setImageURL(imageResourceId);

                    tor.setImageURL((t.getImageURL()));
                    tournamnets.add(tor);
                }

                // Notificar al adapter que los datos han cambiado
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Torneo>> call, Throwable t) {
                Log.e(TAG, "Error en la llamada Retrofit", t);
                Toast.makeText(ActivityListTorneos.this, "Código error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}