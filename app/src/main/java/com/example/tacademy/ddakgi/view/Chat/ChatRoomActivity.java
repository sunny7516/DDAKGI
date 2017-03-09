package com.example.tacademy.ddakgi.view.Chat;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;

import com.example.tacademy.ddakgi.R;
import com.example.tacademy.ddakgi.base.BaseActivity;
import com.example.tacademy.ddakgi.util.U;
import com.example.tacademy.ddakgi.view.Chat.holder.PostHolder;
import com.example.tacademy.ddakgi.view.Chat.model.ChatModel;
import com.example.tacademy.ddakgi.view.Report.reportActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ChatRoomActivity extends BaseActivity {

    // UI
    RecyclerView recyclerView;
    AutoCompleteTextView msg_input;
    ChatRoomActivity.MyAdapter myAdapter = new ChatRoomActivity.MyAdapter();
    LinearLayoutManager linearLayoutManager;

    // FireBase
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    // 데이터 관리
    ArrayList<ChatModel> arrayList = new ArrayList<ChatModel>();
    int index[] = new int[1000];
    String chatting_room_key;

    @Override
    public void finish() {
        U.getInstance().setChattingRoomInside(false);   // 채팅방을 나갔다!
        super.finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_room_activiy);
        U.getInstance().setChattingRoomInside(true);    // 채팅방에 진입!

        // 데이터 받기
        chatting_room_key = getIntent().getStringExtra("chatting_room_key");
        // 콤퍼넌트 리소스를 자바 객체로 연결
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        msg_input = (AutoCompleteTextView) findViewById(R.id.msg_input);
        linearLayoutManager = new LinearLayoutManager(this);    // 선형
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setAdapter(myAdapter); // 데이터 공급원 아답터 연결
        // FB
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        databaseReference.child("chatting").child("rooms").child(chatting_room_key)
                .addChildEventListener(new ChildEventListener() {
                    // 아이템을 검색하거나, 추가될 때 호출(select, insert)
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        ChatModel chatMessage = dataSnapshot.getValue(ChatModel.class);
                        // 추가로 확보된 데이터 리스트에 추가
                        arrayList.add(chatMessage);
                        // 위치를 마지막 메시지 자리로
                        linearLayoutManager.scrollToPosition(arrayList.size() - 1);
                        // 갱신
                        myAdapter.notifyDataSetChanged();
                    }

                    // 아이템의 변화가 감지되면 호출 (udpate)
                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                    }

                    // 아이템이 삭제되면 호출 (delete)
                    @Override
                    public void onChildRemoved(DataSnapshot dataSnapshot) {

                    }

                    // 아이템의 순서가 변경되면 호출
                    @Override
                    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }
    int other_member_id;
    public void goReport(View view) {
        // 상세페이지에서 채팅 신고했을 경우
        other_member_id = getIntent().getExtras().getInt("other_member_id");
        Intent intent = new Intent(this, reportActivity.class);
        intent.putExtra("other_member_id", other_member_id);
        startActivity(intent);
        this.finish();
    }

    // 전송 버튼 누르면 호출
    public void onSend(View view) {
        // 1. 입력데이터 추출
        final String msg = msg_input.getText().toString();
        long sendTime = System.currentTimeMillis();
        ChatModel chatModel = new ChatModel(
                getNickName(),
                msg,
                "t",
                sendTime,
                1
        );

        // 2. 서버 전송 => 여기서는 데이터 직접 추가
        databaseReference.child("chatting").child("rooms").child(chatting_room_key)
                .child(sendTime + "")
                .setValue(chatModel)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.i("CHAT", "등록완료");
                            // 1. 상대방에게 푸시메시지 발송!
                            // 상대방도 채팅방에서 채팅중이면 푸시메시지는 무시
                            // 앱이 꺼져 있다면(노티 혹은 메시지 팝업) 알림 -> 닫기, 확인 -> 앱을 가동시켜서 -> 자동로그인 -> 채팅방 이동
                            // 앱 내에 다른 위치에 있다면, 취향대로! (로그인 화면이 아닌경우) -> 바로 진입
                        }
                    }
                });
        // 5. 입력값 제거
        msg_input.setText("");
    }

    // 아답터
    class MyAdapter extends RecyclerView.Adapter {
        // viewHolder 생성
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            // xml -> view
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.sendbird_view_group_user_message, parent, false);
            return new PostHolder(itemView);
        }

        // ViewHolder에 데이터로 설정(바인딩)한다.
        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            // 보이고자 하는 셀에 내용을 설정한다.
            PostHolder ph = ((PostHolder) holder);
            ph.bindOnPost(ChatRoomActivity.this, arrayList.get(position), getNickName());
            // 일단 초기화
            ph.initProfile();
            if (position > 0) {
                ChatModel cmPre = arrayList.get(position - 1);
                ChatModel cmCur = arrayList.get(position);
                Log.i("CHAT", "cmPre.getSender():" + cmPre.getSender());
                Log.i("CHAT", "cmCur.getSender():" + cmCur.getSender());
                Log.i("CHAT", "getNickName():" + getNickName());
                if (cmPre.getSender().equals(cmCur.getSender())) {
                    // 이전과 동일한 사람이 보낸것이면
                    if (cmCur.getSender().equals(getNickName())) {
                        Log.i("CHAT", "나 숨겨");
                        ph.hideProfile(1);
                    } else {
                        Log.i("CHAT", "너 숨겨");
                        ph.hideProfile(2);
                    }
                }
            }
        }

        // 데이터의 개수
        @Override
        public int getItemCount() {
            return arrayList.size();
        }
    }
}
