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

import com.example.tacademy.ddakgi.R;
import com.example.tacademy.ddakgi.adapter.MyRecyclerAdapter;
import com.example.tacademy.ddakgi.data.Member.ResMember;
import com.example.tacademy.ddakgi.data.Mypage.ResMypage;
import com.example.tacademy.ddakgi.data.NetSSL;
import com.example.tacademy.ddakgi.data.Ottobus;
import com.example.tacademy.ddakgi.view.Help.HelpActivity;
import com.example.tacademy.ddakgi.view.Setting.SettingActivity;
import com.squareup.otto.Subscribe;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;
import io.chooco13.NotoTextView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 마이페이지 MyTab
 */

public class MyTab extends Fragment {

    RecyclerView recyclerviewMyTab;
    LinearLayoutManager linearLayoutManager;

    Toolbar toolbar;
    ImageButton helpBt;
    ImageButton settingBt;
    CircleImageView modifyProfileBt;
    NotoTextView myTabNickname;

    boolean ottoflag = false;
    ResMypage items;

    public MyTab() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_tab, container, false);

        // 레트로핏 통신
        if (!ottoflag) {
            Ottobus.getInstance().getMaingfrag_bus().register(this);
            ottoflag = true;
        }

        myTabNickname = (NotoTextView) view.findViewById(R.id.myTabNickname);

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
        modifyProfileBt = (CircleImageView) view.findViewById(R.id.modifyProfileBt);
        modifyProfileBt.setOnClickListener(onClickListener);

        // Inflate the layout for this fragment
        recyclerviewMyTab = (RecyclerView) view.findViewById(R.id.recyclerviewMyTab);
        linearLayoutManager = new LinearLayoutManager(this.getContext());
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        recyclerviewMyTab.setLayoutManager(linearLayoutManager);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        getProfile();
        getMyPosting();
    }

    int mid;

    // 통신 ========================================================================================
    // 자기 자신의 정보 통신
    public void getProfile() {
        Call<ResMember> resMemberCall = NetSSL.getInstance().getMemberImpFactory().resMember();
        resMemberCall.enqueue(new Callback<ResMember>() {
            @Override
            public void onResponse(Call<ResMember> call, Response<ResMember> response) {
                if (response.body().getResult() != null) {
                    Log.i("RF:MytabProfile", "SUCCESS" + response.body().getResult());
                    if (response.body().getResult().getThumbnail_image() != null) {
                        Picasso.with(getContext()).load(response.body().getResult().getThumbnail_image()).into(modifyProfileBt);
                    }else{
                        modifyProfileBt.setImageResource(R.mipmap.profile_btn);
                    }
                    myTabNickname.setText(response.body().getResult().getNickname());
                    mid = response.body().getResult().getMid();
                } else {
                    Log.i("RF:MytabProfile", "FAIL" + response.body().getError());
                }
            }

            @Override
            public void onFailure(Call<ResMember> call, Throwable t) {
                Log.i("RF:MytabProfile", "ERR" + t.getMessage());
            }
        });
    }

    // 게시물 통신
    public void getMyPosting() {
        Call<ResMypage> resMypageCall = NetSSL.getInstance().getMemberImpFactory().resMypage();
        resMypageCall.enqueue(new Callback<ResMypage>() {
            @Override
            public void onResponse(Call<ResMypage> call, Response<ResMypage> response) {
                if (response.body().getResult() != null) {
                    Log.i("RF:MyPage", "SUCCESS" + response.body().getResult());
                    if (response.body().getResult() != null) {
                        Ottobus.getInstance().getMaingfrag_bus().post(response.body());
                    }
                } else {
                    Log.i("RF:MyPage", "FAIL" + response.body().getError());
                }
            }

            @Override
            public void onFailure(Call<ResMypage> call, Throwable t) {
                Log.i("RF:INTRO", "ERR" + t.getMessage());
            }
        });
    }

    // Ottobus =====================================================================================
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // Ottobus 연결 끊어주기
        Ottobus.getInstance().getMaingfrag_bus().unregister(this);
    }

    @Subscribe
    public void FinishLoad(ResMypage data) {
        items = data;

        MyRecyclerAdapter recyclerAdapter = new MyRecyclerAdapter(getContext(), items, R.layout.home_timeline);
        recyclerAdapter.notifyDataSetChanged();
        recyclerviewMyTab.setAdapter(recyclerAdapter);
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
                    mateIntent.putExtra("id", mid);
                    startActivity(mateIntent);
                    break;
                case R.id.modifyProfileBt:
                    Intent modifyProfile = new Intent(getContext(), ModifyProfileActivity.class);
                    startActivity(modifyProfile);
            }
        }
    };
}