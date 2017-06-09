package com.mamac.ta7weel;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

/**
 * Created by mac on 3/18/17.
 */

public class SettingsFragment extends Fragment implements SeekBar.OnSeekBarChangeListener{

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
        mSeekBar = (SeekBar) view.findViewById(R.id.seekBar1);
        mSeekBar.setOnSeekBarChangeListener(this);
        refresh_time_txt = (TextView) view.findViewById(R.id.refresh_time_txt);

        return view;
    }


    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

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
}
