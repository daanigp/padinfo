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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import com.daanigp.padinfo.Adapter.PlayerAdapter;
import com.daanigp.padinfo.Entity.Player;
import com.daanigp.padinfo.Entity.Respone.ResponseEntity;
import com.daanigp.padinfo.Interface_API.IPadinfo_API;
import com.daanigp.padinfo.Retrofit.RetrofitClient;
import com.daanigp.padinfo.SharedPreferences.SharedPreferencesManager;
import com.daanigp.padinfo.Toast.Toast_Personalized;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityRanking extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private static final String TAG = "ActivityRanking";
    public static int EDIT_PLAYER = 7;
    public static int CREATE_PLAYER = 8;
    Button btnVolverMenu, btnRankFem, btnRankMasc;
    Spinner spinner;
    ArrayList<Player> players;
    String token;
    ListView lista;
    PlayerAdapter adapter;
    boolean adminUser;
    View message_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);

        btnVolverMenu = (Button) findViewById(R.id.btnVolverMenu);
        btnRankFem = (Button) findViewById(R.id.btnRankFem);
        btnRankMasc = (Button) findViewById(R.id.btnRankMasc);
        spinner = (Spinner) findViewById(R.id.spinnerGenderP);

        players = new ArrayList<>();
        token = SharedPreferencesManager.getInstance(ActivityRanking.this).getToken();
        message_layout = getLayoutInflater().inflate(R.layout.toast_customized, null);

        Log.v(TAG, "TOKEN -> " + token);
        chekUserType();

        spinner.setSelection(0);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String gender = parent.getItemAtPosition(position).toString();
                getPlayers(gender);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                showToast("Selecciona el género para ver el ranking");
            }
        });

        lista = (ListView) findViewById(R.id.listRanking);
        adapter = new PlayerAdapter(this, R.layout.item_player, players);

        lista.setAdapter(adapter);
        lista.setOnItemClickListener(this);

        registerForContextMenu(lista);

        btnVolverMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnRankMasc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentRankMasc = new Intent(ActivityRanking.this, ActivityRankingMasculino.class);
                startActivity(intentRankMasc);
            }
        });

        btnRankFem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentRankFem = new Intent(ActivityRanking.this, ActivityRankingFemenino.class);
                startActivity(intentRankFem);
            }
        });


    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        showToast("HAS PULSADO SOBRE -> " + players.get(position).getName());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (adminUser) {
            getMenuInflater().inflate(R.menu.dinamicmenu_create_player, menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int opcionID = item.getItemId();

        if (opcionID == R.id.itemCreateTournament)  {
            Intent intentAddGame = new Intent(ActivityRanking.this, ActivityEdit_CreatePlayer.class);
            startActivityForResult(intentAddGame, CREATE_PLAYER);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        if (adminUser) {
            getMenuInflater().inflate(R.menu.contextmenu_edit_delete, menu);
        }
        super.onCreateContextMenu(menu, v, menuInfo);
    }


    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        Player player = (Player) lista.getItemAtPosition(info.position);

        int id = item.getItemId();

        switch (id) {
            case R.id.itemEditar:
                showToast("EDITAR -> " + player.getId());
                Intent intentEditTournament = new Intent(ActivityRanking.this, ActivityEdit_CreatePlayer.class);
                intentEditTournament.putExtra("idPlayer", player.getId());
                startActivityForResult(intentEditTournament, EDIT_PLAYER);
                break;
            case R.id.itemEliminar:
                showPopupMenu(player);
                break;
        }

        return super.onContextItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == CREATE_PLAYER) {
            if (resultCode == RESULT_OK) {
                getPlayers(selectGender());
            }
        } else if (requestCode == EDIT_PLAYER) {
            if (resultCode == RESULT_OK) {
                getPlayers(selectGender());
            }
        }
    }

    private String selectGender() {
        int posGender = spinner.getSelectedItemPosition();

        if (posGender == 0) {
            return "masc";
        } else {
            return "fem";
        }
    }

    private void chekUserType() {
        List<Long> rolesId = SharedPreferencesManager.getInstance(ActivityRanking.this).getRolesId();

        if (rolesId.size() > 0 && rolesId.contains(1L)) {
            adminUser = true;
        } else {
            adminUser = false;
        }
    }

    private void getPlayers(String gender) {
        Log.v(TAG, "TOKEN -> " + token);

        IPadinfo_API padinfoApi = RetrofitClient.getPadinfoAPI();
        Call<List<Player>> call = padinfoApi.getPlayersByGender(token, gender);

        call.enqueue(new Callback<List<Player>>() {
            @Override
            public void onResponse(Call<List<Player>> call, Response<List<Player>> response) {
                if(!response.isSuccessful()) {
                    Log.v(TAG, "No va (getPlayers) -> response");
                    showToast("Código error: " + response.code());
                    return;
                }

                List<Player> playersAPI = response.body();

                if (playersAPI != null) {
                    players.clear();

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
                    showToast("Error en la respuesta del servidor");
                }
            }

            @Override
            public void onFailure(Call<List<Player>> call, Throwable t) {
                Log.e(TAG, "Error en la llamada Retrofit - (getPlayers)", t);
                showToast("Código error: " + t.getMessage());
            }
        });
    }

    private void showPopupMenu(Player player) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Eliminar jugador/a");
        builder.setMessage("¿Estás seguro de eliminar el jugador/a?");

        builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deletePlayer(player);
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

    private void deletePlayer(Player player) {
        IPadinfo_API padinfoApi = RetrofitClient.getPadinfoAPI();
        Call<ResponseEntity> call = padinfoApi.deletePlayerById(token, player.getId());

        call.enqueue(new Callback<ResponseEntity>() {
            @Override
            public void onResponse(Call<ResponseEntity> call, Response<ResponseEntity> response) {
                if (!response.isSuccessful()) {
                    Log.v(TAG, "No va (deletePlayer) -> response");
                    showToast("Código error: " + response.code());
                    return;
                }

                ResponseEntity res = response.body();

                if (res.getMessege().equalsIgnoreCase("Borrado")) {
                    players.remove(player);
                    adapter.notifyDataSetChanged();
                    showToast("Jugador/a eliminado.");
                } else {
                    showToast("No se ha podido eliminar el jugador.");
                }
            }

            @Override
            public void onFailure(Call<ResponseEntity> call, Throwable t) {
                Log.e(TAG, "Error en la llamada Retrofit - (deletePlayer)", t);
                showToast("Código error: " + t.getMessage());
            }
        });
    }

    private void showToast(String message) {
        Toast_Personalized toast = new Toast_Personalized(message, ActivityRanking.this, message_layout);
        toast.CreateToast();
    }
}