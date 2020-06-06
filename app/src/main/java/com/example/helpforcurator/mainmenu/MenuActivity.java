/**
 * Активность с главным меню.
 * Потом можно заменить на NavigationActivity.
 * **/

package com.example.helpforcurator.mainmenu;

import android.content.Intent;
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
    /** view элемненты **/
    private TableRow t_name, t_timeTable, t_teachers, t_uspevaemost, t_send, t_itogMark, t_information, t_olimps, t_reAccount;
    private Button name, timeTable, teachers, uspevaemost, send, itogMark, information, olimps, reAccount;
    private TextView text;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /** создание фонового процесса-сервиса **/
        createBackgroundProcess();
        /** получение view **/
        setContentView(R.layout.activity_menu);
        name = (Button) findViewById(R.id.name);
        timeTable = (Button) findViewById(R.id.time_table);
        teachers = (Button) findViewById(R.id.techers);
        uspevaemost = (Button) findViewById(R.id.uspevaemost);
        send = (Button) findViewById(R.id.send);
        itogMark = (Button) findViewById(R.id.itog_mark);
        information = (Button) findViewById(R.id.information);
        olimps = (Button) findViewById(R.id.olimp);
        reAccount = (Button) findViewById(R.id.re_akkaunt);
        t_name = (TableRow) findViewById(R.id.tname);
        t_timeTable = (TableRow) findViewById(R.id.ttime_table);
        t_teachers = (TableRow) findViewById(R.id.ttechers);
        t_uspevaemost = (TableRow) findViewById(R.id.tuspevaemost);
        t_send = (TableRow) findViewById(R.id.tsend);
        t_itogMark = (TableRow) findViewById(R.id.titog_mark);
        t_information = (TableRow) findViewById(R.id.tinformation);
        t_olimps = (TableRow) findViewById(R.id.tolimp);
        t_reAccount = (TableRow) findViewById(R.id.tre_akkaunt);
        name.setText(CurrentSession.getSurname() + " " + CurrentSession.getName());
        /** обработка нажатий **/
        name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name.setBackgroundColor(getResources().getColor(R.color.buttonActivity));
                t_name.setBackgroundColor(getResources().getColor(R.color.buttonActivity));
                Intent intent = new Intent(MenuActivity.this, NameActivity.class);
                startActivity(intent);
            }
        });
        t_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name.setBackgroundColor(getResources().getColor(R.color.buttonActivity));
                t_name.setBackgroundColor(getResources().getColor(R.color.buttonActivity));
                Intent intent = new Intent(MenuActivity.this, NameActivity.class);
                startActivity(intent);
            }
        });
        timeTable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timeTable.setBackgroundColor(getResources().getColor(R.color.buttonActivity));
                t_timeTable.setBackgroundColor(getResources().getColor(R.color.buttonActivity));
                Intent intent = new Intent(MenuActivity.this, TimeTableActivity.class);
                startActivity(intent);
            }
        });
        t_timeTable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timeTable.setBackgroundColor(getResources().getColor(R.color.buttonActivity));
                t_timeTable.setBackgroundColor(getResources().getColor(R.color.buttonActivity));
                Intent intent = new Intent(MenuActivity.this, TimeTableActivity.class);
                startActivity(intent);
            }
        });
        teachers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                teachers.setBackgroundColor(getResources().getColor(R.color.buttonActivity));
                t_teachers.setBackgroundColor(getResources().getColor(R.color.buttonActivity));
                Intent intent = new Intent(MenuActivity.this, TeachersActivity.class);
                startActivity(intent);
            }
        });
        t_teachers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                teachers.setBackgroundColor(getResources().getColor(R.color.buttonActivity));
                t_teachers.setBackgroundColor(getResources().getColor(R.color.buttonActivity));
                Intent intent = new Intent(MenuActivity.this, TeachersActivity.class);
                startActivity(intent);
            }
        });
        uspevaemost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uspevaemost.setBackgroundColor(getResources().getColor(R.color.buttonActivity));
                t_uspevaemost.setBackgroundColor(getResources().getColor(R.color.buttonActivity));
                Intent intent = new Intent(MenuActivity.this, UspevaemostActivity.class);
                startActivity(intent);
            }
        });
        t_uspevaemost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uspevaemost.setBackgroundColor(getResources().getColor(R.color.buttonActivity));
                t_uspevaemost.setBackgroundColor(getResources().getColor(R.color.buttonActivity));
                Intent intent = new Intent(MenuActivity.this, UspevaemostActivity.class);
                startActivity(intent);
            }
        });
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                send.setBackgroundColor(getResources().getColor(R.color.buttonActivity));
                t_send.setBackgroundColor(getResources().getColor(R.color.buttonActivity));
                Intent intent = new Intent(MenuActivity.this, SendActivity.class);
                startActivity(intent);
            }
        });
        t_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                send.setBackgroundColor(getResources().getColor(R.color.buttonActivity));
                t_send.setBackgroundColor(getResources().getColor(R.color.buttonActivity));
                Intent intent = new Intent(MenuActivity.this, SendActivity.class);
                startActivity(intent);
            }
        });
        itogMark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itogMark.setBackgroundColor(getResources().getColor(R.color.buttonActivity));
                t_itogMark.setBackgroundColor(getResources().getColor(R.color.buttonActivity));
                Intent intent = new Intent(MenuActivity.this, ItogMarkActivity.class);
                startActivity(intent);
            }
        });
        t_itogMark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itogMark.setBackgroundColor(getResources().getColor(R.color.buttonActivity));
                t_itogMark.setBackgroundColor(getResources().getColor(R.color.buttonActivity));
                Intent intent = new Intent(MenuActivity.this, ItogMarkActivity.class);
                startActivity(intent);
            }
        });
        information.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                information.setBackgroundColor(getResources().getColor(R.color.buttonActivity));
                t_information.setBackgroundColor(getResources().getColor(R.color.buttonActivity));
                Intent intent = new Intent(MenuActivity.this, InformationActivity.class);
                startActivity(intent);
            }
        });
        t_information.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                information.setBackgroundColor(getResources().getColor(R.color.buttonActivity));
                t_information.setBackgroundColor(getResources().getColor(R.color.buttonActivity));
                Intent intent = new Intent(MenuActivity.this, InformationActivity.class);
                startActivity(intent);
            }
        });
        olimps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                olimps.setBackgroundColor(getResources().getColor(R.color.buttonActivity));
                t_olimps.setBackgroundColor(getResources().getColor(R.color.buttonActivity));
                Intent intent = new Intent(MenuActivity.this, OlimpsActivity.class);
                startActivity(intent);
            }
        });
        t_olimps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                olimps.setBackgroundColor(getResources().getColor(R.color.buttonActivity));
                t_olimps.setBackgroundColor(getResources().getColor(R.color.buttonActivity));
                Intent intent = new Intent(MenuActivity.this, OlimpsActivity.class);
                startActivity(intent);
            }
        });
        reAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reAccount.setBackgroundColor(getResources().getColor(R.color.buttonActivity));
                t_reAccount.setBackgroundColor(getResources().getColor(R.color.buttonActivity));
                finish();
            }
        });
        t_reAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reAccount.setBackgroundColor(getResources().getColor(R.color.buttonActivity));
                t_reAccount.setBackgroundColor(getResources().getColor(R.color.buttonActivity));
                finish();
            }
        });
    }

    @Override
    protected void onStart() {
        /** создание исходного внешнего вида **/
        super.onStart();
        name.setBackgroundColor(getResources().getColor(R.color.button));
        timeTable.setBackgroundColor(getResources().getColor(R.color.button));
        teachers.setBackgroundColor(getResources().getColor(R.color.button));
        uspevaemost.setBackgroundColor(getResources().getColor(R.color.button));
        send.setBackgroundColor(getResources().getColor(R.color.button));
        itogMark.setBackgroundColor(getResources().getColor(R.color.button));
        information.setBackgroundColor(getResources().getColor(R.color.button));
        olimps.setBackgroundColor(getResources().getColor(R.color.button));
        reAccount.setBackgroundColor(getResources().getColor(R.color.button));
        t_name.setBackgroundColor(getResources().getColor(R.color.button));
        t_timeTable.setBackgroundColor(getResources().getColor(R.color.button));
        t_teachers.setBackgroundColor(getResources().getColor(R.color.button));
        t_uspevaemost.setBackgroundColor(getResources().getColor(R.color.button));
        t_send.setBackgroundColor(getResources().getColor(R.color.button));
        t_itogMark.setBackgroundColor(getResources().getColor(R.color.button));
        t_information.setBackgroundColor(getResources().getColor(R.color.button));
        t_olimps.setBackgroundColor(getResources().getColor(R.color.button));
        t_reAccount.setBackgroundColor(getResources().getColor(R.color.button));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        CurrentSession.setProgramStart(false);
    }

    /** метод для создания фонового процесса-обновления **/
    private void createBackgroundProcess() {
        CurrentSession.setProgramStart(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(new Intent(this, FoneService.class));
        } else {
            startService(new Intent(this, FoneService.class));
        }
    }
}