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
import android.widget.ToggleButton;

import com.example.tacademy.ddakgi.R;
import com.example.tacademy.ddakgi.util.DatePickerFragment;

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

    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    // 희망하는 방의 유형 중복으로 선택하기
    public void preftypeChecked(View view) {
        ToggleButton preftype = (ToggleButton) view;
        if (preftype.isChecked()) {
            preftype.setTextColor(getResources().getColor(R.color.textpointColor));
        } else {
            preftype.setTextColor(getResources().getColor(R.color.grayTextColor));
        }
    }

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
