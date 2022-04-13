package com.example.trafficapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class IncidentReport extends AppCompatActivity {

    CardView accident,construction,oilSpill,fireCNG,rain,treeFall,roadFight,animalOnRoad,others;
    Button back_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_incident_report);

        accident = findViewById(R.id.cardAccident);
        construction = findViewById(R.id.cardConstruction);
        oilSpill = findViewById(R.id.cardOilSpill);
        fireCNG = findViewById(R.id.cardFireCNG);
        rain = findViewById(R.id.cardRain);
        treeFall = findViewById(R.id.cardTreeFall);
        roadFight = findViewById(R.id.cardRoadFight);
        animalOnRoad = findViewById(R.id.cardAnimalOnRoad);
        others = findViewById(R.id.cardOtherIncidents);
//        back_btn = findViewById(R.id.back_btn);

        accident.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), IncidentForm.class);
                intent.putExtra("Accident", "Accident");
                startActivity(intent);
            }
        });
        construction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), IncidentForm.class);
                intent.putExtra("Construction", "Construction");
                startActivity(intent);
            }
        });
        oilSpill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), IncidentForm.class);
                intent.putExtra("OilSpill", "Oil Spill");
                startActivity(intent);
            }
        });
        fireCNG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), IncidentForm.class);
                intent.putExtra("FireCNG", "FireCNG");
                startActivity(intent);
            }
        });
        rain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), IncidentForm.class);
                intent.putExtra("Rain", "Rain");
                startActivity(intent);
            }
        });
        treeFall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), IncidentForm.class);
                intent.putExtra("TreeFall", "Tree Fall");
                startActivity(intent);
            }
        });
        roadFight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), IncidentForm.class);
                intent.putExtra("RoadFight", "Road Fight");
                startActivity(intent);
            }
        });
        animalOnRoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), IncidentForm.class);
                intent.putExtra("AnimalOnRoad", "AnimalOnRoad");
                startActivity(intent);
            }
        });
        others.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), IncidentForm.class);
                intent.putExtra("Others", "Others");
                startActivity(intent);
            }
        });

        //when back button clicked
//        back_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
//                intent.setFlags(intent.FLAG_ACTIVITY_CLEAR_TOP);
//                startActivity(intent);
//            }
//        });


    }
}
