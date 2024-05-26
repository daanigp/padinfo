package com.daanigp.padinfo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.daanigp.padinfo.Adapter.PartidoAdapter;
import com.daanigp.padinfo.Entity.Game;
import com.daanigp.padinfo.Entity.Respone.ResponseEntity;
import com.daanigp.padinfo.Interface_API.IPadinfo_API;
import com.daanigp.padinfo.Retrofit.RetrofitClient;
import com.daanigp.padinfo.SharedPreferences.SharedPreferencesManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityListPartidos extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private static final String TAG = "ActivityListPartidos";
    public static int CREATE_GAME = 3;
    public static int EDIT_GAME = 4;
    Button btnAddGame, btnVolver;
    ArrayList<Game> games;
    ListView lista;
    PartidoAdapter gameAdapter;
    String token;
    long userId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_partidos);

        games = new ArrayList<>();

        btnAddGame = (Button) findViewById(R.id.btnNewGame);
        btnVolver = (Button) findViewById(R.id.buttonVolver);

        userId = SharedPreferencesManager.getInstance(ActivityListPartidos.this).getUserId();
        token = SharedPreferencesManager.getInstance(ActivityListPartidos.this).getToken();

        getGames();

        lista = (ListView) findViewById(R.id.listaPartidos);
        gameAdapter = new PartidoAdapter(this, R.layout.item_partido, games);

        lista.setAdapter(gameAdapter);
        lista.setOnItemClickListener(this);

        registerForContextMenu(lista);
        btnAddGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentAddGame = new Intent(ActivityListPartidos.this, ActivityCrear_EditarPartido.class);
                startActivityForResult(intentAddGame, CREATE_GAME);
            }
        });

        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent back = new Intent(ActivityListPartidos.this, Activity_Inicio.class);
                startActivity(back);
            }
        });

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(this, "HAS PULSADO SOBRE EL PARTIDO -> " + games.get(position).getId(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getMenuInflater().inflate(R.menu.contextmenu_edit_delete, menu);
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        Game game = (Game) lista.getItemAtPosition(info.position);

        int id = item.getItemId();

        switch (id) {
            case R.id.itemEditar:
                Toast.makeText(getApplicationContext(), "EDITAR -> " + game.getId(), Toast.LENGTH_SHORT).show();
                Intent intentEditarPartido = new Intent(ActivityListPartidos.this, ActivityCrear_EditarPartido.class);
                intentEditarPartido.putExtra("idGame", game.getId());
                startActivityForResult(intentEditarPartido, EDIT_GAME);
                break;
            case R.id.itemEliminar:
                showPopupMenu(game);
                break;
        }

        return super.onContextItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CREATE_GAME) {
            if (resultCode == RESULT_OK) {
                games.clear();
                getGames();
            }
        } else if (requestCode == EDIT_GAME) {
            if (resultCode == RESULT_OK) {
                games.clear();
                getGames();
            }
        }
    }

    private void getGames() {
        IPadinfo_API padinfoApi = RetrofitClient.getPadinfoAPI();
        Call<List<Game>> call = padinfoApi.getGamesByUserId(token, userId);

        call.enqueue(new Callback<List<Game>>() {
            @Override
            public void onResponse(Call<List<Game>> call, Response<List<Game>> response) {
                if(!response.isSuccessful()) {
                    Log.v(TAG, "No va (getPartidosFromDB) -> response");
                    Toast.makeText(ActivityListPartidos.this, "Código error: " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }

                List<Game> gamesAPI = response.body();

                if (gamesAPI != null) {
                    for(Game g: gamesAPI) {
                        Game game = new Game();
                        game.setId(g.getId());
                        game.setUserId(userId);
                        game.setNamePlayer1(g.getNamePlayer1());
                        game.setNamePlayer2(g.getNamePlayer2());
                        game.setNamePlayer3(g.getNamePlayer3());
                        game.setNamePlayer4(g.getNamePlayer4());
                        game.setSet1PointsT1(g.getSet1PointsT1());
                        game.setSet2PointsT1(g.getSet2PointsT1());
                        game.setSet3PointsT1(g.getSet3PointsT1());
                        game.setSet1PointsT2(g.getSet1PointsT2());
                        game.setSet2PointsT2(g.getSet2PointsT2());
                        game.setSet3PointsT2(g.getSet3PointsT2());
                        game.setWinnerTeam(g.getWinnerTeam());

                        games.add(game);
                    }

                    // Notificar al adapter que los datos han cambiado
                    gameAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(ActivityListPartidos.this, "Error en la respuesta del servidor", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Game>> call, Throwable t) {
                Log.e(TAG, "Error en la llamada Retrofit - (getPartidosFromDB)", t);
                Toast.makeText(ActivityListPartidos.this, "Código error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showPopupMenu(Game g) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Eliminar partido");
        builder.setMessage("¿Estás seguro de eliminar el partido?");

        builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deleteGame(g);
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(), "¡CALMA! No has eliminado nada, todo sigue igual.", Toast.LENGTH_SHORT).show();
            }
        });

        builder.show();
    }

    private void deleteGame(Game g) {
        IPadinfo_API padinfoApi = RetrofitClient.getPadinfoAPI();
        Call<ResponseEntity> call = padinfoApi.deleteGameByid(token, g.getId());

        call.enqueue(new Callback<ResponseEntity>() {
            @Override
            public void onResponse(Call<ResponseEntity> call, Response<ResponseEntity> response) {
                if (!response.isSuccessful()) {
                    Log.v(TAG, "No va (userExists) -> response");
                    Toast.makeText(ActivityListPartidos.this, "Código error: " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }

                ResponseEntity res = response.body();

                if (res.getMessege().equalsIgnoreCase("Borrado")) {
                    games.remove(g);
                    gameAdapter.notifyDataSetChanged();
                    Toast.makeText(getApplicationContext(), "Partido eliminado.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "No se ha podido eliminar el partido.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseEntity> call, Throwable t) {
                Log.e(TAG, "Error en la llamada Retrofit - (userExists)", t);
                Toast.makeText(ActivityListPartidos.this, "Código error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}