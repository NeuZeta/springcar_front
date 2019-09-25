package com.nzsoft.springcar.fragments.myreservations;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nzsoft.springcar.R;
import com.nzsoft.springcar.model.Car;
import com.nzsoft.springcar.model.CommonExtra;
import com.nzsoft.springcar.model.Reservation;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReservationViewFragment extends Fragment {

    private TextView introText;
    private TextView reservationNumberText;

    private TextView selectedOffice;
    private TextView selectedPickUpTime;
    private TextView selectedDropOffTime;

    private boolean hasId;

    private Reservation reservation;

    public ReservationViewFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_reservation, container, false);

        introText = (TextView) view.findViewById(R.id.idIntroText);
        reservationNumberText = (TextView) view.findViewById(R.id.idReservationNumber);

        if (!hasId){
            introText.setVisibility(View.VISIBLE);
            reservationNumberText.setVisibility(View.GONE);
        } else {
            introText.setVisibility(View.GONE);
            reservationNumberText.setVisibility(View.VISIBLE);
        }


        selectedOffice = (TextView) view.findViewById(R.id.idOfficeSelected);
        selectedOffice.setText(reservation.getCar().getOffice().getName());

        selectedPickUpTime = (TextView) view.findViewById(R.id.idPickUpDateSelected);
        selectedDropOffTime = (TextView) view.findViewById(R.id.idDropOffDateSelected);

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

        selectedPickUpTime.setText(sdf.format(reservation.getPickUpDate()));
        selectedDropOffTime.setText(sdf.format(reservation.getDropOffDate()));

        View carView = inflater.inflate(R.layout.row_model_car, container, false);

        TextView carModelText = (TextView) carView.findViewById(R.id.idCarInfoModel);
        ImageView carImage = (ImageView) carView.findViewById(R.id.idCarImage);

        Car car = reservation.getCar();

        carModelText.setText(car.getModel());

        String imgURL = "https://springcarback.herokuapp.com/cars/image/" + car.getPhoto();

        Picasso.get().load(imgURL).into(carImage);

        TextView carPriceText = (TextView) carView.findViewById(R.id.idCarInfoPriceBase);
        carPriceText.setText(car.getBasePrice() + "€");

        LinearLayout CarLayout = (LinearLayout) view.findViewById(R.id.idCarInfoLayout);
        CarLayout.addView(carView);

        //Añadir seccion seguros&extras

        LinearLayout extrasLayout = (LinearLayout) view.findViewById(R.id.idExtrasInfoLayout);

        Reservation.InsuranceType insuranceType = reservation.getInsuranceType();
        switch (insuranceType){
            case TOP:
                View topInsuranceView = inflater.inflate(R.layout.row_model_extra, container, false);

                TextView topInsuranceDescription = topInsuranceView.findViewById(R.id.idExtraDescription);
                topInsuranceDescription.setText(getActivity().getResources().getString(R.string.top_insurance_text));

                TextView topInsurancePrice = topInsuranceView.findViewById(R.id.idExtraPrice);
                topInsurancePrice.setText(reservation.getCar().getCategory().getTopInsurancePrice() + "€");

                extrasLayout.addView(topInsuranceView);
                break;

            case BASE:
                View baseInsuranceView = inflater.inflate(R.layout.row_model_extra, container, false);

                TextView baseInsuranceDescription = baseInsuranceView.findViewById(R.id.idExtraDescription);
                baseInsuranceDescription.setText(getActivity().getResources().getString(R.string.base_insurance_text));

                TextView baseInsurancePrice = baseInsuranceView.findViewById(R.id.idExtraPrice);
                baseInsurancePrice.setText(reservation.getCar().getCategory().getBaseInsurancePrice() + "€");

                extrasLayout.addView(baseInsuranceView);
                break;
        }

        if (reservation.isHasTireAndGlassProtection()){
            View tireAndGlassView = inflater.inflate(R.layout.row_model_extra, container, false);

            TextView tireAndGlassDescription = tireAndGlassView.findViewById(R.id.idExtraDescription);
            tireAndGlassDescription.setText(getActivity().getResources().getString(R.string.tire_glass_text));

            TextView tireAndGlassPrice = tireAndGlassView.findViewById(R.id.idExtraPrice);
            tireAndGlassPrice.setText(reservation.getCar().getCategory().getTireAndGlassProtectionPrice() + "€");

            extrasLayout.addView(tireAndGlassView);
        }

        List<CommonExtra> extras = reservation.getCommonExtras();


        for (CommonExtra extra : extras){

            View extraView = inflater.inflate(R.layout.row_model_extra, container, false);

            TextView extraDescription = extraView.findViewById(R.id.idExtraDescription);
            extraDescription.setText(extra.getName());

            TextView extraPrice = extraView.findViewById(R.id.idExtraPrice);
            extraPrice.setText(extra.getPrice() + "€");

            extrasLayout.addView(extraView);
        }

        TextView totalPriceView = (TextView) view.findViewById(R.id.idTotalPrice);

        reservation.setPrice();
        totalPriceView.setText(String.format("%.2f", reservation.getPrice()) + "€");

        return view;
    }

    public Reservation getReservation() {
        return reservation;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }

    public boolean isHasId() {
        return hasId;
    }

    public void setHasId(boolean hasId) {
        this.hasId = hasId;
    }
}
