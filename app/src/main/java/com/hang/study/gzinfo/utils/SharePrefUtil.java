package com.hang.study.gzinfo.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by hang on 16/6/1.
 */
public class SharePrefUtil {
    public static final String xmlName="userinfo";
    public static void setString(Context context,String key,String value){
        SharedPreferences sp=context.getSharedPreferences(xmlName,Context.MODE_PRIVATE);
        sp.edit().putString(key,value).commit();
    }
    public static String getString(Context context,String key,String defStr) {
        SharedPreferences sp=context.getSharedPreferences(xmlName,Context.MODE_PRIVATE);
        String str=sp.getString(key,defStr);
        return str;
    }
    public static void setBoolean(Context context,String key,Boolean value){
        SharedPreferences sp=context.getSharedPreferences(xmlName,Context.MODE_PRIVATE);
        sp.edit().putBoolean(key,value).commit();
    }
    public static Boolean getBoolean(Context context,String key,Boolean defStr) {
        SharedPreferences sp=context.getSharedPreferences(xmlName, Context.MODE_PRIVATE);
        Boolean res=sp.getBoolean(key,defStr);
        return res;
    }

}
