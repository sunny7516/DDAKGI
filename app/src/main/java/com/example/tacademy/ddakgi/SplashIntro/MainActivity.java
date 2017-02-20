package com.example.tacademy.ddakgi.SplashIntro;

/**
 * intro화면
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.tacademy.ddakgi.Adapter.CustomAdapter;
import com.example.tacademy.ddakgi.HomeActivity;
import com.example.tacademy.ddakgi.R;

public class MainActivity extends AppCompatActivity {
    ViewPager pager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        // intent 애니메이션
        overridePendingTransition(R.anim.act_slide_in_from_bottom, R.anim.act_slide_out_to_top);

        pager = (ViewPager) findViewById(R.id.viewPager);

        CustomAdapter adapter = new CustomAdapter(getLayoutInflater());
        pager.setAdapter(adapter);

        // viewpager 양쪽 항목도 조금씩 보이게 하기
        pager.setClipToPadding(false);
        pager.setPadding(40, 0, 40, 0);
        pager.setPageMargin(getResources().getDisplayMetrics().widthPixels / -30);


    }

    // 액티비티 종료 시 애니메이션
    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.act_slide_in_from_top, R.anim.act_slide_out_to_bottom);
    }

    // Main화면으로 이동하는 onClick함수(기본 HomeTab으로 이동)
    public void goHome(View view) {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }
}
