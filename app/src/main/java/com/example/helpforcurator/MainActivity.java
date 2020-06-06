/**
 * Стартовая активность. Имеет три кнопки:
 * 1. "Регистрация" (input.RegistrationActivity).
 * 2. "Вход" (input.LoginActivity).
 * 3. "Получение списка пользователей" (AllUsersActivity).
 * Есть временное поле для ввода ip (на время локального сервера).
 * **/

package com.example.helpforcurator;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.helpforcurator.help.ConectionHealper;
import com.example.helpforcurator.input.LoginActivity;
import com.example.helpforcurator.input.RegustrationActivity;

public class MainActivity extends AppCompatActivity {
    /** view элемненты **/
    private Button login, signIn, allusers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /** получение view **/
        setContentView(R.layout.activity_main);
        login = (Button) findViewById(R.id.login);
        signIn = (Button) findViewById(R.id.signin);
        allusers = (Button) findViewById(R.id.listofuser);
        /** обработка нажатий **/
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConectionHealper.setIp(((EditText) findViewById(R.id.ip)).getText().toString());
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RegustrationActivity.class);
                startActivity(intent);
            }
        });
        allusers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AllUsersActivity.class);
                startActivity(intent);
            }
        });
    }
}
