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

public class Tab3 extends Fragment {
    EditText old, password, password2;
    Button save;
    TextView errors, id;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.tab3, container, false);
        old = (EditText) root.findViewById(R.id.old);
        password = (EditText) root.findViewById(R.id.password);
        password2 = (EditText) root.findViewById(R.id.password2);
        save = (Button) root.findViewById(R.id.save);
        errors = (TextView) root.findViewById(R.id.errors);
        id = (TextView) root.findViewById(R.id.id);
        id.setText(System.lineSeparator() + System.lineSeparator() + "Ваш id : " + CurrentSession.getUserId() + System.lineSeparator() + "Url сервера : " + ConectionHealper.getUrl());
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String oldPassword = old.getText().toString(), pass = password.getText().toString(), pass2 = password2.getText().toString();
                if (oldPassword.equals(CurrentSession.getPassword())){
                    if (!pass.equals("")){
                        if (pass.equals(pass2)){
                            new Update3AsyncTask().execute();
                            old.setBackgroundColor(Color.parseColor("#ffffff"));
                            password.setBackgroundColor(Color.parseColor("#ffffff"));
                            password2.setBackgroundColor(Color.parseColor("#ffffff"));
                            old.setText("");
                            password.setText("");
                            password2.setText("");
                            errors.setText("");
                        }
                        else {
                            old.setBackgroundColor(Color.parseColor("#ffffff"));
                            password.setBackgroundColor(Color.parseColor("#ffffff"));
                            password2.setBackgroundColor(Color.rgb(241, 86, 94));
                            errors.setText("Пароли не совпадают");
                        }
                    } else {
                        old.setBackgroundColor(Color.parseColor("#ffffff"));
                        password.setBackgroundColor(Color.rgb(241, 86, 94));
                        password2.setBackgroundColor(Color.parseColor("#ffffff"));
                        errors.setText("Пустой пароль запрещён");
                    }
                } else {
                    old.setBackgroundColor(Color.rgb(241, 86, 94));
                    password.setBackgroundColor(Color.parseColor("#ffffff"));
                    password2.setBackgroundColor(Color.parseColor("#ffffff"));
                    errors.setText("Старый пароль введён неверно");
                }
            }
        });
        return root;
    }

    @Override
    public void onResume() {
        old.setBackgroundColor(Color.parseColor("#ffffff"));
        password.setBackgroundColor(Color.parseColor("#ffffff"));
        password2.setBackgroundColor(Color.parseColor("#ffffff"));
        old.setText("");
        password.setText("");
        password2.setText("");
        errors.setText("");
        super.onResume();
    }

    class Update3AsyncTask extends AsyncTask<String, String, String> {
        String _password, answer, server = ConectionHealper.getUrl() + "/update";
        @Override
        protected void onPreExecute() {
            _password = password.getText().toString();
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            HashMap<String, String> postDataParams = new HashMap<String, String>();
            postDataParams.put("id", "" + CurrentSession.getUserId());
            postDataParams.put("type", "password");
            postDataParams.put("password", _password);
            answer = ConectionHealper.performGetCall(server, postDataParams);
            return null;
        }

        @Override
        protected void onPostExecute(String res) {
            super.onPostExecute(res);
            CurrentSession.setPassword(_password);
        }
    }
}
