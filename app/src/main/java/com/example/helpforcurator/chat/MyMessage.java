package com.example.helpforcurator.chat;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.helpforcurator.R;
import com.example.helpforcurator.help.CurrentSession;

public class MyMessage extends RelativeLayout {
    TextView author, text, time;

    public MyMessage(Context context) {
        super(context);
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.my_message, this);
        author = (TextView) findViewById(R.id.message_author);
        text = (TextView) findViewById(R.id.message_text);
        time = (TextView) findViewById(R.id.message_time);
        author.setText(CurrentSession.getSurname() + " " + CurrentSession.getName());
    }

    public void setMessageText(String _text){
        text.setText(_text);
    }

    public void setMessageTime(String _time){
        time.setText(_time);
    }
}
