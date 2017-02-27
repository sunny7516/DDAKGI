package com.example.tacademy.ddakgi.view.Help;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.ExpandableListView;

import com.example.tacademy.ddakgi.R;
import com.example.tacademy.ddakgi.base.BaseActivity;

import java.util.ArrayList;

public class HelpActivity extends BaseActivity {

    private ArrayList<String> parentItems = new ArrayList<String>();
    private ArrayList<Object> childItems = new ArrayList<Object>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        ExpandableListView expandableListView = (ExpandableListView) findViewById(R.id.helpList);

        expandableListView.setGroupIndicator(null);
        expandableListView.setClickable(true);

        setGroupParents();
        setChildData();

        BaseExpandableAdapter baseExpandableAdapter = new BaseExpandableAdapter(parentItems, childItems);
        baseExpandableAdapter.setInflater((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE), this);
        expandableListView.setAdapter(baseExpandableAdapter);
    }

    public void setGroupParents() {
        parentItems.add(getResources().getString(R.string.helpQueOne));
        parentItems.add(getResources().getString(R.string.helpQueTwo));
        parentItems.add(getResources().getString(R.string.helpQueThree));
        parentItems.add(getResources().getString(R.string.helpQueFour));
        parentItems.add(getResources().getString(R.string.helpQueFive));
        parentItems.add(getResources().getString(R.string.helpQueSix));
        parentItems.add(getResources().getString(R.string.helpQueSeven));
        parentItems.add(getResources().getString(R.string.helpQueEight));
    }

    public void setChildData() {
        ArrayList<String> child = new ArrayList<String>();
        child.add("");
        child.add(getResources().getString(R.string.helpAnsOne));
        child.add("");
        childItems.add(child);

        child = new ArrayList<String>();
        child.add("");
        child.add(getResources().getString(R.string.helpAnsTwo));
        child.add("");
        childItems.add(child);

        child = new ArrayList<String>();
        child.add("");
        child.add(getResources().getString(R.string.helpAnsThree));
        child.add("");
        childItems.add(child);

        child = new ArrayList<String>();
        child.add("");
        child.add(getResources().getString(R.string.helpAnsFour));
        child.add("");
        childItems.add(child);

        child = new ArrayList<String>();
        child.add("");
        child.add(getResources().getString(R.string.helpAnsFive));
        child.add("");
        childItems.add(child);

        child = new ArrayList<String>();
        child.add("");
        child.add(getResources().getString(R.string.helpAnsSix));
        child.add("");
        childItems.add(child);

        child = new ArrayList<String>();
        child.add("");
        child.add(getResources().getString(R.string.helpAnsSeven));
        child.add("");
        childItems.add(child);

        child = new ArrayList<String>();
        child.add("");
        child.add(getResources().getString(R.string.helpAnsEight));
        child.add("");
        childItems.add(child);
    }
}
