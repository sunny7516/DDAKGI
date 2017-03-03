package com.example.tacademy.ddakgi.base;

import android.app.Activity;
import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import com.example.tacademy.ddakgi.util.StorageHelper;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by Tacademy on 2017-02-17.
 */

public class BaseActivity extends AppCompatActivity {
    private Activity activity;

    // 프로그레스바 선언
    private ProgressDialog pd;

    // 프로그레스바 보이기
    public void showProgress(String msg) {
        // 객체를 1회만 생성한다.
        if (pd == null) {
            // 생성한다.
            pd = new ProgressDialog(this);
            // 백키로 닫는 기능을 제거한다.
            pd.setCancelable(false);
        }
        // 원하는 메시지를 세팅한다.
        pd.setMessage(msg);
        // 화면에 띠워라
        pd.show();
    }

    // 프로그레스바 숨기기
    public void hideProgress() {
        // 닫는다 : 객체가 존재하고, 보일때만
        if (pd != null && pd.isShowing()) {
            pd.dismiss();
        }
    }

    public void back(View view) {
        finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                Toast.makeText(this, "뒤로가기 버튼 눌림", Toast.LENGTH_SHORT).show();

                final SweetAlertDialog alertDialog = new SweetAlertDialog(this);
                alertDialog.setTitle("어플리케이션 종료");
                alertDialog.setContentText("딱지를 종료하시겠습니까?");
                alertDialog.setConfirmText("그만 할래요");
                alertDialog.setCancelText("뒤로 갈래요");
                alertDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        moveTaskToBack(true);
                        finish();
                        android.os.Process.killProcess(android.os.Process.myPid());
                    }
                }).setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismissWithAnimation();
                        finish();
                    }
                });
                alertDialog.show();
        }
        return super.onKeyDown(keyCode, event);
    }

    /*

        // 내 아이디
        public String getUid() {
            if (FirebaseAuth.getInstance().getCurrentUser() == null) return null;
            return FirebaseAuth.getInstance().getCurrentUser().getUid();
        }

    */
    // 내 kakaoId 획득
    public String getKaKaoId() {
        return StorageHelper.getInstance().getString(this, "kakaoID");
    }

    // 내 닉네임 획득
    public String getNickName() {
        return StorageHelper.getInstance().getString(this, "nickname");
    }
}
