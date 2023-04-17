package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class Dashboard extends AppCompatActivity {

    TextView heiWelcome;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        DatabaseHandler db = new DatabaseHandler(Dashboard.this);


        UserModels user = db.getUser();

        heiWelcome = findViewById(R.id.hei_name);
        heiWelcome.setText("hi, " + user.getName());

    }

}