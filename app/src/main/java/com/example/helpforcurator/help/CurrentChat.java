package com.example.helpforcurator.help;

public class CurrentChat {
    static private int id_chat = 1;
    static private String chatName;

    public static int getId_chat() {
        return id_chat;
    }

    public static void setId_chat(int _id_chat) {
        id_chat = _id_chat;
    }

    public static String getChatName() {
        return chatName;
    }

    public static void setChatName(String _chatName) {
        chatName = _chatName;
    }
}
