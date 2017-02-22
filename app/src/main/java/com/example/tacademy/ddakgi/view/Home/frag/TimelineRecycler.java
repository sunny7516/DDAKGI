package com.example.tacademy.ddakgi.view.Home.frag;

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
        View view = inflater.inflate(R.layout.home_timeline, container, false);
        return view;
    }

}