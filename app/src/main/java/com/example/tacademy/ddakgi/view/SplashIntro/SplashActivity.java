package com.example.tacademy.ddakgi.view.SplashIntro;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;

import com.example.tacademy.ddakgi.util.GpsCheckActivity;
import com.example.tacademy.ddakgi.R;

/**
 * 로딩 splash 화면
 * */
public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);

                Intent intent = new Intent(SplashActivity.this, GpsCheckActivity.class);
                startActivity(intent);
                finish();
            }
        };
        handler.sendEmptyMessageDelayed(0, 1000);
    }
}
