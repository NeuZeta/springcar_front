package com.nzsoft.springcar.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nzsoft.springcar.R;
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

    private TextView selectedOffice;
    private TextView selectedPickUpTime;
    private TextView selectedDropOffTime;

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
        introText.setText(getResources().getString(R.string.reservation_intro_text) + reservationIdLong.toString());

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

                //Esto habrá que sacarlo a otro método privado
                selectedOffice = (TextView) findViewById(R.id.idOfficeSelected);
                selectedOffice.setText(reservation.getCar().getOffice().getName());

                selectedPickUpTime = (TextView) findViewById(R.id.idPickUpDateSelected);
                selectedDropOffTime = (TextView) findViewById(R.id.idDropOffDateSelected);

                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

                selectedPickUpTime.setText(sdf.format(reservation.getPickUpDate()));
                selectedDropOffTime.setText(sdf.format(reservation.getDropOffDate()));

                //Car selection
                View carView = getLayoutInflater().inflate(R.layout.row_model_car, placeHolder, false);

                TextView carModelText = (TextView) carView.findViewById(R.id.idCarInfoModel);
                ImageView carImage = (ImageView) carView.findViewById(R.id.idCarImage);

                Car car = reservation.getCar();

                carModelText.setText(car.getModel());

                String imgURL = "https://springcarback.herokuapp.com/cars/image/" + car.getPhoto();

                Picasso.get().load(imgURL).into(carImage);

                TextView carPriceText = (TextView) carView.findViewById(R.id.idCarInfoPriceBase);
                carPriceText.setText(car.getBasePrice() + "€");

                LinearLayout CarLayout = (LinearLayout) findViewById(R.id.idCarInfoLayout);
                CarLayout.addView(carView);

                //Extras selection
                LinearLayout extrasLayout = (LinearLayout) findViewById(R.id.idExtrasInfoLayout);

                Reservation.InsuranceType insuranceType = reservation.getInsuranceType();
                switch (insuranceType){
                    case TOP:
                        View topInsuranceView = getLayoutInflater().inflate(R.layout.row_model_extra, placeHolder, false);

                        TextView topInsuranceDescription = topInsuranceView.findViewById(R.id.idExtraDescription);
                        topInsuranceDescription.setText(getResources().getString(R.string.top_insurance_text));

                        TextView topInsurancePrice = topInsuranceView.findViewById(R.id.idExtraPrice);
                        topInsurancePrice.setText(reservation.getCar().getCategory().getTopInsurancePrice() + "€");

                        extrasLayout.addView(topInsuranceView);
                        break;

                    case BASE:
                        View baseInsuranceView = getLayoutInflater().inflate(R.layout.row_model_extra, placeHolder, false);

                        TextView baseInsuranceDescription = baseInsuranceView.findViewById(R.id.idExtraDescription);
                        baseInsuranceDescription.setText(getResources().getString(R.string.base_insurance_text));

                        TextView baseInsurancePrice = baseInsuranceView.findViewById(R.id.idExtraPrice);
                        baseInsurancePrice.setText(reservation.getCar().getCategory().getBaseInsurancePrice() + "€");

                        extrasLayout.addView(baseInsuranceView);
                        break;
                }

                if (reservation.isHasTireAndGlassProtection()){
                    View tireAndGlassView = getLayoutInflater().inflate(R.layout.row_model_extra, placeHolder, false);

                    TextView tireAndGlassDescription = tireAndGlassView.findViewById(R.id.idExtraDescription);
                    tireAndGlassDescription.setText(getResources().getString(R.string.tire_glass_text));

                    TextView tireAndGlassPrice = tireAndGlassView.findViewById(R.id.idExtraPrice);
                    tireAndGlassPrice.setText(reservation.getCar().getCategory().getTireAndGlassProtectionPrice() + "€");

                    extrasLayout.addView(tireAndGlassView);
                }

                List<CommonExtra> extras = reservation.getCommonExtras();


                for (CommonExtra extra : extras){

                    View extraView = getLayoutInflater().inflate(R.layout.row_model_extra, placeHolder, false);

                    TextView extraDescription = extraView.findViewById(R.id.idExtraDescription);
                    extraDescription.setText(extra.getName());

                    TextView extraPrice = extraView.findViewById(R.id.idExtraPrice);
                    extraPrice.setText(extra.getPrice() + "€");

                    extrasLayout.addView(extraView);
                }

                TextView totalPriceView = (TextView) findViewById(R.id.idTotalPrice);

                reservation.setPrice();
                totalPriceView.setText(String.format("%.2f", reservation.getPrice()) + "€");


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
