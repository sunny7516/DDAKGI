package com.example.tacademy.ddakgi.view.Filter;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.tacademy.ddakgi.R;
import com.example.tacademy.ddakgi.base.BaseActivity;
import com.example.tacademy.ddakgi.util.DatePickerFragment;

import java.util.Calendar;

import io.chooco13.NotoTextView;

/**
 * HomeTab 상단버튼에서 이어지는 filter화면
 */
public class FilterActivity extends BaseActivity {

    Button filterDate;
    ToggleButton filterRoomType;

    LinearLayout roomTypeLinearLayout;
    SeekBar depositSeekBar, monthPriceSeekBar, yearPriceSeekBar;

    NotoTextView depositText;

    // 보낼 값 저장할 변수
    public static String filterAvailable_date;
    public static int filterRoomType1, filterRoomType2, filterRoomType3, filterRoomType4, filterRoomType5, filterDeposit, filterRent;

    public static boolean isFilterSet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        Toolbar toolbar = (Toolbar) findViewById(R.id.filterToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);

        // 입주 가능일
        filterDate = (Button) findViewById(R.id.prefDate);
        roomTypeLinearLayout = (LinearLayout) findViewById(R.id.roomTypeLinearLayout);
        depositSeekBar = (SeekBar) findViewById(R.id.depositSeekBar);
        monthPriceSeekBar = (SeekBar) findViewById(R.id.monthPriceSeekBar);
        yearPriceSeekBar = (SeekBar) findViewById(R.id.yearPriceSeekBar);
        depositText = (NotoTextView) findViewById(R.id.depositText);

