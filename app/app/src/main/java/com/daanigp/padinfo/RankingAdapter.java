package com.daanigp.padinfo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class RankingAdapter extends ArrayAdapter<RankingPlayers> {

    private int mResource;
    private ArrayList<RankingPlayers> rankingPlayers;

    public RankingAdapter(@NonNull Context context, int resource, @NonNull ArrayList<RankingPlayers> objects) {
        super(context, resource, objects);
        mResource = resource;
        rankingPlayers = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(this.getContext());
        View fila = inflater.inflate(mResource, parent, false);

        TextView txtNumeroRanking = fila.findViewById(R.id.txtNumeroRanking);
        TextView txtPuntos = fila.findViewById(R.id.txtPuntos);
        TextView txtNombrePlayer = fila.findViewById(R.id.txtNombrePlayer);
        ImageView imgPlayer = fila.findViewById(R.id.imgPlayer);

        txtNumeroRanking.setText(String.valueOf(rankingPlayers.get(position).getPosicion()));
        txtPuntos.setText(rankingPlayers.get(position).getPuntos());
        txtNombrePlayer.setText(rankingPlayers.get(position).getNombre());
        imgPlayer.setImageResource(rankingPlayers.get(position).getImagen());

        return fila;
    }
}
