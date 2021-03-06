package com.example.tacademy.ddakgi.view.Setting;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import com.example.tacademy.ddakgi.R;
import com.example.tacademy.ddakgi.base.BaseActivity;
import com.example.tacademy.ddakgi.data.DeleteMe.ReqDeleteMe;
import com.example.tacademy.ddakgi.data.Kakao.ResKaKaoLogout;
import com.example.tacademy.ddakgi.data.NetSSL;
import com.example.tacademy.ddakgi.data.RegisterRoom.ResStringString;
import com.example.tacademy.ddakgi.util.StorageHelper;
import com.example.tacademy.ddakgi.view.SplashIntro.MainActivity;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.LogoutResponseCallback;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
                    Toast.makeText(SettingActivity.this, "푸시알림을 켰습니다.", Toast.LENGTH_SHORT).show();
                    // switch 체크상태일 때,
                    // Push 알림 켜놓기
                } else {
                    Toast.makeText(SettingActivity.this, "푸시알림을 껐습니다.", Toast.LENGTH_SHORT).show();
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
        UserManagement.requestLogout(new LogoutResponseCallback() {
            @Override
            public void onCompleteLogout() {
                Log.i("Logout", "logout!");
                // 서버에 저장된 로그인 정보도 모두 삭제
                LogoutDb();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
        Toast.makeText(this, "로그아웃이 완료되었습니다", Toast.LENGTH_SHORT).show();
    }

    public void LogoutDb() {

        Call<ResKaKaoLogout> resKaKaoLogout = NetSSL.getInstance().getMemberImpFactory().resKaKaoLogout();
        resKaKaoLogout.enqueue(new Callback<ResKaKaoLogout>() {
            @Override
            public void onResponse(Call<ResKaKaoLogout> call, Response<ResKaKaoLogout> response) {
                if (response.body().getResult() != null) {
                    Log.i("RF:kakaoLogout", "SUCCESS" + response.body().getResult());
                    // 자동 로그인 해제
                    StorageHelper.getInstance().setBoolean(SettingActivity.this, "AUTOLOGIN", false);
                    // 인트로화면으로 돌아가기
                    Intent intent = new Intent(SettingActivity.this, MainActivity.class);
                    startActivity(intent);

                    finish();
                }
            }

            @Override
            public void onFailure(Call<ResKaKaoLogout> call, Throwable t) {
                Log.i("RF:kakaoLogout", "ERR" + t.getMessage());
            }
        });
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
                Toast.makeText(SettingActivity.this, "회원 탈퇴되었습니다.", Toast.LENGTH_SHORT).show();
                // 회원 탈퇴
                // db에서 회원 정보 삭제
                // deleteMe();
            }
        }).setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                sweetAlertDialog.dismissWithAnimation();
            }
        });
        alertDialog.show();
    }

    public void deleteMe() {
        // MyTab에서 회원아이디 넘겨받음
        int id = getIntent().getExtras().getInt("id");

        Call<ResStringString> resDeleteMe = NetSSL.getInstance().getMemberImpFactory()
                .resDeleteMe(new ReqDeleteMe(id));
        resDeleteMe.enqueue(new Callback<ResStringString>() {
            @Override
            public void onResponse(Call<ResStringString> call, Response<ResStringString> response) {
                if (response.body().getResult() != null) {
                    Log.i("RF:DeleteMe", "SUCCESS" + response.body().getResult());
                    Toast.makeText(getApplicationContext(), "회원 탈퇴가 완료되었습니다", Toast.LENGTH_SHORT).show();
                } else {
                    Log.i("RF:DeleteMe", "FAIL" + response.body().getError());
                }
            }

            @Override
            public void onFailure(Call<ResStringString> call, Throwable t) {
                Log.i("RF:DeleteMe", "ERROR" + t.getMessage());
            }
        });
    }
}
