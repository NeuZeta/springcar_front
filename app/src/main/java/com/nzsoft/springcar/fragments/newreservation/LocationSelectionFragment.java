package com.nzsoft.springcar.fragments.newreservation;


import android.content.Context;
import android.content.Intent;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.nzsoft.springcar.R;
import com.nzsoft.springcar.activities.MainActivity;
import com.nzsoft.springcar.activities.NewReservationActivity;
import com.nzsoft.springcar.model.Office;
import com.nzsoft.springcar.retrofit.RetrofitHelper;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class LocationSelectionFragment extends Fragment {

    private Button nextBtn;
    private Button backBtn;
    private Spinner officeSpinner;
    private List<Office> offices;
    private Office selectedOffice;
    private List<Marker> markers;
    private GoogleMap googleMap;

    public LocationSelectionFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_location_selection, container, false);
        markers = new ArrayList<>();

        /*
         *
         *  GOOGLE MAPS
         *
         * */

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.idMapFragment);

        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(final GoogleMap googleMap) {

                setGoogleMap(googleMap);

                googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                UiSettings uiSettings = googleMap.getUiSettings();
                uiSettings.setZoomControlsEnabled(true);

                CameraPosition googlePlex = CameraPosition.builder()
                        .target(new LatLng(41.357436,2.120132))
                        .zoom(11)
                        .bearing(0)
                        .tilt(0)
                        .build();

                googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(googlePlex));

                Marker eixampleMarker = googleMap.addMarker(new MarkerOptions()
                        .position(new LatLng(41.381649, 2.151232))
                        .title("Eixample")
                        .icon(bitmapDescriptorFromVector(getActivity(),R.drawable.maps_icon)));
                markers.add(eixampleMarker);

                Marker ramblaMarker = googleMap.addMarker(new MarkerOptions()
                        .position(new LatLng(41.392387, 2.162353))
                        .title("Rambla de Catalunya")
                        .icon(bitmapDescriptorFromVector(getActivity(),R.drawable.maps_icon)));
                markers.add(ramblaMarker);

                Marker portMarker = googleMap.addMarker(new MarkerOptions()
                        .position(new LatLng(41.374428, 2.173086))
                        .title("Paral.lel / Port")
                        .icon(bitmapDescriptorFromVector(getActivity(),R.drawable.maps_icon)));
                markers.add(portMarker);

                Marker airportMarker = googleMap.addMarker(new MarkerOptions()
                        .position(new LatLng(41.289402, 2.074430))
                        .title("Airport")
                        .icon(bitmapDescriptorFromVector(getActivity(),R.drawable.maps_icon)));
                markers.add(airportMarker);

                googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener(){

                    @Override
                    public boolean onMarkerClick(Marker marker) {

                        Log.d("***", "Marker pulsado");
                        //Centrar el mapa en el marcador seleccionado
                        googleMap.moveCamera(CameraUpdateFactory.newLatLng(marker.getPosition()));

                        //Setear Oficina en spinner y en la reserva
                        Office office = offices.get(markers.indexOf(marker));
                        officeSpinner.setSelection(markers.indexOf(marker));
                        selectedOffice = office;

                        //Poner el icono standard a todos los markers
                        for (Marker markerl : markers){
                            markerl.setIcon(bitmapDescriptorFromVector(getContext(), R.drawable.maps_icon));
                        }

                        //Cambiar el tono del marker seleccionado
                        marker.setIcon(bitmapDescriptorFromVector(getContext(), R.drawable.maps_icon_selected));

                        return false;
                    }
                });
            }
        });

        /*
         *
         *  SPINNER DE SELECCIÓN DE OFICINA
         *
         * */

        officeSpinner = (Spinner) view.findViewById(R.id.idOfficeSelection);

        Call<List<Office>> call = RetrofitHelper.getApiRest().getAllOffices();

        ((NewReservationActivity)getActivity()).getLoadingPanel().setVisibility(View.VISIBLE);

        call.enqueue(new Callback<List<Office>>() {
            @Override
            public void onResponse(Call<List<Office>> call, Response<List<Office>> response) {

                if (!response.isSuccessful()){
                    Log.d("****", "Response error: " + response.message());
                    return;
                }

                ((NewReservationActivity)getActivity()).getLoadingPanel().setVisibility(View.GONE);

                offices = response.body();

                List<String> officesNameList = new ArrayList<>();

                for (Office office : offices){
                    officesNameList.add(office.getName());
                }

                ArrayAdapter adapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_dropdown_item, officesNameList);
                officeSpinner.setAdapter(adapter);

                //If the reservation already has an Office selected:
                selectedOffice = ((NewReservationActivity) getActivity()).getSelectedOffice();
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

        officeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {

                //Centrar el mapa en el marcador seleccionado
                Marker selectedMarker = markers.get(position);
                googleMap.moveCamera(CameraUpdateFactory.newLatLng(selectedMarker.getPosition()));

                //Cambiar el icono del marker seleccionado y resetear el resto
                for (Marker markerl : markers){
                    markerl.setIcon(bitmapDescriptorFromVector(getContext(), R.drawable.maps_icon));
                }

                selectedMarker.setIcon(bitmapDescriptorFromVector(getContext(), R.drawable.maps_icon_selected));

                Office office = offices.get(position);
                ((NewReservationActivity) getActivity()).setSelectedOffice(office);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
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

    public GoogleMap getGoogleMap() {
        return googleMap;
    }

    public void setGoogleMap(GoogleMap googleMap) {
        this.googleMap = googleMap;
    }

}
