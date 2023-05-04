package com.example.project;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class DataUlasan extends AsyncTask<Void, Void, ArrayList<UlasanModels>> {

    private String status, response;
    private Context context;
    private String destinationId;
    private String token;
    private ArrayList<UlasanModels> ulasanModelsArrayList;

    public DataUlasan(Context context, String destinationId, String token){
        this.context = context;
        this.destinationId = destinationId;
        this.token = token;
    }

    @Override
    protected ArrayList<UlasanModels> doInBackground(Void... voids) {
        try {
            ApiClient apiClient = new ApiClient();
            String response = apiClient.getUlasanbyDestination(this.destinationId, token);

            JSONObject jsonResponse = new JSONObject(response);
            this.status = jsonResponse.getString("status");

            JSONObject data = new JSONObject(jsonResponse.getString("data"));
            JSONArray ulasan = new JSONArray(data.getString("ulasan"));

            this.response = response;



            ulasanModelsArrayList = new ArrayList<>();

            for (int i = 0; i < ulasan.length(); i ++){
                JSONObject u = new JSONObject(ulasan.getString(i));
                ulasanModelsArrayList.add(i, new UlasanModels(
                        u.getString("user_name"),
                        u.getString("message"),
                        (float) u.getDouble("rating")
                ));
            }

        } catch (IOException e) {
//            throw new RuntimeException(e);
        } catch (JSONException e) {
//            throw new RuntimeException(e);
        }

        return ulasanModelsArrayList;
    }

    @Override
    protected void onPostExecute(ArrayList<UlasanModels> result) {
        if (status != null){
            if (status.equals("OK")){
//                Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show();
            } else {
                // tampilkan pesan kesalahan atau lakukan tindakan lain
//                Toast.makeText(context, "Error : ", Toast.LENGTH_SHORT).show();
            }
        }else {
//            Toast.makeText(context, "Error : ", Toast.LENGTH_SHORT).show();
        }
    }
}
