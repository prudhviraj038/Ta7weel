package com.mamac.ta7weel;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

/**
 * Created by mac on 4/15/17.
 */

public class MyJsonObject {
    JsonObject jsonObject;
    MyJsonObject(JsonObject jsonObject){
        this.jsonObject = jsonObject;
    }

    public String getString(String key){

        return jsonObject.get(key).getAsString();
    }


    public JsonArray getJSONArray(String key){

        return jsonObject.get(key).getAsJsonArray();

    }

    public MyJsonObject getJSONObject(String key){

        return new MyJsonObject(jsonObject.get(key).getAsJsonObject());

    }

}
