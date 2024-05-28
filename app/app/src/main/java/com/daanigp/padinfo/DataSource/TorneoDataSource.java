package com.daanigp.padinfo.DataSource;

import com.daanigp.padinfo.Entity.Tournament;
import com.daanigp.padinfo.R;

import java.util.ArrayList;

public class TorneoDataSource {
    public static ArrayList<Tournament> tournaments;

    public static void Initialize() {
        tournaments = new ArrayList<Tournament>();
        tournaments.add(new Tournament(1L, "Modon Abu Dhabi Master 2023", "Abu Dhabi", String.valueOf(R.drawable.wpt_abudhabi)));
        tournaments.add(new Tournament(2L, "La Rioja Open 1000", "La Rioja", String.valueOf(R.drawable.wpt_larioja_open)));
        tournaments.add(new Tournament(3L, "BTG Pactual Chile Padel Open 1000", "Santiago de Chile", String.valueOf(R.drawable.wpt_chile_open)));
        tournaments.add(new Tournament(4L, "Paraguay Padel Open 1000", "Asunción", String.valueOf(R.drawable.wpt_paraguay_open)));
        tournaments.add(new Tournament(5L, "Tau Cerámica Reus Costa Daurada Open 500", "Reus", String.valueOf(R.drawable.wpt_reus_open)));
        tournaments.add(new Tournament(6L, "Cervezas Victoria Granada Open", "Granada", String.valueOf(R.drawable.wpt_granada_open)));
        tournaments.add(new Tournament(7L, "Circus Brussels Padel Open 2023", "Bruselas", String.valueOf(R.drawable.wpt_bruselas_open)));
        tournaments.add(new Tournament(8L, "Tau Cerámica Alicante Open 500", "Alicante", String.valueOf(R.drawable.wpt_alicante_open)));
        tournaments.add(new Tournament(9L, "Estrella Damm Vigo Open 2023", "Vigo", String.valueOf(R.drawable.wpt_vigo_open)));
        tournaments.add(new Tournament(10L, "Cupra Danish Padel Open 2023", "Hillerød", String.valueOf(R.drawable.wpt_hillerod_open)));
        tournaments.add(new Tournament(11L, "Boss Vienna Padel Open 2023", "Vienna", String.valueOf(R.drawable.wpt_vienna_open)));
        tournaments.add(new Tournament(12L, "Cervezas Victoria Marbella Master 2023", "Marbella", String.valueOf(R.drawable.wpt_marbella_master)));
        tournaments.add(new Tournament(13L, "Human French Padel Open 2023", "Toulouse", String.valueOf(R.drawable.wpt_toulouse_open)));
        tournaments.add(new Tournament(14L, "Barceló Valladolid Master 2023", "Valladolid", String.valueOf(R.drawable.wpt_valladolid_master)));
        tournaments.add(new Tournament(15L, "Adeslas València Open 2023", "Valencia", String.valueOf(R.drawable.wpt_valencia_open)));
        tournaments.add(new Tournament(16L, "Cervezas Victoria Málaga Open 2023", "Málaga", String.valueOf(R.drawable.wpt_malaga_open)));
        tournaments.add(new Tournament(17L, "Aare Invest Finland Padel Open 2023", "Nokia", String.valueOf(R.drawable.wpt_nokia_open)));
        tournaments.add(new Tournament(18L, "Sixt Comunidad de Madrid Master 2023", "Madrid", String.valueOf(R.drawable.wpt_madrid_masters)));
        tournaments.add(new Tournament(19L, "Boss German Padel Open 2023", "Düsseldorf", String.valueOf(R.drawable.wpt_dusseldorf_open)));
        tournaments.add(new Tournament(20L, "Decathlon Amsterdam Open 2023", "Amsterdam", String.valueOf(R.drawable.wpt_amsterdam_open)));
        tournaments.add(new Tournament(21L, "Estrella Damm Menorca Open 2023", "Menorca", String.valueOf(R.drawable.wpt_menorca_open)));
        tournaments.add(new Tournament(22L, "Areco Malmö Padel Open 2023", "Malmö", String.valueOf(R.drawable.wpt_malmo_open)));
        tournaments.add(new Tournament(23L, "Skechers Mexico Padel Open 2023", "Ciudad de México", String.valueOf(R.drawable.wpt_ciudaddemexico_open)));
        tournaments.add(new Tournament(24L, "Boss Barcelona Master Final 2023", "Barcelona", String.valueOf(R.drawable.wpt_barcelona_masters_final)));
    }
}
