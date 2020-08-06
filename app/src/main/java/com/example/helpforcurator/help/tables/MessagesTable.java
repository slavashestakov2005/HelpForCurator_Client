package com.example.helpforcurator.help.tables;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class MessagesTable {
    private static String DB_TABLE = "messages";
    private static String COLUMN_ID_CHAT = "id_chat",   // int  NOT NULL
            COLUMN_ID_USER = "id_user",                 // int  NOT NULL    PK
            COLUMN_TEXT = "text",                       // text
            COLUMN_TIME = "time";                       // text NOT NULL    PK
    public static int INDEX_ID_CHAT = 0, INDEX_ID_USER = 1, INDEX_TEXT = 2, INDEX_TIME = 3;

    public static Cursor selectAll(SQLiteDatabase db, int id_chat){
        return db.rawQuery("SELECT * FROM " + DB_TABLE + " WHERE " + COLUMN_ID_CHAT + " = " + id_chat, null);
    }

    public static void insertMessage(SQLiteDatabase db, int id_chat, int id_user, String text, String time){
        Log.e("FONE", "add to " + id_chat + "; text = " + text);
        ContentValues newValues = new ContentValues();
        newValues.put(COLUMN_ID_CHAT, id_chat);
        newValues.put(COLUMN_ID_USER, id_user);
        newValues.put(COLUMN_TEXT, text);
        newValues.put(COLUMN_TIME, time);
        db.insert(DB_TABLE, null, newValues);
    }

    public static void clear(SQLiteDatabase db, int id_chat){
        db.delete(DB_TABLE, COLUMN_ID_CHAT + " = ?", new String[]{"" + id_chat});
    }

    public static int getCount(SQLiteDatabase db, int id_chat){
        return selectAll(db, id_chat).getCount();
    }
}