package com.nzsoft.springcar.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.nzsoft.springcar.R;
import com.nzsoft.springcar.activities.MyReservationsActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyReservationsButtonsFragment extends Fragment {

    private Button backBtn;
    private Button deleteBtn;

    public MyReservationsButtonsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_my_reservations_buttons, container, false);

        backBtn = (Button) view.findViewById(R.id.idBackButton_myReservations);
        deleteBtn = (Button) view.findViewById(R.id.idDeleteBtn_myReservations);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MyReservationsActivity)getActivity()).goBack();
            }
        });

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MyReservationsActivity)getActivity()).CancelReservation();
            }
        });

        return view;
    }

}
