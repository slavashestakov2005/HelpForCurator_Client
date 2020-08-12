/**
 * Вторая активность регистрации.
 * По кнопке "Зарегистрироваться" переходим на mainmenu.MenuActivity.
 * Обращение к серверу ".../create_user".
 * **/

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
import com.example.helpforcurator.help.ConnectionHelper;
import com.example.helpforcurator.help.CurrentSession;
import com.example.helpforcurator.mainmenu.MenuActivity;

import java.util.HashMap;

public class Regustration2Activity extends AppCompatActivity {
    /** extra данные через Intent **/
    public static final String key = "key", key2 = "key2", key3 = "key3";
    private String surname, name, middleName;
    /** view элемненты **/
    private EditText login, password, password2, phone, email;
    private TextView errors;
    private Button enter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /** получение view **/
        setContentView(R.layout.activity_regustration2);
        enter = (Button) findViewById(R.id.enter);
        login = (EditText) findViewById(R.id.login);
        password = (EditText) findViewById(R.id.password);
        password2 = (EditText) findViewById(R.id.password2);
        phone = (EditText) findViewById(R.id.phone);
        email = (EditText) findViewById(R.id.email);
        errors = (TextView) findViewById(R.id.errors);
        /** получение extra данных через Intent **/
        surname = getIntent().getStringExtra(key);
        name = getIntent().getStringExtra(key2);
        middleName = getIntent().getStringExtra(key3);
        /** обработка нажатий **/
        enter.setOnClickListener(new View.OnClickListener() {
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
                                    new CreateUserAsyncTask(s, s1, s3, s4, name, surname, middleName).execute();
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

    /** AsyncTask для обновления данных пользователя **/
    class CreateUserAsyncTask extends AsyncTask<String, String, String> {
        String _login, _password, _phone, _email, _name, _surname, _middleName,
                answer, server = ConnectionHelper.getUrl() + "/create_user";

        public CreateUserAsyncTask(String _login, String _password, String _phone, String _email, String _name, String _surname, String _middleName) {
            this._login = _login;
            this._password = _password;
            this._phone = _phone;
            this._email = _email;
            this._name = _name;
            this._surname = _surname;
            this._middleName = _middleName;
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
            postDataParams.put("middle_name", _middleName);
            answer = ConnectionHelper.performGetCall(server, postDataParams);
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
                CurrentSession.setMiddlename(_middleName);
                Intent intent = new Intent(Regustration2Activity.this, MenuActivity.class);
                startActivity(intent);
                finish();
            }
        }
    }
}
