package com.nzsoft.springcar.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.nzsoft.springcar.R;
import com.nzsoft.springcar.database.DatabaseHelper;
import com.nzsoft.springcar.fragments.BreadcrumbFragment;
import com.nzsoft.springcar.fragments.LocationSelectionFragment;
import com.nzsoft.springcar.model.Client;
import com.nzsoft.springcar.model.Office;
import com.nzsoft.springcar.model.Reservation;


public class MainActivity extends AppCompatActivity {

    private Reservation reservation;
    private Office selectedOffice;
    private String initDate;
    private String finalDate;

    private CurrentStep currentStep;
    private BreadcrumbFragment breadcrumbFragment;

    private DatabaseHelper myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initialize Reservation
        reservation = new Reservation();

        //Get userId from Shared Preferences
        Long userId = loadPreferences();

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

    public String getInitDate() {
        return initDate;
    }

    public void setInitDate(String initDate) {
        this.initDate = initDate;
    }

    public String getFinalDate() {
        return finalDate;
    }

    public void setFinalDate(String finalDate) {
        this.finalDate = finalDate;
    }

    public CurrentStep getCurrentStep() {
        return currentStep;
    }

    public void setCurrentStep(CurrentStep currentStep) {
        this.currentStep = currentStep;
    }

    public enum CurrentStep {
        LOCATION, DATES, CAR, EXTRAS, CONFIRMATION;
    }

    // METODOS PRIVADOS

    private void savePreferences(){

        SharedPreferences preferences = getSharedPreferences("credentials", Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = preferences.edit();
        editor.putLong("userId", 20L);
        editor.commit();
    }


    private Long loadPreferences () {

        SharedPreferences preferences = getSharedPreferences("credentials", Context.MODE_PRIVATE);

        Long userId = preferences.getLong("userId", 20);

        return userId;
    }


}
