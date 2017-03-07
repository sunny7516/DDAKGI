package com.example.tacademy.ddakgi.view.Home.frag;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.tacademy.ddakgi.R;
import com.example.tacademy.ddakgi.adapter.RecyclerAdapter;
import com.example.tacademy.ddakgi.data.HomeTimeline.ResHomePosting;
import com.example.tacademy.ddakgi.data.NetSSL;
import com.example.tacademy.ddakgi.data.Ottobus;
import com.example.tacademy.ddakgi.view.Filter.FilterActivity;
import com.example.tacademy.ddakgi.view.Search.act.SearchActivity;
import com.squareup.otto.Subscribe;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.tacademy.ddakgi.R.id.gosearchBt;

/**
 * 홈화면 > all/room/mate 타임라인 보여줌
 */
public class HomeTab extends Fragment {
    private boolean activityStartup = true;

    RecyclerView recyclerviewHomeTab;
    LinearLayoutManager linearLayoutManager;

    SearchView searchView;
    EditText searchEditText;
    ImageView search_close_btn;
    ImageView search_icon;

    // all/room/mate 버튼
    io.chooco13.NotoTextView all;
    io.chooco13.NotoTextView room;
    io.chooco13.NotoTextView mate;

    ResHomePosting items;

    boolean ottoflag = false;

