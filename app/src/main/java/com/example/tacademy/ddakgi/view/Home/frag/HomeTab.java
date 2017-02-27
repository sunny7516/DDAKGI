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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.tacademy.ddakgi.R;
import com.example.tacademy.ddakgi.adapter.RecyclerAdapter;
import com.example.tacademy.ddakgi.view.Filter.FilterActivity;
import com.example.tacademy.ddakgi.view.Home.model.TimelineItem;
import com.example.tacademy.ddakgi.view.Search.act.SearchActivity;
import com.example.tacademy.ddakgi.view.SignUp.act.SignUpActivity;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

import static com.example.tacademy.ddakgi.R.id.gosearchBt;

public class HomeTab extends Fragment {
    private boolean activityStartup = true;
    final int ITEM_SIZE = 3;

    RecyclerView recyclerviewHomeTab;
    LinearLayoutManager linearLayoutManager;
    RecyclerAdapter recyclerAdapter;

    SearchView searchView;
    EditText searchEditText;
    ImageView search_close_btn;
    ImageView search_icon;
    SweetAlertDialog alert;

    // all/room/mate 버튼
    io.chooco13.NotoTextView all;
    io.chooco13.NotoTextView room;
    io.chooco13.NotoTextView mate;

    public HomeTab() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_tab, container, false);

        // 홈화면 유입 시 로그인 유도 팝업
        letLogin();

        // SearchView Style
        searchView = (SearchView) view.findViewById(R.id.searchView);

        // SearchView Style
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

        // Fragment toolbar
        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.MainToolbar);

        // Toolbar에 OptionMenu 지정
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().hide();

        // search화면으로 가는 ClickListener 적용
        Button gosearchBt = (Button) view.findViewById(R.id.gosearchBt);
        Button filter_menu = (Button) view.findViewById(R.id.filter_menu);
        gosearchBt.setOnClickListener(toolBtListener);
        filter_menu.setOnClickListener(toolBtListener);

        // 홈탭 all/room/mate 버튼
        all = (io.chooco13.NotoTextView) view.findViewById(R.id.all);
        room = (io.chooco13.NotoTextView) view.findViewById(R.id.room);
        mate = (io.chooco13.NotoTextView) view.findViewById(R.id.mate);
        all.setOnClickListener(clickBtListener);
        room.setOnClickListener(clickBtListener);
        mate.setOnClickListener(clickBtListener);

        // Inflate the layout for this fragment
        recyclerviewHomeTab = (RecyclerView) view.findViewById(R.id.recyclerviewHomeTab);

        linearLayoutManager = new LinearLayoutManager(this.getContext());
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        recyclerviewHomeTab.setLayoutManager(linearLayoutManager);

        // 가데이터 > 수정해야됨
        List<TimelineItem> items = new ArrayList<>();
        TimelineItem[] item = new TimelineItem[ITEM_SIZE];
        item[0] = new TimelineItem(R.drawable.testroom0);
        item[1] = new TimelineItem(R.drawable.testroom1);
        item[2] = new TimelineItem(R.drawable.testroom2);

        for (int i = 0; i < ITEM_SIZE; i++) {
            items.add(item[i]);
        }
        recyclerviewHomeTab.setAdapter(new RecyclerAdapter(getContext(), items, R.layout.home_timeline));

        return view;
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
        }
    };

    public static boolean isLogin=true;

    // 로그인 유도 팝업 띄우기
    public void letLogin() {
        if (!isLogin) {
            // 로그인 상태가 아니면
            alert = new SweetAlertDialog(getContext(), SweetAlertDialog.WARNING_TYPE)
                    .setContentText("유용한 정보를 확인하기 위해서 로그인을 해주세요!")
                    .setConfirmText("로그인")
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            alert.dismissWithAnimation();
                            // 로그인 화면으로
                            Intent intent = new Intent(getContext(), SignUpActivity.class);
                            startActivity(intent);
                        }
                    });
            alert.setCancelable(false);
            alert.show();
        } else {
            // 자동 로그인 상태이면
        }
    }
}