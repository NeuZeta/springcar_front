package com.nzsoft.springcar.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


import com.nzsoft.springcar.R;
import com.nzsoft.springcar.fragments.CarSelectionFragment;
import com.nzsoft.springcar.fragments.ReservationsListFragment;
import com.nzsoft.springcar.model.Reservation;
import com.nzsoft.springcar.retrofit.RetrofitHelper;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MyReservationsActivity extends AppCompatActivity {

    private Step step;
    private Reservation reservation;

    private Button deleteBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_reservations);

        //Añadir titulo y logo a la barra principal
        Toolbar myReservationToolbar = (Toolbar) findViewById(R.id.idReservationToolbar);
        setSupportActionBar(myReservationToolbar);

        deleteBtn = (Button) findViewById(R.id.idDeleteBtn_myReservations);
        HideDeleteBtn();

        if (savedInstanceState == null){
            step = Step.LIST;
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.add(R.id.idDestino, new ReservationsListFragment());
            fragmentTransaction.commit();
        }
    }

    public void goBack(){

        switch (step){

            case LIST:      Intent homeIntent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(homeIntent);
                            break;

            case DETAIL:    step = Step.LIST;
                            HideDeleteBtn ();
                            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                            fragmentTransaction.replace(R.id.idDestino, new ReservationsListFragment());
                            fragmentTransaction.commit();
                            break;
        }

    }

    public void CancelReservation(){
        //Confirmation dialog
        ShowAlertDialog ();
    }

    public Step getStep() {
        return step;
    }

    public void setStep(Step step) {
        this.step = step;
    }

    public void ShowDeleteBtn (){
        deleteBtn.setVisibility(View.VISIBLE);
    }

    public void HideDeleteBtn (){
        deleteBtn.setVisibility(View.INVISIBLE);
    }

    public Reservation getReservation() {
        return reservation;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }

    public void ShowAlertDialog (){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete Reservation !");
        builder.setMessage("You are about to delete this reservation. Do you really want to proceed ?");
        builder.setCancelable(false);

        builder.setPositiveButton("DELETE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(), "You've choosen to delete selected reservation", Toast.LENGTH_SHORT).show();

                //delete reservation y después volver al listado
                DeleteReservation();

            }
        });

        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(), "You've changed your mind", Toast.LENGTH_SHORT).show();
            }
        });


        builder.show();
    }

    public void DeleteReservation(){

        Call<Void> deleteCall = RetrofitHelper.getApiRest().deleteReservationById(reservation.getId());

        deleteCall.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {

                if (!response.isSuccessful()){
                    Log.d("ddd", "Response error: " + response.message());
                    return;
                }

                Log.d("ddd", "Hemos eliminado la reserva");
                goBack();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.d("ddd", t.toString());
            }
        });
    }

    /*
     * INNER ENUM State
     *
     */

    public enum Step {
        LIST, DETAIL;
    }

}
