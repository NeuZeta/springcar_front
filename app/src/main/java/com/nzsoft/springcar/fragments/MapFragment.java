package com.nzsoft.springcar.fragments;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

/**
 * A simple {@link Fragment} subclass.
 */
public class MapFragment extends Fragment {


    public MapFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_map, container, false);

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.idMapFragment);

        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {

                googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

                CameraPosition googlePlex = CameraPosition.builder()
                        .target(new LatLng(41.360930,2.125621))
                        .zoom(11)
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

        return rootView;
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
