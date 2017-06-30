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
    int visibleItemCount,totalItemCount,firstVisibleItemIndex;
    String last_loaded_id="0";
    MainActivity mainActivity;


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
        mainActivity = (MainActivity) getActivity();
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        final LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        news = new ArrayList<>();
        adapter = new MultiViewTypeActAdapter(news,getActivity(),mainActivity);
        recyclerView.setAdapter(adapter);
        progress_holder = (LinearLayout) view.findViewById(R.id.progress_holder);
        progress_holder.setVisibility(View.GONE);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);


                visibleItemCount = recyclerView.getChildCount();
                totalItemCount = mLayoutManager.getItemCount();
                firstVisibleItemIndex = mLayoutManager.findFirstVisibleItemPosition();


                if (((totalItemCount) - visibleItemCount) <= firstVisibleItemIndex ) {
                    // Loading NOT in progress and end of list has been reached
                    // also triggered if not enough items to fill the screen
                    // if you start loading


                    Log.e("reached", "climax");

                    if(!last_loaded_id.equals(news.get(news.size()-1).id)) {
                        get_news(news.get(news.size() - 1).id);
                        last_loaded_id = news.get(news.size() - 1).id;
                    }

                } else if (firstVisibleItemIndex == 0) {

                    // top of list reached



                }



            }

        });





        get_news("0");

        return view;
    }

ArrayList<News> news;
    LinearLayout progress_holder;


    public void get_news(String last_id){



        String url = Session.SERVER_URL+"news.php?lang="+Session.getLan(getActivity());

        if(last_id.equals("0")){
            last_id = "0" ;
            show_progress();
            news.clear();
            adapter.notifyDataSetChanged();
        }else{

            url = url+"&last_id="+last_id;

        }

        Log.e("url",url);

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

                               // Log.e("error","error");

                            }
                            adapter.notifyDataSetChanged();

                            if(last_loaded_id.equals("0"))
                            recyclerView.scrollToPosition(0);



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
