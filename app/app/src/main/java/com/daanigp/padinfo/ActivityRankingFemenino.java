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

import com.daanigp.padinfo.Adapter.PlayerAdapter;
import com.daanigp.padinfo.DataSource.RankingDataSource;
import com.daanigp.padinfo.Entity.Player;
import com.daanigp.padinfo.Interface_API.IPadinfo_API;
import com.daanigp.padinfo.Retrofit.RetrofitClient;
import com.daanigp.padinfo.SharedPreferences.SharedPreferencesManager;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityRankingFemenino extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private static final String TAG = "ActivityRankingFemenino";
    Button btnVolver;
    ArrayList<Player> players;
    String token;
    PlayerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking_femenino);

        players = new ArrayList<>();
        token = SharedPreferencesManager.getInstance(ActivityRankingFemenino.this).getToken();

        getPlayers();

        ListView lista = (ListView) findViewById(R.id.rankFemList);
        adapter = new PlayerAdapter(this, R.layout.item_player, players);

        lista.setAdapter(adapter);
        lista.setOnItemClickListener(this);

        btnVolver = (Button) findViewById(R.id.btnVolverRankings);
        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(this, "HAS PULSADO SOBRE -> " + RankingDataSource.rankingFem.get(position).getName(), Toast.LENGTH_LONG).show();
    }

    private void getPlayers() {
        Log.v(TAG, "TOKEN -> " + token);

        IPadinfo_API padinfoApi = RetrofitClient.getPadinfoAPI();
        Call<List<Player>> call = padinfoApi.getPlayersByGender(token, "fem");

        call.enqueue(new Callback<List<Player>>() {
            @Override
            public void onResponse(Call<List<Player>> call, Response<List<Player>> response) {
                if(!response.isSuccessful()) {
                    Log.v(TAG, "No va (getPlayersFEM) -> response");
                    Toast.makeText(ActivityRankingFemenino.this, "Código error: " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }

                Log.v(TAG, "JSON recibido: " + new Gson().toJson(response.body()));

                List<Player> playersAPI = response.body();

                if (playersAPI != null) {
                   /* playersAPI.sort(new Comparator<Player>() {
                        @Override
                        public int compare(Player p1, Player p2) {
                            return Integer.compare(p1.getPosicion(), p2.getPosicion());
                        }
                    });*/

                    for (Player p: playersAPI) {
                        Player player = new Player();
                        player.setId(p.getId());
                        player.setName(p.getName());
                        player.setPoints(p.getPoints());
                        player.setGender(p.getGender());
                        player.setRankingPosition(p.getRankingPosition());
                        player.setImageURL(p.getImageURL());

                        Log.v(TAG, "id -> " + p.getId());
                        Log.v(TAG, "nombre -> " + p.getName());
                        Log.v(TAG, "puntos -> " + p.getPoints());
                        Log.v(TAG, "posicion -> " + p.getRankingPosition());
                        Log.v(TAG, "IMAGEN URL -> " + p.getImageURL());

                        players.add(player);
                    }

                    players.sort(new Comparator<Player>() {
                        @Override
                        public int compare(Player p1, Player p2) {
                            return Integer.compare(p1.getRankingPosition(), p2.getRankingPosition());
                        }
                    });

                    // Notificar al adapter que los datos han cambiado
                    adapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(ActivityRankingFemenino.this, "Error en la respuesta del servidor", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Player>> call, Throwable t) {
                Log.e(TAG, "Error en la llamada Retrofit - (getPlayersFEM)", t);
                Toast.makeText(ActivityRankingFemenino.this, "Código error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}