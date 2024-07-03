package com.daanigp.padinfo.Fragments;

import static androidx.core.content.ContextCompat.getSystemService;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.daanigp.padinfo.Activities.Activity_Main;
import com.daanigp.padinfo.Entity.CreateGame;
import com.daanigp.padinfo.Entity.Game;
import com.daanigp.padinfo.Entity.UpdateGame;
import com.daanigp.padinfo.Interfaces.Interface_API.IPadinfo_API;
import com.daanigp.padinfo.R;
import com.daanigp.padinfo.Retrofit.RetrofitClient;
import com.daanigp.padinfo.SharedPreferences.SharedPreferencesManager;
import com.daanigp.padinfo.Toast.Toast_Personalized;
import com.daanigp.padinfo.databinding.FragmentEditCreateGameBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Edit_CreateGameFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Edit_CreateGameFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_IDGAME = "idGame";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private long idGame;

    public Edit_CreateGameFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param idGame Game id.
     * @return A new instance of fragment Edit_CreateGameFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Edit_CreateGameFragment newInstance(long idGame) {
        Edit_CreateGameFragment fragment = new Edit_CreateGameFragment();
        Bundle args = new Bundle();
        Log.e("TAG", "ID game -> " + idGame);
        args.putLong(ARG_IDGAME, idGame);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            idGame = getArguments().getLong(ARG_IDGAME);
        }
    }

    private static final String TAG = "ActivityEdit_Create_Game";
    private final String CANAL_ID = "33";
    private FragmentEditCreateGameBinding binding;
    private boolean editar;
    private long maxIdGame;
    private String token;
    private long userId;
    private View message_layout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         //root = inflater.inflate(R.layout.fragment_edit__create, container, false);
        binding = FragmentEditCreateGameBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        userId = SharedPreferencesManager.getInstance(getActivity()).getUserId();
        token = SharedPreferencesManager.getInstance(getActivity()).getToken();
        message_layout = getLayoutInflater().inflate(R.layout.toast_customized, null);

        //setDayNight();
        // Para los partidos que se quieren editar
        Toolbar toolbar = binding.toolbarEditCreateGame;
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        if (idGame != 0) {
            //((AppCompatActivity)getActivity()).getActionBar().setTitle(R.string.titleEDIT_Game);
            toolbar.setTitle(R.string.titleEDIT_Game);
            putValuesForIdGame();
            editar = true;
        } else {
            //((AppCompatActivity)getActivity()).getActionBar().setTitle(R.string.titleCREATE_Game);
            toolbar.setTitle(R.string.titleCREATE_Game);
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

                if (!isNullOrEmpty(nomJugador1) && !isNullOrEmpty(nomJugador2) && !isNullOrEmpty(nomJugador3) && !isNullOrEmpty(nomJugador4)) {
                    if (checkSetPoints(ptosSet1Eq1, ptosSet1Eq2, "SET 1") && checkSetPoints(ptosSet2Eq1, ptosSet2Eq2, "SET 2")) {
                        if (setWonByTeam1(ptosSet1Eq1, ptosSet1Eq2)) { // 1 - 0
                            if (setWonByTeam1(ptosSet2Eq1, ptosSet2Eq2)) { // 2 - 0
                                binding.txtPtosSet3Eq1P.setText("0");
                                binding.txtPtosSet3Eq2P.setText("0");
                                equipoGanador = 1;

                                save = true;
                            } else { // 1 - 1
                                puntosSet3Eq1 = binding.txtPtosSet3Eq1P.getText().toString();
                                puntosSet3Eq2 = binding.txtPtosSet3Eq2P.getText().toString();
                                ptosSet3Eq1 = Integer.parseInt(puntosSet3Eq1);
                                ptosSet3Eq2 = Integer.parseInt(puntosSet3Eq2);

                                if (checkSetPoints(ptosSet3Eq1, ptosSet3Eq2, "SET 3")) {
                                    if (setWonByTeam1(ptosSet3Eq1, ptosSet3Eq2)) { // 2 - 1
                                        equipoGanador = 1;
                                    } else { // 1 - 2
                                        equipoGanador = 2;
                                    }

                                    save = true;
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
                                        equipoGanador = 1;
                                    } else { // 1 - 2
                                        equipoGanador = 2;
                                    }

                                    save = true;
                                }
                            } else { // 0 - 2
                                binding.txtPtosSet3Eq1P.setText("0");
                                binding.txtPtosSet3Eq2P.setText("0");
                                equipoGanador = 2;

                                save = true;
                            }
                        }

                    }

                    if (save) {
                        if (editar) {
                            UpdateGame updateGame = new UpdateGame(nomJugador1, nomJugador2, nomJugador3, nomJugador4, ptosSet1Eq1, ptosSet1Eq2, ptosSet2Eq1, ptosSet2Eq2, ptosSet3Eq1, ptosSet3Eq2, equipoGanador);
                            updateGame(updateGame);
                        } else {
                            CreateGame newGame = new CreateGame(nomJugador1, nomJugador2, nomJugador3, nomJugador4, ptosSet1Eq1, ptosSet1Eq2, ptosSet2Eq1, ptosSet2Eq2, ptosSet3Eq1, ptosSet3Eq2, equipoGanador, userId);
                            saveGame(newGame);
                        }
                    }
                } else {
                    showToast("Debes rellenar todos los campos");
                }
            }
        });

        /*binding.btnCancelarPartido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast("No has guardado nada.");
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.remove(getFragmentManager().findFragmentById(R.id.fcv_main_container)).commit();
                //getFragmentManager().beginTransaction().remove(Edit_CreateGameFragment.this).commit();
            }
        });*/
    }

    private boolean isNullOrEmpty(String str) {
        return str == null || str.isEmpty();
    }

    private void addPoints(int points, TextView txtPuntos, TextView txtSet){
        String ptos;

        if (points == 7) {
            showToast("No puedes poner más puntos en " + txtSet.getText().toString());
        } else {
            ptos = String.valueOf(++points);
            txtPuntos.setText(ptos);
        }
    }

    private void delPoints(int points, TextView txtPuntos, TextView txtSet){
        String ptos;

        if (points == 0) {
            showToast("No puedes poner menos puntos en " + txtSet.getText().toString());
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
        } else if (ptos1 == 7 && (ptos2 >= 5 && ptos2 <7)) {
            return true;
        } else if ((ptos1 >= 5 && ptos1 <7) && ptos2 == 7) {
            return true;
        } else {
            showToast("No se puede guardar el " + set +".");
            return false;
        }
    }

    private boolean setWonByTeam1(int ptos1, int ptos2){
        return ptos1 > ptos2;
    }

    private void updateGame(UpdateGame updateGame) {
        IPadinfo_API padinfoApi = RetrofitClient.getPadinfoAPI();
        Call<Game> call = padinfoApi.updateGame(token, idGame, updateGame);

        call.enqueue(new Callback<Game>() {
            @Override
            public void onResponse(Call<Game> call, Response<Game> response) {
                if(!response.isSuccessful()) {
                    Log.v(TAG, "No va (updateGame) -> response");
                    showToast("Código error: " + response.code());
                    return;
                }

                Game gameAPIupdated = response.body();

                if (gameAPIupdated != null) {
                    showToast("Partido actualizado con éxito");

                    binding.editTxtNombreJug1P.setText("");
                    binding.editTxtNombreJug2P.setText("");
                    binding.editTxtNombreJug3P.setText("");
                    binding.editTxtNombreJug4P.setText("");
                    binding.txtPtosSet1Eq1P.setText("0");
                    binding.txtPtosSet2Eq1P.setText("0");
                    binding.txtPtosSet3Eq1P.setText("0");
                    binding.txtPtosSet1Eq2P.setText("0");
                    binding.txtPtosSet2Eq2P.setText("0");
                    binding.txtPtosSet3Eq2P.setText("0");
                    /*FragmentManager manager = getActivity().getSupportFragmentManager();
                    FragmentTransaction transaction = manager.beginTransaction();
                    transaction.remove(Edit_CreateGameFragment.this).commit();*/
                } else {
                    showToast("Error en la respuesta del servidor");
                }

            }

            @Override
            public void onFailure(Call<Game> call, Throwable t) {
                Log.e(TAG, "Error en la llamada Retrofit - (updateGame)", t);
                showToast("Código error: " + t.getMessage());
            }
        });

    }

    private void saveGame(CreateGame newGame){
        IPadinfo_API padinfoApi = RetrofitClient.getPadinfoAPI();
        Call<Game> call = padinfoApi.createGame(token, newGame);

        call.enqueue(new Callback<Game>() {
            @Override
            public void onResponse(Call<Game> call, Response<Game> response) {
                if(!response.isSuccessful()) {
                    Log.v(TAG, "No va (saveGame) -> response");
                    showToast("Código error: " + response.code());
                    return;
                }

                Game gameAPIcreated = response.body();

                if (gameAPIcreated != null) {
                    idGame = gameAPIcreated.getId();

                    showToast("Partido creado con éxito");

                    showNotification(true, true, newGame);

                    binding.editTxtNombreJug1P.setText("");
                    binding.editTxtNombreJug2P.setText("");
                    binding.editTxtNombreJug3P.setText("");
                    binding.editTxtNombreJug4P.setText("");
                    binding.txtPtosSet1Eq1P.setText("0");
                    binding.txtPtosSet2Eq1P.setText("0");
                    binding.txtPtosSet3Eq1P.setText("0");
                    binding.txtPtosSet1Eq2P.setText("0");
                    binding.txtPtosSet2Eq2P.setText("0");
                    binding.txtPtosSet3Eq2P.setText("0");

                    /*FragmentManager manager = getActivity().getSupportFragmentManager();
                    FragmentTransaction transaction = manager.beginTransaction();
                    transaction.remove(Edit_CreateGameFragment.this).commit();*/
                } else {
                    showToast("Error en la respuesta del servidor");
                }
            }

            @Override
            public void onFailure(Call<Game> call, Throwable t) {
                Log.e(TAG, "Error en la llamada Retrofit - (saveGame)", t);
                showToast("Código error: " + t.getMessage());
            }
        });
    }

    private void putValuesForIdGame() {
        IPadinfo_API padinfoApi = RetrofitClient.getPadinfoAPI();
        Call<Game> call = padinfoApi.getGameByIdGame(token, idGame);

        call.enqueue(new Callback<Game>() {
            @Override
            public void onResponse(Call<Game> call, Response<Game> response) {
                if(!response.isSuccessful()) {
                    Log.v(TAG, "No va (putValuesForIdGame) -> response");
                    showToast("Código error: " + response.code());
                    return;
                }

                Game gameAPI = response.body();

                if (gameAPI != null) {
                    binding.editTxtNombreJug1P.setText(gameAPI.getNamePlayer1());
                    binding.editTxtNombreJug2P.setText(gameAPI.getNamePlayer2());
                    binding.editTxtNombreJug3P.setText(gameAPI.getNamePlayer3());
                    binding.editTxtNombreJug4P.setText(gameAPI.getNamePlayer4());
                    binding.txtPtosSet1Eq1P.setText(String.valueOf(gameAPI.getSet1PointsT1()));
                    binding.txtPtosSet2Eq1P.setText(String.valueOf(gameAPI.getSet2PointsT1()));
                    binding.txtPtosSet3Eq1P.setText(String.valueOf(gameAPI.getSet3PointsT1()));
                    binding.txtPtosSet1Eq2P.setText(String.valueOf(gameAPI.getSet1PointsT2()));
                    binding.txtPtosSet2Eq2P.setText(String.valueOf(gameAPI.getSet2PointsT2()));
                    binding.txtPtosSet3Eq2P.setText(String.valueOf(gameAPI.getSet3PointsT2()));
                } else {
                    showToast("Error en la respuesta del servidor");
                    binding.editTxtNombreJug1P.setText("vacio");
                    binding.editTxtNombreJug2P.setText("vacio");
                    binding.editTxtNombreJug3P.setText("vacio");
                    binding.editTxtNombreJug4P.setText("vacio");
                    binding.txtPtosSet1Eq1P.setText("0");
                    binding.txtPtosSet2Eq1P.setText("0");
                    binding.txtPtosSet3Eq1P.setText("0");
                    binding.txtPtosSet1Eq2P.setText("0");
                    binding.txtPtosSet2Eq2P.setText("0");
                    binding.txtPtosSet3Eq2P.setText("0");
                }
            }

            @Override
            public void onFailure(Call<Game> call, Throwable t) {
                Log.e(TAG, "Error en la llamada Retrofit - (putValuesForIdGame)", t);
                showToast("Código error: " + t.getMessage());
            }
        });
    }

    private void showNotification(boolean expandible, boolean actividad, CreateGame newGame) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getActivity(), CANAL_ID);
        builder.setSmallIcon(android.R.drawable.ic_dialog_info);

        if (expandible && actividad) {
            NotificationCompat.InboxStyle estilo = new NotificationCompat.InboxStyle();
            estilo.setBigContentTitle("Nuevo partido creado");

            String[] lines = new String[7];
            lines[0] = "Equipo 1";
            lines[1] = newGame.getNamePlayer1() + " & " + newGame.getNamePlayer2();
            lines[2] = "Equipo 2";
            lines[3] = newGame.getNamePlayer3() + " & " + newGame.getNamePlayer4();
            lines[4] = "SET 1 -> " + newGame.getSet1PointsT1() + " - " + newGame.getSet1PointsT2();
            lines[5] = "SET 2 -> " + newGame.getSet2PointsT1() + " - " + newGame.getSet2PointsT2();
            lines[6] = "SET 3 -> " + newGame.getSet3PointsT1() + " - " + newGame.getSet3PointsT2();

            for (String line: lines) {
                estilo.addLine(line);
            }

            builder.setStyle(estilo);
            builder.setAutoCancel(true);

            Intent intent = new Intent(getActivity(), Activity_Main.class);
            // Tiene que regresar al fragment del listado de partidos
            intent.putExtra("navigateToFragment", "gamesListFragment");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

            PendingIntent pendingIntent = PendingIntent.getActivity(getActivity(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

            builder.setContentIntent(pendingIntent);
        }

        NotificationManager notificationManager = (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel canal = new NotificationChannel(CANAL_ID, "Titulo del canal", NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(canal);
        }

        Notification notification = builder.build();
        notificationManager.notify(Integer.parseInt(CANAL_ID), notification);
    }
    private void showToast(String message) {
        Toast_Personalized toast = new Toast_Personalized(message, getActivity(), message_layout);
        toast.CreateToast();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null; // Liberar la referencia al binding
    }
}