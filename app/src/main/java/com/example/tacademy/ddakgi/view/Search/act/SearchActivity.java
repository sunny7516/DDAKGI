package com.example.tacademy.ddakgi.view.Search.act;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.Toast;

import com.example.tacademy.ddakgi.R;
import com.example.tacademy.ddakgi.base.BaseActivity;
import com.example.tacademy.ddakgi.view.Search.frag.SearchAll;
import com.example.tacademy.ddakgi.view.Search.frag.SearchMap;

public class SearchActivity extends BaseActivity {

    TabLayout tabLayout;
    ViewPager viewPager;
    FragmentAdapter fragmentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        tabLayout = (TabLayout) findViewById(R.id.searchTablayout);
        viewPager = (ViewPager) findViewById(R.id.searchViewPager);
        tabLayout.setTabTextColors(getResources().getColor(R.color.subTextColor), getResources().getColor(R.color.textpointColor));

        fragmentAdapter = new FragmentAdapter(getSupportFragmentManager());
        viewPager.setAdapter(fragmentAdapter);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 1:
                        Toast.makeText(getApplicationContext(), "내 위치와 50m 정도 차이 날 수 있습니다.", Toast.LENGTH_SHORT).show();
                }
                Toast.makeText(getApplicationContext(), "위치 기반 검색은 준비중입니다. 죄송합니다.", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    class FragmentAdapter extends FragmentPagerAdapter {

        // 프레그먼트 화면 개수 및 정의
        Fragment[] frags = new Fragment[]{
                new SearchAll(),
                new SearchMap()
        };

        // 프레그먼트 화면 제목 설정
        String[] titles = new String[]{
                "통합 검색", "지도로 검색"
        };

        // 생성자

        public FragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        // 보여줄 프레그먼트
        @Override
        public Fragment getItem(int position) {
            return frags[position];
        }

        // 프레그먼트 개수
        @Override
        public int getCount() {
            return frags.length;
        }

        // 제목 적용하기

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }
    }
}
