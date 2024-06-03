package com.daanigp.padinfo.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.daanigp.padinfo.Entity.Game;
import com.daanigp.padinfo.R;

import java.util.ArrayList;

public class GameAdapter extends ArrayAdapter<Game> {

    private int mResource;
    private ArrayList<Game> games;

    public GameAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Game> objects) {
        super(context, resource, objects);
        mResource = resource;
        games = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(this.getContext());
        View fila = inflater.inflate(mResource, parent, false);

        TextView txtNombreJugadoresEq1 = fila.findViewById(R.id.txtNombreJugadoresEq1);
        TextView txtNombreJugadoresEq2 = fila.findViewById(R.id.txtNombreJugadoresEq2);
        TextView txtSet1Eq1 = fila.findViewById(R.id.txtPuntosSet1Eq1);
        TextView txtSet2Eq1 = fila.findViewById(R.id.txtPuntosSet2Eq1);
        TextView txtSet3Eq1 = fila.findViewById(R.id.txtPuntosSet3Eq1);
        TextView txtSet1Eq2 = fila.findViewById(R.id.txtPuntosSet1Eq2);
        TextView txtSet2Eq2 = fila.findViewById(R.id.txtPuntosSet2Eq2);
        TextView txtSet3Eq2 = fila.findViewById(R.id.txtPuntosSet3Eq2);

        String nombreJugadoresEq1, nombreJugadoresEq2;
        nombreJugadoresEq1 = games.get(position).getNamePlayer1() + "\n" + games.get(position).getNamePlayer2();
        nombreJugadoresEq2 = games.get(position).getNamePlayer3() + "\n" + games.get(position).getNamePlayer4();

        int equipoGanador = games.get(position).getWinnerTeam();

        if (equipoGanador == 1) {
            txtNombreJugadoresEq1.setTextColor(Color.argb(255, 0 ,255, 0));
        } else {
            txtNombreJugadoresEq2.setTextColor(Color.argb(255, 0 ,255, 0));
        }

        if (games.get(position).getSet1PointsT1() > games.get(position).getSet1PointsT2()) {
            txtSet1Eq1.setTypeface(null, Typeface.BOLD);
        } else {
            txtSet1Eq2.setTypeface(null, Typeface.BOLD);
        }

        if (games.get(position).getSet2PointsT1() > games.get(position).getSet2PointsT2()) {
            txtSet2Eq1.setTypeface(null, Typeface.BOLD);
        } else {
            txtSet2Eq2.setTypeface(null, Typeface.BOLD);
        }

        if (games.get(position).getSet3PointsT1() != games.get(position).getSet3PointsT2()) {
            if (games.get(position).getSet3PointsT1() > games.get(position).getSet3PointsT2()) {
                txtSet3Eq1.setTypeface(null, Typeface.BOLD);
            } else {
                txtSet3Eq2.setTypeface(null, Typeface.BOLD);
            }
        }

        txtNombreJugadoresEq1.setText(nombreJugadoresEq1);
        txtNombreJugadoresEq2.setText(nombreJugadoresEq2);
        txtSet1Eq1.setText(String.valueOf(games.get(position).getSet1PointsT1()));
        txtSet2Eq1.setText(String.valueOf(games.get(position).getSet2PointsT1()));
        txtSet3Eq1.setText(String.valueOf(games.get(position).getSet3PointsT1()));
        txtSet1Eq2.setText(String.valueOf(games.get(position).getSet1PointsT2()));
        txtSet2Eq2.setText(String.valueOf(games.get(position).getSet2PointsT2()));
        txtSet3Eq2.setText(String.valueOf(games.get(position).getSet3PointsT2()));

        return fila;
    }
}
