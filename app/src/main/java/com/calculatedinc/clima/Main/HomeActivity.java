package com.calculatedinc.clima.Main;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.calculatedinc.clima.Manager.APIManager;
import com.calculatedinc.clima.Manager.Utils;
import com.calculatedinc.clima.R;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;

import java.util.Arrays;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    // Calgary, Alberta 51.049999, -114.066666

    // UI Fields
    private View mDecorView;
    private TextView locationNameTextView, dateTextView, temperatureTextView, climateTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        this.setupLocation();
        this.setupPlaces();

        this.mDecorView = getWindow().getDecorView();
        this.mDecorView.setOnSystemUiVisibilityChangeListener((i) -> mDecorView.setSystemUiVisibility(hideSystemBars()));

        this.locationNameTextView = findViewById(R.id.locationNameTextView);
        this.dateTextView = findViewById(R.id.dateTextView);
        this.temperatureTextView = findViewById(R.id.tempTextView);
        this.climateTextView = findViewById(R.id.climateTextView);

        this.temperatureTextView.setText(Html.fromHtml("25<sup><small>°</small></sup>"));
        this.dateTextView.setText(Utils.shared.getCurrentDate());

        APIManager.shared.getWeatherData(51.049999, -114.066666);

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) this.mDecorView.setSystemUiVisibility(hideSystemBars());
    }

    private int hideSystemBars() {
        return View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_IMMERSIVE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
    }

    private void setupPlaces() {
        Places.initialize(getApplicationContext(), "AIzaSyAJWF6RxJwkVRK49r6Tq7456b_2l-33UnM");
        ImageView searchImageView = (ImageView) findViewById(R.id.searchImageView);
        searchImageView.setFocusable(false);
        searchImageView.setOnClickListener((view) -> {
            List<Place.Field> fieldList = Arrays.asList(Place.Field.ADDRESS, Place.Field.LAT_LNG, Place.Field.NAME);
            Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY, fieldList).build(HomeActivity.this);
            startActivityForResult(intent, 100);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK) {
            if (data != null) {
                Place place = Autocomplete.getPlaceFromIntent(data);
                if (place.getLatLng() != null) {
                    getSearchedLocation(place.getLatLng());
                }
            }
        } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
            if (data != null) {
                Status status = Autocomplete.getStatusFromIntent(data);
                Log.wtf("ERROR", status.getStatusMessage());
            }
        }
    }

    private void setupLocation() {
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setInterval(1000 * 30);
        locationRequest.setFastestInterval(1000 * 5);
        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        FusedLocationProviderClient fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) fusedLocationProviderClient.getLastLocation().addOnSuccessListener(this::getLastLocation);
        else requestPermissions(new String[] { Manifest.permission.ACCESS_FINE_LOCATION }, 44);
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

    private void getLastLocation(Location location) {
        if (location != null) {
            Toast.makeText(this, "Latitude: " + location.getLatitude() +  "\nLongitude: " + location.getLongitude(), Toast.LENGTH_SHORT).show();
        }
    }

    private void getSearchedLocation(LatLng location) {
        Toast.makeText(this, "Latitude: " + location.latitude +  "\nLongitude: " + location.longitude, Toast.LENGTH_SHORT).show();
    }

}