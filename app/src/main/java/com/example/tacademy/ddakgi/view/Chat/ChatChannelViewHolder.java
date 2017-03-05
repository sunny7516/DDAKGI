package com.example.tacademy.ddakgi.view.Chat;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.tacademy.ddakgi.R;
import com.squareup.picasso.Picasso;

import java.util.Calendar;

import de.hdodenhof.circleimageview.CircleImageView;
import io.chooco13.NotoTextView;

/**
 * Created by Tacademy on 2017-03-05.
 */

public class ChatChannelViewHolder extends RecyclerView.ViewHolder {
    CircleImageView ic_profile;
    NotoTextView nickName, lastMsg, badge_count, time;
    public ChatChannelViewHolder(View itemView){
        super(itemView);
        ic_profile = (CircleImageView)itemView.findViewById(R.id.ic_profile);
        nickName = (NotoTextView)itemView.findViewById(R.id.nickName);
        lastMsg = (NotoTextView)itemView.findViewById(R.id.lastMsg);
        badge_count = (NotoTextView)itemView.findViewById(R.id.badge_count);
        time = (NotoTextView)itemView.findViewById(R.id.time);
    }
    public void bindToPost(Context context, ChatChannelModel model){
        // 셀 화면 세팅
        // 셀을 누르면 채팅방으로 이동
        nickName.setText(model.getUid());
        lastMsg.setText(model.getLastMsg());
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(model.getTime());
        time.setText(c.get(c.YEAR) + "-"+(c.get(c.MONTH)+1)+"="+c.get(c.DAY_OF_MONTH));
        Picasso.with(context)
                .load(model.getProfile())
                .into(ic_profile);
    }
}
