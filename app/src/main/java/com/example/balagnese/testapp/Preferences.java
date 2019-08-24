package com.example.balagnese.testapp;

import android.content.Context;
import android.content.SharedPreferences;

public class Preferences {

    public static final String APP_PREFERENCES = "mysettings";


    private static SharedPreferences settings = null;
    private static SharedPreferences.Editor editor = null;
    private static Context context = null;



    private static void init(){
        context = MainApplication.getContext();
        settings = context.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        editor = settings.edit();
        editor.apply();
    }

    public static void addProperty( String name, String value ){
        if( settings == null ){
            init();
        }
        editor.putString( name, value );
        editor.commit();
    }

    public static String getProperty( String name ){
        if( settings == null ){
            init();
        }
        return settings.getString( name, null );
    }

    public static void editProperty(String name, String value){
        if (settings == null){
            init();
        }
        editor.remove(name);
        editor.putString(name, value);
        editor.commit();
    }

}
