package com.mamac.ta7weel;

import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;

/**
 * Created by mac on 6/10/17.
 */

public class MapFragment extends SupportMapFragment implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        GoogleMap.OnInfoWindowClickListener,
        GoogleMap.OnMapLongClickListener,
        GoogleMap.OnMapClickListener,
        GoogleMap.OnMarkerClickListener {



    private GoogleApiClient mGoogleApiClient;
    private Location mCurrentLocation;

    private final int[] MAP_TYPES = { GoogleMap.MAP_TYPE_SATELLITE,
            GoogleMap.MAP_TYPE_NORMAL,
            GoogleMap.MAP_TYPE_HYBRID,
            GoogleMap.MAP_TYPE_TERRAIN,
            GoogleMap.MAP_TYPE_NONE };
    private int curMapTypeIndex = 0;
GoogleMap map;
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setHasOptionsMenu(true);

        mGoogleApiClient = new GoogleApiClient.Builder( getActivity() )
                .addConnectionCallbacks( this )
                .addOnConnectionFailedListener( this )
                .addApi( LocationServices.API )
                .build();

                getMap();

    }

    private void getMap(){


             getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(GoogleMap googleMap) {
                    map=googleMap;
                    initListeners();
                }
            });

    }

    private void getMapAddpoints(final ExchangeLocations exchangeLocations){


        getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                map=googleMap;
                initListeners();
                add_map_point(exchangeLocations);

            }
        });

    }

    private void initListeners() {
        map.setOnMarkerClickListener(this);
        map.setOnMapLongClickListener(this);
        map.setOnInfoWindowClickListener( this );
        map.setOnMapClickListener(this);
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        mCurrentLocation = LocationServices
                .FusedLocationApi
                .getLastLocation( mGoogleApiClient );

        initCamera( mCurrentLocation );
    }


    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onInfoWindowClick(Marker marker) {

    }

    @Override
    public void onMapClick(LatLng latLng) {

       // MarkerOptions options = new MarkerOptions().position( latLng );
        //options.title( getAddressFromLatLng( latLng ) );

        //options.icon( BitmapDescriptorFactory.defaultMarker() );
        //map.addMarker( options );
    }

    @Override
    public void onMapLongClick(LatLng latLng) {

    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }

    @Override
    public void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    public void onStop() {
        super.onStop();
        if( mGoogleApiClient != null && mGoogleApiClient.isConnected() ) {
            mGoogleApiClient.disconnect();
        }
    }



    private void initCamera( Location location ) {
        CameraPosition position = CameraPosition.builder()
                .target( new LatLng( 29.365359, 47.991143 ) )
                .zoom( 12f )
                .bearing( 0.0f )
                .tilt( 0.0f )
                .build();

        map.animateCamera( CameraUpdateFactory
                .newCameraPosition( position ), null );

        map.setMapType( MAP_TYPES[curMapTypeIndex] );
        map.setTrafficEnabled( false );
        map.setMyLocationEnabled( false );
        map.getUiSettings().setZoomControlsEnabled( true );
    }


    private String getAddressFromLatLng( LatLng latLng ) {
        Geocoder geocoder = new Geocoder( getActivity() );

        String address = "";
        try {
            address = geocoder
                    .getFromLocation( latLng.latitude, latLng.longitude, 1 )
                    .get( 0 ).getAddressLine( 0 );
        } catch (IOException e ) {
        }

        return address;
    }

    public void clear_map_points(){
        map.clear();
    }

    public void add_map_point(ExchangeLocations exchangeLocations){

        if(map==null){
            getMapAddpoints(exchangeLocations);
        }else{
//            CameraPosition position = CameraPosition.builder()
//                    .target( new LatLng( Float.parseFloat(exchangeLocations.latitude), Float.parseFloat(exchangeLocations.longitude )) )
//                    .zoom( 14f )
//                    .bearing( 0.0f )
//                    .tilt( 0.0f )
//                    .build();
//
//            map.animateCamera( CameraUpdateFactory
//                    .newCameraPosition( position ), null );

            MarkerOptions options = new MarkerOptions().position(new LatLng( Float.parseFloat(exchangeLocations.latitude), Float.parseFloat(exchangeLocations.longitude )));
            options.title( exchangeLocations.title );

            options.icon( BitmapDescriptorFactory.defaultMarker() );
            map.addMarker( options );

        }


    }

}
