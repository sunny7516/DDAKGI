package com.example.tacademy.ddakgi.view.Home.act;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.example.tacademy.ddakgi.R;
import com.example.tacademy.ddakgi.base.BaseActivity;
import com.example.tacademy.ddakgi.data.DetailPost.DetailPosting;
import com.example.tacademy.ddakgi.data.DetailPost.ResDetailPosting;
import com.example.tacademy.ddakgi.data.NetSSL;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;
import io.chooco13.NotoTextView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomemateDetailPageActivity extends BaseActivity {
    ImageView mateDetailPageImg;
    CircleImageView mateDetailProfile;
    NotoTextView detailMateToolbar, mateDetailPageLikeNum, mateDeatilPercent, mateDetailNicknameAge,
            mateDetailPrice, mateDetailLocate, mateDetailRoomType, mateDetailPrefDate, mateDetailDescription,
            detailAnswer1, detailAnswer2, detailAnswer3, detailAnswer4, detailAnswer5,
            detailAnswer6, detailAnswer7, detailAnswer8, detailAnswer9, detailAnswer10;
    ImageView heart_state;
    int roommate_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homemate_detail_page);

        // 파라미터값 받아오기
        roommate_id = getIntent().getIntExtra("roommate_id", 0);
        // 통신
        setRetrofit(roommate_id);
        heart_state = (ImageView) findViewById(R.id.heart_state);
        mateDetailPageImg = (ImageView) findViewById(R.id.mateDetailPageImg);
        mateDetailProfile = (CircleImageView) findViewById(R.id.mateDetailProfile);

        detailMateToolbar = (NotoTextView) findViewById(R.id.detailMateToolbar);
        mateDetailPageLikeNum = (NotoTextView) findViewById(R.id.mateDetailPageLikeNum);
        mateDeatilPercent = (NotoTextView) findViewById(R.id.mateDeatilPercent);
        mateDetailNicknameAge = (NotoTextView) findViewById(R.id.mateDetailNicknameAge);
        mateDetailPrice = (NotoTextView) findViewById(R.id.mateDetailPrice);
        mateDetailLocate = (NotoTextView) findViewById(R.id.mateDetailLocate);
        mateDetailRoomType = (NotoTextView) findViewById(R.id.mateDetailRoomType);
        mateDetailPrefDate = (NotoTextView) findViewById(R.id.mateDetailPrefDate);
        mateDetailDescription = (NotoTextView) findViewById(R.id.mateDetailDescription);

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
    }

    public void setRetrofit(int roommate_id) {
        Call<ResDetailPosting> resDetailPostingCall = NetSSL.getInstance().getMemberImpFactory().resDetailPosting(111);
        resDetailPostingCall.enqueue(new Callback<ResDetailPosting>() {
            @Override
            public void onResponse(Call<ResDetailPosting> call, Response<ResDetailPosting> response) {
                if (response.body().getResult() != null) {
                    Log.i("RF:Detail", "SUCCESS" + response.body().getResult());
                    if (response.body().getResult() != null) {
                        DetailPosting detailPosting = response.body().getResult();
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

    public void setData(DetailPosting detailPosting) {
        if (String.valueOf(detailPosting.getHeart_state()) != null) {
            heart_state.setImageResource(R.mipmap.heart_on_btn);
        } else {
            heart_state.setImageResource(R.mipmap.heart_off_btn);
        }
        Picasso.with(this)
                .load(detailPosting.getRoommate_image().get(0))
                .fit()
                .into(mateDetailPageImg);
        detailMateToolbar.setText(detailPosting.getNickname());
        mateDetailPageLikeNum.setText(String.valueOf(detailPosting.getHeart_count()));
        mateDeatilPercent.setText(detailPosting.getMatching_rate() + "%");
        mateDetailNicknameAge.setText(detailPosting.getNickname() + " 님 / " + detailPosting.getAge() + "세");
        mateDetailPrice.setText("가격 : " + detailPosting.getDeposit() + "/" + detailPosting.getRent());
        mateDetailLocate.setText("위치 : " + detailPosting.getAddress());
        mateDetailRoomType.setText("방 유형 : " + detailPosting.getRoom_type());
        mateDetailPrefDate.setText("입주 가능일 : " + detailPosting.getAvailable_date().split("T")[0]);

        mateDetailDescription.setText(detailPosting.getDescription());

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
        if (detailPosting.getThumbnail_image() == null) {
            mateDetailProfile.setImageResource(R.mipmap.profile);
        } else {
            Picasso.with(this)
                    .load(detailPosting.getThumbnail_image())
                    .fit()
                    .into(mateDetailProfile);
        }
    }
}
