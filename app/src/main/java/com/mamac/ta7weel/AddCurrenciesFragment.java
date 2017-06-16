package com.mamac.ta7weel;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.JsonArray;

import java.util.ArrayList;

/**
 * Created by mac on 6/11/17.
 */

public class AddCurrenciesFragment extends Fragment {

    RecyclerView recyclerView ;
    ArrayList<Rates> rates;
    AddCurrencyAdapter ratesAdapter;

    public static AddCurrenciesFragment newInstance(int someInt) {
        AddCurrenciesFragment myFragment = new AddCurrenciesFragment();

        Bundle args = new Bundle();
        args.putInt("someInt", someInt);
        myFragment.setArguments(args);

        return myFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_currencies, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        rates = new ArrayList<>();

        JsonArray result = AppController.getInstance().rates;


            Log.e("response", result.toString());
            for(int i=0;i<result.size();i++){
                Rates temp = new Rates(result.get(i).getAsJsonObject());
                    rates.add(temp);
        }

        ratesAdapter = new AddCurrencyAdapter(rates,getActivity(),this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.getItemAnimator().setMoveDuration(1000);
        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(getActivity()));
        recyclerView.setAdapter(ratesAdapter);


        //setHasOptionsMenu(true);

        return view;
    }

}
