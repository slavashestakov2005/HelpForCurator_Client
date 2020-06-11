/**
 * Tab (extends Fragment) для активности mainmenu.NameActivity.
 * Здесь располагается и редактируется информация про:
 * 1. Телефон
 * 2. Email
 * Сохраняется кнопкой "Сохранить"
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
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.helpforcurator.help.ConnectionHelper;
import com.example.helpforcurator.help.CurrentSession;
import com.example.helpforcurator.R;

import java.util.HashMap;

public class Tab2 extends Fragment {
    /** view элемненты **/
    private EditText phone, email;
    private Button save;
    private TextView errors;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        /** получение view **/
        View root = inflater.inflate(R.layout.tab2, container, false);
        phone = (EditText) root.findViewById(R.id.phone);
        email = (EditText) root.findViewById(R.id.email);
        errors = (TextView) root.findViewById(R.id.errors);
        save = (Button) root.findViewById(R.id.save);
        /** обработка нажатий **/
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s1 = phone.getText().toString().trim(),
                        s2 = email.getText().toString().trim();
                if (!s1.equals("")) {
                    if (!s2.equals("")){
                        new UpdateAsyncTask().execute();
                        phone.setBackgroundColor(Color.parseColor("#ffffff"));
                        email.setBackgroundColor(Color.parseColor("#ffffff"));
                        errors.setText("");
                    } else {
                        phone.setBackgroundColor(Color.parseColor("#ffffff"));
                        email.setBackgroundColor(Color.rgb(241, 86, 94));
                        errors.setText("Пустой email запрещён");
                    }
                } else {
                    phone.setBackgroundColor(Color.rgb(241, 86, 94));
                    email.setBackgroundColor(Color.parseColor("#ffffff"));
                    errors.setText("Пустой телефон запрещён");
                }
            }
        });
        return root;
    }

    @Override
    public void onResume() {
        /** создание исходного внешнего вида **/
        phone.setBackgroundColor(Color.parseColor("#ffffff"));
        email.setBackgroundColor(Color.parseColor("#ffffff"));
        phone.setText(CurrentSession.getPhone());
        email.setText(CurrentSession.getEmail());
        errors.setText("");
        super.onResume();
    }

    /** AsyncTask для обновления данных пользователя **/
    class UpdateAsyncTask extends AsyncTask<String, String, String> {
        int _id;
        String _phone, _email, answer, server = ConnectionHelper.getUrl() + "/update";

        @Override
        protected void onPreExecute() {
            _id = CurrentSession.getUserId();
            _phone = phone.getText().toString();
            _email = email.getText().toString();
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            HashMap<String, String> postDataParams = new HashMap<String, String>();
            postDataParams.put("id", "" + _id);
            postDataParams.put("type", "contacts");
            postDataParams.put("phone", _phone);
            postDataParams.put("email", _email);
            answer = ConnectionHelper.performGetCall(server, postDataParams);
            return null;
        }

        @Override
        protected void onPostExecute(String res) {
            super.onPostExecute(res);
            CurrentSession.setPhone(_phone);
            CurrentSession.setEmail(_email);
            phone.setText(_phone);
            email.setText(_email);
        }
    }
}
