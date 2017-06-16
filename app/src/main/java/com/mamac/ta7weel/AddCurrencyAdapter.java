package com.mamac.ta7weel;

/**
 * Created by mac on 3/20/17.
 */


import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.ads.formats.NativeAd;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.squareup.picasso.Picasso;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class AddCurrencyAdapter extends RecyclerView.Adapter<AddCurrencyAdapter.MyViewHolder> {

    public List<Rates> moviesList;

    public HashMap<Integer,Rates> dummyList;

    private Context context;
    AddCurrenciesFragment homeFragment;

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener,View.OnClickListener{
        public TextView title, base_rate, value,full_name;
        public ImageView flag_id,selected_tick;
        public Rates rates;

        public MyViewHolder(View view) {

            super(view);
            title = (TextView) view.findViewById(R.id.currency_symbol);
            base_rate = (TextView) view.findViewById(R.id.currency_baserate);
            value = (TextView) view.findViewById(R.id.currency_converted_value);
            full_name = (TextView) view.findViewById(R.id.currency_full_form);
            flag_id = (ImageView) view.findViewById(R.id.currency_flag);
            selected_tick = (ImageView)  view.findViewById(R.id.selcted_tick);
            view.setOnClickListener(this);

        }

        @Override
        public boolean onLongClick(View view) {

            title.setBackgroundColor(Color.parseColor("black"));

            return false;
        }

        @Override
        public void onClick(View view) {

            if(!AppController.getInstance().selected_channels.contains(moviesList.get(getAdapterPosition()).short_name)){

                AppController.getInstance().selected_channels.add(moviesList.get(getAdapterPosition()).short_name);
                selected_tick.setVisibility(View.VISIBLE);
            }else{
                AppController.getInstance().selected_channels.remove(moviesList.get(getAdapterPosition()).short_name);
                selected_tick.setVisibility(View.GONE);

            }


            Log.e("selected",AppController.getInstance().selected_channels.toString());

        }
    }


    public AddCurrencyAdapter(List<Rates> moviesList, Context context, AddCurrenciesFragment homeFragment) {
        this.moviesList = moviesList;
        this.context = context;
        this.homeFragment = homeFragment;

    }



    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.select_currency_item, parent, false);



        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

            Rates rates = moviesList.get(position);
            holder.rates = rates;

            holder.title.setText(rates.short_name);

            holder.base_rate.setText(rates.base_rate);

            holder.value.setText(rates.value);
            holder.full_name.setText(rates.long_name);

            //  holder.flag_id.setImageResource(rates.flag_id);

            Picasso.with(context).load(rates.image).into(holder.flag_id);

        if(AppController.getInstance().selected_channels.contains(moviesList.get(position).short_name)){

            holder.selected_tick.setVisibility(View.VISIBLE);
        }else{
            holder.selected_tick.setVisibility(View.GONE);
        }




    }

    @Override
    public int getItemCount() {

        return moviesList.size();

    }

}