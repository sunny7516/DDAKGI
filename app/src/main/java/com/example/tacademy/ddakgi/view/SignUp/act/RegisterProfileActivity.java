package com.example.tacademy.ddakgi.view.SignUp.act;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.tacademy.ddakgi.R;
import com.example.tacademy.ddakgi.base.BaseActivity;
import com.example.tacademy.ddakgi.util.ImageProc;
import com.miguelbcr.ui.rx_paparazzo.RxPaparazzo;
import com.miguelbcr.ui.rx_paparazzo.entities.size.ScreenSize;
import com.squareup.picasso.Picasso;
import com.yalantis.ucrop.UCrop;

import cn.pedant.SweetAlert.SweetAlertDialog;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.example.tacademy.ddakgi.view.Home.frag.HomeTab.isLogin;

/**
 * sns 연동 가입 후 프로필 등록 화면
 */

public class RegisterProfileActivity extends BaseActivity {
    SweetAlertDialog alert;

    ImageView userProfile;
    EditText userNickname;

    String kakaoProfile, kakaoNickname;
    String havePhoto = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_profile);

        // layout에 있는 프로필 사진, 닉네임
        userProfile = (ImageView) findViewById(R.id.userProfile);
        userNickname = (EditText) findViewById(R.id.userNickname);

        // 이전 화면에서 sns 연동 로그인 시 보내준 정보
        kakaoProfile = getIntent().getStringExtra("kakaoProfile");
        kakaoNickname = getIntent().getStringExtra("kakaoNickname");

        // 이전 화면에서 전달받은 정보들을 현재 layout에 넣어준다.
        ImageProc.getInstance().drawImage(kakaoProfile, userProfile);
        userNickname.setText(kakaoNickname);
    }

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
        Picasso.with(this).load(url).into(userProfile);

        havePhoto = path;
    }

    // 업로드된 파일 삭제
    public void deleteImage(String path) {
        userProfile.setImageResource(R.mipmap.profile);
        havePhoto = null;
        alert.dismissWithAnimation();
    }

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

    public void registerMemberDB(View view){
        // 회원가입 정보 db로 전송
        // 홈탭 로그인 유도 팝업 안뜨게 설정
        isLogin = true;
    }
}
