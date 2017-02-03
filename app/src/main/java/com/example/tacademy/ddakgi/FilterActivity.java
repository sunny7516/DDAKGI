package com.example.tacademy.ddakgi;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

public class FilterActivity extends AppCompatActivity {

    // Filter Button을 Toolbar에 적용
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.filter_layout_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();

        switch (itemId){
            case R.id.filter_cancel:
                this.finish();
                /*
                Intent intent = new Intent(this, HomeActivity.class);
                startActivity(intent);*/
                break;
            case R.id.filter_reset:
                break;
            case R.id.filter_apply:
                break;
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        Toolbar toolbar = (Toolbar) findViewById(R.id.filterToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);
    }
}
