package com.daanigp.padinfo;

import java.util.ArrayList;

public class TorneoDataSource {

    public static ArrayList<Torneo> torneos;

    public static void Inizialize() {
        torneos = new ArrayList<Torneo>();
        torneos.add(new Torneo("MODON ABU DHABI MASTER 2023", "ABU DHABI", R.drawable.wpt_abudhabi));
        torneos.add(new Torneo("LA RIOJA OPEN 1000", "La Rioja", R.drawable.wpt_larioja_open));
        torneos.add(new Torneo("BTG Pactual Chile Padel Open 1000", "Santiago de Chile", R.drawable.wpt_chile_open));
        torneos.add(new Torneo("Paraguay Padel Open 1000", "Asunción", R.drawable.wpt_paraguay_open));
        torneos.add(new Torneo("Cervezas Victoria Granada Open", "Granada", R.drawable.wpt_granada_open));
    }
}
