package com.example.m_hiking_4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Locale;

public class HikingDetail extends AppCompatActivity {

    private TextView nameTv,locationTv,dateTv,parkingTv,lengthTv,difficultTv,descriptionTv;
    private ImageView tripIv;
    private String id;

    private DbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hiking_detail);

        //initial dbHelper
        dbHelper = new DbHelper(this);

        Intent intent = getIntent();
        id = intent.getStringExtra("hikingId");

        nameTv = findViewById(R.id.nameTv);
        locationTv = findViewById(R.id.locationTv);
        dateTv = findViewById(R.id.dateTv);
        parkingTv = findViewById(R.id.parkingTv);
        lengthTv =  findViewById(R.id.lengthTv);
        difficultTv  = findViewById(R.id.difficultTv);
        descriptionTv = findViewById(R.id.descriptionTv);

        tripIv = findViewById(R.id.tripIv);
        loadDataById();
    }

    private void loadDataById() {

        String selectQuery = "SELECT * FROM "+Constants.TABLE_NAME + " WHERE " + Constants.C_ID + " =\"" + id + "\"";
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery,null);

        if(cursor.moveToFirst()){
            do{
                //get data
                        String image = ""+cursor.getString(cursor.getColumnIndexOrThrow(Constants.C_IMAGE));
                        String name = ""+cursor.getString(cursor.getColumnIndexOrThrow(Constants.C_NAME));
                        String location = ""+cursor.getString(cursor.getColumnIndexOrThrow(Constants.C_LOCATION));
                        String date = ""+cursor.getString(cursor.getColumnIndexOrThrow(Constants.C_DATE));
                        String parking = ""+cursor.getString(cursor.getColumnIndexOrThrow(Constants.C_PARKING));
                        String length = ""+cursor.getString(cursor.getColumnIndexOrThrow(Constants.C_LENGTH));
                        String difficult = ""+cursor.getString(cursor.getColumnIndexOrThrow(Constants.C_DIFFICULT));
                        String description = ""+cursor.getString(cursor.getColumnIndexOrThrow(Constants.C_DESCRIPTION));


                        nameTv.setText(name);
                        locationTv.setText(location);
                        dateTv.setText(date);
                        parkingTv.setText(parking);
                        lengthTv.setText(length);
                        difficultTv.setText(difficult);
                        descriptionTv.setText(description);

                        if(image.equals("null")){
                            tripIv.setImageResource(R.drawable.baseline_forest_24);
                        }else{
                            tripIv.setImageURI(Uri.parse(image));
                        }
            }while(cursor.moveToNext());
        }

        db.close();
    }


}