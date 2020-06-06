package com.example.helpforcurator.help;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.helpforcurator.R;

import java.util.List;

public class ChatListAdapter extends BaseAdapter {

    private List<ChatItem> listData;
    private LayoutInflater layoutInflater;
    private Context context;

    public ChatListAdapter(Context aContext, List<ChatItem> listData) {
        this.context = aContext;
        this.listData = listData;
        layoutInflater = LayoutInflater.from(aContext);
    }

    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public Object getItem(int position) {
        return listData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.chat_item, null);
            holder = new ViewHolder();
            holder.name = (TextView) convertView.findViewById(R.id.chat_name);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        ChatItem chatItem = this.listData.get(position);
        holder.name.setText(chatItem.getName());

        return convertView;
    }

    static class ViewHolder {
        TextView name;
    }
}
