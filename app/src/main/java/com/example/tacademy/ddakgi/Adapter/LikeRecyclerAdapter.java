package com.example.tacademy.ddakgi.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.tacademy.ddakgi.view.Home.act.HomeRoomDetailPageActivity;
import com.example.tacademy.ddakgi.view.My.model.MyTimelineItem;
import com.example.tacademy.ddakgi.R;

import java.util.List;

/**
 * LikeTab에 있는 Recyclerview에 적용하는 Adapter
 */

public class LikeRecyclerAdapter extends RecyclerView.Adapter<LikeRecyclerAdapter.ViewHolder> {

    Context context;
    List<MyTimelineItem> items;
    int item_layout;

    public LikeRecyclerAdapter(Context context, List<MyTimelineItem> items, int item_layout) {
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
        final MyTimelineItem item = items.get(position);
        Drawable photo = ContextCompat.getDrawable(context, item.getRoomImg());

        holder.deleteBt.setVisibility(View.VISIBLE);
        holder.deleteBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 삭제 버튼 누르면 화면에서 글 삭제
                items.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, items.size());
                holder.itemView.setVisibility(View.GONE);
                // 데이터 삭제도 구현해야 함
            }
        });

        holder.linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 타임라인 글을 선택하면 상세페이지로 넘어감
                // 넘어갈 떄 이미지 정보 같이 전달함
                Intent detailPage = new Intent(v.getContext(), HomeRoomDetailPageActivity.class);
                v.getContext().startActivity(detailPage);
            }
        });
        holder.image.setImageDrawable(photo);
    }

    @Override
    public int getItemCount() {
        return this.items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        LinearLayout linear;

        TextView loginShadow;
        io.chooco13.NotoTextView loginBeforeText;
        io.chooco13.NotoTextView deleteBt;

        public ViewHolder(View itemView) {
            super(itemView);
            this.image = (ImageView) itemView.findViewById(R.id.mPhoto);
            this.loginShadow = (TextView) itemView.findViewById(R.id.beforeLoginShadow);
            this.linear = (LinearLayout) itemView.findViewById(R.id.timelineLinear);
            this.deleteBt = (io.chooco13.NotoTextView) itemView.findViewById(R.id.deleteBt);
        }
    }

}
