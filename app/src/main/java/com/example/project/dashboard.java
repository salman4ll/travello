package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

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

        heiWelcome = findViewById(R.id.hei__welcome_to);

        TokenManager tokenManager = new TokenManager(this);
        String token = tokenManager.getToken();

        ApiClient apiClient = new ApiClient();
        try {
            String response = apiClient.getUser(token);
            JSONObject jsonResponse = new JSONObject(response);
            String email = jsonResponse.getString("email");
            System.out.println(email);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }
}