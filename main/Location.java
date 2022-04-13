package com.example.trafficapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.media.audiofx.Equalizer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.HashMap;
import java.util.Map;

public class Location extends AppCompatActivity {
    Button btn_getLocation;
    String longitude , latitude , traffic_id ;
    FusedLocationProviderClient fusedLocationProviderClient;
    String URL_POST = "http://192.168.43.56/Traffic/currentlocation.php";
    TextView attendance;
    String atttext = "Attended";

    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        btn_getLocation = findViewById(R.id.btn_getLocation);
        attendance = findViewById(R.id.attendanceText);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(Location.this);



        //taking traffic_id from the shared preference
        SharedPreferences sf = getSharedPreferences("loginSession",MODE_PRIVATE);
        traffic_id = sf.getString("user_id", "");


        btn_getLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //check condition
                if (ActivityCompat.checkSelfPermission(Location.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                        && ActivityCompat.checkSelfPermission(Location.this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                    //Both granted
                    //call method

                    getCurrentLocation();
                    mRunnable.run();


                } else {
                    ActivityCompat.requestPermissions(Location.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION},100);
                }
            }
        });

    }

    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            //sending updated current location
            sendCurrentLocation();
            handler.postDelayed(this,5000);
        }
    };

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 100 && grantResults.length > 0 && (grantResults[0] + grantResults[1]
        == PackageManager.PERMISSION_GRANTED)){

            getCurrentLocation();
        } else {
            Toast.makeText(getApplicationContext(),"Permission Denied",Toast.LENGTH_SHORT).show();
        }
    }

    private void getCurrentLocation() {
        LocationManager locationManager = (LocationManager) getSystemService(
                Context.LOCATION_SERVICE
        );
        //check
        if (locationManager.isProviderEnabled(locationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(locationManager.NETWORK_PROVIDER)){

            fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<android.location.Location>() {
                @Override
                public void onComplete(@NonNull Task<android.location.Location> task) {
                    final android.location.Location location = task.getResult();

                    if (location != null){
                        latitude = String.valueOf(location.getLatitude());
                        longitude = String.valueOf(location.getLongitude());
                    } else {
                        LocationRequest locationRequest = new LocationRequest()
                                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                                .setInterval(10000)
                                .setFastestInterval(1000)
                                .setNumUpdates(1);
                        LocationCallback locationCallback = new LocationCallback(){
                            @Override
                            public void onLocationResult(LocationResult locationResult) {
                                android.location.Location location1 = locationResult.getLastLocation();

                                latitude = String.valueOf(location1.getLatitude());
                                longitude = String.valueOf(location1.getLongitude());

                            }
                        };

                        fusedLocationProviderClient.requestLocationUpdates(locationRequest,
                                locationCallback, Looper.myLooper());
                    }


                }
            });

        } else {

            startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                    .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
        }
    }

    private void sendCurrentLocation() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_POST, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
               // Toast.makeText(getApplication(),response,Toast.LENGTH_SHORT).show();
                attendance.setText(atttext);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }
        ){

            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                params.put("traffic_id",traffic_id);
                params.put("longitude",longitude);
                params.put("latitude",latitude);

                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }


}
