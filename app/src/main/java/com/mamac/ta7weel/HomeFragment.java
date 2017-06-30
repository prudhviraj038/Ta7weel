package com.mamac.ta7weel;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.media.session.IMediaControllerCallback;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.nikoyuwono.toolbarpanel.ToolbarPanelLayout;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Function;
import org.mozilla.javascript.NativeObject;
import org.mozilla.javascript.Scriptable;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Locale;

import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import static com.mamac.ta7weel.Session.format_decimals;

/**
 * Created by mac on 3/18/17.
 */


public class HomeFragment extends Fragment {
    boolean first_open = false;

    LinearLayout calc_one,calc_two,calc_three,calc_four,calc_five,calc_six,calc_seven,calc_eight,calc_nine,calc_zero;
    LinearLayout calc_plus,calc_minus,calc_multiple,calc_divide,calc_equals;
    LinearLayout calc_one_p_zero,calc_point;
    LinearLayout calc_clear,calc_bc;

    ImageView btn_calculator;
    TextView currency_value;
 //   private ToolbarPanelLayout toolbarPanelLayout;
    boolean is_open = false;

    String first_value,second_value;
    String previous_symbol,current_symbol;

    RecyclerView recyclerView ;
    RatesAdapter ratesAdapter;
    RelativeLayout panel;

    ImageView local_currency_img;
    TextView local_currency_txt;
    TextView local_currency_symbol;
    TextView local_currency_name;
    TextView last_update;
    TextView shake_to_convert_text;

    String current_rate;
    Rates current_item;



    JSONArray jsonArray;

    FragmentTouchListner mCallBack;

    Float user_value = 1.00f;



    private RecyclerViewMoveItemCallback recyclerViewMoveCallback;
    private RecyclerViewInsertItemCallback recyclerViewInsertCallback;
    private RecyclerViewNotifyItemCallback recyclerViewNotifyCallback;

    public interface RecyclerViewMoveItemCallback {
        void onLayoutReady();
    }


    public interface RecyclerViewInsertItemCallback {
        void onLayoutReady();
    }

    public interface RecyclerViewNotifyItemCallback {
        void onLayoutReady();
    }



    public static HomeFragment newInstance(int someInt) {
        HomeFragment myFragment = new HomeFragment();

        Bundle args = new Bundle();
        args.putInt("someInt", someInt);
        myFragment.setArguments(args);

        return myFragment;
    }

    public interface FragmentTouchListner{


        public void show_progress();
        public void hide_progress();


    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallBack = (MainActivity) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement LogoutUser");
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);
       // toolbarPanelLayout = (ToolbarPanelLayout) view.findViewById(R.id.sliding_down_toolbar_layout);

        try {

            jsonArray = new JSONArray();
            jsonArray.put(new JSONObject().put("city_id","city").put("city_name","city"));
            jsonArray.put(new JSONObject().put("city_id","city").put("city_name","city"));
            jsonArray.put(new JSONObject().put("city_id","city").put("city_name","city"));
            jsonArray.put(new JSONObject().put("city_id","city").put("city_name","city"));
            jsonArray.put(new JSONObject().put("city_id","city").put("city_name","city"));
            jsonArray.put(new JSONObject().put("city_id","city").put("city_name","city"));
            jsonArray.put(new JSONObject().put("city_id","city").put("city_name","city"));

        } catch (JSONException e) {
            e.printStackTrace();
        }
        local_currency_img = (ImageView) view.findViewById(R.id.local_currency_img);
        local_currency_txt = (TextView) view.findViewById(R.id.local_currency_txt);
        local_currency_symbol = (TextView) view.findViewById(R.id.currency_symbol);
        local_currency_name = (TextView) view.findViewById(R.id.currency_name);
        last_update = (TextView) view.findViewById(R.id.last_update_time);

        current_rate = "1.00";


