package com.example.tacademy.ddakgi.view.Report;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.tacademy.ddakgi.R;
import com.example.tacademy.ddakgi.base.BaseActivity;
import com.example.tacademy.ddakgi.data.NetSSL;
import com.example.tacademy.ddakgi.data.RegisterRoom.ResStringString;
import com.example.tacademy.ddakgi.data.Report.ReqReport;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class reportActivity extends BaseActivity {
    int other_member_id;
    RadioButton reportReasonOne;
    RadioButton reportReasonTwo;
    RadioButton reportReasonThree;
    RadioButton reportReasonFour;
    RadioButton reportReasonFive;
    EditText reportReason;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        reportReasonOne = (RadioButton) findViewById(R.id.reportReasonOne);
        reportReasonTwo = (RadioButton) findViewById(R.id.reportReasonTwo);
        reportReasonThree = (RadioButton) findViewById(R.id.reportReasonThree);
        reportReasonFour = (RadioButton) findViewById(R.id.reportReasonFour);
        reportReasonFive = (RadioButton) findViewById(R.id.reportReasonFive);

        reportReason = (EditText) findViewById(R.id.reportReason);

        reportReasonOne.setOnClickListener(reportClickListener);
        reportReasonTwo.setOnClickListener(reportClickListener);
        reportReasonThree.setOnClickListener(reportClickListener);
        reportReasonFour.setOnClickListener(reportClickListener);
        reportReasonFive.setOnClickListener(reportClickListener);

        other_member_id = getIntent().getExtras().getInt("other_member_id");
        Log.i("other_member_id", other_member_id + "");
    }

    int report_content;
    RadioButton.OnClickListener reportClickListener = new RadioButton.OnClickListener() {
        @Override
        public void onClick(View v) {
            RadioButton reportBt = (RadioButton) v;
            switch (reportBt.getId()) {
                case R.id.reportReasonOne:
                    report_content = 1;
                    reportReason.setEnabled(false);
                    reportReason.setClickable(false);
                    break;
                case R.id.reportReasonTwo:
                    report_content = 2;
                    reportReason.setEnabled(false);
                    reportReason.setClickable(false);
                    break;
                case R.id.reportReasonThree:
                    report_content = 3;
                    reportReason.setEnabled(false);
                    reportReason.setClickable(false);
                    break;
                case R.id.reportReasonFour:
                    report_content = 4;
                    reportReason.setEnabled(false);
                    reportReason.setClickable(false);
                    break;
                case R.id.reportReasonFive:
                    report_content = 5;
                    reportReason.setEnabled(true);
                    reportReason.setClickable(true);
                    break;
            }
        }
    };
    String etc_content;

    public void setReport(View view) {
        if (reportReason != null) {
            etc_content = reportReason.getText().toString();
        } else {
            etc_content = null;
        }
        showProgress("신고 접수 중입니다.");
        Call<ResStringString> resReport = NetSSL.getInstance().getMemberImpFactory()
                .resReport(new ReqReport(other_member_id, report_content, etc_content));
        resReport.enqueue(new Callback<ResStringString>() {
            @Override
            public void onResponse(Call<ResStringString> call, Response<ResStringString> response) {
                if (response.body().getResult() != null) {
                    Log.i("RF:Report", "SUCCESS" + response.body().getResult());
                    hideProgress();
                    Toast.makeText(reportActivity.this, "신고가 접수되었습니다. 감사합니다.", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Log.i("RF:Report", "FAIL" + response.body().getError());
                }
            }

            @Override
            public void onFailure(Call<ResStringString> call, Throwable t) {
                Log.i("RF:Report", "ERR" + t.getMessage());
                hideProgress();
                Toast.makeText(reportActivity.this, "신고가 취소되었습니다. 다시 신청해주세요", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
