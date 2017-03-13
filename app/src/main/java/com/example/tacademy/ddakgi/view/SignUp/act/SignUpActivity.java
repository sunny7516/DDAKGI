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
import com.example.tacademy.ddakgi.base.HomeActivity;
import com.example.tacademy.ddakgi.data.Kakao.ResKaKaoLogin;
import com.example.tacademy.ddakgi.data.NetSSL;
import com.example.tacademy.ddakgi.util.ImageProc;
import com.example.tacademy.ddakgi.view.SignUp.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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

    /**
     * fb 인증으로 가입이 성공하면, fb 데이터베이스의 users 밑으로 회원 정보를 등록한다.
     */
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
                if (userProfile.getThumbnailImagePath() != null) {
                    profile = userProfile.getThumbnailImagePath();
                } else {
                    profile = null;
                }
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
                Log.i("kakaoID", kakaoID);

                long expiresInMilis = accessTokenInfoResponse.getExpiresInMillis();
                Logger.d("this access token expires after " + expiresInMilis + " milliseconds.");

                //액세스 ID를 획득 후 개인정보를 획득한다.
                requestMe();
            }
        });
    }

    // 카카오 로그인 성공함> 다음 단계 진행
    public void onSignUp(int type) {
        if (type < 0) {
            Toast.makeText(this,
                    "인증 오류가 발생하였습니다. 잠시 후 다시 시도해 주시기 바랍니다.",
                    Toast.LENGTH_SHORT);
            return;
        } else {
            if (!this.isFinishing()) {
                // 진행 프로그레스바
                showProgress("프로필 등록 화면으로 이동 중");
            }

            // 인증쪽에 데이터 저장
            String email = kakaoID + "@Kakao.com";
            String pwd = kakaoID + "password";

            // 실서버 디비에 저장
            registerKaKao(email,pwd);
        }

        // 프로그레스바 닫아주기
        hideProgress();
        finish();
    }

    // 서버에 정보 저장
    // 첫 회원, 재로그인 둘다 해야하는 작업
    public void registerKaKao(String email, String pwd) {
        Call<ResKaKaoLogin> resKaKaoLoginCall = NetSSL.getInstance().getMemberImpFactory()
                .resKaKaoLogin(accessToken);
        resKaKaoLoginCall.enqueue(new Callback<ResKaKaoLogin>() {
            @Override
            public void onResponse(Call<ResKaKaoLogin> call, Response<ResKaKaoLogin> response) {
                if (response.body().getResult() != null) {
                    Log.i("SIGNUPaccessToken", accessToken);

                    if (response.body().getResult().equals("처음 로그인하는 회원입니다. 추가정보를 입력해주세요")) {

                        Log.i("RF:KaKaoLogin", "처음 로그인" + response.body().getResult());

                        // 가입 진행! > 프로필 등록화면으로 이동(카카오 기본 정보 넘겨줌)
                        ImageProc.getInstance().getImageLoader(SignUpActivity.this);

                        // 파베 인증에 가입
                        onSignUp(email,pwd);
                        Intent intent = new Intent(SignUpActivity.this, RegisterProfileActivity.class);
                        intent.putExtra("kakaoNickname", nickname);
                        intent.putExtra("kakaoProfile", profile);
                        startActivity(intent);

                    } else if (response.body().getResult().equals("회원가입이 완료된 회원입니다. 홈화면으로 이동합니다. ")) {
                        Log.i("RF:KaKaoLogin", "회원가입이 완료" + response.body().getResult());

                        // 파베 인증 로그인
                        onLogin(email, pwd);
                        Intent intent = new Intent(SignUpActivity.this, HomeActivity.class);
                        startActivity(intent);

                    } else if (response.body().getResult().equals("회원가입이 완료되지 않은 회원입니다. 추가정보를 입력해주세요")) {
                        Log.i("RF:KaKaoLogin", "추가정보를 입력" + response.body().getResult());

                        // 파베 인증 로그인
                        onLogin(email, pwd);
                        Intent intent = new Intent(SignUpActivity.this, RegisterProfileActivity.class);
                        intent.putExtra("kakaoNickname", nickname);
                        intent.putExtra("kakaoProfile", profile);
                        startActivity(intent);
                    }
                } else {
                    Log.i("RF:KaKaoLogin", "FAIL" + response.body().getError());
                }
            }

            @Override
            public void onFailure(Call<ResKaKaoLogin> call, Throwable t) {
                Log.i("RF:KaKaoLogin", "ERROR" + t.getMessage());
            }
        });
    }

    public void onSignUp(String email, String pwd) {
        // 처음 가입하는 회원
        // firebase 인증에 회원 정보 저장
        firebaseAuth.createUserWithEmailAndPassword(email, pwd)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.i("auth", "회원가입 성공");
                            // firebase 디비에 회원 정보 저장
                            onUserSaved(email, nickname, profile);
                        } else {
                            Log.i("auth", "회원가입 실패" + task.getException().getMessage());
                        }
                    }
                });
    }

    public void onLogin(String email, String pwd) {
        firebaseAuth.signInWithEmailAndPassword(email, pwd)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        // 4. 로딩닫기
                        hideProgress();
                        if (task.isSuccessful()) {
                            Log.i("auth", "로그인 인증 SUCCESS");
                        } else {
                            // 실패
                            Log.i("auth", "로그인 인증 FAIL" + task.getException().getMessage());
                        }
                    }
                });
    }

    // 회원 정보 디비에 입력
    public void onUserSaved(String authEmail, String nickname, String profile) {
        String token = FirebaseInstanceId.getInstance().getToken();
        // 토큰이 활성화 될 때까지 못 넘어간다 -> 블럭 코드라서 앱이 먹통이 된다!
        while (token == null) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // 회원정보 생성
        User user = new User(authEmail, nickname, profile, FirebaseInstanceId.getInstance().getToken());
        // 파베 디비 입력
        databaseReference.child("users").child(getUid()).setValue(user)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.i("SUCCESS", "파베 디비 SUCESS");
                        } else {
                            Log.i("Fail", "파베 디비 FAIL");
                        }
                    }
                });
    }
}
