package com.nzsoft.springcar.activities;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.nzsoft.springcar.R;
import com.nzsoft.springcar.fragments.DatesSelectionFragment;

public class DatesSelectionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dates_selection);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.idDatesSelectionFragment, new DatesSelectionFragment());
        fragmentTransaction.commit();

    }
}
