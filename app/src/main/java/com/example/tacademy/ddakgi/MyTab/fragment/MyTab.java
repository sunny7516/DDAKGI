package com.example.tacademy.ddakgi.MyTab.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.tacademy.ddakgi.Adapter.MyRecyclerAdapter;
import com.example.tacademy.ddakgi.MyTab.util.MyTimelineItem;
import com.example.tacademy.ddakgi.R;

import java.util.ArrayList;
import java.util.List;


public class MyTab extends Fragment {

    final int ITEM_SIZE = 3;
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;

    Toolbar toolbar;
    ImageButton helpBt;
    ImageButton settingBt;

    public MyTab() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_tab, container, false);

        // Fragment toolbar 적용하기
        toolbar = (Toolbar) getActivity().findViewById(R.id.myTabTool);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);

        // Toolbar에 있는 버튼
        helpBt = (ImageButton) view.findViewById(R.id.helpBt);
        settingBt = (ImageButton) view.findViewById(R.id.settingBt);
        helpBt.setOnClickListener(onClickListener);
        settingBt.setOnClickListener(onClickListener);

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

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.helpBt:
                    Intent roomIntent = new Intent(getContext(), HelpActivity.class);
                    startActivity(roomIntent);
                    break;
                case R.id.settingBt:
                    Intent mateIntent = new Intent(getContext(), SettingActivity.class);
                    startActivity(mateIntent);
                    break;
            }
        }
    };
}