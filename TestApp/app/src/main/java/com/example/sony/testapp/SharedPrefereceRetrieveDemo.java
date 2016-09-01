package com.example.sony.testapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class SharedPrefereceRetrieveDemo extends AppCompatActivity {

    SharedPreferences sh1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shared_preferece_retrieve_demo);

        sh1=getSharedPreferences("Data", MODE_PRIVATE);

        String n=sh1.getString("k1", "");
        int a=sh1.getInt("k2", 0);
        if(n.equals("")){
            Toast.makeText(this, "no data found!!", Toast.LENGTH_SHORT).show();

            ///if no dat is found go to insert page


            Intent i=new Intent(SharedPrefereceRetrieveDemo.this,SharedPreferenceStoreDemo.class);
            startActivity(i);
        }
        else
            Toast.makeText(this, "value="+n, Toast.LENGTH_SHORT).show();

    }
}
