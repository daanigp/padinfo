package com.daanigp.padinfo.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.daanigp.padinfo.Adapter.TournamentAdapter;
import com.daanigp.padinfo.DataSource.TournamentDataSource;
import com.daanigp.padinfo.Entity.Tournament;
import com.daanigp.padinfo.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TournamentListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TournamentListFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public TournamentListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TournamentListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TournamentListFragment newInstance(String param1, String param2) {
        TournamentListFragment fragment = new TournamentListFragment();
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

    private ArrayList<Tournament> tournaments = new ArrayList<>();
    private ListView lista;
    private TournamentAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root =  inflater.inflate(R.layout.fragment_tournament_list, container, false);

        TournamentDataSource.Initialize();

        tournaments = TournamentDataSource.tournaments;

        lista = root.findViewById(R.id.TournamentListMenu);
        adapter = new TournamentAdapter(getContext(), R.layout.item_tournament, tournaments);

        lista.setAdapter(adapter);
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getContext(), tournaments.get(position).getName(), Toast.LENGTH_SHORT).show();
            }
        });

        return root;
    }
}