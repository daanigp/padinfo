package com.daanigp.padinfo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.daanigp.padinfo.Adapter.PlayerAdapter;
import com.daanigp.padinfo.Entity.Player;
import com.daanigp.padinfo.SharedPreferences.SharedPreferencesManager;

import java.util.ArrayList;

public class ActivityRanking extends AppCompatActivity {

    private static final String TAG = "ActivityRanking";
    Button btnVolverMenu, btnRankFem, btnRankMasc;
    ArrayList<Player> players;
    String token;
    PlayerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);

        btnVolverMenu = (Button) findViewById(R.id.btnVolverMenu);
        btnRankFem = (Button) findViewById(R.id.btnRankFem);
        btnRankMasc = (Button) findViewById(R.id.btnRankMasc);

        players = new ArrayList<>();
        token = SharedPreferencesManager.getInstance(ActivityRanking.this).getToken();

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
}