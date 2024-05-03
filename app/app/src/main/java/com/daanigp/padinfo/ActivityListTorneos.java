package com.daanigp.padinfo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class ActivityListTorneos extends AppCompatActivity implements AdapterView.OnItemClickListener {

    Button btnVolver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_torneos);

        TorneoDataSource.Initialize();

        ListView lista = (ListView) findViewById(R.id.listaTorneos);
        TorneoAdapter adapter = new TorneoAdapter(this, R.layout.item_torneo, TorneoDataSource.torneos);

        lista.setAdapter(adapter);
        lista.setOnItemClickListener(this);

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
        Toast.makeText(this, "HAS PULSADO SOBRE -> " + TorneoDataSource.torneos.get(position).getNombre(), Toast.LENGTH_LONG).show();
    }
}