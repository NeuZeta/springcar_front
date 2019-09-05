package com.nzsoft.springcar.fragments;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.nzsoft.springcar.R;
import com.nzsoft.springcar.activities.MainActivity;
import com.nzsoft.springcar.model.Office;
import com.nzsoft.springcar.model.Reservation;
import com.nzsoft.springcar.retrofit.RetrofitHelper;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class LocationSelectionFragment extends Fragment {

    private Button nextBtn;
    private Spinner officeSpinner;
    private List<Office> offices;

    public LocationSelectionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_location_selection, container, false);

        /*
         *
         *  GOOGLE MAPS
         *
         * */

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.idMapFragment);

        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {

                googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

                CameraPosition googlePlex = CameraPosition.builder()
                        .target(new LatLng(41.357436,2.120132))
                        .zoom(10)
                        .bearing(0)
                        .tilt(0)
                        .build();

                googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(googlePlex));

                googleMap.addMarker(new MarkerOptions()
                        .position(new LatLng(41.381649, 2.151232))
                        .title("Eixample")
                        .icon(bitmapDescriptorFromVector(getActivity(),R.drawable.maps_icon)));

                googleMap.addMarker(new MarkerOptions()
                        .position(new LatLng(41.392387, 2.162353))
                        .title("Rambla de Catalunya")
                        .icon(bitmapDescriptorFromVector(getActivity(),R.drawable.maps_icon)));

                googleMap.addMarker(new MarkerOptions()
                        .position(new LatLng(41.374428, 2.173086))
                        .title("Paral.lel / Port")
                        .icon(bitmapDescriptorFromVector(getActivity(),R.drawable.maps_icon)));

                googleMap.addMarker(new MarkerOptions()
                        .position(new LatLng(41.289402, 2.074430))
                        .title("Airport")
                        .icon(bitmapDescriptorFromVector(getActivity(),R.drawable.maps_icon)));

            }
        });


        /*
         *
         *  SPINNER DE SELECCIÓN DE OFICINA
         *
         * */

        officeSpinner = (Spinner) view.findViewById(R.id.idOfficeSelection);

        Call<List<Office>> call = RetrofitHelper.getApiRest().getAllOffices();

        call.enqueue(new Callback<List<Office>>() {
            @Override
            public void onResponse(Call<List<Office>> call, Response<List<Office>> response) {
                if (!response.isSuccessful()){
                    Log.d("****", "Response error: " + response.message());
                    return;
                }
                offices = response.body();

                List<String> officesNameList = new ArrayList<>();

                for (Office office : offices){
                    officesNameList.add(office.getName());
                }

                ArrayAdapter adapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_dropdown_item, officesNameList);
                officeSpinner.setAdapter(adapter);

                //If the reservation already has an Office selected:
                Office selectedOffice = ((MainActivity) getActivity()).getSelectedOffice();
                if ( selectedOffice != null){
                    for (int i = 0; i < offices.size(); i++){
                        if (offices.get(i).getId() == selectedOffice.getId()){
                            officeSpinner.setSelection(i);
                            return;
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Office>> call, Throwable t) {
                Log.d("***", t.getCause().toString());
            }
        });

        nextBtn = (Button) view.findViewById(R.id.idNextButton_Location);

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Recoger el valor del spinner de oficinas para recuperar la officina seleccionada
                Office office = offices.get(officeSpinner.getSelectedItemPosition());

                //Guardamos la oficina ya que se usa sólo para filtrar los coches

                ((MainActivity) getActivity()).setSelectedOffice(office);

                //mostrar listado de coches
                ((MainActivity)getActivity()).setCurrentStep(MainActivity.CurrentStep.DATES);
                ((MainActivity) getActivity()).replaceFragments(DatesSelectionFragment.class, R.id.idContentFragment);
            }
        });

        return view;
    }

    private BitmapDescriptor bitmapDescriptorFromVector(Context context, int vectorResId) {
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorResId);
        vectorDrawable.setBounds(0, 0, vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight());
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }

}
