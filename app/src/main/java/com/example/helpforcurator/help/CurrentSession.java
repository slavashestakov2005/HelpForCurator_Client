package com.example.helpforcurator.help;

import java.util.ArrayList;

public class CurrentSession {
    static private boolean programStart = true;
    static private int userId = -1;
    static private String login, password, phone, email, name, surname, middlename, chatTimeUpdate;

    static public void setSession(ArrayList<String> mas){
        CurrentSession.userId = Integer.parseInt(mas.get(0));
        CurrentSession.login = mas.get(1);
        CurrentSession.password = mas.get(2);
        CurrentSession.phone = mas.get(3);
        CurrentSession.email = mas.get(4);
        CurrentSession.name = mas.get(5);
        CurrentSession.surname = mas.get(6);
        CurrentSession.middlename = mas.get(7);
    }

    static public void setSession(int userId, String login, String password, String phone, String email, String name, String surname, String middlename) {
        CurrentSession.userId = userId;
        CurrentSession.login = login;
        CurrentSession.password = password;
        CurrentSession.phone = phone;
        CurrentSession.name = name;
        CurrentSession.surname = surname;
        CurrentSession.middlename = middlename;
    }

    static public String getString(){
        return "" + userId + " | " + login + " | " + password + " | " + phone + " | " + email + " | " + name + " | " + surname + " | " + middlename + "|\n";
    }

    public static int getUserId() {
        return userId;
    }

    public static void setUserId(int userId) {
        CurrentSession.userId = userId;
    }

    public static String getLogin() {
        return login;
    }

    public static void setLogin(String login) {
        CurrentSession.login = login;
    }

    public static String getPassword() {
        return password;
    }

    public static void setPassword(String password) {
        CurrentSession.password = password;
    }

    public static String getPhone() {
        return phone;
    }

    public static void setPhone(String phone) {
        CurrentSession.phone = phone;
    }

    public static String getEmail() {
        return email;
    }

    public static void setEmail(String email) {
        CurrentSession.email = email;
    }

    public static String getName() {
        return name;
    }

    public static void setName(String name) {
        CurrentSession.name = name;
    }

    public static String getSurname() {
        return surname;
    }

    public static void setSurname(String surname) {
        CurrentSession.surname = surname;
    }

    public static String getMiddlename() {
        return middlename;
    }

    public static void setMiddlename(String middlename) {
        CurrentSession.middlename = middlename;
    }

    public static boolean isProgramStart() {
        return programStart;
    }

    public static void setProgramStart(boolean programStart) {
        CurrentSession.programStart = programStart;
    }

    public static String getChatTimeUpdate() {
        return chatTimeUpdate;
    }

    public static void setChatTimeUpdate(String chatTimeUpdate) {
        CurrentSession.chatTimeUpdate = chatTimeUpdate;
    }
}
