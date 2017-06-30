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
import android.widget.LinearLayout;

/**
 * Created by mac on 3/18/17.
 */

public class NewsWebFragmnet extends Fragment {

    WebView news_web_view;
    LinearLayout progress_holder;
    public static NewsWebFragmnet newInstance(String someInt) {
        NewsWebFragmnet myFragment = new NewsWebFragmnet();

        Bundle args = new Bundle();
        args.putString("someInt", someInt);
        myFragment.setArguments(args);

        return myFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.newsweb_view, container, false);

        news_web_view = (WebView) view.findViewById(R.id.news_web);
        progress_holder = (LinearLayout) view.findViewById(R.id.progress_holder);


        news_web_view.getSettings().setJavaScriptEnabled(true);
        news_web_view.setWebViewClient(new WebViewController());

        news_web_view.setWebChromeClient(new WebChromeClient() {

            public void onProgressChanged(WebView view, int progress)
            {
                Log.e("url",view.getUrl());

                if(!view.getUrl().equals("about:blank")){
                    if(progress<70){
                        progress_holder.setVisibility(View.VISIBLE);
                    }else{
                        progress_holder.setVisibility(View.GONE);
                    }
                }
            }
        });


        return view;
    }

    public class WebViewController extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }


    public void load_url(News news){
        progress_holder.setVisibility(View.VISIBLE);
        news_web_view.loadUrl("about:blank");
        news_web_view.loadUrl(news.link);
    }

}
