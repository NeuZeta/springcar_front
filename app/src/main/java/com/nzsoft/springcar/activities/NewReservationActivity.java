package com.nzsoft.springcar.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.nzsoft.springcar.R;
import com.nzsoft.springcar.fragments.newreservation.BreadcrumbFragment;
import com.nzsoft.springcar.fragments.newreservation.CarSelectionFragment;
import com.nzsoft.springcar.fragments.newreservation.DatesSelectionFragment;
import com.nzsoft.springcar.fragments.newreservation.ExtrasSelectionFragment;
import com.nzsoft.springcar.fragments.newreservation.LocationSelectionFragment;
import com.nzsoft.springcar.model.Client;
import com.nzsoft.springcar.model.Office;
import com.nzsoft.springcar.model.Reservation;
import com.nzsoft.springcar.utils.Utils;


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

            case EXTRAS:
                break;
            case CONFIRMATION:
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

            case EXTRAS:
                break;
            case CONFIRMATION:
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



}
