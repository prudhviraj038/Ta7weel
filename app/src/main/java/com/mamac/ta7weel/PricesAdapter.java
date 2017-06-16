package com.mamac.ta7weel;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class PricesAdapter extends BaseAdapter implements Filterable {
    Context context;
    ArrayList<MenuItem> categories;
    ArrayList<MenuItem> categories_all;
    PlanetFilter planetFilter;
    Rates current_item;
    Rates dollar_item;

    private static LayoutInflater inflater=null;
    public PricesAdapter(Context mainActivity, ArrayList<MenuItem> categories) {
        // TODO Auto-generated constructor stubcontext=mainActivity;
        this.context = mainActivity;
        inflater = (LayoutInflater)context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.categories = categories;
        this.categories_all = categories;
    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return categories.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public Filter getFilter() {
        if(planetFilter==null)
            planetFilter=new PlanetFilter();
        return planetFilter;
    }

    public class Holder
    {
        TextView cur_symbol,cur_name;
        ImageView country_flag;
        LinearLayout color_change_layout;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub

        Holder holder=new Holder();
        View rowView;
        rowView = inflater.inflate(R.layout.prices_adapter, null);




        holder.cur_symbol=(TextView) rowView.findViewById(R.id.menu_iem);
        holder.cur_symbol.setText(categories.get(position).title);
        holder.cur_symbol.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        holder.cur_symbol.setSingleLine(true);
        holder.cur_symbol.setMarqueeRepeatLimit(1000);
        holder.cur_symbol.setSelected(true);


        holder.cur_name=(TextView) rowView.findViewById(R.id.menu_iem_price);

        if(current_item!=null) {

            Float temp = Float.parseFloat(current_item.base_rate)*Float.parseFloat(categories.get(position).title_ar);


            holder.cur_name.setText(current_item.symbol+" "+ Session.format_decimals(temp));
        }
        else
            holder.cur_name.setText("$ " + categories.get(position).title_ar);


        holder.cur_name.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        holder.cur_name.setSingleLine(true);
        holder.cur_name.setMarqueeRepeatLimit(1000);
        holder.cur_name.setSelected(true);


        holder.color_change_layout = (LinearLayout) rowView.findViewById(R.id.color_change_layout);
        if(position%2==0){
            holder.color_change_layout.setBackgroundColor(Color.parseColor("#FFFFFF"));
        }else{
            holder.color_change_layout.setBackgroundColor(Color.rgb(253,251,240));

        }


//        holder.country_flag = (ImageView) rowView.findViewById(R.id.menu_icon);
//        Picasso.with(context).load(categories.get(position).icon).into(holder.country_flag);


        return rowView;
        
    }


    private class PlanetFilter extends Filter {
        Boolean clear_all=false;
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
// We implement here the filter logic
            clear_all=false;
            if (constraint == null || constraint.length() == 0) {
                clear_all=true;
// No filter implemented we return all the list
                results.values = categories;
                results.count = categories.size();
            }
            else {
// We perform filtering operation
                List<MenuItem> nPlanetList = new ArrayList<>();

                for (MenuItem p : categories_all) {

//Pattern.compile(Pattern.quote(s2), Pattern.CASE_INSENSITIVE).matcher(s1).find();

                    if (Pattern.compile(Pattern.quote(constraint.toString()), Pattern.CASE_INSENSITIVE).matcher(p.title).find())
                        nPlanetList.add(p);
                }

                results.values = nPlanetList;
                results.count = nPlanetList.size();

            }
            return results;
        }
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

            if (results.count == 0) {
//                restaurants = (ArrayList<Restaurants>) results.values;
                notifyDataSetChanged();
            }
            else if(clear_all){

                categories = categories_all;
                notifyDataSetChanged();
            }


            else {
                categories = (ArrayList<MenuItem>) results.values;
                notifyDataSetChanged();
            }



        }

    }


}