package com.example.tacademy.ddakgi.view.Chat.holder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.tacademy.ddakgi.R;
import com.example.tacademy.ddakgi.view.Chat.model.ChatModel;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Tacademy on 2017-03-02.
 */

public class PostHolder extends RecyclerView.ViewHolder {
    TextView msg;
    TextView txt_left, txt_right;
    LinearLayout left_container, right_container;
    CircleImageView img_left_thumbnail, img_right_thumbnail;

    // 뷰로부터 컴포넌트를 획득

    public PostHolder(View itemView) {
        super(itemView);
        msg = (TextView) itemView.findViewById(R.id.msg);
        // 채팅 UI
        txt_left = (TextView) itemView.findViewById(R.id.txt_left);
        txt_right = (TextView) itemView.findViewById(R.id.txt_right);
        left_container = (LinearLayout) itemView.findViewById(R.id.left_container);
        right_container = (LinearLayout) itemView.findViewById(R.id.right_container);

        img_left_thumbnail = (CircleImageView) itemView.findViewById(R.id.img_left_thumbnail);
        img_right_thumbnail = (CircleImageView) itemView.findViewById(R.id.img_right_thumbnail);
    }

    // 데이터 설정
    public void bindOnPost(String text) {
        msg.setText(text);
    }

    public void bindOnPost(String text, int type) {
        if (type == 1) {
            // me
            right_container.setVisibility(View.VISIBLE);
            left_container.setVisibility(View.GONE);
            txt_right.setText(text);
        } else {
            // you
            left_container.setVisibility(View.VISIBLE);
            right_container.setVisibility(View.GONE);
            txt_left.setText(text);
        }
    }

    public void bindOnPost(Context context, ChatModel msg, String myNickname) {
        int type = 0;
        if(msg.getSender().equals(myNickname)) type = 1;    //이번에표시할메시지가 내가쓴글이다
        if(type ==1){
            // me
            right_container.setVisibility(View.VISIBLE);
            left_container.setVisibility(View.GONE);
            txt_right.setText(msg.getMsg());
        }else{
            // you
            left_container.setVisibility(View.VISIBLE);
            right_container.setVisibility(View.GONE);
            txt_left.setText(msg.getMsg());
        }
    }

    public void initProfile(){
        img_right_thumbnail.setVisibility(View.VISIBLE);
        img_left_thumbnail.setVisibility(View.VISIBLE);
    }

    public void hideProfile(int type){
        if(type == 1){
            img_right_thumbnail.setVisibility(View.INVISIBLE);
        }else{
            img_left_thumbnail.setVisibility(View.INVISIBLE);
        }
    }
}
