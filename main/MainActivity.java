package com.example.trafficapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    CardView violationReport;
    CardView IncidentReport;
    CardView MyProfile;
    CardView Reports,Logout,Location;
    String uid;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        violationReport = findViewById(R.id.cardViolationReport);
        IncidentReport = findViewById(R.id.cardIncidentReport);
        MyProfile = findViewById(R.id.cardProfile);
        Reports = findViewById(R.id.cardReports);
        Logout = findViewById(R.id.cardLogout);
        Location = findViewById(R.id.cardLocation);

        SharedPreferences sf = getSharedPreferences("loginSession",MODE_PRIVATE);
        final String username = sf.getString("username", "");



        violationReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ViolationReport.class);
                startActivity(intent);
            }
        });

        IncidentReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), IncidentReport.class);
                startActivity(intent);
            }
        });
        MyProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MyProfile.class);
                intent.putExtra("Message", username);
                startActivity(intent);
            }
        });
        Reports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Reports.class);
                startActivity(intent);
            }
        });
        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getSharedPreferences("loginSession",MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove("user_id");
                editor.remove("firstName");
                editor.remove("lastName");
                editor.remove("email");
                editor.remove("username");
                editor.remove("phone");
                editor.remove("address");
                editor.remove("place");
                editor.apply();


                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
        Location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Location.class);
                startActivity(intent);
            }
        });

    }


}
