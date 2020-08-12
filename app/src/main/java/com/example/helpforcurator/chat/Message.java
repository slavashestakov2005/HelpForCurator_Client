/**
 * Класс для отображения чужих сообщений в активности chat.ChatActivity.
 * Обращение к серверу ".../get_user_info".
 * **/

package com.example.helpforcurator.chat;

import android.content.Context;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.helpforcurator.R;
import com.example.helpforcurator.help.ConnectionHelper;
import com.example.helpforcurator.help.FoneService;
import com.example.helpforcurator.help.tables.UsersTable;

import java.util.HashMap;

public class Message extends RelativeLayout {
    /** view элемненты **/
    private TextView author, text, time;

    public Message(Context context) {
        super(context);
        /** получение view **/
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.message, this);
        author = (TextView) findViewById(R.id.message_author);
        text = (TextView) findViewById(R.id.message_text);
        time = (TextView) findViewById(R.id.message_time);
    }

    public void setMessageAuthor(String author_id){
        try{
            int n = Integer.parseInt(author_id);
            try {
                new GetInfoAsyncTask(n).execute();
            } catch (Exception ex){}
        }catch (NumberFormatException ex){
            author.setText(author_id);
        }
    }

    public void setMessageText(String _text){
        text.setText(_text);
    }

    public void setMessageTime(String _time){
        time.setText(_time);
    }

    /** AsyncTask для получения информации о пользователе, если раньше он нам ничего не отправлял **/
    class GetInfoAsyncTask extends AsyncTask<String, String, String> {
        int _id_user;
        String answer, server = ConnectionHelper.getUrl() + "/get_user_info";

        public GetInfoAsyncTask(int _id_user) {
            this._id_user = _id_user;
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
            author.setText(mas[1] + " " + mas[0]);
            super.onPostExecute(res);
        }
    }
}
