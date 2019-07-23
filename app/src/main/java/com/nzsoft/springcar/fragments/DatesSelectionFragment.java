package com.nzsoft.springcar.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.nzsoft.springcar.R;
import com.nzsoft.springcar.activities.CarSelectionActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class DatesSelectionFragment extends Fragment {

    private Button submitBtn;

    public DatesSelectionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_dates_selection, container, false);

        submitBtn = (Button) view.findViewById(R.id.idSubmitDatesBtn);

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), CarSelectionActivity.class);
                startActivity(intent);
            }
        });

        // Inflate the layout for this fragment
        return view;
    }

}
