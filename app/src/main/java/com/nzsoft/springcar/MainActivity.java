package com.nzsoft.springcar;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.widget.ExpandableListView;


import com.nzsoft.springcar.adapters.ExpandableListAdapter;
import com.nzsoft.springcar.adapters.testAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private ExpandableListView listView;
    private ExpandableListAdapter adapter;
    private testAdapter adapterTest;
    private List<String> listDataHeaders;
    private List<Integer> layouts;
    private List<Fragment> fragments;
    private Map<String, List<String>> listHashmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initData();

        listView = (ExpandableListView) findViewById(R.id.levelExp);
        //adapter = new ExpandableListAdapter(this, listDataHeaders, listHashmap);
        //listView.setAdapter(adapter);

        adapterTest = new testAdapter(this, listDataHeaders, layouts, fragments);
        listView.setAdapter(adapterTest);

    }

    private void initData() {

        listDataHeaders = new ArrayList<>();
        listDataHeaders.add("Dates Selection");
        listDataHeaders.add("Car Selection");
        listDataHeaders.add("Extras Selection");

        List<String> opciones1 = new ArrayList<>();
        opciones1.add("SubOpcion 1.1");

        List<String> opciones2 = new ArrayList<>();
        opciones2.add("SubOpcion 2.1");
        opciones2.add("SubOpcion 2.2");
        opciones2.add("SubOpcion 2.3");

        List<String> opciones3 = new ArrayList<>();
        opciones3.add("SubOpcion 3.1");
        opciones3.add("SubOpcion 3.2");


        listHashmap = new HashMap<>();
        listHashmap.put(listDataHeaders.get(0), opciones1);
        listHashmap.put(listDataHeaders.get(1), opciones2);
        listHashmap.put(listDataHeaders.get(2), opciones3);

        fragments = new ArrayList<>();
        fragments.add(new BlankFragment());
        fragments.add(new BlankFragment2());
        fragments.add(new BlankFragment2());

        layouts = new ArrayList<>();
        layouts.add(R.layout.test_fragment_item);
        layouts.add(R.layout.test_fragment_item_2);
        layouts.add(R.layout.test_fragment_item_3);

    }
}
