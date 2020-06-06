/**
 * Активность со сменой аккаунта.
 * Пока пустая.
 * Потом можно удалить.
 * **/

package com.example.helpforcurator.mainmenu;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.helpforcurator.MainActivity;
import com.example.helpforcurator.R;

public class ReAccountActivity extends AppCompatActivity {
    /** view элемненты **/
    private Button exit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /** получение view **/
        setContentView(R.layout.activity_re_akkaunt);
        exit = (Button) findViewById(R.id.exit);
        /** обработка нажатий **/
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ReAccountActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
