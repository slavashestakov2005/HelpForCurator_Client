package com.example.helpforcurator.mainmenu;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.helpforcurator.R;
import com.example.helpforcurator.help.CurrentSession;
import com.example.helpforcurator.help.FoneService;

public class MenuActivity extends AppCompatActivity {
    TableRow tname, ttime_table, ttechers, tuspevaemost, tsend, titog_mark, tinformation, tolimps, tre_akkaunt;
    Button name, time_table, techers, uspevaemost, send, itog_mark, information, olimps, re_akkaunt;
    TextView text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        CurrentSession.setProgramStart(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(new Intent(this, FoneService.class));
        } else {
            startService(new Intent(this, FoneService.class));
        }
        name = (Button) findViewById(R.id.name);
        time_table = (Button) findViewById(R.id.time_table);
        techers = (Button) findViewById(R.id.techers);
        uspevaemost = (Button) findViewById(R.id.uspevaemost);
        send = (Button) findViewById(R.id.send);
        itog_mark = (Button) findViewById(R.id.itog_mark);
        information = (Button) findViewById(R.id.information);
        olimps = (Button) findViewById(R.id.olimp);
        re_akkaunt = (Button) findViewById(R.id.re_akkaunt);
        /*********************Table rows**********************************/
        tname = (TableRow) findViewById(R.id.tname);
        ttime_table = (TableRow) findViewById(R.id.ttime_table);
        ttechers = (TableRow) findViewById(R.id.ttechers);
        tuspevaemost = (TableRow) findViewById(R.id.tuspevaemost);
        tsend = (TableRow) findViewById(R.id.tsend);
        titog_mark = (TableRow) findViewById(R.id.titog_mark);
        tinformation = (TableRow) findViewById(R.id.tinformation);
        tolimps = (TableRow) findViewById(R.id.tolimp);
        tre_akkaunt = (TableRow) findViewById(R.id.tre_akkaunt);
        name.setText(CurrentSession.getSurname() + " " + CurrentSession.getName());
        name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name.setBackgroundColor(getResources().getColor(R.color.buttonActivity));
                tname.setBackgroundColor(getResources().getColor(R.color.buttonActivity));
                Intent intent = new Intent(MenuActivity.this, NameActivity.class);
                startActivity(intent);
            }
        });
        tname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name.setBackgroundColor(getResources().getColor(R.color.buttonActivity));
                tname.setBackgroundColor(getResources().getColor(R.color.buttonActivity));
                Intent intent = new Intent(MenuActivity.this, NameActivity.class);
                startActivity(intent);
            }
        });
        time_table.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                time_table.setBackgroundColor(getResources().getColor(R.color.buttonActivity));
                ttime_table.setBackgroundColor(getResources().getColor(R.color.buttonActivity));
                Intent intent = new Intent(MenuActivity.this, Time_tableActivity.class);
                startActivity(intent);
            }
        });
        ttime_table.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                time_table.setBackgroundColor(getResources().getColor(R.color.buttonActivity));
                ttime_table.setBackgroundColor(getResources().getColor(R.color.buttonActivity));
                Intent intent = new Intent(MenuActivity.this, Time_tableActivity.class);
                startActivity(intent);
            }
        });
        techers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                techers.setBackgroundColor(getResources().getColor(R.color.buttonActivity));
                ttechers.setBackgroundColor(getResources().getColor(R.color.buttonActivity));
                Intent intent = new Intent(MenuActivity.this, TeachersActivity.class);
                startActivity(intent);
            }
        });
        ttechers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                techers.setBackgroundColor(getResources().getColor(R.color.buttonActivity));
                ttechers.setBackgroundColor(getResources().getColor(R.color.buttonActivity));
                Intent intent = new Intent(MenuActivity.this, TeachersActivity.class);
                startActivity(intent);
            }
        });
        uspevaemost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uspevaemost.setBackgroundColor(getResources().getColor(R.color.buttonActivity));
                tuspevaemost.setBackgroundColor(getResources().getColor(R.color.buttonActivity));
                Intent intent = new Intent(MenuActivity.this, UspevaemostActivity.class);
                startActivity(intent);
            }
        });
        tuspevaemost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uspevaemost.setBackgroundColor(getResources().getColor(R.color.buttonActivity));
                tuspevaemost.setBackgroundColor(getResources().getColor(R.color.buttonActivity));
                Intent intent = new Intent(MenuActivity.this, UspevaemostActivity.class);
                startActivity(intent);
            }
        });
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                send.setBackgroundColor(getResources().getColor(R.color.buttonActivity));
                tsend.setBackgroundColor(getResources().getColor(R.color.buttonActivity));
                Intent intent = new Intent(MenuActivity.this, SendActivity.class);
                startActivity(intent);
            }
        });
        tsend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                send.setBackgroundColor(getResources().getColor(R.color.buttonActivity));
                tsend.setBackgroundColor(getResources().getColor(R.color.buttonActivity));
                Intent intent = new Intent(MenuActivity.this, SendActivity.class);
                startActivity(intent);
            }
        });
        itog_mark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itog_mark.setBackgroundColor(getResources().getColor(R.color.buttonActivity));
                titog_mark.setBackgroundColor(getResources().getColor(R.color.buttonActivity));
                Intent intent = new Intent(MenuActivity.this, Itog_markActivity.class);
                startActivity(intent);
            }
        });
        titog_mark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itog_mark.setBackgroundColor(getResources().getColor(R.color.buttonActivity));
                titog_mark.setBackgroundColor(getResources().getColor(R.color.buttonActivity));
                Intent intent = new Intent(MenuActivity.this, Itog_markActivity.class);
                startActivity(intent);
            }
        });
        information.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                information.setBackgroundColor(getResources().getColor(R.color.buttonActivity));
                tinformation.setBackgroundColor(getResources().getColor(R.color.buttonActivity));
                Intent intent = new Intent(MenuActivity.this, InformationActivity.class);
                startActivity(intent);
            }
        });
        tinformation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                information.setBackgroundColor(getResources().getColor(R.color.buttonActivity));
                tinformation.setBackgroundColor(getResources().getColor(R.color.buttonActivity));
                Intent intent = new Intent(MenuActivity.this, InformationActivity.class);
                startActivity(intent);
            }
        });
        olimps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                olimps.setBackgroundColor(getResources().getColor(R.color.buttonActivity));
                tolimps.setBackgroundColor(getResources().getColor(R.color.buttonActivity));
                Intent intent = new Intent(MenuActivity.this, OlimpsActivity.class);
                startActivity(intent);
            }
        });
        tolimps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                olimps.setBackgroundColor(getResources().getColor(R.color.buttonActivity));
                tolimps.setBackgroundColor(getResources().getColor(R.color.buttonActivity));
                Intent intent = new Intent(MenuActivity.this, OlimpsActivity.class);
                startActivity(intent);
            }
        });
        re_akkaunt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                re_akkaunt.setBackgroundColor(getResources().getColor(R.color.buttonActivity));
                tre_akkaunt.setBackgroundColor(getResources().getColor(R.color.buttonActivity));
                finish();
            }
        });
        tre_akkaunt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                re_akkaunt.setBackgroundColor(getResources().getColor(R.color.buttonActivity));
                tre_akkaunt.setBackgroundColor(getResources().getColor(R.color.buttonActivity));
                finish();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        /****************Buttons***************************/
        name = (Button) findViewById(R.id.name);
        name.setBackgroundColor(getResources().getColor(R.color.button));
        name.setTextColor(Color.BLACK);
        time_table = (Button) findViewById(R.id.time_table);
        time_table.setBackgroundColor(getResources().getColor(R.color.button));
        time_table.setTextColor(Color.BLACK);
        techers = (Button) findViewById(R.id.techers);
        techers.setBackgroundColor(getResources().getColor(R.color.button));
        techers.setTextColor(Color.BLACK);
        uspevaemost = (Button) findViewById(R.id.uspevaemost);
        uspevaemost.setBackgroundColor(getResources().getColor(R.color.button));
        uspevaemost.setTextColor(Color.BLACK);
        send = (Button) findViewById(R.id.send);
        send.setBackgroundColor(getResources().getColor(R.color.button));
        send.setTextColor(Color.BLACK);
        itog_mark = (Button) findViewById(R.id.itog_mark);
        itog_mark.setBackgroundColor(getResources().getColor(R.color.button));
        itog_mark.setTextColor(Color.BLACK);
        information = (Button) findViewById(R.id.information);
        information.setBackgroundColor(getResources().getColor(R.color.button));
        information.setTextColor(Color.BLACK);
        olimps = (Button) findViewById(R.id.olimp);
        olimps.setBackgroundColor(getResources().getColor(R.color.button));
        olimps.setTextColor(Color.BLACK);
        re_akkaunt = (Button) findViewById(R.id.re_akkaunt);
        re_akkaunt.setBackgroundColor(getResources().getColor(R.color.button));
        re_akkaunt.setTextColor(Color.BLACK);
        /*********************Table rows**********************************/
        tname = (TableRow) findViewById(R.id.tname);
        tname.setBackgroundColor(getResources().getColor(R.color.button));
        ttime_table = (TableRow) findViewById(R.id.ttime_table);
        ttime_table.setBackgroundColor(getResources().getColor(R.color.button));
        ttechers = (TableRow) findViewById(R.id.ttechers);
        ttechers.setBackgroundColor(getResources().getColor(R.color.button));
        tuspevaemost = (TableRow) findViewById(R.id.tuspevaemost);
        tuspevaemost.setBackgroundColor(getResources().getColor(R.color.button));
        tsend = (TableRow) findViewById(R.id.tsend);
        tsend.setBackgroundColor(getResources().getColor(R.color.button));
        titog_mark = (TableRow) findViewById(R.id.titog_mark);
        titog_mark.setBackgroundColor(getResources().getColor(R.color.button));
        tinformation = (TableRow) findViewById(R.id.tinformation);
        tinformation.setBackgroundColor(getResources().getColor(R.color.button));
        tolimps = (TableRow) findViewById(R.id.tolimp);
        tolimps.setBackgroundColor(getResources().getColor(R.color.button));
        tre_akkaunt = (TableRow) findViewById(R.id.tre_akkaunt);
        tre_akkaunt.setBackgroundColor(getResources().getColor(R.color.button));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        CurrentSession.setProgramStart(false);
    }
}