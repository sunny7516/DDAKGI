package com.example.tacademy.ddakgi.view.Like;

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

import com.example.tacademy.ddakgi.R;
import com.example.tacademy.ddakgi.adapter.LikeRecyclerAdapter;
import com.example.tacademy.ddakgi.data.Heart.ResHeartPosting;
import com.example.tacademy.ddakgi.data.NetSSL;
import com.example.tacademy.ddakgi.data.Ottobus;
import com.squareup.otto.Subscribe;

import io.chooco13.NotoTextView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LikeTab extends Fragment {

    RecyclerView recyclerviewLikeTab;
    LinearLayoutManager linearLayoutManager;

    Toolbar toolbar;
    NotoTextView deleteBt;

    boolean ottoflag = false;
    ResHeartPosting items;

    public LikeTab() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_like_tab, container, false);

        // 레트로핏 통신 ==============================================================================
        if (!ottoflag) {
            Ottobus.getInstance().getMaingfrag_bus().register(this);
            ottoflag = true;
        }

        // Fragment toolbar 적용하기
        toolbar = (Toolbar) getActivity().findViewById(R.id.likeToolbar);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);

        deleteBt = (NotoTextView) getActivity().findViewById(R.id.deleteBt);

        // Inflate the layout for this fragment
        recyclerviewLikeTab = (RecyclerView) view.findViewById(R.id.recyclerviewLikeTab);
        linearLayoutManager = new LinearLayoutManager(this.getContext());
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        linearLayoutManager.setStackFromEnd(true);
        linearLayoutManager.setReverseLayout(true);
        recyclerviewLikeTab.setLayoutManager(linearLayoutManager);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        Call<ResHeartPosting> resHeartPostingCall = NetSSL.getInstance().getMemberImpFactory().resHeartPosting();
        resHeartPostingCall.enqueue(new Callback<ResHeartPosting>() {
            @Override
            public void onResponse(Call<ResHeartPosting> call, Response<ResHeartPosting> response) {
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
            public void onFailure(Call<ResHeartPosting> call, Throwable t) {
                Log.i("RF:INTRO", "ERR" + t.getMessage());
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // Ottobus 연결 끊어주기
        Ottobus.getInstance().getMaingfrag_bus().unregister(this);
    }

    LikeRecyclerAdapter recyclerAdapter;

    @Subscribe
    public void FinishLoad(ResHeartPosting data) {
        items = data;
        recyclerAdapter = new LikeRecyclerAdapter(getContext(), items, R.layout.home_timeline);
        recyclerAdapter.notifyDataSetChanged();
        recyclerviewLikeTab.setAdapter(recyclerAdapter);
    }
}
