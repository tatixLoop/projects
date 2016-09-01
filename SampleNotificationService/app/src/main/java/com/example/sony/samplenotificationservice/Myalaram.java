package com.example.sony.samplenotificationservice;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class Myalaram extends BroadcastReceiver {

    public static final String ALARM_ALERT_ACTION = "com.android.deskclock.ALARM_ALERT";
    public static final String ALARM_DONE_ACTION = "com.android.deskclock.ALARM_DONE";

    public Myalaram()



    {

    }


    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
//        throw new UnsupportedOperationException("Not yet implemented");

        String action = intent.getAction();
        Toast.makeText(context, "action "+action, Toast.LENGTH_SHORT).show();
        if (action.equals(ALARM_DONE_ACTION) ) {


        Intent i=new Intent(context,NotificationService.class);
            context.startService(i);




        }


    }
}
