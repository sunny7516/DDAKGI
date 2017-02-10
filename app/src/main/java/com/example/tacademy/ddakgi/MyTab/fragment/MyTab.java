package com.example.tacademy.ddakgi.MyTab.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
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

import com.example.tacademy.ddakgi.Adapter.MyRecyclerAdapter;
import com.example.tacademy.ddakgi.MyTab.activity.HelpActivity;
import com.example.tacademy.ddakgi.MyTab.activity.SettingActivity;
import com.example.tacademy.ddakgi.MyTab.util.MyTimelineItem;
import com.example.tacademy.ddakgi.R;

import java.util.ArrayList;
import java.util.List;


public class MyTab extends Fragment {

    final int ITEM_SIZE = 3;
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;


    public MyTab() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_tab, container, false);

        // Fragment toolbar
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.myToolbar);

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
        List<MyTimelineItem> items = new ArrayList<>();
        MyTimelineItem[] item = new MyTimelineItem[ITEM_SIZE];
        item[0] = new MyTimelineItem(R.drawable.testroom0);
        item[1] = new MyTimelineItem(R.drawable.testroom1);
        item[2] = new MyTimelineItem(R.drawable.testroom2);

        for (int i = 0; i < ITEM_SIZE; i++) {
            items.add(item[i]);
        }
        recyclerView.setAdapter(new MyRecyclerAdapter(getContext(), items, R.layout.home_timeline));
        return view;
    }

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
        inflater.inflate(R.menu.mytab_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_help:
                FragmentIntent(new HelpActivity());
                break;
            case R.id.menu_settings:
                FragmentIntent(new SettingActivity());
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    // Fragment 화면 이동(Filter화면 띄우기)
    private void FragmentIntent(Fragment fragment) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction transaction = fragmentManager.beginTransaction();

        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
