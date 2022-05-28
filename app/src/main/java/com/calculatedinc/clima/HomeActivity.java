package com.calculatedinc.clima;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

public class HomeActivity extends AppCompatActivity {

    private View mDecorView;

    // Location Fields
    private LocationRequest locationRequest;
    private LocationCallback locationCallback;
    private FusedLocationProviderClient fusedLocationProviderClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        this.mDecorView = getWindow().getDecorView();
        this.mDecorView.setOnSystemUiVisibilityChangeListener((i) -> mDecorView.setSystemUiVisibility(hideSystemBars()));

        this.setupLocation();

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) this.mDecorView.setSystemUiVisibility(hideSystemBars());
    }

    private int hideSystemBars() {
        return View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_IMMERSIVE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
    }

    private void setupLocation() {
        this.locationRequest = LocationRequest.create();
        this.locationRequest.setInterval(1000 * 30);
        this.locationRequest.setFastestInterval(1000 * 5);
        this.locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        this.fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            this.locationCallback = new LocationCallback() {
                @Override public void onLocationResult(@NonNull LocationResult locationResult) { super.onLocationResult(locationResult);getLocationData(locationResult.getLastLocation()); }
                @Override public void onLocationAvailability(@NonNull LocationAvailability locationAvailability) { super.onLocationAvailability(locationAvailability); } };
            this.fusedLocationProviderClient.requestLocationUpdates(this.locationRequest, this.locationCallback, Looper.myLooper());
        } else {
            requestPermissions(new String[] { Manifest.permission.ACCESS_FINE_LOCATION }, 44);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 44 && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            setupLocation();
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    private void getLocationData(Location location) { }

}