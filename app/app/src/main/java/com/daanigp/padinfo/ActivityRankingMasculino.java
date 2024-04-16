package com.daanigp.padinfo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class ActivityRankingMasculino extends AppCompatActivity implements AdapterView.OnItemClickListener {

    Button btnVolver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking_masculino);

        RankingDataSource.InitializeMasc();

        ListView lista = (ListView) findViewById(R.id.rankMascList);
        RankingAdapter adapter = new RankingAdapter(this, R.layout.item_player, RankingDataSource.rankingMasc);

        lista.setAdapter(adapter);
        lista.setOnItemClickListener(this);

        btnVolver = (Button) findViewById(R.id.btnVolverRanking);
        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(this, "HAS PULSADO SOBRE -> " + RankingDataSource.rankingMasc.get(position).getNombre(), Toast.LENGTH_LONG).show();
    }
}