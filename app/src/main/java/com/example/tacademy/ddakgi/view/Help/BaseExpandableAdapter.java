package com.example.tacademy.ddakgi.view.Help;

import android.app.Activity;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.example.tacademy.ddakgi.R;

import java.util.ArrayList;

import io.chooco13.NotoTextView;

/**
 * 도움말 화면에서 사용한 Expandable ListView의 Adpater
 */

public class BaseExpandableAdapter extends BaseExpandableListAdapter {
    private Activity activity;
    private ArrayList<Object> childItems;
    private LayoutInflater inflater = null;
    private ArrayList<String> parentItems, child;

    public BaseExpandableAdapter(ArrayList<String> parents, ArrayList<Object> children) {
        this.parentItems = parents;
        this.childItems = children;
    }

    public void setInflater(LayoutInflater inflater, Activity activity) {
        this.inflater = inflater;
        this.activity = activity;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        child = (ArrayList<String>) childItems.get(groupPosition);

        TextView textView = null;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.help_list_row, null);
        }
        textView = (NotoTextView) convertView.findViewById(R.id.helpTextView);
        textView.setText(child.get(childPosition));

        return convertView;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.help_list_row, null);
        }
        ((NotoTextView) convertView).setText(parentItems.get(groupPosition));

        if (isExpanded) {
            ((NotoTextView) convertView).setTextColor(ContextCompat.getColor(convertView.getContext(), R.color.textpointColor));
        } else {
            ((NotoTextView) convertView).setTextColor(ContextCompat.getColor(convertView.getContext(), R.color.subTextColor));
        }
        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return null;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return null;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return ((ArrayList<String>) childItems.get(groupPosition)).size();
    }

    @Override
    public int getGroupCount() {
        return parentItems.size();
    }

    @Override
    public void onGroupCollapsed(int groupPosition) {
        super.onGroupCollapsed(groupPosition);
    }

    @Override
    public void onGroupExpanded(int groupPosition) {
        super.onGroupExpanded(groupPosition);
    }
}
