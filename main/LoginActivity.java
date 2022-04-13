package com.example.trafficapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    private TextInputEditText etUsername, etPassword;
    RequestQueue loginQueue;
    StringRequest loginRequest;
    private static String URL_login = "http://192.168.43.56/Traffic/includes/login.inc.php";
    SharedPreferences loginPrefs;
    SharedPreferences.Editor editor;
    ProgressBar progressBar ;
    String enteredUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUsername = findViewById(R.id.username);
        etPassword = findViewById(R.id.password);
       progressBar = findViewById(R.id.progressBar);

        loginQueue = Volley.newRequestQueue(this);

        loginPrefs = getSharedPreferences("loginSession", MODE_PRIVATE);
        if (loginPrefs.contains("username")){
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }

    }
    public void login(View view){


        progressBar.setVisibility(View.VISIBLE);

        enteredUsername = this.etUsername.getText().toString().trim();
        final String password = this.etPassword.getText().toString().trim();

        loginRequest = new StringRequest(Request.Method.POST, URL_login,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        if(response.equals("empty_fields")) {
                            Toast.makeText(LoginActivity.this, "please fill in all fields", Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        } else if(response.equals("user_null")){
                            Toast.makeText(LoginActivity.this, "username or email is incorrect", Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        } else if(response.equals("incorrect_password")){
                            Toast.makeText(LoginActivity.this, "Incorrect password", Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }


                        else if(response.contains("uid=")) {

                            editor = loginPrefs.edit();
                            editor.putString("username", enteredUsername);
                            editor.putString("user_id", response.replace("uid=", ""));
                            editor.apply();


                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            progressBar.setVisibility(View.GONE);
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(LoginActivity.this,"Check your connection", Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("uid", enteredUsername);
                params.put("pwd", password);

                return params;
            }
        };

        loginQueue.add(loginRequest);
    }

}
