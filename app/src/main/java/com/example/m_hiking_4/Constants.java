package com.example.m_hiking_4;

public class Constants {
    public static final String DATABASE_NAME = " HIKING_DB ";
    public static final int DATABASE_VERSION = 1;
    public static final  String TABLE_NAME = " HIKING_TABLE ";
    public static final String C_ID = "ID";
    public static final String C_IMAGE = "IMAGE";
    public static final String C_NAME = "NAME";
    public static final String C_LOCATION = "LOCATION";
    public static final String C_DATE = "DATE";
    public static final String C_PARKING = "PARKING";
    public static final String C_LENGTH = "LENGTH";
    public static final String C_DIFFICULT = "DIFFICULT";
    public static final String C_DESCRIPTION = "DESCRIPTION";

    public static final String CREATE_TABLE = " CREATE TABLE " + TABLE_NAME + " ( "
            + C_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + C_IMAGE + "TEXT,"
            + C_NAME + "TEXT,"
            + C_LOCATION + "TEXT,"
            + C_DATE + "TEXT,"
            + C_PARKING + "TEXT,"
            + C_LENGTH + "TEXT,"
            + C_DIFFICULT + "TEXT,"
            + C_DESCRIPTION + "TEXT"
            + " );";

}
