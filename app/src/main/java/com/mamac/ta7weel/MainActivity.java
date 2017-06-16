package com.mamac.ta7weel;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.IdRes;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.JsonArray;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

import it.sephiroth.android.library.bottomnavigation.BottomNavigation;

public class MainActivity extends AppCompatActivity implements  BottomNavigation.OnMenuItemSelectionListener, HomeFragment.FragmentTouchListner,NewsCalendarFragment.FragmentTouchListner {

    private BottomNavigation mBottomNavigation;
    private ViewPager mViewPager;
    private TabsAdapter tabsAdapter;
    LinearLayout progress_holder;
    private DrawerLayout mDrawerLayout;
    ImageView menu_btn;
    ImageView add_btn;
    TextView change_lan;
    ProgressBar mprogressBar;


    private void openNavigation(){

        if(mDrawerLayout.isDrawerOpen(GravityCompat.START))

            mDrawerLayout.closeDrawer(GravityCompat.START,true);
        else
            mDrawerLayout.openDrawer(GravityCompat.START,true);

    }


    private void setupActionBar() {

        getSupportActionBar().setHomeButtonEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setIcon(new ColorDrawable(getResources().getColor(android.R.color.transparent)));



        ActionBar.LayoutParams layoutParams = new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT,
                ActionBar.LayoutParams.MATCH_PARENT);

