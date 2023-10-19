package com.example.m_hiking_4;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DbHelper extends SQLiteOpenHelper {
    public DbHelper(@Nullable Context context) {
        super(context, Constants.DATABASE_NAME, null, Constants.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(Constants.CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS "+Constants.TABLE_NAME);

        onCreate(db);

    }

    public long insertHiking(String image, String name, String location, String date, String parking, String length, String difficult, String description){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();


        contentValues.put(Constants.C_IMAGE,image);
        contentValues.put(Constants.C_NAME,name);
        contentValues.put(Constants.C_LOCATION,location);
        contentValues.put(Constants.C_DATE,date);
        contentValues.put(Constants.C_PARKING,parking);
        contentValues.put(Constants.C_LENGTH,length);
        contentValues.put(Constants.C_DIFFICULT,difficult);
        contentValues.put(Constants.C_DESCRIPTION,description);

        // Insert data in rows, it will return id of the record
        long id = db.insert(Constants.TABLE_NAME, null,contentValues);

        //close database
        db.close();

        //return id
        return id;

    }

    //get data
    public ArrayList<ModelHiking> getAllData(){
        //create ArrrayList
        ArrayList<ModelHiking> arrayList = new ArrayList<>();
        String selectQuery =  "SELECT * FROM " + Constants.TABLE_NAME;

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery,null);

        if (cursor.moveToFirst()){
            do {
                ModelHiking modelHiking = new ModelHiking(
                            ""+cursor.getInt(cursor.getColumnIndexOrThrow(Constants.C_ID)),
                        ""+cursor.getString(cursor.getColumnIndexOrThrow(Constants.C_IMAGE)),
                        ""+cursor.getString(cursor.getColumnIndexOrThrow(Constants.C_NAME)),
                        ""+cursor.getString(cursor.getColumnIndexOrThrow(Constants.C_LOCATION)),
                        ""+cursor.getString(cursor.getColumnIndexOrThrow(Constants.C_DATE)),
                        ""+cursor.getString(cursor.getColumnIndexOrThrow(Constants.C_PARKING)),
                        ""+cursor.getString(cursor.getColumnIndexOrThrow(Constants.C_LENGTH)),
                        ""+cursor.getString(cursor.getColumnIndexOrThrow(Constants.C_DIFFICULT)),
                        ""+cursor.getString(cursor.getColumnIndexOrThrow(Constants.C_DESCRIPTION))
                );
                arrayList.add(modelHiking);
            }while (cursor.moveToNext());
        }
        db.close();
        return arrayList;
    }
}
