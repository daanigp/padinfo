package com.daanigp.padinfo;

import java.util.ArrayList;

public class TorneoDataSource {
    public static ArrayList<Torneo> torneos;

    public static void Initialize() {
        torneos = new ArrayList<Torneo>();
        torneos.add(new Torneo("Modon Abu Dhabi Master 2023", "Abu Dhabi", R.drawable.wpt_abudhabi));
        torneos.add(new Torneo("La Rioja Open 1000", "La Rioja", R.drawable.wpt_larioja_open));
        torneos.add(new Torneo("BTG Pactual Chile Padel Open 1000", "Santiago de Chile", R.drawable.wpt_chile_open));
        torneos.add(new Torneo("Paraguay Padel Open 1000", "Asunción", R.drawable.wpt_paraguay_open));
        torneos.add(new Torneo("Tau Cerámica Reus Costa Daurada Open 500", "Reus", R.drawable.wpt_reus_open));
        torneos.add(new Torneo("Cervezas Victoria Granada Open", "Granada", R.drawable.wpt_granada_open));
        torneos.add(new Torneo("Circus Brussels Padel Open 2023", "Bruselas", R.drawable.wpt_bruselas_open));
        torneos.add(new Torneo("Tau Cerámica Alicante Open 500", "Alicante", R.drawable.wpt_alicante_open));
        torneos.add(new Torneo("Estrella Damm Vigo Open 2023", "Vigo", R.drawable.wpt_vigo_open));
        torneos.add(new Torneo("Cupra Danish Padel Open 2023", "Hillerød", R.drawable.wpt_hillerod_open));
        torneos.add(new Torneo("Boss Vienna Padel Open 2023", "Vienna", R.drawable.wpt_vienna_open));
        torneos.add(new Torneo("Cervezas Victoria Marbella Master 2023", "Marbella", R.drawable.wpt_marbella_master));
        torneos.add(new Torneo("Human French Padel Open 2023", "Toulouse", R.drawable.wpt_toulouse_open));
        torneos.add(new Torneo("Barceló Valladolid Master 2023", "Valladolid", R.drawable.wpt_valladolid_master));
        torneos.add(new Torneo("Adeslas València Open 2023", "Valencia", R.drawable.wpt_valencia_open));
        torneos.add(new Torneo("Cervezas Victoria Málaga Open 2023", "Málaga", R.drawable.wpt_malaga_open));
        torneos.add(new Torneo("Aare Invest Finland Padel Open 2023", "Nokia", R.drawable.wpt_nokia_open));
        torneos.add(new Torneo("Sixt Comunidad de Madrid Master 2023", "Madrid", R.drawable.wpt_madrid_masters));
        torneos.add(new Torneo("Boss German Padel Open 2023", "Düsseldorf", R.drawable.wpt_dusseldorf_open));
        torneos.add(new Torneo("Decathlon Amsterdam Open 2023", "Amsterdam", R.drawable.wpt_amsterdam_open));
        torneos.add(new Torneo("Estrella Damm Menorca Open 2023", "Menorca", R.drawable.wpt_menorca_open));
        torneos.add(new Torneo("Areco Malmö Padel Open 2023", "Malmö", R.drawable.wpt_malmo_open));
        torneos.add(new Torneo("Skechers Mexico Padel Open 2023", "Ciudad de México", R.drawable.wpt_ciudaddemexico_open));
        torneos.add(new Torneo("Boss Barcelona Master Final 2023", "Barcelona", R.drawable.wpt_barcelona_masters_final));
    }
}
