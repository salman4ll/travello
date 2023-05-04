package com.example.project;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class MyDestination extends AsyncTask<Void, Void, Void> {
    private Context context;
    private String status;

    public MyDestination(Context context){
        this.context = context;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        try {
            ApiClient apiClient = new ApiClient();
            String response = apiClient.getDestinations();
            JSONObject jsonResponse = new JSONObject(response);
            status = jsonResponse.getString("status");
            JSONArray jsonArray = new JSONArray(jsonResponse.getString("data"));

            ArrayList<DestinationModels> destinationModels;

            destinationModels = new ArrayList<>();

            DatabaseDestinationHandler db = new DatabaseDestinationHandler(context);

            if (db.getRecordCount()==jsonArray.length()) {
                for (int i = 0; i < jsonArray.length(); i ++ ){
                    JSONObject dataInput = new JSONObject(jsonArray.getString(i));
                    destinationModels.add(i, new DestinationModels(
                            dataInput.getString("_id"),
                            dataInput.getString("name"),
                            dataInput.getString("description"),
                            dataInput.getString("location"),
                            dataInput.getString("category"),
                            dataInput.getString("image"),
                            (float) dataInput.getDouble("rating")
                    ));
                    db.updateRecord(destinationModels.get(i));
                    db.close();
                }
            }else {
                for (int i = 0; i < jsonArray.length(); i ++ ){
                    JSONObject dataInput = new JSONObject(jsonArray.getString(i));
                    destinationModels.add(i, new DestinationModels(
                            dataInput.getString("_id"),
                            dataInput.getString("name"),
                            dataInput.getString("description"),
                            dataInput.getString("location"),
                            dataInput.getString("category"),
                            dataInput.getString("image"),
                            (float) dataInput.getDouble("rating")
                    ));
                    db.deleteRecord(destinationModels.get(i));
                    db.addRecord(destinationModels.get(i));
                    db.close();
                }
            }

        } catch (IOException e) {
//            throw new RuntimeException(e);
            e.printStackTrace();
        } catch (JSONException e) {
//            throw new RuntimeException(e);
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        // Metode ini akan dijalankan di thread utama setelah doInBackground() selesai

        if (status != null){
            if (status.equals("OK")){
//                Toast.makeText(context, "Update Data", Toast.LENGTH_SHORT).show();
            }else {
//                Toast.makeText(context, "Gagal Update Data : Pastikan Koneksi Internet Bagus", Toast.LENGTH_SHORT).show();
            }
        }else {
//            Toast.makeText(context, "Gagal Update Data : Pastikan Koneksi Internet Bagus", Toast.LENGTH_SHORT).show();
        }

    }
}
