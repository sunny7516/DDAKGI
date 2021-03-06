package com.example.tacademy.ddakgi.view.Write.act;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.tacademy.ddakgi.R;
import com.example.tacademy.ddakgi.base.BaseActivity;
import com.example.tacademy.ddakgi.data.ModifyPosting.DefaultModel;
import com.example.tacademy.ddakgi.data.ModifyPosting.ReqModifyRoom;
import com.example.tacademy.ddakgi.data.ModifyPosting.ResModifyDefault;
import com.example.tacademy.ddakgi.data.NetSSL;
import com.example.tacademy.ddakgi.data.RegisterRoom.ReqRegisterRoom;
import com.example.tacademy.ddakgi.data.RegisterRoom.ResStringString;
import com.example.tacademy.ddakgi.util.DatePickerFragment;

import io.chooco13.NotoTextView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class WriteRoomActivity extends BaseActivity {
    LinearLayout roomType;
    LinearLayout roomSize;
    LinearLayout extraInfo;
    LinearLayout newLinear;

    Boolean roomflag = true;
    Boolean sizeflag = true;
    Boolean extraflag = true;

    // 받아올 값
    EditText registerRoomTitle;
    EditText registerRoomDeposit;
    EditText registerRoomRent;
    EditText registerRoomFloor;
    Button prefDate;
    EditText registerRoomManageCost;
    EditText registerRoomDescription;
    EditText registerRoomExtraQueOne;
    EditText registerRoomExtraQueTwo;
    EditText registerRoomExtraQueThree;

    // 저장할 변수
    String title;
    String local1;
    String local2;
    String local3;
    String detailed_local;
    public static double room_latitude;
    public static double room_longitude;
    int room_type;
    int size;
    int deposit;
    int rent;
    int floor;
    String available_date;
    int manage_cost;
    int options;
    String description;
    String extra_q1;
    String extra_q2;
    String extra_q3;

    int isModify;
    int roommate_id;

    public static Button writelocateBt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_room);

        if (getIntent().getExtras() != null) {
            isModify = getIntent().getExtras().getInt("modify");
            roommate_id = getIntent().getExtras().getInt("roommate_id");

            // 수정 디폴트 통신 호출
            if (isModify == 0) {
                // 수정화면이면 디폴트값 지정
                modifyRetrofit(roommate_id);
            }
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.writeroomTool);
        this.setSupportActionBar(toolbar);

        // 펼쳐지는 메뉴 visibility 설정하기 위해서
        roomType = (LinearLayout) findViewById(R.id.roomType);
        roomSize = (LinearLayout) findViewById(R.id.roomSize);
        extraInfo = (LinearLayout) findViewById(R.id.extraInfo);

        // 받아올 값
        registerRoomTitle = (EditText) findViewById(R.id.registerRoomTitle);            // 제목
        writelocateBt = (Button) findViewById(R.id.writelocateBt);                      // 위치
        registerRoomDeposit = (EditText) findViewById(R.id.registerRoomDeposit);        // 보증금
        registerRoomRent = (EditText) findViewById(R.id.registerRoomRent);              // 월세 전세
        registerRoomFloor = (EditText) findViewById(R.id.registerRoomFloor);            // 층수
        prefDate = (Button) findViewById(R.id.prefDate);                                // 입주 가능일
        registerRoomManageCost = (EditText) findViewById(R.id.registerRoomManageCost);  // 관리비
        registerRoomDescription = (EditText) findViewById(R.id.registerRoomDescription);//자기소개
        registerRoomExtraQueOne = (EditText) findViewById(R.id.registerRoomExtraQueOne);// 추가질문1

        registerRoomTitle.setTextColor(ContextCompat.getColor(this, R.color.subTextColor));
        // 빈 공간 (새롭게 생길 부분)
        newLinear = (LinearLayout) findViewById(R.id.ExtraQuelinear);
    }

    // 통신 =========================================================================================
    // 글 수정하기 > 디폴트 값 지정 통신
    DefaultModel defaultModel;

    public void modifyRetrofit(int roommate_id) {
        Call<ResModifyDefault> resModifyDefaultCall = NetSSL.getInstance().getMemberImpFactory().resModifyDefault(roommate_id);
        resModifyDefaultCall.enqueue(new Callback<ResModifyDefault>() {
            @Override
            public void onResponse(Call<ResModifyDefault> call, Response<ResModifyDefault> response) {
                if (response.body().getResult() != null) {
                    Log.i("RF:modifyDefault", "SUCCESS" + response.body().getResult());
                    defaultModel = response.body().getResult();
                    // 결과값 디폴트로 적용
                    setDefault(defaultModel);
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

    NotoTextView defaultSize;
    NotoTextView defaultType;
    ToggleButton defaultCost;
    ToggleButton defaultOption;

    // 통신 결과값 수정 디폴트로 적용
    public void setDefault(DefaultModel defaultModel) {
        registerRoomTitle.setText(defaultModel.getTitle());
        writelocateBt.setText(defaultModel.getAddress());

        // 유형 디폴트값
        switch (defaultModel.getRoom_type()) {
            case "원룸":
                defaultType = (NotoTextView) findViewById(R.id.typeOne);
                typeChecked(defaultType);
                break;
            case "투룸":
                defaultType = (NotoTextView) findViewById(R.id.typeTwo);
                typeChecked(defaultType);
                break;
            case "주택":
                defaultType = (NotoTextView) findViewById(R.id.typeHouse);
                typeChecked(defaultType);
                break;
            case "오피스텔":
                defaultType = (NotoTextView) findViewById(R.id.typeOffice);
                typeChecked(defaultType);
                break;
            case "아파트":
                defaultType = (NotoTextView) findViewById(R.id.typeApart);
                typeChecked(defaultType);
                break;
        }

        // 크기 디폴트값
        switch (defaultModel.getSize()) {
            case "10평미만":
                defaultSize = (NotoTextView) findViewById(R.id.sizeUnderTen);
                sizeClicked(defaultSize);
                break;
            case "10평대":
                defaultSize = (NotoTextView) findViewById(R.id.sizeTen);
                sizeClicked(defaultSize);
                break;
            case "20평대":
                defaultSize = (NotoTextView) findViewById(R.id.sizeTwenty);
                sizeClicked(defaultSize);
                break;
            case "30평대":
                defaultSize = (NotoTextView) findViewById(R.id.sizeThirty);
                sizeClicked(defaultSize);
                break;
            case "40평대이상":
                defaultSize = (NotoTextView) findViewById(R.id.sizeFirty);
                sizeClicked(defaultSize);
                break;
        }

        registerRoomDeposit.setText(String.valueOf(defaultModel.getDeposit()));
        registerRoomRent.setText(String.valueOf(defaultModel.getRent()));
        registerRoomFloor.setText(String.valueOf(defaultModel.getFloor()));
        prefDate.setText(defaultModel.getAvailable_date().split("T")[0]);
        registerRoomManageCost.setText(String.valueOf(defaultModel.getManage_cost()));
        registerRoomDescription.setText(defaultModel.getDescription());
        registerRoomExtraQueOne.setText(defaultModel.getExtra_q1());

        for(int i=0; i<3; i++){
            plusExtra(null);
        }

        if (registerRoomExtraQueTwo != null) {
            registerRoomExtraQueTwo.setText(defaultModel.getExtra_q2());
        }
        if (registerRoomExtraQueThree != null) {
            registerRoomExtraQueThree.setText(defaultModel.getExtra_q3());
        }

        // 관리비 내용 디폴트값
        String[] cost_inc = new String[defaultModel.getManage_cost_inc().split(",").length];
        for (int i = 0; i < cost_inc.length; i++) {
            cost_inc[i] = defaultModel.getManage_cost_inc().split(",")[i];
        }
        for (String inc : cost_inc) {
            switch (inc) {
                case "수도":
                    defaultCost = (ToggleButton) findViewById(R.id.registerRoomManageCostOne);
                    defaultCost.setChecked(true);
                    togglePay(defaultCost);
                    break;
                case "전기":
                    defaultCost = (ToggleButton) findViewById(R.id.registerRoomManageCostTwo);
                    defaultCost.setChecked(true);
                    togglePay(defaultCost);
                    break;
                case "인터넷":
                    defaultCost = (ToggleButton) findViewById(R.id.registerRoomManageCostThree);
                    defaultCost.setChecked(true);
                    togglePay(defaultCost);
                    break;
                case "도시가스":
                    defaultCost = (ToggleButton) findViewById(R.id.registerRoomManageCostFour);
                    defaultCost.setChecked(true);
                    togglePay(defaultCost);
                    break;
                case "TV":
                    defaultCost = (ToggleButton) findViewById(R.id.registerRoomManageCostFive);
                    defaultCost.setChecked(true);
                    togglePay(defaultCost);
                    break;
                case "난방":
                    defaultCost = (ToggleButton) findViewById(R.id.registerRoomManageCostSix);
                    defaultCost.setChecked(true);
                    togglePay(defaultCost);
                    break;
            }
        }

        // 옵션 디폴트값
        String[] options = new String[defaultModel.getOptions().split(",").length];
        for (int i = 0; i < options.length; i++) {
            options[i] = defaultModel.getOptions().split(",")[i];
        }
        for (String option : options) {
            switch (option) {
                case "에어컨":
                    defaultOption = (ToggleButton) findViewById(R.id.airconditon);
                    defaultOption.setChecked(true);
                    toggledOp(defaultOption);
                    break;
                case "침대":
                    defaultOption = (ToggleButton) findViewById(R.id.bed);
                    defaultOption.setChecked(true);
                    toggledOp(defaultOption);
                    break;
                case "엘레베이터":
                    defaultOption = (ToggleButton) findViewById(R.id.elevator);
                    defaultOption.setChecked(true);
                    toggledOp(defaultOption);
                    break;
                case "전자레인지":
                    defaultOption = (ToggleButton) findViewById(R.id.microwave);
                    defaultOption.setChecked(true);
                    toggledOp(defaultOption);
                    break;
                case "주차":
                    defaultOption = (ToggleButton) findViewById(R.id.parking);
                    defaultOption.setChecked(true);
                    toggledOp(defaultOption);
                    break;
                case "냉장고":
                    defaultOption = (ToggleButton) findViewById(R.id.refrige);
                    defaultOption.setChecked(true);
                    toggledOp(defaultOption);
                    break;

                case "가스레인지":
                    defaultOption = (ToggleButton) findViewById(R.id.stove);
                    defaultOption.setChecked(true);
                    toggledOp(defaultOption);
                    break;

                case "TV":
                    defaultOption = (ToggleButton) findViewById(R.id.tv);
                    defaultOption.setChecked(true);
                    toggledOp(defaultOption);
                    break;
                case "세탁기":
                    defaultOption = (ToggleButton) findViewById(R.id.washmachine);
                    defaultOption.setChecked(true);
                    toggledOp(defaultOption);
                    break;
            }
        }
    }

    // 완료 버튼
    public void finishRegisterRoom(View view) {
        // 모든 답변이 완료됐는지 확인 후

        registerRoomExtraQueTwo = (EditText) newLinear.findViewById(R.id.descriptionEditText + 1);    // 추가질문2
        registerRoomExtraQueThree = (EditText) newLinear.findViewById(R.id.descriptionEditText + 2);  // 추가질문3
        if (registerRoomExtraQueTwo != null) {
            extra_q2 = registerRoomExtraQueTwo.getText().toString();
        } else {
            extra_q2 = null;
        }
        if (registerRoomExtraQueTwo != null) {
            extra_q3 = registerRoomExtraQueThree.getText().toString();
        } else {
            extra_q3 = null;
        }

        // 완료 됐으면
        // 데이터 뽑기
        title = registerRoomTitle.getText().toString(); // 제목

        String location = writelocateBt.getText().toString();   // 위치
        String[] locationSplit = location.split(" ");
        String Ko = locationSplit[0];   // '대한민국'을 제외한 주소값을 보내기 위해서
        local1 = locationSplit[1];
        local2 = locationSplit[2];
        local3 = locationSplit[3];
        detailed_local = locationSplit[4];

        roomTypeNum(clickedType);   // 선택된 방 유형 enum값
        roomSizeNum(clickedSize);   // 선택된 방 크기 enum값

        deposit = Integer.parseInt(registerRoomDeposit.getText().toString());           // 보증금
        rent = Integer.parseInt(registerRoomRent.getText().toString());                 // 월세 전세
        floor = Integer.parseInt(registerRoomFloor.getText().toString());               // 층수
        available_date = prefDate.getText().toString();                                 // 입주 가능일
        manage_cost = Integer.parseInt(registerRoomManageCost.getText().toString());    // 관리비
        options = OptionNum();                                                          // 옵션
        description = registerRoomDescription.getText().toString();                     // 자기소개
        extra_q1 = registerRoomExtraQueOne.getText().toString();
        // 추가질문1

        if (isModify == 0) {
            // 수정 완료 통신
            modifyFinishRetrofit(roommate_id);
        } else if (isModify == 1) {
            setData();
            local3 = locationSplit[3];
        }
        Log.i("modify", isModify + "");
    }

    // 수정 등록 통신
    public void modifyFinishRetrofit(int roommate_id){
        Call<ResStringString> resModifyRoomCall = NetSSL.getInstance().getMemberImpFactory().resModifyRoom(roommate_id,
                new ReqModifyRoom(title, local1, local2, local3, detailed_local,
                        room_latitude, room_longitude, room_type, size, deposit, rent, floor, available_date, manage_cost, costNum, options, description,
                        extra_q1, extra_q2, extra_q3));
        resModifyRoomCall.enqueue(new Callback<ResStringString>() {
            @Override
            public void onResponse(Call<ResStringString> call, Response<ResStringString> response) {
                if (response.body().getResult() != null) {
                    Log.i("RF:modifyFinish", "SUCCESS" + response.body().getResult());
                    Toast.makeText(WriteRoomActivity.this, "글 수정이 완료되었습니다.", Toast.LENGTH_SHORT).show();
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

    // 글 등록 통신
    public void setData() {
        // 디비에 정보 저장
        Call<ResStringString> resRegisterRoomCall = NetSSL.getInstance().getMemberImpFactory()
                .registerRoom(new ReqRegisterRoom(title, local1, local2, local3, detailed_local,
                        room_latitude, room_longitude, room_type, size, deposit, rent, floor, available_date, manage_cost, costNum, options, description,
                        extra_q1, extra_q2, extra_q3));
        resRegisterRoomCall.enqueue(new Callback<ResStringString>() {
            @Override
            public void onResponse(Call<ResStringString> call, Response<ResStringString> response) {
                if (response.body().getResult() != null) {
                    Log.i("RF:RegisterRoom", "SUCCESS" + response.body().getResult());
                    Toast.makeText(WriteRoomActivity.this, "방 등록이 완료되었습니다.", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Log.i("RF:RegisterRoom", "FAIL" + response.body().getError());
                }
            }

            @Override
            public void onFailure(Call<ResStringString> call, Throwable t) {
                Log.i("RF:RegisterRoom", "ERR" + t.getMessage());
            }
        });
    }

    // 화면 이벤트 ===================================================================================
    public void showRoomType(View view) {
        if (roomflag) {
            roomflag = false;
            roomType.setVisibility(GONE);
        } else {
            roomflag = true;
            roomType.setVisibility(VISIBLE);
        }
    }

    public void showRoomSize(View view) {
        if (sizeflag) {
            sizeflag = false;
            roomSize.setVisibility(GONE);
        } else {
            sizeflag = true;
            roomSize.setVisibility(VISIBLE);
        }
    }

    public void ShowExtraInfo(View view) {
        if (extraflag) {
            extraflag = false;
            extraInfo.setVisibility(GONE);
        } else {
            extraflag = true;
            extraInfo.setVisibility(VISIBLE);
        }
    }

    // 위치 등록
    public void goLocate(View view) {
        //Toast.makeText(this, "goLocate", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, WriteLocateActivity.class);
        startActivity(intent);
    }

    // 사진 등록화면으로 이동
    public void goWriteRoomImage(View view) {
        Intent intent = new Intent(this, WriteRoomImage.class);
        startActivity(intent);
    }

    // 인증 사진 등록화면으로 이동
    public void goIdentify(View view) {
        Intent intent = new Intent(this, WriteRoomIdentify.class);
        startActivity(intent);
    }

    // 입주 가능일
    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    // 방 유형 단일 선택
    NotoTextView clickedType = null;

    public void typeChecked(View view) {
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

    // 방 크기 단일 선택
    NotoTextView clickedSize = null;

    public void sizeClicked(View view) {
        // 선택된 텍스트가 없으면,
        // 처음으로 선택한 텍스트를 clickedView에 넣고 색상을 적용한다.
        if (clickedSize == null) {
            clickedSize = (NotoTextView) view;
            clickedSize.setTextColor(getResources().getColor(R.color.textpointColor));
        } else if (clickedSize != view) {
            // 선택된 텍스트가 저장된 텍스트와 다르면
            // 저장했던 텍스트 색을 default로 변경하고,
            // 현재 선택된 텍스트를 저장 변수에 넣는다. (포인트 색상으로 적용)
            clickedSize.setTextColor(getResources().getColor(R.color.grayTextColor));
            clickedSize = (NotoTextView) view;
            clickedSize.setTextColor(getResources().getColor(R.color.textpointColor));
        }
    }

    // 선택된 방 크기에 따라 enum값 결정
    public void roomSizeNum(TextView view) {
        switch (view.getId()) {
            case R.id.sizeUnderTen:
                size = 1;
                break;
            case R.id.sizeTen:
                size = 2;
                break;
            case R.id.sizeTwenty:
                size = 3;
                break;
            case R.id.sizeThirty:
                size = 4;
                break;
            case R.id.sizeFirty:
                size = 5;
                break;
        }
    }

    // 중복 선택 가능 (관리비 내용)
    int costNum = 0;

    public void togglePay(View view) {
        ToggleButton toggleButton = (ToggleButton) view;
        if (toggleButton.isChecked()) {
            switch (toggleButton.getId()) {
                case R.id.registerRoomManageCostOne:
                    costNum += 1;
                    break;
                case R.id.registerRoomManageCostTwo:
                    costNum += 2;
                    break;
                case R.id.registerRoomManageCostThree:
                    costNum += 4;
                    break;
                case R.id.registerRoomManageCostFour:
                    costNum += 8;
                    break;
                case R.id.registerRoomManageCostFive:
                    costNum += 16;
                    break;
                case R.id.registerRoomManageCostSix:
                    costNum += 32;
                    break;
            }
            toggleButton.setTextColor(getResources().getColor(R.color.textpointColor));
        } else if (!toggleButton.isChecked()) {
            switch (toggleButton.getId()) {
                case R.id.registerRoomManageCostOne:
                    costNum -= 1;
                    break;
                case R.id.registerRoomManageCostTwo:
                    costNum -= 2;
                    break;
                case R.id.registerRoomManageCostThree:
                    costNum -= 4;
                    break;
                case R.id.registerRoomManageCostFour:
                    costNum -= 8;
                    break;
                case R.id.registerRoomManageCostFive:
                    costNum -= 16;
                    break;
                case R.id.registerRoomManageCostSix:
                    costNum -= 32;
                    break;
            }
            toggleButton.setTextColor(getResources().getColor(R.color.grayTextColor));
        }
    }

    int[] clickedOptions = new int[10];

    // 중복 선택 가능 (옵션 내용)
    public void toggledOp(View view) {
        ToggleButton toggleButton = (ToggleButton) view;
        if (!toggleButton.isChecked()) {
            switch (toggleButton.getId()) {
                case R.id.airconditon:
                    clickedOptions[0] = 0;
                    toggleButton.setBackground(getResources().getDrawable(R.mipmap.airconditon_icon));
                    break;
                case R.id.bed:
                    clickedOptions[1] = 0;
                    toggleButton.setBackground(getResources().getDrawable(R.mipmap.bed_icon));
                    break;
                case R.id.elevator:
                    clickedOptions[2] = 0;
                    toggleButton.setBackground(getResources().getDrawable(R.mipmap.elevator_icon));
                    break;
                case R.id.microwave:
                    clickedOptions[3] = 0;
                    toggleButton.setBackground(getResources().getDrawable(R.mipmap.microwave_icon));
                    break;
                case R.id.parking:
                    clickedOptions[4] = 0;
                    toggleButton.setBackground(getResources().getDrawable(R.mipmap.parking_icon));
                    break;
                case R.id.refrige:
                    clickedOptions[5] = 0;
                    toggleButton.setBackground(getResources().getDrawable(R.mipmap.refrige_icon));
                    break;
                case R.id.stove:
                    clickedOptions[6] = 0;
                    toggleButton.setBackground(getResources().getDrawable(R.mipmap.stove_icon));
                    break;
                case R.id.tv:
                    clickedOptions[7] = 0;
                    toggleButton.setBackground(getResources().getDrawable(R.mipmap.tv_icon));
                    break;
                case R.id.washmachine:
                    clickedOptions[8] = 0;
                    toggleButton.setBackground(getResources().getDrawable(R.mipmap.washmachine_icon));
                    break;
            }
        } else {
            switch (toggleButton.getId()) {
                case R.id.airconditon:
                    clickedOptions[0] = 1;
                    toggleButton.setBackground(getResources().getDrawable(R.mipmap.airconditon_on_icon));
                    break;
                case R.id.bed:
                    clickedOptions[1] = 2;
                    toggleButton.setBackground(getResources().getDrawable(R.mipmap.bed_on_icon));
                    break;
                case R.id.elevator:
                    clickedOptions[2] = 4;
                    toggleButton.setBackground(getResources().getDrawable(R.mipmap.elevator_on_icon));
                    break;
                case R.id.microwave:
                    clickedOptions[3] = 8;
                    toggleButton.setBackground(getResources().getDrawable(R.mipmap.microwave_on_icon));
                    break;
                case R.id.parking:
                    clickedOptions[4] = 16;
                    toggleButton.setBackground(getResources().getDrawable(R.mipmap.parking_on_icon));
                    break;
                case R.id.refrige:
                    clickedOptions[5] = 32;
                    toggleButton.setBackground(getResources().getDrawable(R.mipmap.refrige_on_icon));
                    break;
                case R.id.stove:
                    clickedOptions[6] = 64;
                    toggleButton.setBackground(getResources().getDrawable(R.mipmap.stove_on_icon));
                    break;
                case R.id.tv:
                    clickedOptions[7] = 128;
                    toggleButton.setBackground(getResources().getDrawable(R.mipmap.tv_on_icon));
                    break;
                case R.id.washmachine:
                    clickedOptions[8] = 256;
                    toggleButton.setBackground(getResources().getDrawable(R.mipmap.washmachine_on_icon));
                    break;
            }
        }
    }

    // 옵션 중복 선택 Set 값
    int setNum;

    public int OptionNum() {
        for (int i = 0; i < clickedOptions.length; i++) {
            setNum += clickedOptions[i];
        }
        Log.i("setNum", setNum + "");
        return setNum;
    }

    int plusNum;
    EditText descriptionEditText;   // 추가질문 받는 칸

    // 추가질문 생성하고 각 질문의 EditText에 ID값 설정
    public void plusExtra(View view) {
        plusNum++;
        if (plusNum < 3) {
            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            // 새롭게 생긴 listview에 들어갈 구성물
            LinearLayout contentLinear = (LinearLayout) inflater.inflate(R.layout.extraque_view, null);

            contentLinear.setOrientation(LinearLayout.HORIZONTAL);
            newLinear.addView(contentLinear, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT));

            descriptionEditText = (EditText) contentLinear.findViewById(R.id.descriptionEditText);
            descriptionEditText.setId(R.id.descriptionEditText + plusNum);
        }
    }
}