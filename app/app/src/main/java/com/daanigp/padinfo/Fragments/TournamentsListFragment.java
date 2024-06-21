package com.daanigp.padinfo.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.daanigp.padinfo.ActivityTournament;
import com.daanigp.padinfo.Adapter.TournamentAdapter;
import com.daanigp.padinfo.DataSource.TournamentDataSource;
import com.daanigp.padinfo.Entity.Tournament;
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
 * Use the {@link TournamentsListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TournamentsListFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public TournamentsListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TournamentsListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TournamentsListFragment newInstance(String param1, String param2) {
        TournamentsListFragment fragment = new TournamentsListFragment();
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

    private static final String TAG = "TOURNAMENTLIST_FRAGMENT";
    private ArrayList<Tournament> tournaments = new ArrayList<>();
    private ListView lista;
    private TournamentAdapter adapter;
    private View message_layout;
    private String token;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root =  inflater.inflate(R.layout.fragment_tournament_list, container, false);

        message_layout = getLayoutInflater().inflate(R.layout.toast_customized, null);
        token = SharedPreferencesManager.getInstance(getContext()).getToken();

        tournamentDataSource();
        //getTournaments();

        lista = root.findViewById(R.id.TournamentListMenu);
        adapter = new TournamentAdapter(getContext(), R.layout.item_tournament, tournaments);

        lista.setAdapter(adapter);
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent viewTournmanet = new Intent(getContext(), ActivityTournament.class);
                viewTournmanet.putExtra("idTournament", tournaments.get(position).getId());
                startActivity(viewTournmanet);
            }
        });

        return root;
    }

    private void tournamentDataSource() {
        TournamentDataSource.Initialize();

        tournaments = TournamentDataSource.tournaments;
    }

    private void getTournaments(){
        IPadinfo_API padinfoApi = RetrofitClient.getPadinfoAPI();
        Call<List<Tournament>> call = padinfoApi.getTournaments(token);

        call.enqueue(new Callback<List<Tournament>>() {
            @Override
            public void onResponse(Call<List<Tournament>> call, Response<List<Tournament>> response) {
                if(!response.isSuccessful()) {
                    Log.e(TAG, "No va getTournaments - response" + response);
                    showToast("Código error: " + response.code());
                    return;
                }

                List<Tournament> tournamentsApi = response.body();

                if (tournamentsApi != null) {
                    tournaments.clear();

                    for (Tournament t : tournamentsApi) {
                        Tournament tor = new Tournament();
                        tor.setId(t.getId());
                        tor.setName(t.getName());
                        tor.setCity(t.getCity());
                        tor.setImageURL((t.getImageURL()));
                        tournaments.add(tor);
                    }

                    adapter.notifyDataSetChanged();
                } else {
                    showToast("Error en la respuesta del servidor");
                }
            }

            @Override
            public void onFailure(Call<List<Tournament>> call, Throwable t) {
                Log.e(TAG, "Error en la llamada Retrofit - getTournaments", t);
                showToast("Código error: " + t.getMessage());
            }
        });
    }

    private void showToast(String message) {
        Toast_Personalized toast = new Toast_Personalized(message, getActivity(), message_layout);
        toast.CreateToast();
    }
}