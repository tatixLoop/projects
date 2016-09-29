package com.appstory.aarppo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by jithin suresh on 30-09-2016.
 */
public class AutoStart extends BroadcastReceiver
{
    public void onReceive(Context arg0, Intent arg1)
    {
        Intent intent = new Intent(arg0,AarpoCheckService.class);
        arg0.startService(intent);
        Log.i("Autostart", "started");
    }
}