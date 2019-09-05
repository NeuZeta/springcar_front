package com.nzsoft.springcar.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.nzsoft.springcar.R;

public class SuccessActivity extends AppCompatActivity {

    private TextView reservationId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success);

        reservationId = (TextView) findViewById(R.id.idReservationId);

        //Obtenemos el intent que nos llega
        Intent intent = getIntent();

        //Los datos "extra" llegan a traves de un Bundle
        Bundle bundle = intent.getExtras();

        Long reservationIdLong = bundle.getLong("ReservationID");

        reservationId.setText(reservationIdLong.toString());


    }
}
