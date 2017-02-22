package com.example.tacademy.ddakgi.view.Setting;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.example.tacademy.ddakgi.R;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.LogoutResponseCallback;

import static com.example.tacademy.ddakgi.view.Home.frag.HomeTab.isLogin;

public class SettingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
    }

    // 세션이 열렸을 때만 활성화
    // 자동로그인상태인지도 저장해놔야 sns종류에 따라서 로그아웃 띄워야됨
    public void onClickLogout(View view) {
        // 로그인 유도 팝업창 다시 뜨게하는 변수
        isLogin = false;

        UserManagement.requestLogout(new LogoutResponseCallback() {
            @Override
            public void onCompleteLogout() {
                //redirectLoginActivity();
                Log.i("Logout","logout!");
                // 저장된 로그인 정보도 모두 삭제
            }
        });
    }
}
