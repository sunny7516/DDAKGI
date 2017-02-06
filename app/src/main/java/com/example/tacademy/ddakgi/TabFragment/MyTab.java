package com.example.tacademy.ddakgi.TabFragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.tacademy.ddakgi.MyTab.HelpActivity;
import com.example.tacademy.ddakgi.MyTab.SettingActivity;
import com.example.tacademy.ddakgi.R;


public class MyTab extends Fragment {
    public MyTab() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_tab, container, false);

        Toolbar toolbar = (Toolbar) view.findViewById(R.id.myToolbar);

        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setTitle(null);
        setHasOptionsMenu(true);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        // destroy all menu and re-call onCreateOptionsMenu
        getActivity().invalidateOptionsMenu();
    }

    // Filter Button을 Toolbar에 적용
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.mytab_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_help:
                Intent goHelp = new Intent(getActivity(), HelpActivity.class);
                startActivity(goHelp);
                break;
            case R.id.menu_settings:
                Intent goSetting = new Intent(getActivity(), SettingActivity.class);
                startActivity(goSetting);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
