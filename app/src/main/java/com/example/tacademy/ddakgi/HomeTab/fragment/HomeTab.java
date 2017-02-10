package com.example.tacademy.ddakgi.HomeTab.fragment;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.tacademy.ddakgi.Adapter.RecyclerAdapter;
import com.example.tacademy.ddakgi.HomeTab.activity.FilterActivity;
import com.example.tacademy.ddakgi.HomeTab.util.TimelineItem;
import com.example.tacademy.ddakgi.R;
import com.example.tacademy.ddakgi.Search.SearchActivity;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */

public class HomeTab extends Fragment {
    final int ITEM_SIZE = 3;
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    private boolean activityStartup = true;

    public HomeTab() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_tab, container, false);

        // SearchView Style
        SearchView searchView = (SearchView) view.findViewById(R.id.searchView);

        EditText searchEditText = (EditText) searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        searchEditText.setTextColor(getResources().getColor(R.color.black));
        searchEditText.setHintTextColor(getResources().getColor(R.color.gray));

        ImageView search_close_btn = (ImageView) searchView.findViewById(android.support.v7.appcompat.R.id.search_close_btn);
        ImageView search_icon = (ImageView) searchView.findViewById(android.support.v7.appcompat.R.id.search_mag_icon);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            search_close_btn.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.gray)));
            search_icon.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.gray)));
        }

        searchView.setIconifiedByDefault(false);
        // 처음 searchview 초기화 시 커서가 보이지 않도록 지정
        searchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    if (activityStartup) {
                        view.clearFocus();
                        activityStartup = false;
                    }
                }
            }
        });

        // Fragment toolbar
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.homeToolbar);
        // search화면으로 가는 ClickListener 적용
        Button gosearchBt = (Button) view.findViewById(R.id.gosearchBt);
        gosearchBt.setOnClickListener(goSearchListener);

        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setTitle(null);
        setHasOptionsMenu(true);

        // Inflate the layout for this fragment
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);

        linearLayoutManager = new LinearLayoutManager(this.getContext());
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        // 가데이터 > 수정해야됨
        List<TimelineItem> items = new ArrayList<>();
        TimelineItem[] item = new TimelineItem[ITEM_SIZE];
        item[0] = new TimelineItem(R.drawable.testroom0);
        item[1] = new TimelineItem(R.drawable.testroom1);
        item[2] = new TimelineItem(R.drawable.testroom2);

        for (int i = 0; i < ITEM_SIZE; i++) {
            items.add(item[i]);
        }
        recyclerView.setAdapter(new RecyclerAdapter(getContext(), items, R.layout.home_timeline));
        return view;
    }

    // Click Event
    private View.OnClickListener goSearchListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                // Toolbar에 있는 검색창 눌렀을 때 search화면으로 intent
                case R.id.gosearchBt:
                    Intent Intent = new Intent(getContext(), SearchActivity.class);
                    startActivity(Intent);
                    break;
            }
        }
    };

    // toolbar button =======================================================================
    @Override
    public void onResume() {
        super.onResume();
        // destroy all menu and re-call onCreateOptionsMenu
        getActivity().invalidateOptionsMenu();
    }

    // Filter Button을 Toolbar에 적용
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.hometab_menu, menu);

    }

    // toolbar 옵션 메뉴 선택 시 event
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_filter:
                Intent intent = new Intent(getActivity(), FilterActivity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
