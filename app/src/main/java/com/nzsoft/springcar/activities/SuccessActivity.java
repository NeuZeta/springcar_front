package com.nzsoft.springcar.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.nzsoft.springcar.R;
import com.nzsoft.springcar.model.Reservation;
import com.nzsoft.springcar.retrofit.RetrofitHelper;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SuccessActivity extends AppCompatActivity {

    private TextView reservationId;

    private TextView selectedOffice;
    private TextView selectedPickUpTime;
    private TextView selectedDropOffTime;

    private Reservation reservation;

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

        //reservationId.setText(reservationIdLong.toString());

        Call<Reservation> call = RetrofitHelper.getApiRest().getReservationById(reservationIdLong);

        call.enqueue(new Callback<Reservation>() {
            @Override
            public void onResponse(Call<Reservation> call, Response<Reservation> response) {
                if (!response.isSuccessful()){
                    Log.d("___", "Response error: " + response.message());
                    return;
                }

                reservation = response.body();
                reservationId.setText(reservation.getId().toString());
            }

            @Override
            public void onFailure(Call<Reservation> call, Throwable t) {
                Log.d("___", "Response error: " + t.getCause().toString());
            }
        });




    }
}
