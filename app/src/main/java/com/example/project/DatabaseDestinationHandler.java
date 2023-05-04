package com.example.project;

import android.app.Service;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class DatabaseDestinationHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "travello_1";
    private static final String TABLE_USER = "tbl_destinations";

    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_DESCRIPTION = "descrtion";
    private static final String KEY_LOCATION = "location";
    private static final String KEY_CATEGORY = "category";
    private static final String KEY_IMAGE = "image";
    private static final String KEY_RATING = "rating";


    public DatabaseDestinationHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_USER + "(" + KEY_ID + " TEXT PRIMARY KEY," + KEY_NAME + " TEXT, " + KEY_DESCRIPTION + " TEXT," + KEY_LOCATION + " TEXT," + KEY_CATEGORY + " TEXT,"+ KEY_IMAGE + " TEXT,"+KEY_RATING+" REAL"+ ")";
        db.execSQL(CREATE_USERS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        onCreate(db);
    }

    public void addRecord(DestinationModels destinationModels) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID, destinationModels.getId());
        values.put(KEY_NAME, destinationModels.getName());
        values.put(KEY_DESCRIPTION, destinationModels.getDescription());
        values.put(KEY_LOCATION, destinationModels.getLocation());
        values.put(KEY_CATEGORY, destinationModels.getCategory());
        values.put(KEY_IMAGE, destinationModels.getImage());
        values.put(KEY_RATING, destinationModels.getRating());

        db.insert(TABLE_USER, null, values);
        db.close();
    }

    public DestinationModels getRecord(String id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USER, new String[]{KEY_ID, KEY_NAME, KEY_DESCRIPTION, KEY_LOCATION, KEY_CATEGORY, KEY_IMAGE}, KEY_ID + "=?", new String[] { id }, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                DestinationModels destination = new DestinationModels(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3),cursor.getString(4),cursor.getString(5),cursor.getFloat(6));
                return destination;
            }while (cursor.moveToNext());
        }
        return null;
    }

    public List<DestinationModels> getAllRecord() {
        List<DestinationModels> recordList = new ArrayList<DestinationModels>();
        String sqlectQuery = "SELECT * FROM " + TABLE_USER;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(sqlectQuery, null);

        while (cursor.moveToNext()) {
            DestinationModels destinationModels = new DestinationModels();

            destinationModels.setId(cursor.getString(0));
            destinationModels.setName(cursor.getString(1));
            destinationModels.setDescription(cursor.getString(2));
            destinationModels.setLocation(cursor.getString(3));
            destinationModels.setCategory(cursor.getString(4));
            destinationModels.setImage(cursor.getString(5));
            destinationModels.setRating(cursor.getFloat(6));
            recordList.add(destinationModels);
        }
        cursor.close();
        db.close();
        return recordList;
    }

    public List<DestinationModels> getByCategoryRecord(String category) {
        List<DestinationModels> recordList = new ArrayList<DestinationModels>();

        // Menggunakan placeholder atau argumen terpisah dalam query
        String sqlectQuery = "SELECT * FROM " + TABLE_USER + " WHERE " + KEY_CATEGORY + " = ?";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(sqlectQuery, new String[]{category}); // Menyediakan nilai parameter sebagai argumen terpisah

        while (cursor.moveToNext()) {
            DestinationModels destinationModels = new DestinationModels();

            destinationModels.setId(cursor.getString(0));
            destinationModels.setName(cursor.getString(1));
            destinationModels.setDescription(cursor.getString(2));
            destinationModels.setLocation(cursor.getString(3));
            destinationModels.setCategory(cursor.getString(4));
            destinationModels.setImage(cursor.getString(5));
            destinationModels.setRating(cursor.getFloat(6));
            recordList.add(destinationModels);
        }
        cursor.close();
        db.close();
        return recordList;
    }


    public int getRecordCount() {
        String countQuery = "SELECT * FROM " + TABLE_USER;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        try {
            return cursor.getCount();
        }finally {
            cursor.close();
        }
    }

    public int updateRecord(DestinationModels destinationModels) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, destinationModels.getName());
        values.put(KEY_DESCRIPTION, destinationModels.getDescription());
        values.put(KEY_LOCATION, destinationModels.getLocation());
        values.put(KEY_CATEGORY, destinationModels.getCategory());
        values.put(KEY_IMAGE, destinationModels.getImage());
        values.put(KEY_RATING, destinationModels.getRating());

        return db.update(TABLE_USER, values, KEY_ID + "=?", new String[] {destinationModels.getId() });
    }

    public void deleteRecord(DestinationModels destinationModels) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_USER, KEY_ID + " = ?", new String[] { destinationModels.getId() });
        db.close();
    }

}
