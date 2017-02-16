package com.example.tacademy.ddakgi.HomeTab.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.tacademy.ddakgi.R;

/**
 * HomeTab 상단버튼에서 이어지는 filter화면
 */
public class FilterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        Toolbar toolbar = (Toolbar) findViewById(R.id.filterToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);
    }
}
