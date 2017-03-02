package com.example.tacademy.ddakgi.view.Home.act;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.tacademy.ddakgi.R;
import com.example.tacademy.ddakgi.base.BaseActivity;
import com.example.tacademy.ddakgi.view.Chat.ChatChannelModel;
import com.example.tacademy.ddakgi.view.Chat.ChatRoomActivity;
import com.example.tacademy.ddakgi.view.SignUp.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class HomeRoomDetailPageActivity extends BaseActivity {
    String auth_uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homeroom_detail_page);
    }

    // 채팅 신청 ====================================================================================
    DatabaseReference databaseReference;

    public void onChatRequest(View view) {
        // x. 채팅 신청하기
        databaseReference = FirebaseDatabase.getInstance().getReference();
        // 내 아이디가 유효하면
        databaseReference
                .child("users")
                .child(getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        User user = dataSnapshot.getValue(User.class);
                        if (user == null) {

                        } else {
                            checkYou();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }

    public void checkYou() {
        // 1. 상대방 아이디가 존재하는지 체크
        final String you_id = auth_uid;
        final String you_profile = "https://img.clipartfest.com/6d25d32351f5488391800817f379106f_10-woman-profile-silhouette-woman-profile-clipart_900-1412.png";
        databaseReference
                .child("users")
                .child(you_id)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        User user = dataSnapshot.getValue(User.class);
                        if (user == null) {
                            Toast.makeText(HomeRoomDetailPageActivity.this, "이미 탈퇴한 회원입니다.", Toast.LENGTH_SHORT).show();
                        } else {
                            // 나와 채팅방을 개설한 적 있는가
                            databaseReference.child("channel").child(getUid()).child(you_id)
                                    .addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                            ChatChannelModel checkKey = dataSnapshot.getValue(ChatChannelModel.class);
                                            if (checkKey != null) {
                                                // 이미 존재함 => 채팅방으로 이동: checkKey
                                                Toast.makeText(HomeRoomDetailPageActivity.this, "채팅방으로 이동합니다", Toast.LENGTH_SHORT).show();
                                                goChatting(checkKey.getChatting_channel());
                                            } else {
                                                // 채팅방 존재하지 않음 => 채널 생성 => 채팅방으로 이동
                                                makeChannel(you_id, you_profile);
                                            }
                                        }

                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {

                                        }
                                    });
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }
    public void makeChannel(String you_id, String you_profile){
        // channel에 데이터 설정
        ChatChannelModel ccm = new ChatChannelModel(
                you_id,
                getNickName()+"님이 채팅을 요청합니다",
                1,
                System.currentTimeMillis(),
                you_profile
        );

        // 입력 준비
        final String key = databaseReference.child("chatting").child("rooms").push().getKey();

        // 채팅방 번호 세팅
        ccm.setChatting_channel(key);
        // 데이터 가공
        Map<String, Object> postMap = ccm.toChannelMap();
        // 입력 데이터 준비
        Map<String, Object> updates = new HashMap<String, Object>();
        updates.put("/channel/" + getUid() +"/" + you_id, postMap);
        updates.put("/channel/" + you_id + "/" + getUid(), postMap);
        // 추가
        databaseReference.updateChildren(updates, new DatabaseReference.CompletionListener(){
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if(databaseError != null){
                    Log.i("CHAT", "오류" + databaseError.getMessage());
                }else{
                    // 채팅 채널 생성이 완료!
                    Toast.makeText(HomeRoomDetailPageActivity.this, "채팅방으로 이동합니다", Toast.LENGTH_SHORT).show();
                    goChatting(key);
                }
            }
        });
    }
    public void goChatting(String roomKey){
        Intent intent = new Intent(this, ChatRoomActivity.class);
        intent.putExtra("chatting_room_key", roomKey);
        //intent.putExtra("you", model);
        startActivity(intent);
    }
}