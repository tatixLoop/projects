package com.appstory.aarppo;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Process;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by jithin suresh on 28-09-2016.
 */
public class AarpoCheckService extends Service {

    private Looper mServiceLooper;
    long diffTime = 20;
    Handler handler;
    int cbRemoved = 0;
    private ServiceHandler mServiceHandler;
    public Runnable updateTimer = new Runnable() {
        public void run() {



            diffTime--;
            Log.d("JKS","diff = " +diffTime);

            if(diffTime == 15)
            {
                Intent i = new Intent(AarpoCheckService.this, AarpoBlast.class);
                i.putExtra("timeLeft",diffTime);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
            }

            if(diffTime <=0 )
            {
               // handler.removeCallbacks(this);
                Log.d("JKS","Runnable reached less than 0 or 0");
               // cbRemoved = 1;
                diffTime = getNewDiffTime();
            }



            handler.postDelayed(this, 1000);
        }};

    private  int targetTime = 20;
    // Handler that receives messages from the thread
    private final class ServiceHandler extends Handler {
        public ServiceHandler(Looper looper) {
            super(looper);
        }
        @Override
        public void handleMessage(Message msg) {
            // Normally we would do some work here, like download a file.
            // For our sample, we just sleep for 5 seconds.
            Log.d("JKS","inside service handler");
            while(true) {
                try {
                    Log.d("JKS","inside service handler while loop");
                    if(cbRemoved == 1) {
                        handler.postDelayed(updateTimer, 0);

                        diffTime = getNewDiffTime();
                        cbRemoved =0;
                    }
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    // Restore interrupt status.
                    Thread.currentThread().interrupt();
                }
                // Stop the service using the startId, so that we don't stop
                // the service in the middle of handling another job
            }//stopSelf(msg.arg1);

        }
    }

    private  int getNewDiffTime()
    {
        return 25;
    }

    @Override
    public void onCreate() {
        // Start up the thread running the service.  Note that we create a
        // separate thread because the service normally runs in the process's
        // main thread, which we don't want to block.  We also make it
        // background priority so CPU-intensive work will not disrupt our UI.
        HandlerThread thread = new HandlerThread("ServiceStartArguments",
                Process.THREAD_PRIORITY_BACKGROUND);
        thread.start();
        Log.d("JKS","onCreate serviice");

        // Get the HandlerThread's Looper and use it for our Handler

        handler = new Handler();
        cbRemoved = 1;
        mServiceLooper = thread.getLooper();
        mServiceHandler = new ServiceHandler(mServiceLooper);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this, "service starting", Toast.LENGTH_SHORT).show();

        Log.d("JKS","Start Service");
        // For each start request, send a message to start a job and deliver the
        // start ID so we know which request we're stopping when we finish the job
        Message msg = mServiceHandler.obtainMessage();
        msg.arg1 = startId;
        mServiceHandler.sendMessage(msg);

        // If we get killed, after returning from here, restart
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // We don't provide binding, so return null
        return null;
    }

    @Override
    public void onDestroy() {
        Log.d("JKS","Service stopned");
        Toast.makeText(this, "service done", Toast.LENGTH_SHORT).show();
    }
}
