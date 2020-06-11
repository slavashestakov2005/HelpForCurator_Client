package com.example.helpforcurator.help.tables;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class UsersTable {
    private static String DB_TABLE = "users";
    private static String COLUMN_ID = "id",     // int  NOT NULL    PK
            COLUMN_NAME = "name",               // text
            COLUMN_SURNAME = "surname",         // text
            COLUMN_TIME = "time";               // text
    public static int INDEX_ID = 0, INDEX_NAME = 1, INDEX_SURNAME = 2, INDEX_TIME = 3;

    public static Cursor select(SQLiteDatabase db, int id_user){
        return db.rawQuery("SELECT * FROM " + DB_TABLE + " WHERE " + COLUMN_ID + " = " + id_user, null);
    }

    public static boolean contain(SQLiteDatabase db, int id_user){
        Cursor cursor = select(db, id_user);
        return cursor != null && cursor.getCount() > 0;
    }

    public static void insertUser(SQLiteDatabase db, int id_user, String name, String surname, String time){
        ContentValues newValues = new ContentValues();
        newValues.put(COLUMN_ID, id_user);
        newValues.put(COLUMN_NAME, name);
        newValues.put(COLUMN_SURNAME, surname);
        newValues.put(COLUMN_TIME, time);
        db.insert(DB_TABLE, null, newValues);
    }

    public static void insertUser(SQLiteDatabase db, int id_user, String name, String surname){
        insertUser(db, id_user, name, surname, "0");
    }

    public static void updateUser(SQLiteDatabase db, int id_user, String name, String surname, String time){
        if (id_user < 1 || name == null || name.trim().equals("") || surname == null || surname.trim().equals("") ||
                time == null || time.trim().equals("")) return;
        Log.i("TAG", "updateUser() with id = " + id_user + " name = " + name + " surname = " + surname + "time = " + time);
        ContentValues updatedValues = new ContentValues();
        updatedValues.put(COLUMN_ID, id_user);
        updatedValues.put(COLUMN_NAME, name);
        updatedValues.put(COLUMN_SURNAME, surname);
        updatedValues.put(COLUMN_TIME, time);
        db.update(DB_TABLE, updatedValues, COLUMN_ID + " = " + id_user, null);
    }
}