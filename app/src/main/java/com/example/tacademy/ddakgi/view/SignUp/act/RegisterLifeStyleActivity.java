package com.example.tacademy.ddakgi.view.SignUp.act;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckedTextView;

import com.example.tacademy.ddakgi.R;

import io.chooco13.NotoTextView;

import static com.example.tacademy.ddakgi.view.SignUp.act.RegisterProfileActivity.isLifeStyleFinish;

public class RegisterLifeStyleActivity extends AppCompatActivity {
    NotoTextView lifestyleFinish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_life_style);
        lifestyleFinish = (NotoTextView) findViewById(R.id.lifestyleFinish);

        for (int i = 1; i < CheckObj.length; i++) {
            CheckObj[i] = false;
        }
    }

    // 항목 단일 선택하는 부분
    public void styleCheck(View view) {
        switch (view.getTag().toString()) {
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
    CheckedTextView[] AnsObj = new CheckedTextView[11];
    Boolean[] CheckObj = new Boolean[11];

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

    // 모든 문항의 답변이 완료되었는지 확인
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
    public void finishBt(){
        // 모든 문항의 답변이 끝나면 완료 버튼을 활성화한다.
        lifestyleFinish.setTextColor(ContextCompat.getColor(this, R.color.defaultTextColor));
        lifestyleFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 프로필 등록화면에 설문패턴 응답이 끝났음을 알려준다.
                isLifeStyleFinish = true;
                // 설문패턴 DB로 저장
                finish();
            }
        });
    }
}
