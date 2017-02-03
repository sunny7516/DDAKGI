package com.example.tacademy.ddakgi;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Tacademy on 2017-02-03.
 */

public class PostHolder extends RecyclerView.ViewHolder {
    TextView mNickname;

    public PostHolder(View itemView){
        super(itemView);
        mNickname = (TextView)itemView.findViewById(R.id.mNickname);
    }

    public void bindOnPost(String nickName){
        mNickname.setText(nickName);
    }
}
