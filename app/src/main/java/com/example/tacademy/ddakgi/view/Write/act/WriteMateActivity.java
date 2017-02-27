package com.example.tacademy.ddakgi.view.Write.act;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.tacademy.ddakgi.R;
import com.example.tacademy.ddakgi.util.DatePickerFragment;

import io.chooco13.NotoTextView;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class WriteMateActivity extends AppCompatActivity {
    LinearLayout prefroomType;
    LinearLayout mateextraInfo;

    Boolean roomflag = true;
    Boolean extraflag = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_mate);

        Toolbar toolbar = (Toolbar) findViewById(R.id.writeMateTool);
        this.setSupportActionBar(toolbar);
        // 펼쳐지는 메뉴 visibility 설정하기 위해서
        prefroomType = (LinearLayout) findViewById(R.id.prefroomType);
        mateextraInfo = (LinearLayout) findViewById(R.id.mateextraInfo);
    }

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
        WriteRoomActivity.writelocateBt = (Button) view;
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

    // 추가 질문 받는 부분
    public void plusExtra(View view) {
        // 빈 공간 (새롭게 생길 부분)
        LinearLayout newLinear = (LinearLayout) findViewById(R.id.ExtraMateQuelinear);
        // 새롭게 생긴 listview에 들어갈 구성물
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout contentLinear = (LinearLayout) inflater.inflate(R.layout.extraque_view, null);

        contentLinear.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT));
        contentLinear.setOrientation(LinearLayout.HORIZONTAL);
        newLinear.addView(contentLinear);
    }

    public void back(View view){
        finish();
    }
}
