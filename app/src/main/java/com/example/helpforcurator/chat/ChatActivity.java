package com.example.helpforcurator.chat;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.helpforcurator.R;
import com.example.helpforcurator.help.ConectionHealper;
import com.example.helpforcurator.help.CurrentChat;
import com.example.helpforcurator.help.CurrentSession;
import com.example.helpforcurator.help.FoneService;
import com.example.helpforcurator.help.tables.MessagesTable;
import com.example.helpforcurator.help.tables.UsersTable;

import java.util.HashMap;

public class ChatActivity extends AppCompatActivity {
    private LinearLayout container;
    private MyMessage myMessage = null;
    private Message message = null;
    EditText text;
    Button send;
    UpdateReceiver upd_res;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        /** views **/
        container = (LinearLayout) findViewById(R.id.layout);
        text = (EditText) findViewById(R.id.text);
        send = (Button) findViewById(R.id.send);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String _text = text.getText().toString().trim();
                if (!_text.equals("")){
                    new SendMessageAsyncTask(CurrentChat.getId_chat(), CurrentChat.getChatName(), CurrentSession.getUserId(), _text).execute();
                    text.setText("");
                }
            }
        });
        // Создаём и регистрируем широковещательный приёмник
        upd_res = new UpdateReceiver();
        registerReceiver(upd_res, new IntentFilter("com.example.helpforcurator.action.UPDATE_Chat"));
    }

    public void create_lv(){
        container.removeAllViews();
        Cursor messages = MessagesTable.selectAll(FoneService.localDB, CurrentChat.getId_chat());
        if (messages == null || messages.getCount() == 0) return;
        messages.moveToFirst();
        while (!messages.isAfterLast()) {
            final int id_user = messages.getInt(MessagesTable.INDEX_ID_USER);
            final String text = messages.getString(MessagesTable.INDEX_TEXT);
            final String time = messages.getString(MessagesTable.INDEX_TIME);
            if (id_user == CurrentSession.getUserId()){
                myMessage = new MyMessage(getApplicationContext());
                myMessage.setMessageText(text);
                myMessage.setMessageTime(time);
                container.addView(myMessage);
                myMessage = null;
            }
            else{
                final Cursor author = UsersTable.select(FoneService.localDB, id_user);
                final String surname_name;
                if (author == null || author.getCount() == 0) surname_name = "" + id_user;
                else{
                    author.moveToFirst();
                    surname_name = author.getString(UsersTable.INDEX_SURNAME) + " " + author.getString(UsersTable.INDEX_NAME);
                }
                author.close();
                message = new Message(getApplicationContext());
                message.setMessageAuthor(surname_name);
                message.setMessageText(text);
                message.setMessageTime(time);
                container.addView(message);
                message = null;
            }
            messages.moveToNext();
        }
        messages.close();
        final ScrollView scrollview = ((ScrollView) findViewById(R.id.scroll));
        scrollview.post(new Runnable() {
            @Override
            public void run() {
                scrollview.fullScroll(ScrollView.FOCUS_DOWN);
            }
        });
    }

    @Override
    protected void onResume() {
        create_lv();
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(upd_res);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_chat, menu);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setTitle(CurrentChat.getChatName());
        actionbar.setDisplayHomeAsUpEnabled(true);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                Intent intent = new Intent(ChatActivity.this, ChatSettingsActivity.class);
                startActivityForResult(intent, 1);
                return true;
            default:
                finish();
                return true;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) return;
        String result = data.getStringExtra("result");
        if (result != null && result.equals("exit")) finish();
    }

    class SendMessageAsyncTask extends AsyncTask<String, String, String> {
        int id_chat, id_author;
        String _text, _name_chat;
        String answer, server = ConectionHealper.getUrl() + "/send";

        public SendMessageAsyncTask(int id_chat, String _name_chat, int id_author, String _text) {
            this.id_chat = id_chat;
            this._name_chat = _name_chat;
            this.id_author = id_author;
            this._text = _text;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            HashMap<String, String> postDataParams = new HashMap<String, String>();
            postDataParams.put("id_chat", "" + id_chat);
            postDataParams.put("id_author", "" + id_author);
            postDataParams.put("text", _text);
            answer = ConectionHealper.performGetCall(server, postDataParams);
            return null;
        }

        @Override
        protected void onPostExecute(String res) {
            super.onPostExecute(res);
        }
    }

    public class UpdateReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.i("TAG", "FoneProcess нашёл новые сообщения");
            create_lv();
        }
    }
}