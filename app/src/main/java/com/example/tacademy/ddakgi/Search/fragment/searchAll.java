package com.example.tacademy.ddakgi.Search.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tacademy.ddakgi.R;

/**
* 검색창에서 전체검색에 해당하는 Fragment
* */

public class SearchAll extends Fragment {
    public SearchAll() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search_all, container, false);
    }
}
