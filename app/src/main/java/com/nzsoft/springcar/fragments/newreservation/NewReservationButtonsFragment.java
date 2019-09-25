package com.nzsoft.springcar.fragments.newreservation;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.nzsoft.springcar.R;
import com.nzsoft.springcar.activities.NewReservationActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewReservationButtonsFragment extends Fragment {

    private Button backBtn;
    private Button nextBtn;

    public NewReservationButtonsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_new_reservation_buttons, container, false);

        backBtn = (Button) view.findViewById(R.id.idBackButton_NewReservation);
        nextBtn = (Button) view.findViewById(R.id.idNextButton_NewReservation);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((NewReservationActivity)getActivity()).goBack();
            }
        });

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((NewReservationActivity)getActivity()).nextStep();
            }
        });

        return view;
    }

}
