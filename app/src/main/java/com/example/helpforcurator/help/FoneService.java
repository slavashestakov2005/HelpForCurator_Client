/**
 * Класс фонового сервиса.
 * За основу взят пример с https://habr.com/ru/post/269135.
 * Этот класс:
 * 1. Обновляет список сообщений текущего чата (.../messages_chat_after и UPDATE_CHAT).
 * 2. Обновляет список чатов текущего пользователя. (.../get_chats и UPDATE_LIST_CHAT).
 * 3. Спит 15 секунд и заново отправляет запросы на сервер.
 * Также он изменяет время отправки сообщений с мс. в привычный вид "dd.mm.yyyy hh.mm".
 * Обращение к серверу ".../messages_chat_after".
 * Обращение к серверу ".../get_chats".
 * **/

package com.example.helpforcurator.help;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.example.helpforcurator.MainActivity;
import com.example.helpforcurator.R;
import com.example.helpforcurator.help.tables.ChatUserTable;
import com.example.helpforcurator.help.tables.ChatsTable;
import com.example.helpforcurator.help.tables.MessagesTable;
import com.example.helpforcurator.help.tables.UsersTable;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class FoneService extends Service {
    static public SQLiteDatabase localDB = null;
    Thread thr;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public void onStart(Intent intent, int startId) {
        /** Подключение к локальной БД **/
        if (localDB == null) {
            DataBaseHelper mDBHelper = new DataBaseHelper(this);
            try {
                mDBHelper.updateDataBase();
            } catch (IOException mIOException) {
                throw new Error("UnableToUpdateDatabase");
            }
            try {
                localDB = mDBHelper.getWritableDatabase();
            } catch (SQLException mSQLException) {
                throw mSQLException;
            }
        }
        /** Создание notification и его показ в трее**/
        String channel;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            channel = createChannel();
        else {
            channel = "";
        }
        Intent iN = new Intent(getApplicationContext(), MainActivity.class);
        iN.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pI = PendingIntent.getActivity(getApplicationContext(),
                0, iN, PendingIntent.FLAG_CANCEL_CURRENT);
        NotificationCompat.Builder bI = new NotificationCompat.Builder(
                getApplicationContext(), channel);
        bI.setContentIntent(pI)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setLargeIcon(
                        BitmapFactory.decodeResource(getApplicationContext()
                                .getResources(), R.drawable.ic_launcher_background))
                .setAutoCancel(true)
                .setContentTitle(getResources().getString(R.string.app_name))
                .setContentText("Получаю сообщения от сервера");
        Notification notification = bI.build();
        startForeground(101, notification);
        startLoop();
    }

    /** Регистрация процесса, если API 26+ (Android 8.0+) **/
    @NonNull
    @TargetApi(26)
    private synchronized String createChannel() {
        NotificationManager mNotificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        String name = "snap map fake location ";
        int importance = NotificationManager.IMPORTANCE_LOW;
        NotificationChannel mChannel = new NotificationChannel("snap map channel", name, importance);
        mChannel.enableLights(true);
        mChannel.setLightColor(Color.BLUE);
        if (mNotificationManager != null) {
            mNotificationManager.createNotificationChannel(mChannel);
        } else {
            stopSelf();
        }
        return "snap map channel";
    }

    /** Поток обращенй к серверу **/
    private void startLoop() {
        thr = new Thread(new Runnable() {
            public void run() {
                initAsyncChatsUpdate();
                while (CurrentSession.isProgramStart()) {
                    getChats();
                    try {
                        Thread.sleep(15000);
                    } catch (Exception e) {}
                }
                stopSelf();
            }

            private void initAsyncChatsUpdate(){
                Cursor cursor = ChatUserTable.select(localDB, CurrentSession.getUserId());
                if (cursor != null && cursor.getCount() > 0){
                    cursor.moveToFirst();
                    while (!cursor.isAfterLast()){
                        final int chat_id = cursor.getInt(0);
                        Log.i("CHAT", chat_id + "");
                        Thread thread = new Thread(new Runnable() {
                            final int _id_chat = chat_id;
                            final int user_id = CurrentSession.getUserId();
                            @Override
                            public void run() {
                                while (CurrentSession.getUserId() == user_id && CurrentSession.isProgramStart()){
                                    getMessagesInChat(_id_chat);
                                    try {
                                        Thread.sleep(1000);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        });
                        thread.setDaemon(true);
                        thread.start();
                        cursor.moveToNext();
                    }
                }
            }
        });
        thr.setDaemon(true);
        thr.start();
    }

    private String convertTime(String _time){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date(Long.parseLong(_time)));
        final StringBuilder builder = new StringBuilder();
        int time = calendar.get(calendar.DAY_OF_MONTH);
        if (time < 10) builder.append("0");
        builder.append(time).append(".");
        time = calendar.get(calendar.MONTH) + 1;
        if (time < 10) builder.append("0");
        builder.append(time).append(".");
        time = calendar.get(calendar.YEAR);
        builder.append(time).append(" ");
        time = calendar.get(calendar.AM_PM) * 12 + calendar.get(calendar.HOUR);
        if (time < 10) builder.append("0");
        builder.append(time).append(".");
        time = calendar.get(calendar.MINUTE);
        if (time < 10) builder.append("0");
        builder.append(time);
        return builder.toString();
    }

    private void getMessagesInChat(int _id_chat){
        // создадим запрос и получим ответ
        final String server = ConnectionHelper.getUrl() + "/messages_chat_after";
        HashMap<String, String> postDataParams = new HashMap<String, String>();
        Cursor cursor = ChatsTable.select(localDB, _id_chat);
        cursor.moveToFirst();
        String _time = cursor.getString(ChatsTable.INDEX_TIME);
        String _name_chat = cursor.getString(ChatsTable.INDEX_NAME);
        postDataParams.put("id_chat", "" + _id_chat);
        postDataParams.put("time", "" + _time);
        String answer = ConnectionHelper.performGetCall(server, postDataParams);
        // работаем с ответом
        if (answer != null && !answer.equals("")) {
            String[] mas = answer.split(" \\| ");
            // чат обновлялся
            _time = mas[0];
            ChatsTable.updateChat(localDB, _id_chat, _name_chat, _time);
            // парсим ответ
            int length = -1, id_chat = -1, id_user = -1;
            boolean b = false;
            boolean isTime = true;
            String text = "", time = "";
            for(String word : mas){
                if (isTime){
                    isTime = false;
                    continue;
                }
                if (length != -1 && id_chat != -1 && b && !time.equals("") && text.length() >= length){
                    // если всё распарсили -> добавить в БД
                    MessagesTable.insertMessage(localDB, id_chat, id_user, text, convertTime(time));
                    // продолжить парсинг
                    length = -1; id_chat = -1; id_user = -1; text = ""; time = ""; b = false;
                }
                if (length == -1) length = Integer.parseInt(word);
                else if (id_chat == -1) id_chat = Integer.parseInt(word);
                else if (!b) { id_user = Integer.parseInt(word); b = true; }
                else if (time.equals("")) time = word;
                else if (text.length() < length){
                    text += word;
                    if (text.length() < length) text += " | ";
                }
            }
            if (length != -1 && id_chat != -1 && b && !time.equals("") && text.length() >= length) {
                // если всё распарсили -> добавить в БД
                MessagesTable.insertMessage(localDB, id_chat, id_user, text, convertTime(time));
            }
            // обновить view
            if (mas.length > 1) sendBroadcast(new Intent("com.example.helpforcurator.action.UPDATE_CHAT"));
        }
    }

    private void getChats(){
        final String server = ConnectionHelper.getUrl() + "/get_chats";
        // создадим запрос и получим ответ
        final int id = CurrentSession.getUserId();
        String name = CurrentSession.getName();
        String surname = CurrentSession.getSurname();
        Cursor cursor = UsersTable.select(localDB, id);
        String time;
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            time = cursor.getString(UsersTable.INDEX_TIME);
        } else time = "0";
        HashMap<String, String> postDataParams = new HashMap<String, String>();
        postDataParams.put("id_user", "" + id);
        postDataParams.put("time", time);
        String answer = ConnectionHelper.performGetCall(server, postDataParams);
        // работаем с ответом
        if (answer != null && !answer.equals("")) {
            String[] mas = answer.split(" \\| ");
            time = mas[0];
            UsersTable.updateUser(localDB, id, name, surname, time);
            if (CurrentSession.getUserId() == id) CurrentSession.setChatTimeUpdate(time);
            if (mas.length < 2) return;
            for (int i = 2; i < mas.length; i += 2){
                final int id_chat = Integer.parseInt(mas[i - 1]);
                if (!ChatUserTable.contain(localDB, id_chat, id)){
                    // добавить в БД чат, time = "0", т.к. не обновлялся никогда
                    ChatsTable.insertChat(localDB, id_chat, mas[i], "0");
                    // добавим в БД наше наличие
                    ChatUserTable.insertChat(localDB, id_chat, id);
                    Thread thread = new Thread(new Runnable() {
                        final int _id_chat = id_chat;
                        final int user_id = CurrentSession.getUserId();
                        @Override
                        public void run() {
                            Log.i("CHAT", _id_chat + "");
                            while (CurrentSession.getUserId() == user_id && CurrentSession.isProgramStart()){
                                getMessagesInChat(_id_chat);
                                try {
                                    Thread.sleep(1000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    });
                    thread.setDaemon(true);
                    thread.start();
                }
            }
            // обновить view
            sendBroadcast(new Intent("com.example.helpforcurator.action.UPDATE_LIST_CHAT"));
        }
    }
}