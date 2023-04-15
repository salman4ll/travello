package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;

public class dashboard extends AppCompatActivity {

    TextView heiWelcome;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        TokenManager tokenManager = new TokenManager(this);
        String token = tokenManager.getToken();

        getUser(token);
    }

    private void getUser(final String token) {
        new Thread(new Runnable() {
            @Override
            public void run() {
//                setContentView(R.layout.activity_dashboard);
                heiWelcome = findViewById(R.id.hei__welcome_to);
                try {
                    ApiClient apiClient = new ApiClient();
                    String response = apiClient.getUser(token);

                    JSONObject jsonResponse = new JSONObject(response);
                    String data = jsonResponse.getString("data");

                    JSONObject jsonData = new JSONObject(data);

                    String name = jsonData.getString("name");

                    heiWelcome.setText("hi, "+name);


                } catch (IOException | JSONException e) {
                    e.printStackTrace();
//                    Toast.makeText(dashboard.this, "Login Gagal", Toast.LENGTH_SHORT).show();
                }
            }
        }).start();
    }
}