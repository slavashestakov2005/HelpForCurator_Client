/**
 * Активность для добавления нового чата.
 * Есть только настройка имени.
 * Обращение к серверу ".../create_chat".
 * Обращение к серверу ".../insert_to_chat".
 * **/

package com.example.helpforcurator.chat;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.helpforcurator.R;
import com.example.helpforcurator.help.ConnectionHelper;
import com.example.helpforcurator.help.CurrentChat;
import com.example.helpforcurator.help.CurrentSession;

import java.util.HashMap;

public class CreateNewChatActivity extends AppCompatActivity {
    /** view элемненты **/
    private EditText name;
    private TextView errors;
    private Button create;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /** получение view **/
        setContentView(R.layout.activity_create_new_chat);
        name = (EditText) findViewById(R.id.name);
        errors = (TextView) findViewById(R.id.errors);
        create = (Button) findViewById(R.id.create_chat);
        /** обработка нажатий **/
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = name.getText().toString().trim();
                if (text.equals("")){
                    name.setBackgroundColor(getColor(R.color.error));
                    errors.setText("Пустое имя чата");
                } else {
                    new CreateChatAsyncTask(text).execute();
                }
            }
        });
        /** изменение шапки **/
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            default:
                finish();
                return true;
        }
    }

    /** AsyncTask для создания нового чата **/
    class CreateChatAsyncTask extends AsyncTask<String, String, String> {
        String _name;
        String answer, server = ConnectionHelper.getUrl() + "/create_chat";

        public CreateChatAsyncTask(String _name) {
            this._name = _name;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            HashMap<String, String> postDataParams = new HashMap<String, String>();
            postDataParams.put("name", _name);
            answer = ConnectionHelper.performGetCall(server, postDataParams);
            return null;
        }

        @Override
        protected void onPostExecute(String res) {
            super.onPostExecute(res);
            if (answer.equals("error")){
                name.setBackgroundColor(getColor(R.color.error));
                errors.setText("Чат уже существует");
            }
            else{
                Toast.makeText(getApplicationContext(), "Чат создан с номером " + answer, Toast.LENGTH_SHORT).show();
                new InsertToChatAsyncTask(CurrentSession.getUserId(), Integer.parseInt(answer), _name).execute();
            }
        }
    }

    /** AsyncTask для добавления себя в чат при удочном создании нового чата **/
    class InsertToChatAsyncTask extends AsyncTask<String, String, String> {
        int _user_id, _chat_id;
        String _chat_name;
        String answer, server = ConnectionHelper.getUrl() + "/insert_to_chat";

        public InsertToChatAsyncTask(int _user_id, int _chat_id, String _chat_name) {
            this._user_id = _user_id;
            this._chat_id = _chat_id;
            this._chat_name = _chat_name;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            HashMap<String, String> postDataParams = new HashMap<String, String>();
            postDataParams.put("id_chat", "" + _chat_id);
            postDataParams.put("id_user", "" + _user_id);
            answer = ConnectionHelper.performGetCall(server, postDataParams);
            return null;
        }

        @Override
        protected void onPostExecute(String res) {
            super.onPostExecute(res);
            Toast.makeText(getApplicationContext(), "Вы добавлены", Toast.LENGTH_SHORT).show();
            CurrentChat.setChatName(_chat_name);
            CurrentChat.setId_chat(_chat_id);
            Intent intent = new Intent(CreateNewChatActivity.this, ChatActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
