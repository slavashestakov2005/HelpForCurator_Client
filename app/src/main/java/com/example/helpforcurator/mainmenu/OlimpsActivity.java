/**
 * Активность с олимпиадами.
 * Пока пустая.
 * **/

package com.example.helpforcurator.mainmenu;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.helpforcurator.R;

public class OlimpsActivity extends AppCompatActivity {
    /** view элемненты **/
    private Button exit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /** получение view **/
        setContentView(R.layout.activity_olimps);
        exit = (Button) findViewById(R.id.exit);
        /** обработка нажатий **/
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        /** изменение шапки **/
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            default:
                finish();
                return true;
        }
    }
}
