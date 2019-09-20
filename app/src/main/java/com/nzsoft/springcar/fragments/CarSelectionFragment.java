package com.nzsoft.springcar.fragments;


import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.nzsoft.springcar.R;
import com.nzsoft.springcar.activities.NewReservationActivity;
import com.nzsoft.springcar.adapters.CarListAdapter;
import com.nzsoft.springcar.model.Car;
import com.nzsoft.springcar.model.Reservation;
import com.nzsoft.springcar.retrofit.RetrofitHelper;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.text.SimpleDateFormat;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class CarSelectionFragment extends Fragment {

    public static int selectedCar = -1;
    private Button nextBtn;
    private Button prevBtn;


    public CarSelectionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_car_selection, container, false);

        final ListView carListView = (ListView) view.findViewById(R.id.idCarsList);

        ((NewReservationActivity)getActivity()).getLoadingPanel().setVisibility(View.GONE);

        nextBtn = (Button) view.findViewById(R.id.idNextButton_CarSelection);
        prevBtn = (Button) view.findViewById(R.id.idBackButton_CarSelection);

        if (((NewReservationActivity)getActivity()).getReservation().getCar() == null){
            nextBtn.getBackground().setColorFilter(nextBtn.getContext().getResources().getColor(R.color.colorPaleGray), PorterDuff.Mode.SRC);
            nextBtn.setEnabled(false);
        } else {
            nextBtn.getBackground().setColorFilter(nextBtn.getContext().getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC);
            nextBtn.setEnabled(true);
        }

        Reservation reservation = ((NewReservationActivity)getActivity()).getReservation();

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        String fechaFin = sdf.format(reservation.getDropOffDate());
        String fechaInicio = sdf.format(reservation.getPickUpDate());

        Long officeId = ((NewReservationActivity)getActivity()).getSelectedOffice().getId();

        Call<List<Car>> notAvailableCarsCall = RetrofitHelper.getApiRest().getNotAvailableCars(fechaFin, fechaInicio, officeId);

        ((NewReservationActivity)getActivity()).getLoadingPanel().setVisibility(View.VISIBLE);

        notAvailableCarsCall.enqueue(new Callback<List<Car>>() {
            @Override
            public void onResponse(Call<List<Car>> call, Response<List<Car>> response) {
                if (!response.isSuccessful()){
                    Log.d("****", "Response error: " + response.toString());
                    return;
                }

                ((NewReservationActivity)getActivity()).getLoadingPanel().setVisibility(View.GONE);

                final List<Car> cars = response.body();

                final CarListAdapter carListAdapter = new CarListAdapter(getActivity(), cars);
                carListView.setAdapter(carListAdapter);
                if (selectedCar != -1){
                    carListView.setSelection(selectedCar);
                }

                carListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        selectedCar = position;
                        carListAdapter.notifyDataSetChanged();
                        ((NewReservationActivity) getActivity()).getReservation().setCar(cars.get(position));

                        nextBtn.getBackground().setColorFilter(nextBtn.getContext().getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC);
                        nextBtn.setEnabled(true);
                    }
                });

            }

            @Override
            public void onFailure(Call<List<Car>> call, Throwable t) {
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



        nextBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                ((NewReservationActivity)getActivity()).setCurrentStep(NewReservationActivity.CurrentStep.EXTRAS);
                ((NewReservationActivity) getActivity()).replaceFragments(ExtrasSelectionFragment.class, R.id.idContentFragment);
            }
        });


        prevBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                ((NewReservationActivity)getActivity()).getReservation().setCar(null);
                selectedCar = -1;
                ((NewReservationActivity)getActivity()).setCurrentStep(NewReservationActivity.CurrentStep.DATES);
                ((NewReservationActivity) getActivity()).replaceFragments(DatesSelectionFragment.class, R.id.idContentFragment);
            }
        });

        return view;
    }

}
