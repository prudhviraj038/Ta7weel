package com.mamac.ta7weel;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import static com.mamac.ta7weel.SettingsFragment.html2text;

/**
 * Created by yellowsoft on 1/7/17.
 */

public class AboutFragment  extends Fragment {
    TextView about;

    public static AboutFragment newInstance(String someInt) {
        AboutFragment myFragment = new AboutFragment();

        Bundle args = new Bundle();
        args.putString("someInt", someInt);
        myFragment.setArguments(args);

        return myFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.about_fragment, container, false);
        about = (TextView) view.findViewById(R.id.about);
        about.setText(Html.fromHtml(Session.getSettings(getActivity(),"about")));
        //setHasOptionsMenu(true);

        return view;
    }


}

