package com.example.tacademy.ddakgi.LikeTab;

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

import com.example.tacademy.ddakgi.Adapter.LikeRecyclerAdapter;
import com.example.tacademy.ddakgi.MyTab.util.MyTimelineItem;
import com.example.tacademy.ddakgi.R;

import java.util.ArrayList;
import java.util.List;

import io.chooco13.NotoTextView;


public class LikeTab extends Fragment {

    final int ITEM_SIZE = 3;
    RecyclerView recyclerviewLikeTab;
    LinearLayoutManager linearLayoutManager;

    Toolbar toolbar;
    NotoTextView deleteBt;

    public LikeTab() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_like_tab, container, false);

        // Fragment toolbar 적용하기
        toolbar = (Toolbar) getActivity().findViewById(R.id.likeToolbar);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);

        deleteBt = (NotoTextView)getActivity().findViewById(R.id.deleteBt);

        // Inflate the layout for this fragment
        recyclerviewLikeTab = (RecyclerView) view.findViewById(R.id.recyclerviewLikeTab);
        linearLayoutManager = new LinearLayoutManager(this.getContext());
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        recyclerviewLikeTab.setLayoutManager(linearLayoutManager);

        // 가데이터
        List<MyTimelineItem> items = new ArrayList<>();
        MyTimelineItem[] item = new MyTimelineItem[ITEM_SIZE];
        item[0] = new MyTimelineItem(R.drawable.testroom0);
        item[1] = new MyTimelineItem(R.drawable.testroom1);
        item[2] = new MyTimelineItem(R.drawable.testroom2);

        for (int i = 0; i < ITEM_SIZE; i++) {
            items.add(item[i]);
        }
        recyclerviewLikeTab.setAdapter(new LikeRecyclerAdapter(getContext(), items, R.layout.home_timeline));

        return view;
    }
}
