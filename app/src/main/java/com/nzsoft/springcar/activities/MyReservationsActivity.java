package com.nzsoft.springcar.activities;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.nzsoft.springcar.R;
import com.nzsoft.springcar.adapters.ReservationsListAdapter;
import com.nzsoft.springcar.fragments.ReservationViewFragment;
import com.nzsoft.springcar.model.Reservation;
import com.nzsoft.springcar.retrofit.RetrofitHelper;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyReservationsActivity extends AppCompatActivity {

    private Button backToHomeBtn;
    private Button backToReservationsBtn;
    private Button deleteBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_reservations);

        //Añadir titulo y logo a la barra principal
        Toolbar myReservationToolbar = (Toolbar) findViewById(R.id.idReservationToolbar);
        setSupportActionBar(myReservationToolbar);

        Call<List<Reservation>> call = RetrofitHelper.getApiRest().getAllReservations();

        call.enqueue(new Callback<List<Reservation>>() {
            @Override
            public void onResponse(Call<List<Reservation>> call, Response<List<Reservation>> response) {

                if (!response.isSuccessful()){
                    Log.d("****", "Response error: " + response.message());
                    return;
                }

                final List<Reservation> reservations = response.body();

                final ReservationsListAdapter reservationsListAdapter = new ReservationsListAdapter(getApplicationContext(), reservations);

                final ListView reservationList = (ListView) findViewById(R.id.idReservationsList);
                reservationList.setAdapter(reservationsListAdapter);

                reservationList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        ReservationViewFragment reservationViewFragment = new ReservationViewFragment();
                        reservationViewFragment.setReservation(reservations.get(position));

                        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.idDestino, reservationViewFragment);
                        fragmentTransaction.commit();

                    }
                });

            }

            @Override
            public void onFailure(Call<List<Reservation>> call, Throwable t) {

            }
        });

    }
}
