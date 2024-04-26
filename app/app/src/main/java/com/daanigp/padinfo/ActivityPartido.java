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

        // Añadir y borrar puntos set 1 equipo 1
        binding.btnAddPtsSet11P.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String puntosSet, ptos;
                puntosSet = binding.txtPtosSet1Eq1P.getText().toString();

                int pts = Integer.parseInt(puntosSet);

                addPoints(pts, binding.txtPtosSet1Eq1P, binding.textViewSet11P);
            }
        });

        binding.btnDelPtsSet11P.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String puntosSet, ptos;
                puntosSet = binding.txtPtosSet1Eq1P.getText().toString();

                int pts = Integer.parseInt(puntosSet);

                delPoints(pts, binding.txtPtosSet1Eq1P, binding.textViewSet11P);
            }
        });

        // Añadir y borrar puntos set 2 equipo 1
        binding.btnAddPtsSet21P.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String puntosSet, ptos;
                puntosSet = binding.txtPtosSet2Eq1P.getText().toString();

                int pts = Integer.parseInt(puntosSet);

                addPoints(pts, binding.txtPtosSet2Eq1P, binding.textViewSet21P);
            }
        });

        binding.btnDelPtsSet21P.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String puntosSet, ptos;
                puntosSet = binding.txtPtosSet2Eq1P.getText().toString();

                int pts = Integer.parseInt(puntosSet);

                delPoints(pts, binding.txtPtosSet2Eq1P, binding.textViewSet21P);
            }
        });

        // Añadir y borrar puntos set 3 equipo 1
        binding.btnAddPtsSet31P.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String puntosSet, ptos;
                puntosSet = binding.txtPtosSet3Eq1P.getText().toString();

                int pts = Integer.parseInt(puntosSet);

                addPoints(pts, binding.txtPtosSet3Eq1P, binding.textViewSet31P);
            }
        });

        binding.btnDelPtsSet31P.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String puntosSet, ptos;
                puntosSet = binding.txtPtosSet3Eq1P.getText().toString();

                int pts = Integer.parseInt(puntosSet);

                delPoints(pts, binding.txtPtosSet3Eq1P, binding.textViewSet31P);
            }
        });

        // Añadir y borrar puntos set 1 equipo 2
        binding.btnAddPtsSet12P.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String puntosSet, ptos;
                puntosSet = binding.txtPtosSet1Eq2P.getText().toString();

                int pts = Integer.parseInt(puntosSet);

                addPoints(pts, binding.txtPtosSet1Eq2P, binding.textViewSet12P);
            }
        });

        binding.btnDelPtsSet12P.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String puntosSet, ptos;
                puntosSet = binding.txtPtosSet1Eq2P.getText().toString();

                int pts = Integer.parseInt(puntosSet);

                delPoints(pts, binding.txtPtosSet1Eq2P, binding.textViewSet12P);
            }
        });

        // Añadir y borrar puntos set 2 equipo 2
        binding.btnAddPtsSet22P.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String puntosSet, ptos;
                puntosSet = binding.txtPtosSet2Eq2P.getText().toString();

                int pts = Integer.parseInt(puntosSet);

                addPoints(pts, binding.txtPtosSet2Eq2P, binding.textViewSet22P);
            }
        });

        binding.btnDelPtsSet22P.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String puntosSet, ptos;
                puntosSet = binding.txtPtosSet2Eq2P.getText().toString();

                int pts = Integer.parseInt(puntosSet);

                delPoints(pts, binding.txtPtosSet2Eq2P, binding.textViewSet22P);
            }
        });

        // Añadir y borrar puntos set 3 equipo 2
        binding.btnAddPtsSet32P.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String puntosSet, ptos;
                puntosSet = binding.txtPtosSet3Eq2P.getText().toString();

                int pts = Integer.parseInt(puntosSet);

                addPoints(pts, binding.txtPtosSet3Eq2P, binding.textViewSet32P);
            }
        });

        binding.btnDelPtsSet32P.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String puntosSet, ptos;
                puntosSet = binding.txtPtosSet3Eq2P.getText().toString();

                int pts = Integer.parseInt(puntosSet);

                delPoints(pts, binding.txtPtosSet3Eq2P, binding.textViewSet32P);
            }
        });

        // Botones de guardar y cancelar partido
        binding.btnGuardarPartido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(ActivityPartido.this, "Partido guardado", Toast.LENGTH_SHORT).show();

                String puntosSet1Eq1, puntosSet1Eq2, puntosSet2Eq1, puntosSet2Eq2, puntosSet3Eq1, puntosSet3Eq2;
                int ptosSet1Eq1, ptosSet1Eq2, ptosSet2Eq1, ptosSet2Eq2, ptosSet3Eq1, ptosSet3Eq2;

                puntosSet1Eq1 = binding.txtPtosSet1Eq1P.getText().toString();
                puntosSet1Eq2 = binding.txtPtosSet1Eq2P.getText().toString();
                puntosSet2Eq1 = binding.txtPtosSet2Eq1P.getText().toString();
                puntosSet2Eq2 = binding.txtPtosSet2Eq2P.getText().toString();
                puntosSet3Eq1 = binding.txtPtosSet3Eq1P.getText().toString();
                puntosSet3Eq2 = binding.txtPtosSet3Eq2P.getText().toString();

                ptosSet1Eq1 = Integer.parseInt(puntosSet1Eq1);
                ptosSet1Eq2 = Integer.parseInt(puntosSet1Eq2);
                ptosSet2Eq1 = Integer.parseInt(puntosSet2Eq1);
                ptosSet2Eq2 = Integer.parseInt(puntosSet2Eq2);
                ptosSet3Eq1 = Integer.parseInt(puntosSet3Eq1);
                ptosSet3Eq2 = Integer.parseInt(puntosSet3Eq2);

                if (checkSetPoints(ptosSet1Eq1, ptosSet1Eq2, "SET 1") && checkSetPoints(ptosSet2Eq1, ptosSet2Eq2, "SET 2") && checkSetPoints(ptosSet3Eq1, ptosSet3Eq2, "SET 3")) {
                    if (setWonByTeam1(ptosSet1Eq1, ptosSet1Eq2)) { // 1 - 0
                        if (setWonByTeam1(ptosSet2Eq1, ptosSet2Eq2)) { // 2 - 0
                            Toast.makeText(ActivityPartido.this, "Equipo 1 gana el PARTIDO.", Toast.LENGTH_SHORT).show();
                        } else { // 1 - 1
                            if (setWonByTeam1(ptosSet3Eq1, ptosSet3Eq2)) { // 2 - 1
                                Toast.makeText(ActivityPartido.this, "Equipo 1 gana el PARTIDO.", Toast.LENGTH_SHORT).show();
                            } else { // 1 - 2
                                Toast.makeText(ActivityPartido.this, "Equipo 2 gana el PARTIDO.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    } else { // 0 - 1
                        if (setWonByTeam1(ptosSet2Eq1, ptosSet2Eq2)) { // 1 - 1
                            if (setWonByTeam1(ptosSet3Eq1, ptosSet3Eq2)) { // 2 - 1
                                Toast.makeText(ActivityPartido.this, "Equipo 1 gana el PARTIDO.", Toast.LENGTH_SHORT).show();
                            } else { // 1 - 2
                                Toast.makeText(ActivityPartido.this, "Equipo 2 gana el PARTIDO.", Toast.LENGTH_SHORT).show();
                            }
                        } else { // 0 - 2
                            Toast.makeText(ActivityPartido.this, "Equipo 2 gana el PARTIDO.", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                
            }
        });

        binding.btnCancelarPartido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ActivityPartido.this, "Has cancelado los cambios", Toast.LENGTH_SHORT).show();
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

    private boolean checkSetPoints(int ptos1, int ptos2, String set){
        if (ptos1 == 6 && ptos2 < 6) {
            return true;
        } else if (ptos1 < 6 && ptos2 == 6) {
            return true;
        } else if (ptos1 == 7 && ptos2 == 6) {
            return true;
        } else if (ptos1 == 6 && ptos2 == 7) {
            return true;
        } else {
            Toast.makeText(ActivityPartido.this, "No se puede guardar el " + set +".", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    private boolean setWonByTeam1(int ptos1, int ptos2){
        return ptos1 > ptos2;
    }
}