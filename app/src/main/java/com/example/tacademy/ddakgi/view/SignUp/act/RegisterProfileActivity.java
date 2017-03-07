package com.example.tacademy.ddakgi.view.SignUp.act;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tacademy.ddakgi.R;
import com.example.tacademy.ddakgi.base.BaseActivity;
import com.example.tacademy.ddakgi.base.HomeActivity;
import com.example.tacademy.ddakgi.data.LifeStyleLogin.ReqLifeStyleLogin;
import com.example.tacademy.ddakgi.data.NetSSL;
import com.example.tacademy.ddakgi.data.RegisterRoom.ResStringString;
import com.example.tacademy.ddakgi.util.ImageProc;
import com.example.tacademy.ddakgi.util.StorageHelper;
import com.example.tacademy.ddakgi.util.U;
import com.miguelbcr.ui.rx_paparazzo.RxPaparazzo;
import com.miguelbcr.ui.rx_paparazzo.entities.size.ScreenSize;
import com.squareup.picasso.Picasso;
import com.yalantis.ucrop.UCrop;

import cn.pedant.SweetAlert.SweetAlertDialog;
import de.hdodenhof.circleimageview.CircleImageView;
import io.chooco13.NotoTextView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * sns 연동 가입 후 프로필 등록 화면
 */

public class RegisterProfileActivity extends BaseActivity {
    SweetAlertDialog alert;

    CircleImageView userProfile;
    EditText userNickname;
    EditText userAge;
    CheckBox infoCheckBox;
    CheckBox serviceCheckBox;
    CheckBox allCheckBox;
    NotoTextView registerMemberFinish;

    String kakaoProfile, kakaoNickname;
    String havePhoto = null;

    // 저장할 변수
    String dbNickname;
    int dbAge;
    int lifestyle_q1;
    int lifestyle_q2;
    int lifestyle_q3;
    int lifestyle_q4;
    int lifestyle_q5;
    int lifestyle_q6;
    int lifestyle_q7;
    int lifestyle_q8;
    int lifestyle_q9;
    int lifestyle_q10;
    String[] profile_photos = new String[3];

