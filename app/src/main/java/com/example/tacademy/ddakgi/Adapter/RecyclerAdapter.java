package com.example.tacademy.ddakgi.Adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.tacademy.ddakgi.R;
import com.example.tacademy.ddakgi.HomeTab.TimelineItem;

import java.util.List;

/**
 * Created by Tacademy on 2017-02-03.
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
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.home_timeline, null);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final TimelineItem item = items.get(position);
        Drawable photo = ContextCompat.getDrawable(context, item.getImage());

        holder.image.setImageDrawable(photo);
    }

    @Override
    public int getItemCount() {
        return this.items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView image;
        CardView cardView;

        public ViewHolder(View itemView) {

            super(itemView);
            this.image = (ImageView)itemView.findViewById(R.id.mPhoto);
            this.cardView = (CardView)itemView.findViewById(R.id.cardView);
        }
    }
}
