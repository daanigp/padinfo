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

        TextView nombresEq1 = fila.findViewById(R.id.namesPlayersTeam1);
        TextView nombresEq2 = fila.findViewById(R.id.namesPlayersTeam2);
        TextView ptsEq1 = fila.findViewById(R.id.pointsTeam1);
        TextView ptsEq2 = fila.findViewById(R.id.pointsTeam2);

        nombresEq1.setTextColor(Color.argb(255, 0 ,0, 0));
        nombresEq2.setTextColor(Color.argb(255, 0 ,0, 0));
        ptsEq1.setTextColor(Color.argb(255, 0 ,0, 0));
        ptsEq2.setTextColor(Color.argb(255, 0 ,0, 0));

        String nombreJugadoresEq1, nombreJugadoresEq2;
        nombreJugadoresEq1 = games.get(position).getNamePlayer1() + "\n" + games.get(position).getNamePlayer2();
        nombreJugadoresEq2 = games.get(position).getNamePlayer3() + "\n" + games.get(position).getNamePlayer4();

        int equipoGanador = games.get(position).getWinnerTeam();
        int puntosEquipo1 = 0, puntosEquipo2 = 0;

        if (equipoGanador == 1) {
            nombresEq1.setTextColor(Color.argb(255, 0 ,255, 0));
            nombresEq1.setTypeface(null, Typeface.BOLD);
        } else {
            nombresEq2.setTextColor(Color.argb(255, 0 ,255, 0));
            nombresEq2.setTypeface(null, Typeface.BOLD);
        }

        if (games.get(position).getSet1PointsT1() > games.get(position).getSet1PointsT2()) {
            //txtSet1Eq1.setTypeface(null, Typeface.BOLD);
            puntosEquipo1 += 1;
        } else {
            //txtSet1Eq2.setTypeface(null, Typeface.BOLD);
            puntosEquipo2 += 1;
        }

        if (games.get(position).getSet2PointsT1() > games.get(position).getSet2PointsT2()) {
            //txtSet2Eq1.setTypeface(null, Typeface.BOLD);
            puntosEquipo1 += 1;
        } else {
            //txtSet2Eq2.setTypeface(null, Typeface.BOLD);
            puntosEquipo2 += 1;
        }

        if (games.get(position).getSet3PointsT1() != games.get(position).getSet3PointsT2()) {
            if (games.get(position).getSet3PointsT1() > games.get(position).getSet3PointsT2()) {
                //txtSet3Eq1.setTypeface(null, Typeface.BOLD);
                puntosEquipo1 += 1;
            } else {
                //txtSet3Eq2.setTypeface(null, Typeface.BOLD);
                puntosEquipo2 += 1;
            }
        }

        if (puntosEquipo1 > puntosEquipo2) {
            ptsEq1.setTypeface(null, Typeface.BOLD);
        } else {
            ptsEq2.setTypeface(null, Typeface.BOLD);
        }

        nombresEq1.setText(nombreJugadoresEq1);
        nombresEq2.setText(nombreJugadoresEq2);
        ptsEq1.setText(String.valueOf(puntosEquipo1));
        ptsEq2.setText(String.valueOf(puntosEquipo2));
        /*txtSet1Eq1.setText(String.valueOf(games.get(position).getSet1PointsT1()));
        txtSet2Eq1.setText(String.valueOf(games.get(position).getSet2PointsT1()));
        txtSet3Eq1.setText(String.valueOf(games.get(position).getSet3PointsT1()));
        txtSet1Eq2.setText(String.valueOf(games.get(position).getSet1PointsT2()));
        txtSet2Eq2.setText(String.valueOf(games.get(position).getSet2PointsT2()));
        txtSet3Eq2.setText(String.valueOf(games.get(position).getSet3PointsT2()));*/

        return fila;
    }
}
