package com.example.m_hiking_4;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    private FloatingActionButton fab;
    private RecyclerView hikingRv;
    private DbHelper dbHelper;
    private AdapterHiking adapterHiking;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DbHelper(this);

        fab = findViewById(R.id.fab);
        hikingRv = findViewById(R.id.hikingRv);

        hikingRv.setHasFixedSize(true);


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddEditTrip.class);
                startActivity(intent);
            }
        });

        loadData();
    }

    private void loadData() {
        adapterHiking = new AdapterHiking(this, dbHelper.getAllData());
        hikingRv.setAdapter(adapterHiking);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }
}