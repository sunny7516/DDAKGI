package com.example.tacademy.ddakgi.view.Write.act;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.example.tacademy.ddakgi.R;
import com.example.tacademy.ddakgi.util.DatePickerFragment;
import com.example.tacademy.ddakgi.base.BaseActivity;

import io.chooco13.NotoTextView;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class WriteRoomActivity extends BaseActivity{
    LinearLayout roomType;
    LinearLayout roomSize;
    LinearLayout extraInfo;

    Boolean roomflag = true;
    Boolean sizeflag = true;
    Boolean extraflag = true;

    Button plusExtraQue;

    public static Button writelocateBt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_room);

        Toolbar toolbar = (Toolbar) findViewById(R.id.writeroomTool);
        this.setSupportActionBar(toolbar);

        // 펼쳐지는 메뉴 visibility 설정하기 위해서
        roomType = (LinearLayout) findViewById(R.id.roomType);
        roomSize = (LinearLayout) findViewById(R.id.roomSize);
        extraInfo = (LinearLayout) findViewById(R.id.extraInfo);

        writelocateBt = (Button) findViewById(R.id.writelocateBt);
    }

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

    // 방 크기 단일 선택
    TextView clickedSize = null;

    public void sizeClicked(View view) {
        // 선택된 텍스트가 없으면,
        // 처음으로 선택한 텍스트를 clickedView에 넣고 색상을 적용한다.
        if (clickedSize == null) {
            clickedSize = (TextView) view;
            clickedSize.setTextColor(getResources().getColor(R.color.textpointColor));
        } else if (clickedSize != view) {
            // 선택된 텍스트가 저장된 텍스트와 다르면
            // 저장했던 텍스트 색을 default로 변경하고,
            // 현재 선택된 텍스트를 저장 변수에 넣는다. (포인트 색상으로 적용)
            clickedSize.setTextColor(getResources().getColor(R.color.grayTextColor));
            clickedSize = (TextView) view;
            clickedSize.setTextColor(getResources().getColor(R.color.textpointColor));
        }
    }

    // 중복 선택 가능 (관리비 내용)
    public void togglePay(View view) {
        ToggleButton toggleButton = (ToggleButton) view;
        if (toggleButton.isChecked()) {
            toggleButton.setTextColor(getResources().getColor(R.color.textpointColor));
        } else {
            toggleButton.setTextColor(getResources().getColor(R.color.grayTextColor));
        }
    }

    // 중복 선택 가능 (옵션 내용)
    public void toggledOp(View view) {
        ToggleButton toggleButton = (ToggleButton) view;
        if (toggleButton.isChecked()) {
            switch (toggleButton.getId()) {
                case R.id.airconditon:
                    toggleButton.setBackground(getResources().getDrawable(R.mipmap.airconditon_icon));
                    break;
                case R.id.bed:
                    toggleButton.setBackground(getResources().getDrawable(R.mipmap.bed_icon));
                    break;
                case R.id.elevator:
                    toggleButton.setBackground(getResources().getDrawable(R.mipmap.elevator_icon));
                    break;
                case R.id.microwave:
                    toggleButton.setBackground(getResources().getDrawable(R.mipmap.microwave_icon));
                    break;
                case R.id.parking:
                    toggleButton.setBackground(getResources().getDrawable(R.mipmap.parking_icon));
                    break;
                case R.id.refrige:
                    toggleButton.setBackground(getResources().getDrawable(R.mipmap.refrige_icon));
                    break;
                case R.id.stove:
                    toggleButton.setBackground(getResources().getDrawable(R.mipmap.stove_icon));
                    break;
                case R.id.tv:
                    toggleButton.setBackground(getResources().getDrawable(R.mipmap.tv_icon));
                    break;
                case R.id.washmachine:
                    toggleButton.setBackground(getResources().getDrawable(R.mipmap.washmachine_icon));
                    break;
            }
        } else {
            switch (toggleButton.getId()) {
                case R.id.airconditon:
                    toggleButton.setBackground(getResources().getDrawable(R.mipmap.airconditon_on_icon));
                    break;
                case R.id.bed:
                    toggleButton.setBackground(getResources().getDrawable(R.mipmap.bed_on_icon));
                    break;
                case R.id.elevator:
                    toggleButton.setBackground(getResources().getDrawable(R.mipmap.elevator_on_icon));
                    break;
                case R.id.microwave:
                    toggleButton.setBackground(getResources().getDrawable(R.mipmap.microwave_on_icon));
                    break;
                case R.id.parking:
                    toggleButton.setBackground(getResources().getDrawable(R.mipmap.parking_on_icon));
                    break;
                case R.id.refrige:
                    toggleButton.setBackground(getResources().getDrawable(R.mipmap.refrige_on_icon));
                    break;
                case R.id.stove:
                    toggleButton.setBackground(getResources().getDrawable(R.mipmap.stove_on_icon));
                    break;
                case R.id.tv:
                    toggleButton.setBackground(getResources().getDrawable(R.mipmap.tv_on_icon));
                    break;
                case R.id.washmachine:
                    toggleButton.setBackground(getResources().getDrawable(R.mipmap.washmachine_on_icon));
                    break;
            }
        }
    }

    public void plusExtra(View view) {
        // 빈 공간 (새롭게 생길 부분)
        LinearLayout newLinear = (LinearLayout) findViewById(R.id.ExtraQuelinear);
        // 새롭게 생긴 listview에 들어갈 구성물
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout contentLinear = (LinearLayout) inflater.inflate(R.layout.extraque_view, null);

        contentLinear.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT));
        contentLinear.setOrientation(LinearLayout.HORIZONTAL);
        newLinear.addView(contentLinear);
    }
}