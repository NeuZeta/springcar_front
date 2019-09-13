package com.nzsoft.springcar.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.nzsoft.springcar.R;
import com.nzsoft.springcar.activities.MyReservationsActivity;
import com.nzsoft.springcar.model.Reservation;

import java.text.SimpleDateFormat;
import java.util.List;

public class ReservationsListAdapter extends BaseAdapter {

    private LayoutInflater inflater = null;
    private List<Reservation> reservations;

    public ReservationsListAdapter (Context context, List<Reservation> reservations){
        this.reservations = reservations;
        inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return reservations.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = inflater.inflate(R.layout.row_model_reservation, null);

        TextView reservationNoText = (TextView) view.findViewById(R.id.idReservationNo);
        TextView pickUpDateText = (TextView) view.findViewById(R.id.idReservationInitialDate);
        TextView dropOffDateText = (TextView) view.findViewById(R.id.idReservationFinDate);
        TextView officeText = (TextView) view.findViewById(R.id.idReservationOffice);

        Reservation reservation = reservations.get(position);

        reservationNoText.setText(reservation.getId().toString());

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        pickUpDateText.setText(sdf.format(reservation.getPickUpDate()));
        dropOffDateText.setText(sdf.format(reservation.getDropOffDate()));

        officeText.setText(reservation.getCar().getOffice().getName());

        return view;
    }
}
