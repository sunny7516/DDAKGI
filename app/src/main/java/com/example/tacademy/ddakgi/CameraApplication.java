package com.example.tacademy.ddakgi;

import android.app.Application;

import com.miguelbcr.ui.rx_paparazzo.RxPaparazzo;

/**
 * Created by Tacademy on 2017-02-13.
 */

public class CameraApplication extends Application {
    @Override public void onCreate(){
        super.onCreate();
        RxPaparazzo.register(this);
    }
}
