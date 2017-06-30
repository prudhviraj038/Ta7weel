package com.mamac.ta7weel;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by mac on 6/9/17.
 */

public class Session {

    static String lan="lan";

    static String C_ID="c_id";

    static String RATES="r_id";
    static String SETTINGS="s_id";
    static String PRICES="p_id";
    static String Ref_Time="new_ref_time";
    static String up_Time="ref_time";
    static String selected_currency="selected_currency";


    static String sb_shake="sb_shake";
    static String sb_notify="sb_notify";


    static String SERVER_URL = "http://ta7weel.net/api/";
    static String MAPS_API_KEY = "AIzaSyD4KUFJ5-WdXL-yipGe27aJTcDkWki5hNI";

    public static void setLan(Context context, String c_id) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(lan, c_id);
        editor.apply();
    }

    public static String getLan(Context context){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString(lan, "ar");
    }

    public static void setCurrencyID(Context context, String c_id) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(C_ID, c_id);
        editor.apply();
    }

    public static String getCurrencyID(Context context){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString(C_ID, "USD");
    }

    public static void setCurrencies(Context context, String c_id) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(selected_currency, c_id);
        editor.apply();
    }

    public static String getCurrencies(Context context){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString(selected_currency, "-1");
    }


    public static void setRATES(Context context, String c_id) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(RATES, c_id);
        editor.apply();
    }

    public static String getRATES(Context context){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString(RATES, "-1");
    }


    public static void setSETTINGS(Context context, String c_id) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(SETTINGS, c_id);
        editor.apply();
    }

    public static String getSETTINGS(Context context){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString(SETTINGS, "-1");
    }

    public static void setPRICES(Context context, String c_id) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(PRICES, c_id);
        editor.apply();
    }

    public static String getPRICES(Context context){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString(PRICES, "-1");
    }


    public static void setRefreshTime(Context context, int minutes) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(Ref_Time, minutes);
        editor.apply();
    }

    public static int getRefreshTime(Context context){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        try {
            return sharedPreferences.getInt(Ref_Time, 1);
        }catch (Exception ex){
            return 1;
        }
    }

    public static void setSettings(Context context, String about_us) {
        try {
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("settings", about_us);
            editor.apply();
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public static String getSettings(Context context,String word) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        JSONObject jsonObject;
        String temp = "";
        try {
            jsonObject = new JSONObject(sharedPreferences.getString("settings", "-1"));
            temp = jsonObject.getString(word);
        } catch (JSONException e) {
            temp = word;
            e.printStackTrace();
        }

        return temp;
    }


    public static String getCurrentTimeStamp(){
        try {

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String currentDateTime = dateFormat.format(new Date()); // Find todays date

            return currentDateTime;
        } catch (Exception e) {
            e.printStackTrace();

            return "";
        }
    }


    public static void setlastupdate(Context context, String c_id) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(up_Time, c_id);
        editor.apply();
    }

    public static String getlastupdate(Context context){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString(up_Time, "");
    }


    public static String format_decimals(Float value){

        return String.format(Locale.ENGLISH,"%.4f",value);

    }


    public static void set_shake_status(Context context, Boolean c_id) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(sb_shake, c_id);
        editor.apply();
    }

    public static Boolean get_shake_status(Context context){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getBoolean(sb_shake, true);
    }


    public static void set_notify_status(Context context, Boolean c_id) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(sb_notify, c_id);
        editor.apply();
    }

    public static Boolean get_notify_status(Context context){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getBoolean(sb_notify, true);
    }


}
