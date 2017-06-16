package com.mamac.ta7weel;

import com.google.gson.JsonObject;

/**
 * Created by mac on 3/20/17.
 */

public class Rates {

    int flag_id,pos;
    String id,short_name,long_name,country,symbol,image,base_rate,value;
    boolean is_selected = false;



    Rates(JsonObject jsonObject){

        this.id = jsonObject.get("id").getAsString();
        this.short_name = jsonObject.get("name_short").getAsString();
        this.long_name = jsonObject.get("name_long").getAsString();
        this.country = jsonObject.get("country").getAsString();
        this.symbol = jsonObject.get("symbol").getAsString();
        this.image = jsonObject.get("image").getAsString();
        this.base_rate = jsonObject.get("convertion_rate").getAsString();
        this.value = "1.0000";
    }

    Rates(int flag_id,String base_rate,String value,String full_name,String name){
        this.short_name = name;
        this.flag_id = flag_id;
        this.base_rate = base_rate;
        this.value = value;
        this.long_name = full_name;
        this.value = "1.0000";
    }
}
