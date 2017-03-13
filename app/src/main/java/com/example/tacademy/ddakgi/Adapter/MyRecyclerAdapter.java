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

import com.example.tacademy.ddakgi.R;
import com.example.tacademy.ddakgi.data.Mypage.Mypage;
import com.example.tacademy.ddakgi.data.Mypage.ResMypage;
import com.example.tacademy.ddakgi.data.NetSSL;
import com.example.tacademy.ddakgi.data.RegisterRoom.ResStringString;
import com.example.tacademy.ddakgi.view.Home.act.HomeRoomDetailPageActivity;
import com.example.tacademy.ddakgi.view.Home.act.HomemateDetailPageActivity;
import com.example.tacademy.ddakgi.view.Write.act.WriteMateActivity;
import com.example.tacademy.ddakgi.view.Write.act.WriteRoomActivity;
import com.squareup.picasso.Picasso;

import cn.pedant.SweetAlert.SweetAlertDialog;
import de.hdodenhof.circleimageview.CircleImageView;
import io.chooco13.NotoTextView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Mytab에 있는 Recyclerview에 적용하는 Adapter
 */

public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.ViewHolder> {

    Context context;
    ResMypage items;
    int item_layout;
    SweetAlertDialog alert;

    public MyRecyclerAdapter(Context context, ResMypage items, int item_layout) {
        this.context = context;
        this.items = items;
        this.item_layout = item_layout;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_timeline, null);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Mypage item = items.getResult().get(position);

        // 좋아요 버튼 클릭이벤트 막아두기
        holder.mLike.setClickable(false);
        holder.matching_rate.setVisibility(View.GONE);
        holder.matching_text.setVisibility(View.GONE);

        if (item.getThumbnail_image() != null) {
            Picasso.with(context).load(item.getThumbnail_image()).fit().into(holder.mProfile);
        } else {
            holder.mProfile.setImageResource(R.mipmap.profile_edit);
        }
        holder.mLikeNum.setText(String.valueOf(item.getHeart_count()));
        holder.mNickname.setText(item.getNickname());
        holder.mDate.setText(item.getCtime().split("T")[0]);
        holder.mTitle.setText(item.getTitle());
        if (item.getRoomming() == 0) {
            holder.mPrice.setVisibility(View.GONE);
        } else {
            holder.mPrice.setVisibility(View.VISIBLE);
            holder.mPrice.setText(item.getDeposit() + "/" + item.getRent());
        }
        holder.mAge.setText(String.valueOf(item.getAge()));
        holder.mLocation.setText(item.getAddress());
        if (item.getRoommate_image() != null) {
            Picasso.with(context).load(item.getRoommate_image()[0]).fit().into(holder.image);
        }

        // 수정버튼
        holder.modifyBt.setVisibility(View.VISIBLE);
        holder.modifyBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent modifyIntent;

                if(item.getRoomming()==0){
                    modifyIntent = new Intent(v.getContext(), WriteMateActivity.class);
                }else{
                    modifyIntent = new Intent(v.getContext(), WriteRoomActivity.class);
                }
                modifyIntent.putExtra("modify",0);
                modifyIntent.putExtra("roommate_id", item.getRid());
                v.getContext().startActivity(modifyIntent);
            }
        });

        // 삭제버튼
        holder.deleteBt.setVisibility(View.VISIBLE);
        holder.deleteBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 삭제 버튼 누르면 글 삭제할건지 확인
                alert =
                        new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE)
                                .setContentText("글을 삭제하시겠습니까?")
                                .setConfirmText("삭제")
                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sDialog) {
                                        // 삭제 버튼 누르면 화면에서 글 삭제
                                        alert.dismissWithAnimation();
                                        // 데이터 처리
                                        removePosting(position);

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
        // 상세페이지로 이동
        holder.linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent detailPage;
                // 타임라인 글을 선택하면 상세페이지로 넘어감
                // 넘어갈 떄 이미지 정보 같이 전달함
                if (item.getRoomming() == 0) {
                    detailPage = new Intent(v.getContext(), HomemateDetailPageActivity.class);
                } else {
                    detailPage = new Intent(v.getContext(), HomeRoomDetailPageActivity.class);
                }
                detailPage.putExtra("roommate_id", items.getResult().get(position).getRid());
                Log.i("checkUid", "recyclerview"+items.getResult().get(position).getRid());
                v.getContext().startActivity(detailPage);
            }
        });
    }

    public void removePosting(int position) {
        Call<ResStringString> resDeletePosting =
                NetSSL.getInstance().getMemberImpFactory().resDeletePosting(items.getResult().get(position).getRid());
        resDeletePosting.enqueue(new Callback<ResStringString>() {
            @Override
            public void onResponse(Call<ResStringString> call, Response<ResStringString> response) {
                if (response.body().getResult() != null) {
                    Log.i("RF:DeleteMyPosting", "SUCCESS" + response.body().getResult());
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

    @Override
    public int getItemCount() {
        return this.items.getResult().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        LinearLayout linear;

        ImageButton mLike;
        CircleImageView mProfile;
        NotoTextView mNickname, mDate, mTitle, mPrice, mAge, mLocation, matching_rate, mLikeNum, matching_text;

        TextView loginShadow;
        io.chooco13.NotoTextView modifyBt;
        io.chooco13.NotoTextView deleteBt;

        public ViewHolder(View itemView) {
            super(itemView);
            this.matching_text = (NotoTextView) itemView.findViewById(R.id.matching_text);
            this.mLikeNum = (NotoTextView) itemView.findViewById(R.id.mLikeNum);
            this.matching_rate = (NotoTextView) itemView.findViewById(R.id.matching_rate);
            this.mProfile = (CircleImageView) itemView.findViewById(R.id.mProfile);
            this.mNickname = (NotoTextView) itemView.findViewById(R.id.mNickname);
            this.mDate = (NotoTextView) itemView.findViewById(R.id.mDate);
            this.mTitle = (NotoTextView) itemView.findViewById(R.id.mTitle);
            this.mPrice = (NotoTextView) itemView.findViewById(R.id.mPrice);
            this.mAge = (NotoTextView) itemView.findViewById(R.id.mAge);
            this.mLocation = (NotoTextView) itemView.findViewById(R.id.mLocation);
            this.mLike = (ImageButton) itemView.findViewById(R.id.mLike);

            this.image = (ImageView) itemView.findViewById(R.id.mPhoto);
            this.loginShadow = (TextView) itemView.findViewById(R.id.beforeLoginShadow);
            this.linear = (LinearLayout) itemView.findViewById(R.id.timelineLinear);
            this.modifyBt = (io.chooco13.NotoTextView) itemView.findViewById(R.id.modifyBt);
            this.deleteBt = (io.chooco13.NotoTextView) itemView.findViewById(R.id.deleteBt);
        }
    }
}
