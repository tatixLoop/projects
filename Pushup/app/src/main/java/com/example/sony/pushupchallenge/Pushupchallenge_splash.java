package com.example.sony.pushupchallenge;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Pushupchallenge_splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_pushupchallenge_splash);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //create an intent will start the home activity


                Intent intent =new Intent(Pushupchallenge_splash.this,Pushupchallenge_home.class);
                Pushupchallenge_splash.this.startActivity(intent);
               Pushupchallenge_splash.this.finish();

            }
        },5000);

    }

}
