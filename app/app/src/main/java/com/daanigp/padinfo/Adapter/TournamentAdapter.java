package com.daanigp.padinfo.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.daanigp.padinfo.Entity.Tournament;
import com.daanigp.padinfo.R;

import java.util.ArrayList;

public class TournamentAdapter extends ArrayAdapter<Tournament> {
    private int mResource;
    private ArrayList<Tournament> tournaments;

    public TournamentAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Tournament> objects) {
        super(context, resource, objects);
        mResource = resource;
        tournaments = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(this.getContext());
        View fila = inflater.inflate(mResource, parent, false);

        TextView txtNombreTorneo = fila.findViewById(R.id.txtNombreTorneo);
        TextView txtCiudad = fila.findViewById(R.id.txtCiudad);
        ImageView imgTorneo = fila.findViewById(R.id.imgTorneo);

        Tournament tournament = tournaments.get(position);

        txtNombreTorneo.setText(tournament.getName());
        txtCiudad.setText(tournament.getCity());

        int imageResourceId = getContext().getResources().getIdentifier(tournament.getImageURL(), "drawable", getContext().getPackageName());
        imgTorneo.setImageResource(imageResourceId);

        return fila;
    }
}
