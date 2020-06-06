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
    FloatingActionButton create_chat;
    ListView listChat;
    List<ChatItem> data;
    ChatListAdapter adapter;
    UpdateReceiver upd_res;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send);
        create_chat = (FloatingActionButton) findViewById(R.id.create_chat);
        create_chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SendActivity.this, CreateNewChatActivity.class);
                startActivity(intent);
            }
        });
        // Создаём и регистрируем широковещательный приёмник
        upd_res = new UpdateReceiver();
        registerReceiver(upd_res, new IntentFilter("com.example.helpforcurator.action.UPDATE_LIST_CHAT"));
        if(!UsersTable.contain(FoneService.localDB, CurrentSession.getUserId())){
            UsersTable.insertUser(FoneService.localDB, CurrentSession.getUserId(), CurrentSession.getName(), CurrentSession.getSurname());
            CurrentSession.setChatTimeUpdate("0");
        }
        else{
            Cursor cursor = UsersTable.select(FoneService.localDB, CurrentSession.getUserId());
            cursor.moveToFirst();
            CurrentSession.setChatTimeUpdate(cursor.getString(UsersTable.INDEX_TIME));
        }
        // шапка
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onStart() {
        // список чатов
        data = new ArrayList<>();
        getData(data);  // сначала загрузим из БД
        listChat = (ListView) findViewById(R.id.chat_list);
        adapter = new ChatListAdapter(this, data);
        listChat.setAdapter(adapter);
        listChat.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                Object o = listChat.getItemAtPosition(position);
                ChatItem item = (ChatItem) o;
                Toast.makeText(getApplicationContext(), "Selected :" + " " + item, Toast.LENGTH_LONG).show();
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
        unregisterReceiver(upd_res);
    }

    private void create_lv() {
        getData(data);
        adapter.notifyDataSetChanged();
    }

    private void getData(List<ChatItem> data) {
        data.clear();
        // получим список чатов
        Cursor chats = ChatUserTable.select(FoneService.localDB, CurrentSession.getUserId());
        if (chats == null || chats.getCount() == 0) return;
        chats.moveToFirst();
        while (!chats.isAfterLast()){
            // получаем по ID имя
            final int id_chat = chats.getInt(0);
            Cursor cursor2 = ChatsTable.select(FoneService.localDB, id_chat);
            cursor2.moveToFirst();
            final String name = cursor2.getString(ChatsTable.INDEX_NAME);
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
            Log.i("TAG", "FoneProcess нашёл новый чат");
            create_lv();
        }
    }
}
