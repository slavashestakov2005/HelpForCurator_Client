/**
 * Входная активность.
 * На время отладки пропускается, чтобы входить с выключенным сервером.
 * Посли кнопки "Вход" переходим на menumain.MenuActivity.
 * Обращение к серверу ".../input".
 * **/

package com.example.helpforcurator.input;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.helpforcurator.R;
import com.example.helpforcurator.help.ConnectionHelper;
import com.example.helpforcurator.help.CurrentSession;
import com.example.helpforcurator.mainmenu.MenuActivity;

import java.util.ArrayList;
import java.util.HashMap;

public class LoginActivity extends AppCompatActivity {
    /** view элемненты **/
    private EditText login, password;
    private Button enter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /** получение view **/
        setContentView(R.layout.activity_login);
        login = (EditText) findViewById(R.id.login);
        password = (EditText) findViewById(R.id.password);
        enter = (Button) findViewById(R.id.enter);
        /** временное заполнение данных данными пользователя по умолчанию **/
        CurrentSession.setUserId(-1);
        CurrentSession.setLogin("login");
        CurrentSession.setPassword("password");
        CurrentSession.setPhone("phone");
        CurrentSession.setEmail("email");
        CurrentSession.setName("Имя");
        CurrentSession.setSurname("Фамилия");
        CurrentSession.setMiddlename("Отчество");
        Intent intent = new Intent(LoginActivity.this, MenuActivity.class);
        startActivity(intent);
        finish();
        /** обработка нажатий **/
        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new InputAsyncTask().execute();
            }
        });
    }

    /** AsyncTask для входа пользователя **/
    class InputAsyncTask extends AsyncTask<String, String, String> {
        String _login, _password, answer, server = ConnectionHelper.getUrl() + "/input";

        @Override
        protected void onPreExecute() {
            _login = login.getText().toString();
            _password = password.getText().toString();
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            HashMap<String, String> postDataParams = new HashMap<String, String>();
            postDataParams.put("login", _login);
            answer = ConnectionHelper.performGetCall(server, postDataParams);
            return null;
        }

        @Override
        protected void onPostExecute(String res) {
            super.onPostExecute(res);
            String[] split = answer.split(" \\| ");
            ArrayList<String> array = new ArrayList<String>();
            for(String word : split){
                array.add(word);
            }
            if (_password.equals(array.get(2))){
                CurrentSession.setSession(array);
                Intent intent = new Intent(LoginActivity.this, MenuActivity.class);
                startActivity(intent);
            }
            else{
                Toast.makeText(getApplicationContext(), "Неправильный логин или пароль", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
