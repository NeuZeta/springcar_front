package com.nzsoft.springcar.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;

import com.nzsoft.springcar.R;
import com.nzsoft.springcar.model.Client;
import com.nzsoft.springcar.retrofit.RetrofitHelper;
import com.nzsoft.springcar.utils.Utils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Comprobamos si el usuario está registrado y si no lo está, abrimos la pagina de registro
        Long userId = Utils.loadPreferences(this);

        if (userId == 0){
            Intent intentMyAccount = new Intent(getApplicationContext(), AccountActivity.class);
            startActivity(intentMyAccount);
        }

        //Añadir titulo y logo a la barra principal
        Toolbar myReservationToolbar = (Toolbar) findViewById(R.id.idReservationToolbar);
        setSupportActionBar(myReservationToolbar);

        //Coger la referencia al layout que hace de botón y ponerle el listener

        //Botón AÑADIR RESERVA
        LinearLayout newReservationBtn = (LinearLayout) findViewById(R.id.idNewReservationLayout);

        newReservationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentNewReservation = new Intent(v.getContext(), NewReservationActivity.class);
                startActivity(intentNewReservation);
            }
        });

        //Botón MY ACCOUNT
        LinearLayout myAccount = (LinearLayout) findViewById(R.id.idMyAccountLayout);

        myAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentMyAccount = new Intent(v.getContext(), AccountActivity.class);
                startActivity(intentMyAccount);
            }
        });

        //Botón MY RESERVATIONS
        LinearLayout myReservationsBtn = (LinearLayout) findViewById(R.id.idMyReservationsLayout);

        myReservationsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentMyReservations = new Intent(v.getContext(), MyReservationsActivity.class);
                startActivity(intentMyReservations);
            }
        });

    }

}
