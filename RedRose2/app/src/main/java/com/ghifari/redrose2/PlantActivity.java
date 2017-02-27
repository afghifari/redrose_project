package com.ghifari.redrose2;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static android.R.id.list;

/**
 * Created by Lenovo PC on 02/23/2017.
 */

public class PlantActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks, LocationListener {

    GoogleApiClient mGoogleApiClient;

    Location mLastLocation;
    //private android.content.Context context;

    private static int minTime = 10000;
    private static int fastestTime = 5000;
    private static int distanceThreshold = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plant);

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API).build();

        Button b_plant = (Button) findViewById(R.id.b_plant_plant);
        b_plant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MyApplication) getApplication()).setMyPohon(new Pohon());

                SharedPreferences mPrefs = getSharedPreferences("geloman", MODE_PRIVATE);
                SharedPreferences.Editor prefsEditor = mPrefs.edit();
                Gson gson = new Gson();
                String json = gson.toJson(((MyApplication) getApplication()).getMyPohon());
                prefsEditor.putString("myPohon", json);
                prefsEditor.commit();

                Intent goalintent = new Intent(PlantActivity.this, PohonStatus.class);
                PlantActivity.this.startActivity(goalintent);
            }
        });


        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks((GoogleApiClient.ConnectionCallbacks) this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
        final Context thisObject = this;

        HerokuConnector http = new HerokuConnector();

        Thread thread = new Thread(http);
        thread.start();
        try{
            thread.join();
        }
        catch (Exception e)
        {

        }

        Spinner s = (Spinner) findViewById(R.id.dd_plant_seeds);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(thisObject,
                android.R.layout.simple_spinner_item, http.getseeds());
        s.setAdapter(adapter);
    }



    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
        Log.v("mulai", "mgoogleapi konek");
    }

    @Override
    protected void onResume(){
        super.onResume();

    }

    @Override
    protected void onPause(){
        super.onPause();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    @Override
    public void onConnected(Bundle connectionHint) {

        Log.v("on connected", "masuk");

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.v("access", "not granted");
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    200);
        }


        Log.v("access","granted");
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

        Log.v("access","granted");
        double lat=0;
        double lng=0;
        if (mLastLocation != null) {
            lat = mLastLocation.getLatitude();
            lng = mLastLocation.getLongitude();

            Log.v("lat", String.valueOf(lat));
            Log.v("lng", String.valueOf(lng));

            //mLatitudeText.setText(String.valueOf(mLastLocation.getLatitude()));
            //mLongitudeText.setText(String.valueOf(mLastLocation.getLongitude()));

        } else{

            LocationRequest mLocationRequest = new LocationRequest();
            mLocationRequest.setInterval(minTime);
            mLocationRequest.setFastestInterval(fastestTime);
            mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
            mLocationRequest.setSmallestDisplacement(distanceThreshold);
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);

            Log.v("fail", "gabisa dapet lokasi");

        }


        Geocoder gcd = new Geocoder(this, Locale.getDefault());
        List<Address> addresses = null;
        try {
            addresses = gcd.getFromLocation(lat, lng, 1);
        } catch (IOException e) {
            Log.v("fail", "ga dapet lokasi");
            e.printStackTrace();
        }
        if (addresses!= null && addresses.size() > 0)
        {
            TextView textview = (TextView) findViewById(R.id.location);
            plantingLocation = addresses.get(0).getLocality();
            textview.setText((CharSequence) addresses.get(0).getLocality());
        }
        else
        {
            // do your staff
        }


    }

    @Override
    public void onConnectionSuspended(int i) {
        mGoogleApiClient.connect();
    }


    @Override
    public void onLocationChanged(Location location) {
        mLastLocation = location;
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.i("fail", "Connection failed" + connectionResult.getErrorCode());
    }

    private String plantingLocation;
}
