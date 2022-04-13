package com.example.trafficapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class ViolationForm extends AppCompatActivity {

     TextView textType;
     AlertDialog mAlertDialog;
     AlertDialog.Builder builder;
     TextInputEditText type_violation_edit, driver_name, license_plate_number, driver_address, driver_description, vehicle_model,punishment_price;
     String tripleSeats,noHelmet,noSeatBelt,wrongParking,fancyPlate,usingMobile,stopLine,others,reportedDate,payment="Not Paid";
     String URL_POST = "http://192.168.43.56/Traffic/sendviolationreport.php";
     Button submit,back_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_violation_form);

        driver_name = findViewById(R.id.driver_name);
        license_plate_number = findViewById(R.id.license_plate_number);
        driver_address = findViewById(R.id.driver_address);
        vehicle_model = findViewById(R.id.vehicle_model);
        driver_description = findViewById(R.id.driver_description);
        punishment_price = findViewById(R.id.punishment_price);
        submit = findViewById(R.id.submit);
        back_btn = findViewById(R.id.back_btn);


        Calendar calendar = Calendar.getInstance();

        textType = findViewById(R.id.type_violation);
        type_violation_edit = findViewById(R.id.type_violation_edit);
        type_violation_edit.setVisibility(View.GONE);

        tripleSeats = getIntent().getStringExtra("TripleSeats");
        noSeatBelt = getIntent().getStringExtra("NoSeatBelt");
        noHelmet = getIntent().getStringExtra("NoHelmet");
        wrongParking = getIntent().getStringExtra("WrongParking");
        fancyPlate = getIntent().getStringExtra("FancyPlate");
        usingMobile = getIntent().getStringExtra("UsingMobile");
        stopLine = getIntent().getStringExtra("StopLine");
        others = getIntent().getStringExtra("Others");

        //violation

        if (tripleSeats != null) {
            textType.setText(tripleSeats);
        }
        if (noSeatBelt != null) {
            textType.setText(noSeatBelt);
        }
        if (noHelmet != null) {
            textType.setText(noHelmet);
        }
        if (wrongParking != null) {
            textType.setText(wrongParking);
        }
        if (fancyPlate != null) {
            textType.setText(fancyPlate);
        }

        if (usingMobile != null) {
            textType.setText(usingMobile);
        }
        if (stopLine != null) {
            textType.setText(stopLine);
        }
        if (others != null) {
            textType.setText(others);
            type_violation_edit.setVisibility(View.VISIBLE);
            textType.setVisibility(View.GONE);
            textType.setHeight(0);
        }

        ////current date
        int year = calendar.get(Calendar.YEAR);
        int monthOfYear = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        reportedDate =  year+"-"+monthOfYear+"-"+dayOfMonth;

//        //when back button clicked
//        back_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getApplicationContext(),ViolationReport.class);
//                intent.setFlags(intent.FLAG_ACTIVITY_CLEAR_TOP);
//                startActivity(intent);
//
//            }
//        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder = new AlertDialog.Builder(ViolationForm.this);
                builder.setMessage("Do you want to send this report");
                builder.setCancelable(true);
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mAlertDialog.cancel();
                        ////call///
                        insertVR();
                        ////////

                    }
                })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mAlertDialog.cancel();
                            }
                        });
                mAlertDialog = builder.create();
                mAlertDialog.show();
            }
        });
    }

    private void insertVR() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_POST, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplication(),response,Toast.LENGTH_SHORT).show();
                if(response.contains("sent")) {
                    driver_name.setText("");
                    license_plate_number.setText("");
                    driver_address.setText("");
                    vehicle_model.setText("");
                    driver_description.setText("");
                    punishment_price.setText("");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ViolationForm.this,error+"",Toast.LENGTH_SHORT).show();
            }
        }
        ){

            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();                String driverName = driver_name.getText().toString();
                String licensePlateNumber = license_plate_number.getText().toString();
                String driverAddress = driver_address.getText().toString();
                String vehicleModel = vehicle_model.getText().toString();
                String driverDescription = driver_description.getText().toString();
                String punishmentPrice = punishment_price.getText().toString();


                if (others != null) {
                    String type = type_violation_edit.getText().toString();
                    params.put("type",type);
                } else {
                    String type = textType.getText().toString();
                    params.put("type",type);
                }

                params.put("driverName",driverName);
                params.put("licensePlateNumber",licensePlateNumber);
                params.put("driverAddress",driverAddress);
                params.put("vehicleModel",vehicleModel);
                params.put("description",driverDescription);

                params.put("payment",payment);
                params.put("reportDate", reportedDate);
                params.put("punishmentPrice", punishmentPrice);

                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }
}
