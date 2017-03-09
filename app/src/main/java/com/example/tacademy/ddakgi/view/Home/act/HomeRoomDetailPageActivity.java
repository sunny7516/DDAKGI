package com.example.tacademy.ddakgi.view.Home.act;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tacademy.ddakgi.R;
import com.example.tacademy.ddakgi.base.BaseActivity;
import com.example.tacademy.ddakgi.data.DetailPost.DetailPosting;
import com.example.tacademy.ddakgi.data.DetailPost.ResDetailPosting;
import com.example.tacademy.ddakgi.data.Heart.ReqSetHeart;
import com.example.tacademy.ddakgi.data.NetSSL;
import com.example.tacademy.ddakgi.data.RegisterRoom.ResStringString;
import com.example.tacademy.ddakgi.view.Chat.ChatRoomActivity;
import com.example.tacademy.ddakgi.view.Chat.model.ChatChannelModel;
import com.example.tacademy.ddakgi.view.SignUp.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;
import de.hdodenhof.circleimageview.CircleImageView;
import io.chooco13.NotoTextView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 방 상세 페이지
 */

public class HomeRoomDetailPageActivity extends BaseActivity {
    String postKey;
    String auth_uid;

    public int roommate_id;
    DetailPosting detailPosting;
    RelativeLayout chatLayout;

    ImageView detailPageImg, roomHeart_state;
    NotoTextView DetailToolbarNickname, roomDetailPageLikeNum, percent, detailNicknameAge, percentTitle;
    NotoTextView detailPrice, detailLocate, detailRoomType, detailPay, detailOptions, detailRoomHeight, detailRoomSize, detailPrefDate;
    NotoTextView detailDescription, detailAnswer1, detailAnswer2, detailAnswer3, detailAnswer4, detailAnswer5;
    NotoTextView detailAnswer6, detailAnswer7, detailAnswer8, detailAnswer9, detailAnswer10;
    CircleImageView detailProfile;
    TextView chatString;

