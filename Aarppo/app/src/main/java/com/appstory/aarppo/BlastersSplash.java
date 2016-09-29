package com.appstory.aarppo;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.io.IOException;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class BlastersSplash extends AppCompatActivity {

    public static long ClockdriftTime = 0;
    public static long getTimeDelay()
    {
        return ClockdriftTime;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        setContentView(R.layout.activity_blasters_splash);
        Intent service = new Intent(this, AarpoCheckService.class);

        startService(service);
        ClockdriftTime = 0;

        Thread thread = new Thread() {
            @Override
            public void run() {
                ClockdriftTime = 0;
                try {
                    if(isNetworkAvailable()) {
                        Date netWorkTime = ISLMatchPage.getNetworkTime();
                        Date curTime = new Date();
                        long diffTime = netWorkTime.getTime() - curTime.getTime();

                        long secs = TimeUnit.MILLISECONDS.toSeconds(diffTime) % 60;
                        long minutes = TimeUnit.MILLISECONDS.toMinutes(diffTime) % 60;
                        long hours = TimeUnit.MILLISECONDS.toHours(diffTime) % 24;
                        long days = TimeUnit.MILLISECONDS.toDays(diffTime);

                        Log.d("JKS","+++++++++++++++++++++++++++++++++++++++++++++++++++++++");
                        Log.d("JKS", "Time drift" + days + ":" + String.format("%02d", hours) + ":" + String.format("%02d", minutes) + ":"
                                + String.format("%02d", secs));
                        Log.d("JKS","+++++++++++++++++++++++++++++++++++++++++++++++++++++++");

                        ClockdriftTime = diffTime;
                    }
                }catch (IOException ex)
                {

                }
            }
        };

        thread.start();


       Log.d("JKS", "Get network time exits");

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent i = new Intent(BlastersSplash.this, BlastersMain.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(i);

            }
        }, 2000);


    }
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
