package com.example.cse5236.mobilebuddy;


import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.google.android.gms.location.LocationServices;

public class CollectFood extends FragmentActivity implements OnMapReadyCallback {


    private static final int MY_PERMISSION_ACCESS_COARSE_LOCATION = 11;
    private final int MY_PERMISSION_ACCESS_FINE_LOCATION = 1;

    Button exploreButton;
    Button foodButton;
    TextView exploreText;
    Marker startMark;
    boolean setMark = false;
    Marker foodMark;
    boolean foodMarkSet = false;
    LocationManager locationManager;
    String locServ;
    double startLat;
    double startLong;

    Context context;
    Activity active;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION}, MY_PERMISSION_ACCESS_COARSE_LOCATION);
        }
        locationManager = (LocationManager)getSystemService(this.LOCATION_SERVICE);
        locServ = this.LOCATION_SERVICE;

        super.onCreate(savedInstanceState);
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
            setContentView(R.layout.activity_collect_food_horiz);
        else
            setContentView(R.layout.activity_collect_food);


        exploreButton = findViewById(R.id.ExploreButton);
        foodButton = findViewById(R.id.FoodButton);
        exploreText = findViewById(R.id.ExploreText);
        this.context = this;
        this.active = this;


        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


    }

    @Override
    public void onMapReady(GoogleMap map) {

        final GoogleMap mapObj = map;
        exploreButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {


                if (ContextCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(active, new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION}, MY_PERMISSION_ACCESS_COARSE_LOCATION);
                }
                if (ContextCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(active, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSION_ACCESS_FINE_LOCATION);
                }
                //ActivityCompat.requestPermissions(active, new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION}, MY_PERMISSION_ACCESS_COARSE_LOCATION);
                //ActivityCompat.requestPermissions(active, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSION_ACCESS_FINE_LOCATION);
                try{
                    locationManager = (LocationManager)getSystemService(locServ);
                    Looper looper = null;
                    locationManager.requestSingleUpdate(locationManager.GPS_PROVIDER, locationListenerGPS, looper);
                    Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                    startLong = location.getLongitude();
                    startLat = location.getLatitude();
                    startMark = mapObj.addMarker(new MarkerOptions().position(new LatLng(startLat, startLong)).title("Start"));
                    setMark = true;
                    exploreText.setText("Walk 100m away and collect food");
                }
                catch (Exception e){
                    Log.e("GPS Error", "mobilebuddy "+ e);
                    exploreText.setText("There was an error getting GPS location");
                }

            }
        });

        foodButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(active, new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION}, MY_PERMISSION_ACCESS_COARSE_LOCATION);
                }
                if (ContextCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(active, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSION_ACCESS_FINE_LOCATION);
                }
                if(foodMarkSet){
                    foodMark.remove();
                }

                try{
                    locationManager = (LocationManager)getSystemService(locServ);
                    Looper looper = null;
                    locationManager.requestSingleUpdate(locationManager.GPS_PROVIDER, locationListenerGPS, looper);
                    Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                    double longitude = location.getLongitude();
                    double latitude = location.getLatitude();
                    foodMark = mapObj.addMarker(new MarkerOptions().position(new LatLng(latitude, longitude)).title("Start"));
                    foodMarkSet = true;

                    double dist = farAway(startLat,startLong,latitude,longitude);
                    if(!setMark){

                    }
                    else if(dist < 100){
                        exploreText.setText("You need to be 100m away. You're only " + dist + "m");
                    }
                    else{
                        exploreText.setText("Good Job!");
                        foodMark.remove();
                        startMark.remove();
                        foodMarkSet = false;
                        setMark = false;
                        int currentHungerStat = HomeScreenActivity.getStat(active, "hunger" );
                        HomeScreenActivity.setStat(active, "hunger", currentHungerStat - 20);
                    }
                }
                catch (Exception e){
                    Log.e("GPS Error", "mobilebuddy "+ e);
                    exploreText.setText("There was an error getting GPS location");
                }



            }
        });

    }
    LocationListener locationListenerGPS = new LocationListener() {
        @Override
        public void onLocationChanged(android.location.Location location) {
            double latitude=location.getLatitude();
            double longitude=location.getLongitude();

        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };

    @Override
    public void onStart(){
        super.onStart();


    }


    private double farAway(double lat1, double long1, double lat2, double long2){
        double R = 6378.137; // Radius of earth in KM
        double dLat = lat2 * Math.PI / 180 - lat1 * Math.PI / 180;
        double dLon = long2 * Math.PI / 180 - long1 * Math.PI / 180;
        double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
                Math.cos(lat1 * Math.PI / 180) * Math.cos(lat2 * Math.PI / 180) *
                        Math.sin(dLon/2) * Math.sin(dLon/2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        double d = R * c;
        return d * 1000; // meters
    }

    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }
            // other 'case' lines to check for other
            // permissions this app might request
        }
    }


        }
