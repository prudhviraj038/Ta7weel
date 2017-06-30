package com.mamac.ta7weel;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

/**
 * Created by mac on 3/18/17.
 */

public class TabsAdapter extends FragmentStatePagerAdapter {

    NewsFragment newsFragment;
    HomeFragment homeFragment;
    NewsWebFragmnet newsWebFragmnet;



    public TabsAdapter(FragmentManager fm) {
        super(fm);
    }


    @Override
    public Fragment getItem(int position) {

        Log.e("pos",String.valueOf(position));

        if(position==0) {

            homeFragment = HomeFragment.newInstance(position);

            return homeFragment;
        }

        else if(position == 1)
            return ChartFragmnet.newInstance("chart fragment");

        else if(position == 2){

               newsFragment = NewsFragment.newInstance(position);

            return newsFragment;
        }

        else if(position == 3) {

            newsWebFragmnet = NewsWebFragmnet.newInstance("News");

            return newsWebFragmnet;
        }

        else if(position == 4)
            return SettingsFragment.newInstance(position);

        else if(position == 5)
            return GoldPricesFragmnet.newInstance("Gold Prices");

        else if(position == 6)
            return SilverPricesFragment.newInstance("Silver Prices");

        else if(position == 7)
            return OilPricesFragment.newInstance("Oil Prices");

        else if(position == 8)
            return ExchangeLocationsFragment.newInstance("Exchange Locations");

        else if(position == 9)
            return NewsCalendarFragment.newInstance("News Calender");

        else if(position == 10)
            return AddCurrenciesFragment.newInstance(position);



        else
            return DemoFragment.newInstance(position);

    }

    @Override
    public int getCount() {
        return 11;
    }
}
