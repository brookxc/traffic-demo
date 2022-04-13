package com.example.trafficapp;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Reports extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    List<ReportData> reportDatas;
    String URL_GET = "http://192.168.43.56/Traffic/reports.php";
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reports);

        final SwipeRefreshLayout refreshLayout = findViewById(R.id.SwipeRefreshLayout);
        recyclerView = findViewById(R.id.recyclerView);
        progressBar = findViewById(R.id.progressBar);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        reportDatas = new ArrayList<>();
        //////////////////
        getReports();
        refreshLayout.setRefreshing(false);
    }
    private void getReports() {

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_GET, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonResult = new JSONObject(response);
                    int success = jsonResult.getInt("success");
                    if (success == 1) {
                        JSONArray reports = jsonResult.getJSONArray("reports");

                        for (int i = 0; i < reports.length(); i++) {

                            JSONObject report = reports.getJSONObject(i);

                            ReportData list = new ReportData(report.getInt("id"),
                                    report.getString("driverName"),
                                    report.getString("licensePlateNumber"),
                                    report.getString("driverAddress"),
                                    report.getString("type"),
                                    report.getString("description"),
                                    report.getString("reportDate"),
                                    report.getString("payment"),
                                    report.getString("vehicleModel"),
                                    report.getString("punishmentPrice"));
                            reportDatas.add(list);

                        }
                        adapter = new Adapter(getApplicationContext(), reportDatas);
                        recyclerView.setAdapter(adapter);
                        progressBar.setVisibility(View.GONE);

                    } else {
                        Toast.makeText(getApplicationContext(), "there is a report", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("tag", "onErrorResponse: " + error.getMessage());
            }
        });

        Volley.newRequestQueue(Reports.this).add(stringRequest);
    }
}