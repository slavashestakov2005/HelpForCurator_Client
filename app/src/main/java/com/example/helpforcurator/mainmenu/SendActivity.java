/**
 * Активность со списком чатов.
 * Каждый чат ведёт в chat.ChatActivity.
 * FAB со знаком "+" ведёт chat.CreateNewChatActivity.
 * Здесь работает ресивер UPDATE_LIST_CHAT.
 * Работа ресивера отображается в Log.i.FONE.
 * **/

package com.example.helpforcurator.mainmenu;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.helpforcurator.R;
import com.example.helpforcurator.chat.ChatActivity;
import com.example.helpforcurator.chat.CreateNewChatActivity;
import com.example.helpforcurator.help.ChatItem;
import com.example.helpforcurator.help.ChatListAdapter;
import com.example.helpforcurator.help.CurrentChat;
import com.example.helpforcurator.help.CurrentSession;
import com.example.helpforcurator.help.FoneService;
import com.example.helpforcurator.help.tables.ChatUserTable;
import com.example.helpforcurator.help.tables.ChatsTable;
import com.example.helpforcurator.help.tables.UsersTable;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class SendActivity extends AppCompatActivity {
    /** view элемненты **/
    private FloatingActionButton create_chat;
    private ListView listChat;
    private List<ChatItem> data;
    private ChatListAdapter adapter;
    /** ресивер **/
    private UpdateReceiver upd_res;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        data = new ArrayList<>();
        /** получение view **/
        setContentView(R.layout.activity_send);
        create_chat = (FloatingActionButton) findViewById(R.id.create_chat);
        listChat = (ListView) findViewById(R.id.chat_list);
        /** обработка нажатий **/
        create_chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SendActivity.this, CreateNewChatActivity.class);
                startActivity(intent);
            }
        });
        /** создаём ресивер UPDATE_LIST_CHAT **/
        createReceiver();
        insertSessionToDataBase();
        /** изменение шапки **/
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onStart() {
        /** создаём список чатов с адаптером **/
        getData(data);
        adapter = new ChatListAdapter(getApplicationContext(), data);
        listChat.setAdapter(adapter);
        /** обработка нажатия на чат **/
        listChat.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                Object o = listChat.getItemAtPosition(position);
                ChatItem item = (ChatItem) o;
                Toast.makeText(getApplicationContext(), "Selected : " + item, Toast.LENGTH_LONG).show();
                CurrentChat.setChatName(item.getName());
                CurrentChat.setId_chat(item.getId_chat());
                Intent intent = new Intent(SendActivity.this, ChatActivity.class);
                startActivity(intent);
            }
        });
        super.onStart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        /** удаляем ресивер **/
        unregisterReceiver(upd_res);
    }

    /** метод для загрузки списка чатов из локальной БД **/
    private void getData(List<ChatItem> data) {
        data.clear();
        Cursor chats = ChatUserTable.select(FoneService.localDB, CurrentSession.getUserId());
        if (chats == null || chats.getCount() == 0) return;
        chats.moveToFirst();
        while (!chats.isAfterLast()){
            // получаем по ID имя
            final int id_chat = chats.getInt(0);
            Cursor cursor = ChatsTable.select(FoneService.localDB, id_chat);
            cursor.moveToFirst();
            final String name = cursor.getString(ChatsTable.INDEX_NAME);
            data.add(new ChatItem(id_chat, name));
            chats.moveToNext();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            default:
                finish();
                return true;
        }
    }

    public class UpdateReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.i("FONE", "FoneProcess нашёл новый чат");
            updateList();
        }
    }

    private void createReceiver(){
        upd_res = new UpdateReceiver();
        registerReceiver(upd_res, new IntentFilter("com.example.helpforcurator.action.UPDATE_LIST_CHAT"));
    }

    private void insertSessionToDataBase(){
        if(!UsersTable.contain(FoneService.localDB, CurrentSession.getUserId())){
            UsersTable.insertUser(FoneService.localDB, CurrentSession.getUserId(), CurrentSession.getName(), CurrentSession.getSurname());
            CurrentSession.setChatTimeUpdate("0");
        }
        else{
            Cursor cursor = UsersTable.select(FoneService.localDB, CurrentSession.getUserId());
            cursor.moveToFirst();
            CurrentSession.setChatTimeUpdate(cursor.getString(UsersTable.INDEX_TIME));
        }
    }

    private void updateList(){
        getData(data);
        adapter.notifyDataSetChanged();
    }
}
