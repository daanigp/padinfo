package com.daanigp.padinfo;

import java.util.ArrayList;

public class RankingDataSource {
    public static ArrayList<RankingPlayers> rankingMasc;
    public static ArrayList<RankingPlayers> rankingFem;

    public static void InitializeMasc() {
        rankingMasc = new ArrayList<>();
        rankingMasc.add(new RankingPlayers(1, "17.745 puntos", "Arturo Coello", R.drawable.arturo_coello));
        rankingMasc.add(new RankingPlayers(1, "17.745 puntos", "Agustín Tapia", R.drawable.agustin_tapia));
        rankingMasc.add(new RankingPlayers(3, "15.410 puntos", "Franco Stupaczuk", R.drawable.franco_stupaczuk));
        rankingMasc.add(new RankingPlayers(3, "15.410 puntos", "Martin Di Nenno", R.drawable.martin_di_nenno));
        rankingMasc.add(new RankingPlayers(5, "10.510 puntos", "Ale Galán", R.drawable.ale_galan));
    }

    public static void InitializeFem() {
        rankingFem = new ArrayList<>();
        rankingFem.add(new RankingPlayers(1, "19.220 puntos", "Paula Josemaría", R.drawable.paula_josemaria));
        rankingFem.add(new RankingPlayers(1, "19.220 puntos", "Ariana Sánchez", R.drawable.ariana_sanchez));
        rankingFem.add(new RankingPlayers(3, "14.890 puntos", "Gemma Triay", R.drawable.gemma_tiray));
        rankingFem.add(new RankingPlayers(4, "13.680 puntos", "Bea González", R.drawable.bea_gonzalez));
        rankingFem.add(new RankingPlayers(5, "13.360 puntos", "Delfi Brea", R.drawable.delfi_brea));
    }
}
