package com.example.tacademy.ddakgi.view.My;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.tacademy.ddakgi.R;
import com.example.tacademy.ddakgi.base.BaseActivity;
import com.example.tacademy.ddakgi.data.Member.MemberModel;
import com.example.tacademy.ddakgi.data.Member.ReqUpdateMemberInfo;
import com.example.tacademy.ddakgi.data.Member.ResMember;
import com.example.tacademy.ddakgi.data.NetSSL;
import com.example.tacademy.ddakgi.data.RegisterRoom.ResStringString;
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
 * 프로필 수정 페이지
 */

public class ModifyProfileActivity extends BaseActivity {
    NotoTextView modifyProfileFinish;
    EditText modifyNickname, modifyAge;
    CircleImageView modifyProfileImage;

    LinearLayout lifestyleLinearlayout;
    CheckedTextView lifestyle_q1, lifestyle_q2, lifestyle_q3, lifestyle_q4, lifestyle_q5, lifestyle_q6,
            lifestyle_q7, lifestyle_q8, lifestyle_q9, lifestyle_q10;

    SweetAlertDialog alert;

    String havePhoto = null;

    // 저장할 변수
    String nickname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_profile);

        // 자신의 정보 조회
        getMemberInfo();

        lifestyleLinearlayout = (LinearLayout) findViewById(R.id.lifestyleLinearlayout);
        modifyProfileFinish = (NotoTextView) findViewById(R.id.modifyProfileFinish);
        modifyNickname = (EditText) findViewById(R.id.modifyNickname);
        modifyAge = (EditText) findViewById(R.id.modifyAge);
        modifyProfileImage = (CircleImageView) findViewById(R.id.modifyProfileImage);

        for (int i = 1; i < CheckObj.length; i++) {
            CheckObj[i] = false;
        }
    }

    MemberModel memberModel;

    // 통신 =========================================================================================
    // 회원 정보 디폴트값 지정
    public void getMemberInfo() {
        Call<ResMember> resMemberCall = NetSSL.getInstance().getMemberImpFactory().resMember();
        resMemberCall.enqueue(new Callback<ResMember>() {
            @Override
            public void onResponse(Call<ResMember> call, Response<ResMember> response) {
                if (response.body().getResult() != null) {
                    Log.i("RF:MEModify", "SUCCESS" + response.body().getResult());
                    memberModel = response.body().getResult();
                    modifyNickname.setText(memberModel.getNickname());
                    modifyAge.setText(String.valueOf(memberModel.getAge()));
                    // 생활패턴 디폴트 값
                    setLifeStyle(memberModel.getLifestyle_q1(), memberModel.getLifestyle_q2(),
                            memberModel.getLifestyle_q3(), memberModel.getLifestyle_q4(),
                            memberModel.getLifestyle_q5(), memberModel.getLifestyle_q6(),
                            memberModel.getLifestyle_q7(), memberModel.getLifestyle_q8(),
                            memberModel.getLifestyle_q9(), memberModel.getLifestyle_q10());
                    if (memberModel.getThumbnail_image() != null) {
                        Picasso.with(getApplicationContext()).load(memberModel.getThumbnail_image()).into(modifyProfileImage);
                    } else {
                        modifyProfileImage.setImageResource(R.mipmap.profile);
                    }
                } else {
                    Log.i("RF:MEModify", "FAIL" + response.body().getError());
                }
            }

            @Override
            public void onFailure(Call<ResMember> call, Throwable t) {
                Log.i("RF:MEModify", "ERR" + t.getMessage());
            }
        });
    }

    // 생활패턴 디폴트값 세팅
    public void setLifeStyle(String lifeStyle1, String lifeStyle2, String lifeStyle3, String lifeStyle4,
                             String lifeStyle5, String lifeStyle6, String lifeStyle7, String lifeStyle8,
                             String lifeStyle9, String lifeStyle10) {
        // 넘겨받은 생활패턴 답변의 번호 앞에 num을 붙이고
        // 해당 문자열을 Tag로 갖는 checkedTextView를 찾는다.
        // 메소드를 호출해 매개변수로 넘겨줘 클릭 이벤트를 수행한다.

        // Tag 찾아서 저장
        lifestyle_q1 = (CheckedTextView) lifestyleLinearlayout.findViewWithTag("num1/" + lifeStyle1);
        lifestyle_q2 = (CheckedTextView) lifestyleLinearlayout.findViewWithTag("num2/" + lifeStyle2);
        lifestyle_q3 = (CheckedTextView) lifestyleLinearlayout.findViewWithTag("num3/" + lifeStyle3);
        lifestyle_q4 = (CheckedTextView) lifestyleLinearlayout.findViewWithTag("num4/" + lifeStyle4);
        lifestyle_q5 = (CheckedTextView) lifestyleLinearlayout.findViewWithTag("num5/" + lifeStyle5);
        lifestyle_q6 = (CheckedTextView) lifestyleLinearlayout.findViewWithTag("num6/" + lifeStyle6);
        lifestyle_q7 = (CheckedTextView) lifestyleLinearlayout.findViewWithTag("num7/" + lifeStyle7);
        lifestyle_q8 = (CheckedTextView) lifestyleLinearlayout.findViewWithTag("num8/" + lifeStyle8);
        lifestyle_q9 = (CheckedTextView) lifestyleLinearlayout.findViewWithTag("num9/" + lifeStyle9);
        lifestyle_q10 = (CheckedTextView) lifestyleLinearlayout.findViewWithTag("num10/" + lifeStyle10);

        ClickedOne(lifestyle_q1, 1);
        ClickedOne(lifestyle_q2, 2);
        ClickedOne(lifestyle_q3, 3);
        ClickedOne(lifestyle_q4, 4);
        ClickedOne(lifestyle_q5, 5);
        ClickedOne(lifestyle_q6, 6);
        ClickedOne(lifestyle_q7, 7);
        ClickedOne(lifestyle_q8, 8);
        ClickedOne(lifestyle_q9, 9);
        ClickedOne(lifestyle_q10, 10);
    }

    // 마이 정보 수정 후 디비로 업데이트
    public void updateMemberDB(String nickname) {
        Call<ResStringString> resUpdateMemberCall = NetSSL.getInstance().getMemberImpFactory()
                .resUpdateMember(new ReqUpdateMemberInfo(nickname, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, "profile_image"));
        resUpdateMemberCall.enqueue(new Callback<ResStringString>() {
            @Override
            public void onResponse(Call<ResStringString> call, Response<ResStringString> response) {
                if (response.body().getResult() != null) {
                    Log.i("RF:UpdateMe", "SUCCESS" + response.body().getResult());
                } else {
                    Log.i("RF:UpdateMe", "FAIL" + response.body().getError());
                }
            }

            @Override
            public void onFailure(Call<ResStringString> call, Throwable t) {
                Log.i("RF:UPDATE", "ERROR" + t.getMessage());
            }
        });
    }

    // 프로필 사진 재선택 ==============================================================================
    // 사진이 있는지 없는지 확인(있으면 삭제 혹은 재설정, 없으면 사진선택)
    public void modifyProfileImage(View view) {
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
                .fit().into(modifyProfileImage);

        havePhoto = path;
    }

    // 업로드된 파일 삭제
    // db에서도 삭제해야 됨
    public void deleteImage(String path) {
        modifyProfileImage.setImageResource(R.mipmap.profile);
        havePhoto = null;
        alert.dismissWithAnimation();
    }

    // 답변 후 완료 ==================================================================================
    // 항목 단일 선택하는 부분
    public void styleCheck(View view) {
        switch (view.getTag().toString().split("/")[0]) {
            case "num1":
                ClickedOne(view, 1);
                break;
            case "num2":
                ClickedOne(view, 2);
                break;
            case "num3":
                ClickedOne(view, 3);
                break;
            case "num4":
                ClickedOne(view, 4);
                break;
            case "num5":
                ClickedOne(view, 5);
                break;
            case "num6":
                ClickedOne(view, 6);
                break;
            case "num7":
                ClickedOne(view, 7);
                break;
            case "num8":
                ClickedOne(view, 8);
                break;
            case "num9":
                ClickedOne(view, 9);
                break;
            case "num10":
                ClickedOne(view, 10);
                break;
        }
    }

    // 단일 선택
    CheckedTextView clickedObject;
    CheckedTextView[] AnsObj = new CheckedTextView[11]; // 비교를 위해 해당 질문에 대한 답변 저장
    Boolean[] CheckObj = new Boolean[11];               // 문항에 대한 답변 했는지의 여부 확인

    public void ClickedOne(View view, int position) {
        clickedObject = AnsObj[position];
        // 선택된 텍스트가 없으면,
        // 처음으로 선택한 텍스트를 clickedView에 넣고 색상을 적용한다.
        if (clickedObject == null) {
            clickedObject = (CheckedTextView) view;
            AnsObj[position] = clickedObject;
            clickedObject.setBackgroundColor(ContextCompat.getColor(this, R.color.clickedObjectColor));
            setTrue(position);
        } else if (clickedObject != view) {
            // 선택된 텍스트가 저장된 텍스트와 다르면
            // 저장했던 텍스트 색을 default로 변경하고,
            // 현재 선택된 텍스트를 저장 변수에 넣는다. (포인트 색상으로 적용)
            clickedObject.setBackgroundColor(ContextCompat.getColor(this, R.color.float_transparent));
            clickedObject = (CheckedTextView) view;
            AnsObj[position] = clickedObject;
            clickedObject.setBackgroundColor(ContextCompat.getColor(this, R.color.clickedObjectColor));
            setTrue(position);
        }
    }

    // 답변한 문항 체크
    private void setTrue(int pos) {
        CheckObj[pos] = true;
        ifAllChecked();
    }

    // 모든 생활패턴의 답변이 완료되었는지 확인
    public boolean ifAllChecked() {
        for (int i = 1; i < CheckObj.length; i++) {
            boolean flag = CheckObj[i];
            if (!flag) {
                return false;
            }
        }
        finishBt();
        return true;
    }

    // 완료 버튼 이벤트
    public void finishBt() {
        // 모든 문항의 답변이 끝나면 완료 버튼을 활성화한다.
        modifyProfileFinish.setTextColor(ContextCompat.getColor(this, R.color.defaultTextColor));
        modifyProfileFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isValidate()) {
                    Toast.makeText(ModifyProfileActivity.this, "모든 문항에 답해주세요!", Toast.LENGTH_SHORT).show();
                } else {
                    // 수정된 설문패턴 DB로 저장
                    Toast.makeText(ModifyProfileActivity.this, "회원님의 정보가 수정되었습니다.", Toast.LENGTH_SHORT).show();
                    nickname = modifyNickname.getText().toString();
                    updateMemberDB(nickname);
                    // 화면 종료
                    finish();
                }
            }
        });
    }

    // 닉네임, 나이 확인
    public boolean isValidate() {
        // 닉네임 입력 확인
        if (TextUtils.isEmpty(modifyNickname.getText().toString())) {
            modifyNickname.setError("닉네임을 입력하세요!");
            return false;
        } else {
            modifyNickname.setError(null);
        }
        // 나이 입력 확인
        if (TextUtils.isEmpty(modifyAge.getText().toString())) {
            modifyAge.setError("나이를 입력하세요!");
            return false;
        } else {
            modifyAge.setError(null);
        }
        return true;
    }
}
