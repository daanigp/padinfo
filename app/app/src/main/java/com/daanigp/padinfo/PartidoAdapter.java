package com.daanigp.padinfo;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class PartidoAdapter extends ArrayAdapter<Partido> {

    private int mResource;
    private ArrayList<Partido> partidos;

    public PartidoAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Partido> objects) {
        super(context, resource, objects);
        mResource = resource;
        partidos = objects;
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
        nombreJugadoresEq1 = partidos.get(position).getPlayer1() + "\n" + partidos.get(position).getPlayer2();
        nombreJugadoresEq2 = partidos.get(position).getPlayer3() + "\n" + partidos.get(position).getPlayer4();

        int equipoGanador = partidos.get(position).getEquipoGanador();

        if (equipoGanador == 1) {
            txtNombreJugadoresEq1.setTextColor(Color.argb(255, 0 ,255, 0));
        } else {
            txtNombreJugadoresEq2.setTextColor(Color.argb(255, 0 ,255, 0));
        }

        if (partidos.get(position).getSet1PointsT1() > partidos.get(position).getSet1PointsT2()) {
            txtSet1Eq1.setTypeface(null, Typeface.BOLD);
        } else {
            txtSet1Eq2.setTypeface(null, Typeface.BOLD);
        }

        if (partidos.get(position).getSet2PointsT1() > partidos.get(position).getSet2PointsT2()) {
            txtSet2Eq1.setTypeface(null, Typeface.BOLD);
        } else {
            txtSet2Eq2.setTypeface(null, Typeface.BOLD);
        }

        if (partidos.get(position).getSet3PointsT1() > partidos.get(position).getSet3PointsT2()) {
            txtSet3Eq1.setTypeface(null, Typeface.BOLD);
        } else {
            txtSet3Eq2.setTypeface(null, Typeface.BOLD);
        }

        txtNombreJugadoresEq1.setText(nombreJugadoresEq1);
        txtNombreJugadoresEq2.setText(nombreJugadoresEq2);
        txtSet1Eq1.setText(String.valueOf(partidos.get(position).getSet1PointsT1()));
        txtSet2Eq1.setText(String.valueOf(partidos.get(position).getSet2PointsT1()));
        txtSet3Eq1.setText(String.valueOf(partidos.get(position).getSet3PointsT1()));
        txtSet1Eq2.setText(String.valueOf(partidos.get(position).getSet1PointsT2()));
        txtSet2Eq2.setText(String.valueOf(partidos.get(position).getSet2PointsT2()));
        txtSet3Eq2.setText(String.valueOf(partidos.get(position).getSet3PointsT2()));

        return fila;
    }
}
