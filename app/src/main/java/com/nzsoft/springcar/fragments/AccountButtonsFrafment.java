package com.nzsoft.springcar.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.nzsoft.springcar.R;
import com.nzsoft.springcar.activities.AccountActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class AccountButtonsFrafment extends Fragment {

    public AccountButtonsFrafment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account_buttons_frafment, container, false);

        Button backBtn = view.findViewById(R.id.idBackBtn_Account);
        Button actionBtn = view.findViewById(R.id.idActionBtn_Account);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((AccountActivity)getActivity()).GoBack();
            }
        });

        actionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((AccountActivity)getActivity()).PerformAction();
            }
        });

        return view;
    }

}
