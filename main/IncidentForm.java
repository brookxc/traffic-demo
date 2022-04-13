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
import java.util.Objects;

public class IncidentForm extends AppCompatActivity {

    Button submit,back_btn;
    TextView textType;
    EditText type_Incident_edit;
    AlertDialog mAlertDialog;
    AlertDialog.Builder builder;
    String accident,construction,oilSpill,fireCNG,rain,treeFall,roadFight,animalOnRoad,others,reportedDate;

    /////////// sending////
    TextInputEditText casualties,license_plate_number,incident_place,incident_detail, vehicle_model, soft_injuries, hard_injuries, property_loss;
    private static String URL_POST = "http://192.168.43.56/Traffic/sendincidentreport.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_incident_form);

        textType = findViewById(R.id.type_incident);
        casualties = findViewById(R.id.casualties);
        license_plate_number = findViewById(R.id.license_plate_number);
        incident_place = findViewById(R.id.incident_place);
        vehicle_model = findViewById(R.id.vehicle_model);
        incident_detail = findViewById(R.id.incident_detail);
        hard_injuries = findViewById(R.id.hard_injuries);
        soft_injuries = findViewById(R.id.soft_injuries);
        property_loss = findViewById(R.id.property_loss);
        submit = findViewById(R.id.submit);
//        back_btn = findViewById(R.id.back_btn);

        Calendar calendar = Calendar.getInstance();

        type_Incident_edit = findViewById(R.id.type_incident_edit);
        type_Incident_edit.setVisibility(View.GONE);

        accident = getIntent().getStringExtra("Accident");
        construction = getIntent().getStringExtra("Construction");
        oilSpill = getIntent().getStringExtra("OilSpill");
        fireCNG = getIntent().getStringExtra("FireCNG");
        rain = getIntent().getStringExtra("Rain");
        treeFall = getIntent().getStringExtra("TreeFall");
        roadFight = getIntent().getStringExtra("RoadFight");
        animalOnRoad = getIntent().getStringExtra("AnimalOnRoad");
        others = getIntent().getStringExtra("Others");

        //Incident

        if(accident != null) {
            textType.setText(accident);
        }
        if(construction != null) {
            textType.setText(construction);
        }
        if (oilSpill != null) {
            textType.setText(oilSpill);
        }
        if (fireCNG != null) {
            textType.setText(fireCNG);
        }
        if (rain != null) {
            textType.setText(rain);
        }
        if (treeFall != null) {
            textType.setText(treeFall);
        }
        if (roadFight != null) {
            textType.setText(roadFight);
        }
        if (animalOnRoad != null) {
            textType.setText(animalOnRoad);
        }
        if (others != null) {
            textType.setText(others);
            type_Incident_edit.setVisibility(View.VISIBLE);
            textType.setVisibility(View.GONE);
        }
//        //when back button clicked
//        back_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getApplicationContext(),IncidentReport.class);
//                intent.setFlags(intent.FLAG_ACTIVITY_CLEAR_TOP);
//                startActivity(intent);
//            }
//        });

        ////current date
                int year = calendar.get(Calendar.YEAR);
                int monthOfYear = calendar.get(Calendar.MONTH);
                int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
                reportedDate =  year+"-"+monthOfYear+"-"+dayOfMonth;


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                builder = new AlertDialog.Builder(IncidentForm.this);
                builder.setMessage("Do you want to send this report");
                builder.setCancelable(true);
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mAlertDialog.cancel();
                        ////call///
                        insertIR();
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

    private void insertIR() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_POST, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplication(),response,Toast.LENGTH_SHORT).show();
                if(response.contains("sent")) {
                    license_plate_number.setText("");
                    casualties.setText("");
                    incident_place.setText("");
                    vehicle_model.setText("");
                    incident_detail.setText("");
                    hard_injuries.setText("");
                    soft_injuries.setText("");
                    property_loss.setText("");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(IncidentForm.this, error + "", Toast.LENGTH_SHORT).show();
            }
        }
        ) {

            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                String Casualties = casualties.getText().toString();
                String licensePlateNumber = license_plate_number.getText().toString();
                String incidentPlace = incident_place.getText().toString();
                String vehicleModel = vehicle_model.getText().toString();
                String incidentDetail = incident_detail.getText().toString();
                String hardInjuries = hard_injuries.getText().toString();
                String softInjuries = soft_injuries.getText().toString();
                String propertyLoss = property_loss.getText().toString();


                if (others != null) {
                    String type = type_Incident_edit.getText().toString();
                    params.put("incident_category",type);
                } else {
                    String type = textType.getText().toString();
                    params.put("incident_category",type);
                }

                params.put("casualties", Casualties);
                params.put("license_plate_number", licensePlateNumber);
                params.put("incident_place", incidentPlace);
                params.put("vehicle_model",vehicleModel);
                params.put("incident_detail", incidentDetail);
                params.put("hard_injuries", hardInjuries);
                params.put("soft_injuries", softInjuries);
                params.put("property_loss", propertyLoss);

                params.put("reported_date", reportedDate);

                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

}
