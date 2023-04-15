package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class Registrasi extends AppCompatActivity {

    private Button btnBack, btnCreate, btnLogin;
    private EditText firstName;
    private EditText lastName;
    private EditText email;
    private EditText password;

    private TextView message;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrasi);



        message = findViewById(R.id.message_error_create);
        message.setText(getIntent().getStringExtra("message"));

        btnCreate = findViewById(R.id.tombol_regist);

        firstName = findViewById(R.id.first_name_regist);
        lastName = findViewById(R.id.last_name);
        email = findViewById(R.id.email_regist);
        password = findViewById(R.id.password_regist);

        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String valFirstName = firstName.getText().toString();
                String valLastName = lastName.getText().toString();
                String valEmail = email.getText().toString();
                String valPassword = password.getText().toString();
                regist(valFirstName, valLastName, valEmail, valPassword);
            }
        });


        btnBack = findViewById(R.id.button_back);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Registrasi.this, MainActivity.class);
                startActivity(intent);
            }
        });

        btnLogin = findViewById(R.id.move_login);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Registrasi.this, Login2.class);
                startActivity(intent);
            }
        });


    }

    private void regist(final String firsName, final String lastName, final String email, final String password) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    ApiClient apiClient = new ApiClient();
                    String response = apiClient.registration(firsName, lastName, email, password);
                    JSONObject jsonResponse = new JSONObject(response);
                    String status = jsonResponse.getString("status");

                    if (!status.equals("OK")) {
                        String errorMessage = jsonResponse.getString("message");
                        Intent intent = new Intent(Registrasi.this, Registrasi.class);
                        intent.putExtra("message", errorMessage);
                        startActivity(intent);
                        finish();

                    }else {
                        String errorMessage = jsonResponse.getString("message");
                        Intent intent = new Intent(Registrasi.this, Login2.class);
                        intent.putExtra("message", errorMessage);
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