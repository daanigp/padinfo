package com.daanigp.padinfo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ActivityListPartidos extends AppCompatActivity {

    Button btnAddGame;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_partidos);

        btnAddGame = (Button) findViewById(R.id.btnNewGame);

        btnAddGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentAddGame = new Intent(ActivityListPartidos.this, ActivityPartido.class);
                startActivity(intentAddGame);
            }
        });
    }
}