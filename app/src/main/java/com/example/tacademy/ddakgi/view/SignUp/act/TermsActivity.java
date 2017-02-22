package com.example.tacademy.ddakgi.view.SignUp.act;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.example.tacademy.ddakgi.R;
import com.example.tacademy.ddakgi.view.SignUp.frag.InfoFrag;
import com.example.tacademy.ddakgi.view.SignUp.frag.ServiceFrag;

import static com.example.tacademy.ddakgi.view.SignUp.act.RegisterProfileActivity.termFragment;

public class TermsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms);

        switch (termFragment) {
            case "info":
                replaceFragment(new InfoFrag());
                break;
            case "service":
                replaceFragment(new ServiceFrag());
                break;
        }
    }

    // 각 탭에 해당하는 화면으로 변경
    public void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);

        // Fragment의 변경이 있은 후에는 반드시 commit 메소드로 변경사항 반영.
        transaction.commit();
    }
}