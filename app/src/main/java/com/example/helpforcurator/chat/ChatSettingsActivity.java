/**
 * Активность с настройками чата.
 * Работают:
 * 1. Выход из чата.
 * 2. Добавление в чат.
 * 3. Удаление истории (возможно нет так, как нужно).
 * Обращение к серверу ".../insert_to_chat".
 * Обращение к серверу ".../delete_user_from_chat".
 * Обращение к серверу ".../who_in_chat".
 * Обращение к серверу ".../get_user_info".
 * **/

package com.example.helpforcurator.chat;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.InputType;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.helpforcurator.R;
import com.example.helpforcurator.help.ConnectionHelper;
import com.example.helpforcurator.help.CurrentChat;
import com.example.helpforcurator.help.CurrentSession;
import com.example.helpforcurator.help.FoneService;
import com.example.helpforcurator.help.tables.ChatUserTable;
import com.example.helpforcurator.help.tables.MessagesTable;
import com.example.helpforcurator.help.tables.UsersTable;

import java.util.ArrayList;
import java.util.HashMap;

public class ChatSettingsActivity extends AppCompatActivity{
    /** видимые настройки **/
    private boolean showOutDialog = false, showInputDialog = false, showClearDialog = false;
    /** view элементы **/
    private ListView userList;
    private TextView out, add, clear, sound;
    private ProgressBar progressBar;
    private ArrayList<HashMap<String, String>> arrayList;
    private HashMap<String, String> map;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /** получение view **/
        setContentView(R.layout.activity_chat_settings);
        userList = (ListView) findViewById(R.id.user_list);
        out = (TextView) findViewById(R.id.out);
        add = (TextView) findViewById(R.id.add);
        clear = (TextView) findViewById(R.id.clear);
        sound = (TextView) findViewById(R.id.sound);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        /** тестовый список **/
        loadList();
        /** обработка нажатий **/
        out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog aboutDialog = new AlertDialog.Builder(
                        ChatSettingsActivity.this).setTitle("Выйти").setMessage("Вы уверены, что хотите выйти из чата?")
                        .setNegativeButton("ОТМЕНА", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        })
                        .setPositiveButton("ОК", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                new DeleteFormChatAsyncTask(CurrentSession.getUserId(), CurrentChat.getId_chat()).execute();
                                dialog.cancel();
                            }
                        })
                        .setOnCancelListener(new DialogInterface.OnCancelListener() {
                            @Override
                            public void onCancel(DialogInterface dialog) {
                                showOutDialog = false;
                            }
                        })
                        .create();
                if (!showOutDialog){
                    aboutDialog.show();
                    showOutDialog = true;
                }
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(ChatSettingsActivity.this).setTitle("Добавить");
                final EditText input = new EditText(ChatSettingsActivity.this);
                input.setInputType(InputType.TYPE_CLASS_NUMBER);
                builder.setView(input);
                builder.setNegativeButton("ОТМЕНА", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.setPositiveButton("ОК", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String stringId = input.getText().toString();
                        int id = 0;
                        try{
                            id = Integer.parseInt(stringId);
                        }catch (NumberFormatException ex) {}
                        if (id < 0) id = 0;
                        if (id > 0){
                            Toast.makeText(getApplicationContext(), "Добавить пользователя " + id, Toast.LENGTH_SHORT).show();
                            new InsertToChatAsyncTask(id, CurrentChat.getId_chat()).execute();
                        }
                        dialog.cancel();
                    }
                });
                builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        showInputDialog = false;
                    }
                });
                if (!showInputDialog){
                    builder.show();
                    showInputDialog = true;
                }
            }
        });
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog aboutDialog = new AlertDialog.Builder(
                        ChatSettingsActivity.this).setTitle("Очистить").setMessage("Вы уверены, что хотите очистить чат?")
                        .setNegativeButton("ОТМЕНА", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        })
                        .setPositiveButton("ОК", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                MessagesTable.clear(FoneService.localDB, CurrentChat.getId_chat());
                                dialog.cancel();
                            }
                        })
                        .setOnCancelListener(new DialogInterface.OnCancelListener() {
                            @Override
                            public void onCancel(DialogInterface dialog) {
                                showClearDialog = false;
                            }
                        })
                        .create();
                if (!showClearDialog) {
                    aboutDialog.show();
                    showClearDialog = true;
                }
            }
        });
        sound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Изменён звук", Toast.LENGTH_SHORT).show();
            }
        });
        /** редактируем заголовок **/
        ActionBar actionbar = getSupportActionBar();
        actionbar.setTitle(CurrentChat.getChatName());
        actionbar.setDisplayHomeAsUpEnabled(true);
    }

    private void loadList(){
        arrayList = new ArrayList<>();
        addToList(CurrentSession.getSurname() + " " + CurrentSession.getName(), "Вы");
        SimpleAdapter adapter = new SimpleAdapter(this, arrayList, android.R.layout.simple_list_item_2,
                new String[]{"Name", "Tel"},
                new int[]{android.R.id.text1, android.R.id.text2});
        userList.setAdapter(adapter);
        new GetUsersAsyncTask(CurrentChat.getId_chat(), CurrentSession.getUserId()).execute();
    }

    private void addOne(String text1, String text2){
        map = new HashMap<>();
        map.put("Name", text1);
        map.put("Tel", text2);
    }

    private void addToList(String text1, String text2){
        addOne(text1, text2);
        arrayList.add(map);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            default:
                finish();
                return true;
        }
    }

    /** AsyncTask для добавления другого пользователя в чат **/
    class InsertToChatAsyncTask extends AsyncTask<String, String, String> {
        int _user_id, _chat_id;
        String answer, server = ConnectionHelper.getUrl() + "/insert_to_chat";

        public InsertToChatAsyncTask(int _user_id, int _chat_id) {
            this._user_id = _user_id;
            this._chat_id = _chat_id;
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
            Toast.makeText(getApplicationContext(), "Пользователь добавлен", Toast.LENGTH_SHORT).show();
        }
    }

    /** AsyncTask для удаления себя из чата **/
    class DeleteFormChatAsyncTask extends AsyncTask<String, String, String> {
        int _user_id, _chat_id;
        String answer, server = ConnectionHelper.getUrl() + "/delete_user_from_chat";

        public DeleteFormChatAsyncTask(int _user_id, int _chat_id) {
            this._user_id = _user_id;
            this._chat_id = _chat_id;
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
            /** удаление из локальной БД и возвращение в mainmenu.SendActivity **/
            Toast.makeText(getApplicationContext(), "Вы удалены", Toast.LENGTH_SHORT).show();
            ChatUserTable.deleteChat(FoneService.localDB, _chat_id, _user_id);
            Intent intent = new Intent();
            intent.putExtra("result", "exit");
            setResult(RESULT_OK, intent);
            finish();
        }
    }

    /** AsyncTask для получения списка пользователей **/
    class GetUsersAsyncTask extends AsyncTask<String, String, String>{
        int _chat_id, _user_id;
        String answer, server = ConnectionHelper.getUrl() + "/who_in_chat";

        public GetUsersAsyncTask(int _chat_id, int _user_id) {
            this._chat_id = _chat_id;
            this._user_id = _user_id;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            HashMap<String, String> postDataParams = new HashMap<String, String>();
            postDataParams.put("id_chat", "" + _chat_id);
            answer = ConnectionHelper.performGetCall(server, postDataParams);
            return null;
        }

        @Override
        protected void onPostExecute(String res) {
            super.onPostExecute(res);
            /** добавление результата в view **/
            String mas[] = answer.split(" \\| ");
            for(int i = 0; i < mas.length; ++i){
                int currentId = 0, insertPos = -1;
                try{
                    currentId = Integer.parseInt(mas[i]);
                }catch (NumberFormatException ex) {}
                final Cursor author = UsersTable.select(FoneService.localDB, currentId);
                final String surname_name;
                if (author == null || author.getCount() == 0){
                    insertPos = arrayList.size();
                    surname_name = "" + currentId;
                }
                else{
                    author.moveToFirst();
                    surname_name = author.getString(UsersTable.INDEX_SURNAME) + " " + author.getString(UsersTable.INDEX_NAME);
                }
                author.close();
                if (currentId > 0 && currentId != _user_id) addToList(surname_name, "id : " + currentId);
                if (insertPos > -1) new GetInfoAsyncTask(currentId, insertPos).execute();
            }
            userList.deferNotifyDataSetChanged();
            progressBar.setVisibility(View.GONE);
        }
    }

    /** AsyncTask для получения информации о пользователе, если раньше он нам ничего не отправлял **/
    class GetInfoAsyncTask extends AsyncTask<String, String, String> {
        int _id_user, position;
        String answer, server = ConnectionHelper.getUrl() + "/get_user_info";

        public GetInfoAsyncTask(int _id_user, int position) {
            this._id_user = _id_user;
            this.position = position;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            HashMap<String, String> postDataParams = new HashMap<String, String>();
            postDataParams.put("id_user", "" + _id_user);
            answer = ConnectionHelper.performGetCall(server, postDataParams);
            return null;
        }

        @Override
        protected void onPostExecute(String res) {
            /** Добавление результата в локальную БД и в view, при удачном исходе. **/
            if (answer == null || answer.equals("")) return;
            String mas[] = answer.split(" \\| ");
            UsersTable.insertUser(FoneService.localDB, _id_user, mas[0], mas[1]);
            addOne(mas[1] + " " + mas[0], "id : " + _id_user);
            arrayList.set(position, map);
            userList.deferNotifyDataSetChanged();
            super.onPostExecute(res);
        }
    }
}