        local_currency_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //show_alert_edit(jsonArray);
                //getdata();
            }
        });

        local_currency_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // show_alert_edit(jsonArray);
               // getdata();

            }
        });
        shake_to_convert_text = (TextView) view.findViewById(R.id.shake_to_convert_text);



        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        rates = new ArrayList<>();
        ratesAdapter = new RatesAdapter(rates,getActivity(),this);
        final LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.getItemAnimator().setMoveDuration(1000);
        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(getActivity()));
        recyclerView.setAdapter(ratesAdapter);

//        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
//            @Override
//            public void onClick(View view, final int position) {
//                Log.d("pos", "onClick " + position);
//                ratesAdapter.notifyItemMoved(position,0);
//                recyclerView.scrollToPosition(0);
//                final Rates temp = ratesAdapter.moviesList.get(0);
//                current_item = temp;
//                set_current_item(current_item);
//                currency_value.setText(current_item.value);
//
//                //prepare_rates_dynamic(Float.parseFloat(currency_value.getText().toString()));
//
//            }
//
//            @Override
//            public void onLongClick(View view, int position) {
//
//            }
//        }));






        previous_symbol="";
        current_symbol = "";

        panel = (RelativeLayout) view.findViewById(R.id.panel);

        btn_calculator = (ImageView) view.findViewById(R.id.btn_calculator);

        btn_calculator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(is_open) {
                  //  toolbarPanelLayout.closePanel();
                    panel.setVisibility(View.GONE);
                    is_open=false;
                    first_open=false;
                    currency_value.setBackgroundColor(Color.TRANSPARENT);
                    calculate();
                }
                else {
                    //toolbarPanelLayout.openPanel();
                    panel.setVisibility(View.VISIBLE);
                    is_open=true;
                    first_open=true;
                    currency_value.setBackgroundColor(Color.parseColor("#F2F2F2"));

                    if(Session.get_shake_status(getActivity()))
                        shake_to_convert_text.setText("Shake to convert");
                    else
                       shake_to_convert_text.setText("Shake to convert is disabled");


                }


            }
        });

        currency_value = (TextView) view.findViewById(R.id.currency_value);
        currency_value.setText("1.0000");
        currency_value.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //int[] pos = {0,0};

                //currency_value.getLocationOnScreen(pos);
                //Log.e("pos",pos[0] + " " + pos[1]);

                //showSortPopup(getActivity(),new Point(pos[0],pos[1]));
            }
        });

        calc_one = (LinearLayout) view.findViewById(R.id.calc_one);
        calc_two = (LinearLayout) view.findViewById(R.id.calc_two);
        calc_three = (LinearLayout) view.findViewById(R.id.calc_three);
        calc_four = (LinearLayout) view.findViewById(R.id.calc_four);
        calc_five = (LinearLayout) view.findViewById(R.id.calc_five);
        calc_six = (LinearLayout) view.findViewById(R.id.calc_six);
        calc_seven = (LinearLayout) view.findViewById(R.id.calc_seven);
        calc_eight = (LinearLayout) view.findViewById(R.id.calc_eight);
        calc_nine = (LinearLayout) view.findViewById(R.id.calc_nine);
        calc_zero = (LinearLayout) view.findViewById(R.id.calc_zero);

        calc_point = (LinearLayout) view.findViewById(R.id.calc_point);
        calc_one_p_zero = (LinearLayout) view.findViewById(R.id.calc_one_p_zero);

        calc_plus = (LinearLayout) view.findViewById(R.id.calc_plus);
        calc_minus = (LinearLayout) view.findViewById(R.id.calc_minus);
        calc_multiple = (LinearLayout) view.findViewById(R.id.calc_multiply);
        calc_divide = (LinearLayout) view.findViewById(R.id.calc_divide);
        calc_equals = (LinearLayout) view.findViewById(R.id.calc_equals);

        calc_clear = (LinearLayout) view.findViewById(R.id.calc_clear);
        calc_bc = (LinearLayout) view.findViewById(R.id.calc_backspace);

        calc_one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                this_button_pressed("1");
            }
        });

        calc_two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                this_button_pressed("2");
            }
        });

        calc_three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                this_button_pressed("3");
            }
        });
        calc_four.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                this_button_pressed("4");
            }
        });
        calc_five.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                this_button_pressed("5");
            }
        });
        calc_six.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                this_button_pressed("6");
            }
        });
        calc_seven.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                this_button_pressed("7");
            }
        });
        calc_eight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                this_button_pressed("8");
            }
        });
        calc_nine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                this_button_pressed("9");
            }
        });
        calc_zero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!currency_value.getText().toString().endsWith("/"))
                    this_button_pressed("0");
            }
        });

        calc_one_p_zero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                this_point_pressed("1.0");
            }
        });

        calc_point.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                this_point_pressed(".");
            }
        });


        calc_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                this_symbol_pressed("+");
            }
        });
        calc_minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                this_symbol_pressed("-");
            }
        });
        calc_multiple.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                this_symbol_pressed("*");
            }
        });

        calc_divide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                this_symbol_pressed("/");
            }
        });

        calc_equals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                this_equal_pressed("=");
            }
        });

        calc_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                this_clear_pressed("C");
            }
        });

        calc_bc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                this_clear_pressed("BC");
            }
        });

            //prepare_rates(1.0f);





