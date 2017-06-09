package com.mamac.ta7weel;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class Chanel implements Serializable {

        String ch_id,parent_id,ch_title,ch_title_ar,ch_title_fr,ch_image,cover_image,count;
        boolean like = false;


        Chanel(MyJsonObject jsonObject1){
            try {
                ch_id=jsonObject1.getString("id");
                like = false;
                ch_title=jsonObject1.getString("title");
                ch_title_ar=jsonObject1.getString("title_ar");
                ch_title_fr=jsonObject1.getString("title_fr");
                ch_image=jsonObject1.getString("image");
                cover_image=jsonObject1.getString("cover_image");
                count = jsonObject1.getString("count");

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    public String get_ch_title(Context context){

        return ch_title;
    }


}