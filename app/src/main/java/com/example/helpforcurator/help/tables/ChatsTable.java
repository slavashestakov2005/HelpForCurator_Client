package com.example.helpforcurator.help.tables;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class ChatsTable {
    private static String DB_TABLE = "chats";
    private static String COLUMN_ID = "id", COLUMN_NAME = "name", COLUMN_TIME = "time";
    public static int INDEX_ID = 0, INDEX_NAME = 1, INDEX_TIME = 2;

    public static Cursor select(SQLiteDatabase db, int id){
        return db.rawQuery("SELECT * FROM " + DB_TABLE + " WHERE " + COLUMN_ID + " = " + id, null);
    }

    public static void updateChat(SQLiteDatabase db, int id, String name, String time){
        if (id < 1 || name == null || name.trim().equals("") || time == null || time.trim().equals("")) return;
        Log.i("TAG", "updateChat() with id = " + id + " name = " + name + " time = " + time);
        ContentValues updatedValues = new ContentValues();
        updatedValues.put(COLUMN_ID, id);
        updatedValues.put(COLUMN_NAME, name);
        updatedValues.put(COLUMN_TIME, time);
        db.update(DB_TABLE, updatedValues, COLUMN_ID + " = " + id, null);
    }

    public static void insertChat(SQLiteDatabase db, int id, String name, String time){
        Log.i("TAG", "insertChat() with id = " + id + " name = " + name + " time = " + time);
        ContentValues newValues = new ContentValues();
        newValues.put(COLUMN_ID, id);
        newValues.put(COLUMN_NAME, name);
        newValues.put(COLUMN_TIME, time);
        db.insert(DB_TABLE, null, newValues);
    }
}