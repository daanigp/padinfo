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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.daanigp.padinfo.Adapter.TournamentAdapter;
import com.daanigp.padinfo.Entity.Respone.ResponseEntity;
import com.daanigp.padinfo.Entity.Tournament;
import com.daanigp.padinfo.Interface_API.IPadinfo_API;
import com.daanigp.padinfo.Retrofit.RetrofitClient;
import com.daanigp.padinfo.SharedPreferences.SharedPreferencesManager;
import com.daanigp.padinfo.Toast.Toast_Personalized;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityList_Tournament extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private static final String TAG = "ActivityList_Tournament";
    public static int EDIT_TOURNAMENT = 5;
    public static int CREATE_TOURNAMENT = 6;
    Button btnVolver;
    ArrayList<Tournament> tournamnets;
    String token;
    ListView lista;
    TournamentAdapter adapter;
    boolean adminUser;
    View message_layout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_tournaments);

        tournamnets = new ArrayList<>();

        token = SharedPreferencesManager.getInstance(ActivityList_Tournament.this).getToken();
        message_layout = getLayoutInflater().inflate(R.layout.toast_customized, null);

        chekUserType();

        Log.v(TAG, "TOKEN -> " + token);

        setDayNight();

        getTournaments();

        lista = (ListView) findViewById(R.id.listaTorneos);
        adapter = new TournamentAdapter(this, R.layout.item_tournament, tournamnets);

        lista.setAdapter(adapter);
        lista.setOnItemClickListener(this);

        registerForContextMenu(lista);

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
        Intent viewTournmanet = new Intent(ActivityList_Tournament.this, ActivityTournament.class);
        viewTournmanet.putExtra("idTournament", tournamnets.get(position).getId());
        startActivity(viewTournmanet);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (adminUser) {
            getMenuInflater().inflate(R.menu.dinamicmenu_create_tournament, menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int opcionID = item.getItemId();

        if (opcionID == R.id.itemCreateTournament)  {
            Intent intentAddGame = new Intent(ActivityList_Tournament.this, ActivityEdit_Create_Tournament.class);
            startActivityForResult(intentAddGame, CREATE_TOURNAMENT);
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
        Tournament tournament = (Tournament) lista.getItemAtPosition(info.position);

        int id = item.getItemId();

        switch (id) {
            case R.id.itemEditar:
                Intent intentEditTournament = new Intent(ActivityList_Tournament.this, ActivityEdit_Create_Tournament.class);
                intentEditTournament.putExtra("idTournament", tournament.getId());
                startActivityForResult(intentEditTournament, EDIT_TOURNAMENT);
                break;
            case R.id.itemEliminar:
                showPopupMenu(tournament);
                break;
        }

        return super.onContextItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == CREATE_TOURNAMENT) {
            if (resultCode == RESULT_OK) {
                getTournaments();
            }
        } else if (requestCode == EDIT_TOURNAMENT) {
            if (resultCode == RESULT_OK) {
                getTournaments();
            }
        }
    }

    public void setDayNight() {
        int theme = SharedPreferencesManager.getInstance(this).getTheme();
        if (theme == 0) {
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }

    private void chekUserType() {
        List<Long> rolesId = SharedPreferencesManager.getInstance(ActivityList_Tournament.this).getRolesId();

        if (rolesId.size() > 0 && rolesId.contains(1L)) {
            adminUser = true;
        } else {
            adminUser = false;
        }

        invalidateOptionsMenu();
        updateContextMenu();
    }

    private void getTournaments(){
        IPadinfo_API padinfoApi = RetrofitClient.getPadinfoAPI();
        Call<List<Tournament>> call = padinfoApi.getTournaments(token);

        call.enqueue(new Callback<List<Tournament>>() {
            @Override
            public void onResponse(Call<List<Tournament>> call, Response<List<Tournament>> response) {
                if(!response.isSuccessful()) {
                    Log.e(TAG, "No va getTournaments - response" + response);
                    showToast("Código error: " + response.code());
                    return;
                }

                List<Tournament> tournamentsApi = response.body();

                if (tournamentsApi != null) {
                    tournamnets.clear();

                    for (Tournament t : tournamentsApi) {
                        Tournament tor = new Tournament();
                        tor.setId(t.getId());
                        tor.setName(t.getName());
                        tor.setCity(t.getCity());
                        tor.setImageURL((t.getImageURL()));
                        tournamnets.add(tor);
                    }

                    adapter.notifyDataSetChanged();
                } else {
                    showToast("Error en la respuesta del servidor");
                }
            }

            @Override
            public void onFailure(Call<List<Tournament>> call, Throwable t) {
                Log.e(TAG, "Error en la llamada Retrofit - getTournaments", t);
                showToast("Código error: " + t.getMessage());
            }
        });
    }

    private void showPopupMenu(Tournament tournament) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Eliminar torneo");
        builder.setMessage("¿Estás seguro de eliminar el torneo?");

        builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deleteTournament(tournament);
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

    private void deleteTournament(Tournament tournament) {
        IPadinfo_API padinfoApi = RetrofitClient.getPadinfoAPI();
        Call<ResponseEntity> call = padinfoApi.deleteTournamentById(token, tournament.getId());

        call.enqueue(new Callback<ResponseEntity>() {
            @Override
            public void onResponse(Call<ResponseEntity> call, Response<ResponseEntity> response) {
                if (!response.isSuccessful()) {
                    Log.v(TAG, "No va (deleteTournament) -> response");
                    showToast("Código error: " + response.code());
                    return;
                }

                ResponseEntity res = response.body();

                if (res.getMessege().equalsIgnoreCase("Borrado")) {
                    tournamnets.remove(tournament);
                    adapter.notifyDataSetChanged();
                    showToast("Torneo eliminado.");
                } else {
                    showToast("No se ha podido eliminar el torneo.");
                }
            }

            @Override
            public void onFailure(Call<ResponseEntity> call, Throwable t) {
                Log.e(TAG, "Error en la llamada Retrofit - (deleteTournament)", t);
                showToast("Código error: " + t.getMessage());
            }
        });
    }

    private void showToast(String message) {
        Toast_Personalized toast = new Toast_Personalized(message, ActivityList_Tournament.this, message_layout);
        toast.CreateToast();
    }

    private void updateContextMenu() {
        View view = findViewById(R.id.listaTorneos);
        unregisterForContextMenu(view);
        registerForContextMenu(view);
    }
}