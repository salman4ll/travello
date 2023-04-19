package com.example.project;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;

public class DatabaseUserHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "travello";
    private static final String TABLE_USER = "tbl_users";

    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_ROLE = "role";

    public DatabaseUserHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_USER + "(" + KEY_ID + " TEXT PRIMARY KEY," + KEY_NAME + " TEXT, " + KEY_EMAIL + " TEXT," + KEY_ROLE + " TEXT" + ")";
        db.execSQL(CREATE_USERS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        onCreate(db);
    }

    public void addRecord(UserModels userModels) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID, userModels.getId());
        values.put(KEY_NAME, userModels.getName());
        values.put(KEY_EMAIL, userModels.getEmail());
        values.put(KEY_ROLE, userModels.getRole());

        db.insert(TABLE_USER, null, values);
        db.close();
    }

    public UserModels getUser(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USER, new String[]{KEY_ID, KEY_NAME, KEY_EMAIL, KEY_ROLE}, null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                UserModels user = new UserModels(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3));
                return user;
            }while (cursor.moveToNext());
        }
        return null;
    }

    public int getUserModelCount() {
        String countQuery = "SELECT * FROM " + TABLE_USER;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        try {
            return cursor.getCount();
        }finally {
            cursor.close();
        }

    }

    public int updateUser(UserModels user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, user.getName());
        values.put(KEY_EMAIL, user.getEmail());
        values.put(KEY_ROLE, user.getRole());

        return db.update(TABLE_USER, values, KEY_ID + " =?", new String[] {user.getId() });
    }

    public void deleteUser(UserModels user) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_USER, KEY_ID + " = ?", new String[] { user.getId() });
        db.close();
    }

}
