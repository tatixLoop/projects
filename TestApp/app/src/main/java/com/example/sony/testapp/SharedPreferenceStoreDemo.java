package com.example.sony.testapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SharedPreferenceStoreDemo extends AppCompatActivity implements View.OnClickListener{

    EditText t1,t2;
    Button b1;
    SharedPreferences sh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shared_preference_store_demo);

        t1=(EditText)findViewById(R.id.t1);
        t2=(EditText)findViewById(R.id.t2);
        b1=(Button)findViewById(R.id.btSave);
        b1.setOnClickListener(this);
        sh=getSharedPreferences("Data", MODE_PRIVATE);


    }

    @Override
    public void onClick(View v) {

        if(v==b1){

            String n=t1.getText().toString();
            String a=t2.getText().toString();
            int aa=Integer.parseInt(a);


            SharedPreferences.Editor edt=sh.edit();
            edt.putString("k1", n);
            edt.putInt("k2", aa);
            edt.apply();// or edt.coomit();

            Intent  ii= new Intent(SharedPreferenceStoreDemo.this, SharedPrefereceRetrieveDemo.class);
            startActivity(ii);

        }
    }
}
