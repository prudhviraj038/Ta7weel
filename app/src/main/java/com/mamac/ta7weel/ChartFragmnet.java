package com.mamac.ta7weel;

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
 * Created by mac on 3/18/17.
 */

public class ChartFragmnet extends Fragment {

    WebView gold_chart,silver_chat;
    public static ChartFragmnet newInstance(String someInt) {
        ChartFragmnet myFragment = new ChartFragmnet();

        Bundle args = new Bundle();
        args.putString("someInt", someInt);
        myFragment.setArguments(args);

        return myFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.charts_view, container, false);

        gold_chart = (WebView) view.findViewById(R.id.gold_prices);
        silver_chat = (WebView) view.findViewById(R.id.silver_prices);


        gold_chart.getSettings().setJavaScriptEnabled(true);
        gold_chart.setWebViewClient(new WebViewController());

        gold_chart.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress)
            {

            }
        });

        gold_chart.loadUrl("http://ta7weel.net/api/gold-charts.php");


        silver_chat.getSettings().setJavaScriptEnabled(true);
        silver_chat.setWebViewClient(new WebViewController2());

        silver_chat.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress)
            {

            }
        });

        silver_chat.loadUrl("http://ta7weel.net/api/silver-charts.php");



        return view;
    }

    public class WebViewController extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }


    public class WebViewController2 extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }


}
