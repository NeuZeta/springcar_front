package com.nzsoft.springcar.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.nzsoft.springcar.R;
import com.nzsoft.springcar.activities.MainActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class DatesSelectionViewFragment extends Fragment {

    private Button editBtn;

    public DatesSelectionViewFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_view_dates_selection, container, false);

        editBtn = view.findViewById(R.id.idSubmitDatesBtn);
        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ((MainActivity) getActivity()).replaceFragments(DatesSelectionFragment.class, R.id.idOpcion1Panel);

            }
        });

        return view;
    }

}
