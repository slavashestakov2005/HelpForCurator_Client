package com.example.helpforcurator.chat;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.helpforcurator.R;

public class SystemMessage extends RelativeLayout {
    /** view элемненты **/
    private TextView text;

    public SystemMessage(Context context) {
        super(context);
        /** получение view **/
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.system_message, this);
        text = (TextView) findViewById(R.id.message_text);
    }

    public void setMessageText(String _text){
        text.setText(_text);
    }

    public void setMessageTime(String _time){

    }
}
