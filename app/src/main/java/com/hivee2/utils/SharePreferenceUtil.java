package com.hivee2.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

/**
 * Created by heshixiyang on 2016/5/3.
 */
public class SharePreferenceUtil {
    private static SharedPreferences mSharedPreferences = null;
    private static SharedPreferences.Editor mEditor = null;

    public static void init(Context context){
        if (null == mSharedPreferences) {
            mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        }
    }

    public static void commitStringNoUserName(String key, String value){
        mEditor = mSharedPreferences.edit();
        mEditor.putString(key, value);
        mEditor.apply();
    }

    public static String getStringNoUserName(String key, String failValue){
        return mSharedPreferences.getString(key, failValue);
    }

    public static boolean getBooleanNoUserName(String key,boolean failValue){
        return mSharedPreferences.getBoolean(key, failValue);
    }

    public static void commitBooleanNoUserName(String key, boolean value){
        mEditor = mSharedPreferences.edit();
        mEditor.putBoolean(key, value);
        mEditor.apply();
    }

    public static void NoUserName(String key, int value){
        mEditor = mSharedPreferences.edit();
        mEditor.putInt(key, value);
        mEditor.apply();
    }

    public static int getIntNoUserName(String key, int failValue){
        return mSharedPreferences.getInt(key, failValue);
    }

    public static void commitLongNoUserName(String key, long value){
        mEditor = mSharedPreferences.edit();
        mEditor.putLong(key, value);
        mEditor.apply();
    }

    public static long getLongNoUserName(String key, long failValue) {
        return mSharedPreferences.getLong(key, failValue);
    }


    public static void setUsername(String u){
        mEditor = mSharedPreferences.edit();
        mEditor.putString("userName", u);
        mEditor.apply();
    }

    public static String getUsername(){
        return mSharedPreferences.getString("userName","");
    }


    protected static void removeKey(String key){
        key=getUsername()+key;
        mEditor = mSharedPreferences.edit();
        mEditor.remove(key);
        mEditor.apply();
    }

    protected static void removeAll(){
        mEditor = mSharedPreferences.edit();
        mEditor.clear();
        mEditor.apply();
    }

    public static void commitString(String key, String value){
        key=getUsername()+key;
        Log.d("SharePreferen token", key);
        mEditor = mSharedPreferences.edit();
        mEditor.putString(key, value);
        mEditor.apply();
    }

    public static String getString(String key, String failValue){
        key=getUsername()+key;
        return mSharedPreferences.getString(key, failValue);
    }

    public static void commitInt(String key, int value){
        key=getUsername()+key;
        mEditor = mSharedPreferences.edit();
        mEditor.putInt(key, value);
        mEditor.apply();
    }

    public static int getInt(String key, int failValue){
        key=getUsername()+key;
        return mSharedPreferences.getInt(key, failValue);
    }

    public static void commitLong(String key, long value){
        key=getUsername()+key;
        mEditor = mSharedPreferences.edit();
        mEditor.putLong(key, value);
        mEditor.apply();
    }

    public static long getLong(String key, long failValue) {
        key=getUsername()+key;
        return mSharedPreferences.getLong(key, failValue);
    }

    public static boolean getBoolean(String key,boolean failValue){
        key=getUsername()+key;
        return mSharedPreferences.getBoolean(key, failValue);
    }

    public static void commitBoolean(String key, boolean value){
        key=getUsername()+key;
        mEditor = mSharedPreferences.edit();
        mEditor.putBoolean(key, value);
        mEditor.apply();
    }

}
