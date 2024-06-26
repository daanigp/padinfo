package com.daanigp.padinfo.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.daanigp.padinfo.Activities.ActivityEdit_Create_Game;
import com.daanigp.padinfo.Activities.ActivityList_Games;
import com.daanigp.padinfo.Adapter.GameAdapter;
import com.daanigp.padinfo.Entity.Game;
import com.daanigp.padinfo.Interface_API.IPadinfo_API;
import com.daanigp.padinfo.R;
import com.daanigp.padinfo.Retrofit.RetrofitClient;
import com.daanigp.padinfo.SharedPreferences.SharedPreferencesManager;
import com.daanigp.padinfo.Toast.Toast_Personalized;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GamesListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GamesListFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public GamesListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment GamesListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static GamesListFragment newInstance(String param1, String param2) {
        GamesListFragment fragment = new GamesListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    private static final String TAG = "ActivityList_Games";
    public static int CREATE_GAME = 3;
    public static int EDIT_GAME = 4;
    private Button btnAddGame;
    private ArrayList<Game> games = new ArrayList<>();
    private ListView lista;
    private GameAdapter gameAdapter;
    private String token;
    private long userId;
    private View message_layout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_games, container, false);

        btnAddGame = root.findViewById(R.id.btnNewGame);
        userId = SharedPreferencesManager.getInstance(getContext()).getUserId();
        token = SharedPreferencesManager.getInstance(getContext()).getToken();
        message_layout = getLayoutInflater().inflate(R.layout.toast_customized, null);

        getGames();

        lista = (ListView) root.findViewById(R.id.listaPartidos);
        gameAdapter = new GameAdapter(getContext(), R.layout.item_game, games);

        lista.setAdapter(gameAdapter);
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showToast("WINNERS -> " + games.get(position).getWinnerTeam());
            }
        });

        btnAddGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentAddGame = new Intent(getActivity(), ActivityEdit_Create_Game.class);
                startActivityForResult(intentAddGame, CREATE_GAME);
            }
        });


        return root;
    }

    private void getGames() {
        IPadinfo_API padinfoApi = RetrofitClient.getPadinfoAPI();
        Call<List<Game>> call = padinfoApi.getGamesByUserId(token, userId);

        call.enqueue(new Callback<List<Game>>() {
            @Override
            public void onResponse(Call<List<Game>> call, Response<List<Game>> response) {
                if(!response.isSuccessful()) {
                    Log.v(TAG, "No va (getPartidosFromDB) -> response -> " + response.toString());
                    showToast("Código error: " + response.code());
                    return;
                }

                List<Game> gamesAPI = response.body();

                if (gamesAPI != null) {
                    games.clear();

                    for(Game g: gamesAPI) {
                        Game game = new Game();
                        game.setId(g.getId());
                        game.setUserId(userId);
                        game.setNamePlayer1(g.getNamePlayer1());
                        game.setNamePlayer2(g.getNamePlayer2());
                        game.setNamePlayer3(g.getNamePlayer3());
                        game.setNamePlayer4(g.getNamePlayer4());
                        game.setSet1PointsT1(g.getSet1PointsT1());
                        game.setSet2PointsT1(g.getSet2PointsT1());
                        game.setSet3PointsT1(g.getSet3PointsT1());
                        game.setSet1PointsT2(g.getSet1PointsT2());
                        game.setSet2PointsT2(g.getSet2PointsT2());
                        game.setSet3PointsT2(g.getSet3PointsT2());
                        game.setWinnerTeam(g.getWinnerTeam());

                        games.add(game);
                    }

                    gameAdapter.notifyDataSetChanged();
                } else {
                    showToast("Error en la respuesta del servidor");
                }
            }

            @Override
            public void onFailure(Call<List<Game>> call, Throwable t) {
                Log.e(TAG, "Error en la llamada Retrofit - (getPartidosFromDB)", t);
                showToast("Código error: " + t.getMessage());
            }
        });
    }


    private void showToast(String message) {
        Toast_Personalized toast = new Toast_Personalized(message, getActivity(), message_layout);
        toast.CreateToast();
    }
}