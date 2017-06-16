package com.mamac.ta7weel;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

/**
 * Created by mac on 6/10/17.
 */

public class NewsCalendarFragment extends Fragment {

FragmentTouchListner mCallBack;

    public static NewsCalendarFragment newInstance(String someInt) {
        NewsCalendarFragment myFragment = new NewsCalendarFragment();

        Bundle args = new Bundle();
        args.putString("someInt", someInt);
        myFragment.setArguments(args);

        return myFragment;
    }


    public interface FragmentTouchListner{


        public void show_progress();
        public void hide_progress();


    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallBack = (MainActivity) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement LogoutUser");
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.news_calendar_fragment, container, false);

        WebView webView = (WebView) view.findViewById(R.id.webview);

        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewController());
        webView.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress)
            {
              //  mCallBack.show_progress();

               // if(progress > 70)
                //    mCallBack.hide_progress();

            }
        });


        Log.e("Setings",AppController.getInstance().settings.toString());

        String url = AppController.getInstance().settings.get("calender").getAsString();


        webView.loadUrl(url);


        return view;
    }

    public class WebViewController extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }

}