    // 생활 패턴 응답 완료했는지 확인하는 변수
    public static boolean isLifeStyleFinish = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_profile);
        Log.i("isLifeStyleFinish1", isLifeStyleFinish + "");

        // layout에 있는 프로필 사진, 닉네임
        userProfile = (CircleImageView) findViewById(R.id.userProfile);
        userNickname = (EditText) findViewById(R.id.userNickname);
        userAge = (EditText) findViewById(R.id.userAge);
        registerMemberFinish = (NotoTextView) findViewById(R.id.registerMemberFinish);

        // 이전 화면에서 sns 연동 로그인 시 보내준 정보
        kakaoProfile = getIntent().getStringExtra("kakaoProfile");
        kakaoNickname = getIntent().getStringExtra("kakaoNickname");

        // 이전 화면에서 전달받은 정보들을 현재 layout에 넣어준다.
        ImageProc.getInstance().drawImage(kakaoProfile, userProfile);
        userNickname.setText(kakaoNickname);

        infoCheckBox = (CheckBox) findViewById(R.id.infoCheckBox);
        serviceCheckBox = (CheckBox) findViewById(R.id.serviceCheckBox);
        allCheckBox = (CheckBox) findViewById(R.id.allCheckBox);
        allCheckBox.setChecked(allCheckBox.isChecked());
    }

    // 전체 동의 체크박스 눌렀을 때, 하위 박스 이벤트 처리
    public void allChecked(View view) {
        CheckBox all = (CheckBox) view;
        if (all.isChecked()) {
            infoCheckBox.setChecked(true);
            serviceCheckBox.setChecked(true);
        } else {
            infoCheckBox.setChecked(false);
            serviceCheckBox.setChecked(false);
        }
    }

    // 카메라 =======================================================================================
    // 사진이 있는지 없는지 확인(있으면 삭제 혹은 재설정, 없으면 사진선택)
    public void checkNull(View view) {
        if (havePhoto == null) {
            changeProfile();
        } else {
            alert =
                    new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("사진삭제")
                            .setContentText("사진을 삭제하실겁니까?")
                            .setConfirmText("YES")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    deleteImage(havePhoto);
                                }
                            })
                            .setCancelText("Change Photo")
                            .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    alert.dismissWithAnimation();
                                    havePhoto = null;
                                    changeProfile();
                                }
                            });
            alert.setCancelable(true);
            alert.show();
        }
    }

    // 사진 선택(카메라, 포토앨범)
    public void changeProfile() {
        alert =
                new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("사진선택")
                        .setContentText("사진을 선택할 방법을 고르세요!!")
                        .setConfirmText("카메라")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                onCamera();
                            }
                        })
                        .setCancelText("포토앨범")
                        .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                onGallery();
                            }
                        });
        alert.setCancelable(true);
        alert.show();
    }

    // 사진찍어 불러오기
    public void onCamera() {

        // 크롭작업을 하기 위해 옵션 설정(편집)
        UCrop.Options options = new UCrop.Options();
        options.setToolbarColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
        options.setMaxBitmapSize(1024 * 1024 * 2);    //1024*1024 = 1MB

        RxPaparazzo.takeImage(this)
                .size(new ScreenSize()) //사이즈(smallSize, ScreenSize, OriginalSize, CustomMaxSize)
                .crop(options)          // 편집
                .useInternalStorage()   //내부 저장 (안쓰면 외부 공용 공간에 앱이름으로 생성됨)
                .usingCamera()          // 카메라 사용
                .subscribeOn(Schedulers.io())   //IO
                .observeOn(AndroidSchedulers.mainThread())  // 스레드 생성
                .subscribe(response -> {    //결과 처리
                    // See response.resultCode() doc
                    // 실패 처리
                    if (response.resultCode() != RESULT_OK) {
                        //     response.targetUI().showUserCanceled();
                        return;
                    }
                    Log.i("PH", response.data());
                    loadImage(response.data());
                    // response.targetUI().loadImage(response.data());
                });
    }

    // 포토앨범에서 불러오기
    public void onGallery() {
        // 크롭작업을 하기 위해 옵션 설정(편집)
        UCrop.Options options = new UCrop.Options();
        options.setToolbarColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
        options.setMaxBitmapSize(1024 * 1024 * 2);

        RxPaparazzo.takeImage(this)
                .size(new ScreenSize())
                .crop(options)
                .useInternalStorage()
                .usingGallery()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    // See response.resultCode() doc
                    if (response.resultCode() != RESULT_OK) {
                        return;
                    }
                    Log.i("PH", response.data());
                    loadImage(response.data());
                });
    }

    // 사진 올리기
    public void loadImage(String path) {

        alert.dismissWithAnimation();

        // 이미지뷰에 이미지를 세팅
        // path : /data/user/0/com.example.tacademy.photoprocessing/files/PhotoProcessing/IMG-19012017_044702_481.jpeg
        String url = "file://" + path;

        Picasso.with(this).setLoggingEnabled(true);
        Picasso.with(this).setIndicatorsEnabled(true);
        Picasso.with(this).invalidate(url);
        Picasso.with(this).load(url)
                .fit().into(userProfile);

        havePhoto = path;
    }

    // 업로드된 파일 삭제
    public void deleteImage(String path) {
        userProfile.setImageResource(R.mipmap.profile);
        havePhoto = null;
        alert.dismissWithAnimation();
    }

    // 클릭 이벤트 ===================================================================================
    // 생활패턴 등록하러가는 클릭 이벤트
    public void goRegisterLifeStyle(View view) {
        Intent intent = new Intent(this, RegisterLifeStyleActivity.class);
        startActivity(intent);
    }

    public static String termFragment;

    // 이용약관 동의하러 가는 클릭 이벤트
    public void goTerms(View view) {
        Intent termsIntent = new Intent(this, TermsActivity.class);

        if (view.getId() == R.id.InfoTerms) {
            termFragment = "info";
        } else if (view.getId() == R.id.ServiceTerms) {
            termFragment = "service";
        }
        startActivity(termsIntent);
    }

    // 완료 버튼 눌렀을 때
    public void registerMemberDB(View view) {
        // 모든 답변 응답했는지 확인
        if (!isValidate()) {
            Toast.makeText(this, "모든 문항에 답변해주세요!", Toast.LENGTH_SHORT).show();
            return;
        } else
            // 모두 답변했으면
            // 로딩화면 보여주고 회원가입 화면 닫기
            showProgress("회원 정보를 등록중입니다");

        // 자동로그인을 위해서 Storagehelper 이용
        StorageHelper.getInstance().setBoolean(this, "AUTOLOGIN", true);

        // 회원가입 정보 db로 전송
        registerMemberData();

        hideProgress();
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        Toast.makeText(this, userNickname.getText().toString() + " 님 환영합니다.", Toast.LENGTH_SHORT).show();
        this.finish();
    }

    public void registerMemberData() {
        dbNickname = userNickname.getText().toString();
        dbAge = Integer.parseInt(userAge.getText().toString());
        int[] lifestyleAnswer = U.getInstance().getLifestyleNum();
        lifestyle_q1 = lifestyleAnswer[0];
        lifestyle_q2 = lifestyleAnswer[1];
        lifestyle_q3 = lifestyleAnswer[2];
        lifestyle_q4 = lifestyleAnswer[3];
        lifestyle_q5 = lifestyleAnswer[4];
        lifestyle_q6 = lifestyleAnswer[5];
        lifestyle_q7 = lifestyleAnswer[6];
        lifestyle_q8 = lifestyleAnswer[7];
        lifestyle_q9 = lifestyleAnswer[8];
        lifestyle_q10 = lifestyleAnswer[9];
        String profileImg = userProfile.toString();
        String thumnailImg = userProfile.toString();
        profile_photos[0] = profileImg;
        profile_photos[1] = thumnailImg;

        // 디비에 정보 저장
        Call<ResStringString> resLifeStyleLogin = NetSSL.getInstance().getMemberImpFactory()
                .resLifeStyleLogin(new ReqLifeStyleLogin(dbNickname, getUid(), dbAge, lifestyle_q1, lifestyle_q2,
                        lifestyle_q3, lifestyle_q4, lifestyle_q5, lifestyle_q6, lifestyle_q7, lifestyle_q8,
                        lifestyle_q9, lifestyle_q10, profile_photos));
        resLifeStyleLogin.enqueue(new Callback<ResStringString>() {
            @Override
            public void onResponse(Call<ResStringString> call, Response<ResStringString> response) {
                if (response.body().getResult() != null) {
                    Log.i("RF:PROFILE", "SUCCESS" + response.body().getResult());
                } else {
                    Log.i("RF:PROFILE", "FAIL" + response.body().getError());
                }
            }

            @Override
            public void onFailure(Call<ResStringString> call, Throwable t) {
                Log.i("RF", "ERR" + t.getMessage());
            }
        });
    }

    //모든 답변했는지 확인
    public boolean isValidate() {
        // 닉네임 입력 확인
        if (TextUtils.isEmpty(userNickname.getText().toString())) {
            userNickname.setError("닉네임을 입력하세요!");
            return false;
        } else {
            userNickname.setError(null);
        }
        // 나이 입력 확인
        if (TextUtils.isEmpty(userAge.getText().toString())) {
            userAge.setError("나이를 입력하세요!");
            return false;
        } else {
            userAge.setError(null);
        }
        // 생활패턴 입력 확인
        if (!isLifeStyleFinish) {
            Log.i("isLifeStyleFinish3", isLifeStyleFinish + "");
            Toast.makeText(this, "모든 생활패턴에 답해주세요!", Toast.LENGTH_SHORT).show();
            return false;
        }
        // 약관 동의 여부 확인
        if (!infoCheckBox.isChecked() && !serviceCheckBox.isChecked()) {
            Toast.makeText(this, "약관동의는 필수 사항입니다.", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}
