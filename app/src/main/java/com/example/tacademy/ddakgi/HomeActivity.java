package com.example.tacademy.ddakgi;

/**
 * Tab 변경해주면서 해당 Fragment 지정해주는 밑바탕 Activity
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;

import com.example.tacademy.ddakgi.TabFragment.ChatTab;
import com.example.tacademy.ddakgi.TabFragment.HomeTab;
import com.example.tacademy.ddakgi.TabFragment.LikeTab;
import com.example.tacademy.ddakgi.TabFragment.MyTab;
import com.example.tacademy.ddakgi.TabFragment.WriteTab;

public class HomeActivity extends AppCompatActivity {
    private TabLayout tabLayout;
    private LinearLayout fragment_container;

    // Filter Button을 Toolbar에 적용
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.filter, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();

        switch (itemId){
            case R.id.menu_filter:
                Intent intent = new Intent(this, FilterActivity.class);
                startActivity(intent);
                break;
        }
        return true;
    }

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
        // 액티비티에 toolbar 적용
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);

        // TabLayout 초기화
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.addTab(tabLayout.newTab().setText("HomeTab"));
        tabLayout.addTab(tabLayout.newTab().setText("ChatTab"));
        tabLayout.addTab(tabLayout.newTab().setText("WriteTab"));
        tabLayout.addTab(tabLayout.newTab().setText("LikeTab"));
        tabLayout.addTab(tabLayout.newTab().setText("MyTab"));
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
    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);

        transaction.commit();
    }

    // 화면 종료시 애니메이션
    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.act_slide_in_from_top, R.anim.act_slide_out_to_bottom);
    }
}