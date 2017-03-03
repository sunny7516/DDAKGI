package com.example.tacademy.ddakgi.data;

import com.squareup.otto.Bus;

/**
 * Created by Tacademy on 2017-02-28.
 */

public class Ottobus {
    private static Ottobus ourInstance = new Ottobus();

    public static Ottobus getInstance() {
        return ourInstance;
    }

    private Ottobus() {
    }

    Bus maingfrag_bus = new Bus();

    public static Ottobus getOurInstance() {
        return ourInstance;
    }

    public static void setOurInstance(Ottobus ourInstance) {
        Ottobus.ourInstance = ourInstance;
    }

    public Bus getMaingfrag_bus() {
        return maingfrag_bus;
    }

    public void setMaingfrag_bus(Bus maingfrag_bus) {
        this.maingfrag_bus = maingfrag_bus;
    }
}
