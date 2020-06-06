package com.example.helpforcurator.help;

public class ChatItem {
    private int id_chat;
    private String name;

    public ChatItem(int id_chat, String name) {
        this.id_chat = id_chat;
        this.name = name;
    }

    public int getId_chat() {
        return id_chat;
    }

    public void setId_chat(int id_chat) {
        this.id_chat = id_chat;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}

