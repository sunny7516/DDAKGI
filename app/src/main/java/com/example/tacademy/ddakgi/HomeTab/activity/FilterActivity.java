package com.example.tacademy.ddakgi.HomeTab.activity;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ToggleButton;

import com.example.tacademy.ddakgi.R;
import com.example.tacademy.ddakgi.Util.DatePickerFragment;
import com.example.tacademy.ddakgi.base.BaseActivity;

/**
 * HomeTab 상단버튼에서 이어지는 filter화면
 */
public class FilterActivity extends BaseActivity {

    Button filterDate;
    ToggleButton filterRoomType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        Toolbar toolbar = (Toolbar) findViewById(R.id.filterToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);

        // 입주 가능일
        filterDate = (Button) findViewById(R.id.prefDate);
    }

    boolean filterFlag = true;

    // 입주가능일 show/hide
    public void showFilterprefDate(View view) {
        if (!filterFlag) {
            filterDate.setVisibility(View.GONE);
            filterFlag = true;
        } else
            filterDate.setVisibility(View.VISIBLE);
        filterFlag = false;
    }

    // 방 유형 필터링 선택하기(중복)
    public void filterChecked(View view) {
        filterRoomType = (ToggleButton) view;
        if (filterRoomType.isChecked()) {
            filterRoomType.setTextColor(getResources().getColor(R.color.textpointColor));
        } else {
            filterRoomType.setTextColor(getResources().getColor(R.color.subTextColor));
        }
    }

    // 입주가능일 DatePickerDialog 띄우기
    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    // 월세 / 전세 선택하기
    io.chooco13.NotoTextView clickedText;
    io.chooco13.NotoTextView unclickedText;

    public void priceType(View view) {
        clickedText = (io.chooco13.NotoTextView) view;
        switch (clickedText.getId()) {
            case R.id.priceMonth:
                unclickedText = (io.chooco13.NotoTextView) findViewById(R.id.priceYear);
                break;
            case R.id.priceYear:
                unclickedText = (io.chooco13.NotoTextView) findViewById(R.id.priceMonth);
                break;
        }
        clickedText.setTextColor(getResources().getColor(R.color.defaultTextColor));
        clickedText.setTextSize(16);
        unclickedText.setTextColor(getResources().getColor(R.color.subTextColor));
        unclickedText.setTextSize(13);
    }

    // 필터 리셋
    public void reset(View view) {

    }

    // 필터 적용
    public void apply(View view) {

    }

}
