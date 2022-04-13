package com.example.trafficapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MyProfile extends AppCompatActivity {

    String URL = "http://192.168.43.56/Traffic/myprofile.php";
    TextView uid ,tFirstName1, tLastName, tPhoneNumber, tAddress, tWorkPlace, tEmail, userID;
    String username;
    ProgressBar progressBar ;
    Button back_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);

        userID = findViewById(R.id.userID);
        uid = findViewById(R.id.uid);
        tFirstName1 = findViewById(R.id.firstName);
        tLastName = findViewById(R.id.lastName);
        tPhoneNumber = findViewById(R.id.phoneNumber);
        //tAddress = findViewById(R.id.address);
        tWorkPlace = findViewById(R.id.workPlace);
        tEmail = findViewById(R.id.email);
        back_btn = findViewById(R.id.back_btn);

        progressBar = findViewById(R.id.progressBar);
        //when back button clicked
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                intent.setFlags(intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        username = getIntent().getExtras().getString("Message");

        if (username.equals("")) {

            Toast.makeText(this, "Check Detail!", Toast.LENGTH_LONG).show();
            return;
        }
/////////////// Send a username to receive the user details
        sendUid();

    }


    private void sendUid() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonResult = new JSONObject(response);
                    int success = jsonResult.getInt("success");
                    if (success == 1) {
                        JSONArray user = jsonResult.getJSONArray("user");

                        JSONObject report = user.getJSONObject(0);
                        int user_id = report.getInt("user_id");
                        String firstName = report.getString("first_name");
                        String lastName = report.getString("last_name");
                        String email = report.getString("user_email");
                        String phone = report.getString("user_phone");
                        //String address = report.getString("user_address");
                        String place = report.getString("assigned_place");

                        SharedPreferences sharedPreferences = getSharedPreferences("loginSession",MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("user_id", String.valueOf(user_id));
                        editor.putString("firstName",firstName);
                        editor.putString("lastName",lastName);
                        editor.putString("email",email);
                        editor.putString("username",username);
                        editor.putString("phone",phone);
                        //editor.putString("address",address);
                        editor.putString("place",place);
                        editor.apply();

                        tFirstName1.setText(firstName);
                        tLastName.setText(lastName);
                        tPhoneNumber.setText(phone);
                      //  tAddress.setText(address);
                        tWorkPlace.setText(place);
                        uid.setText(String.format("%s %s", firstName, lastName));
                        tEmail.setText(email);
                        userID.setText(String.valueOf(user_id));
                        progressBar.setVisibility(View.GONE);


                    } else {
                        Toast.makeText(getApplicationContext(), "No Data Found", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MyProfile.this,error+"",Toast.LENGTH_SHORT).show();
            }
        }
        ){

            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();

                params.put("uid",username);

                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }


}





