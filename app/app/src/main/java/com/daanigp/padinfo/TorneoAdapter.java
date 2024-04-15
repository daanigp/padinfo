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

public class TorneoAdapter extends ArrayAdapter<Torneo> {
    private int mResource;
    private ArrayList<Torneo> torneos2023;

    public TorneoAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Torneo> objects) {
        super(context, resource, objects);
        mResource = resource;
        torneos2023 = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(this.getContext());
        View fila = inflater.inflate(mResource, parent, false);

        TextView txtNombreTorneo = fila.findViewById(R.id.txtNombreTorneo);
        TextView txtCiudad = fila.findViewById(R.id.txtCiudad);
        ImageView imgTorneo = fila.findViewById(R.id.imgTorneo);

        txtNombreTorneo.setText(torneos2023.get(position).getNombre());
        txtCiudad.setText(torneos2023.get(position).getCiudad());
        imgTorneo.setImageResource(torneos2023.get(position).getImagen());

        return fila;
    }
}
