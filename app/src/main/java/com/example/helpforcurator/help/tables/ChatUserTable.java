package com.example.helpforcurator.help.tables;


import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class ChatUserTable {
    private static String DB_TABLE = "chat_user";
    private static String COLUMN_ID_CHAT = "id_chat", COLUMN_ID_USER = "id_user";
    public static int INDEX_ID_CHAT = 0, INDEX_ID_USER = 1;

    public static Cursor select(SQLiteDatabase db, int id_user){
        return db.rawQuery("SELECT " + COLUMN_ID_CHAT + " FROM " + DB_TABLE + " WHERE " + COLUMN_ID_USER + " = " + id_user, null);
    }

    public static void insertChat(SQLiteDatabase db, int id_chat, int id_user){
        ContentValues newValues = new ContentValues();
        newValues.put(COLUMN_ID_CHAT, id_chat);
        newValues.put(COLUMN_ID_USER, id_user);
        db.insert(DB_TABLE, null, newValues);
    }

    public static int getCount(SQLiteDatabase db, int id_user){
        return select(db, id_user).getCount();
    }

    public static void deleteChat(SQLiteDatabase db, int chat_id, int user_id) {
        db.delete(DB_TABLE, COLUMN_ID_CHAT + " = ? and " + COLUMN_ID_USER + " = ?", new String[]{"" + chat_id, "" + user_id});
    }
}