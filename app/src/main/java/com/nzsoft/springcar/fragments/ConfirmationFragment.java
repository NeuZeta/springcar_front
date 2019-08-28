package com.nzsoft.springcar.fragments;


import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.nzsoft.springcar.R;
import com.nzsoft.springcar.activities.MainActivity;
import com.nzsoft.springcar.adapters.CarListAdapter;
import com.nzsoft.springcar.adapters.ExtrasListAdapter;
import com.nzsoft.springcar.model.Car;
import com.nzsoft.springcar.model.CommonExtra;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

        selectedCar = (ListView) view.findViewById(R.id.idCarSelectedList);
        List<Car> carSelected = new ArrayList<>();
        carSelected.add(mainActivity.getReservation().getCar());

        CarListAdapter carListAdapter = new CarListAdapter(getActivity(), carSelected);
        selectedCar.setAdapter(carListAdapter);

        selectedExtras = (ListView) view.findViewById(R.id.idExtrasList);
        List<CommonExtra> extras = mainActivity.getReservation().getCommonExtras();
        Log.d("****", extras.toString());

        ExtrasListAdapter extrasListAdapter = new ExtrasListAdapter(getActivity(), extras);
        selectedExtras.setAdapter(extrasListAdapter);

        return view;
    }

}
