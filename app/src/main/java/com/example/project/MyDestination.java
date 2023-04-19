package com.example.project;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class MyDestination {
    public MyDestination(Context context){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    ApiClient apiClient = new ApiClient();
                    String response = apiClient.getDestinations();
                    JSONObject dataArray = new JSONObject(response);
                    JSONArray jsonArray = new JSONArray(dataArray.getString("data"));

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
                                    dataInput.getString("image")
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
                                    dataInput.getString("image")
                            ));
                            db.deleteRecord(destinationModels.get(i));
                            db.addRecord(destinationModels.get(i));
                            db.close();
                        }
                    }



                } catch (IOException e) {
                    throw new RuntimeException(e);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();
    }



}
