package com.example.helpforcurator.name;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.helpforcurator.help.ConectionHealper;
import com.example.helpforcurator.help.CurrentSession;
import com.example.helpforcurator.R;

import java.util.HashMap;

public class Tab1 extends Fragment {
    EditText name, surname, middlename;
    Button save;
    TextView errors;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.tab1, container, false);
        name = (EditText) root.findViewById(R.id.name);
        surname = (EditText) root.findViewById(R.id.surname);
        middlename = (EditText) root.findViewById(R.id.middlename);
        errors = (TextView) root.findViewById(R.id.errors);
        save = (Button) root.findViewById(R.id.save);
        name.setText(CurrentSession.getName());
        surname.setText(CurrentSession.getSurname());
        middlename.setText(CurrentSession.getMiddlename());
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s1 = surname.getText().toString(), s2 = name.getText().toString(), s3 = middlename.getText().toString();
                if (!s2.equals("")) {
                    if (!s1.equals("")) {
                        if (!s3.equals("")) {
                            new Update1AsyncTask().execute();
                            name.setBackgroundColor(Color.parseColor("#ffffff"));
                            surname.setBackgroundColor(Color.parseColor("#ffffff"));
                            middlename.setBackgroundColor(Color.parseColor("#ffffff"));
                            errors.setText("");
                        } else {
                            surname.setBackgroundColor(Color.parseColor("#ffffff"));
                            name.setBackgroundColor(Color.parseColor("#ffffff"));
                            middlename.setBackgroundColor(Color.rgb(241, 86, 94));
                            errors.setText("Пустое отчество запрещено");
                        }
                    } else {
                        name.setBackgroundColor(Color.parseColor("#ffffff"));
                        surname.setBackgroundColor(Color.rgb(241, 86, 94));
                        middlename.setBackgroundColor(Color.parseColor("#ffffff"));
                        errors.setText("Пустая фамилия запрещена");
                    }

                } else {
                    name.setBackgroundColor(Color.rgb(241, 86, 94));
                    surname.setBackgroundColor(Color.parseColor("#ffffff"));
                    middlename.setBackgroundColor(Color.parseColor("#ffffff"));
                    errors.setText("Пустое имя запрещено");
                }
            }
        });
        return root;
    }

    @Override
    public void onResume() {
        name.setBackgroundColor(Color.parseColor("#ffffff"));
        surname.setBackgroundColor(Color.parseColor("#ffffff"));
        middlename.setBackgroundColor(Color.parseColor("#ffffff"));
        name.setText(CurrentSession.getName());
        surname.setText(CurrentSession.getSurname());
        middlename.setText(CurrentSession.getMiddlename());
        errors.setText("");
        super.onResume();
    }

    class Update1AsyncTask extends AsyncTask<String, String, String> {
        String _name, _surname, _middlename, answer, server = ConectionHealper.getUrl() + "/update";
        @Override
        protected void onPreExecute() {
            _name = name.getText().toString();
            _surname = surname.getText().toString();
            _middlename = middlename.getText().toString();
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            HashMap<String, String> postDataParams = new HashMap<String, String>();
            postDataParams.put("id", "" + CurrentSession.getUserId());
            postDataParams.put("type", "name");
            postDataParams.put("name", _name);
            postDataParams.put("surname", _surname);
            postDataParams.put("middlename", _middlename);
            answer = ConectionHealper.performGetCall(server, postDataParams);
            return null;
        }

        @Override
        protected void onPostExecute(String res) {
            super.onPostExecute(res);
            CurrentSession.setName(_name);
            CurrentSession.setSurname(_surname);
            CurrentSession.setMiddlename(_middlename);
            name.setText(_name);
            surname.setText(_surname);
            middlename.setText(_middlename);
        }
    }
}
