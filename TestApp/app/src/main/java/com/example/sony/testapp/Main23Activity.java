package com.example.sony.testapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

//intent 2nd page   ......
public class Main23Activity extends AppCompatActivity  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main23);

        Bundle b=getIntent().getExtras();
        if(b!=null){
            int sqr=b.getInt("k_sqr");
            Toast.makeText(Main23Activity.this, "sqr ="+sqr,Toast.LENGTH_SHORT).show();

        }
    }
}
