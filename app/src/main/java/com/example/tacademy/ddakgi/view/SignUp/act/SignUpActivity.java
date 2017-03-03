package com.example.tacademy.ddakgi.view.SignUp.act;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import com.example.tacademy.ddakgi.R;
import com.example.tacademy.ddakgi.util.ImageProc;
import com.example.tacademy.ddakgi.view.SignUp.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.kakao.auth.ApiResponseCallback;
import com.kakao.auth.AuthService;
import com.kakao.auth.network.response.AccessTokenInfoResponse;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.MeResponseCallback;
import com.kakao.usermgmt.response.model.UserProfile;
import com.kakao.util.helper.log.Logger;

/**
 * sns 연동 가입 후 프로필 등록화면으로 연결
 * sns 연동 가입 정보 firebase로 저장
 */

public class SignUpActivity extends KakaoLoginActivity {

    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;

    String nickname, kakaoID, profile;
    final int PERMISSION_READ_STATE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        // 안드로이드 6.0 (혹은 5.0) 이상부터는 개인정보보호 정책이 강화되어서
        // 민감한 API들에 대한 퍼미션을 무조건 앱 사용 시 다시 띄워서
        // 동의를 받아야 사용이 가능하도록 변경되었다.
        // 이런 정책을 피하는 방법은 하위 버전으로 컴파일하여 서비스하면 된다.
        int permissionCheck = ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_PHONE_STATE);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_PHONE_STATE}, PERMISSION_READ_STATE);
        } else {
            // you have the permission
        }
    }

    // 답변과 상관없이 응답하면 불러오는 부분
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_READ_STATE: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission granted!
                    // you may now do the action that requires this permission
                } else {
                    // permission denied
                }
                return;
            }

        }
    }

    // 세션이 열리면 호출되는 메소드를 재정의하여 사용자 정보를 포함하여 가져온다.
    @Override
    protected void redirectSignupActivity() {
        super.redirectSignupActivity();
        requestAccessTokenInfo();
    }

    // 프로필 정보 가져오기
    private void requestMe() {
        UserManagement.requestMe(new MeResponseCallback() {
            @Override
            public void onFailure(ErrorResult errorResult) {
                String message = "failed to get user info. msg=" + errorResult;
                Logger.d(message);
                onSignUp(-4);

                //redirectLoginActivity();
            }

            @Override
            public void onSessionClosed(ErrorResult errorResult) {
                // redirectLoginActivity();
                onSignUp(-5);
            }

            @Override
            public void onSuccess(UserProfile userProfile) {
                Logger.d("UserProfile : " + userProfile);
                Log.i("KAKA", "UserProfile:" + userProfile);
                // redirectMainActivity();
                nickname = userProfile.getNickname();
                profile = userProfile.getThumbnailImagePath();
                onSignUp(1);
            }

            @Override
            public void onNotSignedUp() {
                //showSignup();
                onSignUp(-6);
            }
        });
    }

    private void requestAccessTokenInfo() {
        AuthService.requestAccessTokenInfo(new ApiResponseCallback<AccessTokenInfoResponse>() {
            @Override
            public void onSessionClosed(ErrorResult errorResult) {
                // redirectLoginActivity(self);
                onSignUp(-1);
            }

            @Override
            public void onNotSignedUp() {
                // not happened
                onSignUp(-2);
            }

            @Override
            public void onFailure(ErrorResult errorResult) {
                Logger.e("failed to get access token info. msg=" + errorResult);
                onSignUp(-3);

            }

            @Override
            public void onSuccess(AccessTokenInfoResponse accessTokenInfoResponse) {
                long userId = accessTokenInfoResponse.getUserId();
                Logger.d("this access token is for userId=" + userId);
                kakaoID = "" + userId;

                long expiresInMilis = accessTokenInfoResponse.getExpiresInMillis();
                Logger.d("this access token expires after " + expiresInMilis + " milliseconds.");

                //액세스 ID를 획득 후 개인정보를 획득한다.
                requestMe();
            }
        });
    }

    // 모든 정보를 획득하였다 -> 회원가입 진행
    public void onSignUp(int type) {
        if (type < 0) {
            Toast.makeText(this,
                    "인증 오류가 발생하였습니다. 잠시 후 다시 시도해 주시기 바랍니다.",
                    Toast.LENGTH_SHORT);
            return;
        } else {
            // 로그인 유도 팝업 뜨지 않게하는 변수
            // 진행 프로그레스바
            showProgress("프로필 등록 화면으로 이동 중");
            // firebase에 회원 정보 저장
            onUserSaved(nickname, profile);
            String email = nickname+"@Kakao.com";
            // 가입 진행! > 프로필 등록화면으로 이동(카카오 기본 정보 넘겨줌)
            ImageProc.getInstance().getImageLoader(this);

            Intent intent = new Intent(this, RegisterProfileActivity.class);
            intent.putExtra("kakaoNickname", nickname);
            intent.putExtra("kakaoProfile", profile);
            startActivity(intent);
/*
            // 카카오 로그인 시 정보 넣어주기
            registerKaKao();*/

            // 프로그레스바 닫아주기
            hideProgress();
            finish();
        }
    }
/*
    public void registerKaKao() {
        Call<ResKaKaoLogin> resKaKaoLoginCall = NetSSL.getInstance().getMemberImpFactory()
                .resKaKaoLogin(new ReqKaKaoLogin(db_token));
        resKaKaoLoginCall.enqueue(new Callback<ResKaKaoLogin>() {
            @Override
            public void onResponse(Call<ResKaKaoLogin> call, Response<ResKaKaoLogin> response) {
                if(response.body().getResult() != null){
                    Log.i("RF:KaKaoLogin", "SUCCESS" + response.body().getResult());
                }else{
                    Log.i("RF:KaKaoLogin", "FAIL" + response.body().getError());
                }
            }

            @Override
            public void onFailure(Call<ResKaKaoLogin> call, Throwable t) {
                Log.i("RF:KaKaoLogin", "ERROR" + t.getMessage());
            }
        });
    }*/

    // 회원 정보 디비에 입력
    public void onUserSaved(String nickname, String profile) {
        String token = FirebaseInstanceId.getInstance().getToken();
        // 토큰이 활성화 될 때까지 못 넘어간다 -> 블럭 코드라서 앱이 먹통이 된다!
        while (token == null) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        String db_nickname = nickname;
        String db_profile = profile;
        // 회원정보 생성
        User user = new User(db_nickname, db_profile, FirebaseInstanceId.getInstance().getToken());
        // 디비 입력
        databaseReference.child("users").child(kakaoID).setValue(user)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            //로그인
                        } else {

                        }
                    }
                });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
