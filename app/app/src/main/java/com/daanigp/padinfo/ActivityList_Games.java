package com.daanigp.padinfo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

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

import com.daanigp.padinfo.Adapter.GameAdapter;
import com.daanigp.padinfo.Entity.Game;
import com.daanigp.padinfo.Entity.Respone.ResponseEntity;
import com.daanigp.padinfo.Interface_API.IPadinfo_API;
import com.daanigp.padinfo.Retrofit.RetrofitClient;
import com.daanigp.padinfo.SharedPreferences.SharedPreferencesManager;
import com.daanigp.padinfo.Toast.Toast_Personalized;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityList_Games extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private static final String TAG = "ActivityList_Games";
    public static int CREATE_GAME = 3;
    public static int EDIT_GAME = 4;
    Button btnAddGame, btnVolver;
    ArrayList<Game> games;
    ListView lista;
    GameAdapter gameAdapter;
    String token;
    long userId;
    View message_layout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_games);

        games = new ArrayList<>();

        btnAddGame = (Button) findViewById(R.id.btnNewGame);
        btnVolver = (Button) findViewById(R.id.buttonVolver);

        userId = SharedPreferencesManager.getInstance(ActivityList_Games.this).getUserId();
        token = SharedPreferencesManager.getInstance(ActivityList_Games.this).getToken();
        message_layout = getLayoutInflater().inflate(R.layout.toast_customized, null);

        setDayNight();
        getGames();

        lista = (ListView) findViewById(R.id.listaPartidos);
        gameAdapter = new GameAdapter(this, R.layout.item_game, games);

        lista.setAdapter(gameAdapter);
        lista.setOnItemClickListener(this);

        registerForContextMenu(lista);
        btnAddGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentAddGame = new Intent(ActivityList_Games.this, ActivityEdit_Create_Game.class);
                startActivityForResult(intentAddGame, CREATE_GAME);
            }
        });

        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent back = new Intent(ActivityList_Games.this, Activity_Initiate.class);
                startActivity(back);
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

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        showToast("HAS PULSADO SOBRE EL PARTIDO -> " + games.get(position).getId());
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
                showToast("EDITAR -> " + game.getId());
                Intent intentEditarPartido = new Intent(ActivityList_Games.this, ActivityEdit_Create_Game.class);
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
                getGames();
            }
        } else if (requestCode == EDIT_GAME) {
            if (resultCode == RESULT_OK) {
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
                    Log.v(TAG, "No va (getPartidosFromDB) -> response -> " + response.toString());
                    showToast("Código error: " + response.code());
                    return;
                }

                List<Game> gamesAPI = response.body();

                if (gamesAPI != null) {
                    games.clear();

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
                    showToast("Error en la respuesta del servidor");
                }
            }

            @Override
            public void onFailure(Call<List<Game>> call, Throwable t) {
                Log.e(TAG, "Error en la llamada Retrofit - (getPartidosFromDB)", t);
                showToast("Código error: " + t.getMessage());
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
                showToast("¡CALMA! No has eliminado nada, todo sigue igual.");
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
                    showToast("Código error: " + response.code());
                    return;
                }

                ResponseEntity res = response.body();

                if (res.getMessege().equalsIgnoreCase("Borrado")) {
                    games.remove(g);
                    gameAdapter.notifyDataSetChanged();
                    showToast("Partido eliminado.");
                } else {
                    showToast("No se ha podido eliminar el partido.");
                }
            }

            @Override
            public void onFailure(Call<ResponseEntity> call, Throwable t) {
                Log.e(TAG, "Error en la llamada Retrofit - (userExists)", t);
                showToast("Código error: " + t.getMessage());
            }
        });
    }

    private void showToast(String message) {
        Toast_Personalized toast = new Toast_Personalized(message, ActivityList_Games.this, message_layout);
        toast.CreateToast();
    }
}