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

public class Tab2 extends Fragment {
    EditText phone, email;
    Button save;
    TextView errors;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.tab2, container, false);
        phone = (EditText) root.findViewById(R.id.phone);
        email = (EditText) root.findViewById(R.id.email);
        errors = (TextView) root.findViewById(R.id.errors);
        save = (Button) root.findViewById(R.id.save);
        phone.setText(CurrentSession.getPhone());
        email.setText(CurrentSession.getEmail());
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = phone.getText().toString();
                String s2 = email.getText().toString();
                if (!s.equals("")) {
                    if (!s2.equals("")){
                        new Update2AsyncTask().execute();
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
        phone.setBackgroundColor(Color.parseColor("#ffffff"));
        email.setBackgroundColor(Color.parseColor("#ffffff"));
        phone.setText(CurrentSession.getPhone());
        email.setText(CurrentSession.getEmail());
        errors.setText("");
        super.onResume();
    }

    class Update2AsyncTask extends AsyncTask<String, String, String> {
        String _phone, _email, answer, server = ConectionHealper.getUrl() + "/update";
        @Override
        protected void onPreExecute() {
            _phone = phone.getText().toString();
            _email = email.getText().toString();
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            HashMap<String, String> postDataParams = new HashMap<String, String>();
            postDataParams.put("id", "" + CurrentSession.getUserId());
            postDataParams.put("type", "contacts");
            postDataParams.put("phone", _phone);
            postDataParams.put("email", _email);
            answer = ConectionHealper.performGetCall(server, postDataParams);
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
