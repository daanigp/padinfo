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

public class PlayerAdapter extends ArrayAdapter<Player> {

    private int mResource;
    private ArrayList<Player> rankingPlayers;

    public PlayerAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Player> objects) {
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

        Player player = rankingPlayers.get(position);

        txtNumeroRanking.setText(String.valueOf(player.getPosicion()));
        txtPuntos.setText(player.getPuntos());
        txtNombrePlayer.setText(player.getNombre());

        int imageResourceId = getContext().getResources().getIdentifier(player.getImagen(), "drawable", getContext().getPackageName());
        imgPlayer.setImageResource(imageResourceId);

        return fila;
    }
}
