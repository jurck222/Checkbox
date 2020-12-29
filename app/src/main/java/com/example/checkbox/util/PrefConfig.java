package com.example.checkbox.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.checkbox.checkbox.checkbox;

public class PrefConfig {

    private static final String IME_PREFERENCE = "package com.example.checkbox.util";
    private static final String PREF_OBKLJUKANE_KEY = "pref_obkljukane_key";
    private static final String PREF_VSE_KEY = "pref_vse_key";
    private static final String PREF_IZBRISANO_KEY = "pref_izbrisano_key";
    private static final String PREF_TOTAL_KEY = "pref_total_key";
    private static final String PREF_NMODE_KEY = "pref_nmode_key";

    public static void setObkljukane(Context context, int total){
        SharedPreferences pref = context.getSharedPreferences(IME_PREFERENCE,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt(PREF_OBKLJUKANE_KEY, total);
        editor.apply();
    }

    public static int getObkljukane(Context context) {
        SharedPreferences pref = context.getSharedPreferences(IME_PREFERENCE,Context.MODE_PRIVATE);
        return pref.getInt(PREF_OBKLJUKANE_KEY,0);
    }

    public static void setVse(Context context, int total){
        SharedPreferences pref = context.getSharedPreferences(IME_PREFERENCE,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt(PREF_VSE_KEY, total);
        editor.apply();
    }

    public static int getVse(Context context) {
        SharedPreferences pref = context.getSharedPreferences(IME_PREFERENCE,Context.MODE_PRIVATE);
        return pref.getInt(PREF_VSE_KEY,0);
    }

    public static void setIzbrisano(Context context, int total){
        SharedPreferences pref = context.getSharedPreferences(IME_PREFERENCE,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt(PREF_IZBRISANO_KEY, total);
        editor.apply();
    }

    public static int getIzbrisano(Context context) {
        SharedPreferences pref = context.getSharedPreferences(IME_PREFERENCE,Context.MODE_PRIVATE);
        return pref.getInt(PREF_IZBRISANO_KEY,0);
    }

    public static void setTotal(Context context, int total){
        SharedPreferences pref = context.getSharedPreferences(IME_PREFERENCE,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt(PREF_TOTAL_KEY, total);
        editor.apply();
    }

    public static int getTotal(Context context) {
        SharedPreferences pref = context.getSharedPreferences(IME_PREFERENCE,Context.MODE_PRIVATE);
        return pref.getInt(PREF_TOTAL_KEY,0);
    }


    public static void setNmode(Context context, int total){
        SharedPreferences pref = context.getSharedPreferences(IME_PREFERENCE,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt(PREF_NMODE_KEY, total);
        editor.apply();
    }

    public static int getNmode(Context context) {
        SharedPreferences pref = context.getSharedPreferences(IME_PREFERENCE,Context.MODE_PRIVATE);
        return pref.getInt(PREF_NMODE_KEY,-1);
    }

}
