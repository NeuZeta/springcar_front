package com.nzsoft.springcar.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.nzsoft.springcar.R;
import com.nzsoft.springcar.activities.NewReservationActivity;
import com.nzsoft.springcar.model.Category;
import com.nzsoft.springcar.model.CommonExtra;
import com.nzsoft.springcar.model.Reservation;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ExtrasSelectionFragment extends Fragment {


    public ExtrasSelectionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_extras_selection, container, false);

        ((NewReservationActivity)getActivity()).getLoadingPanel().setVisibility(View.GONE);

        //Setear los precios según la categoría del coche seleccionado:

        TextView topInsurancePriceText = view.findViewById(R.id.idTopInsurancePrice);
        TextView baseInsurancePriceText = view.findViewById(R.id.idBasicInsurancePrice);
        TextView tireAndGlassPriceText = view.findViewById(R.id.idTireAndGlassPrice);

        Category carCategory = ((NewReservationActivity) getActivity()).getReservation().getCar().getCategory();

        topInsurancePriceText.setText(carCategory.getTopInsurancePrice() + " €");
        baseInsurancePriceText.setText(carCategory.getBaseInsurancePrice() + " €");
        tireAndGlassPriceText.setText(carCategory.getTireAndGlassProtectionPrice() + " €");

        Button nextBtn = (Button) view.findViewById(R.id.idNextButton_Dates);
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                RadioGroup insuranceType = view.findViewById(R.id.idInsuranceType);

                int insuranceTypeID = insuranceType.getCheckedRadioButtonId();

                switch (insuranceTypeID){
                    case (R.id.idBasicInsurance):
                        ((NewReservationActivity) getActivity()).getReservation().setInsuranceType(Reservation.InsuranceType.BASE);
                        break;
                    case (R.id.idTopInsurance):
                        ((NewReservationActivity) getActivity()).getReservation().setInsuranceType(Reservation.InsuranceType.TOP);
                        break;
                }

                CheckBox tireAndGlassCheckbox = (CheckBox) view.findViewById(R.id.idTireAndGlass);
                if (tireAndGlassCheckbox.isChecked()){
                    ((NewReservationActivity) getActivity()).getReservation().setHasTireAndGlassProtection(true);
                }

                List<CommonExtra> commonExtras = new ArrayList<>();

                CheckBox extraBabyChair = (CheckBox) view.findViewById(R.id.idExtra_BabyChair);
                if (extraBabyChair.isChecked()) {
                    commonExtras.add(new CommonExtra(1L, "Baby Chair", 11.99));
                }

                CheckBox extraChildChair = (CheckBox) view.findViewById(R.id.idExtra_ChildChair);
                if (extraChildChair.isChecked()) {
                    commonExtras.add(new CommonExtra(2L, "Child Chair", 11.99));
                }

                CheckBox extraBoosterChair = (CheckBox) view.findViewById(R.id.idExtra_BoosterChair);
                if (extraBoosterChair.isChecked()) {
                    commonExtras.add(new CommonExtra(3L, "Booster Chair", 9.99));
                }

                CheckBox extraSnowChains = (CheckBox) view.findViewById(R.id.idExtra_SnowChains);
                if (extraSnowChains.isChecked()) {
                    commonExtras.add(new CommonExtra(4L, "Snow Chains", 18.33));
                }

                CheckBox extraSkyRack = (CheckBox) view.findViewById(R.id.idExtra_SkyRack);
                if (extraSkyRack.isChecked()) {
                    commonExtras.add(new CommonExtra(5L, "Sky Rack", 18.33));
                }

                ((NewReservationActivity)getActivity()).getReservation().setCommonExtras(commonExtras);
                ((NewReservationActivity)getActivity()).setCurrentStep(NewReservationActivity.CurrentStep.CONFIRMATION);
                ((NewReservationActivity)getActivity()).replaceFragments(ConfirmationFragment.class, R.id.idContentFragment);

            }
        });

        Button backBtn = (Button) view.findViewById(R.id.idBackButton_Dates);
        backBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                ((NewReservationActivity)getActivity()).setCurrentStep(NewReservationActivity.CurrentStep.CAR);
                ((NewReservationActivity) getActivity()).replaceFragments(CarSelectionFragment.class, R.id.idContentFragment);
            }
        });

        return view;
    }

}
