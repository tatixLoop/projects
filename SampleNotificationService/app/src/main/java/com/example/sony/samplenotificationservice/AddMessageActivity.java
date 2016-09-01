package com.example.sony.samplenotificationservice;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;

public class AddMessageActivity extends AppCompatActivity implements View.OnClickListener{
Button SAVE;
    EditText Time;
    EditText Message;
    DBConnection db1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SAVE= (Button) findViewById(R.id.bt_save);
        Time= (EditText) findViewById(R.id.et_time);
        Message= (EditText) findViewById(R.id.et_message);
        SAVE.setOnClickListener(this);
        Intent ii=new Intent(this, NotificationService.class);
        startService(ii);
    }
@Override
    protected void onStart()
    {
        super.onStart();
        db1 = new DBConnection(AddMessageActivity.this);

    }

    @Override
    public void onClick(View v) {

        if (v==SAVE)
        {
            String a=Time.getText().toString();
            String b=Message.getText().toString();
            String h=a.substring(0, a.indexOf(":"));
            String m=a.substring(a.indexOf(":") + 1);
            int hh=Integer.parseInt(h);
            int mm=Integer.parseInt(m);
            Time.setText("");
            Message.setText("");


            db1.openConnection();
            String st="insert into tb_time(Time,Message)values('"+a+"','"+b+"')";
            boolean a1= db1.insertData(st);
            if (a1)
            {


                Calendar calendar = Calendar.getInstance();



                calendar.set(Calendar.HOUR_OF_DAY, hh);
                calendar.set(Calendar.MINUTE, mm);
                calendar.set(Calendar.SECOND, 0);
                calendar.set(Calendar.AM_PM,Calendar.PM);

                Toast.makeText(AddMessageActivity.this, "adding..", Toast.LENGTH_SHORT).show();
                Intent myIntent = new Intent(this, Myalaram.class);
                PendingIntent pendingIntent  = PendingIntent.getBroadcast(this, 0, myIntent, 0);

                AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
                alarmManager.set(AlarmManager.RTC, calendar.getTimeInMillis(), pendingIntent);




                // set alarm
                Toast.makeText(this, "Inserted", Toast.LENGTH_SHORT).show();
            }
            else
                Toast.makeText(this, "notInserted", Toast.LENGTH_SHORT).show();

        }
    }
}
