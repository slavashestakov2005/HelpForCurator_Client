package com.example.helpforcurator.chat;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.helpforcurator.R;
import com.example.helpforcurator.help.ConectionHealper;
import com.example.helpforcurator.help.FoneService;
import com.example.helpforcurator.help.tables.UsersTable;

import java.util.HashMap;

public class Message extends RelativeLayout {
    TextView author, text, time;

    public Message(Context context) {
        super(context);
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
            } catch (Exception ex){
                Log.i("Error", ex.getStackTrace().toString());
            }
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

    class GetInfoAsyncTask extends AsyncTask<String, String, String> {
        int id_user;
        String answer, server = ConectionHealper.getUrl() + "/get_user_info";

        public GetInfoAsyncTask(int id_user) {
            this.id_user = id_user;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            HashMap<String, String> postDataParams = new HashMap<String, String>();
            postDataParams.put("id", "" + id_user);
            answer = ConectionHealper.performGetCall(server, postDataParams);
            return null;
        }

        @Override
        protected void onPostExecute(String res) {
            // узнали человека -> изменем view и добавить в БД
            if (answer == null || answer.equals("")) return;
            String mas[] = answer.split(" \\| ");
            UsersTable.insertUser(FoneService.localDB, id_user, mas[0], mas[1]);
            author.setText(mas[1] + " " + mas[0]);
            super.onPostExecute(res);
        }
    }
}
