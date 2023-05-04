package com.example.project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class UlasanDestination extends AppCompatActivity {
    RecyclerView recyclerView;
    RecyclerView.Adapter recyclerViewAdapter;
    RecyclerView.LayoutManager recycleViewLayoutManager;
    ArrayList<UlasanModels> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ulasan_destination);


        recyclerView = findViewById(R.id.recycler_ulasan);
        recyclerView.setHasFixedSize(true);

        recycleViewLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(recycleViewLayoutManager);
//        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        data = new ArrayList<>();

        String  destinationId = getIntent().getStringExtra("id");

        TokenManager tokenManager = new TokenManager(this);

        DataUlasan dataUlasan = new DataUlasan(this, destinationId, tokenManager.getToken());
        dataUlasan.execute();


        try {
            data = dataUlasan.get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        recyclerViewAdapter = new AdapterRecycleViewUlasan(this, data);
        recyclerView.setAdapter(recyclerViewAdapter);

    }
}