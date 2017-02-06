package com.example.tacademy.ddakgi.TabFragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.tacademy.ddakgi.HomeTab.FilterActivity;
import com.example.tacademy.ddakgi.R;
import com.example.tacademy.ddakgi.Adapter.RecyclerAdapter;
import com.example.tacademy.ddakgi.HomeTab.TimelineItem;

import java.util.ArrayList;
import java.util.List;

public class HomeTab extends Fragment {
    final int ITEM_SIZE = 3;
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;

    public HomeTab() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_tab, container, false);

        // Fragment toolbar
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.homeToolbar);

        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setTitle(null);
        setHasOptionsMenu(true);

        // Inflate the layout for this fragment
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);

        linearLayoutManager = new LinearLayoutManager(this.getContext());
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        // 가데이터
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
