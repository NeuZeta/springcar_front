package com.nzsoft.springcar.activities;

import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nzsoft.springcar.R;
import com.nzsoft.springcar.fragments.ReservationViewFragment;
import com.nzsoft.springcar.model.Car;
import com.nzsoft.springcar.model.CommonExtra;
import com.nzsoft.springcar.model.Reservation;
import com.nzsoft.springcar.retrofit.RetrofitHelper;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SuccessActivity extends AppCompatActivity {

    private TextView introText;
    private TextView reservationNumberText;
    private Button homeBtn;
    private Reservation reservation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success);

        //reservationId = (TextView) findViewById(R.id.idReservationId);

        final FrameLayout placeHolder = (FrameLayout) findViewById(R.id.idReservationDestination);
        getLayoutInflater().inflate(R.layout.fragment_reservation, placeHolder);

        //Obtenemos el intent que nos llega
        Intent intent = getIntent();

        //Los datos "extra" llegan a traves de un Bundle
        Bundle bundle = intent.getExtras();

        Long reservationIdLong = bundle.getLong("ReservationID");

        introText = (TextView) findViewById(R.id.idIntroText);
        introText.setVisibility(View.GONE);
        reservationNumberText = (TextView) findViewById(R.id.idReservationNumber);
        reservationNumberText.setVisibility(View.VISIBLE);

        //Recuperamos la reserva del servidor

        Call<Reservation> call = RetrofitHelper.getApiRest().getReservationById(reservationIdLong);

        call.enqueue(new Callback<Reservation>() {
            @Override
            public void onResponse(Call<Reservation> call, Response<Reservation> response) {
                if (!response.isSuccessful()){
                    Log.d("___", "Response error: " + response.toString());
                    return;
                }

                reservation = response.body();

                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                ReservationViewFragment reservationViewFragment = new ReservationViewFragment();
                reservationViewFragment.setReservation(reservation);
                reservationViewFragment.setHasId(true);
                fragmentTransaction.add(R.id.idReservationDestination, reservationViewFragment);
                fragmentTransaction.commit();

            }

            @Override
            public void onFailure(Call<Reservation> call, Throwable t) {
                Log.d("___", "Throwable error: " + t.getCause().toString());
            }
        });

        homeBtn = (Button) findViewById(R.id.idGoToHomeBtn);
        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentHome= new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intentHome);
            }
        });

    }
}
