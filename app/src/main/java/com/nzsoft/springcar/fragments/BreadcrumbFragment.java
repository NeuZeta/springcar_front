package com.nzsoft.springcar.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.nzsoft.springcar.R;
import com.nzsoft.springcar.activities.ReservationActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class BreadcrumbFragment extends Fragment {

    private ImageView step01;
    private ImageView step02;
    private ImageView step03;
    private ImageView step04;
    private ImageView step05;

    private ImageView connection01;
    private ImageView connection02;
    private ImageView connection03;
    private ImageView connection04;


    public BreadcrumbFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_breadcrumb, container, false);

        step01 = (ImageView) view.findViewById(R.id.idBCStep01);
        step02 = (ImageView) view.findViewById(R.id.idBCStep02);
        step03 = (ImageView) view.findViewById(R.id.idBCStep03);
        step04 = (ImageView) view.findViewById(R.id.idBCStep04);
        step05 = (ImageView) view.findViewById(R.id.idBCStep05);

        connection01 = (ImageView) view.findViewById(R.id.idBCConection01);
        connection02 = (ImageView) view.findViewById(R.id.idBCConection02);
        connection03 = (ImageView) view.findViewById(R.id.idBCConection03);
        connection04 = (ImageView) view.findViewById(R.id.idBCConection04);

        return view;
    }


    public void changeStep (ReservationActivity.CurrentStep currentStep){

        if (currentStep == ReservationActivity.CurrentStep.LOCATION){
            step01.setImageDrawable(getResources().getDrawable(R.drawable.location_active));
            step02.setImageDrawable(getResources().getDrawable(R.drawable.inactive));
            connection01.setColorFilter(getResources().getColor(R.color.colorPaleGray));
        }

        if (currentStep == ReservationActivity.CurrentStep.DATES){
            step01.setImageDrawable(getResources().getDrawable(R.drawable.location_saved));
            step02.setImageDrawable(getResources().getDrawable(R.drawable.calendar_active));
            step03.setImageDrawable(getResources().getDrawable(R.drawable.inactive));
            connection01.setColorFilter(getResources().getColor(R.color.colorPrimary));
            connection02.setColorFilter(getResources().getColor(R.color.colorPaleGray));
        }

        if (currentStep == ReservationActivity.CurrentStep.CAR){
            step02.setImageDrawable(getResources().getDrawable(R.drawable.calendar_saved));
            step03.setImageDrawable(getResources().getDrawable(R.drawable.car_active));
            step04.setImageDrawable(getResources().getDrawable(R.drawable.inactive));
            connection02.setColorFilter(getResources().getColor(R.color.colorPrimary));
            connection03.setColorFilter(getResources().getColor(R.color.colorPaleGray));
        }

        if (currentStep == ReservationActivity.CurrentStep.EXTRAS){
            step03.setImageDrawable(getResources().getDrawable(R.drawable.car_saved));
            step04.setImageDrawable(getResources().getDrawable(R.drawable.extras_active));
            step05.setImageDrawable(getResources().getDrawable(R.drawable.inactive));
            connection03.setColorFilter(getResources().getColor(R.color.colorPrimary));
            connection04.setColorFilter(getResources().getColor(R.color.colorPaleGray));
        }

        if (currentStep == ReservationActivity.CurrentStep.CONFIRMATION){
            step04.setImageDrawable(getResources().getDrawable(R.drawable.extras_saved));
            step05.setImageDrawable(getResources().getDrawable(R.drawable.confirm_active));
            connection04.setColorFilter(getResources().getColor(R.color.colorPrimary));
        }

    }

}
