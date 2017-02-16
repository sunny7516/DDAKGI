package com.example.tacademy.ddakgi.Adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.tacademy.ddakgi.MyTab.util.MyTimelineItem;
import com.example.tacademy.ddakgi.R;

import java.util.List;


/**
 * Mytab에 있는 Recyclerview에 적용하는 Adapter
 */

public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.ViewHolder> {

    Context context;
    List<MyTimelineItem> items;
    int item_layout;

    public MyRecyclerAdapter(Context context, List<MyTimelineItem> items, int item_layout) {
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
        final MyTimelineItem item = items.get(position);
        Drawable photo = ContextCompat.getDrawable(context, item.getRoomImg());

        holder.modifyBt.setVisibility(View.VISIBLE);
        holder.deleteBt.setVisibility(View.VISIBLE);
        holder.image.setImageDrawable(photo);

        holder.loginShadow.setVisibility(View.GONE);
        holder.loginBeforeText.setVisibility(View.GONE);
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
        Button modifyBt;
        Button deleteBt;

        public ViewHolder(View itemView) {
            super(itemView);
            this.image = (ImageView) itemView.findViewById(R.id.mPhoto);
            this.loginShadow = (TextView) itemView.findViewById(R.id.beforeLoginShadow);
            this.loginBeforeText = (io.chooco13.NotoTextView) itemView.findViewById(R.id.loginBeforeText);
            this.linear = (LinearLayout) itemView.findViewById(R.id.timelineLinear);
            this.modifyBt = (Button) itemView.findViewById(R.id.modifyBt);
            this.deleteBt = (Button) itemView.findViewById(R.id.deleteBt);
        }
    }
}
