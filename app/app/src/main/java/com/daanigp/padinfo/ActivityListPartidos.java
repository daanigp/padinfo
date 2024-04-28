package com.daanigp.padinfo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ActivityListPartidos extends AppCompatActivity implements AdapterView.OnItemClickListener {

    SQLiteDatabase db;
    Button btnAddGame, btnVolver;
    ArrayList<Partido> partidos;
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

        ListView lista = (ListView) findViewById(R.id.listaPartidos);
        PartidoAdapter adapter = new PartidoAdapter(this, R.layout.item_partido, partidos);

        lista.setAdapter(adapter);
        lista.setOnItemClickListener(this);

        btnAddGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentAddGame = new Intent(ActivityListPartidos.this, ActivityPartido.class);
                startActivity(intentAddGame);
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
}