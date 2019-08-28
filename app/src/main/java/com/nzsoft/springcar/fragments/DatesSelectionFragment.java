package com.nzsoft.springcar.fragments;


import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import com.nzsoft.springcar.R;
import com.nzsoft.springcar.activities.MainActivity;
import com.nzsoft.springcar.model.Reservation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 */
public class DatesSelectionFragment extends Fragment {

    private TextView pickupDateTextView;
    private TextView dropoffDateTextView;
    private Button nextBtn;
    private Button backBtn;
    private Calendar calendar;
    private DatePickerDialog datePickerDialog;

    private SimpleDateFormat simpleDateFormat;


    public DatesSelectionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_dates_selection, container, false);

        simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");


        /*
        *
        * SETEAMOS FECHAS INICIALES
        *
        * */

        pickupDateTextView = (TextView) view.findViewById(R.id.idPickupDate);
        dropoffDateTextView = (TextView) view.findViewById(R.id.idDropoffDate);

        final Reservation reservation = ((MainActivity)getActivity()).getReservation();

        calendar = Calendar.getInstance();

        if (reservation != null){
            if (reservation.getPickUpDate() == null && reservation.getDropOffDate() == null){

                //Si no hay fechas asignadas en la reserva asignamos la fecha de hoy y mañana

                Date todayDate = new Date();
                calendar.setTime(todayDate);
                calendar.add(Calendar.DATE, 1);
                Date tomorrowDate = calendar.getTime();

                pickupDateTextView.setText(simpleDateFormat.format(todayDate));
                dropoffDateTextView.setText(simpleDateFormat.format(tomorrowDate));

                reservation.setPickUpDate(todayDate);
                reservation.setDropOffDate(tomorrowDate);

            } else {

                //Si la reserva ya tiene fechas las mostramos en el calendario

                String pickUpDateString = simpleDateFormat.format(reservation.getPickUpDate());
                String dropOffDateString = simpleDateFormat.format(reservation.getDropOffDate());

                pickupDateTextView.setText(pickUpDateString);
                dropoffDateTextView.setText(dropOffDateString);
            }
        }

        /*
         *
         *  SELECCIÓN DE FECHAS
         *
         * */

        pickupDateTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                calendar = Calendar.getInstance();
                calendar.setTime(reservation.getPickUpDate());

                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);

                datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int mYear, int mMonth, int mDay) {

                        String longMonth = ("00" + (mMonth+1));
                        String month = longMonth.substring(longMonth.length()-2);

                        pickupDateTextView.setText(mDay + "-" + month + "-" + mYear);

                        //Seteamos la fecha en la reserva

                        Date pickUpDate = null;
                         try {
                             pickUpDate = simpleDateFormat.parse(pickupDateTextView.getText().toString());
                         } catch (ParseException e){
                             e.printStackTrace();
                         }
                        ((MainActivity) getActivity()).getReservation().setPickUpDate(pickUpDate);

                    }
                }, year, month, day);

                datePickerDialog.show();
            }
        });

        dropoffDateTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar = Calendar.getInstance();
                calendar.setTime(reservation.getDropOffDate());

                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);

                datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int mYear, int mMonth, int mDay) {
                        String month = ("00" + (mMonth+1)).substring(1);

                        dropoffDateTextView.setText(mDay + "-" + month + "-" + mYear);

                        //Seteamos la fecha en la reserva

                        Date dropOffDate = null;

                        try {
                            dropOffDate = simpleDateFormat.parse(dropoffDateTextView.getText().toString());
                        } catch (ParseException e){
                            e.printStackTrace();
                        }

                        ((MainActivity) getActivity()).getReservation().setDropOffDate(dropOffDate);
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


        nextBtn = (Button) view.findViewById(R.id.idNextButton_Dates);

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //mostrar listado de coches
                ((MainActivity)getActivity()).setCurrentStep(MainActivity.CurrentStep.CAR);
                ((MainActivity) getActivity()).replaceFragments(CarSelectionFragment.class, R.id.idContentFragment);
            }
        });

        backBtn = (Button) view.findViewById(R.id.idBackButton_Dates);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Reseteamos las fechas
                ((MainActivity) getActivity()).getReservation().setPickUpDate(null);
                ((MainActivity) getActivity()).getReservation().setDropOffDate(null);

                //Volvemos a la pantalla anterior
                ((MainActivity)getActivity()).setCurrentStep(MainActivity.CurrentStep.LOCATION);
                ((MainActivity) getActivity()).replaceFragments(LocationSelectionFragment.class, R.id.idContentFragment);
            }
        });

        // Inflate the layout for this fragment
        return view;
    }


}
