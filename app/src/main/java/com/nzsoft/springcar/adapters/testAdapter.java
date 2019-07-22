package com.nzsoft.springcar.adapters;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.graphics.Typeface;
import android.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.nzsoft.springcar.R;

import java.util.List;
import java.util.Map;

public class testAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<String> titulos;
    private List<Fragment> fragments;

    public testAdapter(Context context, List<String> titulos, List<Fragment> fragments) {
        this.context = context;
        this.titulos = titulos;
        this.fragments = fragments;
    }


    @Override
    public int getGroupCount() {
        return titulos.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return fragments.size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.titulos.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return fragments.get(groupPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String headerTitle = (String) getGroup(groupPosition);

        if (convertView == null){
            LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_group, null);
        }

        TextView lv1ListHeader = (TextView) convertView.findViewById(R.id.id1ListHeader);
        lv1ListHeader.setTypeface(null, Typeface.BOLD);
        lv1ListHeader.setText(headerTitle);

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        Fragment fragment = (Fragment) getChild(groupPosition, childPosition);

        if (convertView == null){
            LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.test_fragment_item, null);
        }

        FragmentManager fragmentManager = ((AppCompatActivity)this.context).getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.replace(R.id.idDestino, fragment);

        fragmentTransaction.commit();

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}
