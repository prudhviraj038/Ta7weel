package com.mamac.ta7weel;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by mac on 6/10/17.
 */

public class SilverPricesFragment extends Fragment {

    ImageView flag_img,drop_img;
    TextView  full_name,short_name;
    Rates current_item;

    PricesAdapter menuAdapter;

    public static SilverPricesFragment newInstance(String someInt) {
        SilverPricesFragment myFragment = new SilverPricesFragment();

        Bundle args = new Bundle();
        args.putString("someInt", someInt);
        myFragment.setArguments(args);

        return myFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.gold_price_fragment, container, false);

        flag_img = (ImageView) view.findViewById(R.id.flag_img);
        drop_img = (ImageView) view.findViewById(R.id.drop_bttn);

        full_name = (TextView) view.findViewById(R.id.full_name);
        short_name = (TextView) view.findViewById(R.id.short_name);


        flag_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                show_alert_edit();
            }
        });

        drop_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                show_alert_edit();
            }
        });

        full_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                show_alert_edit();
            }
        });

        short_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                show_alert_edit();
            }
        });


        ArrayList<MenuItem> menuItems = new ArrayList<>();





        if(AppController.getInstance().prices !=null) {
            JsonObject gold_prices = AppController.getInstance().prices.get("silver").getAsJsonObject();

            menuItems.add(new MenuItem("Silver Ounces", gold_prices.get("ounces").getAsString(), R.drawable.ic_gold_prices));
            menuItems.add(new MenuItem("Silver Per Kilo", gold_prices.get("kilo").getAsString(), R.drawable.ic_silver_prices));
            menuItems.add(new MenuItem("Silver per Gram 99.9 ", gold_prices.get("gram99").getAsString(), R.drawable.ic_oil_prices));
            menuItems.add(new MenuItem("Silver per Gram 95.8 ", gold_prices.get("gram95").getAsString(), R.drawable.ic_exchange_locations));
            menuItems.add(new MenuItem("Silver per Gram 92.5 ", gold_prices.get("gram92").getAsString(), R.drawable.ic_news_calender));
            menuItems.add(new MenuItem("Silver per Gram 90 ", gold_prices.get("gram90").getAsString(), R.drawable.ic_news_calender));
            menuItems.add(new MenuItem("Silver Coins ", gold_prices.get("coins80").getAsString(), R.drawable.ic_live_broadcast));


            menuAdapter = new PricesAdapter(getActivity(), menuItems);

            ListView prices_list = (ListView) view.findViewById(R.id.prices_list);


            View footerView = ((LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.prices_list_footer, null, false);
            prices_list.addFooterView(footerView);
            prices_list.setAdapter(menuAdapter);

            WebView gold_chart = (WebView) footerView.findViewById(R.id.webview);

            gold_chart.getSettings().setJavaScriptEnabled(true);

            gold_chart.setWebViewClient(new WebViewController());

            gold_chart.setWebChromeClient(new WebChromeClient() {
                public void onProgressChanged(WebView view, int progress)
                {

                }
            });

            gold_chart.loadUrl("http://ta7weel.net/api/silver-charts.php");


        }


        JsonArray result = AppController.getInstance().rates;
        categories = new ArrayList<>();
        for(int i=0;i<result.size();i++){
            Rates temp = new Rates(result.get(i).getAsJsonObject());
            categories.add(temp);
            if(temp.short_name.equals("KWD")){
                current_item = temp;
                Picasso.with(getActivity()).load(categories.get(i).image).into(flag_img);
                short_name.setText(categories.get(i).short_name +"(" +categories.get(i).symbol+ ")");
                //local_currency_symbol.setText(categories.get(i).symbol);
                full_name.setText(categories.get(i).long_name);
                //current_rate=categories.get(i).base_rate;
                menuAdapter.current_item = current_item;
                menuAdapter.notifyDataSetChanged();

            }


        }

        return view;
    }



    public class WebViewController extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }

    CategoryAdapter categoryAdapter;
    ArrayList<Rates> categories ;

    private void show_alert_edit(){

        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());


// ...Irrelevant code for customizing the buttons and title
        LayoutInflater inflater=null;
        inflater = (LayoutInflater) getActivity().
                getSystemService(android.content.Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.select_city_pop_up, null);

        dialogBuilder.setView(dialogView);
        final AlertDialog alertDialog = dialogBuilder.create();
        TextView pop_title = (TextView) dialogView.findViewById(R.id.pop_up_title);
        ListView listView = (ListView) dialogView.findViewById(R.id.city_list);
        categoryAdapter = new CategoryAdapter(getActivity(),categories);

        listView.setAdapter(categoryAdapter);
        pop_title.setText("Select Currency");

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                if(categoryAdapter!=null) {

                    Picasso.with(getActivity()).load(categories.get(i).image).into(flag_img);
                    short_name.setText(categories.get(i).short_name +"(" +categories.get(i).symbol+ ")");
                    //local_currency_symbol.setText(categories.get(i).symbol);
                    full_name.setText(categories.get(i).long_name);
                    //current_rate=categories.get(i).base_rate;

                    current_item = categories.get(i);

                    menuAdapter.current_item = current_item;

                    menuAdapter.notifyDataSetChanged();

                    alertDialog.dismiss();
                }
            }
        });

        final EditText sub_cat_edit = (EditText) dialogView.findViewById(R.id.sub_cat_pop);
        TextView save = (TextView) dialogView.findViewById(R.id.pop_save_btn);
        TextView cancel = (TextView) dialogView.findViewById(R.id.pop_cancel_btn);
        LinearLayout main_cat_select = (LinearLayout) dialogView.findViewById(R.id.select_product_category);
        main_cat_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {




            }
        });

        sub_cat_edit.setText("");

        sub_cat_edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if(categoryAdapter!=null)
                    categoryAdapter.getFilter().filter(charSequence);

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });



        categoryAdapter.notifyDataSetChanged();


        alertDialog.show();


    }



}

