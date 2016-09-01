package com.example.sony.testapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class viewintent extends AppCompatActivity {

    TextView txt_print;
    TextView txt_n1;
    TextView txt_add1;
TextView txt_mob1;
    TextView txt_emailadd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewintent);


        txt_print= (TextView) findViewById(R.id.textView2);
        txt_n1= (TextView) findViewById(R.id.textView3);
        txt_add1= (TextView) findViewById(R.id.textView4);
txt_mob1= (TextView) findViewById(R.id.textView);
        txt_emailadd= (TextView) findViewById(R.id.textView5);
        Bundle b1=getIntent().getExtras();
        if(b1!=null) {
           // String details = b1.getString("print");
            String n=b1.getString("name");
            String a=b1.getString("age");
            String b2= b1.getString("Address");
            String c1=b1.getString("mobno");
            String d1=b1.getString("email");

            txt_print.setText(n);
            txt_n1.setText(a);
            txt_add1.setText(b2);
            txt_mob1.setText(c1);
            txt_emailadd.setText(d1);
        }


    }
}
