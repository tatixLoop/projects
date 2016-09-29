package com.appstory.aarppo;

import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Environment;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

public class AarpoBlast extends AppCompatActivity {
    int timeLeft;
    TextView txtCountDown;// = (TextView)findViewById(R.id.txt_counter_cheer);

    Handler handler2;
    Boolean bool = false;
    Handler handler,dhandler;
    Runnable runnable;
    MediaPlayer mp;
    boolean donotplay = false;

    public Runnable updateTimer = new Runnable() {
        public void run() {

            timeLeft--;
            txtCountDown.setText(""+timeLeft);
            handler2.postDelayed(this, 1000);

            if(timeLeft == 0 && donotplay == false)
            {
                handler2.removeCallbacks(updateTimer);

                dhandler= new Handler();
                dhandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Initial();
                    }
                },0);
                /*try{
                    //you can change the path, here path is external directory(e.g. sdcard) /Music/maine.mp3

                    mP.setDataSource(Environment.getExternalStorageDirectory().getPath()+"/music/Jiya_Jale-VmusiQ.Com.mp3");


                   // mP= MediaPlayer.create(this, R.raw.ccc);
                    mP.prepare();
                    mP.start();
                    Toast.makeText(getApplicationContext(), "playing", Toast.LENGTH_SHORT).show();

                    mP.prepare();
                }catch(Exception e){

                    e.printStackTrace();}*/
            }
        }};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_aarpo_blast);
        donotplay = false;
        txtCountDown = (TextView)findViewById(R.id.txt_counter_cheer);

       // mP=new MediaPlayer();

      //  timeLeft = getIntent().getIntExtra("timeLeft",0);
        timeLeft =15;
        handler2 = new Handler();
        handler2.postDelayed(updateTimer, 0);
        //*********SCREEN WAKE CODE *******
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    private void Initial()
    {
        mp = MediaPlayer.create(this, R.raw.song);
        handler = new Handler();
        runnable=new Runnable() {
            @Override
            public void run() {
                handler.postDelayed(this, 750);
                changeColor();
                mp.start();
            }
        };
        handler.postDelayed(runnable,750);
    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
        donotplay = true;
        if(timeLeft <=0) {
            mp.stop();
            handler.removeCallbacks(runnable);
        }
    }

    public void changeColor() {
        if (bool) {
            findViewById(R.id.flashscreen).setBackgroundColor(Color.YELLOW);
            bool = false;
        } else {
            findViewById(R.id.flashscreen).setBackgroundColor(Color.BLUE);
            bool = true;

        }
    }
}
