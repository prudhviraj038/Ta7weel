package com.mamac.ta7weel;

import com.google.gson.JsonObject;

/**
 * Created by mac on 3/20/17.
 */

public class ExchangeLocations {

    String id,title,title_ar,latitude,longitude,description,description_ar;



    ExchangeLocations(JsonObject jsonObject){

        this.id = jsonObject.get("id").getAsString();
        this.title = jsonObject.get("title").getAsString();
        this.title_ar = jsonObject.get("title_ar").getAsString();
        this.latitude = jsonObject.get("latitude").getAsString();
        this.longitude = jsonObject.get("longitude").getAsString();
        this.description = jsonObject.get("description").getAsString();
        this.description_ar = jsonObject.get("description_ar").getAsString();

    }




}