    NotoTextView roomModifyBt, roomDeleteBt;
    SweetAlertDialog alert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homeroom_detail_page);

        // 키 획득
        postKey = getIntent().getStringExtra("KEY");
        // 파라미터값 받아오기
        roommate_id = getIntent().getExtras().getInt("roommate_id");
        // 통신
        setDetail(roommate_id);

        // xml -> java
        chatLayout = (RelativeLayout) findViewById(R.id.chatLayout);
        chatString = (TextView) findViewById(R.id.chatString);

        roomModifyBt = (NotoTextView)findViewById(R.id.roomModifyBt);
        roomDeleteBt = (NotoTextView)findViewById(R.id.roomDeleteBt);

        percentTitle = (NotoTextView) findViewById(R.id.percentTitle);
        roomHeart_state = (ImageView) findViewById(R.id.roomHeart_state);
        DetailToolbarNickname = (NotoTextView) findViewById(R.id.DetailToolbarNickname);
        detailPageImg = (ImageView) findViewById(R.id.detailPageImg);
        roomDetailPageLikeNum = (NotoTextView) findViewById(R.id.roomDetailPageLikeNum);
        percent = (NotoTextView) findViewById(R.id.percent);
        detailNicknameAge = (NotoTextView) findViewById(R.id.detailNicknameAge);
        detailPrice = (NotoTextView) findViewById(R.id.detailPrice);
        detailLocate = (NotoTextView) findViewById(R.id.detailLocate);
        detailRoomType = (NotoTextView) findViewById(R.id.detailRoomType);
        detailPay = (NotoTextView) findViewById(R.id.detailPay);
        detailOptions = (NotoTextView) findViewById(R.id.detailOptions);
        detailRoomHeight = (NotoTextView) findViewById(R.id.detailRoomHeight);
        detailRoomSize = (NotoTextView) findViewById(R.id.detailRoomSize);
        detailPrefDate = (NotoTextView) findViewById(R.id.detailPrefDate);
        detailDescription = (NotoTextView) findViewById(R.id.detailDescription);
        detailAnswer1 = (NotoTextView) findViewById(R.id.detailAnswer1);
        detailAnswer2 = (NotoTextView) findViewById(R.id.detailAnswer2);
        detailAnswer3 = (NotoTextView) findViewById(R.id.detailAnswer3);
        detailAnswer4 = (NotoTextView) findViewById(R.id.detailAnswer4);
        detailAnswer5 = (NotoTextView) findViewById(R.id.detailAnswer5);
        detailAnswer6 = (NotoTextView) findViewById(R.id.detailAnswer6);
        detailAnswer7 = (NotoTextView) findViewById(R.id.detailAnswer7);
        detailAnswer8 = (NotoTextView) findViewById(R.id.detailAnswer8);
        detailAnswer9 = (NotoTextView) findViewById(R.id.detailAnswer9);
        detailAnswer10 = (NotoTextView) findViewById(R.id.detailAnswer10);
        detailProfile = (CircleImageView) findViewById(R.id.detailProfile);
    }

    boolean heartflag;

    // 클릭 이벤트
    public void clickHeart(View view) {
        int likeNum = Integer.parseInt(roomDetailPageLikeNum.getText().toString());
        if (!heartflag) {
            roomHeart_state.setImageResource(R.mipmap.heart_on_btn);
            roomDetailPageLikeNum.setText(String.valueOf(likeNum + 1));
            heartflag = true;
            setHeart();
        } else {
            roomHeart_state.setImageResource(R.mipmap.heart_off_btn);
            roomDetailPageLikeNum.setText(String.valueOf(likeNum - 1));
            heartflag = false;
            deleteHeart();
        }
    }

    public void deleteRoomPosting(View view){
        alert =
                new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                        .setContentText("글을 삭제하시겠습니까?")
                        .setConfirmText("삭제")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                // 삭제 버튼 누르면 화면에서 글 삭제
                                alert.dismissWithAnimation();
                                deleteDB();
                            }
                        })
                        .setCancelText("취소")
                        .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                alert.dismissWithAnimation();
                            }
                        });
        alert.setCancelable(true);
        alert.show();
    }

    // 통신 =========================================================================================

    // 글 삭제
    public void deleteDB(){
        Call<ResStringString> resDeletePosting =
                NetSSL.getInstance().getMemberImpFactory().resDeletePosting(detailPosting.getRid());
        resDeletePosting.enqueue(new Callback<ResStringString>() {
            @Override
            public void onResponse(Call<ResStringString> call, Response<ResStringString> response) {
                if (response.body().getResult() != null) {
                    Log.i("RF:DeleteMyPosting", "SUCCESS" + response.body().getResult());
                    Toast.makeText(HomeRoomDetailPageActivity.this, "글이 삭제되었습니다.", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Log.i("RF:DeleteMyPosting", "FAIL" + response.body().getError());
                }
            }

            @Override
            public void onFailure(Call<ResStringString> call, Throwable t) {
                Log.i("RF:DeleteMyPosting", "ERR" + t.getMessage());
            }
        });
    }

    // 찜 등록 통신
    public void setHeart() {
        Call<ResStringString> resReport = NetSSL.getInstance().getMemberImpFactory()
                .resSetHeart(new ReqSetHeart(roommate_id));
        resReport.enqueue(new Callback<ResStringString>() {
            @Override
            public void onResponse(Call<ResStringString> call, Response<ResStringString> response) {
                if (response.body().getResult() != null) {
                    Log.i("RF:SetHeart", "SUCCESS" + response.body().getResult());
                    Toast.makeText(HomeRoomDetailPageActivity.this, "찜 목록에 추가되었습니다.", Toast.LENGTH_SHORT).show();
                } else {
                    Log.i("RF:SetHeart", "FAIL" + response.body().getError());
                }
            }

            @Override
            public void onFailure(Call<ResStringString> call, Throwable t) {
                Log.i("RF:SetHeart", "ERR" + t.getMessage());
            }
        });
    }

    // 찜 삭제 통신
    public void deleteHeart() {
        Call<ResStringString> resDeleteHeartCall = NetSSL.getInstance().getMemberImpFactory()
                .resDeleteHeart(roommate_id);
        resDeleteHeartCall.enqueue(new Callback<ResStringString>() {
            @Override
            public void onResponse(Call<ResStringString> call, Response<ResStringString> response) {
                if (response.body().getResult() != null) {
                    Log.i("RF:deleteHeart", "SUCCESS" + response.body().getResult());
                    if (response.body().getResult() != null) {
                        Log.i("RF:deleteHeart", response.body().getResult() + "");
                        Toast.makeText(HomeRoomDetailPageActivity.this, "찜 목록에서 삭제되었습니다", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.i("RF:deleteHeart", "FAIL" + response.body().getError());
                }
            }

            @Override
            public void onFailure(Call<ResStringString> call, Throwable t) {
                Log.i("RF:deleteHeart", "ERR" + t.getMessage());
            }
        });
    }

    // 상세페이지 조회
    public void setDetail(int roommate_id) {
        Call<ResDetailPosting> resDetailPostingCall = NetSSL.getInstance().getMemberImpFactory().resDetailPosting(roommate_id);
        resDetailPostingCall.enqueue(new Callback<ResDetailPosting>() {
            @Override
            public void onResponse(Call<ResDetailPosting> call, Response<ResDetailPosting> response) {
                if (response.body().getResult() != null) {
                    Log.i("RF:Detail", "SUCCESS" + response.body().getResult());
                    if (response.body().getResult() != null) {
                        detailPosting = response.body().getResult();
                        if (detailPosting.getHeart_state() == 0) {
                            heartflag = false;
                        } else {
                            heartflag = true;
                        }
                        // 데이터 세팅
                        setData(detailPosting);
                    }
                } else {
                    Log.i("RF:Detail", "FAIL" + response.body().getError());
                }
            }

            @Override
            public void onFailure(Call<ResDetailPosting> call, Throwable t) {
                Log.i("RF:RoomMate", "ERR" + t.getMessage());
            }
        });
    }

    // 화면 데이터 세팅 ===============================================================================
    public void setData(DetailPosting detailPosting) {
        auth_uid = detailPosting.getUid();  // 게시글 작성자의 uid

        // 나 자신이면 매칭률, 채팅 안보이게함
        if (getUid() == auth_uid) {
            chatLayout.setVisibility(View.GONE);
            percent.setVisibility(View.GONE);
            percentTitle.setVisibility(View.GONE);
            chatString.setVisibility(View.GONE);

            // 수정 삭제 버튼
            roomModifyBt.setVisibility(View.VISIBLE);
            roomDeleteBt.setVisibility(View.VISIBLE);
        } else {
            chatLayout.setVisibility(View.VISIBLE);
            percent.setVisibility(View.VISIBLE);
            percentTitle.setVisibility(View.VISIBLE);
            chatString.setVisibility(View.VISIBLE);

            // 수정 삭제 버튼
            roomModifyBt.setVisibility(View.GONE);
            roomDeleteBt.setVisibility(View.GONE);
        }

        if (detailPosting.getHeart_state() == 0) {
            roomHeart_state.setImageResource(R.mipmap.heart_off_btn);
        } else {
            roomHeart_state.setImageResource(R.mipmap.heart_on_btn);
        }
        if (detailPosting.getRoommate_image() != null) {
            Picasso.with(this)
                    .load(detailPosting.getRoommate_image().get(0))
                    .fit()
                    .into(detailPageImg);
        }
        DetailToolbarNickname.setText(detailPosting.getNickname());
        roomDetailPageLikeNum.setText(String.valueOf(detailPosting.getHeart_count()));
        percent.setText(detailPosting.getMatching_rate() + "%");
        detailNicknameAge.setText(detailPosting.getNickname() + " 님 / " + detailPosting.getAge() + "세");
        detailPrice.setText("가격 : " + detailPosting.getDeposit() + "/" + detailPosting.getRent());
        detailLocate.setText("위치 : " + detailPosting.getAddress());
        detailRoomType.setText("방 유형 : " + detailPosting.getRoom_type());
        detailPay.setText("관리비 : " + detailPosting.getManage_cost() + "(" + detailPosting.getManage_cost_inc() + ")");
        detailOptions.setText("옵션 : " + detailPosting.getOptions());
        detailRoomHeight.setText("층수 : " + detailPosting.getFloor());
        detailRoomSize.setText("면적 : " + detailPosting.getSize());
        detailPrefDate.setText("입주 가능일 : " + detailPosting.getAvailable_date().split("T")[0]);

        detailDescription.setText(detailPosting.getDescription());

        detailAnswer1.setText(detailPosting.getLifestyle_q1());
        detailAnswer2.setText(detailPosting.getLifestyle_q2());
        detailAnswer3.setText(detailPosting.getLifestyle_q3());
        detailAnswer4.setText(detailPosting.getLifestyle_q4());
        detailAnswer5.setText(detailPosting.getLifestyle_q5());
        detailAnswer6.setText(detailPosting.getLifestyle_q6());
        detailAnswer7.setText(detailPosting.getLifestyle_q7());
        detailAnswer8.setText(detailPosting.getLifestyle_q8());
        detailAnswer9.setText(detailPosting.getLifestyle_q9());
        detailAnswer10.setText(detailPosting.getLifestyle_q10());
        if (detailPosting.getThumbnail_image() != null) {
            Picasso.with(this)
                    .load(detailPosting.getThumbnail_image())
                    .fit()
                    .into(detailProfile);
        } else {
            detailProfile.setImageResource(R.mipmap.profile);
        }
/*
        if (auth_uid.equals(getUid())) {
            findViewById(R.id.chatBt).setVisibility(GONE);
        }*/
    }

    // 채팅 신청 ====================================================================================
    DatabaseReference databaseReference;

    public void onChatRequest(View view) {
        showProgress("채팅방으로 이동합니다");

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
                            Log.i("chat", "내 아이디 없음");
                        } else {
                            Log.i("chat", "checkYou()");
                            checkYou();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.i("chat", "error" + databaseError.toString());
                    }
                });
    }

    // 존재하는 회원인지 확인
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
                                                //Toast.makeText(HomeRoomDetailPageActivity.this, "채팅방으로 이동합니다", Toast.LENGTH_SHORT).show();
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
                        Log.i("chat", "checkYou:error" + databaseError.toString());
                    }
                });
        hideProgress();
    }

    // 채팅방 최초 생성
    public void makeChannel(String you_id, String you_profile) {
        // channel에 데이터 설정
        ChatChannelModel ccm = new ChatChannelModel(
                detailPosting.getMid(),
                you_id,
                getNickName() + "님이 채팅을 요청합니다",
                1,
                System.currentTimeMillis(),
                you_profile
        );
        Log.i("CHAT", detailPosting.getMid() + "/" + getUid() + "/" + you_id);

        // 입력 준비
        final String key = databaseReference.child("chatting").child("rooms").push().getKey();

        // 채팅방 번호 세팅
        ccm.setChatting_channel(key);
        // 데이터 가공
        Map<String, Object> postMap = ccm.toChannelMap();
        // 입력 데이터 준비
        Map<String, Object> updates = new HashMap<String, Object>();
        updates.put("/channel/" + getUid() + "/" + you_id, postMap);
        updates.put("/channel/" + you_id + "/" + getUid(), postMap);
        // 추가
        databaseReference.updateChildren(updates, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if (databaseError != null) {
                    Log.i("CHAT", "오류" + databaseError.getMessage());
                } else {
                    // 채팅 채널 생성이 완료!
                    Toast.makeText(HomeRoomDetailPageActivity.this, "채팅방으로 이동합니다", Toast.LENGTH_SHORT).show();
                    goChatting(key);
                }
            }
        });
    }

    // 이미 존재하는 채팅방으로 이동
    public void goChatting(String roomKey) {
        Intent intent = new Intent(this, ChatRoomActivity.class);
        intent.putExtra("chatting_room_key", roomKey);
        intent.putExtra("you", detailPosting);
        intent.putExtra("other_member_id", detailPosting.getMid());
        startActivity(intent);
    }
}