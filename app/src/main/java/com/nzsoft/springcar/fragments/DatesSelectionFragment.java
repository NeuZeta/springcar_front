package com.nzsoft.springcar.fragments;


import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;

import com.nzsoft.springcar.R;
import com.nzsoft.springcar.activities.MainActivity;
import com.nzsoft.springcar.model.Office;
import com.nzsoft.springcar.model.Reservation;
import com.nzsoft.springcar.retrofit.RetrofitHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class DatesSelectionFragment extends Fragment {

    private Spinner officeSpinner;
    private TextView pickupDateTextView;
    private TextView dropoffDateTextView;
    private Button submitBtn;
    private Calendar calendar;
    private DatePickerDialog datePickerDialog;

    private List<Office> offices;


    public DatesSelectionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_dates_selection, container, false);

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
            }

            @Override
            public void onFailure(Call<List<Office>> call, Throwable t) {
                Log.d("***", t.getCause().toString());
            }
        });

        /*
         *
         *  SELECCIÓN DE FECHAS
         *
         * */

        pickupDateTextView = (TextView) view.findViewById(R.id.idPickupDate);
        dropoffDateTextView = (TextView) view.findViewById(R.id.idDropoffDate);

        pickupDateTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar = Calendar.getInstance();

                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);

                datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int mYear, int mMonth, int mDay) {
                        String month = ("00" + (mMonth+1)).substring(1);
                        pickupDateTextView.setText(mDay + "-" + month + "-" + mYear);
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });

        dropoffDateTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar = Calendar.getInstance();

                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);

                datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int mYear, int mMonth, int mDay) {
                        String month = ("00" + (mMonth+1)).substring(1);
                        dropoffDateTextView.setText(mDay + "-" + month + "-" + mYear);
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });

        /*
         *
         *  BOTON SUBMIT
         *
         * */

        submitBtn = (Button) view.findViewById(R.id.idSubmitDatesBtn);

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Recoger el valor del spinner de oficinas para recuperar la officina seleccionada
                Office office = offices.get(officeSpinner.getSelectedItemPosition());

                //Convertir el string en fecha
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                Date pickUpDate = null;
                Date dropOffDate = null;

                try {
                    pickUpDate = sdf.parse(pickupDateTextView.getText().toString());
                    dropOffDate = sdf.parse(dropoffDateTextView.getText().toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                //Inicializamos la reserva que iremos completando en las siguientes pantallas
                //Seteamos en esa reserva las fechas de inicio y fin
                //Guardamos la oficina ya que se usa sólo para filtrar los coches

                ((MainActivity) getActivity()).setReservation(new Reservation());
                ((MainActivity) getActivity()).setSelectedOffice(office);
                ((MainActivity) getActivity()).getReservation().setPickUpDate(pickUpDate);
                ((MainActivity) getActivity()).getReservation().setDropOffDate(dropOffDate);

                //mostrar listado de coches
                ((MainActivity) getActivity()).replaceFragments(CarSelectionFragment.class, R.id.idContentFragment);
            }
        });

        // Inflate the layout for this fragment
        return view;
    }


}
