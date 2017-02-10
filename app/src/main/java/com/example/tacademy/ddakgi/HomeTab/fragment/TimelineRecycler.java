package com.example.tacademy.ddakgi.HomeTab.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tacademy.ddakgi.R;

/**
 * Recycler에 들어가는 timeline 구성
 */
public class TimelineRecycler extends Fragment {

    public TimelineRecycler() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_timeline_recycler, container, false);
    }

}
