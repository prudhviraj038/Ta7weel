package com.mamac.ta7weel;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.hardware.SensorListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.IdRes;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarDrawerToggle;
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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.JsonArray;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

import it.sephiroth.android.library.bottomnavigation.BottomNavigation;

public class MainActivity extends AppCompatActivity implements  BottomNavigation.OnMenuItemSelectionListener, HomeFragment.FragmentTouchListner,
        NewsCalendarFragment.FragmentTouchListner,SensorListener {

    private BottomNavigation mBottomNavigation;
    private ViewPager mViewPager;
    private TabsAdapter tabsAdapter;
    LinearLayout progress_holder;
    private DrawerLayout mDrawerLayout;
    ImageView menu_btn;
    ImageView back_btn;
    TextView page_title;
    ImageView page_logo;
    ImageView add_btn;
    TextView change_lan;
    ProgressBar mprogressBar;
    private ActionBarDrawerToggle mDrawerToggle;
    boolean previous_page;



    private void openNavigation(){

        if(mDrawerLayout.isDrawerOpen(GravityCompat.START))

            mDrawerLayout.closeDrawer(GravityCompat.START,true);
        else
            mDrawerLayout.openDrawer(GravityCompat.START,true);

    }


    private void setupActionBar() {
//set action bar
        getSupportActionBar().setHomeButtonEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setIcon(new ColorDrawable(getResources().getColor(android.R.color.transparent)));



        ActionBar.LayoutParams layoutParams = new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT,
                ActionBar.LayoutParams.MATCH_PARENT);

        LayoutInflater inflater = getLayoutInflater();
        View v = inflater.inflate(R.layout.action_bar_title,null);

        page_logo = (ImageView) v.findViewById(R.id.page_logo);
        page_title = (TextView) v.findViewById(R.id.page_title);

        menu_btn = (ImageView) v.findViewById(R.id.btn_menu);
        menu_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openNavigation();
            }
        });

        back_btn = (ImageView) v.findViewById(R.id.btn_back);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        add_btn = (ImageView) v.findViewById(R.id.btn_addnew);
        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mViewPager.setCurrentItem(10,true);
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

                tabsAdapter.newsFragment.get_news("0");

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
        sensorMgr = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensorMgr.registerListener(this,
                SensorManager.SENSOR_ACCELEROMETER,
                SensorManager.SENSOR_DELAY_GAME);

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

                     if(position == 0)
                         mBottomNavigation.setSelectedIndex(position, false);
                     else if(position == 1)
                         mBottomNavigation.setSelectedIndex(position, false);
                     else if(position == 2)
                         mBottomNavigation.setSelectedIndex(position, false);
                     else if(position == 3)
                         mBottomNavigation.setSelectedIndex(2, false);
                     else if(position == 4)
                         mBottomNavigation.setSelectedIndex(3, false);


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
        menuItems.add(new MenuItem("Oil Price","",R.drawable.ic_oil_prices));
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
            //    previous_page = mViewPager.getCurrentItem();

                switch (i){
                    case 0:

                        mViewPager.setCurrentItem(5);

                        break;

                    case 1:

                        mViewPager.setCurrentItem(6);

                        break;

                    case 2:

                        mViewPager.setCurrentItem(7);
                        break;

                    case 3:

                        mViewPager.setCurrentItem(8);
                        break;

                    case 4:

                        mViewPager.setCurrentItem(9);
                        break;

                    case 5:

                        Intent intent = new Intent(MainActivity.this,YoutubePlayer.class);
                        startActivity(intent);


                        break;



                }
            }
        });

        mDrawerLayout.setScrimColor(getResources().getColor(android.R.color.transparent));

        Toolbar toolbar = (Toolbar) findViewById(R.id.my_awesome_toolbar);
        setSupportActionBar(toolbar);
        setupActionBar();
        setHeader(0);

        get_rates();


        drawerView = (LinearLayout) findViewById(R.id.drawerView);
        mainView = (RelativeLayout) findViewById(R.id.mainView);


        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, null, R.string.app_name, R.string.app_name) {
            public void onDrawerClosed(View view) {
                supportInvalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                supportInvalidateOptionsMenu();
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                mainView.setTranslationX(slideOffset * drawerView.getWidth());
                mDrawerLayout.bringChildToFront(drawerView);
                mDrawerLayout.requestLayout();
            }
        };



        mDrawerLayout.setDrawerListener(mDrawerToggle);

    }

    LinearLayout drawerView;
    RelativeLayout mainView;

    @Override
    public void onMenuItemSelect(@IdRes int i, int i1, boolean b) {

        Log.e("i",String.valueOf(i1));
       // previous_page = mViewPager.getCurrentItem();
        if(i1==3)
            mViewPager.setCurrentItem(4);
        else
            mViewPager.setCurrentItem(i1);


    }

    @Override
    public void onMenuItemReselect(@IdRes int i, int i1, boolean b) {
     //   Log.e("previous",String.valueOf(previous_page));
        if (i1 == 2 && !previous_page && mViewPager.getCurrentItem()!=3 ){

            try {
                tabsAdapter.newsFragment.get_news("0");
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        previous_page = false;


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
        page_title.setVisibility(View.INVISIBLE);
        page_logo.setVisibility(View.VISIBLE);
        menu_btn.setVisibility(View.VISIBLE);
        back_btn.setVisibility(View.GONE);



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
                page_logo.setVisibility(View.INVISIBLE);
                page_title.setVisibility(View.VISIBLE);
                page_title.setText("News");

                break;


            case 3:

                //mprogressBar.setVisibility(View.INVISIBLE);
                add_btn.setVisibility(View.INVISIBLE);
                change_lan.setVisibility(View.GONE);
                page_logo.setVisibility(View.INVISIBLE);
                page_title.setVisibility(View.VISIBLE);
                page_title.setText("News");
                menu_btn.setVisibility(View.GONE);
                back_btn.setVisibility(View.VISIBLE);
                break;


            case 5:

                //mprogressBar.setVisibility(View.INVISIBLE);
                add_btn.setVisibility(View.INVISIBLE);
                page_logo.setVisibility(View.INVISIBLE);
                page_title.setVisibility(View.VISIBLE);
                page_title.setText("Gold Prices");



                break;

            case 6:

                //mprogressBar.setVisibility(View.INVISIBLE);
                add_btn.setVisibility(View.INVISIBLE);
                page_logo.setVisibility(View.INVISIBLE);
                page_title.setVisibility(View.VISIBLE);
                page_title.setText("Silver Prices");



                break;

            case 7:

                //mprogressBar.setVisibility(View.INVISIBLE);
                add_btn.setVisibility(View.INVISIBLE);
                page_logo.setVisibility(View.INVISIBLE);
                page_title.setVisibility(View.VISIBLE);
                page_title.setText("Oil Price");


                break;

            case 8:

                //mprogressBar.setVisibility(View.INVISIBLE);
                add_btn.setVisibility(View.INVISIBLE);
                page_logo.setVisibility(View.INVISIBLE);
                page_title.setVisibility(View.VISIBLE);
                page_title.setText("Exchange Locations");


                break;



            case 9:

                //mprogressBar.setVisibility(View.INVISIBLE);
                add_btn.setVisibility(View.INVISIBLE);
                page_logo.setVisibility(View.INVISIBLE);
                page_title.setVisibility(View.VISIBLE);
                page_title.setText("News Calender");


                break;

            case 10:

                //mprogressBar.setVisibility(View.INVISIBLE);
                page_logo.setVisibility(View.INVISIBLE);
                page_title.setVisibility(View.VISIBLE);
                page_title.setText("Add Currencies");

                menu_btn.setVisibility(View.GONE);
                back_btn.setVisibility(View.VISIBLE);

                break;

            case 11:
                page_logo.setVisibility(View.INVISIBLE);
                page_title.setVisibility(View.VISIBLE);
                page_title.setText("Contact us");
                menu_btn.setVisibility(View.GONE);
                back_btn.setVisibility(View.VISIBLE);
                break;

            case 12:
                page_logo.setVisibility(View.INVISIBLE);
                page_title.setVisibility(View.VISIBLE);
                page_title.setText("About us");
                menu_btn.setVisibility(View.GONE);
                back_btn.setVisibility(View.VISIBLE);
                break;



            default:

                break;
        }
    }

    @Override
    public void onBackPressed() {
        // your code.
        previous_page=false;
            if(mDrawerLayout.isDrawerOpen(GravityCompat.START))
                mDrawerLayout.closeDrawer(GravityCompat.START,true);
            else  if(mViewPager.getCurrentItem()!=0){

                if(mViewPager.getCurrentItem()==3) {
                    previous_page=true;
                    mViewPager.setCurrentItem(2, true);
                }

                else  if (mViewPager.getCurrentItem() ==11 || mViewPager.getCurrentItem() == 12)
                    mViewPager.setCurrentItem(4,true);
                else
                mViewPager.setCurrentItem(0,false);
            }

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
                        }, 1000*60*Session.getRefreshTime(MainActivity.this));

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

    float x,y,z,last_x,last_y,last_z;
    long lastUpdate;
    private static final int SHAKE_THRESHOLD = 800;
    SensorManager sensorMgr;


    @Override
    public void onSensorChanged(int sensor, float[] values) {

        if (sensor == SensorManager.SENSOR_ACCELEROMETER) {
            long curTime = System.currentTimeMillis();
            // only allow one update every 100ms.
            if ((curTime - lastUpdate) > 100) {
                long diffTime = (curTime - lastUpdate);
                lastUpdate = curTime;

                x = values[SensorManager.DATA_X];
                y = values[SensorManager.DATA_Y];
                z = values[SensorManager.DATA_Z];

                float speed = Math.abs(x+y+z - last_x - last_y - last_z) / diffTime * 10000;

                if (speed > SHAKE_THRESHOLD) {
                    if(Session.get_shake_status(MainActivity.this)) {
                        if(tabsAdapter.homeFragment!=null){
                            tabsAdapter.homeFragment.device_shaked();
                        }
                    }
                }
                last_x = x;
                last_y = y;
                last_z = z;
            }
        }

    }

    @Override
    public void onAccuracyChanged(int i, int i1) {

    }

    public void goto_news_detail(News news){

        mViewPager.setCurrentItem(3);

        if(tabsAdapter.newsWebFragmnet!=null){
            tabsAdapter.newsWebFragmnet.load_url(news);
        }
    }

    public void go_to_contact_us(){
        mViewPager.setCurrentItem(11);
    }

    public void goto_about_page(){
        mViewPager.setCurrentItem(12);
    }


}
