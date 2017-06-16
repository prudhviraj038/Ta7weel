package com.mamac.ta7weel;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.JsonArray;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;

/**
 * Created by mac on 6/10/17.
 */

public class ExchangeLocationsFragment extends Fragment {

    MapFragment mapFragment;
    ArrayList<ExchangeLocations> exchangeLocationses;
    public static ExchangeLocationsFragment newInstance(String someInt) {
        ExchangeLocationsFragment myFragment = new ExchangeLocationsFragment();

        Bundle args = new Bundle();
        args.putString("someInt", someInt);
        myFragment.setArguments(args);

        return myFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.map_layout, container, false);

        mapFragment = (MapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        exchangeLocationses = new ArrayList<>();

        final EditText editText = (EditText) view.findViewById(R.id.locations_search);
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    getLocations(editText.getText().toString());

                    if (v != null) {
                        InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    }


                    return true;
                }
                return false;
            }
        });

        getLocations("");
        return view;
    }


    private void getLocations(String search){
        Ion.with(this)
                .load(Session.SERVER_URL+"locations.php?search="+search)
                .asJsonArray()
                .setCallback(new FutureCallback<JsonArray>() {
                    @Override
                    public void onCompleted(Exception e, JsonArray result) {
                        if(e!=null){
                            e.printStackTrace();
                        }else{

                            try {
                                mapFragment.clear_map_points();

                                for (int i = 0; i < result.size(); i++) {

                                    ExchangeLocations exchangeLocations = new ExchangeLocations(result.get(i).getAsJsonObject());
                                    exchangeLocationses.add(exchangeLocations);
                                    mapFragment.add_map_point(exchangeLocations);

                                }
                            }catch (Exception ex){
                                ex.printStackTrace();
                            }
                        }
                    }
                });

    }
}

