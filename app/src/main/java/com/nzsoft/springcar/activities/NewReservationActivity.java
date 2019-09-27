package com.nzsoft.springcar.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.nzsoft.springcar.R;
import com.nzsoft.springcar.fragments.newreservation.BreadcrumbFragment;
import com.nzsoft.springcar.fragments.newreservation.CarSelectionFragment;
import com.nzsoft.springcar.fragments.newreservation.ConfirmationFragment;
import com.nzsoft.springcar.fragments.newreservation.DatesSelectionFragment;
import com.nzsoft.springcar.fragments.newreservation.ExtrasSelectionFragment;
import com.nzsoft.springcar.fragments.newreservation.LocationSelectionFragment;
import com.nzsoft.springcar.model.Client;
import com.nzsoft.springcar.model.CommonExtra;
import com.nzsoft.springcar.model.Office;
import com.nzsoft.springcar.model.Reservation;
import com.nzsoft.springcar.retrofit.RetrofitHelper;
import com.nzsoft.springcar.utils.Utils;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class NewReservationActivity extends AppCompatActivity {

    private Reservation reservation;
    private Office selectedOffice;

    private CurrentStep currentStep;
    private BreadcrumbFragment breadcrumbFragment;

    private Button nextBtn;
    private Button backBtn;

    private View loadingPanel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);

        //Añadir titulo y logo a la barra principal
        Toolbar myReservationToolbar = (Toolbar) findViewById(R.id.idReservationToolbar);
        setSupportActionBar(myReservationToolbar);

        nextBtn = (Button) findViewById(R.id.idNextButton_NewReservation);
        backBtn = (Button) findViewById(R.id.idBackButton_NewReservation);

        loadingPanel = findViewById(R.id.loadingPanel_Reservation);
        loadingPanel.setVisibility(View.GONE);

        //Initialize Reservation
        reservation = new Reservation();

        //Get userId from Shared Preferences
        Long userId = Utils.loadPreferences(this);

        //Create Client
        Client client = new Client();
        client.setId(userId);

        reservation.setClient(client);

        breadcrumbFragment = (BreadcrumbFragment) getSupportFragmentManager().findFragmentById(R.id.idBreadCrumbFragment);

        if (savedInstanceState == null){
            setCurrentStep(CurrentStep.LOCATION);
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.add(R.id.idContentFragment, new LocationSelectionFragment());
            fragmentTransaction.commit();
        }

    }

    public void replaceFragments(Class fragmentClass, int view) {
        Fragment fragment = null;
        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Insert the fragment by replacing any existing fragment
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(view, fragment);
        fragmentTransaction.commit();

        //Update Breadcrumb fragment
        breadcrumbFragment.changeStep(currentStep);

    }

    public Reservation getReservation() {
        return reservation;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }

    public Office getSelectedOffice() {
        return selectedOffice;
    }

    public void setSelectedOffice(Office selectedOffice) {
        this.selectedOffice = selectedOffice;
    }

    public View getLoadingPanel () {
        return this.loadingPanel;
    }

    /*
    public void setLoadingPanel (int visibility){
        this.loadingPanel.setVisibility(visibility);
    }

    public CurrentStep getCurrentStep() {
        return currentStep;
    }
    */

    public void setCurrentStep(CurrentStep currentStep) {
        this.currentStep = currentStep;
    }

    public void goBack (){

        switch (currentStep) {
            case LOCATION:          Intent homeIntent = new Intent(this, MainActivity.class);
                                    startActivity(homeIntent);
                                    break;

            case DATES:             //Reset dates selection
                                    reservation.setPickUpDate(null);
                                    reservation.setDropOffDate(null);
                                    currentStep = CurrentStep.LOCATION;
                                    replaceFragments(LocationSelectionFragment.class, R.id.idContentFragment);
                                    break;

            case CAR:               currentStep = CurrentStep.DATES;
                                    reservation.setCar(null);
                                    CarSelectionFragment.selectedCar = -1;
                                    replaceFragments(DatesSelectionFragment.class, R.id.idContentFragment);
                                    break;

            case EXTRAS:            currentStep = CurrentStep.CAR;
                                    replaceFragments(CarSelectionFragment.class, R.id.idContentFragment);
                                    break;

            case CONFIRMATION:      currentStep = CurrentStep.EXTRAS;
                                    replaceFragments(ExtrasSelectionFragment.class, R.id.idContentFragment);
                                    break;
        }

    }

    public void nextStep () {
        switch (currentStep) {
            case LOCATION:          currentStep = CurrentStep.DATES;
                                    replaceFragments(DatesSelectionFragment.class, R.id.idContentFragment);
                                    break;

            case DATES:             currentStep = CurrentStep.CAR;
                                    replaceFragments(CarSelectionFragment.class, R.id.idContentFragment);
                                    break;

            case CAR:               currentStep = CurrentStep.EXTRAS;
                                    replaceFragments(ExtrasSelectionFragment.class, R.id.idContentFragment);
                                    break;

            case EXTRAS:            setInsuranceType ();
                                    setExtrasSelected ();
                                    currentStep = CurrentStep.CONFIRMATION;
                                    replaceFragments(ConfirmationFragment.class, R.id.idContentFragment);
                                    break;

            case CONFIRMATION:      createReservation();
                                    break;
        }
    }

    public enum CurrentStep {
        LOCATION, DATES, CAR, EXTRAS, CONFIRMATION;
    }

    public void deactivateContinueBtn (){
        nextBtn.getBackground().setColorFilter(nextBtn.getContext().getResources().getColor(R.color.colorPaleGray), PorterDuff.Mode.SRC);
        nextBtn.setEnabled(false);
    }

    public void activateContinueBtn () {
        nextBtn.getBackground().setColorFilter(nextBtn.getContext().getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC);
        nextBtn.setEnabled(true);
    }

    // METODOS PRIVADOS

    private void getFragmentInstance (){
        ExtrasSelectionFragment fragm = (ExtrasSelectionFragment)getSupportFragmentManager().findFragmentById(R.id.idContentFragment);

    }

    private void setInsuranceType () {
        ExtrasSelectionFragment fragm = (ExtrasSelectionFragment)getSupportFragmentManager().findFragmentById(R.id.idContentFragment);
        int insuranceTypeID = fragm.getInsuranceTypeId();

        switch (insuranceTypeID){
            case (R.id.idBasicInsurance):   reservation.setInsuranceType(Reservation.InsuranceType.BASE);
                                            break;

            case (R.id.idTopInsurance):     reservation.setInsuranceType(Reservation.InsuranceType.TOP);
                                            break;
        }
    }

    private void setExtrasSelected () {
        ExtrasSelectionFragment fragm = (ExtrasSelectionFragment)getSupportFragmentManager().findFragmentById(R.id.idContentFragment);
        List<CommonExtra> extras = fragm.getExtrasSelected();
        reservation.setCommonExtras(extras);
    }

    private void createReservation(){

        reservation.setReservationDate(new Date());

        Call<Reservation> call = RetrofitHelper.getApiRest().createReservation(reservation);

        loadingPanel.setVisibility(View.VISIBLE);

        call.enqueue(new Callback<Reservation>() {
            @Override
            public void onResponse(Call<Reservation> call, Response<Reservation> response) {
                Log.d("***", "Response: " + response.toString());

                CarSelectionFragment.selectedCar = -1;

                loadingPanel.setVisibility(View.GONE);

                Intent intentSuccess = new Intent(getApplicationContext(), SuccessActivity.class);
                intentSuccess.putExtra("ReservationID", response.body().getId());
                startActivity(intentSuccess);
            }

            @Override
            public void onFailure(Call<Reservation> call, Throwable t) {
                if (t instanceof SocketTimeoutException)
                {
                    call.clone().enqueue(this);
                }
                else if (t instanceof IOException)
                {
                    call.clone().enqueue(this);
                }
                else
                {
                    Log.d("___", t.toString());
                }
            }
        });

    }

}
