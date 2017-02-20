package com.example.tacademy.ddakgi.HomeTab.activity;

import android.os.Bundle;
import android.view.View;

import com.example.tacademy.ddakgi.R;
import com.example.tacademy.ddakgi.base.BaseActivity;

public class HomeRoomDetailPageActivity extends BaseActivity {

    View life_style_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homeroom_detail_page);

        life_style_list = findViewById(R.id.life_style_list);

        if(true){
            // 로그인 시 생활패턴 보이도록
        }else{
            // 로그인 안했을 떄는 생활패턴 볼 수 없음
            life_style_list.setVisibility(View.INVISIBLE);
        }
    }
}