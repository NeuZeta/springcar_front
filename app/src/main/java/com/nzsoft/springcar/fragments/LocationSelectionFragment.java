package com.nzsoft.springcar.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.nzsoft.springcar.R;
import com.nzsoft.springcar.activities.MainActivity;
import com.nzsoft.springcar.model.Office;
import com.nzsoft.springcar.model.Reservation;
import com.nzsoft.springcar.retrofit.RetrofitHelper;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class LocationSelectionFragment extends Fragment {

    private Button nextBtn;
    private Spinner officeSpinner;
    private List<Office> offices;

    public LocationSelectionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_location_selection, container, false);

        /*
         *
         *  SPINNER DE SELECCIÓN DE OFICINA
         *
         * */

        officeSpinner = (Spinner) view.findViewById(R.id.idOfficeSelection);

        Call<List<Office>> call = RetrofitHelper.getApiRest().getAllOffices();

        call.enqueue(new Callback<List<Office>>() {
            @Override
            public void onResponse(Call<List<Office>> call, Response<List<Office>> response) {
                if (!response.isSuccessful()){
                    Log.d("****", "Response error: " + response.message());
                    return;
                }
                offices = response.body();

                List<String> officesNameList = new ArrayList<>();

                for (Office office : offices){
                    officesNameList.add(office.getName());
                }

                ArrayAdapter adapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_dropdown_item, officesNameList);
                officeSpinner.setAdapter(adapter);

                //If the reservation already has an Office selected:
                Office selectedOffice = ((MainActivity) getActivity()).getSelectedOffice();
                if ( selectedOffice != null){
                    for (int i = 0; i < offices.size(); i++){
                        if (offices.get(i).getId() == selectedOffice.getId()){
                            officeSpinner.setSelection(i);
                            return;
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Office>> call, Throwable t) {
                Log.d("***", t.getCause().toString());
            }
        });

        nextBtn = (Button) view.findViewById(R.id.idNextButton_Location);

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Recoger el valor del spinner de oficinas para recuperar la officina seleccionada
                Office office = offices.get(officeSpinner.getSelectedItemPosition());

                //Guardamos la oficina ya que se usa sólo para filtrar los coches

                ((MainActivity) getActivity()).setSelectedOffice(office);

                //mostrar listado de coches
                ((MainActivity)getActivity()).setCurrentStep(MainActivity.CurrentStep.DATES);
                ((MainActivity) getActivity()).replaceFragments(DatesSelectionFragment.class, R.id.idContentFragment);
            }
        });

        return view;
    }

}
