package com.daanigp.padinfo.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.daanigp.padinfo.Entity.Player;
import com.daanigp.padinfo.R;

import java.util.ArrayList;

public class PlayerAdapter extends ArrayAdapter<Player> {
    private static final String TAG = "ActivityRanking_Fem";

    private int mResource;
    private ArrayList<Player> players;

    public PlayerAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Player> objects) {
        super(context, resource, objects);
        mResource = resource;
        players = objects;
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

        Player player = players.get(position);

        txtNumeroRanking.setText(String.valueOf(player.getRankingPosition()));
        txtPuntos.setText(player.getPoints());
        txtNombrePlayer.setText(player.getName());

        int imageResourceId = getContext().getResources().getIdentifier(player.getImageURL(), "drawable", getContext().getPackageName());
        imgPlayer.setImageResource(imageResourceId);

        return fila;
    }
}
