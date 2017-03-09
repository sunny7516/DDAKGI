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
import android.widget.TextView;
import android.widget.Toast;

import com.example.tacademy.ddakgi.R;
import com.example.tacademy.ddakgi.data.Heart.HeartPosing;
import com.example.tacademy.ddakgi.data.Heart.ResHeartPosting;
import com.example.tacademy.ddakgi.data.NetSSL;
import com.example.tacademy.ddakgi.data.RegisterRoom.ResStringString;
import com.example.tacademy.ddakgi.view.Home.act.HomeRoomDetailPageActivity;
import com.example.tacademy.ddakgi.view.Home.act.HomemateDetailPageActivity;
import com.squareup.picasso.Picasso;

import cn.pedant.SweetAlert.SweetAlertDialog;
import de.hdodenhof.circleimageview.CircleImageView;
import io.chooco13.NotoTextView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * LikeTab에 있는 Recyclerview에 적용하는 Adapter
 */

public class LikeRecyclerAdapter extends RecyclerView.Adapter<LikeRecyclerAdapter.ViewHolder> {

    Context context;
    ResHeartPosting items;
    int item_layout;

    SweetAlertDialog alert;

    public LikeRecyclerAdapter(Context context, ResHeartPosting items, int item_layout) {
        this.context = context;
        this.items = items;
        this.item_layout = item_layout;
    }

    @Override
    public LikeRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_timeline, null);
        return new LikeRecyclerAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(LikeRecyclerAdapter.ViewHolder holder, int position) {
        HeartPosing item = items.getResult().get(position);

        holder.mLike.setImageResource(R.mipmap.heart_on_btn);
        holder.mLike.setClickable(false);
        Picasso.with(context).load(item.getThumbnail_image()).fit().into(holder.mPhoto);
        holder.mNickname.setText(item.getNickname());
        holder.mDate.setText(item.getCtime().split("T")[0]);
        holder.matching_rate.setText(String.valueOf(item.getMatching_rate()));
        holder.mTitle.setText(item.getTitle());
        holder.mPrice.setText(item.getDeposit() + "/" + item.getRent());
        holder.mAge.setText(String.valueOf(item.getAge()));
        holder.mLocation.setText(item.getAddress());
        holder.mLikeNum.setText(String.valueOf(item.getHeart_count()));
        Picasso.with(context).load(item.getRoommate_image()[0]).fit().into(holder.mPhoto);

        holder.deleteBt.setVisibility(View.VISIBLE);
        holder.deleteBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 삭제 버튼 누르면 글 삭제할건지 확인
                alert =
                        new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE)
                                .setContentText("찜 목록에서 삭제하시겠습니까?")
                                .setConfirmText("삭제")
                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sDialog) {
                                        // 화면에서 삭제
                                        alert.dismissWithAnimation();
                                        // 데이터 처리
                                        removeLike(position);

                                        items.getResult().remove(position);
                                        notifyItemRemoved(position);
                                        notifyItemRangeChanged(position, items.getResult().size());
                                        holder.itemView.setVisibility(View.GONE);
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
        });

        // 타임라인 글을 선택하면 상세페이지로 넘어감
        holder.linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent detailPage;

                // 넘어갈 때 이미지 정보 같이 전달함
                if (items.getResult().get(position).getRoomming() == 0) {
                    detailPage = new Intent(v.getContext(), HomemateDetailPageActivity.class);
                } else {
                    detailPage = new Intent(v.getContext(), HomeRoomDetailPageActivity.class);
                }
                detailPage.putExtra("roommate_id", items.getResult().get(position).getRid());

                v.getContext().startActivity(detailPage);

            }
        });
    }

    // 찜 목록 해제 통신
    public void removeLike(int position) {
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

    @Override
    public int getItemCount() {
        return this.items.getResult().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        // 화면 상의 컴포넌트
        CircleImageView mProfile;
        NotoTextView mNickname, mDate, matching_rate, mTitle, mPrice, mAge, mLocation, mLikeNum;
        ImageButton mLike;
        ImageView mPhoto;

        LinearLayout linear;

        TextView loginShadow;
        io.chooco13.NotoTextView deleteBt;

        public ViewHolder(View itemView) {
            super(itemView);

            this.mProfile = (CircleImageView) itemView.findViewById(R.id.mProfile);
            this.mNickname = (NotoTextView) itemView.findViewById(R.id.mNickname);
            this.mDate = (NotoTextView) itemView.findViewById(R.id.mDate);
            this.matching_rate = (NotoTextView) itemView.findViewById(R.id.matching_rate);
            this.mTitle = (NotoTextView) itemView.findViewById(R.id.mTitle);
            this.mPrice = (NotoTextView) itemView.findViewById(R.id.mPrice);
            this.mAge = (NotoTextView) itemView.findViewById(R.id.mAge);
            this.mLocation = (NotoTextView) itemView.findViewById(R.id.mLocation);
            this.mLikeNum = (NotoTextView) itemView.findViewById(R.id.mLikeNum);
            this.mLike = (ImageButton) itemView.findViewById(R.id.mLike);
            this.mPhoto = (ImageView) itemView.findViewById(R.id.mPhoto);

            this.loginShadow = (TextView) itemView.findViewById(R.id.beforeLoginShadow);
            this.linear = (LinearLayout) itemView.findViewById(R.id.timelineLinear);
            this.deleteBt = (io.chooco13.NotoTextView) itemView.findViewById(R.id.deleteBt);
        }
    }

}
