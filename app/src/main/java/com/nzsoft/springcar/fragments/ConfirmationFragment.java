package com.nzsoft.springcar.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.nzsoft.springcar.R;
import com.nzsoft.springcar.activities.ReservationActivity;
import com.nzsoft.springcar.activities.SuccessActivity;
import com.nzsoft.springcar.model.Car;
import com.nzsoft.springcar.model.CommonExtra;
import com.nzsoft.springcar.model.Reservation;
import com.nzsoft.springcar.retrofit.RetrofitHelper;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class ConfirmationFragment extends Fragment {

    private TextView selectedOffice;
    private TextView selectedPickUpTime;
    private TextView selectedDropOffTime;

    private Reservation reservation;


    public ConfirmationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        reservation = ((ReservationActivity)getActivity()).getReservation();

        View view = inflater.inflate(R.layout.fragment_confirmation, container, false);

        FrameLayout placeHolder = (FrameLayout) view.findViewById(R.id.idReservationDestinationConfirmation);
        getLayoutInflater().inflate(R.layout.fragment_reservation, placeHolder);


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

        Button nextBtn = (Button) view.findViewById(R.id.idNextButton_Confirmation);
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Seteamos la fecha actual en la reserva
                reservation.setReservationDate(new Date());

                GsonBuilder builder = new GsonBuilder();
                builder.registerTypeAdapter(Date.class, new JsonSerializer<Date>() {
                    public JsonElement serialize(Date src, Type typeOfSrc, JsonSerializationContext context) {

                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
                        String dateStr = sdf.format(src);
                        return new JsonPrimitive(dateStr);
                    }
                });

                /*
                Gson gson = builder.create();
                gson.toJson(reservation);
                Log.d("***", gson.toJson(reservation));
                */

                Call<Reservation> call = RetrofitHelper.getApiRest().createReservation(reservation);

                call.enqueue(new Callback<Reservation>() {
                    @Override
                    public void onResponse(Call<Reservation> call, Response<Reservation> response) {
                        Log.d("***", "Response: " + response.toString());

                        Intent intentSuccess = new Intent(getContext(), SuccessActivity.class);
                        intentSuccess.putExtra("ReservationID", response.body().getId());
                        startActivity(intentSuccess);
                    }

                    @Override
                    public void onFailure(Call<Reservation> call, Throwable t) {
                        Log.d("***", "Error: " + t.getCause().toString());
                    }
                });

            }
        });

        Button prevBtn = (Button) view.findViewById(R.id.idBackButton_Confirmation);
        prevBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ReservationActivity)getActivity()).setCurrentStep(ReservationActivity.CurrentStep.EXTRAS);
                ((ReservationActivity) getActivity()).replaceFragments(ExtrasSelectionFragment.class, R.id.idContentFragment);
            }
        });

        return view;
    }

}
