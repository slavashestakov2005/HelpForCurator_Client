/**
 * Активность с настройками чата.
 * Работают:
 * 1. Выход из чата.
 * 2. Добавление в чат.
 * 3. Удаление истории (возможно нет так, как нужно).
 * **/

package com.example.helpforcurator.chat;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.example.helpforcurator.R;
import com.example.helpforcurator.help.ConnectionHelper;
import com.example.helpforcurator.help.CurrentChat;
import com.example.helpforcurator.help.CurrentSession;
import com.example.helpforcurator.help.FoneService;
import com.example.helpforcurator.help.tables.ChatUserTable;
import com.example.helpforcurator.help.tables.MessagesTable;

import java.util.HashMap;

public class ChatSettingsActivity extends PreferenceActivity implements SharedPreferences.OnSharedPreferenceChangeListener {
    /** видимые настройки **/
    private boolean showOutDialog = false, inputId = false, showClearDialog = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences_chat);
    }

    @Override
    protected void onStart() {
        super.onStart();
        PreferenceManager.getDefaultSharedPreferences(this).registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        PreferenceManager.getDefaultSharedPreferences(this).unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals(getString(R.string.pref_out))){
            /** отмена изменений **/
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean(key, false);
            editor.apply();
            /** создание и показ одного диалога **/
            final AlertDialog aboutDialog = new AlertDialog.Builder(
                    ChatSettingsActivity.this).setTitle("Выйти").setMessage("Вы уверены, что хотите выйти из чата?")
                    .setNegativeButton("ОТМЕНА", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                            showOutDialog = false;
                        }
                    })
                    .setPositiveButton("ОК", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            new DeleteFormChatAsyncTask(CurrentSession.getUserId(), CurrentChat.getId_chat()).execute();
                            dialog.cancel();
                            showOutDialog = false;
                        }
                    })
                    .create();
            if (!showOutDialog){
                aboutDialog.show();
                showOutDialog = true;
            }
        }
        else if (key.equals(getString(R.string.pref_add))){
            if (!inputId) {
                /** обработка результата ввода на один диалог **/
                inputId = true;
                int id = 0;
                try {
                    id = Integer.parseInt(sharedPreferences.getString(key, "0"));
                } catch (NumberFormatException ex) {}
                if (id < 0) id = 0;
                if (id > 0) {
                    Toast.makeText(getApplicationContext(), "Добавить пользователя " + id, Toast.LENGTH_SHORT).show();
                    new InsertToChatAsyncTask(id, CurrentChat.getId_chat(), CurrentSession.getName()).execute();
                }
                /** отмена изменений **/
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(key, "");
                editor.apply();
                inputId = false;
            }
        }
        else if (key.equals(getString(R.string.pref_clear))){
            /** отмена изменений **/
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean(key, false);
            editor.apply();
            /** создание и показ одного диалога **/
            final AlertDialog aboutDialog = new AlertDialog.Builder(
                    ChatSettingsActivity.this).setTitle("Очистить").setMessage("Вы уверены, что хотите очистить чат?")
                    .setNegativeButton("ОТМЕНА", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                            showClearDialog = false;
                        }
                    })
                    .setPositiveButton("ОК", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            MessagesTable.clear(FoneService.localDB, CurrentChat.getId_chat());
                            dialog.cancel();
                            showClearDialog = false;
                        }
                    })
                    .create();
            if (!showClearDialog){
                aboutDialog.show();
                showClearDialog = true;
            }
        }
        else if (key.equals(getString(R.string.pref_sound))) Toast.makeText(getApplicationContext(), "Изменён звук", Toast.LENGTH_SHORT).show();
        getPreferenceScreen().removeAll();
        addPreferencesFromResource(R.xml.preferences_chat);
    }

    /** AsyncTask для добавления другого пользователя в чат **/
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
}
