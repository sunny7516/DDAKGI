package com.example.tacademy.ddakgi.view.Chat;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tacademy.ddakgi.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;


public class ChatTab extends Fragment {
    RecyclerView chat_channel_recyclerView;
    FirebaseRecyclerAdapter firebaseRecyclerAdapter;
    LinearLayoutManager linearLayoutManager;

    public ChatTab() {
        // Required empty public constructor
    }

    public Query getQuery(DatabaseReference databaseReference) {
        // 나의 채팅리스트 요청
        Query query = databaseReference.child("channel").child(getUid());
        return query;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chat_tab, container, false);
        // 화면 구성 세팅..
        chat_channel_recyclerView = (RecyclerView) view.findViewById(R.id.chat_channel_recyclerView);
        // 리사이클러뷰 위로 뭔가가 있어서 자동으로 올라가 가려지는 것을 막고,
        // 현재 위치를 유지시킨다.
        chat_channel_recyclerView.setFocusable(false);
        // 레이아웃 세팅
        linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setStackFromEnd(true);
        linearLayoutManager.setReverseLayout(true);
        chat_channel_recyclerView.setLayoutManager(linearLayoutManager);
        // 쿼리 수행
        Query query = getQuery(FirebaseDatabase.getInstance().getReference());
        // 아답터 생성
        firebaseRecyclerAdapter
                = new FirebaseRecyclerAdapter<ChatChannelModel, ChatChannelViewHolder>(
                ChatChannelModel.class,
                R.layout.cell_chat_channel_layout,
                ChatChannelViewHolder.class,
                // 쿼리 결과
                query
        ) {
            @Override
            protected void populateViewHolder(ChatChannelViewHolder viewHolder, ChatChannelModel model, int position) {
                // 1. position -> 데이터 획득 (참조 획득)
                final DatabaseReference databaseReference = getRef(position);
                // 1. 채팅방 키 획득 작성 실습
                // 2. viewHolder -> 이벤트 등록
                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // 채팅방으로 이동
                        Intent intent = new Intent(getContext(), ChatRoomActivity.class);
                        intent.putExtra("chatting_room_key", model.getChatting_channel());
                        // 채팅방으로 갈 때 상대방 정보에 대한 class를 담는게 정석이고,
                        // 여기서는 코드를 많이 변경하지 않는 범위에서 Post를 재활용하는 측면으로 구성된다.
                        // intent.putExtra("you", new Post("", "", model.getUid(), "quest"));
                        startActivity(intent);
                    }
                });
                viewHolder.bindToPost(getContext(), model);
            }
        };
        return view;
    }

    // 나의 아이디
    public String getUid() {
        return FirebaseAuth.getInstance().getCurrentUser().getUid();
    }
}
