package com.daanigp.padinfo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import java.net.MalformedURLException;
import java.net.URL;

public class Activity_Inicio extends AppCompatActivity {

    TextView txtInfoApp, txtInfoApp_webs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);

        txtInfoApp = (TextView) findViewById(R.id.txtInfoApp);
        txtInfoApp_webs = (TextView) findViewById(R.id.txtInfoApp_webs);

        String premierPadel, rankingMasculino, rankingFemenino;
        premierPadel = "https://premierpadel.com/";
        rankingMasculino = "https://www.padelfip.com/ranking-male/";
        rankingFemenino = "https://www.padelfip.com/ranking-female/";

        try {

            URL urlPremierPadel = new URL(premierPadel);
            URL urlRankingMasculino = new URL(rankingMasculino);
            URL urlRankingFemenino = new URL(rankingFemenino);

            txtInfoApp_webs.setText("Premier padel: " + urlPremierPadel);
            txtInfoApp_webs.append("\nRanking masculino: " + urlRankingMasculino);
            txtInfoApp_webs.append("\nRanking femenino: " + urlRankingFemenino);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

    }
}