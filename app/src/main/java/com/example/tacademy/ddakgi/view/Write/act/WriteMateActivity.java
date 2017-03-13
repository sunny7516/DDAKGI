package com.example.tacademy.ddakgi.view.Write.act;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.tacademy.ddakgi.R;
import com.example.tacademy.ddakgi.data.ModifyPosting.DefaultModel;
import com.example.tacademy.ddakgi.data.ModifyPosting.ReqModifyMate;
import com.example.tacademy.ddakgi.data.ModifyPosting.ResModifyDefault;
import com.example.tacademy.ddakgi.data.NetSSL;
import com.example.tacademy.ddakgi.data.RegisterMate.ReqRegisterMate;
import com.example.tacademy.ddakgi.data.RegisterRoom.ResStringString;
import com.example.tacademy.ddakgi.util.DatePickerFragment;

import io.chooco13.NotoTextView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class WriteMateActivity extends AppCompatActivity {
    LinearLayout prefroomType, mateextraInfo;
    DefaultModel defaultModel;

    Boolean roomflag = true;
    Boolean extraflag = true;

    // 받아올 값
    EditText registerMateTitle, registerMateDeposit, registerMateRent, registerMateDescription;
    EditText registerMateExtraQueOne, registerMateExtraQueTwo, registerMateExtraQueThree;
    Button writepreflocateBt, prefDate;

    // 저장할 변수
    String title, local1, local2, local3, available_date, description;
    String extra_q1, extra_q2, extra_q3;
    int room_type, deposit, rent;

    int isModify;
    int roommate_id;
    public static Button writeMatelocateBt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_mate);

        if (getIntent().getExtras() != null) {
            isModify = getIntent().getExtras().getInt("modify");
            roommate_id = getIntent().getExtras().getInt("roommate_id");

            // 수정 통신 호출
            if (isModify == 0) {
                // 수정화면이면 디폴트값 지정
                modifyRetrofit(roommate_id);
            }
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.writeMateTool);
        this.setSupportActionBar(toolbar);
        // 펼쳐지는 메뉴 visibility 설정하기 위해서
        prefroomType = (LinearLayout) findViewById(R.id.prefroomType);
        mateextraInfo = (LinearLayout) findViewById(R.id.mateextraInfo);

        registerMateTitle = (EditText) findViewById(R.id.registerMateTitle);
        registerMateDeposit = (EditText) findViewById(R.id.registerMateDeposit);
        registerMateRent = (EditText) findViewById(R.id.registerMateRent);
        registerMateDescription = (EditText) findViewById(R.id.registerMateDescription);

        registerMateExtraQueOne = (EditText) findViewById(R.id.registerMateExtraQueOne);

        prefDate = (Button) findViewById(R.id.prefDate);
        writepreflocateBt = (Button) findViewById(R.id.writepreflocateBt);

        registerMateTitle.setTextColor(ContextCompat.getColor(this, R.color.subTextColor));
        // 빈 공간 (새롭게 생길 부분)
        mateextraInfo = (LinearLayout) findViewById(R.id.ExtraMateQuelinear);
    }

    // 통신 =========================================================================================

    // 글 조회 통신 > 디폴트 값 지정
    public void modifyRetrofit(int roommate_id) {
        Call<ResModifyDefault> resModifyDefaultCall = NetSSL.getInstance().getMemberImpFactory().resModifyDefault(roommate_id);
        resModifyDefaultCall.enqueue(new Callback<ResModifyDefault>() {
            @Override
            public void onResponse(Call<ResModifyDefault> call, Response<ResModifyDefault> response) {
                if (response.body().getResult() != null) {
                    Log.i("RF:modifyDefault", "SUCCESS" + response.body().getResult());
                    defaultModel = response.body().getResult();
                    setMateDefault(defaultModel);
                } else {
                    Log.i("RF:modifyDefault", "FAIL" + response.body().getError());
                }
            }

            @Override
            public void onFailure(Call<ResModifyDefault> call, Throwable t) {
                Log.i("RF:modifyDefault", "ERR" + t.getMessage());
            }
        });
    }

    NotoTextView defaultType;

    // 통신 결과값을 수정 디폴트값으로 지정
    public void setMateDefault(DefaultModel defaultModel) {
        registerMateTitle.setText(defaultModel.getTitle());
        writepreflocateBt.setText(defaultModel.getAddress());

        // 유형 디폴트값
        switch (defaultModel.getRoom_type()) {
            case "원룸":
                defaultType = (NotoTextView) findViewById(R.id.typeOne);
                preftypeChecked(defaultType);
                break;
            case "투룸":
                defaultType = (NotoTextView) findViewById(R.id.typeTwo);
                preftypeChecked(defaultType);
                break;
            case "주택":
                defaultType = (NotoTextView) findViewById(R.id.typeHouse);
                preftypeChecked(defaultType);
                break;
            case "오피스텔":
                defaultType = (NotoTextView) findViewById(R.id.typeOffice);
                preftypeChecked(defaultType);
                break;
            case "아파트":
                defaultType = (NotoTextView) findViewById(R.id.typeApart);
                preftypeChecked(defaultType);
                break;
        }

        registerMateDeposit.setText(String.valueOf(defaultModel.getDeposit()));
        registerMateRent.setText(String.valueOf(defaultModel.getRent()));
        prefDate.setText(defaultModel.getAvailable_date().split("T")[0]);
        registerMateDescription.setText(defaultModel.getDescription());

        for (int i = 0; i < 3; i++) {
            plusExtra(null);
        }

        if (registerMateExtraQueTwo != null) {
            registerMateExtraQueTwo.setText(defaultModel.getExtra_q2());
        }
        if (registerMateExtraQueThree != null) {
            registerMateExtraQueThree.setText(defaultModel.getExtra_q3());
        }
    }

    // 완료 버튼 > 글 등록 통신
    public void registerMate(View view) {
        // 글 등록 통신
        // 모든 답변 완료했는지 확인하고
        registerMateExtraQueTwo = (EditText) mateextraInfo.findViewById(R.id.descriptionEditText + 1);    // 추가질문2
        registerMateExtraQueThree = (EditText) mateextraInfo.findViewById(R.id.descriptionEditText + 2);  // 추가질문3
        if (registerMateExtraQueTwo != null) {
            extra_q2 = registerMateExtraQueTwo.getText().toString();
        } else {
            extra_q2 = null;
        }
        if (registerMateExtraQueThree != null) {
            extra_q3 = registerMateExtraQueThree.getText().toString();
        } else {
            extra_q3 = null;
        }

        // 완료 됐으면 데이터 뽑기
        title = registerMateTitle.getText().toString();
        String location = writepreflocateBt.getText().toString();   // 위치
        /*String[] locationSplit = location.split(" ");
        String Ko = locationSplit[0];   // '대한민국'을 제외한 주소값을 보내기 위해서
        local1 = locationSplit[1];
        local2 = locationSplit[2];
        if (locationSplit[3] != null) {
            local3 = locationSplit[3];
        }
        */
        String[] locationSplit = new String[location.split(" ").length];
        for (int i = 0; i < locationSplit.length; i++) {
            locationSplit[i] = location.split(" ")[i];
        }
        local1 = locationSplit[1];
        local2 = locationSplit[2];
        local3 = locationSplit[3];

        roomTypeNum(clickedType);
        deposit = Integer.parseInt(registerMateDeposit.getText().toString());           // 보증금
        rent = Integer.parseInt(registerMateRent.getText().toString());
        available_date = prefDate.getText().toString();                                 // 입주 가능일
        description = registerMateDescription.getText().toString();                     // 자기소개

        extra_q1 = registerMateExtraQueOne.getText().toString();

        if (isModify == 0) {
            // 수정 완료 통신
            modifyFinishRetrofit(roommate_id);
        } else if (isModify == 1) {
            registerRetrofit();
            local3 = locationSplit[3];
        }
        Log.i("modify", isModify + "");
    }

    public void registerRetrofit() {
        Call<ResStringString> resRegisterMateCall = NetSSL.getInstance().getMemberImpFactory()
                .registerMate(new ReqRegisterMate(title, local1, local2, local3, room_type, deposit, rent, available_date,
                        description, extra_q1, extra_q2, extra_q3));
        resRegisterMateCall.enqueue(new Callback<ResStringString>() {
            @Override
            public void onResponse(Call<ResStringString> call, Response<ResStringString> response) {
                if (response.body().getResult() != null) {
                    Log.i("RF:RegisterMate", "SUCCESS" + response.body().getResult());
                    Toast.makeText(WriteMateActivity.this, "룸메이트 등록이 완료되었습니다.", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Log.i("RF:RegisterMate", "FAIL" + response.body().getError());
                }
            }

            @Override
            public void onFailure(Call<ResStringString> call, Throwable t) {
                Log.i("RF:RegisterMate", "ERR" + t.getMessage());
            }
        });
    }

    public void modifyFinishRetrofit(int roommate_id) {
        Call<ResStringString> resModifyMateCall = NetSSL.getInstance().getMemberImpFactory().resModifyMate(roommate_id,
                new ReqModifyMate(title, local1, local2, local3, room_type, deposit, rent, available_date,
                        description, extra_q1, extra_q2, extra_q3));
        resModifyMateCall.enqueue(new Callback<ResStringString>() {
            @Override
            public void onResponse(Call<ResStringString> call, Response<ResStringString> response) {
                if (response.body().getResult() != null) {
                    Log.i("RF:modifyFinish", "SUCCESS" + response.body().getResult());
                    Toast.makeText(WriteMateActivity.this, "글 수정이 완료되었습니다.", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Log.i("RF:modifyFinish", "FAIL" + response.body().getError());
                }
            }

            @Override
            public void onFailure(Call<ResStringString> call, Throwable t) {
                Log.i("RF:modifyFinish", "ERR" + t.getMessage());
            }
        });
    }
    // 화면 이벤트 ===================================================================================

    // 방 유형 펼치기
    public void showPrefRoomType(View view) {
        if (roomflag) {
            roomflag = false;
            prefroomType.setVisibility(GONE);
        } else {
            roomflag = true;
            prefroomType.setVisibility(VISIBLE);
        }
    }

    // 추가 질문 펼치기
    public void ShowMateExtraInfo(View view) {
        if (extraflag) {
            extraflag = false;
            mateextraInfo.setVisibility(GONE);
        } else {
            extraflag = true;
            mateextraInfo.setVisibility(VISIBLE);
        }
    }

    // 위치 등록
    public void goLocate(View view) {
        //Toast.makeText(this, "goLocate", Toast.LENGTH_SHORT).show();
        WriteMateActivity.writeMatelocateBt = (Button) view;
        Intent intent = new Intent(this, WriteLocateActivity.class);
        startActivity(intent);
    }

    // 프로필 이미지 등록화면으로 이동
    public void goWriteRoomImage(View view) {
        Intent intent = new Intent(this, WriteRoomImage.class);
        startActivity(intent);
    }

    // 인증 사진 등록화면으로 이동
    public void goMateIdentify(View view) {
        Intent intent = new Intent(this, WriteMateIdentify.class);
        startActivity(intent);
    }

    // 입주 가능일
    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    // 방 유형 단일 선택
    NotoTextView clickedType = null;

    public void preftypeChecked(View view) {
        // 선택된 텍스트가 없으면,
        // 처음으로 선택한 텍스트를 clickedView에 넣고 색상을 적용한다.
        if (clickedType == null) {
            clickedType = (NotoTextView) view;
            clickedType.setTextColor(getResources().getColor(R.color.textpointColor));
        } else if (clickedType != view) {
            // 선택된 텍스트가 저장된 텍스트와 다르면
            // 저장했던 텍스트 색을 default로 변경하고,
            // 현재 선택된 텍스트를 저장 변수에 넣는다. (포인트 색상으로 적용)
            clickedType.setTextColor(getResources().getColor(R.color.grayTextColor));
            clickedType = (NotoTextView) view;
            clickedType.setTextColor(getResources().getColor(R.color.textpointColor));
        }
    }

    // 선택된 방 유형에 따라 enum값 결정
    public void roomTypeNum(NotoTextView view) {
        switch (view.getId()) {
            case R.id.typeOne:
                room_type = 1;
                break;
            case R.id.typeTwo:
                room_type = 2;
                break;
            case R.id.typeHouse:
                room_type = 4;
                break;
            case R.id.typeOffice:
                room_type = 8;
                break;
            case R.id.typeApart:
                room_type = 16;
                break;
        }
    }

    int plusNum;
    EditText descriptionEditText;   // 추가질문 받는 칸

    // 추가 질문 생성하고 각 질문의 EditText에 ID값 설정
    public void plusExtra(View view) {
        plusNum++;
        if (plusNum < 3) {
            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            // 새롭게 생긴 listview에 들어갈 구성물
            LinearLayout contentLinear = (LinearLayout) inflater.inflate(R.layout.extraque_view, null);

            contentLinear.setOrientation(LinearLayout.HORIZONTAL);
            mateextraInfo.addView(contentLinear, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT));

            descriptionEditText = (EditText) contentLinear.findViewById(R.id.descriptionEditText);
            descriptionEditText.setId(R.id.descriptionEditText + plusNum);
        }
    }

    public void back(View view) {
        finish();
    }
}
