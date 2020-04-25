package com.example.androidregisterandlogin;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.google.android.material.behavior.HideBottomViewOnScrollBehavior;

import java.util.HashMap;

public class SessionManager {

    SharedPreferences sharedPreferences;
    public SharedPreferences.Editor editor;
    public Context context;
    int PRIVATE_MODE = 0;

    private static final String PREF_NAME = "LOGIN";
    private static final String LOGIN = "IS_LOGIN";

    public static final String NAME = "NAME";
    public static final String EMAIL = "EMAIL";
    public static final String LASTNAME = "LASTNAME";
    public static final String SURNAME = "SURNAME";
    public static final String PHONENUMBER = "PHONENUMBER";
    public static final String EXPERTISE = "EXPERTISE";
    public static final String LOCATION = "LOCATION";


    public SessionManager(Context context){
        this.context = context;
        sharedPreferences = context.getSharedPreferences(PREF_NAME,PRIVATE_MODE);
        editor = sharedPreferences.edit();

    }

    public void createSession(String name,String email,String lastname,String surname,String phonenumber,String expertise,String location){
        editor.putBoolean(LOGIN,true);
        editor.putString(NAME,name);
        editor.putString(EMAIL,email);
        editor.putString(LASTNAME,lastname);
        editor.putString(SURNAME,surname);
        editor.putString(PHONENUMBER,phonenumber);
        editor.putString(EXPERTISE,expertise);
        editor.putString(LOCATION,location);
        editor.apply();
    }


    public  boolean isLoggin(){
        return sharedPreferences.getBoolean(LOGIN,false);
    }

    public void checkLogin(){
        if(!this.isLoggin()){
            Intent i = new Intent(context,LoginActivity.class);
            context.startActivity(i);
            ((HomeActivity) context).finish();
        }
    }

    public HashMap<String,String>  getUserDetails(){
        HashMap<String,String> user = new HashMap<>();
        user.put(NAME,sharedPreferences.getString(NAME,null));
        user.put(EMAIL,sharedPreferences.getString(EMAIL,null));
        user.put(LASTNAME,sharedPreferences.getString(LASTNAME,null));
        user.put(SURNAME,sharedPreferences.getString(SURNAME,null));
        user.put(PHONENUMBER,sharedPreferences.getString(PHONENUMBER,null));
        user.put(EXPERTISE,sharedPreferences.getString(EXPERTISE,null));
        user.put(LOCATION,sharedPreferences.getString(LOCATION,null));


        return  user;
    }

    public void logout(){
        editor.clear();
        editor.commit();

        Intent i = new Intent(context,LoginActivity.class);
        context.startActivity(i);
        ((HomeActivity) context).finish();

    }


}
