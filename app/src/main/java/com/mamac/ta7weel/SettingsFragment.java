package com.mamac.ta7weel;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

/**
 * Created by mac on 3/18/17.
 */

public class SettingsFragment extends Fragment implements SeekBar.OnSeekBarChangeListener{
    LinearLayout whatsup_ll,fb_ll,insta_ll,twitter_ll,email_ll,sms_ll;
    private SeekBar mSeekBar;
    private TextView refresh_time_txt;


    public static SettingsFragment newInstance(int someInt) {
        SettingsFragment myFragment = new SettingsFragment();

        Bundle args = new Bundle();
        args.putInt("someInt", someInt);
        myFragment.setArguments(args);

        return myFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        
        View view = inflater.inflate(R.layout.settings_fragment, container, false);
        getdata();
        mSeekBar = (SeekBar) view.findViewById(R.id.seekBar1);
        mSeekBar.setOnSeekBarChangeListener(this);
        refresh_time_txt = (TextView) view.findViewById(R.id.refresh_time_txt);

        whatsup_ll=(LinearLayout)view.findViewById(R.id.sett_whatsup_ll);
        fb_ll=(LinearLayout)view.findViewById(R.id.sett_fb_ll);
        twitter_ll=(LinearLayout)view.findViewById(R.id.sett_twitter_ll);
        insta_ll=(LinearLayout)view.findViewById(R.id.sett_insta_ll);
        email_ll=(LinearLayout)view.findViewById(R.id.sett_email_ll);
        sms_ll=(LinearLayout)view.findViewById(R.id.sett_sms_ll);
        whatsup_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("text/plain");
                i.putExtra(Intent.EXTRA_SUBJECT, html2text(Session.getSettings(getActivity(),"title")));
                i.putExtra(Intent.EXTRA_TEXT, html2text(Session.getSettings(getActivity(),"playstore_link")));
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.setPackage("com.whatsapp");
                try {
                    startActivity(i);
                } catch (Exception e) {

                    try {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + "com.whatsapp")));
                    } catch (android.content.ActivityNotFoundException anfe) {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + "com.whatsapp")));
                    }
                }
            }
        });
        fb_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("text/plain");
                i.putExtra(Intent.EXTRA_SUBJECT, html2text(Session.getSettings(getActivity(),"title")));
                i.putExtra(Intent.EXTRA_TEXT, html2text(Session.getSettings(getActivity(),"playstore_link")));
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.setPackage("com.facebook.katana");
                try {
                    startActivity(i);
                } catch (Exception e) {

                    try {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + "com.facebook.katana")));
                    } catch (android.content.ActivityNotFoundException anfe) {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + "com.facebook.katana")));
                    }
                }

            }
        });
        twitter_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("text/plain");
                i.putExtra(Intent.EXTRA_SUBJECT, html2text(Session.getSettings(getActivity(),"title")));
                i.putExtra(Intent.EXTRA_TEXT, html2text(Session.getSettings(getActivity(),"playstore_link")));
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.setPackage("com.twitter.android");
                try {
                    startActivity(i);
                } catch (Exception e) {

                    try {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + "com.twitter.android")));
                    } catch (android.content.ActivityNotFoundException anfe) {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + "com.twitter.android")));
                    }
                }
            }
        });
        insta_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("text/plain");
                i.putExtra(Intent.EXTRA_SUBJECT, html2text(Session.getSettings(getActivity(),"title")));
                i.putExtra(Intent.EXTRA_TEXT, html2text(Session.getSettings(getActivity(),"playstore_link")));
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.setPackage("com.instagram.android");
                try {
                    startActivity(i);
                } catch (Exception e) {

                    try {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + "com.instagram.android")));
                    } catch (android.content.ActivityNotFoundException anfe) {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + "com.instagram.android")));
                    }
                }
            }
        });
        email_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("text/plain");
                i.putExtra(Intent.EXTRA_SUBJECT, html2text(Session.getSettings(getActivity(),"title")));
                i.putExtra(Intent.EXTRA_TEXT, html2text(Session.getSettings(getActivity(),"playstore_link")));
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.setPackage("com.google.android.gm");
                try {
                    startActivity(i);
                } catch (Exception e) {

                    try {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + "com.google.android.gm")));
                    } catch (android.content.ActivityNotFoundException anfe) {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + "com.google.android.gm")));
                    }

                }
            }
        });
        sms_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sendIntent = new Intent(Intent.ACTION_VIEW);
                sendIntent.putExtra("sms_body", html2text(Session.getSettings(getActivity(),"playstore_link")));
                sendIntent.setType("vnd.android-dir/mms-sms");
                startActivity(sendIntent);
            }
        });

        return view;
    }
    public static String html2text(String html) {
        // return Jsoup.parse(html).text();
        return html;
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
        Session.setRefreshTime(getActivity(),String.valueOf(i));
        if(i<2)
            refresh_time_txt.setText(String.valueOf(i+1)+ " Minute");
        else
            refresh_time_txt.setText(String.valueOf(i+1)+ " Minutes");

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
    private void getdata(){
        Ion.with(getActivity())
                .load("http://mamacgroup.com/ta7weel/api/settings.php")
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        // do stuff with the result or error
                        if(e==null) {
                            Log.e("response", result.toString());
                            try {
                                Session.setSettings(getActivity(), result.toString());
                            }catch (Exception ex){
                                ex.printStackTrace();
                            }

                        }else{
                            e.printStackTrace();
                        }

                    }
                });


    }
}


