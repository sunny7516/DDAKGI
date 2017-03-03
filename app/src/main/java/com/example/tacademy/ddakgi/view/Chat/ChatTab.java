package com.example.tacademy.ddakgi.view.Chat;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tacademy.ddakgi.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;


public class ChatTab extends Fragment {
    RecyclerView chat_channel_recyclerView;
    FirebaseRecyclerAdapter firebaseRecyclerAdapter;
    LinearLayoutManager linearLayoutManager;

    public ChatTab() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chat_tab, container, false);
        // 화면 구성 세팅..
        chat_channel_recyclerView = (RecyclerView)view.findViewById(R.id.chat_channel_recyclerView);
        // 리사이클러뷰 위로 뭔가가 있어서 자동으로 올라가 가려지는 것을 막고,
        // 현재 위치를 유지시킨다.
        chat_channel_recyclerView.setFocusable(false);
        // 레이아웃 세팅
        linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setStackFromEnd(true);
        linearLayoutManager.setReverseLayout(true);
        chat_channel_recyclerView.setLayoutManager(linearLayoutManager);
        return view;
    }
}
