package com.example.jokesapif95107;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class UsersDBHelpers extends SQLiteOpenHelper {

    private static final String DB_NAME = "users.db";
    private static final String TABLE_NAME = "users_table";
    private static final String COL_1 = "ID";
    private static final String COL_2 = "USER";
    private static final String COL_3 = "PASSWORD";

    private static final String JOKES_TABLE = "jokes_table";
    private static final String JOKES_COL_1 = "USERNAME";
    private static final String JOKES_COL_2 = "JOKE";

    public UsersDBHelpers(Context context) {
        super(context, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase SQLiteDB) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_2 + " TEXT, " + COL_3 + " TEXT)";
        String createJokesTable = "CREATE TABLE " + JOKES_TABLE + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                JOKES_COL_1 + " TEXT, " + JOKES_COL_2 + " TEXT)";
        SQLiteDB.execSQL(createTable);
        SQLiteDB.execSQL(createJokesTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase SQLiteDB, int oldVer, int newVer) {
        SQLiteDB.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        SQLiteDB.execSQL("DROP TABLE IF EXISTS " + JOKES_TABLE);
        onCreate(SQLiteDB);
    }

    public boolean addData(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, username);
        contentValues.put(COL_3, password);

        long result = db.insert(TABLE_NAME, null, contentValues);

        return result != -1;
    }

    public boolean addJokeData(String username, String joke) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(JOKES_COL_1, username);
        contentValues.put(JOKES_COL_2, joke);
        Cursor cursor = db.rawQuery("SELECT " + "*" + " FROM " + JOKES_TABLE + " WHERE " + JOKES_COL_1 + "=?" + " and " + JOKES_COL_2 + "=?", new String[]{username, joke});

        if (cursor.getCount() <= 0) {
            long result = db.insert(JOKES_TABLE, null, contentValues);
            return result != -1;
        }

        return false;
    }

    public ArrayList<String> getUserSavedJokes(String username) {
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<String> savedJokes = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT " + "*" + " FROM " + JOKES_TABLE + " WHERE " + JOKES_COL_1 + "=?", new String[]{username});
        try {

            if (!cursor.moveToFirst())
                return null;

            do {
                savedJokes.add(cursor.getString(2));
            } while (cursor.moveToNext());
        } finally {
            cursor.close();

        }
        return savedJokes;
    }


    public int getCurrentUser(String username) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + COL_1 + " FROM " + TABLE_NAME + " WHERE " + COL_2 + "=?", new String[]{username});
        int id = -1;
        if (cursor.moveToFirst()) id = cursor.getInt(0);
        cursor.close();
        db.close();
        return id;
    }

    public int getCurrentPasswordAndUser(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + COL_1 + " FROM " + TABLE_NAME + " WHERE " + COL_2 + "=?" + " and " + COL_3 + "=?", new String[]{username, password});
        int id = -1;
        if (cursor.moveToFirst()) id = cursor.getInt(0);
        cursor.close();
        db.close();
        return id;
    }

    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
    }
}
