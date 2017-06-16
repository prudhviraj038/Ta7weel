package com.mamac.ta7weel;

import android.content.Context;

import java.io.Serializable;

/**
 * Created by sriven on 5/6/2016.
 */
public class MenuItem implements Serializable {
    String title,title_ar,id;
    int icon;

    boolean selected = false;

    MenuItem(String title, String title_ar, int id){
        this.title=title;
        this.title_ar=title_ar;
       // this.id = id;
        this.icon = id;
        selected=false;
    }

    MenuItem(String title, String title_ar, String id, boolean selected){
        this.title=title;
        this.title_ar=title_ar;
        this.selected = selected;
        this.id = id;
    }

    public String getTitle(Context context) {
      //  if(Settings.get_user_language(context).equals("ar"))
        //    return title_ar;
        //else
            return  title;
    }

    public boolean isSelected() {
        return selected;
    }
    public void setSelected(boolean selected) {
        this.selected = selected;
    }

}