        LayoutInflater inflater = getLayoutInflater();
        View v = inflater.inflate(R.layout.action_bar_title,null);
        menu_btn = (ImageView) v.findViewById(R.id.btn_menu);
        menu_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openNavigation();
            }
        });

        add_btn = (ImageView) v.findViewById(R.id.btn_addnew);
        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mViewPager.setCurrentItem(9,true);
            }
        });
        change_lan = (TextView) v.findViewById(R.id.language_change);

        if(Session.getLan(this).equals("en"))
            change_lan.setText(" العربية");
        else
            change_lan.setText("English");


        change_lan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            String lan = Session.getLan(MainActivity.this);

                if(lan.equals("en")){

                    Session.setLan(MainActivity.this,"ar");
                    change_lan.setText("English");


                }else{
                    Session.setLan(MainActivity.this,"en");
                    change_lan.setText(" العربية");
                }

                tabsAdapter.newsFragment.get_news();

            }
        });

        getSupportActionBar().setCustomView(v, layoutParams);
        Toolbar parent = (Toolbar) v.getParent();

        parent.setContentInsetsAbsolute(0, 0);
         mprogressBar = (ProgressBar) v.findViewById(R.id.circular_progress_bar);
        final ObjectAnimator anim = ObjectAnimator.ofInt(mprogressBar, "progress", 0, 1440);
        anim.setRepeatCount(ObjectAnimator.INFINITE);
        anim.setRepeatMode(ObjectAnimator.RESTART);
        anim.setDuration(60000);
        anim.setInterpolator(new DecelerateInterpolator());
        anim.start();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        progress_holder = (LinearLayout) findViewById(R.id.progress_holder);
        progress_holder.setVisibility(View.GONE);
        mBottomNavigation = (BottomNavigation) findViewById(R.id.BottomNavigation);
        mBottomNavigation.setOnMenuItemClickListener(this);
        tabsAdapter = new TabsAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.viewPager);
        mViewPager.setAdapter(tabsAdapter);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                try {
                    mBottomNavigation.setSelectedIndex(position, false);



                }catch (Exception ex){
                    ex.printStackTrace();
                }

                setHeader(position);
            }



            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        ArrayList<MenuItem> menuItems = new ArrayList<>();

        menuItems.add(new MenuItem("Gold Prices","",R.drawable.ic_gold_prices));
        menuItems.add(new MenuItem("Silver Prices","",R.drawable.ic_silver_prices));
        menuItems.add(new MenuItem("Oil Prices","",R.drawable.ic_oil_prices));
        menuItems.add(new MenuItem("Exchange Locations","",R.drawable.ic_exchange_locations));
        menuItems.add(new MenuItem("News Calender","",R.drawable.ic_news_calender));
        menuItems.add(new MenuItem("Live Broadcast","",R.drawable.ic_live_broadcast));


        MenuAdapter menuAdapter = new MenuAdapter(this,menuItems);

        ListView listView = (ListView) findViewById(R.id.menu_list);
        listView.setAdapter(menuAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                mDrawerLayout.closeDrawer(GravityCompat.START,true);

                Log.e("pos click",String.valueOf(i));

                switch (i){
                    case 0:

                        mViewPager.setCurrentItem(4);

                        break;

                    case 1:

                        mViewPager.setCurrentItem(5);

                        break;

                    case 2:

                        mViewPager.setCurrentItem(6);
                        break;

                    case 3:

                        mViewPager.setCurrentItem(7);
                        break;

                    case 4:

                        mViewPager.setCurrentItem(8);
                        break;

                    case 5:

                        Intent intent = new Intent(MainActivity.this,YoutubePlayer.class);
                        startActivity(intent);


                        break;



                }
            }
        });

        mDrawerLayout.setScrimColor(getResources().getColor(android.R.color.transparent));


        setupActionBar();
        setHeader(0);

        get_rates();

    }

    @Override
    public void onMenuItemSelect(@IdRes int i, int i1, boolean b) {

      //  Log.e("i",String.valueOf(i1));
        mViewPager.setCurrentItem(i1);


    }

    @Override
    public void onMenuItemReselect(@IdRes int i, int i1, boolean b) {
       // Log.e("ir",String.valueOf(i1));

    }

    @Override
    public void show_progress() {
       // progress_holder.setVisibility(View.VISIBLE);
    }

    @Override
    public void hide_progress() {
       // progress_holder.setVisibility(View.GONE);
    }


    @Override
    protected void onDestroy(){
        super.onDestroy();
        Session.setCurrencies(this,AppController.getInstance().selected_channels.toString());
        Session.setlastupdate(this,AppController.getInstance().last_updated_time);


    }


    private void setHeader(int pos){

        mprogressBar.setVisibility(View.INVISIBLE);
        add_btn.setVisibility(View.INVISIBLE);
        change_lan.setVisibility(View.INVISIBLE);


        switch(pos){

            case 0:

                //mprogressBar.setVisibility(View.VISIBLE);
                add_btn.setVisibility(View.VISIBLE);
                change_lan.setVisibility(View.GONE);

                break;


            case 2:

                //mprogressBar.setVisibility(View.INVISIBLE);
                add_btn.setVisibility(View.INVISIBLE);
                change_lan.setVisibility(View.VISIBLE);

                break;

            default:

                break;
        }
    }

    @Override
    public void onBackPressed() {
        // your code.
            if(mDrawerLayout.isDrawerOpen(GravityCompat.START))
                mDrawerLayout.closeDrawer(GravityCompat.START,true);
            else  if(mViewPager.getCurrentItem()!=0)
                mViewPager.setCurrentItem(0,false);
            else
                finish();

    }



    private void get_rates(){

        Log.e("calling_rates","now");
        Ion.with(this)
                .load(Session.SERVER_URL+"currency.php")
                .asJsonArray()
                .setCallback(new FutureCallback<JsonArray>() {
                    @Override
                    public void onCompleted(Exception e, JsonArray result) {


                        final Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                get_rates();
                            }
                        }, 1000*10);

                        if(e!=null){
                            e.printStackTrace();
                        }else{

                            AppController.getInstance().rates=result;

                            if(tabsAdapter.homeFragment!=null){
                                Session.setlastupdate(MainActivity.this,Session.getCurrentTimeStamp());
                                tabsAdapter.homeFragment.refresh_rates();
                            }

                        }
                    }
                });


    }


}
