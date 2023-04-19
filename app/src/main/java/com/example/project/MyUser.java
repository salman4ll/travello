package com.example.project;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class MyUser {
    public MyUser(){

    }

    public void updateUser(Context context, final String token, UserModels user) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    ApiClient apiClient = new ApiClient();
                    String response = apiClient.updateUser(token, user.getName(), user.getEmail());

                    JSONObject jsonResponse = new JSONObject(response);

                    String status = jsonResponse.getString("status");

                    if (!status.equals("OK")){
                        Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
                    } else {
                        String data = jsonResponse.getString("data");
                        JSONObject jsonData = new JSONObject(data);

                        user.setName(jsonData.getString("name"));
                        user.setEmail(jsonData.getString("email"));

                        DatabaseUserHandler db = new DatabaseUserHandler(context);

                        db.updateUser(user);
                        db.close();
                    }

                } catch (IOException | JSONException e) {
                    e.printStackTrace();
//                    Toast.makeText(dashboard.this, "Login Gagal", Toast.LENGTH_SHORT).show();
                }
            }
        }).start();
    }
}
