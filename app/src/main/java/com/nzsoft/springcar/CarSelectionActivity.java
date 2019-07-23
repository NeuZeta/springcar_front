package com.nzsoft.springcar;

import android.support.constraint.ConstraintLayout;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.nzsoft.springcar.fragments.DatesSelectionFragment;

public class CarSelectionActivity extends AppCompatActivity {
    private Button btn1;
    private ListView carListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_selection);

        btn1 = (Button) findViewById(R.id.idOpcion1Btn);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConstraintLayout opcion1Panel = (ConstraintLayout) findViewById(R.id.idOpcion1Panel);

                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.idOpcion1Panel, new DatesSelectionFragment());
                fragmentTransaction.commit();

                if (opcion1Panel.getVisibility() == View.VISIBLE) {
                    opcion1Panel.setVisibility(View.GONE);
                } else {
                    opcion1Panel.setVisibility(View.VISIBLE);
                }
            }
        });


        carListView = (ListView) findViewById(R.id.idCarsList);




    }
}
