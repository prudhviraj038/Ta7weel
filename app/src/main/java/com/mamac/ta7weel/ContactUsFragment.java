package com.mamac.ta7weel;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by yellowsoft on 30/6/17.
 */

public class ContactUsFragment extends Fragment {
    EditText name,email,phone,message;
    LinearLayout submit_btn;


    public static ContactUsFragment newInstance(String someInt) {
        ContactUsFragment myFragment = new ContactUsFragment();

        Bundle args = new Bundle();
        args.putString("someInt", someInt);
        myFragment.setArguments(args);

        return myFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.contact_fragment, container, false);
//        TextView textView = (TextView) view.findViewById(R.id.textView);
//        textView.setText(String.valueOf(getArguments().getInt("someInt")));

        //setHasOptionsMenu(true);

        name = (EditText) view.findViewById(R.id.name);
        email = (EditText) view.findViewById(R.id.email);
        phone = (EditText) view.findViewById(R.id.phone);
        message = (EditText) view.findViewById(R.id.message);
        submit_btn = (LinearLayout) view.findViewById(R.id.submit_btn);
        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name_string = name.getText().toString();
                String email_string = email.getText().toString();
                String phone_string = phone.getText().toString();
                String message_string = message.getText().toString();
                if (name_string.equals("")){
                    Toast.makeText(getContext(),"Please Enter Name",Toast.LENGTH_SHORT).show();
                }else if (email_string.equals("")){
                    Toast.makeText(getContext(),"Please Enter Email",Toast.LENGTH_SHORT).show();
                }else if (phone_string.equals("")){
                    Toast.makeText(getContext(),"Please Enter Phone",Toast.LENGTH_SHORT).show();
                }else if (name_string.equals("")){
                    Toast.makeText(getContext(),"Please Enter Message",Toast.LENGTH_SHORT).show();
                }else {
                    Ion.with(this)
                            .load(Session.SERVER_URL+"contact-us.php")
                            .setBodyParameter("name",name_string)
                            .setBodyParameter("email",email_string)
                            .setBodyParameter("phone",phone_string)
                            .setBodyParameter("message",message_string)
                            .asJsonObject()
                            .setCallback(new FutureCallback<JsonObject>() {
                                @Override
                                public void onCompleted(Exception e, JsonObject result) {
                                    if (result.get("status").getAsString.equals("Sucess")){
                                        Toast.makeText(getContext(),result.get("message").getAsString,Toast.LENGTH_SHORT).show();
                                    }else{
                                        Toast.makeText(getContext(),result.get("message").getAsString,Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });


        return view;
    }

}
