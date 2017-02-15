package com.example.tacademy.ddakgi.HomeTab;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.tacademy.ddakgi.R;

/**
 * Created by Tacademy on 2017-02-03.
 */

public class PostHolder extends RecyclerView.ViewHolder {
    TextView mNickname;

    public PostHolder(View itemView){
        super(itemView);
        mNickname = (TextView)itemView.findViewById(R.id.nickname);
    }

    public void bindOnPost(String nickName){
        mNickname.setText(nickName);
    }
}
