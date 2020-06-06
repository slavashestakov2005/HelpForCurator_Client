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
import com.example.helpforcurator.help.ConectionHealper;
import com.example.helpforcurator.help.CurrentSession;
import com.example.helpforcurator.mainmenu.MenuActivity;

import java.util.ArrayList;
import java.util.HashMap;

public class LoginActivity extends AppCompatActivity {
    EditText login, password;
    Button enter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        login = (EditText) findViewById(R.id.login);
        password = (EditText) findViewById(R.id.password);
        enter = (Button) findViewById(R.id.enter);
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
        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new InputAsyncTask().execute();
            }
        });
    }

    class InputAsyncTask extends AsyncTask<String, String, String> {
        String log, pass, answer, server = ConectionHealper.getUrl() + "/input";
        @Override
        protected void onPreExecute() {
            log = login.getText().toString();
            pass = password.getText().toString();
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            HashMap<String, String> postDataParams = new HashMap<String, String>();
            postDataParams.put("login", log);
            answer = ConectionHealper.performGetCall(server, postDataParams);
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
            if (pass.equals(array.get(2))){
                CurrentSession.setSession(array);
                Intent intent = new Intent(LoginActivity.this, MenuActivity.class);
                startActivity(intent);
            }
            else{
                Toast toast = Toast.makeText(getApplicationContext(), "Неправильный логин или пароль", Toast.LENGTH_SHORT);
                toast.show();
            }
        }
    }
}
