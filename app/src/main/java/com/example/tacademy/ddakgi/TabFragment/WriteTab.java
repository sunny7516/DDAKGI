package com.example.tacademy.ddakgi.TabFragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.tacademy.ddakgi.R;
import com.example.tacademy.ddakgi.WriteTab.WriteMateActivity;
import com.example.tacademy.ddakgi.WriteTab.WriteRoomActivity;

public class WriteTab extends Fragment {
    ImageButton roomBt;
    ImageButton mateBt;

    public WriteTab() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_write_tab, container, false);

        roomBt = (ImageButton) view.findViewById(R.id.roomBt);
        mateBt = (ImageButton) view.findViewById(R.id.mateBt);
        roomBt.setOnClickListener(selectBtListener);
        mateBt.setOnClickListener(selectBtListener);
        return view;
    }

    private View.OnClickListener selectBtListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.roomBt:
                    Intent roomIntent = new Intent(getContext(), WriteRoomActivity.class);
                    startActivity(roomIntent);
                    break;
                case R.id.mateBt:
                    Intent mateIntent = new Intent(getContext(), WriteMateActivity.class);
                    startActivity(mateIntent);
                    break;
            }
        }
    };
}
