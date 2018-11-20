package com.example.user.sprachtrainer.utils;

import android.se.omapi.Session;



public class SessionPrefs {

    private static SessionPrefs INSTANCE;

    public static SessionPrefs get(){
        if(INSTANCE == null){
            INSTANCE= new SessionPrefs();
        }
        return  INSTANCE;
    }
    private SessionPrefs(){

    }
}
