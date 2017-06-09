package com.mamac.ta7weel;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by mac on 3/18/17.
 */

public class TabsAdapter extends FragmentStatePagerAdapter {

    public TabsAdapter(FragmentManager fm) {
        super(fm);
    }


    @Override
    public Fragment getItem(int position) {
        if(position==0)
            return  HomeFragment.newInstance(position);
        else if(position == 1)
            return LineChartActivity.newInstance(position);

        else if(position == 2)
            return NewsFragment.newInstance(position);

        else if(position == 3)
            return SettingsFragment.newInstance(position);

        else
            return DemoFragment.newInstance(position);
    }

    @Override
    public int getCount() {
        return 4;
    }
}
