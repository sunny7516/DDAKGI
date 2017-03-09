package com.example.tacademy.ddakgi.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.tacademy.ddakgi.R;
import com.example.tacademy.ddakgi.data.DetailPost.ResDetailPosting;
import com.example.tacademy.ddakgi.data.Heart.ReqSetHeart;
import com.example.tacademy.ddakgi.data.HomeTimeline.HomePostingModel;
import com.example.tacademy.ddakgi.data.HomeTimeline.ResHomePosting;
import com.example.tacademy.ddakgi.data.NetSSL;
import com.example.tacademy.ddakgi.data.RegisterRoom.ResStringString;
import com.example.tacademy.ddakgi.view.Home.act.HomeRoomDetailPageActivity;
import com.example.tacademy.ddakgi.view.Home.act.HomemateDetailPageActivity;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;
import io.chooco13.NotoTextView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * HomeTab에 있는 Recyclerview에 적용하는 Adapter
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    Context context;
    ResHomePosting items;
    int item_layout;

    public RecyclerAdapter(Context context, ResHomePosting items, int item_layout) {
        this.context = context;
        this.items = items;
        this.item_layout = item_layout;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_timeline, null);
        return new ViewHolder(v);
    }

    int heart_state;

    boolean myHeart;

    // 각 position에 해당하는 view에 데이터 연결하는 부분
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        heart_state = items.getResult().get(position).getHeart_state();

        HomePostingModel item = items.getResult().get(position);
        heart_state = item.getHeart_state();

        if (heart_state == 0) {
            myHeart = false;
            holder.mLike.setImageResource(R.mipmap.heart_off_btn);
        } else {
            myHeart = true;
            holder.mLike.setImageResource(R.mipmap.heart_on_btn);
        }

        holder.mNickname.setText(item.getNickname());
        holder.mDate.setText(item.getCtime().split("T")[0]);
        holder.mTitle.setText(item.getTitle());
        holder.mAge.setText(String.valueOf(item.getAge()));
        holder.mLocation.setText(item.getAddress());
        holder.mPrice.setText(item.getDeposit() + "/" + item.getRent());
        holder.mLikeNum.setText(String.valueOf(item.getHeart_count()));
        if (item.getThumbnail_image() != null) {
            Picasso.with(context).load(item.getThumbnail_image()).fit().into(holder.mProfile);
        } else {
            holder.mProfile.setImageResource(R.mipmap.profile);
        }
        holder.matching_rate.setText(item.getMatching_rate() + "%");
        if (item.getRoommate_image() != null) {
            Picasso.with(context).load(item.getRoommate_image()[0])
                    .fit()
                    .into(holder.mPhoto);
        }

        holder.timelineLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int rid = items.getResult().get(position).getRid();
                // 게시물의 rooming값을 확인하고 게시물 종류를 구분하기 위해서
                isRoomMate(rid);
            }
        });

        holder.mLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!myHeart) {
                    myHeart = true;
                    // 좋아요 채우기
                    holder.mLike.setImageResource(R.mipmap.heart_on_btn);
                    // 좋아요 개수 증가
                    int likeNum = Integer.parseInt(holder.mLikeNum.getText().toString());
                    holder.mLikeNum.setText(String.valueOf(likeNum + 1));
                    // 찜 등록 통신
                    setHeart(position);
                } else if (myHeart) {
                    myHeart = false;
                    // 좋아요 없애기
                    holder.mLike.setImageResource(R.mipmap.heart_off_btn);
                    // 좋아요 개수 감소
                    int likeNum = Integer.parseInt(holder.mLikeNum.getText().toString());
                    holder.mLikeNum.setText(String.valueOf(likeNum - 1));
                    // 찜 삭제 통신
                    deleteHeart(position);
                }
            }
        });
    }

    // 찜 등록 통신
    public void setHeart(int position) {
        Call<ResStringString> resReport = NetSSL.getInstance().getMemberImpFactory()
                .resSetHeart(new ReqSetHeart(items.getResult().get(position).getRid()));
        resReport.enqueue(new Callback<ResStringString>() {
            @Override
            public void onResponse(Call<ResStringString> call, Response<ResStringString> response) {
                if (response.body().getResult() != null) {
                    Log.i("RF:SetHeart", "SUCCESS" + response.body().getResult());
                    Toast.makeText(context, "찜 목록에 추가되었습니다.", Toast.LENGTH_SHORT).show();
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
    public void deleteHeart(int position) {
        Call<ResStringString> resDeleteHeartCall = NetSSL.getInstance().getMemberImpFactory()
                .resDeleteHeart(items.getResult().get(position).getRid());
        resDeleteHeartCall.enqueue(new Callback<ResStringString>() {
            @Override
            public void onResponse(Call<ResStringString> call, Response<ResStringString> response) {
                if (response.body().getResult() != null) {
                    Log.i("RF:deleteHeart", "SUCCESS" + response.body().getResult());
                    if (response.body().getResult() != null) {
                        Log.i("RF:deleteHeart", items.getResult() + "");
                        Toast.makeText(context, "찜 목록에서 삭제되었습니다", Toast.LENGTH_SHORT).show();
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

    public int roomOrMate;

    // 상단 all/room/mate 버튼 눌렀을 때 데이터 변경
    public void isRoomMate(int roommate_id) {
        Call<ResDetailPosting> resDetailPostingCall = NetSSL.getInstance().getMemberImpFactory().resDetailPosting(roommate_id);
        resDetailPostingCall.enqueue(new Callback<ResDetailPosting>() {
            @Override
            public void onResponse(Call<ResDetailPosting> call, Response<ResDetailPosting> response) {
                if (response.body().getResult() != null) {
                    Log.i("RF:Detail", "SUCCESS" + response.body().getResult());
                    if (response.body().getResult() != null) {
                        roomOrMate = response.body().getResult().getRoomming();
                        // roomming이 0이면 룸메, 1이면 방 상세화면으로 이동
                        // 게시물 번호를 같이 넘겨줘서 상세정보를 불러올 수 있도록 한다.
                        if (roomOrMate == 0) {
                            Intent detailMatePage = new Intent(context, HomemateDetailPageActivity.class);
                            detailMatePage.putExtra("roommate_id", roommate_id);
                            context.startActivity(detailMatePage);
                        } else if (roomOrMate == 1) {
                            Intent detailRoomPage = new Intent(context, HomeRoomDetailPageActivity.class);
                            detailRoomPage.putExtra("roommate_id", roommate_id);
                            context.startActivity(detailRoomPage);
                        }
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

    // 항목의 개수
    @Override
    public int getItemCount() {
        return this.items.getResult().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        // 화면에 있는 값들
        NotoTextView mNickname, mDate, mAge, mPrice, mLocation, mLikeNum, mTitle, matching_rate;
        ImageView mPhoto;
        CircleImageView mProfile;
        ImageButton mLike;

        LinearLayout timelineLinear;

        public ViewHolder(View itemView) {
            super(itemView);
            this.matching_rate = (NotoTextView) itemView.findViewById(R.id.matching_rate);
            this.mProfile = (CircleImageView) itemView.findViewById(R.id.mProfile);
            this.mNickname = (NotoTextView) itemView.findViewById(R.id.mNickname);
            this.mDate = (NotoTextView) itemView.findViewById(R.id.mDate);
            this.mPhoto = (ImageView) itemView.findViewById(R.id.mPhoto);
            this.mTitle = (NotoTextView) itemView.findViewById(R.id.mTitle);
            this.mAge = (NotoTextView) itemView.findViewById(R.id.mAge);
            this.mPrice = (NotoTextView) itemView.findViewById(R.id.mPrice);
            this.mLocation = (NotoTextView) itemView.findViewById(R.id.mLocation);
            this.mLikeNum = (NotoTextView) itemView.findViewById(R.id.mLikeNum);
            this.mPhoto = (ImageView) itemView.findViewById(R.id.mPhoto);
            this.timelineLinear = (LinearLayout) itemView.findViewById(R.id.timelineLinear);
            this.mLike = (ImageButton) itemView.findViewById(R.id.mLike);
        }
    }
}
