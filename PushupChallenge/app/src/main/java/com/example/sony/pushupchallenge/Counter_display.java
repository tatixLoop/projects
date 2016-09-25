package com.example.sony.pushupchallenge;

        import android.annotation.TargetApi;
        import android.content.Intent;
        import android.database.Cursor;
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
        import android.util.Log;
        import android.view.View;
        import android.view.WindowManager;
        import android.widget.Button;
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

    boolean startChallenge;
    Button btn_stop;

    int targetPushUp = 10;
    int pushUp_id = 0;
    int targetTime = 0;
    boolean  challengeCompleted;


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
        time = (TextView) findViewById(R.id.time);
        btn_stop = (Button) findViewById(R.id.btn_stop);

        startChallenge = false;
        time.setVisibility(View.GONE);
        tv_num.setVisibility(View.GONE);
        btn_stop.setVisibility(View.GONE);
        TextView result = (TextView)findViewById(R.id.txt_challenge_result);
        result.setVisibility(View.GONE);

        Button startNow = (Button) findViewById(R.id.btn_startNow);
        startNow.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                time.setVisibility(View.VISIBLE);
                tv_num.setVisibility(View.VISIBLE);
                btn_stop.setVisibility(View.VISIBLE);
                startChallenge = true;
                starttime = SystemClock.uptimeMillis();
                Button startNow = (Button) findViewById(R.id.btn_startNow);
                startNow.setVisibility(View.GONE);
                TextView txt_temp = (TextView)findViewById(R.id.textView4);
                TextView result = (TextView)findViewById(R.id.txt_challenge_result);
                txt_temp.setVisibility(View.GONE);
                result.setVisibility(View.GONE);
                Button saveData = (Button) findViewById(R.id.btn_stop);
                saveData.setText("STOP TEST");

                t = 0;
                handler.postDelayed(updateTimer, 0);
            }
        });



        TextView txt_targetPushup = (TextView) findViewById(R.id.txt_numTargetPushUp);
        TextView txt_targetTime = (TextView) findViewById(R.id.txt_targetTime);


        String fid = getIntent().getStringExtra("fid");
        int position = getIntent().getIntExtra("position", -1);
        Log.d("JKS","Intent received on cahallenge page pos= "+position+" fid = "+fid);
        targetPushUp = getTargetPushUPFromPushupId(fid);

        txt_targetPushup.setText(" "+targetPushUp);

        PushUPtraineeData trainee = new PushUPtraineeData(getApplicationContext());
        int stamina = trainee.getStamina();
        Log.d("JKS","Stamina = "+stamina);
        targetTime = targetPushUp * stamina;
        challengeCompleted = false;

        txt_targetTime.setText(" "+ String.format("%02d", targetTime/1000) + " seconds");

        //*********SCREEN WAKE CODE *******
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        Button saveData = (Button) findViewById(R.id.btn_stop);
        saveData.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(challengeCompleted == true)
                {
                    Log.d("JKS","Took "+ (targetTime - updatedtime) + " to take "+ targetPushUp);
                    Databasepushup db = new Databasepushup(getApplicationContext());
                    db.openConnection();
                    String querry = "insert into tb_pushupdata (Pushup_id,Date,Attemptno,Numpushup,Timetaken,Badge) values("+pushUp_id+",'date', 1 ,"+targetPushUp + ","+(targetTime - updatedtime) +","+1+")";
                    Log.d("JKS","Querry = "+querry);
                    db.insertData(querry);
                    db.closeConnection();
                    finish();
                }
                else if(startChallenge == true)
                {
                    finish();
                }

                else
                {
                    Log.d("JKS", "Challenge going on");
                    time.setVisibility(View.VISIBLE);
                    tv_num.setVisibility(View.VISIBLE);
                    btn_stop.setVisibility(View.VISIBLE);
                    startChallenge = true;
                    starttime = SystemClock.uptimeMillis();
                    Button startNow = (Button) findViewById(R.id.btn_startNow);
                    startNow.setVisibility(View.GONE);
                    TextView txt_temp = (TextView)findViewById(R.id.textView4);
                    TextView result = (TextView)findViewById(R.id.txt_challenge_result);
                    txt_temp.setVisibility(View.GONE);
                    result.setVisibility(View.GONE);
                    Button saveData = (Button) findViewById(R.id.btn_stop);
                    saveData.setText("STOP CHALLENGE");
                    nearCount = 0;
                    tv_num.setText(String.format("%d", nearCount));

                    t = 0;
                    handler.postDelayed(updateTimer, 0);
                }
            }
        });

    }

    //*************** STOP WATCH ****super*********
    public Runnable updateTimer = new Runnable() {
        public void run() {
            timeInMilliseconds = SystemClock.uptimeMillis() - starttime;
            updatedtime = targetTime - timeInMilliseconds;
            if(updatedtime <= 0)
            {
                startChallenge = false;
                handler.removeCallbacks(updateTimer);
                updatedtime = 0;
                TextView result = (TextView)findViewById(R.id.txt_challenge_result);
                result.setVisibility(View.VISIBLE);
                result.setText("Challenge Failed");
                Button saveData = (Button) findViewById(R.id.btn_stop);
                saveData.setText("TRY AGAIN");
                challengeCompleted = false;
            }
            secs = (int) (updatedtime / 1000);
            mins = secs / 60;
            secs = secs % 60;
            milliseconds = (int) (updatedtime % 1000);
            time.setText("" + mins + ":" + String.format("%02d", secs) + ":"
                    + String.format("%03d", milliseconds));
            time.setTextColor(Color.RED);
            handler.postDelayed(this, 0);
        }};



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
        if(startChallenge == false) return;
        if (event.values[0] < event.sensor.getMaximumRange()) {
            nearCount++;
            tv_num.setText(String.format("%d", nearCount));
            speakOut();

        }
        else if(nearCount==targetPushUp)
        {
            handler.removeCallbacks(updateTimer);
            startChallenge = false;
            TextView result = (TextView)findViewById(R.id.txt_challenge_result);
            result.setText("Challenge completed");
            result.setVisibility(View.VISIBLE);
            challengeCompleted = true;
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
    int getTargetPushUPFromPushupId(String pushUp_id)
    {
        int pushUPNum = 5;
        Databasepushup db = new Databasepushup(getApplicationContext());
        db.openConnection();
        String querry = "select targetPushUp from tb_pushupdetails WHERE Pushup_id="+pushUp_id;
        Log.d("JKS","Querry = "+querry);
        Cursor c = db.selectData(querry);
        while(c.moveToNext())
        {
            Log.d("JKS","no= "+c.getString(0));
            pushUPNum = c.getInt(0);

        }
        db.closeConnection();

        return pushUPNum;
    }
}





