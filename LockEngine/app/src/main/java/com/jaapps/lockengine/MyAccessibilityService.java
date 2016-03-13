package com.jaapps.lockengine;

import android.accessibilityservice.AccessibilityService;
import android.content.Context;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;

/**
 * Created by jithin on 3/1/16.
 */
public class MyAccessibilityService extends AccessibilityService {
    public static Context ctx;
    public static String sActivityName;
    public static  void setMyContext(Context myCtx)
    {
        ctx = myCtx;
    }
    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        // TODO Auto-generated method stub
        if(event.getEventType() == AccessibilityEvent.TYPES_ALL_MASK){
            Log.d("JKS","Event " +event.getClassName().toString());
        }

        if(event.getEventType() == AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED){
            Log.d("JKS","Event TYPE_WINDOW_CONTENT_CHANGED " +event.getClassName().toString());

           // UStats.printCurrentUsageStatus(ctx);
        }

        if(event.getEventType() == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED){
            Log.d("JKS","Event TYPE_WINDOW_STATE_CHANGED " +event.getClassName().toString());
        }

        if(event.getEventType() == AccessibilityEvent.TYPE_ANNOUNCEMENT){
            Log.d("JKS","Event TYPE_ANNOUNCEMENT " +event.getClassName().toString());
        }
        if(event.getEventType() == AccessibilityEvent.TYPE_VIEW_CLICKED){
            Log.d("JKS","Event TYPE_VIEW_CLICKED  " +event.getClassName().toString());
            UStats.printCurrentUsageStatus(ctx);

        }

        if(event.getEventType() == AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED){
            Log.d("JKS","TYPE_NOTIFICATION_STATE_CHANGED " +event.getClassName().toString());
        }
        if(event.getEventType() == AccessibilityEvent.TYPE_WINDOWS_CHANGED){
            Log.d("JKS","Event  TYPE_WINDOWS_CHANGED " +event.getClassName().toString());
        }
        if(event.getEventType() == AccessibilityEvent.CONTENT_CHANGE_TYPE_CONTENT_DESCRIPTION){
            Log.d("JKS","Event CONTENT_CHANGE_TYPE_CONTENT_DESCRIPTION " +event.getClassName().toString());
        }
        if(event.getEventType() == AccessibilityEvent.TYPE_ASSIST_READING_CONTEXT){
            Log.d("JKS","Event  TYPE_ASSIST_READING_CONTEXT " +event.getClassName().toString());
        }
        if(event.getEventType() == AccessibilityEvent.TYPE_VIEW_ACCESSIBILITY_FOCUS_CLEARED){
            Log.d("JKS","Event TYPE_VIEW_ACCESSIBILITY_FOCUS_CLEARED " +event.getClassName().toString());
        }


        if(event.getEventType() == AccessibilityEvent.CONTENT_CHANGE_TYPE_CONTENT_DESCRIPTION){
            Log.d("JKS","Event CONTENT_CHANGE_TYPE_CONTENT_DESCRIPTION " +event.getClassName().toString());
        }







    }

    @Override
    public void onInterrupt() {
        // TODO Auto-generated method stub

    }

}