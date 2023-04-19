package com.example.project;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterRecycleView extends RecyclerView.Adapter<AdapterRecycleView.ViewHolder> {
    private ArrayList<DestinationModels> dataDestination;
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textName;
        TextView textLocation;
        ImageView imageIcon;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textName = itemView.findViewById(R.id.judul_destinasi);
            textLocation = itemView.findViewById(R.id.lokasi_destionasi);
            imageIcon = itemView.findViewById(R.id.image_destinasi);
        }
    }

    AdapterRecycleView(ArrayList<DestinationModels> dataDestination) {
        this.dataDestination = dataDestination;
    }

    @NonNull
    @Override
    public AdapterRecycleView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_destination, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterRecycleView.ViewHolder holder, int position) {

        TextView textName = holder.textName;
        TextView textLocation = holder.textLocation;
        ImageView imageIcon = holder.imageIcon;

        textName.setText(dataDestination.get(position).getName());
        textLocation.setText(dataDestination.get(position).getLocation());
        imageIcon.setImageResource(R.mipmap.ic_launcher);
    }

    @Override
    public int getItemCount() {
        return dataDestination.size();
    }
}
