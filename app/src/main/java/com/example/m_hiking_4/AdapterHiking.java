package com.example.m_hiking_4;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterHiking extends RecyclerView.Adapter<AdapterHiking.HikingViewHolder> {

    private Context context;
    private ArrayList<ModelHiking> hikingList;

    //add constructor


    public AdapterHiking(Context context, ArrayList<ModelHiking> hikingList) {
        this.context = context;
        this.hikingList = hikingList;
    }

    @NonNull
    @Override
    public HikingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_hiking_item,parent,false);
        HikingViewHolder vh = new HikingViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull HikingViewHolder holder, int position) {

        ModelHiking modelHiking = hikingList.get(position);

        String id = modelHiking.getId();
        String image = modelHiking.getImage();
        String name = modelHiking.getName();


        holder.tripName.setText(name);
        if (image.equals("")){
            holder.hikingImage.setImageResource(R.drawable.baseline_forest_24);
        }else{
            holder.hikingImage.setImageURI(Uri.parse(image));
        }

        holder.hikingAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,HikingDetail.class);
                intent.putExtra("hikingId",id);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return hikingList.size();
    }

    class HikingViewHolder extends RecyclerView.ViewHolder{
        ImageView hikingImage,hikingAdd;
        TextView tripName;

        public HikingViewHolder(@NonNull View itemView) {
            super(itemView);

            hikingImage = itemView.findViewById(R.id.trip_image);
            hikingAdd = itemView.findViewById(R.id.trip_add);
            tripName = itemView.findViewById(R.id.trip_name);
        }
    }
}
