package com.mamac.ta7weel;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.drawable.ColorDrawable;
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
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.zip.Inflater;

import it.sephiroth.android.library.bottomnavigation.BottomNavigation;

public class MainActivity extends AppCompatActivity implements  BottomNavigation.OnMenuItemSelectionListener, HomeFragment.FragmentTouchListner {

    private BottomNavigation mBottomNavigation;
    private ViewPager mViewPager;
    private TabsAdapter tabsAdapter;
    LinearLayout progress_holder;


    private void setupActionBar() {

        getSupportActionBar().setHomeButtonEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setIcon(new ColorDrawable(getResources().getColor(android.R.color.transparent)));



        ActionBar.LayoutParams layoutParams = new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT,
                ActionBar.LayoutParams.MATCH_PARENT);

        LayoutInflater inflater = getLayoutInflater();
        View v = inflater.inflate(R.layout.action_bar_title,null);
        getSupportActionBar().setCustomView(v, layoutParams);
        Toolbar parent = (Toolbar) v.getParent();

        parent.setContentInsetsAbsolute(0, 0);
        ProgressBar mprogressBar = (ProgressBar) v.findViewById(R.id.circular_progress_bar);
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
                mBottomNavigation.setSelectedIndex(position,false);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

            setupActionBar();
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
        progress_holder.setVisibility(View.VISIBLE);
    }

    @Override
    public void hide_progress() {
        progress_holder.setVisibility(View.GONE);
    }
}
