package com.example.helpforcurator.input;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.helpforcurator.R;
import com.example.helpforcurator.help.ConectionHealper;
import com.example.helpforcurator.help.CurrentSession;
import com.example.helpforcurator.mainmenu.MenuActivity;

import java.util.HashMap;

public class Regustration2Activity extends AppCompatActivity {
    public static final String key = "key", key2 = "key2", key3 = "key3";
    String surname, name, otchestvo;
    EditText login, password, password2, phone, email;
    TextView errors;
    Button exit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regustration2);
        exit = (Button) findViewById(R.id.exit);
        login = (EditText) findViewById(R.id.login);
        password = (EditText) findViewById(R.id.password);
        password2 = (EditText) findViewById(R.id.password2);
        phone = (EditText) findViewById(R.id.phone);
        email = (EditText) findViewById(R.id.email);
        errors = (TextView) findViewById(R.id.errors);
        surname = getIntent().getStringExtra(key);
        name = getIntent().getStringExtra(key2);
        otchestvo = getIntent().getStringExtra(key3);
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s1 = password.getText().toString();
                String s2 = password2.getText().toString();
                String s3 = phone.getText().toString().trim();
                String s4 = email.getText().toString().trim();
                String s = login.getText().toString();
                if (!s.equals("")) {
                    if (s1.equals(s2)) {
                        if (!s1.equals("")) {
                            if (!s3.equals("")) {
                                if (!s4.equals("")){
                                    new CreateAsyncTask(s, s1, s3, s4, name, surname, otchestvo).execute();
                                } else {
                                    login.setBackgroundColor(getColor(R.color.white));
                                    password.setBackgroundColor(getColor(R.color.white));
                                    password2.setBackgroundColor(getColor(R.color.white));
                                    phone.setBackgroundColor(getColor(R.color.white));
                                    email.setBackgroundColor(getColor(R.color.error));
                                    errors.setText("Пустой email запрещён");
                                }

                            } else {
                                login.setBackgroundColor(getColor(R.color.white));
                                password.setBackgroundColor(getColor(R.color.white));
                                password2.setBackgroundColor(getColor(R.color.white));
                                phone.setBackgroundColor(getColor(R.color.error));
                                email.setBackgroundColor(getColor(R.color.white));
                                errors.setText("Пустой телефон запрещён");
                            }
                        } else {
                            login.setBackgroundColor(getColor(R.color.white));
                            password.setBackgroundColor(getColor(R.color.error));
                            password2.setBackgroundColor(getColor(R.color.white));
                            phone.setBackgroundColor(getColor(R.color.white));
                            email.setBackgroundColor(getColor(R.color.white));
                            errors.setText("Пустой пароль запрещён");
                        }
                    } else {
                        login.setBackgroundColor(getColor(R.color.white));
                        password.setBackgroundColor(getColor(R.color.white));
                        password2.setBackgroundColor(getColor(R.color.error));
                        phone.setBackgroundColor(getColor(R.color.white));
                        email.setBackgroundColor(getColor(R.color.white));
                        errors.setText("Пароли не совпадают");
                    }
                } else {
                    login.setBackgroundColor(getColor(R.color.error));
                    password.setBackgroundColor(getColor(R.color.white));
                    password2.setBackgroundColor(getColor(R.color.white));
                    phone.setBackgroundColor(getColor(R.color.white));
                    email.setBackgroundColor(getColor(R.color.white));
                    errors.setText("Пустой логин запрещён");
                }
            }
        });
    }

    class CreateAsyncTask extends AsyncTask<String, String, String> {
        String _login, _password, _phone, _email, _name, _surname, _middlename;
        String answer, server = ConectionHealper.getUrl() + "/create";

        public CreateAsyncTask(String _login, String _password, String _phone, String _email, String _name, String _surname, String _middlename) {
            this._login = _login;
            this._password = _password;
            this._phone = _phone;
            this._email = _email;
            this._name = _name;
            this._surname = _surname;
            this._middlename = _middlename;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            HashMap<String, String> postDataParams = new HashMap<String, String>();
            postDataParams.put("login", _login);
            postDataParams.put("password", _password);
            postDataParams.put("phone", _phone);
            postDataParams.put("email", _email);
            postDataParams.put("name", _name);
            postDataParams.put("surname", _surname);
            postDataParams.put("middlename", _middlename);
            answer = ConectionHealper.performGetCall(server, postDataParams);
            return null;
        }

        @Override
        protected void onPostExecute(String res) {
            super.onPostExecute(res);
            if (answer.equals("error")){
                login.setBackgroundColor(getColor(R.color.error));
                password.setBackgroundColor(getColor(R.color.white));
                password2.setBackgroundColor(getColor(R.color.white));
                phone.setBackgroundColor(getColor(R.color.white));
                email.setBackgroundColor(getColor(R.color.white));
                errors.setText("Логин уже существует");
            }
            else{
                CurrentSession.setUserId(Integer.parseInt(answer));
                CurrentSession.setLogin(_login);
                CurrentSession.setPassword(_password);
                CurrentSession.setPhone(_phone);
                CurrentSession.setEmail(_email);
                CurrentSession.setName(_name);
                CurrentSession.setSurname(_surname);
                CurrentSession.setMiddlename(_middlename);
                Intent intent = new Intent(Regustration2Activity.this, MenuActivity.class);
                startActivity(intent);
                finish();
            }
        }
    }
}
