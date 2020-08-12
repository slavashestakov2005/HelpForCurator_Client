/**
 * Tab (extends Fragment) для активности mainmenu.NameActivity.
 * Здесь располагается и редактируется информация про:
 * 1. Фамилию
 * 2. Имя
 * 3. Отчество
 * Сохраняется кнопкой "Сохранить"
 * Обращение к серверу ".../update".
 * **/

package com.example.helpforcurator.name;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.helpforcurator.R;
import com.example.helpforcurator.help.ConnectionHelper;
import com.example.helpforcurator.help.CurrentSession;

import java.util.HashMap;

public class Tab1 extends Fragment {
    /** view элемненты **/
    private EditText name, surname, middleName;
    private Button save;
    private RadioGroup gender;
    private TextView errors;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        /** получение view **/
        final View root = inflater.inflate(R.layout.tab1, container, false);
        name = (EditText) root.findViewById(R.id.name);
        surname = (EditText) root.findViewById(R.id.surname);
        middleName = (EditText) root.findViewById(R.id.middlename);
        gender = (RadioGroup) root.findViewById(R.id.gender);
        errors = (TextView) root.findViewById(R.id.errors);
        save = (Button) root.findViewById(R.id.save);
        /** обработка нажатий **/
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s1 = surname.getText().toString().trim(),
                        s2 = name.getText().toString().trim(),
                        s3 = middleName.getText().toString().trim();
                if (!s2.equals("")) {
                    if (!s1.equals("")) {
                        if (!s3.equals("")) {
                            new UpdateAsyncTask().execute();
                            name.setBackgroundColor(Color.parseColor("#ffffff"));
                            surname.setBackgroundColor(Color.parseColor("#ffffff"));
                            middleName.setBackgroundColor(Color.parseColor("#ffffff"));
                            // int radioButtonId = gender.getCheckedRadioButtonId();
                            // Toast.makeText(getContext(), ((RadioButton) root.findViewById(radioButtonId)).getText(), Toast.LENGTH_SHORT).show();
                            errors.setText("");
                        } else {
                            surname.setBackgroundColor(Color.parseColor("#ffffff"));
                            name.setBackgroundColor(Color.parseColor("#ffffff"));
                            middleName.setBackgroundColor(Color.rgb(241, 86, 94));
                            errors.setText("Пустое отчество запрещено");
                        }
                    } else {
                        name.setBackgroundColor(Color.parseColor("#ffffff"));
                        surname.setBackgroundColor(Color.rgb(241, 86, 94));
                        middleName.setBackgroundColor(Color.parseColor("#ffffff"));
                        errors.setText("Пустая фамилия запрещена");
                    }

                } else {
                    name.setBackgroundColor(Color.rgb(241, 86, 94));
                    surname.setBackgroundColor(Color.parseColor("#ffffff"));
                    middleName.setBackgroundColor(Color.parseColor("#ffffff"));
                    errors.setText("Пустое имя запрещено");
                }
            }
        });
        return root;
    }

    @Override
    public void onResume() {
        /** создание исходного внешнего вида **/
        name.setBackgroundColor(Color.parseColor("#ffffff"));
        surname.setBackgroundColor(Color.parseColor("#ffffff"));
        middleName.setBackgroundColor(Color.parseColor("#ffffff"));
        name.setText(CurrentSession.getName());
        surname.setText(CurrentSession.getSurname());
        middleName.setText(CurrentSession.getMiddlename());
        errors.setText("");
        super.onResume();
    }

    /** AsyncTask для обновления данных пользователя **/
    class UpdateAsyncTask extends AsyncTask<String, String, String> {
        int _id;
        String _name, _surname, _middleName, answer, server = ConnectionHelper.getUrl() + "/update";

        @Override
        protected void onPreExecute() {
            _id = CurrentSession.getUserId();
            _name = name.getText().toString();
            _surname = surname.getText().toString();
            _middleName = middleName.getText().toString();
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            HashMap<String, String> postDataParams = new HashMap<String, String>();
            postDataParams.put("id_user", "" + _id);
            postDataParams.put("type", "name");
            postDataParams.put("name", _name);
            postDataParams.put("surname", _surname);
            postDataParams.put("middle_name", _middleName);
            answer = ConnectionHelper.performGetCall(server, postDataParams);
            return null;
        }

        @Override
        protected void onPostExecute(String res) {
            super.onPostExecute(res);
            CurrentSession.setName(_name);
            CurrentSession.setSurname(_surname);
            CurrentSession.setMiddlename(_middleName);
            name.setText(_name);
            surname.setText(_surname);
            middleName.setText(_middleName);
        }
    }
}
