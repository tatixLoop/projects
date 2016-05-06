package com.appsecurity.tatix.appsecurity;

import android.accessibilityservice.AccessibilityService;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;

/**
 * Created by jithin on 3/1/16.
 */
public class MyAccessibilityService extends AccessibilityService {
    public static Context ctx;
    public static boolean checkUsageStatFlag;
    public boolean startLockThread = false;

    public static  void setMyContext(Context myCtx)
    {
        ctx = myCtx;
    }

    public void startLockingThread()
    {
        Log.d("JKS","Starting locking thread in accessibility service");
        checkUsageStatFlag = false;
        MainActivityLockScreen.setAppCtx(getApplicationContext());
        try{

            SQLiteDatabase db = openOrCreateDatabase("tileLockDB", Context.MODE_PRIVATE, null);
            db.execSQL("CREATE TABLE IF NOT EXISTS tileLockApps(package VARCHAR);");
            Cursor c=db.rawQuery("SELECT * FROM tileLockApps", null);
            MainActivityLockScreen.loadAllApplications(getPackageManager(),db,c);
        }
        catch (Exception ex) {
            Log.d("JKS ","Caught exception in sql code");
        }


        /* thread that actually locks */
        Thread threadLockEngine = new Thread() {
            @Override
            public void run() {
                try {
                    boolean run = true;
                    do {
                        sleep(1000);
                        if(MainActivityLockScreen.getSecurityStatus() == true && checkUsageStatFlag == true)
                        {
                            Log.d("JKS", "Launch print stats");
                            //UStats.printCurrentUsageStatus(ctx);
                            UStats.printCurrentUsageStatus(getApplicationContext());
                            checkUsageStatFlag = false;
                        }

                    }while(run);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (RuntimeException r){
                    r.printStackTrace();
                }
            }
        };

        threadLockEngine.start();
    }
    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        // TODO Auto-generated method stub
        if(event.getEventType() == AccessibilityEvent.TYPES_ALL_MASK){
            //    Log.d("JKS","Event " +event.getClassName().toString());
        }

        if(event.getEventType() == AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED){
            //      Log.d("JKS","Event TYPE_WINDOW_CONTENT_CHANGED " +event.getClassName().toString());

            // UStats.printCurrentUsageStatus(ctx);
        }

        if(event.getEventType() == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED){
            //    Log.d("JKS","Event TYPE_WINDOW_STATE_CHANGED " +event.getClassName().toString());
        }

        if(event.getEventType() == AccessibilityEvent.TYPE_ANNOUNCEMENT){
            //   Log.d("JKS","Event TYPE_ANNOUNCEMENT " +event.getClassName().toString());
        }
        if(event.getEventType() == AccessibilityEvent.TYPE_VIEW_CLICKED){
            if(startLockThread == false)
            {
                Log.d("JKS","Locking thread hasn't started, start locking thread");
                startLockThread = true;
                startLockingThread();
            }
            Log.d("JKS", "Event TYPE_VIEW_CLICKED  " + event.getClassName().toString());
            //UStats.printCurrentUsageStatus(getApplicationContext());
            checkUsageStatFlag = true;
        }

        if(event.getEventType() == AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED){
            //      Log.d("JKS","TYPE_NOTIFICATION_STATE_CHANGED " +event.getClassName().toString());
        }
        if(event.getEventType() == AccessibilityEvent.TYPE_WINDOWS_CHANGED){
            //        Log.d("JKS","Event  TYPE_WINDOWS_CHANGED " +event.getClassName().toString());
        }
        if(event.getEventType() == AccessibilityEvent.CONTENT_CHANGE_TYPE_CONTENT_DESCRIPTION){
            //       Log.d("JKS","Event CONTENT_CHANGE_TYPE_CONTENT_DESCRIPTION " +event.getClassName().toString());
        }

        if(event.getEventType() == AccessibilityEvent.TYPE_VIEW_ACCESSIBILITY_FOCUS_CLEARED){
            //        Log.d("JKS","Event TYPE_VIEW_ACCESSIBILITY_FOCUS_CLEARED " +event.getClassName().toString());
        }

        if(event.getEventType() == AccessibilityEvent.CONTENT_CHANGE_TYPE_CONTENT_DESCRIPTION){
            //      Log.d("JKS","Event CONTENT_CHANGE_TYPE_CONTENT_DESCRIPTION " +event.getClassName().toString());
        }
    }

    @Override
    public void onInterrupt() {
        // TODO Auto-generated method stub

    }

}