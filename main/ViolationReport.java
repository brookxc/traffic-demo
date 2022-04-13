package com.example.trafficapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ViolationReport extends AppCompatActivity {

    CardView tripleSeats,noHelmet,noSeatBelt,wrongParking,fancyPlate,usingMobile,stopLine,others;
    Button back_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_violation_report);


        tripleSeats = findViewById(R.id.cardTripleSeat);
        noHelmet = findViewById(R.id.cardNoHelmet);
        noSeatBelt = findViewById(R.id.cardNoSeatBelt);
        wrongParking = findViewById(R.id.cardWrongParking);
        fancyPlate = findViewById(R.id.cardFancyPlate);
        usingMobile = findViewById(R.id.cardUsingMobile);
        stopLine = findViewById(R.id.cardStopLine);
        others = findViewById(R.id.cardOtherViolations);
//        back_btn = findViewById(R.id.back_btn);

        tripleSeats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ViolationForm.class);
                intent.putExtra("TripleSeats", "Triple Seats");
                startActivity(intent);
            }
        });
        noHelmet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ViolationForm.class);
                intent.putExtra("NoHelmet", "NO Helmet");
                startActivity(intent);
            }
        });
        noSeatBelt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ViolationForm.class);
                intent.putExtra("NoSeatBelt", "No Seat Belt");
                startActivity(intent);
            }
        });
        wrongParking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ViolationForm.class);
                intent.putExtra("WrongParking", "Wrong Parking");
                startActivity(intent);
            }
        });
        usingMobile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ViolationForm.class);
                intent.putExtra("UsingMobile", "Using Phone");
                startActivity(intent);
            }
        });
        fancyPlate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ViolationForm.class);
                intent.putExtra("FancyPlate", "Fancy Plate");
                startActivity(intent);
            }
        });
        stopLine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ViolationForm.class);
                intent.putExtra("StopLine", "Stop Line");
                startActivity(intent);
            }
        });
        others.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ViolationForm.class);
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
