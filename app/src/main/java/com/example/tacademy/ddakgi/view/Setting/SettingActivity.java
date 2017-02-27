package com.example.tacademy.ddakgi.view.Setting;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import com.example.tacademy.ddakgi.R;
import com.example.tacademy.ddakgi.base.BaseActivity;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.LogoutResponseCallback;

import cn.pedant.SweetAlert.SweetAlertDialog;

import static com.example.tacademy.ddakgi.view.Home.frag.HomeTab.isLogin;

public class SettingActivity extends BaseActivity {

    Switch pushSettingSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        pushSettingSwitch = (Switch) findViewById(R.id.pushSettingSwitch);
        pushSettingSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // switch 체크상태일 때,
                    // Push 알림 켜놓기
                } else {
                    // switch 체크상태 아닐 때,
                    // Push 알림 끄기
                }
            }
        });
    }

    // 세션이 열렸을 때만 활성화
    // 자동로그인상태인지도 저장해놔야 sns종류에 따라서 로그아웃 띄워야됨
    public void onClickLogout(View view) {
        final SweetAlertDialog alertDialog = new SweetAlertDialog(this);
        alertDialog.setContentText("로그아웃 하시겠습니까?");
        alertDialog.setConfirmText("로그아웃");
        alertDialog.setCancelText("아니오");
        alertDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                sweetAlertDialog.dismissWithAnimation();
                goLogout();
            }
        }).setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                sweetAlertDialog.dismissWithAnimation();
            }
        });
        alertDialog.show();
    }

    public void goLogout() {
        // 로그인 유도 팝업창 다시 뜨게하는 변수
        isLogin = false;

        UserManagement.requestLogout(new LogoutResponseCallback() {
            @Override
            public void onCompleteLogout() {
                Log.i("Logout", "logout!");
                // 저장된 로그인 정보도 모두 삭제
            }
        });
        Toast.makeText(this, "로그아웃이 완료되었습니다", Toast.LENGTH_SHORT).show();
    }

    // 회원 탈퇴 팝업 띄우기
    public void onClickGetOut(View view) {
        final SweetAlertDialog alertDialog = new SweetAlertDialog(this);
        alertDialog.setContentText("정말 탈퇴하시겠습니까?");
        alertDialog.setConfirmText("회원 탈퇴");
        alertDialog.setCancelText("아니오");
        alertDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                sweetAlertDialog.dismissWithAnimation();
                // 회원 탈퇴
                // db에서 회원 정보 삭제
            }
        }).setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                sweetAlertDialog.dismissWithAnimation();
            }
        });
        alertDialog.show();
    }
}
