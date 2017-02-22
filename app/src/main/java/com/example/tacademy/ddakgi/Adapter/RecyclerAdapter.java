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

import com.example.tacademy.ddakgi.view.Home.act.HomeRoomDetailPageActivity;
import com.example.tacademy.ddakgi.view.Home.model.TimelineItem;
import com.example.tacademy.ddakgi.R;

import java.util.List;

/**
 * HomeTab에 있는 Recyclerview에 적용하는 Adapter
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    Context context;
    List<TimelineItem> items;
    int item_layout;

    public RecyclerAdapter(Context context, List<TimelineItem> items, int item_layout) {
        super();
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
        final TimelineItem item = items.get(position);

        Drawable photo = ContextCompat.getDrawable(context, item.getRoomImg());

        holder.image.setImageDrawable(photo);
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

    @Override
    public int getItemCount() {
        return this.items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        LinearLayout timelineLinear;

        public ViewHolder(View itemView) {
            super(itemView);
            this.image = (ImageView) itemView.findViewById(R.id.mPhoto);
            this.timelineLinear = (LinearLayout) itemView.findViewById(R.id.timelineLinear);
        }
    }
}
