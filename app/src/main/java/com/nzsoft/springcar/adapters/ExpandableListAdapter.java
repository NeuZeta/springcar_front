package com.nzsoft.springcar.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.nzsoft.springcar.R;

import java.util.List;
import java.util.Map;

public class ExpandableListAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<String> listDataHeaders;
    private Map<String, List<String>> listHashmap;

    public ExpandableListAdapter(Context context, List<String> listDataHeaders, Map<String, List<String>> listHashmap) {
        this.context = context;
        this.listDataHeaders = listDataHeaders;
        this.listHashmap = listHashmap;
    }

    @Override
    public int getGroupCount() {
        return listDataHeaders.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return listHashmap.get(listDataHeaders.get(i)).size();
    }

    @Override
    public Object getGroup(int i) {
        return listDataHeaders.get(i);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return listHashmap.get(listDataHeaders.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View view, ViewGroup parent) {
        String headerTitle = (String) getGroup(groupPosition);

        if (view == null){
            LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.list_group, null);
        }

        TextView lv1ListHeader = (TextView) view.findViewById(R.id.id1ListHeader);
        lv1ListHeader.setTypeface(null, Typeface.BOLD);
        lv1ListHeader.setText(headerTitle);

        return view;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View view, ViewGroup parent) {
        final String childText = (String) getChild(groupPosition, childPosition);

        if (view == null){
            LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.list_item, null);
        }

        TextView textListChild = (TextView) view.findViewById(R.id.id1ListItem);
        textListChild.setText(childText);

        return view;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
