package com.example.tacademy.ddakgi.view.My;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.tacademy.ddakgi.R;
import com.example.tacademy.ddakgi.adapter.MyRecyclerAdapter;
import com.example.tacademy.ddakgi.data.Member.ResMember;
import com.example.tacademy.ddakgi.data.NetSSL;
import com.example.tacademy.ddakgi.view.Help.HelpActivity;
import com.example.tacademy.ddakgi.view.My.model.MyTimelineItem;
import com.example.tacademy.ddakgi.view.Setting.SettingActivity;

import java.util.ArrayList;
import java.util.List;

import io.chooco13.NotoTextView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MyTab extends Fragment {

    final int ITEM_SIZE = 3;
    RecyclerView recyclerviewMyTab;
    LinearLayoutManager linearLayoutManager;

    Toolbar toolbar;
    ImageButton helpBt;
    ImageButton settingBt;
    ImageView modifyProfileBt;
    NotoTextView myTabNickname;

    public MyTab() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_tab, container, false);

        // 내 정보(사진, 닉네임) 가져와서 적용
        getMyInfo();
        myTabNickname = (NotoTextView)view.findViewById(R.id.myTabNickname);

        // Fragment toolbar 적용하기
        toolbar = (Toolbar) getActivity().findViewById(R.id.myTabTool);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);

        // Toolbar에 있는 버튼
        helpBt = (ImageButton) view.findViewById(R.id.helpBt);
        settingBt = (ImageButton) view.findViewById(R.id.settingBt);
        helpBt.setOnClickListener(onClickListener);
        settingBt.setOnClickListener(onClickListener);

        // 프로필 수정으로 이동
        modifyProfileBt = (ImageView) view.findViewById(R.id.modifyProfileBt);
        modifyProfileBt.setOnClickListener(onClickListener);

        // Inflate the layout for this fragment
        recyclerviewMyTab = (RecyclerView) view.findViewById(R.id.recyclerviewMyTab);
        linearLayoutManager = new LinearLayoutManager(this.getContext());
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        recyclerviewMyTab.setLayoutManager(linearLayoutManager);

        // 가데이터
        List<MyTimelineItem> items = new ArrayList<>();
        MyTimelineItem[] item = new MyTimelineItem[ITEM_SIZE];
        item[0] = new MyTimelineItem(R.drawable.testroom0);
        item[1] = new MyTimelineItem(R.drawable.testroom1);
        item[2] = new MyTimelineItem(R.drawable.testroom2);

        for (int i = 0; i < ITEM_SIZE; i++) {
            items.add(item[i]);
        }
        recyclerviewMyTab.setAdapter(new MyRecyclerAdapter(getContext(), items, R.layout.home_timeline));

        return view;
    }

    // DB에서 data get ==========================================================================
    public void getMyInfo() {
        Call<ResMember> resMemberCall = NetSSL.getInstance().getMemberImpFactory().resMember();
        resMemberCall.enqueue(new Callback<ResMember>() {
            @Override
            public void onResponse(Call<ResMember> call, Response<ResMember> response) {
                if (response.body().getResult() != null) {
                    Log.i("RF:ME", "SUCCESS" + response.body().getResult().getNickname());
                    myTabNickname.setText(response.body().getResult().getNickname());
                } else {
                    Log.i("RF:ME", "FAIL" + response.body().getError());
                }
            }

            @Override
            public void onFailure(Call<ResMember> call, Throwable t) {
                Log.i("RF:ME", "ERR" + t.getMessage());
            }
        });
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.helpBt:
                    Intent roomIntent = new Intent(getContext(), HelpActivity.class);
                    startActivity(roomIntent);
                    break;
                case R.id.settingBt:
                    Intent mateIntent = new Intent(getContext(), SettingActivity.class);
                    startActivity(mateIntent);
                    break;
                case R.id.modifyProfileBt:
                    Intent modifyProfile = new Intent(getContext(), ModifyProfileActivity.class);
                    startActivity(modifyProfile);
            }
        }
    };
}