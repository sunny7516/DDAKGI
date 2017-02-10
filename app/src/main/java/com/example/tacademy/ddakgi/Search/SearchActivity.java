package com.example.tacademy.ddakgi.Search;

import android.content.res.ColorStateList;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.tacademy.ddakgi.R;
import com.example.tacademy.ddakgi.Search.fragment.SearchAll;
import com.example.tacademy.ddakgi.Search.fragment.SearchMap;

public class SearchActivity extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager viewPager;
    FragmentAdapter fragmentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        tabLayout = (TabLayout) findViewById(R.id.searchTablayout);
        viewPager = (ViewPager) findViewById(R.id.searchViewPager);

        // SearchView Style
        SearchView searchView = (SearchView) findViewById(R.id.fragSearchView);

        EditText searchEditText = (EditText) searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        searchEditText.setTextColor(getResources().getColor(R.color.black));
        searchEditText.setHintTextColor(getResources().getColor(R.color.gray));

        ImageView search_close_btn = (ImageView) searchView.findViewById(android.support.v7.appcompat.R.id.search_close_btn);
        ImageView search_icon = (ImageView) searchView.findViewById(android.support.v7.appcompat.R.id.search_mag_icon);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            search_close_btn.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.gray)));
            search_icon.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.gray)));
        }

        // 검색필드를 항상 표시하고 싶으면 false, 아이콘으로 보이고 싶으면 true
        searchView.setIconifiedByDefault(false);

        fragmentAdapter = new FragmentAdapter(getSupportFragmentManager());
        viewPager.setAdapter(fragmentAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    class FragmentAdapter extends FragmentPagerAdapter {

        // 프레그먼트 화면 개수 및 정의
        Fragment[] frags = new Fragment[]{
                new SearchAll(),
                new SearchMap()
        };

        // 프레그먼트 화면 제목 설정
        String[] titles = new String[]{
                "전체 검색", "지도로 검색"
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
