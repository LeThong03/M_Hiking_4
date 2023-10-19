package com.example.m_hiking_4;




import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;

import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;



import com.google.android.material.floatingactionbutton.FloatingActionButton;



public class AddEditTrip extends AppCompatActivity {

    private ImageView captureIv;
    private EditText nameEdt,locationEdt,dateEdt,parkingEdt,lengthEdt,difficultEdt,descriptionEdt;
    private FloatingActionButton fab;

    private ActionBar actionBar;

    String name,location,date,parking,length,difficult,description;

    private static final int CAMERA_PERMISSION = 100;
    private static final int STORAGE_PERMISSION = 200;
    private static final int IMAGE_FROM_GALLERY = 300;
    private static final int IMAGE_FROM_CAMERA = 400;

    private String[] cameraPermission;
    private String[] storagePermission;


    Uri imageUri;

    //dbHelper
    private DbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_trip);

        dbHelper = new DbHelper(this);

        cameraPermission = new String[]{Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE};
        storagePermission = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};

        actionBar = getSupportActionBar();


        actionBar.setTitle("Add trip");
        //back home button
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);


        captureIv = findViewById(R.id.captureIv);
        nameEdt = findViewById(R.id.nameEdt);
        locationEdt = findViewById(R.id.locationEdt);
        dateEdt = findViewById(R.id.dateEdt);
        parkingEdt = findViewById(R.id.parkingEdt);
        lengthEdt = findViewById(R.id.lengthEdt);
        difficultEdt = findViewById(R.id.difficultEdt);
        descriptionEdt = findViewById(R.id.descriptionEdt);
        fab = findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveData();
            }
        });

        captureIv.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showImagePickerDialog();
            }
        }));

    }

    private void showImagePickerDialog() {
        String options[] = {"Camera", "Gallery"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose an image");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(i == 0){
                    if(!checkCameraPermission()){
                        requestCameraPermission();
                    } else{
                        pickFromCamera();
                    }
                }else if (i == 1){
                    if(!checkStoragePermission()){
                        requestStoragePermission();
                    }else{
                        pickFromGallery();
                    }
                }
            }
        }).create().show();
    }

    private void pickFromGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent,IMAGE_FROM_GALLERY);
    }

    private void pickFromCamera() {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE,"Image Title");
        values.put(MediaStore.Images.Media.DESCRIPTION,"Image Detail");

        imageUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,values);

        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);

        startActivityForResult(cameraIntent,IMAGE_FROM_CAMERA);
    }

    private void saveData() {
        name = nameEdt.getText().toString();
        location = locationEdt.getText().toString();
        date = dateEdt.getText().toString();
        parking = parkingEdt.getText().toString();
        length = lengthEdt.getText().toString();
        difficult = difficultEdt.getText().toString();
        description = descriptionEdt.getText().toString();

        //check name
        if(!name.isEmpty() || !location.isEmpty() || !date.isEmpty() || !parking.isEmpty() || !length.isEmpty() || !difficult.isEmpty() || !description.isEmpty()){

            //save data in sqlite
            long id = dbHelper.insertHiking(
                    ""+imageUri,
                    ""+name,
                    ""+location,
                    ""+date,
                    ""+parking,
                    ""+length,
                    ""+difficult,
                    ""+description
            );

            //check input data successfully
            Toast.makeText(getApplicationContext(), "Add Successfully",Toast.LENGTH_SHORT).show();

        }else{
            Toast.makeText(getApplicationContext(), "Nothing to show", Toast.LENGTH_SHORT).show();
        }
    }

    //back button event
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }


    private boolean checkCameraPermission(){
        boolean result = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == (PackageManager.PERMISSION_GRANTED);
        boolean result_1 = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED);
        return result & result_1;
    }
    private void requestCameraPermission(){
        //handle request permission
        ActivityCompat.requestPermissions(this,cameraPermission,CAMERA_PERMISSION);
    }

    private boolean checkStoragePermission(){
        boolean result_1 = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED);
        return result_1;
    }
    private void requestStoragePermission(){
        ActivityCompat.requestPermissions(this,storagePermission,STORAGE_PERMISSION);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode){
            case CAMERA_PERMISSION:
                if(grantResults.length >0){
                    boolean cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean storageAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;

                    if (cameraAccepted && storageAccepted){
                        pickFromCamera();
                    }else{
                        Toast.makeText(getApplicationContext(),"Camera & Storage permission required", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            case STORAGE_PERMISSION:
                if(grantResults.length >0){

                    boolean storageAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;

                    if (storageAccepted){
                        pickFromGallery();
                    }else{
                        Toast.makeText(getApplicationContext(),"Camera & Storage permission required", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
        }
    }


}