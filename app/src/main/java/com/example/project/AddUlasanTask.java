package com.example.project;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class AddUlasanTask extends AsyncTask<Void, Void, String> {
    private Context context;
    private String token;
    private String destinationId;
    private String message;
    private String statusResponse, messageResponse;
    private float rating;

    public AddUlasanTask(Context context, String token, String destinationId,String message, float rating){
        this.context = context;
        this.token = token;
        this.destinationId = destinationId;
        this.message = message;
        this.rating = rating;
    }
    @Override
    protected String doInBackground(Void... voids) {
        try {
            ApiClient apiClient = new ApiClient();
            String response = apiClient.postUlasan(token, destinationId, message, rating);

            JSONObject jsonResponse = new JSONObject(response);

            this.statusResponse = jsonResponse.getString("status");
            this.messageResponse = jsonResponse.getString("message");

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        if (statusResponse != null){
            if (statusResponse.equals("OK")){
                Toast.makeText(context, messageResponse, Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(context, "Gagal : \n"+messageResponse, Toast.LENGTH_SHORT).show();
            }
        }else {
            Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
        }
    }
}
