package com.mamac.ta7weel;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by mac on 6/9/17.
 */

public class Session {

    static String C_ID="c_id";

    public static void setCurrencyID(Context context, String c_id) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(C_ID, c_id);
        editor.apply();
    }

    public static String getCurrencyID(Context context){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString(C_ID, "-1");
    }

}
