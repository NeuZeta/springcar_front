package com.nzsoft.springcar.fragments.newreservation;


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
import com.nzsoft.springcar.fragments.myreservations.ReservationViewFragment;
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

        ((NewReservationActivity)getActivity()).getLoadingPanel().setVisibility(View.GONE);

        reservation = ((NewReservationActivity)getActivity()).getReservation();

        FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
        ReservationViewFragment reservationViewFragment = new ReservationViewFragment();
        reservationViewFragment.setReservation(reservation);
        reservationViewFragment.setHasId(false);
        fragmentTransaction.add(R.id.idReservationDestination, reservationViewFragment);
        fragmentTransaction.commit();

        return view;
    }

}