    public HomeTab() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_tab, container, false);

        // 레트로핏 통신 ==============================================================================
        if (!ottoflag) {
            Ottobus.getInstance().getMaingfrag_bus().register(this);
            ottoflag = true;
        }

        AllPostingSet();

        // SearchView Style ========================================================================
        searchView = (SearchView) view.findViewById(R.id.searchView);

        searchEditText = (EditText) searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        searchEditText.setTextColor(getResources().getColor(R.color.subTextColor));
        searchEditText.setHintTextColor(getResources().getColor(R.color.grayTextColor));
        searchEditText.setTextSize(13);

        search_close_btn = (ImageView) searchView.findViewById(android.support.v7.appcompat.R.id.search_close_btn);
        search_icon = (ImageView) searchView.findViewById(android.support.v7.appcompat.R.id.search_mag_icon);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            search_close_btn.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.subTextColor)));
            search_icon.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.subTextColor)));
        }

        searchView.setIconifiedByDefault(false);
        // 처음 searchview 초기화 시 커서가 보이지 않도록 지정
        searchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    if (activityStartup) {
                        view.clearFocus();
                        activityStartup = false;
                    }
                }
            }
        });

        // Fragment toolbar ========================================================================
        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.MainToolbar);

        // Toolbar에 OptionMenu 지정
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().hide();

        // search화면으로 가는 ClickListener 적용 ======================================================
        Button gosearchBt = (Button) view.findViewById(R.id.gosearchBt);
        Button filter_menu = (Button) view.findViewById(R.id.filter_menu);
        gosearchBt.setOnClickListener(toolBtListener);
        filter_menu.setOnClickListener(toolBtListener);

        // 홈탭 all/room/mate 버튼 ===================================================================
        all = (io.chooco13.NotoTextView) view.findViewById(R.id.all);
        room = (io.chooco13.NotoTextView) view.findViewById(R.id.room);
        mate = (io.chooco13.NotoTextView) view.findViewById(R.id.mate);
        all.setOnClickListener(clickBtListener);
        room.setOnClickListener(clickBtListener);
        mate.setOnClickListener(clickBtListener);

        // 화면 구성 세팅..
        recyclerviewHomeTab = (RecyclerView) view.findViewById(R.id.recyclerviewHomeTab);
        // 리사이클러뷰 위로 뭔가가 있어서 자동으로 올라가서 가려지면
        // 이것을 넣어서 현재 위치로 유지시킨다.
        recyclerviewHomeTab.setFocusable(false);
        // 레이아웃 세팅
        linearLayoutManager = new LinearLayoutManager(this.getContext());
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        recyclerviewHomeTab.setLayoutManager(linearLayoutManager);

        return view;
    }

    public void AllPostingSet() {
        Call<ResHomePosting> resAllPostingCall = NetSSL.getInstance().getMemberImpFactory().resAllPosting();
        resAllPostingCall.enqueue(new Callback<ResHomePosting>() {
            @Override
            public void onResponse(Call<ResHomePosting> call, Response<ResHomePosting> response) {
                if (response.body().getResult() != null) {
                    Log.i("RF:Main/Room", "SUCCESS" + response.body().getResult());
                    if (response.body().getResult() != null) {
                        Ottobus.getInstance().getMaingfrag_bus().post(response.body());
                    }
                } else {
                    Log.i("RF:Main/Room", "FAIL" + response.body().getError());
                }
            }

            @Override
            public void onFailure(Call<ResHomePosting> call, Throwable t) {
                Log.i("RF:INTRO", "ERR" + t.getMessage());
            }
        });
    }

    public void RoomOrMateSet(int roomming) {
        Call<ResHomePosting> resRoomMatePosting = NetSSL.getInstance().getMemberImpFactory().resRoomMatePosting(roomming);
        resRoomMatePosting.enqueue(new Callback<ResHomePosting>() {
            @Override
            public void onResponse(Call<ResHomePosting> call, Response<ResHomePosting> response) {
                if (response.body().getResult() != null) {
                    Log.i("RF:RoomMate", "SUCCESS" + response.body().getResult());
                    if (response.body().getResult() != null) {
                        Log.i("RF:RoomMate", items.getResult() + "");
                        Ottobus.getInstance().getMaingfrag_bus().post(response.body());
                    }
                } else {
                    Log.i("RF:RoomMate", "FAIL" + response.body().getError());
                }
            }

            @Override
            public void onFailure(Call<ResHomePosting> call, Throwable t) {
                Log.i("RF:RoomMate", "ERR" + t.getMessage());
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // Ottobus 연결 끊어주기
        Ottobus.getInstance().getMaingfrag_bus().unregister(this);
    }

    @Subscribe
    public void FinishLoad(ResHomePosting data) {
        items = data;
        recyclerviewHomeTab.setAdapter(new RecyclerAdapter(getContext(), items, R.layout.home_timeline));
    }

    // Toolbar Click Event
    private View.OnClickListener toolBtListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                // 검색창 눌렀을 때 search화면으로 intent
                case gosearchBt:
                    Intent searchintent = new Intent(getContext(), SearchActivity.class);
                    startActivity(searchintent);
                    break;
                // 이미지 버튼 눌렀을 때 filter 화면으로 intent
                case R.id.filter_menu:
                    Intent intent = new Intent(getActivity(), FilterActivity.class);
                    startActivity(intent);
                    break;
            }
        }
    };

    // all/room/mate Bt Click Event
    io.chooco13.NotoTextView clickedText = null;
    private View.OnClickListener clickBtListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            all.setTextColor(getResources().getColor(R.color.grayTextColor));
            all.setBackgroundColor(getResources().getColor(R.color.white));
            if (clickedText == null) {
                clickedText = (io.chooco13.NotoTextView) view;
                clickedText.setTextColor(getResources().getColor(R.color.white));
                clickedText.setBackgroundColor(getResources().getColor(R.color.pointColor));
            } else if (clickedText != view) {
                clickedText.setTextColor(getResources().getColor(R.color.grayTextColor));
                clickedText.setBackgroundColor(getResources().getColor(R.color.white));
                clickedText = (io.chooco13.NotoTextView) view;
                clickedText.setTextColor(getResources().getColor(R.color.white));
                clickedText.setBackgroundColor(getResources().getColor(R.color.pointColor));
            }
            switch (view.getId()) {
                case R.id.room:
                    RoomOrMateSet(1);
                    break;
                case R.id.mate:
                    RoomOrMateSet(0);
                    break;
                case R.id.all:
                    AllPostingSet();
            }
        }
    };
}