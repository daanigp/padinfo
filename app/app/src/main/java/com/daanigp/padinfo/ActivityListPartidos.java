package com.daanigp.padinfo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ActivityListPartidos extends AppCompatActivity implements AdapterView.OnItemClickListener {

    public static int CREATE_GAME = 3;
    public static int EDIT_GAME = 4;
    SQLiteDatabase db;
    Button btnAddGame, btnVolver;
    ArrayList<Partido> partidos;
    ListView lista;
    PartidoAdapter gameAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_partidos);

        btnAddGame = (Button) findViewById(R.id.btnNewGame);
        btnVolver = (Button) findViewById(R.id.buttonVolver);

        db = openOrCreateDatabase("UsersPadinfo", Context.MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS users(" +
                "User VARCHAR, " +
                "Password VARCHAR, " +
                "Isconnected INTEGER" +
                ");"
        );
        db.execSQL("CREATE TABLE IF NOT EXISTS games(" +
                "IdGame INTEGER, " +
                "User VARCHAR, " +
                "Player1 VARCHAR, " +
                "Player2 VARCHAR, " +
                "Player3 VARCHAR, " +
                "Player4 VARCHAR, " +
                "Set1PointsT1 INTEGER, " +
                "Set2PointsT1 INTEGER, " +
                "Set3PointsT1 INTEGER, " +
                "Set1PointsT2 INTEGER, " +
                "Set2PointsT2 INTEGER, " +
                "Set3PointsT2 INTEGER, " +
                "EquipoGanador INTEGER" +
                ");"
        );

        partidos = getPartidosFromDB();

        lista = (ListView) findViewById(R.id.listaPartidos);
        gameAdapter = new PartidoAdapter(this, R.layout.item_partido, partidos);

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
        Toast.makeText(this, "HAS PULSADO SOBRE EL PARTIDO -> " + partidos.get(position).getIdGame(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getMenuInflater().inflate(R.menu.menu_contextual_partidos, menu);
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        Partido partido = (Partido) lista.getItemAtPosition(info.position);

        int id = item.getItemId();

        switch (id) {
            case R.id.itemEditar:
                Toast.makeText(getApplicationContext(), "EDITAR -> " + partido.getIdGame(), Toast.LENGTH_SHORT).show();
                Intent intentEditarPartido = new Intent(ActivityListPartidos.this, ActivityCrear_EditarPartido.class);
                intentEditarPartido.putExtra("idGame", partido.getIdGame());
                startActivityForResult(intentEditarPartido, EDIT_GAME);
                break;
            case R.id.itemEliminar:
                showPopupMenu(partido);
                break;
        }

        return super.onContextItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CREATE_GAME) {
            if (resultCode == RESULT_OK) {
                //partidos.clear();
                //partidos = getPartidosFromDB();
                gameAdapter.notifyDataSetChanged();
            }
        } else if (requestCode == EDIT_GAME) {
            if (resultCode == RESULT_OK) {
                partidos.clear();
                partidos = getPartidosFromDB();
                gameAdapter.notifyDataSetChanged();
            }
        }
    }

    private ArrayList<Partido> getPartidosFromDB(){
        String user = getUserConnected();
        ArrayList<Partido> games = new ArrayList<>();

        if (!(user.equalsIgnoreCase("") || user.isEmpty())) {
            Cursor c = db.rawQuery("SELECT * FROM games WHERE User = '" + user + "'", null);
            if (c.getCount() > 0){
                while(c.moveToNext()) {
                    Partido p = new Partido();
                    p.setIdGame(c.getInt(0));
                    p.setUser(c.getString(1));
                    p.setPlayer1(c.getString(2));
                    p.setPlayer2(c.getString(3));
                    p.setPlayer3(c.getString(4));
                    p.setPlayer4(c.getString(5));
                    p.setSet1PointsT1(c.getInt(6));
                    p.setSet2PointsT1(c.getInt(7));
                    p.setSet3PointsT1(c.getInt(8));
                    p.setSet1PointsT2(c.getInt(9));
                    p.setSet2PointsT2(c.getInt(10));
                    p.setSet3PointsT2(c.getInt(11));
                    p.setEquipoGanador(c.getInt(12));

                    games.add(p);
                }
                return games;
            } else {
                Toast.makeText(this, "2. NO furula :(", Toast.LENGTH_SHORT).show();
                return games;
            }
        }

        Toast.makeText(this, "1. NO VVA :(", Toast.LENGTH_SHORT).show();
        return games;
    }

    private String getUserConnected(){
        Cursor c = db.rawQuery("SELECT * FROM users WHERE Isconnected = 1", null);

        if (c.moveToFirst()) {
            int indexUser = c.getColumnIndex("User");
            String user = c.getString(indexUser);
            if (user != null || !user.isEmpty()){
                c.close();
                return user;
            }
        }

        c.close();

        return "";
    }

    private void showPopupMenu(Partido p) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Eliminar Partido");
        builder.setMessage("¿Estás seguro de eliminar el partido?");

        builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (deleteGame(p)) {
                    partidos.remove(p);
                    gameAdapter.notifyDataSetChanged();
                    Toast.makeText(getApplicationContext(), "Partido eliminado.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "No se ha podido eliminar el partido.", Toast.LENGTH_SHORT).show();
                }
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

    private boolean deleteGame(Partido p){
        Cursor c = db.rawQuery("SELECT * FROM games WHERE IdGame = " + p.getIdGame() + ";", null);

        if (c.getCount() != 0) {
            db.execSQL("DELETE FROM games WHERE IdGame = " + p.getIdGame() + ";");
            c.close();
            return true;
        }

        c.close();

        return false;
    }
}