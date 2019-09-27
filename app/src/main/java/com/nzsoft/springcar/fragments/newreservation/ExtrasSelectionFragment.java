package com.nzsoft.springcar.fragments.newreservation;


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

    private RadioGroup insuranceType;
    private CheckBox tireAndGlassCheckbox;
    private CheckBox extraBabyChair;
    private CheckBox extraChildChair;
    private CheckBox extraBoosterChair;
    private CheckBox extraSnowChains;
    private CheckBox extraSkyRack;


    public ExtrasSelectionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_extras_selection, container, false);

        ((NewReservationActivity)getActivity()).getLoadingPanel().setVisibility(View.GONE);

        insuranceType = view.findViewById(R.id.idInsuranceType);
        extraBabyChair = (CheckBox) view.findViewById(R.id.idExtra_BabyChair);
        extraChildChair = (CheckBox) view.findViewById(R.id.idExtra_ChildChair);
        extraBoosterChair = (CheckBox) view.findViewById(R.id.idExtra_BoosterChair);
        extraSnowChains = (CheckBox) view.findViewById(R.id.idExtra_SnowChains);
        extraSkyRack = (CheckBox) view.findViewById(R.id.idExtra_SkyRack);
        tireAndGlassCheckbox = (CheckBox) view.findViewById(R.id.idTireAndGlass);

        //Setear los precios según la categoría del coche seleccionado:

        TextView topInsurancePriceText = view.findViewById(R.id.idTopInsurancePrice);
        TextView baseInsurancePriceText = view.findViewById(R.id.idBasicInsurancePrice);
        TextView tireAndGlassPriceText = view.findViewById(R.id.idTireAndGlassPrice);


        Category carCategory = ((NewReservationActivity) getActivity()).getReservation().getCar().getCategory();

        topInsurancePriceText.setText(carCategory.getTopInsurancePrice() + " €");
        baseInsurancePriceText.setText(carCategory.getBaseInsurancePrice() + " €");
        tireAndGlassPriceText.setText(carCategory.getTireAndGlassProtectionPrice() + " €");

        return view;
    }

    public List<CommonExtra> getExtrasSelected (){

        List<CommonExtra> commonExtras = new ArrayList<>();

        if (tireAndGlassCheckbox.isChecked()){
            ((NewReservationActivity) getActivity()).getReservation().setHasTireAndGlassProtection(true);
        }

        if (extraBabyChair.isChecked()) {
            commonExtras.add(new CommonExtra(1L, "Baby Chair", 11.99));
        }

        if (extraChildChair.isChecked()) {
            commonExtras.add(new CommonExtra(2L, "Child Chair", 11.99));
        }

        if (extraBoosterChair.isChecked()) {
            commonExtras.add(new CommonExtra(3L, "Booster Chair", 9.99));
        }

        if (extraSnowChains.isChecked()) {
            commonExtras.add(new CommonExtra(4L, "Snow Chains", 18.33));
        }

        if (extraSkyRack.isChecked()) {
            commonExtras.add(new CommonExtra(5L, "Sky Rack", 18.33));
        }

        return commonExtras;
    }

    public int getInsuranceTypeId(){
        int insuranceTypeID = insuranceType.getCheckedRadioButtonId();
        return insuranceTypeID;
    }

}
