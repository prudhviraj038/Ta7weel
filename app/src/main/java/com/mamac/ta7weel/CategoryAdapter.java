package com.mamac.ta7weel;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class CategoryAdapter extends BaseAdapter implements Filterable {
    Context context;
    ArrayList<Rates> categories;
    ArrayList<Rates> categories_all;
    PlanetFilter planetFilter;

    private static LayoutInflater inflater=null;
    public CategoryAdapter(Context mainActivity, ArrayList<Rates> categories) {
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
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub

        Holder holder=new Holder();
        View rowView;
        rowView = inflater.inflate(R.layout.select_currency_item, null);




         holder.cur_symbol=(TextView) rowView.findViewById(R.id.currency_symbol);
        holder.cur_symbol.setText(categories.get(position).short_name);

        holder.cur_name=(TextView) rowView.findViewById(R.id.currency_full_form);
        holder.cur_name.setText(categories.get(position).long_name);


        holder.country_flag = (ImageView) rowView.findViewById(R.id.currency_flag);
        Picasso.with(context).load(categories.get(position).image).into(holder.country_flag);


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
                List<Rates> nPlanetList = new ArrayList<>();

                for (Rates p : categories_all) {

//Pattern.compile(Pattern.quote(s2), Pattern.CASE_INSENSITIVE).matcher(s1).find();

                    if (Pattern.compile(Pattern.quote(constraint.toString()), Pattern.CASE_INSENSITIVE).matcher(p.short_name).find())
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
                categories = (ArrayList<Rates>) results.values;
                notifyDataSetChanged();
            }



        }

    }


}