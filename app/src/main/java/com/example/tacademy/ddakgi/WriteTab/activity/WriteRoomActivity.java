package com.example.tacademy.ddakgi.WriteTab.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.example.tacademy.ddakgi.R;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class WriteRoomActivity extends AppCompatActivity {
    LinearLayout roomType;
    LinearLayout roomSize;
    LinearLayout extraInfo;

    Boolean roomflag = true;
    Boolean sizeflag = true;
    Boolean extraflag = true;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.write_room_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();

        switch (itemId) {
            case R.id.writeroomBack:
                break;
            case R.id.writeroomFinish:
                break;
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_room);

        Toolbar toolbar = (Toolbar) findViewById(R.id.writeroomTool);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);

        roomType = (LinearLayout) findViewById(R.id.roomType);
        roomSize = (LinearLayout) findViewById(R.id.roomSize);
        extraInfo = (LinearLayout) findViewById(R.id.extraInfo);
    }

    public void showRoomType(View view) {
        if (roomflag) {
            roomflag = false;
            roomType.setVisibility(VISIBLE);
        } else {
            roomflag = true;
            roomType.setVisibility(GONE);
        }
    }

    public void showRoomSize(View view) {
        if (sizeflag) {
            sizeflag = false;
            roomSize.setVisibility(VISIBLE);
        } else {
            sizeflag = true;
            roomSize.setVisibility(GONE);
        }
    }

    public void ShowExtraInfo(View view) {
        if (extraflag) {
            extraflag = false;
            extraInfo.setVisibility(VISIBLE);
        } else {
            extraflag = true;
            extraInfo.setVisibility(GONE);
        }
    }

    // 위치 등록
    public void goLocate(View view){

    }
}
