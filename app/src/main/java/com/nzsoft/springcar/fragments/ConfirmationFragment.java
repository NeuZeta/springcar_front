package com.nzsoft.springcar.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.nzsoft.springcar.R;
import com.nzsoft.springcar.activities.MainActivity;
import com.nzsoft.springcar.model.Car;
import com.nzsoft.springcar.model.CommonExtra;
import com.nzsoft.springcar.model.Reservation;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ConfirmationFragment extends Fragment {

    private TextView selectedOffice;
    private TextView selectedPickUpTime;
    private TextView selectedDropOffTime;

    private ListView selectedCar;
    private ListView selectedExtras;

    private Button nextBtn;
    private Button prevBtn;


    public ConfirmationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        MainActivity mainActivity = ((MainActivity)getActivity());

        View view = inflater.inflate(R.layout.fragment_confirmation, container, false);

        selectedOffice = (TextView) view.findViewById(R.id.idOfficeSelected);
        selectedOffice.setText(mainActivity.getSelectedOffice().getName());

        selectedPickUpTime = (TextView) view.findViewById(R.id.idPickUpDateSelected);
        selectedDropOffTime = (TextView) view.findViewById(R.id.idDropOffDateSelected);

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");


        selectedPickUpTime.setText(sdf.format(mainActivity.getReservation().getPickUpDate()));
        selectedDropOffTime.setText(sdf.format(mainActivity.getReservation().getDropOffDate()));

        View carView = inflater.inflate(R.layout.row_model_car, container, false);

        TextView carModelText = (TextView) carView.findViewById(R.id.idCarInfoModel);
        ImageView carImage = (ImageView) carView.findViewById(R.id.idCarImage);

        Car car = mainActivity.getReservation().getCar();

        carModelText.setText(car.getModel());

        String imgURL = "https://springcarback.herokuapp.com/cars/image/" + car.getPhoto();

        Picasso.get().load(imgURL).into(carImage);

        TextView carPriceText = (TextView) carView.findViewById(R.id.idCarInfoPriceBase);
        carPriceText.setText(car.getBasePrice() + "€");

        LinearLayout CarLayout = (LinearLayout) view.findViewById(R.id.idCarInfoLayout);
        CarLayout.addView(carView);


        //Añadir seccion seguros&extras

        LinearLayout extrasLayout = (LinearLayout) view.findViewById(R.id.idExtrasInfoLayout);

        Reservation.InsuranceType insuranceType = mainActivity.getReservation().getInsuranceType();
        switch (insuranceType){
            case TOP:
                View topInsuranceView = inflater.inflate(R.layout.row_model_extra, container, false);

                TextView topInsuranceDescription = topInsuranceView.findViewById(R.id.idExtraDescription);
                topInsuranceDescription.setText(getActivity().getResources().getString(R.string.top_insurance_text));

                TextView topInsurancePrice = topInsuranceView.findViewById(R.id.idExtraPrice);
                topInsurancePrice.setText(mainActivity.getReservation().getCar().getCategory().getTopInsurancePrice() + "€");

                extrasLayout.addView(topInsuranceView);
                break;

            case BASE:
                View baseInsuranceView = inflater.inflate(R.layout.row_model_extra, container, false);

                TextView baseInsuranceDescription = baseInsuranceView.findViewById(R.id.idExtraDescription);
                baseInsuranceDescription.setText(getActivity().getResources().getString(R.string.base_insurance_text));

                TextView baseInsurancePrice = baseInsuranceView.findViewById(R.id.idExtraPrice);
                baseInsurancePrice.setText(mainActivity.getReservation().getCar().getCategory().getBaseInsurancePrice() + "€");

                extrasLayout.addView(baseInsuranceView);
                break;
        }

        if (mainActivity.getReservation().isHasTireAndGlassProtection()){
            View tireAndGlassView = inflater.inflate(R.layout.row_model_extra, container, false);

            TextView tireAndGlassDescription = tireAndGlassView.findViewById(R.id.idExtraDescription);
            tireAndGlassDescription.setText(getActivity().getResources().getString(R.string.tire_glass_text));

            TextView tireAndGlassPrice = tireAndGlassView.findViewById(R.id.idExtraPrice);
            tireAndGlassPrice.setText(mainActivity.getReservation().getCar().getCategory().getTireAndGlassProtectionPrice() + "€");

            extrasLayout.addView(tireAndGlassView);
        }

        List<CommonExtra> extras = mainActivity.getReservation().getCommonExtras();


            for (CommonExtra extra : extras){

                View extraView = inflater.inflate(R.layout.row_model_extra, container, false);

                TextView extraDescription = extraView.findViewById(R.id.idExtraDescription);
                extraDescription.setText(extra.getName());

                TextView extraPrice = extraView.findViewById(R.id.idExtraPrice);
                extraPrice.setText(extra.getPrice() + "€");

                extrasLayout.addView(extraView);
            }

        TextView totalPriceView = (TextView) view.findViewById(R.id.idTotalPrice);
        totalPriceView.setText(String.format("%.2f", mainActivity.getReservation().getTotalPrice()) + "€");

        Button nextBtn = (Button) view.findViewById(R.id.idNextButton_Confirmation);
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //De momento nada
            }
        });

        Button prevBtn = (Button) view.findViewById(R.id.idBackButton_Confirmation);
        prevBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).setCurrentStep(MainActivity.CurrentStep.EXTRAS);
                ((MainActivity) getActivity()).replaceFragments(ExtrasSelectionFragment.class, R.id.idContentFragment);
            }
        });

        return view;
    }

}
