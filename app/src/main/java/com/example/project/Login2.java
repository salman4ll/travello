package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;


public class Login2 extends AppCompatActivity {


    private Button loginButton, backButton, createButton;
    private EditText emailEditText;
    private EditText passwordEditText;

    private SharedPreferences sharedPref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);

        TextView errorText = findViewById(R.id.message_error);

        errorText.setText(getIntent().getStringExtra("message"));

        loginButton = findViewById(R.id.login_button);
        backButton = findViewById(R.id.button_back);
        createButton = findViewById(R.id.button_create);

        emailEditText = findViewById(R.id.email);
        passwordEditText = findViewById(R.id.password);

        sharedPref = PreferenceManager.getDefaultSharedPreferences(this);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                login(email, password);
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login2.this, MainActivity.class);
                startActivity(intent);
            }
        });

        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login2.this, Registrasi.class);
                startActivity(intent);
            }
        });


    }

    private void login(final String email, final String password) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    ApiClient apiClient = new ApiClient();
                    String response = apiClient.login(email, password);
                    JSONObject jsonResponse = new JSONObject(response);
                    String status = jsonResponse.getString("status");

                    if (!status.equals("OK")) {
                        String errorMessage = jsonResponse.getString("message");
                        Intent intent = new Intent(Login2.this, Login2.class);
                        intent.putExtra("message", errorMessage);
                        startActivity(intent);
                        finish();

                    }else {
                        String token = jsonResponse.getString("token");

                        TokenManager tokenManager = new TokenManager(Login2.this);
                        tokenManager.saveToken(token);

                        Intent intent = new Intent(Login2.this, dashboard.class);
                        startActivity(intent);
                        finish();
                    }


                } catch (IOException | JSONException e) {
                    e.printStackTrace();
//                    Toast.makeText(Login2.this, "Login Gagal", Toast.LENGTH_SHORT).show();
                }
            }
        }).start();
    }
}