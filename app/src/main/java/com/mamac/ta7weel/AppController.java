package com.mamac.ta7weel;

import android.app.Application;
import android.os.Handler;
import android.util.Log;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import org.json.JSONArray;

import java.util.ArrayList;

/**
 * Created by mac on 6/10/17.
 */

public class AppController  extends Application{

    private static AppController mInstance;

    public  JsonObject settings;
    public JsonArray rates;
    public  JsonObject prices;
    public ArrayList<String> selected_channels;
    public  String last_updated_time;






    @Override
    public void onCreate() {
        super.onCreate();

        mInstance = this;
        selected_channels = new ArrayList<>();
        last_updated_time = Session.getlastupdate(this);



    }



    public static synchronized AppController getInstance() {
        return mInstance;
    }





}
