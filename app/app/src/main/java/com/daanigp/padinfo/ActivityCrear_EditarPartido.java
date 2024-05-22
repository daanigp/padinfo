package com.daanigp.padinfo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.daanigp.padinfo.databinding.ActivityPartidoBinding;

public class ActivityCrear_EditarPartido extends AppCompatActivity {

    SQLiteDatabase db;
    ActivityPartidoBinding binding;
    boolean editar;
    int idEditGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partido);

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

        binding = ActivityPartidoBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        // Para los partidos que se quieren editar
        idEditGame = getIntent().getIntExtra("idGame", 0);
        if (idEditGame != 0) {
            putValuesForIdGame();
            editar = true;
        }


        // Añadir y borrar puntos set 1 equipo 1
        binding.btnAddPtsSet11P.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String puntosSet;
                puntosSet = binding.txtPtosSet1Eq1P.getText().toString();

                int pts = Integer.parseInt(puntosSet);

                addPoints(pts, binding.txtPtosSet1Eq1P, binding.textViewSet11P);
            }
        });

        binding.btnDelPtsSet11P.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String puntosSet;
                puntosSet = binding.txtPtosSet1Eq1P.getText().toString();

                int pts = Integer.parseInt(puntosSet);

                delPoints(pts, binding.txtPtosSet1Eq1P, binding.textViewSet11P);
            }
        });

        // Añadir y borrar puntos set 2 equipo 1
        binding.btnAddPtsSet21P.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String puntosSet;
                puntosSet = binding.txtPtosSet2Eq1P.getText().toString();

                int pts = Integer.parseInt(puntosSet);

                addPoints(pts, binding.txtPtosSet2Eq1P, binding.textViewSet21P);
            }
        });

        binding.btnDelPtsSet21P.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String puntosSet;
                puntosSet = binding.txtPtosSet2Eq1P.getText().toString();

                int pts = Integer.parseInt(puntosSet);

                delPoints(pts, binding.txtPtosSet2Eq1P, binding.textViewSet21P);
            }
        });

        // Añadir y borrar puntos set 3 equipo 1
        binding.btnAddPtsSet31P.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String puntosSet;
                puntosSet = binding.txtPtosSet3Eq1P.getText().toString();

                int pts = Integer.parseInt(puntosSet);

                addPoints(pts, binding.txtPtosSet3Eq1P, binding.textViewSet31P);
            }
        });

        binding.btnDelPtsSet31P.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String puntosSet;
                puntosSet = binding.txtPtosSet3Eq1P.getText().toString();

                int pts = Integer.parseInt(puntosSet);

                delPoints(pts, binding.txtPtosSet3Eq1P, binding.textViewSet31P);
            }
        });

        // Añadir y borrar puntos set 1 equipo 2
        binding.btnAddPtsSet12P.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String puntosSet;
                puntosSet = binding.txtPtosSet1Eq2P.getText().toString();

                int pts = Integer.parseInt(puntosSet);

                addPoints(pts, binding.txtPtosSet1Eq2P, binding.textViewSet12P);
            }
        });

        binding.btnDelPtsSet12P.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String puntosSet;
                puntosSet = binding.txtPtosSet1Eq2P.getText().toString();

                int pts = Integer.parseInt(puntosSet);

                delPoints(pts, binding.txtPtosSet1Eq2P, binding.textViewSet12P);
            }
        });

        // Añadir y borrar puntos set 2 equipo 2
        binding.btnAddPtsSet22P.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String puntosSet;
                puntosSet = binding.txtPtosSet2Eq2P.getText().toString();

                int pts = Integer.parseInt(puntosSet);

                addPoints(pts, binding.txtPtosSet2Eq2P, binding.textViewSet22P);
            }
        });

        binding.btnDelPtsSet22P.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String puntosSet;
                puntosSet = binding.txtPtosSet2Eq2P.getText().toString();

                int pts = Integer.parseInt(puntosSet);

                delPoints(pts, binding.txtPtosSet2Eq2P, binding.textViewSet22P);
            }
        });

        // Añadir y borrar puntos set 3 equipo 2
        binding.btnAddPtsSet32P.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String puntosSet;
                puntosSet = binding.txtPtosSet3Eq2P.getText().toString();

                int pts = Integer.parseInt(puntosSet);

                addPoints(pts, binding.txtPtosSet3Eq2P, binding.textViewSet32P);
            }
        });

        binding.btnDelPtsSet32P.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String puntosSet;
                puntosSet = binding.txtPtosSet3Eq2P.getText().toString();

                int pts = Integer.parseInt(puntosSet);

                delPoints(pts, binding.txtPtosSet3Eq2P, binding.textViewSet32P);
            }
        });

        // Botones de guardar y cancelar partido
        binding.btnGuardarPartido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nomJugador1, nomJugador2, nomJugador3, nomJugador4, puntosSet1Eq1, puntosSet1Eq2, puntosSet2Eq1, puntosSet2Eq2, puntosSet3Eq1, puntosSet3Eq2;
                int ptosSet1Eq1, ptosSet1Eq2, ptosSet2Eq1, ptosSet2Eq2, ptosSet3Eq1, ptosSet3Eq2, equipoGanador;
                boolean save = false;

                nomJugador1 = binding.editTxtNombreJug1P.getText().toString();
                nomJugador2 = binding.editTxtNombreJug2P.getText().toString();
                nomJugador3 = binding.editTxtNombreJug3P.getText().toString();
                nomJugador4 = binding.editTxtNombreJug4P.getText().toString();

                puntosSet1Eq1 = binding.txtPtosSet1Eq1P.getText().toString();
                puntosSet1Eq2 = binding.txtPtosSet1Eq2P.getText().toString();
                puntosSet2Eq1 = binding.txtPtosSet2Eq1P.getText().toString();
                puntosSet2Eq2 = binding.txtPtosSet2Eq2P.getText().toString();

                ptosSet1Eq1 = Integer.parseInt(puntosSet1Eq1);
                ptosSet1Eq2 = Integer.parseInt(puntosSet1Eq2);
                ptosSet2Eq1 = Integer.parseInt(puntosSet2Eq1);
                ptosSet2Eq2 = Integer.parseInt(puntosSet2Eq2);
                ptosSet3Eq1 = 0;
                ptosSet3Eq2 = 0;

                equipoGanador = 0;

                if (checkSetPoints(ptosSet1Eq1, ptosSet1Eq2, "SET 1") && checkSetPoints(ptosSet2Eq1, ptosSet2Eq2, "SET 2")) {
                    if (setWonByTeam1(ptosSet1Eq1, ptosSet1Eq2)) { // 1 - 0
                        if (setWonByTeam1(ptosSet2Eq1, ptosSet2Eq2)) { // 2 - 0
                            binding.txtPtosSet3Eq1P.setText("0");
                            binding.txtPtosSet3Eq2P.setText("0");
                            Toast.makeText(ActivityCrear_EditarPartido.this, "Equipo 1 gana el PARTIDO.", Toast.LENGTH_SHORT).show();
                            equipoGanador = 1;
                        } else { // 1 - 1
                            puntosSet3Eq1 = binding.txtPtosSet3Eq1P.getText().toString();
                            puntosSet3Eq2 = binding.txtPtosSet3Eq2P.getText().toString();
                            ptosSet3Eq1 = Integer.parseInt(puntosSet3Eq1);
                            ptosSet3Eq2 = Integer.parseInt(puntosSet3Eq2);

                            if (checkSetPoints(ptosSet3Eq1, ptosSet3Eq2, "SET 3")) {
                                if (setWonByTeam1(ptosSet3Eq1, ptosSet3Eq2)) { // 2 - 1
                                    Toast.makeText(ActivityCrear_EditarPartido.this, "Equipo 1 gana el PARTIDO.", Toast.LENGTH_SHORT).show();
                                    equipoGanador = 1;
                                } else { // 1 - 2
                                    Toast.makeText(ActivityCrear_EditarPartido.this, "Equipo 2 gana el PARTIDO.", Toast.LENGTH_SHORT).show();
                                    equipoGanador = 2;
                                }
                            }
                        }
                    } else { // 0 - 1
                        if (setWonByTeam1(ptosSet2Eq1, ptosSet2Eq2)) { // 1 - 1
                            puntosSet3Eq1 = binding.txtPtosSet3Eq1P.getText().toString();
                            puntosSet3Eq2 = binding.txtPtosSet3Eq2P.getText().toString();
                            ptosSet3Eq1 = Integer.parseInt(puntosSet3Eq1);
                            ptosSet3Eq2 = Integer.parseInt(puntosSet3Eq2);

                            if (checkSetPoints(ptosSet3Eq1, ptosSet3Eq2, "SET 3")) {
                                if (setWonByTeam1(ptosSet3Eq1, ptosSet3Eq2)) { // 2 - 1
                                    Toast.makeText(ActivityCrear_EditarPartido.this, "Equipo 1 gana el PARTIDO.", Toast.LENGTH_SHORT).show();
                                    equipoGanador = 1;
                                } else { // 1 - 2
                                    Toast.makeText(ActivityCrear_EditarPartido.this, "Equipo 2 gana el PARTIDO.", Toast.LENGTH_SHORT).show();
                                    equipoGanador = 2;
                                }
                            }
                        } else { // 0 - 2
                            binding.txtPtosSet3Eq1P.setText("0");
                            binding.txtPtosSet3Eq2P.setText("0");
                            Toast.makeText(ActivityCrear_EditarPartido.this, "Equipo 2 gana el PARTIDO.", Toast.LENGTH_SHORT).show();
                            equipoGanador = 2;
                        }
                    }

                    save = true;
                }

                if (save) {
                    Toast.makeText(getApplicationContext(), "EQUIPO 1 -> " + nomJugador1 + " + " + nomJugador2 + "\nEQUIPO 2 -> " + nomJugador3 + " + " + nomJugador4, Toast.LENGTH_SHORT).show();

                    saveDB(nomJugador1, nomJugador2, nomJugador3, nomJugador4, ptosSet1Eq1, ptosSet1Eq2, ptosSet2Eq1, ptosSet2Eq2, ptosSet3Eq1, ptosSet3Eq2, equipoGanador);
                    setResult(RESULT_OK);
                    finish();
                }
            }
        });

        binding.btnCancelarPartido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ActivityCrear_EditarPartido.this, "Has cancelado los cambios", Toast.LENGTH_SHORT).show();
                setResult(RESULT_CANCELED);
                finish();
            }
        });
    }

    private void addPoints(int points, TextView txtPuntos, TextView txtSet){
        String ptos;

        if (points == 7) {
            Toast.makeText(ActivityCrear_EditarPartido.this, "No puedes poner más puntos en " + txtSet.getText().toString(), Toast.LENGTH_SHORT).show();
        } else {
            ptos = String.valueOf(++points);
            txtPuntos.setText(ptos);
        }
    }

    private void delPoints(int points, TextView txtPuntos, TextView txtSet){
        String ptos;

        if (points == 0) {
            Toast.makeText(ActivityCrear_EditarPartido.this, "No puedes poner menos puntos en " + txtSet.getText().toString(), Toast.LENGTH_SHORT).show();
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
        } else if (ptos1 == 7 && ptos2 >= 5) {
            return true;
        } else if (ptos1 >= 5 && ptos2 == 7) {
            return true;
        } else {
            Toast.makeText(ActivityCrear_EditarPartido.this, "No se puede guardar el " + set +".", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    private boolean setWonByTeam1(int ptos1, int ptos2){
        return ptos1 > ptos2;
    }

    private void saveDB(String nomJugador1, String nomJugador2, String nomJugador3, String nomJugador4, int ptosSet1Eq1, int ptosSet1Eq2, int ptosSet2Eq1, int ptosSet2Eq2, int ptosSet3Eq1, int ptosSet3Eq2, int equipoGanador){
        String usr = userConnected();

        if (!(usr.equalsIgnoreCase("") || usr.isEmpty())) {
            int idGame;
            if (editar) {
                idGame = idEditGame;
                //Hacemos update de los datos del idGame
                ContentValues valores = new ContentValues();
                valores.put("Player1", nomJugador1);
                valores.put("Player2", nomJugador2);
                valores.put("Player3", nomJugador3);
                valores.put("Player4", nomJugador4);
                valores.put("Set1PointsT1", ptosSet1Eq1);
                valores.put("Set2PointsT1", ptosSet2Eq1);
                valores.put("Set3PointsT1", ptosSet3Eq1);
                valores.put("Set1PointsT2", ptosSet1Eq2);
                valores.put("Set2PointsT2", ptosSet2Eq2);
                valores.put("Set3PointsT2", ptosSet3Eq2);
                valores.put("EquipoGanador", equipoGanador);

                String whereClause = "IdGame = ?";
                String[] whereArgs = { String.valueOf(idGame) };
                db.update("games", valores, whereClause, whereArgs);

            } else {
                idGame = getIdGame();
                //Insertamos un nuevo partido
                db.execSQL("INSERT INTO games VALUES(" +
                        idGame + ", " +
                        "'" + usr + "', " +
                        "'" + nomJugador1 + "', " +
                        "'" + nomJugador2 + "', " +
                        "'" + nomJugador3 + "', " +
                        "'" + nomJugador4 + "', " +
                        ptosSet1Eq1 + ", " +
                        ptosSet2Eq1 + ", " +
                        ptosSet3Eq1 + ", " +
                        ptosSet1Eq2 + ", " +
                        ptosSet2Eq2 + ", " +
                        ptosSet3Eq2 + ", " +
                        equipoGanador +
                        ");"
                );
            }
        } else {
            Toast.makeText(this, "No se pueden guardar los datos debido a un error en la app", Toast.LENGTH_SHORT).show();
        }
    }

    private String userConnected(){
        Cursor c = db.rawQuery("SELECT * FROM users WHERE Isconnected = 1", null);

        if (c.moveToFirst()) {
            int indexUser = c.getColumnIndex("UserEntity");
            String user = c.getString(indexUser);
            if (user != null || !user.isEmpty()){
                c.close();
                return user;
            }
        }

        c.close();

        return "";
    }

    private int getIdGame(){
        int idGame = 0;

        Cursor c = db.rawQuery("SELECT * FROM games", null);

        if (c.getCount() == 0) {
            c.close();
            return ++idGame;
        } else if (c.moveToLast()) {
            int indexId = c.getColumnIndex("IdGame");
            int id = c.getInt(indexId);
            c.close();
            return ++id;
        }
        c.close();

        return idGame;
    }

    private void putValuesForIdGame(){
        Cursor c = db.rawQuery("SELECT * FROM games WHERE IdGame = " + idEditGame + "", null);

        if (c.getCount() > 0) {
            while(c.moveToNext()) {
                binding.editTxtNombreJug1P.setText(c.getString(2));
                binding.editTxtNombreJug2P.setText(c.getString(3));
                binding.editTxtNombreJug3P.setText(c.getString(4));
                binding.editTxtNombreJug4P.setText(c.getString(5));
                binding.txtPtosSet1Eq1P.setText(String.valueOf(c.getInt(6)));
                binding.txtPtosSet2Eq1P.setText(String.valueOf(c.getInt(7)));
                binding.txtPtosSet3Eq1P.setText(String.valueOf(c.getInt(8)));
                binding.txtPtosSet1Eq2P.setText(String.valueOf(c.getInt(9)));
                binding.txtPtosSet2Eq2P.setText(String.valueOf(c.getInt(10)));
                binding.txtPtosSet3Eq2P.setText(String.valueOf(c.getInt(11)));
            }
        }

        c.close();
    }
}