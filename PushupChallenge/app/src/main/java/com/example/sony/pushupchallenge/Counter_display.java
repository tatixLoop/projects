package com.example.sony.pushupchallenge;

        import android.annotation.TargetApi;
        import android.content.Intent;
        import android.graphics.Color;
        import android.hardware.Sensor;
        import android.hardware.SensorEvent;
        import android.hardware.SensorEventListener;
        import android.hardware.SensorManager;
        import android.os.Build;
        import android.os.Handler;
        import android.os.SystemClock;
        import android.speech.tts.TextToSpeech;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.view.WindowManager;
        import android.widget.TextView;
        import android.widget.Toast;


        import java.util.Locale;



public class Counter_display extends AppCompatActivity implements TextToSpeech.OnInitListener,SensorEventListener {

    private SensorManager mSensorManager;
    private Sensor mSensor;
    private int nearCount;
    //TextView textView;
    TextToSpeech tts;
    private int MY_DATA_CHECK_CODE=0;

    TextView tv_num,time;


    long starttime = 0L;
    long timeInMilliseconds = 0L;
    long timeSwapBuff = 0L;
    long updatedtime = 0L;
    int t = 1;
    int secs = 0;
    int mins = 0;
    int milliseconds = 0;
    Handler handler = new Handler();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pushup_challenger);

                //check for TTS data
                Intent checkTTSIntent = new Intent();
                checkTTSIntent.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
                startActivityForResult(checkTTSIntent, MY_DATA_CHECK_CODE);

                mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
                mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
                tv_num = (TextView) findViewById(R.id.tv_chllengecount);


        //*********SCREEN WAKE CODE *******
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

    }

//*************** STOP WATCH ****super*********
public Runnable updateTimer = new Runnable() {
    public void run() {
        timeInMilliseconds = SystemClock.uptimeMillis() - starttime;
        updatedtime = timeSwapBuff + timeInMilliseconds;
        secs = (int) (updatedtime / 1000);
        mins = secs / 60;
        secs = secs % 60;
        milliseconds = (int) (updatedtime % 1000);
        time.setText("" + mins + ":" + String.format("%02d", secs) + ":"
                + String.format("%03d", milliseconds));
        time.setTextColor(Color.RED);
        handler.postDelayed(this, 0);
    }};


    //******** BACK KEY PRESSED********
            @Override
            public void onBackPressed() {
              // Intent i = new Intent(getApplicationContext(),PushupList.class);
              //  startActivity(i);
               this. finish();
              //  overridePendingTransition(R.anim.push_up_in, R.anim.push_up_out);
                //setContentView(R.layout.activity_counter_display);
              //  super.finish();

            }

            // ************PROXIMITY SENSOR LISTENER ***********

            protected void onResume() {

                super.onResume();
                mSensorManager.registerListener(this, mSensor,
                        SensorManager.SENSOR_DELAY_NORMAL);
            }

            protected void onPause() {
                super.onPause();
                mSensorManager.unregisterListener(this);
            }

            //******PROXIMITY SENSOR CHANGE EVENT *****
            @Override
            public void onSensorChanged(SensorEvent event) {
                if (event.values[0] < event.sensor.getMaximumRange()) {
                    nearCount++;
                    tv_num.setText(String.format("%d", nearCount));
                    speakOut();
                    //****start stop watch *****
                    if (nearCount==1)
                    {
                    starttime = SystemClock.uptimeMillis();
                    handler.postDelayed(updateTimer, 0);
                    t = 0;
                    time = (TextView) findViewById(R.id.time);}
                }
                else if(nearCount==5)
                {
                    finish();
                }

            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }

            //********* TEXT TO SPEECH CODE***********
            public void onDestroy() {
                // Don't forget to shutdown!
                if (tts != null) {
                    tts.stop();
                    tts.shutdown();
                }
                super.onDestroy();
            }

            //act on result of TTS data check
            protected void onActivityResult(int requestCode, int resultCode, Intent data) {

                if (requestCode == MY_DATA_CHECK_CODE) {
                    if (resultCode == TextToSpeech.Engine.CHECK_VOICE_DATA_PASS) {
                        //the user has the necessary data - create the TTS
                        tts = new TextToSpeech(this, this);
                    }
                    else {
                        //no data - install it now
                        Intent installTTSIntent = new Intent();
                        installTTSIntent.setAction(TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
                        startActivity(installTTSIntent);
                    }
                }
            }

            public void onInit(int status) {


                if (status == TextToSpeech.SUCCESS) {
                    if(tts.isLanguageAvailable(Locale.US)==TextToSpeech.LANG_AVAILABLE)
                        tts.setLanguage(Locale.US);
                }
                else if (status == TextToSpeech.ERROR) {
                    Toast.makeText(this, "Sorry! Text To Speech failed...", Toast.LENGTH_LONG).show();
                }

         /*   int result = tts.setLanguage(Locale.UK);

             //tts.setPitch(2); // set pitch level

            //tts.setSpeechRate(2); // set speech speed rate

            if (result == TextToSpeech.LANG_MISSING_DATA
                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "Language is not supported");
            } else {
                speakOut();
            }

        } else {
            Log.e("TTS", "Initilization Failed");
        }*/

            }

            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            private void speakOut() {

                String text =tv_num.getText().toString();

                if(Build.VERSION.RELEASE.startsWith("5"))
                {
                    tts.speak(text, TextToSpeech.QUEUE_FLUSH, null,null);
                }
                else {
                    tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
                }
            }
        }





