package com.example.tacademy.ddakgi.SignUp;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;

import com.example.tacademy.ddakgi.R;
import com.kakao.auth.ISessionCallback;
import com.kakao.auth.Session;
import com.kakao.util.exception.KakaoException;
import com.kakao.util.helper.log.Logger;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SignUpActivity extends AppCompatActivity {
    private SessionCallback callback;

    // 액티비티가 떴을 때 한번 로그인하면 자동로그인하도록 설정하는 부분
    private class SessionCallback implements ISessionCallback {

        @Override
        public void onSessionOpened() {
            redirectSignupActivity();
        }

        @Override
        public void onSessionOpenFailed(KakaoException exception) {
            if (exception != null) {
                Logger.e(exception);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        // 액티비티가 생성되면 세션에 아답터를 등록하고 체킹에 들어간다.
        // 카카오 로그인에 대한 세션 체킹을 위한 아답터 생성
        callback = new SessionCallback();
        // 세션 객체에 등록
        Session.getCurrentSession().addCallback(callback);
        // 세션 체킹
        Session.getCurrentSession().checkAndImplicitOpen();

        //해시키 구하기
        try {
            PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 액티비티가 소멸되면 세션에 등록된 아답터를 제거한다.
        Session.getCurrentSession().removeCallback(callback);
    }

    // 로그인 수행 후 돌아왔을 때 호출된다 (데이터를 가지고)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data != null)
            Log.i("KAKAO", "Intent:" + data.toString());

        if (Session.getCurrentSession().handleActivityResult(requestCode, resultCode, data)) {
            return;
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    // 간단하게 로그인 하는 액티비티로 이동
    protected void redirectSignupActivity() {
        /*
        final Intent intent = new Intent(this, SampleSignupActivity.class);
        startActivity(intent);
        */
        finish();
    }
}
