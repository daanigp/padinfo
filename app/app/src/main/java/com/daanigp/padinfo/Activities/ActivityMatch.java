package com.daanigp.padinfo.Activities;

import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.daanigp.padinfo.R;

public class ActivityMatch extends AppCompatActivity {

    TextView namesTeam1, namesTeam2, ptsS1T1, ptsS1T2, ptsS2T1, ptsS2T2, ptsS3T1, ptsS3T2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_match);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        namesTeam1 = findViewById(R.id.namePlayersTeam1);
        namesTeam2 = findViewById(R.id.namePlayersTeam2);
        ptsS1T1 = findViewById(R.id.pointsSet1Team1);
        ptsS2T1 = findViewById(R.id.pointsSet2Team1);
        ptsS3T1 = findViewById(R.id.pointsSet3Team1);
        ptsS1T2 = findViewById(R.id.pointsSet1Team2);
        ptsS2T2 = findViewById(R.id.pointsSet2Team2);
        ptsS3T2 = findViewById(R.id.pointsSet3Team2);

        namesTeam1.setText("AAAAAAAAAAA");
        namesTeam2.setText("BBBBBBBBBBB");

        ptsS1T1.setText("6");
        ptsS2T1.setText("6");
        ptsS3T1.setText("0");
        ptsS1T2.setText("0");
        ptsS2T2.setText("0");
        ptsS3T2.setText("0");

    }
}