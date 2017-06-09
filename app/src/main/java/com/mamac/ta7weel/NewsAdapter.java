package com.mamac.ta7weel;

/**
 * Created by mac on 3/20/17.
 */


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.MyViewHolder> {

    private List<Rates> moviesList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, base_rate, value,full_name;
        public ImageView flag_id;

        public MyViewHolder(View view) {
            super(view);

//            title = (TextView) view.findViewById(R.id.currency_symbol);
//            base_rate = (TextView) view.findViewById(R.id.currency_baserate);
//            value = (TextView) view.findViewById(R.id.currency_converted_value);
//            full_name = (TextView) view.findViewById(R.id.currency_full_form);
//            flag_id = (ImageView) view.findViewById(R.id.currency_flag);
//
        }
    }


    public NewsAdapter(List<Rates> moviesList) {
        this.moviesList = moviesList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.news_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
     //   Rates rates = moviesList.get(position);

//        holder.title.setText(rates.name);
//        holder.base_rate.setText(rates.base_rate);
//        holder.value.setText(rates.value);
//        holder.full_name.setText(rates.full_name);
//        holder.flag_id.setImageResource(rates.flag_id);

            }

    @Override
    public int getItemCount() {
      //  return moviesList.size();

        return 10;

    }
}