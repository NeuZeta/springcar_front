package com.nzsoft.springcar.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.nzsoft.springcar.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class BreadcrumbFragment extends Fragment {

    private final int[] BOTONES_MAIN_MENU = {R.id.idDatesSelectionBC_btn, R.id.idCarSelectionBC_btn, R.id.idExtrasSelectionBC_btn};

    public BreadcrumbFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_breadcrumb, container, false);

        Button menuBtn;

        for (int i = 0; i < BOTONES_MAIN_MENU.length; i++){

            menuBtn = (Button) view.findViewById(BOTONES_MAIN_MENU[i]);

            final int BUTTON = i;

            menuBtn.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v) {
                    Fragment fragment = null;

                    switch (BUTTON){
                        case 0: fragment = new DatesSelectionFragment();
                            break;
                        case 1: fragment = new CarSelectionFragment();
                            break;
                        case 2: fragment = new CarSelectionFragment();
                            break;
                    }

                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                    fragmentTransaction.replace(R.id.idContentFragment, fragment);
                    fragmentTransaction.commit();
                }
            });
        }



        return view;
    }

}
