package com.sharnoxz.vacxinity.accounts;

import android.content.Context;
import android.content.SharedPreferences;


public class SharedPrefs{

    public static String readSharedSetting(Context ctx, String settingName, String defaultValue){
        SharedPreferences sharedPreferences = ctx.getSharedPreferences("sharedPrefs",Context.MODE_PRIVATE);
        return sharedPreferences.getString(settingName,defaultValue);
    }

    public static void saveSharedSetting(Context ctx,String settingName,String settingValue){
        SharedPreferences sharedPreferences = ctx.getSharedPreferences("sharedPrefs",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(settingName,settingValue);
        editor.apply();
    }

}
