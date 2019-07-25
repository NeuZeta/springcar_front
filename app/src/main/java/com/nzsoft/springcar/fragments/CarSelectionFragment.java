package com.nzsoft.springcar.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.nzsoft.springcar.R;
import com.nzsoft.springcar.adapters.CarListAdapter;
import com.nzsoft.springcar.model.Car;
import com.nzsoft.springcar.retrofit.RetrofitHelper;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class CarSelectionFragment extends Fragment {


    public CarSelectionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_car_selection, container, false);

        final ListView carListView = (ListView) view.findViewById(R.id.idCarsList);

        Call<List<Car>> call = RetrofitHelper.getApiRest().getAllCars();

        call.enqueue(new Callback<List<Car>>() {
            @Override
            public void onResponse(Call<List<Car>> call, Response<List<Car>> response) {
                if (!response.isSuccessful()){
                    Log.d("****", "Response error: " + response.message());
                    return;
                }
                List<Car> cars = response.body();

                CarListAdapter carListAdapter = new CarListAdapter(getActivity(), cars);
                carListView.setAdapter(carListAdapter);

            }

            @Override
            public void onFailure(Call<List<Car>> call, Throwable t) {
                Log.d("***", "Error: " + t.getCause().toString());
            }

        });

        return view;
    }

}
