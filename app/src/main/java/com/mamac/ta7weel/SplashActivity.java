package com.mamac.ta7weel;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import org.json.JSONArray;
import org.json.JSONException;

/**
 * Created by mac on 6/9/17.
 */

public class SplashActivity extends Activity{


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.splashactivity);

               get_settings();

    }


    private void get_settings(){

        Ion.with(this)
                .load(Session.SERVER_URL+"settings.php")
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {


                        if(e!=null){

                            JsonParser jsonParser = new JsonParser();

                            if(!Session.getSETTINGS(SplashActivity.this).equals("-1")) {

                                AppController.getInstance().settings = (JsonObject) jsonParser.parse(Session.getSETTINGS(SplashActivity.this));;
                            }
                            e.printStackTrace();

                        }else{
                              AppController.getInstance().settings=result;
                            Session.setSETTINGS(SplashActivity.this,result.toString());

                        }

                        get_rates();

                    }
                });
    }


    private void get_rates(){

        Ion.with(this)
                .load(Session.SERVER_URL+"currency.php")
                .asJsonArray()
                .setCallback(new FutureCallback<JsonArray>() {
                    @Override
                    public void onCompleted(Exception e, JsonArray result) {

                        if(e!=null){

                            JsonParser jsonParser = new JsonParser();
                            if(!Session.getRATES(SplashActivity.this).equals("-1")) {

                                AppController.getInstance().rates = (JsonArray) jsonParser.parse(Session.getRATES(SplashActivity.this));;
                            }

                            e.printStackTrace();
                        }else{

                            AppController.getInstance().rates=result;
                            Session.setRATES(SplashActivity.this,result.toString());
                            Session.setlastupdate(SplashActivity.this,Session.getCurrentTimeStamp());
                        }

                        get_prices();
                    }
                });

    }

    private void get_prices(){

        Ion.with(this)
                .load(Session.SERVER_URL+"gold-prices.php")
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {

                        if(e!=null){

                            JsonParser jsonParser = new JsonParser();
                            if(!Session.getPRICES(SplashActivity.this).equals("-1")) {

                                AppController.getInstance().prices = (JsonObject) jsonParser.parse(Session.getPRICES(SplashActivity.this));;
                            }

                            e.printStackTrace();
                        }else{

                            AppController.getInstance().prices=result;
                            Session.setPRICES(SplashActivity.this,result.toString());



                        }

                        String selected_ch = Session.getCurrencies(SplashActivity.this);
                        Log.e("selected_ch_splash",selected_ch);

                        if(!selected_ch.equals("-1")){
                            try {
                                JSONArray jsonArray = new JSONArray(selected_ch);

                                AppController.getInstance().selected_channels.clear();
                                for(int i=0;i<jsonArray.length();i++){

                                    AppController.getInstance().selected_channels.add(jsonArray.get(i).toString());
                                }

                            } catch (JSONException e1) {
                                e1.printStackTrace();
                            }
                        }else{

                            AppController.getInstance().selected_channels.add("USD");
                            AppController.getInstance().selected_channels.add("EUR");
                            AppController.getInstance().selected_channels.add("JPY");
                            AppController.getInstance().selected_channels.add("GBP");
                            AppController.getInstance().selected_channels.add("AUD");
                            AppController.getInstance().selected_channels.add("KWD");
                        }




                        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();

                    }
                });

    }


}
