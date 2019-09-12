package com.nzsoft.springcar.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.nzsoft.springcar.R;
import com.nzsoft.springcar.activities.MyReservationsActivity;
import com.nzsoft.springcar.adapters.ReservationsListAdapter;
import com.nzsoft.springcar.model.Reservation;
import com.nzsoft.springcar.retrofit.RetrofitHelper;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReservationsListFragment extends Fragment {


    public ReservationsListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_reservations_list, container, false);


        Call<List<Reservation>> call = RetrofitHelper.getApiRest().getAllReservations();

        call.enqueue(new Callback<List<Reservation>>() {
            @Override
            public void onResponse(Call<List<Reservation>> call, Response<List<Reservation>> response) {

                if (!response.isSuccessful()){
                    Log.d("****", "Response error: " + response.message());
                    return;
                }

                final List<Reservation> reservations = response.body();

                final ReservationsListAdapter reservationsListAdapter = new ReservationsListAdapter(getContext(), reservations);

                final ListView reservationList = (ListView) view.findViewById(R.id.idReservationsList);
                reservationList.setAdapter(reservationsListAdapter);

                reservationList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        ((MyReservationsActivity)getActivity()).setStep(MyReservationsActivity.Step.DETAIL);
                        ReservationViewFragment reservationViewFragment = new ReservationViewFragment();
                        reservationViewFragment.setReservation(reservations.get(position));

                        ((MyReservationsActivity)getActivity()).ShowDeleteBtn();

                        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.idDestino, reservationViewFragment);
                        fragmentTransaction.commit();

                    }
                });

            }

            @Override
            public void onFailure(Call<List<Reservation>> call, Throwable t) {

            }
        });

        return view;

    }

}
