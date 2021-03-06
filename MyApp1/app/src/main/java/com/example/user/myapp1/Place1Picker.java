package com.example.user.myapp1;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


/* Use this to get SHA1 Key using CMD in Windows
* keytool -list -v -keystore "%USERPROFILE%\.android\debug.keystore" -alias androiddebugkey -storepass android -keypass android
*/

public class Place1Picker extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    private GoogleApiClient mGoogleApiClient;
    private int PLACE_PICKER_REQUEST = 111;
    private TextView tvPlaceDetails;
    private FloatingActionButton fabPickPlace;
    // Write a message to the database
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("message");




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_picker);

        initViews();

        mGoogleApiClient = new GoogleApiClient
                .Builder(this)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .enableAutoManage(this, this)
                .build();


        fabPickPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
                try {
                    startActivityForResult(builder.build(Place1Picker.this), PLACE_PICKER_REQUEST);
                    Log.d("hammad","ahad");
                } catch (GooglePlayServicesRepairableException | GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void initViews() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
       // setSupportActionBar(toolbar);
        fabPickPlace = (FloatingActionButton) findViewById(R.id.fab);
        tvPlaceDetails = (TextView) findViewById(R.id.placeDetails);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Snackbar.make(fabPickPlace, connectionResult.getErrorMessage() + "", Snackbar.LENGTH_LONG).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(data, this);
                StringBuilder stBuilder = new StringBuilder();
                String placename = String.format("%s", place.getName());
                String latitude = String.valueOf(place.getLatLng().latitude);
                String longitude = String.valueOf(place.getLatLng().longitude);
                String address = String.format("%s", place.getAddress());
                stBuilder.append("Name: ");
                stBuilder.append(placename);
                stBuilder.append("\n");

                stBuilder.append("Latitude: ");
                stBuilder.append(latitude);
                myRef=database.getReference("users");
                myRef.child("latitude").push().setValue(latitude.toString());

                stBuilder.append("\n");
                stBuilder.append("Longitude: ");
                stBuilder.append(longitude);
                myRef=database.getReference("users");
                myRef.child("longitude").push().setValue(longitude.toString());

                stBuilder.append("\n");
                stBuilder.append("Address: ");
                stBuilder.append(address);
                myRef=database.getReference("users");
                myRef.child("Address").push().setValue(address.toString());

                tvPlaceDetails.setText(stBuilder.toString());
            }
        }
    }

}
