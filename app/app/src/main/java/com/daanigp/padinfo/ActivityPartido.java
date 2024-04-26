package com.daanigp.padinfo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.daanigp.padinfo.databinding.ActivityPartidoBinding;

public class ActivityPartido extends AppCompatActivity {

    ActivityPartidoBinding binding;
    /*Button btnAddPtsSet1Eq1, btnAddPtsSet2Eq1, btnAddPtsSet3Eq1, btnAddPtsSet1Eq2, btnAddPtsSet2Eq2, btnAddPtsSet3Eq2;
    Button btnDelPtsSet1Eq1, btnDelPtsSet2Eq1, btnDelPtsSet3Eq1, btnDelPtsSet1Eq2, btnDelPtsSet2Eq2, btnDelPtsSet3Eq2;
    TextView txtPtsSet1Eq1, txtPtsSet2Eq1, txtPtsSet3Eq1, txtPtsSet1Eq2, txtPtsSet2Eq2, txtPtsSet3Eq2;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partido);

        int ptsSet1Eq1, ptsSet2Eq1, ptsSet3Eq1, ptsSet1Eq2, ptsSet2Eq2, ptsSet3Eq2;

        int[] puntos = { 0, 15, 30, 40 };
        binding = ActivityPartidoBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.btnAddPtsSet11P.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String puntosSet, ptos;
                puntosSet = binding.txtPtosSet1Eq1P.getText().toString();

                int pts = Integer.parseInt(puntosSet);
                /*if (pts == puntos[0]){
                    ptos = String.valueOf(puntos[1]);
                    binding.txtPtosSet1Eq1P.setText(ptos);
                } else if (pts == puntos[1]) {
                    ptos = String.valueOf(puntos[2]);
                    binding.txtPtosSet1Eq1P.setText(ptos);
                } else if (pts == puntos[2]) {
                    ptos = String.valueOf(puntos[3]);
                    binding.txtPtosSet1Eq1P.setText(ptos);
                } else if (pts == puntos[3]) {
                    Toast.makeText(ActivityPartido.this, "No puedes poner más puntos", Toast.LENGTH_SHORT).show();
                }*/
                addPoints(pts, binding.txtPtosSet1Eq1P, binding.textViewSet11P);
                /*if (pts == 7) {
                    Toast.makeText(ActivityPartido.this, "No puedes poner más puntos en " + binding.textViewSet11P.getText().toString(), Toast.LENGTH_SHORT).show();
                } else {
                    ptos = String.valueOf(++pts);
                    binding.txtPtosSet1Eq1P.setText(ptos);
                }*/
            }
        });

        binding.btnDelPtsSet11P.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String puntosSet, ptos;
                puntosSet = binding.txtPtosSet1Eq1P.getText().toString();

                int pts = Integer.parseInt(puntosSet);
                /*if (pts == puntos[0]){
                    Toast.makeText(ActivityPartido.this, "No puedes poner menos puntos", Toast.LENGTH_SHORT).show();
                } else if (pts == puntos[1]) {
                    ptos = String.valueOf(puntos[0]);
                    binding.txtPtosSet1Eq1P.setText(ptos);
                } else if (pts == puntos[2]) {
                    ptos = String.valueOf(puntos[1]);
                    binding.txtPtosSet1Eq1P.setText(ptos);
                } else if (pts == puntos[3]) {
                    ptos = String.valueOf(puntos[2]);
                    binding.txtPtosSet1Eq1P.setText(ptos);
                }*/
                delPoints(pts, binding.txtPtosSet1Eq1P, binding.textViewSet11P);
                /*if (pts == 0) {
                    Toast.makeText(ActivityPartido.this, "No puedes poner menos puntos en " + binding.textViewSet11P.getText().toString(), Toast.LENGTH_SHORT).show();
                } else {
                    ptos = String.valueOf(--pts);
                    binding.txtPtosSet1Eq1P.setText(ptos);
                }*/
            }
        });

        binding.btnAddPtsSet21P.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String puntosSet, ptos;
                puntosSet = binding.txtPtosSet2Eq1P.getText().toString();

                int pts = Integer.parseInt(puntosSet);
                addPoints(pts, binding.txtPtosSet2Eq1P, binding.textViewSet21P);
                /*if (pts == puntos[0]){
                    ptos = String.valueOf(puntos[1]);
                    binding.txtPtosSet2Eq1P.setText(ptos);
                } else if (pts == puntos[1]) {
                    ptos = String.valueOf(puntos[2]);
                    binding.txtPtosSet2Eq1P.setText(ptos);
                } else if (pts == puntos[2]) {
                    ptos = String.valueOf(puntos[3]);
                    binding.txtPtosSet2Eq1P.setText(ptos);
                } else if (pts == puntos[3]) {
                    Toast.makeText(ActivityPartido.this, "No puedes poner más puntos", Toast.LENGTH_SHORT).show();
                }*/
            }
        });

        binding.btnDelPtsSet21P.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String puntosSet, ptos;
                puntosSet = binding.txtPtosSet2Eq1P.getText().toString();

                int pts = Integer.parseInt(puntosSet);
                delPoints(pts, binding.txtPtosSet2Eq1P, binding.textViewSet21P);
                /*if (pts == puntos[0]){
                    Toast.makeText(ActivityPartido.this, "No puedes poner menos puntos", Toast.LENGTH_SHORT).show();
                } else if (pts == puntos[1]) {
                    ptos = String.valueOf(puntos[0]);
                    binding.txtPtosSet2Eq1P.setText(ptos);
                } else if (pts == puntos[2]) {
                    ptos = String.valueOf(puntos[1]);
                    binding.txtPtosSet2Eq1P.setText(ptos);
                } else if (pts == puntos[3]) {
                    ptos = String.valueOf(puntos[2]);
                    binding.txtPtosSet2Eq1P.setText(ptos);
                }*/
            }
        });
    }

    private void addPoints(int points, TextView txtPuntos, TextView txtSet){
        String ptos;

        if (points == 7) {
            Toast.makeText(ActivityPartido.this, "No puedes poner más puntos en " + txtSet.getText().toString(), Toast.LENGTH_SHORT).show();
        } else {
            ptos = String.valueOf(++points);
            txtPuntos.setText(ptos);
        }
    }

    private void delPoints(int points, TextView txtPuntos, TextView txtSet){
        String ptos;

        if (points == 0) {
            Toast.makeText(ActivityPartido.this, "No puedes poner menos puntos en " + txtSet.getText().toString(), Toast.LENGTH_SHORT).show();
        } else {
            ptos = String.valueOf(--points);
            txtPuntos.setText(ptos);
        }
    }
}