// Extend the Callback class

        ItemTouchHelper.Callback _ithCallback = new ItemTouchHelper.Callback() {
            //and in your imlpementaion of
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                // get the viewHolder's and target's positions in your adapter data, swap them
                Collections.swap(rates, viewHolder.getAdapterPosition(), target.getAdapterPosition());
                // and notify the adapter that its dataset has changed
                ratesAdapter.notifyItemMoved(viewHolder.getAdapterPosition(), target.getAdapterPosition());
                return true;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                //TODO
            }

            //defines the enabled move directions in each state (idle, swiping, dragging).
            @Override
            public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                return makeFlag(ItemTouchHelper.ACTION_STATE_DRAG,
                        ItemTouchHelper.DOWN | ItemTouchHelper.UP | ItemTouchHelper.START | ItemTouchHelper.END);
            }
        };


        ItemTouchHelper ith = new ItemTouchHelper(_ithCallback);
        ith.attachToRecyclerView(recyclerView);


        getallcurrencies(1.00f,false);

        last_update.setText( "Last update at " + Session.getlastupdate(getActivity()));


        panel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_calculator.performClick();
            }
        });

        return view;
    }


    private void this_button_pressed(String button_label){

        Log.e("pressed",button_label);

        if(first_open){
            currency_value.setText(button_label);
            reset_currency_color();
        }else{
            if(currency_value.getText().toString().equals("0"))
                currency_value.setText(button_label);
            else
                currency_value.append(button_label);

        }


    }


    private void this_symbol_pressed(String button_label){
        Log.e("pressed",button_label);

        if(previous_symbol.equals("")){
           // first_value=currency_value.getText().toString();
            currency_value.append(button_label);
            previous_symbol=button_label;
        }else {
            calculate();
        }

     //   calculate();
    }


    private void this_equal_pressed(String button_label){
        Log.e("pressed",button_label);

//        if(previous_symbol.equals("")){
//            //currency_value.append(button_label);
//        }else {
//            calculate();
//        }
        btn_calculator.performClick();

        calculate();
    }



    private void this_clear_pressed(String button_label){
        Log.e("pressed",button_label);

        if(button_label.equals("C")){

            previous_symbol="";
            current_symbol = "";
            first_value = "";
            second_value = "";
            currency_value.setText("0");

        }else{
            int length = currency_value.getText().length();
            if(length>1)
            currency_value.setText(currency_value.getText().subSequence(0,length-1));
            else if(length==1)
                currency_value.setText("0");
        }


    }

    private void this_point_pressed(String button_label){
        Log.e("pressed",button_label);
    }

    private void calculate(){

        String input = currency_value.getText().toString().replace("/","//");

        int total = 0;

//        try {
//            if (previous_symbol.equals("+"))
//                total = Integer.parseInt(first_value) + Integer.parseInt(second_value);
//            else if (previous_symbol.equals("-"))
//                total = Integer.parseInt(first_value) - Integer.parseInt(second_value);
//            else if (previous_symbol.equals("X"))
//                total = Integer.parseInt(first_value) * Integer.parseInt(second_value);
//            else
//                total = Integer.parseInt(first_value) / Integer.parseInt(second_value);
//            currency_value.setText(String.valueOf(total));
//        }catch (Exception ex){
//            currency_value.setText("0");
//            calc_clear.performClick();
//
//        }


        Object[] params = new Object[] { "Android" };

// Every Rhino VM begins with the enter()
// This Context is not Android's Context
        Context rhino = Context.enter();

// Turn off optimization to make Rhino Android compatible
        rhino.setOptimizationLevel(-1);
        try {
            Scriptable scope = rhino.initStandardObjects();

            // Note the forth argument is 1, which means the JavaScript source has
            // been compressed to only one line using something like YUI
            rhino.evaluateString(scope, "function hello(java) {\n" +
                    "    if (typeof log != 'undefined') {\n" +
                    "        log(\"JavaScript say hello to \" + java);\n" +
                    "\n" +
                    "        log(\"Also, I can access Java object: \" + javaContext);\n" +
                    "    }\n" +
                    "\n" +
                    "    return { foo: eval('"+currency_value.getText().toString()+"') };\n" +
                    "}", "JavaScript", 1, null);

            // Get the functionName defined in JavaScriptCode
            Object obj = scope.get("hello", scope);

            if (obj instanceof Function) {
                Function jsFunction = (Function) obj;

                // Call the function with params
                Object jsResult = jsFunction.call(rhino, scope, scope, params);

                // Parse the jsResult object to a String
                final String result = (String) Context.jsToJava(((NativeObject)jsResult).get("foo", (NativeObject)jsResult), String.class);
                Log.e("res",result);
                currency_value.setText(format_decimals(Float.valueOf(result)));
                previous_symbol="";
                prepare_rates_dynamic(Float.valueOf(result));
                user_value = Float.valueOf(result);

            }
        } finally {
            Context.exit();
        }


    }

    ArrayList<Rates> rates;

    float rate_cad=1.33f;
    float rate_eur=0.93f;
    float rate_jpy=112.66f;
    float rate_skw=1117.63f;
    float rate_egp=18.30f;

    float value_cad=1.0f;
    float value_eur=2.0f;
    float value_jpy=3.0f;
    float value_skw=4.0f;
    float value_egp=5.0f;


    private  void prepare_rates(Float user_entered_value){


        rates.clear();



        value_cad = user_entered_value*rate_cad;
        value_eur = user_entered_value*rate_eur;
        value_jpy = user_entered_value*rate_jpy;
        value_skw = user_entered_value*rate_skw;
        value_egp = user_entered_value*rate_egp;


            rates.add(new Rates(R.drawable.ic_flag_canada,"1 USD = "+rate_cad+" CAD","$ "+ format_decimals(value_cad),"Candian Dollar","CAD"));
            rates.add(new Rates(R.drawable.ic_flag_europe,"1 USD = "+rate_eur+" EUR","$ "+format_decimals(value_eur),"EUR","EUR"));
            rates.add(new Rates(R.drawable.ic_flag_japan,"1 USD = "+ rate_jpy+" JPY","$ "+format_decimals(value_jpy),"JPY","YEN"));
            rates.add(new Rates(R.drawable.ic_flag_skw,"1 USD = "+ rate_skw+" SKW","$ "+format_decimals(value_skw),"SKW","SKW"));
            rates.add(new Rates(R.drawable.ic_flag_egp,"1 USD = "+ rate_egp+" EGP","$ "+format_decimals(value_egp),"EGP","EGP"));

            ratesAdapter.notifyDataSetChanged();


    }



    CategoryAdapter categoryAdapter;
    ArrayList<Rates> categories;

    private void show_alert_edit(JsonArray jsonArray){
        categories = new ArrayList<>();
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

                    Picasso.with(getActivity()).load(categories.get(i).image).into(local_currency_img);
                    local_currency_txt.setText(categories.get(i).short_name);
                    local_currency_symbol.setText(categories.get(i).symbol);
                    local_currency_name.setText(categories.get(i).long_name);
                    current_rate=categories.get(i).base_rate;


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


        for (int i = 0; i < jsonArray.size(); i++) {

            try {
                JsonObject tempjson = jsonArray.get(i).getAsJsonObject();
                Rates category = new Rates(tempjson);
                categories.add(category);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        categoryAdapter.notifyDataSetChanged();


        alertDialog.show();


    }



    private void getdata(){

        mCallBack.show_progress();

        Ion.with(getActivity())
                .load(Session.SERVER_URL+"currency.php")
                .asJsonArray()
                .setCallback(new FutureCallback<JsonArray>() {
                    @Override
                    public void onCompleted(Exception e, JsonArray result) {
                        // do stuff with the result or error
                        mCallBack.hide_progress();
                        if(e==null) {
                            Log.e("response", result.toString());
                            show_alert_edit(result);
                        }
                        else
                            Log.e("error",e.getLocalizedMessage());
                    }
                });


    }

    private void getallcurrencies(Float user_enterd_value,boolean update){

        JsonArray result = AppController.getInstance().rates;

        if(rates!=null)
        {
            rates.clear();
            Log.e("response", result.toString());
            for(int i=0;i<result.size();i++){

                Rates temp = new Rates(result.get(i).getAsJsonObject());

                if(AppController.getInstance().selected_channels.contains(temp.short_name))

                    if(current_item==null) {
                        Log.e(temp.short_name,Session.getCurrencyID(getActivity()));
                        if (temp.short_name.equals(Session.getCurrencyID(getActivity()))) {
                            current_item = temp;
                            rates.add(0,temp);
                        }else{
                            rates.add(temp);
                        }
                }else {

                        if(current_item.short_name.equals(temp.short_name))
                            rates.add(0,temp);

                        else
                            rates.add(temp);
                    }


            }

            if(current_item==null)
                current_item=rates.get(0);

            set_current_item(current_item,update);
           // ratesAdapter.notifyDataSetChanged();
            prepare_rates_dynamic(user_enterd_value);

        }


    }


    private  void prepare_rates_dynamic(Float user_entered_value){






//        value_cad = user_entered_value*rate_cad;
//        value_eur = user_entered_value*rate_eur;
//        value_jpy = user_entered_value*rate_jpy;
//        value_skw = user_entered_value*rate_skw;
//        value_egp = user_entered_value*rate_egp;
//
//
//        rates.add(new Rates(R.drawable.ic_flag_canada,"1 USD = "+rate_cad+" CAD","$ "+format_decimals(value_cad),"Candian Dollar","CAD"));
//        rates.add(new Rates(R.drawable.ic_flag_europe,"1 USD = "+rate_eur+" EUR","$ "+format_decimals(value_eur),"EUR","EUR"));
//        rates.add(new Rates(R.drawable.ic_flag_japan,"1 USD = "+ rate_jpy+" JPY","$ "+format_decimals(value_jpy),"JPY","YEN"));
//        rates.add(new Rates(R.drawable.ic_flag_skw,"1 USD = "+ rate_skw+" SKW","$ "+format_decimals(value_skw),"SKW","SKW"));
//        rates.add(new Rates(R.drawable.ic_flag_egp,"1 USD = "+ rate_egp+" EGP","$ "+format_decimals(value_egp),"EGP","EGP"));




        for(int i=0;i<ratesAdapter.moviesList.size();i++){

            Float temp = Float.parseFloat(ratesAdapter.moviesList.get(i).base_rate)*user_entered_value;

            ratesAdapter.moviesList.get(i).value = format_decimals( temp/Float.parseFloat(current_rate));

            ratesAdapter.notifyItemChanged(i);

       }




    }



    public   void set_current_item(Rates rate, final Integer pos){

        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                Picasso.with(getActivity()).load(current_item.image).into(local_currency_img);
                local_currency_txt.setText(current_item.short_name);
                local_currency_symbol.setText(current_item.symbol);
                local_currency_name.setText(current_item.long_name);
                currency_value.setText(current_item.value);
                user_value = Float.parseFloat(current_item.value);

                current_rate=current_item.base_rate;
                ratesAdapter.notifyDataSetChanged();

            }
        };


        final Handler handler = new Handler();
        handler.postDelayed(runnable,1000);



    }


    public   void set_current_item(Rates rate,boolean update){

        Picasso.with(getActivity()).load(current_item.image).into(local_currency_img);
        local_currency_txt.setText(current_item.short_name);
        local_currency_symbol.setText(current_item.symbol);
        local_currency_name.setText(current_item.long_name);

           if(!update)
           currency_value.setText(current_item.value);

        user_value = Float.parseFloat(current_item.value);
        current_rate=current_item.base_rate;




    }

    PopupWindow changeSortPopUp;


    private void showSortPopup(final Activity context, Point p)
    {
        // Inflate the popup_layout.xml
     //   LinearLayout viewGroup = (LinearLayout) context.findViewById(R.id.currency_value);

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(android.content.Context.LAYOUT_INFLATER_SERVICE);
        View layout = layoutInflater.inflate(R.layout.select_text_popup,null);

        TextView copy = (TextView) layout.findViewById(R.id.pop_copy);
        TextView cut = (TextView) layout.findViewById(R.id.pop_cut);
        TextView paste = (TextView) layout.findViewById(R.id.pop_paste);

        copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeSortPopUp.dismiss();
            }
        });

        cut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeSortPopUp.dismiss();
            }
        });

        paste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeSortPopUp.dismiss();
            }
        });

        // Creating the PopupWindow
        changeSortPopUp = new PopupWindow(context);
        changeSortPopUp.setContentView(layout);
        changeSortPopUp.setWidth(LinearLayout.LayoutParams.WRAP_CONTENT);
        changeSortPopUp.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        changeSortPopUp.setFocusable(true);


        // Some offset to align the popup a bit to the left, and a bit down, relative to button's position.
        Log.e("width",currency_value.getWidth() + "");

        int OFFSET_X = -dp_to_pixels(100.0f)+ (currency_value.getWidth()/2);
        int OFFSET_Y = -dp_to_pixels(40.0f);

        // Clear the default translucent background
        changeSortPopUp.setBackgroundDrawable(new BitmapDrawable());

        // Displaying the popup at the specified location, + offsets.
        changeSortPopUp.showAtLocation(currency_value, Gravity.NO_GRAVITY, p.x + OFFSET_X, p.y + OFFSET_Y);



    }


        private int dp_to_pixels(Float dp){

                Resources r = getResources();
                float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics());
            return (int)px;
        }



    private void reset_currency_color(){

        if(first_open){
            first_open=false;
            currency_value.setBackgroundColor(Color.TRANSPARENT);
        }

    }


    public void refresh_rates(){

        Log.e("user_value",String.valueOf(user_value));

        last_update.setText("Last update at "+ Session.getCurrentTimeStamp());


        getallcurrencies(user_value,true);

    }


    public void device_shaked(){
        if(panel.getVisibility()==View.VISIBLE){
            calculate();
            panel.setVisibility(View.GONE);

        }
    }


}
