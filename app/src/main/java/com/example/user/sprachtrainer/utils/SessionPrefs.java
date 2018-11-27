package com.example.user.sprachtrainer.utils;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.se.omapi.Session;

import com.example.user.sprachtrainer.MainActivity;


public class SessionPrefs {

    //Storage File
    public static final String SHARED_PREF_NAME = "larntech";

    //Username
    public static final String USER_NAME = "username";

    public static SessionPrefs mInstance;

    public static Context mCtx;


    public SessionPrefs(Context context) {
        mCtx = context;
    }


    public static synchronized SessionPrefs getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SessionPrefs(context);
        }
        return mInstance;
    }


    //method to store user data
    public void storeUserName(String names) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(USER_NAME, names);
        editor.commit();
    }

    //check if user is logged in
    public boolean isLoggedIn() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(USER_NAME, null) != null;
    }


    //find logged in user
    public String LoggedInUser() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(USER_NAME, null);

    }


    //Logout user
    public void logout() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();
        mCtx.startActivity(new Intent(mCtx, MainActivity.class));
    }
}
