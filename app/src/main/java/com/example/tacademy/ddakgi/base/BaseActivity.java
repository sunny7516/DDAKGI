package com.example.tacademy.ddakgi.base;

import android.app.Activity;
import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.example.tacademy.ddakgi.util.StorageHelper;
import com.google.firebase.auth.FirebaseAuth;

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

    private long backKeyPressedTime = 0;
    Toast toast;

    // back버튼을 2초 이내에 누르면 어플을 종료시킨다.
    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() > backKeyPressedTime + 2000) {
            backKeyPressedTime = System.currentTimeMillis();
            finish();
            //toast = Toast.makeText(getApplicationContext(), "\'뒤로\'버튼을 한번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT);
            //toast.show();
            return;
        }
        if (System.currentTimeMillis() <= backKeyPressedTime + 2000) {
            this.finish();
            toast.cancel();
        }
    }

    // 나의 아이디
    public String getUid() {
        if (FirebaseAuth.getInstance().getCurrentUser() == null) return null;
        return FirebaseAuth.getInstance().getCurrentUser().getUid();
    }

    // 내 닉네임 획득
    public String getNickName() {
        return StorageHelper.getInstance().getString(this, "nickname");
    }
}
