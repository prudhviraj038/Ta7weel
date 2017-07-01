package com.mamac.ta7weel;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.kyleduo.switchbutton.SwitchButton;

/**
 * Created by mac on 3/18/17.
 */

public class SettingsFragment extends Fragment implements SeekBar.OnSeekBarChangeListener{
    LinearLayout whatsup_ll,fb_ll,insta_ll,twitter_ll,email_ll,sms_ll,contact_us,about_us;
    private SeekBar mSeekBar;
    private TextView refresh_time_txt;
    SwitchButton sb_shake;
    SwitchButton sb_notify;
    MainActivity mainActivity;


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
        refresh_time_txt = (TextView) view.findViewById(R.id.refresh_time_txt);
        getdata();
        mainActivity = (MainActivity) getActivity();

        sb_shake = (SwitchButton) view.findViewById(R.id.sb_shake);
        sb_notify = (SwitchButton) view.findViewById(R.id.sb_notify);

        sb_shake.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                sb_shake.setChecked(b);
                Session.set_shake_status(getActivity(),b);
            }
        });

        sb_notify.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                sb_notify.setChecked(b);
                Session.set_notify_status(getActivity(),b);
            }
        });

        sb_shake.setChecked(Session.get_shake_status(getActivity()));
        sb_notify.setChecked(Session.get_notify_status(getActivity()));

        mSeekBar = (SeekBar) view.findViewById(R.id.seekBar1);
        mSeekBar.setOnSeekBarChangeListener(this);
        mSeekBar.setProgress(Session.getRefreshTime(getActivity())-1);


        whatsup_ll=(LinearLayout)view.findViewById(R.id.sett_whatsup_ll);
        fb_ll=(LinearLayout)view.findViewById(R.id.sett_fb_ll);
        twitter_ll=(LinearLayout)view.findViewById(R.id.sett_twitter_ll);
        insta_ll=(LinearLayout)view.findViewById(R.id.sett_insta_ll);
        email_ll=(LinearLayout)view.findViewById(R.id.sett_email_ll);
        sms_ll=(LinearLayout)view.findViewById(R.id.sett_sms_ll);
        contact_us = (LinearLayout) view.findViewById(R.id.contact_us);
        about_us = (LinearLayout) view.findViewById(R.id.about_us);
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
        contact_us.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainActivity.go_to_contact_us();
            }
        });

        about_us.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainActivity.goto_about_page();
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
        Session.setRefreshTime(getActivity(),i+1);
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


