package com.example.tacademy.ddakgi;

/**
 * Tab 변경해주면서 해당 Fragment 지정해주는 밑바탕 Activity
 */

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;

import com.example.tacademy.ddakgi.TabFragment.ChatTab;
import com.example.tacademy.ddakgi.HomeTab.fragment.HomeTab;
import com.example.tacademy.ddakgi.TabFragment.LikeTab;
import com.example.tacademy.ddakgi.MyTab.fragment.MyTab;
import com.example.tacademy.ddakgi.WriteTab.fragment.WriteTab;

public class HomeActivity extends AppCompatActivity {
    private TabLayout tabLayout;
    public LinearLayout fragment_container;

    /*
    fragment_container을 private으로 선언 후 사용했는데,
    사용되지 않았다고 비활성화 > public으로 변경
    */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // intent시 애니메이션
        overridePendingTransition(R.anim.act_slide_in_from_bottom, R.anim.act_slide_out_to_top);
        // 기본 탭 지정
        replaceFragment(new HomeTab());


        // 각 Tab화면을 뿌려줄 밑바탕 layout
        fragment_container = (LinearLayout) findViewById(R.id.fragment_container);

        // TabLayout 초기화
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.addTab(tabLayout.newTab().setText("HomeTab"));
        tabLayout.addTab(tabLayout.newTab().setText("ChatTab"));
        tabLayout.addTab(tabLayout.newTab().setText("WriteTab"));
        tabLayout.addTab(tabLayout.newTab().setText("LikeTab"));
        tabLayout.addTab(tabLayout.newTab().setText("MyTab"));
        tabLayout.setBackgroundColor(Color.WHITE);
        tabLayout.setTabTextColors(getResources().getColor(R.color.black), getResources().getColor(R.color.colorAccent));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);


        // Set TabSelectedListener
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                //viewPager.setCurrentItem(tab.getPosition());
                if (tab.getPosition() == 0) {
                    replaceFragment(new HomeTab());
                } else if (tab.getPosition() == 1) {
                    replaceFragment(new ChatTab());
                } else if (tab.getPosition() == 2) {
                    replaceFragment(new WriteTab());
                } else if (tab.getPosition() == 3) {
                    replaceFragment(new LikeTab());
                } else if (tab.getPosition() == 4) {
                    replaceFragment(new MyTab());
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }


    // 각 탭에 해당하는 화면으로 변경
    public void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);

        // Fragment의 변경이 있은 후에는 반드시 commit 메소드로 변경사항 반영.
        transaction.commit();
    }

    // 화면 종료시 애니메이션
    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.act_slide_in_from_top, R.anim.act_slide_out_to_bottom);
    }
}