package com.appstory.aarppo;

import android.content.Intent;
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

    Handler handler;
    MediaPlayer mP;
    public Runnable updateTimer = new Runnable() {
        public void run() {

            timeLeft--;
            txtCountDown.setText(""+timeLeft);
            handler.postDelayed(this, 1000);

            if(timeLeft == 0)
            {
                handler.removeCallbacks(updateTimer);


                try{
                    //you can change the path, here path is external directory(e.g. sdcard) /Music/maine.mp3

                    mP.setDataSource(Environment.getExternalStorageDirectory().getPath()+"/music/Jiya_Jale-VmusiQ.Com.mp3");


                   // mP= MediaPlayer.create(this, R.raw.ccc);
                    mP.prepare();
                    mP.start();
                    Toast.makeText(getApplicationContext(), "playing", Toast.LENGTH_SHORT).show();

                    mP.prepare();
                }catch(Exception e){

                    e.printStackTrace();}
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
        txtCountDown = (TextView)findViewById(R.id.txt_counter_cheer);

        mP=new MediaPlayer();

      //  timeLeft = getIntent().getIntExtra("timeLeft",0);
        timeLeft =15;
        handler = new Handler();
        handler.postDelayed(updateTimer, 0);
    }
}