        yearPriceSeekBar.setOnSeekBarChangeListener(hundredSizeListener);
        depositSeekBar.setOnSeekBarChangeListener(hundredSizeListener);
        monthPriceSeekBar.setOnSeekBarChangeListener(fiveSizeListener);
    }

    // 월세 Seekbar Listener
    SeekBar.OnSeekBarChangeListener fiveSizeListener = new SeekBar.OnSeekBarChangeListener() {
        String progressString;

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            seekBar.setMax(100);
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            if (seekBar.getProgress() == 5000) {
                Toast.makeText(FilterActivity.this, "Any", Toast.LENGTH_SHORT).show();
                filterRent = 999999;
            } else {
                progressString = String.valueOf(seekBar.getProgress() * 5);
                Toast.makeText(FilterActivity.this, progressString, Toast.LENGTH_SHORT).show();
                filterRent = Integer.valueOf(progressString);
            }
        }
    };

    // 보증금, 전세 Seekbar listener
    SeekBar.OnSeekBarChangeListener hundredSizeListener = new SeekBar.OnSeekBarChangeListener() {
        String progressString;

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            seekBar.setMax(50);
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            // 최대값(5000)일 때와 아닐 때를 구분
            if (seekBar.getProgress() == 5000) {
                Toast.makeText(FilterActivity.this, "Any", Toast.LENGTH_SHORT).show();

                // seekbar가 전세인지 보증금인지 구분
                if (seekBar.getId() == R.id.depositSeekBar) {
                    filterDeposit = 999999;
                } else {
                    progressString = String.valueOf(seekBar.getProgress() * 100);
                    Toast.makeText(FilterActivity.this, progressString, Toast.LENGTH_SHORT).show();
                }
            }
        }
    };

    boolean filterFlag = true;

    // 입주가능일 show/hide
    public void showFilterprefDate(View view) {
        if (filterFlag) {
            filterFlag = false;
            filterDate.setVisibility(View.GONE);
        } else {
            filterFlag = true;
            filterDate.setVisibility(View.VISIBLE);
        }
    }

    int[] checkedFilter = new int[6];

    // 방 유형 필터링 선택하기(중복)
    public void filterChecked(View view) {
        filterRoomType = (ToggleButton) view;
        if (filterRoomType.isChecked()) {
            filterRoomType.setTextColor(getResources().getColor(R.color.textpointColor));
            switch (filterRoomType.getId()) {
                case R.id.typeOne:
                    checkedFilter[0] = 1;
                    break;
                case R.id.typeTwo:
                    checkedFilter[1] = 2;
                    break;
                case R.id.typeHouse:
                    checkedFilter[2] = 3;
                    break;
                case R.id.typeOffice:
                    checkedFilter[3] = 4;
                    break;
                case R.id.typeApart:
                    checkedFilter[4] = 5;
                    break;
            }
        } else {
            filterRoomType.setTextColor(getResources().getColor(R.color.subTextColor));
            switch (filterRoomType.getId()) {
                case R.id.typeOne:
                    checkedFilter[0] = 0;
                    break;
                case R.id.typeTwo:
                    checkedFilter[1] = 0;
                    break;
                case R.id.typeHouse:
                    checkedFilter[2] = 0;
                    break;
                case R.id.typeOffice:
                    checkedFilter[3] = 0;
                    break;
                case R.id.typeApart:
                    checkedFilter[4] = 0;
                    break;
            }
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
                depositText.setTextColor(ContextCompat.getColor(this, R.color.defaultTextColor));
                depositSeekBar.setEnabled(true);
                unclickedText = (io.chooco13.NotoTextView) findViewById(R.id.priceYear);
                monthPriceSeekBar.setVisibility(View.VISIBLE);
                yearPriceSeekBar.setVisibility(View.GONE);
                break;
            case R.id.priceYear:
                filterRent = 0;
                depositText.setTextColor(ContextCompat.getColor(this, R.color.grayTextColor));
                depositSeekBar.setEnabled(false);
                unclickedText = (io.chooco13.NotoTextView) findViewById(R.id.priceMonth);
                monthPriceSeekBar.setVisibility(View.GONE);
                yearPriceSeekBar.setVisibility(View.VISIBLE);
                break;
        }
        clickedText.setTextColor(getResources().getColor(R.color.defaultTextColor));
        clickedText.setTextSize(16);
        unclickedText.setTextColor(getResources().getColor(R.color.subTextColor));
        unclickedText.setTextSize(13);
    }

    // 필터 리셋
    public void reset(View view) {
        // 방 유형 색상 디폴트값으로 되돌리기
        ToggleButton toggleButton1 = (ToggleButton) roomTypeLinearLayout.findViewWithTag("type1");
        ToggleButton toggleButton2 = (ToggleButton) roomTypeLinearLayout.findViewWithTag("type2");
        ToggleButton toggleButton3 = (ToggleButton) roomTypeLinearLayout.findViewWithTag("type3");
        ToggleButton toggleButton4 = (ToggleButton) roomTypeLinearLayout.findViewWithTag("type4");
        ToggleButton toggleButton5 = (ToggleButton) roomTypeLinearLayout.findViewWithTag("type5");
        toggleButton1.setTextColor(ContextCompat.getColor(this, R.color.subTextColor));
        toggleButton2.setTextColor(ContextCompat.getColor(this, R.color.subTextColor));
        toggleButton3.setTextColor(ContextCompat.getColor(this, R.color.subTextColor));
        toggleButton4.setTextColor(ContextCompat.getColor(this, R.color.subTextColor));
        toggleButton5.setTextColor(ContextCompat.getColor(this, R.color.subTextColor));

        depositSeekBar.setProgress(0);
        if (monthPriceSeekBar != null) {
            monthPriceSeekBar.setProgress(0);
        } else {
            yearPriceSeekBar.setProgress(0);
        }

        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        filterDate.setText(year + "-" + month + "-" + day);
        isFilterSet = false;
    }

    // 필터 적용
    public void apply(View view) {
        isFilterSet = true;
        // 이전 화면 통신으로 값 전달
        filterAvailable_date = filterDate.getText().toString();

        filterRoomType1 = checkedFilter[0];
        filterRoomType2 = checkedFilter[1];
        filterRoomType3 = checkedFilter[2];
        filterRoomType4 = checkedFilter[3];
        filterRoomType5 = checkedFilter[4];
        Log.i("필터값 넘기기", "type1: " + filterRoomType1 +
                "type2: " + filterRoomType2 +
                "type3: " + filterRoomType3 +
                "type4: " + filterRoomType4 +
                "type5: " + filterRoomType5 +
                "deposit: " + filterDeposit +
                "filterRent: " + filterRent +
                "date: " + filterAvailable_date);

        Toast.makeText(this, "필터가 적용되었습니다!", Toast.LENGTH_SHORT).show();
        setResult(1000);
        finish();
    }
}
