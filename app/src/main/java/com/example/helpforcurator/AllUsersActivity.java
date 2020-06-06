/**
 * Временная активность.
 * По ней можно проверить подключение к серверу путём получения списка всех пользователей
 * **/

package com.example.helpforcurator;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.helpforcurator.help.ConectionHealper;

import java.util.HashMap;

public class AllUsersActivity extends AppCompatActivity {
    /** view элементы **/
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /** получение view **/
        setContentView(R.layout.activity_all_users);
        textView = (TextView) findViewById(R.id.text);
        /** AsyncTask обращение к серверу **/
        new ShowAllUsersAsyncTask().execute();
    }

    /** AsyncTask для получения списка пользователей **/
    class ShowAllUsersAsyncTask extends AsyncTask<String, String, String> {
        String answer, server = ConectionHealper.getUrl() + "/all";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            HashMap<String, String> postDataParams = new HashMap<String, String>();
            answer = ConectionHealper.performGetCall(server, postDataParams);
            return null;
        }

        @Override
        protected void onPostExecute(String res) {
            super.onPostExecute(res);
            String[] mas = answer.split(" \\| ");
            StringBuilder result = new StringBuilder();
            result.append(mas[0] + "\n");
            for (int i = 1; i < mas.length; ++i){
                result.append(mas[i] + "\t");
                if (i % 8 == 0) result.append("\n");
            }
            textView.setText(result.toString());
        }
    }
}
