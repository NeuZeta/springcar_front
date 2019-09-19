package com.nzsoft.springcar.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.nzsoft.springcar.R;
import com.nzsoft.springcar.activities.NewReservationActivity;
import com.nzsoft.springcar.activities.SuccessActivity;
import com.nzsoft.springcar.model.Reservation;
import com.nzsoft.springcar.retrofit.RetrofitHelper;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.SocketTimeoutException;
import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class ConfirmationFragment extends Fragment {

    private TextView introText;
    private TextView reservationNumberText;

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

        View view = inflater.inflate(R.layout.fragment_confirmation, container, false);

        reservation = ((NewReservationActivity)getActivity()).getReservation();

        FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
        ReservationViewFragment reservationViewFragment = new ReservationViewFragment();
        reservationViewFragment.setReservation(reservation);
        reservationViewFragment.setHasId(false);
        fragmentTransaction.add(R.id.idReservationDestination, reservationViewFragment);
        fragmentTransaction.commit();

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

                Call<Reservation> call = RetrofitHelper.getApiRest().createReservation(reservation);

                call.enqueue(new Callback<Reservation>() {
                    @Override
                    public void onResponse(Call<Reservation> call, Response<Reservation> response) {
                        Log.d("***", "Response: " + response.toString());

                        CarSelectionFragment.selectedCar = -1;
                        Intent intentSuccess = new Intent(getContext(), SuccessActivity.class);
                        intentSuccess.putExtra("ReservationID", response.body().getId());
                        startActivity(intentSuccess);
                    }

                    @Override
                    public void onFailure(Call<Reservation> call, Throwable t) {
                        if (t instanceof SocketTimeoutException)
                        {
                            call.clone().enqueue(this);
                        }
                        else if (t instanceof IOException)
                        {
                            call.clone().enqueue(this);
                        }
                        else
                        {
                            Log.d("___", t.toString());
                        }
                    }
                });

            }
        });

        Button prevBtn = (Button) view.findViewById(R.id.idBackButton_Confirmation);
        prevBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((NewReservationActivity)getActivity()).setCurrentStep(NewReservationActivity.CurrentStep.EXTRAS);
                ((NewReservationActivity) getActivity()).replaceFragments(ExtrasSelectionFragment.class, R.id.idContentFragment);
            }
        });

        return view;
    }

}
