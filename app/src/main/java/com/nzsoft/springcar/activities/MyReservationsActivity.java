package com.nzsoft.springcar.activities;

import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Switch;

import com.nzsoft.springcar.R;
import com.nzsoft.springcar.adapters.ReservationsListAdapter;
import com.nzsoft.springcar.fragments.ReservationViewFragment;
import com.nzsoft.springcar.fragments.ReservationsListFragment;
import com.nzsoft.springcar.model.Reservation;
import com.nzsoft.springcar.retrofit.RetrofitHelper;

import java.util.List;

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

    /*
     * INNER ENUM State
     *
     */

    public enum Step {
        LIST, DETAIL;
    }

}
