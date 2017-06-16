package com.mamac.ta7weel;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by mac on 6/10/17.
 */

public class OilPricesFragment extends Fragment {

    TextView oil_price,entry_price,close_price,high_price,low_price;
    public static OilPricesFragment newInstance(String someInt) {
        OilPricesFragment myFragment = new OilPricesFragment();

        Bundle args = new Bundle();
        args.putString("someInt", someInt);
        myFragment.setArguments(args);

        return myFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.oil_prices_fragment, container, false);
        oil_price = (TextView) view.findViewById(R.id.oil_prices_txt);
        entry_price = (TextView) view.findViewById(R.id.entry_price);
        high_price = (TextView) view.findViewById(R.id.high_price);
        low_price = (TextView) view.findViewById(R.id.low_price);
        close_price = (TextView) view.findViewById(R.id.close_price);

        try{
            oil_price.setText("$" + AppController.getInstance().prices.get("oil").getAsJsonObject().get("price").getAsString());
            entry_price.setText("$" +AppController.getInstance().prices.get("oil").getAsJsonObject().get("price").getAsString());
            high_price.setText("$" +AppController.getInstance().prices.get("oil").getAsJsonObject().get("price").getAsString());
            low_price.setText("$" +AppController.getInstance().prices.get("oil").getAsJsonObject().get("price").getAsString());
            close_price.setText("$" +AppController.getInstance().prices.get("oil").getAsJsonObject().get("price").getAsString());

        }catch (Exception ex) {
            ex.printStackTrace();
        }

        WebView webView = (WebView) view.findViewById(R.id.oil_price_web);

        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewController());

        webView.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress)
            {

            }
        });

        webView.loadUrl("http://ta7weel.net/api/oil-charts.php");



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


