package com.nzsoft.springcar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ExpandableListView;


import com.nzsoft.springcar.adapters.ExpandableListAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private ExpandableListView listView;
    private ExpandableListAdapter adapter;
    private List<String> listDataHeaders;
    private Map<String, List<String>> listHashmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initData();

        listView = (ExpandableListView) findViewById(R.id.levelExp);
        adapter = new ExpandableListAdapter(this, listDataHeaders, listHashmap);
        listView.setAdapter(adapter);
    }

    private void initData() {

        listDataHeaders = new ArrayList<>();
        listDataHeaders.add("Opcion 1");
        listDataHeaders.add("Opcion 2");
        listDataHeaders.add("Opcion 3");

        List<String> opciones1 = new ArrayList<>();
        opciones1.add("SubOpcion 1");

        List<String> opciones2 = new ArrayList<>();
        opciones2.add("SubOpcion 1");
        opciones2.add("SubOpcion 2");
        opciones2.add("SubOpcion 3");

        List<String> opciones3 = new ArrayList<>();
        opciones3.add("SubOpcion 1");
        opciones3.add("SubOpcion 2");


        listHashmap = new HashMap<>();
        listHashmap.put(listDataHeaders.get(0), opciones1);
        listHashmap.put(listDataHeaders.get(1), opciones2);
        listHashmap.put(listDataHeaders.get(2), opciones3);


    }
}
