package com.daanigp.padinfo.DataSource;

import com.daanigp.padinfo.Entity.Player;
import com.daanigp.padinfo.R;

import java.util.ArrayList;

public class RankingDataSource {
    public static ArrayList<Player> rankingMasc;
    public static ArrayList<Player> rankingFem;

    public static void InitializeMasc() {
        rankingMasc = new ArrayList<>();
        rankingMasc.add(new Player(1, "masc", 1, "17.745 puntos", "Arturo Coello", String.valueOf(R.drawable.arturo_coello)));
        rankingMasc.add(new Player(2, "masc", 1, "17.745 puntos", "Agustín Tapia", String.valueOf(R.drawable.agustin_tapia)));
        rankingMasc.add(new Player(3, "masc", 3, "15.410 puntos", "Franco Stupaczuk", String.valueOf(R.drawable.franco_stupaczuk)));
        rankingMasc.add(new Player(4, "masc", 3, "15.410 puntos", "Martin Di Nenno", String.valueOf(R.drawable.martin_di_nenno)));
        rankingMasc.add(new Player(5, "masc", 5, "10.510 puntos", "Ale Galán", String.valueOf(R.drawable.ale_galan)));
    }

    public static void InitializeFem() {
        rankingFem = new ArrayList<>();
        rankingFem.add(new Player(6, "fem", 1, "19.220 puntos", "Paula Josemaría", String.valueOf(R.drawable.paula_josemaria)));
        rankingFem.add(new Player(7, "fem", 1, "19.220 puntos", "Ariana Sánchez", String.valueOf(R.drawable.ariana_sanchez)));
        rankingFem.add(new Player(8, "fem", 3, "14.890 puntos", "Gemma Triay", String.valueOf(R.drawable.gemma_tiray)));
        rankingFem.add(new Player(9, "fem", 4, "13.680 puntos", "Bea González", String.valueOf(R.drawable.bea_gonzalez)));
        rankingFem.add(new Player(10, "fem", 5, "13.360 puntos", "Delfi Brea", String.valueOf(R.drawable.delfi_brea)));
    }
}
