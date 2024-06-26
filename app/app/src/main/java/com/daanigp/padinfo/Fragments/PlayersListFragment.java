package com.daanigp.padinfo.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.daanigp.padinfo.Activities.ActivityPlayer;
import com.daanigp.padinfo.Adapter.PlayerAdapter;
import com.daanigp.padinfo.DataSource.RankingDataSource;
import com.daanigp.padinfo.Entity.Player;
import com.daanigp.padinfo.Interface_API.IPadinfo_API;
import com.daanigp.padinfo.R;
import com.daanigp.padinfo.Retrofit.RetrofitClient;
import com.daanigp.padinfo.SharedPreferences.SharedPreferencesManager;
import com.daanigp.padinfo.Toast.Toast_Personalized;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PlayersListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PlayersListFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PlayersListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PlayersListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PlayersListFragment newInstance(String param1, String param2) {
        PlayersListFragment fragment = new PlayersListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    private static final String SELECTED_GENDER_KEY = "KEY_SELECTED_GENDER";
    private static final String TAG = "PLAYERSLIST_FRAGMENT";
    private ArrayList<Player> players = new ArrayList<>();
    private ListView lista;
    private PlayerAdapter adapter;
    private String token;
    private View message_layout;
    private String gender;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_player_list, container, false);

        message_layout = getLayoutInflater().inflate(R.layout.toast_customized, null);
        token = SharedPreferencesManager.getInstance(getContext()).getToken();

        lista = root.findViewById(R.id.PlayersListMenu);
        adapter = new PlayerAdapter(getContext(), R.layout.item_player, players);

        lista.setAdapter(adapter);
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent viewPlayer = new Intent(getActivity(), ActivityPlayer.class);
                viewPlayer.putExtra("idPlayer", players.get(position).getId());
                startActivity(viewPlayer);
            }
        });

        if (savedInstanceState == null) {
            //playersDataSource("masc");
            getPlayers("masc");
        } else {
            //playersDataSource(gender);
            gender = savedInstanceState.getString(SELECTED_GENDER_KEY);
            getPlayers(gender);
        }

        return root;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(SELECTED_GENDER_KEY, gender);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.dinamicmenu_filter_players, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.itemMale_player:
                showToast("MASCULINO");
                gender = "masc";
                getPlayers(gender);
                return true;
            case R.id.itemFemale_player:
                showToast("FEMENINO");
                gender = "fem";
                getPlayers(gender);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void playersDataSource(String g) {
        RankingDataSource.InitializeFem();

        if (g.equalsIgnoreCase("masc")) {
            players = RankingDataSource.rankingMasc;
            adapter.notifyDataSetChanged();
        } else {
            players = RankingDataSource.rankingFem;
            adapter.notifyDataSetChanged();
        }
    }

    private void getPlayers(String gender) {
        Log.v(TAG, "TOKEN -> " + token);

        IPadinfo_API padinfoApi = RetrofitClient.getPadinfoAPI();
        Call<List<Player>> call = padinfoApi.getPlayersByGender(token, gender);

        call.enqueue(new Callback<List<Player>>() {
            @Override
            public void onResponse(Call<List<Player>> call, Response<List<Player>> response) {
                if(!response.isSuccessful()) {
                    Log.v(TAG, "No va (getPlayers) -> response");
                    showToast("Código error: " + response.code());
                    return;
                }

                List<Player> playersAPI = response.body();

                if (playersAPI != null) {
                    players.clear();

                    for (Player p: playersAPI) {
                        Player player = new Player();
                        player.setId(p.getId());
                        player.setName(p.getName());
                        player.setPoints(p.getPoints());
                        player.setGender(p.getGender());
                        player.setRankingPosition(p.getRankingPosition());
                        player.setImageURL(p.getImageURL());

                        players.add(player);
                    }

                    players.sort(new Comparator<Player>() {
                        @Override
                        public int compare(Player p1, Player p2) {
                            return Integer.compare(p1.getRankingPosition(), p2.getRankingPosition());
                        }
                    });

                    adapter.notifyDataSetChanged();
                } else {
                    showToast("Error en la respuesta del servidor");
                }
            }

            @Override
            public void onFailure(Call<List<Player>> call, Throwable t) {
                Log.e(TAG, "Error en la llamada Retrofit - (getPlayers)", t);
                showToast("Código error: " + t.getMessage());
            }
        });
    }

    private void showToast(String message) {
        Toast_Personalized toast = new Toast_Personalized(message, getActivity(), message_layout);
        toast.CreateToast();
    }
}