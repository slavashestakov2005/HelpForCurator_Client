/**
 * Активность с информацией о пользователе.
 * Пока пустая.
 * **/

package com.example.helpforcurator.mainmenu;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.helpforcurator.R;
import com.example.helpforcurator.help.CurrentSession;
import com.example.helpforcurator.name.SectionsPagerAdapter;
import com.google.android.material.tabs.TabLayout;

public class NameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /** подключение Tab адаптера **/
        setContentView(R.layout.activity_name);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
        /** изменение шапки **/
        ActionBar actionbar = getSupportActionBar();
        actionbar.setTitle(CurrentSession.getSurname() + " " + CurrentSession.getName());
        actionbar.setDisplayHomeAsUpEnabled(true);
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