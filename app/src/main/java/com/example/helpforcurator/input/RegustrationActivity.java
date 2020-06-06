/**
 * Первая активность регистрации.
 * По кнопке "Далее" переходим на продолжение регистрации (input.Registration2Activity).
 * **/

package com.example.helpforcurator.input;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.helpforcurator.R;

public class RegustrationActivity extends AppCompatActivity {
    /** view элемненты **/
    private EditText surname, name, middleName;
    private Button next;
    private TextView errors;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /** получение view **/
        setContentView(R.layout.activity_regustration);
        next = (Button) findViewById(R.id.exit);
        surname = (EditText) findViewById(R.id.surname);
        name = (EditText) findViewById(R.id.name);
        middleName = (EditText) findViewById(R.id.otchestvo);
        errors = (TextView) findViewById(R.id.errors);
        /** обработка нажатий **/
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s1 = surname.getText().toString().trim();
                String s2 = name.getText().toString().trim();
                String s3 = middleName.getText().toString().trim();
                if (!s1.equals("")) {
                    if (!s2.equals("")) {
                        if (!s3.equals("")) {
                            Intent intent = new Intent(RegustrationActivity.this, Regustration2Activity.class);
                            intent.putExtra(Regustration2Activity.key, s1);
                            intent.putExtra(Regustration2Activity.key2, s2);
                            intent.putExtra(Regustration2Activity.key3, s3);
                            startActivity(intent);
                            finish();
                        } else {
                            surname.setBackgroundColor(getColor(R.color.white));
                            name.setBackgroundColor(getColor(R.color.white));
                            middleName.setBackgroundColor(getColor(R.color.error));
                            errors.setText("Пустое отчество запрещено");
                        }
                    } else {
                        surname.setBackgroundColor(getColor(R.color.white));
                        name.setBackgroundColor(getColor(R.color.error));
                        middleName.setBackgroundColor(getColor(R.color.white));
                        errors.setText("Пустое имя запрещено");
                    }

                } else {
                    surname.setBackgroundColor(getColor(R.color.error));
                    name.setBackgroundColor(getColor(R.color.white));
                    middleName.setBackgroundColor(getColor(R.color.white));
                    errors.setText("Пустая фамилия запрещена");
                }
            }
        });
    }
}