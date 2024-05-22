package com.daanigp.padinfo;

import java.util.ArrayList;

public class RankingDataSource {
    public static ArrayList<Player> rankingMasc;
    public static ArrayList<Player> rankingFem;

    public static void InitializeMasc() {
        rankingMasc = new ArrayList<>();
        rankingMasc.add(new Player(1, 1, "17.745 puntos", "Arturo Coello", String.valueOf(R.drawable.arturo_coello), "masc"));
        rankingMasc.add(new Player(2, 1, "17.745 puntos", "Agustín Tapia", String.valueOf(R.drawable.agustin_tapia), "masc"));
        rankingMasc.add(new Player(3, 3, "15.410 puntos", "Franco Stupaczuk", String.valueOf(R.drawable.franco_stupaczuk), "masc"));
        rankingMasc.add(new Player(4, 3, "15.410 puntos", "Martin Di Nenno", String.valueOf(R.drawable.martin_di_nenno), "masc"));
        rankingMasc.add(new Player(5, 5, "10.510 puntos", "Ale Galán", String.valueOf(R.drawable.ale_galan), "masc"));
    }

    public static void InitializeFem() {
        rankingFem = new ArrayList<>();
        rankingFem.add(new Player(6, 1, "19.220 puntos", "Paula Josemaría", String.valueOf(R.drawable.paula_josemaria), "fem"));
        rankingFem.add(new Player(7, 1, "19.220 puntos", "Ariana Sánchez", String.valueOf(R.drawable.ariana_sanchez), "fem"));
        rankingFem.add(new Player(8, 3, "14.890 puntos", "Gemma Triay", String.valueOf(R.drawable.gemma_tiray), "fem"));
        rankingFem.add(new Player(9, 4, "13.680 puntos", "Bea González", String.valueOf(R.drawable.bea_gonzalez), "fem"));
        rankingFem.add(new Player(10, 5, "13.360 puntos", "Delfi Brea", String.valueOf(R.drawable.delfi_brea), "fem"));
    }
}
