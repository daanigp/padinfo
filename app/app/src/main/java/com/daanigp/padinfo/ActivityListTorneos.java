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
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.daanigp.padinfo.Adapter.TorneoAdapter;
import com.daanigp.padinfo.Entity.Game;
import com.daanigp.padinfo.Entity.Respone.ResponseEntity;
import com.daanigp.padinfo.Entity.Tournament;
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
    public static int EDIT_TOURNAMENT = 5;
    public static int CREATE_TOURNAMENT = 6;
    Button btnVolver;
    ArrayList<Tournament> tournamnets;
    String token;
    ListView lista;
    TorneoAdapter adapter;
    boolean adminUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_torneos);

        tournamnets = new ArrayList<>();

        token = SharedPreferencesManager.getInstance(ActivityListTorneos.this).getToken();

        chekUserType();

        Log.v(TAG, "TOKEN -> " + token);

        getTournaments();

        lista = (ListView) findViewById(R.id.listaTorneos);
        adapter = new TorneoAdapter(this, R.layout.item_torneo, tournamnets);

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
        Toast.makeText(this, "HAS PULSADO SOBRE -> " + tournamnets.get(position).getName(), Toast.LENGTH_LONG).show();
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
            Intent intentAddGame = new Intent(ActivityListTorneos.this, ActivityEdit_CreateTournament.class);
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
                Toast.makeText(getApplicationContext(), "EDITAR -> " + tournament.getId(), Toast.LENGTH_SHORT).show();
                Intent intentEditTournament = new Intent(ActivityListTorneos.this, ActivityEdit_CreateTournament.class);
                intentEditTournament.putExtra("idGame", tournament.getId());
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
                tournamnets.clear();
                getTournaments();
            }
        } else if (requestCode == EDIT_TOURNAMENT) {
            if (resultCode == RESULT_OK) {
                tournamnets.clear();
                getTournaments();
            }
        }
    }

    private void chekUserType() {
        List<Long> rolesId = SharedPreferencesManager.getInstance(ActivityListTorneos.this).getRolesId();

        if (rolesId.size() > 0 && rolesId.contains(1L)) {
            adminUser = true;
        } else {
            adminUser = false;
        }
    }

    private void getTournaments(){
        IPadinfo_API padinfoApi = RetrofitClient.getPadinfoAPI();
        Call<List<Tournament>> call = padinfoApi.getTournaments(token);

        call.enqueue(new Callback<List<Tournament>>() {
            @Override
            public void onResponse(Call<List<Tournament>> call, Response<List<Tournament>> response) {
                if(!response.isSuccessful()) {
                    Log.e(TAG, "No va getTournaments - response" + response);
                    Toast.makeText(ActivityListTorneos.this, "Código error: " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }

                List<Tournament> tournamentsApi = response.body();

                if (tournamentsApi != null) {
                    for (Tournament t : tournamentsApi) {
                        Tournament tor = new Tournament();
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
            public void onFailure(Call<List<Tournament>> call, Throwable t) {
                Log.e(TAG, "Error en la llamada Retrofit - getTournaments", t);
                Toast.makeText(ActivityListTorneos.this, "Código error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
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
                Toast.makeText(getApplicationContext(), "¡CALMA! No has eliminado nada, todo sigue igual.", Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(ActivityListTorneos.this, "Código error: " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }

                ResponseEntity res = response.body();

                if (res.getMessege().equalsIgnoreCase("Borrado")) {
                    tournamnets.remove(tournament);
                    adapter.notifyDataSetChanged();
                    Toast.makeText(getApplicationContext(), "Torneo eliminado.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "No se ha podido eliminar el torneo.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseEntity> call, Throwable t) {
                Log.e(TAG, "Error en la llamada Retrofit - (deleteTournament)", t);
                Toast.makeText(ActivityListTorneos.this, "Código error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}