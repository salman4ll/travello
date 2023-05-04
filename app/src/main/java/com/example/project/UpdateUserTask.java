package com.example.project;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class UpdateUserTask extends AsyncTask<Void, Void, String> {
    private Context context;
    private String token;
    private UserModels user;

    public UpdateUserTask(Context context, String token, UserModels user) {
        this.context = context;
        this.token = token;
        this.user = user;
    }

    @Override
    protected String doInBackground(Void... voids) {
        String result = null;
        try {
            ApiClient apiClient = new ApiClient();
            String response = apiClient.updateUser(token, user.getName(), user.getEmail());

            JSONObject jsonResponse = new JSONObject(response);

            String status = jsonResponse.getString("status");

            if (!status.equals("OK")) {
                result = jsonResponse.toString();
            } else {
                String data = jsonResponse.getString("data");
                JSONObject jsonData = new JSONObject(data);

                user.setName(jsonData.getString("name"));
                user.setEmail(jsonData.getString("email"));

                DatabaseUserHandler db = new DatabaseUserHandler(context);

                db.updateUser(user);
                db.close();
                result = "OK";
            }

        } catch (IOException | JSONException e) {
            e.printStackTrace();
            result = e.getMessage();
        }

        return result;
    }

    @Override
    protected void onPostExecute(String result) {
        if (result != null) {
            if (result.equals("OK")) {
                Toast.makeText(context, "Update user berhasil", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "Update user gagal: " + result, Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(context, "Terjadi kesalahan: " + result, Toast.LENGTH_SHORT).show();
        }
    }
}
