package com.example.tacademy.ddakgi.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.tacademy.ddakgi.R;
import com.example.tacademy.ddakgi.data.HomeTimeline.HomePostingModel;
import com.example.tacademy.ddakgi.data.HomeTimeline.ResHomePosting;
import com.example.tacademy.ddakgi.view.Home.act.HomeRoomDetailPageActivity;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;
import io.chooco13.NotoTextView;

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

    // 각 position에 해당하는 view에 데이터 연결하는 부분
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        HomePostingModel item = items.getResult().get(position);

        holder.mNickname.setText(item.getNickname());
        holder.mDate.setText(item.getCtime());
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
                // 타임라인 글을 선택하면 상세페이지로 넘어감
                // 넘어갈 떄 이미지 정보 같이 전달함
                Intent detailPage = new Intent(v.getContext(), HomeRoomDetailPageActivity.class);
                v.getContext().startActivity(detailPage);
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
        }
    }
}
