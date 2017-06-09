package com.mamac.ta7weel;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by mac on 3/18/17.
 */

public class NewsFragment extends Fragment {
    RecyclerView recyclerView;
    MultiViewTypeActAdapter adapter;

    public static NewsFragment newInstance(int someInt) {
        NewsFragment myFragment = new NewsFragment();

        Bundle args = new Bundle();
        args.putInt("someInt", someInt);
        myFragment.setArguments(args);

        return myFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_news, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        news = new ArrayList<>();
        adapter = new MultiViewTypeActAdapter(news,getActivity());
        recyclerView.setAdapter(adapter);
        progress_holder = (LinearLayout) view.findViewById(R.id.progress_holder);
        progress_holder.setVisibility(View.GONE);

        get_news();

        return view;
    }

ArrayList<News> news;
    LinearLayout progress_holder;


    private void get_news(){


        String url = "http://ta7weel.net/api/news.php?lang=en";
        show_progress();
        Ion.with(getActivity())
                .load(url)
                .asJsonArray()
                .setCallback(new FutureCallback<JsonArray>() {
                    @Override
                    public void onCompleted(Exception e, JsonArray result) {
                        // do stuff with the result or error
                        hide_progress();
                        if(e==null) {
                            Log.e("response", result.toString());

                            for(int i=0;i<result.size();i++){

                                MyJsonObject myJsonObject = new MyJsonObject(result.get(i).getAsJsonObject());
                                News temp = new News(myJsonObject,getActivity());
                                if(!temp.id.equals("0"))
                                news.add(temp);

                                Log.e("error","error");

                            }
                            adapter.notifyDataSetChanged();

                        }else{
                            e.printStackTrace();
                        }
                    }
                });
    }


    public void show_progress() {
        progress_holder.setVisibility(View.VISIBLE);
    }


    public void hide_progress() {
        progress_holder.setVisibility(View.GONE);
    }


}
