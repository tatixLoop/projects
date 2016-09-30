package com.appstory.aarppo;

import android.app.Service;
import android.content.Intent;
import android.database.Cursor;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Process;
import android.os.Message;
import android.text.format.DateFormat;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.SimpleTimeZone;
import java.util.concurrent.TimeUnit;

/**
 * Created by jithin suresh on 28-09-2016.
 */
public class AarpoCheckService extends Service {

    private Looper mServiceLooper;
    long diffTime = 20;
    Handler handler;
    int cbRemoved = 0;
    int todaysGameId = -1;
    Date todaysMatchTime;
    int todaysTeam1;
    int todaysTeam2;
    private ServiceHandler mServiceHandler;
    public Runnable updateTimer = new Runnable() {
        public void run() {
            diffTime--;
            Log.d("JKS","diff = " +diffTime);

            if(diffTime == 15)
            {
                Intent i = new Intent(AarpoCheckService.this, AarpoBlast.class);
                i.putExtra("timeLeft",diffTime);
                i.putExtra("gameId",todaysGameId);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
            }

            if(diffTime <=0 )
            {
                handler.removeCallbacks(this);
                Log.d("JKS","Runnable reached less than 0 or 0");
               //cbRemoved = 1;
               // diffTime = getNewDiffTime();
            }
            handler.postDelayed(this, 1000);
        }};


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
                    if(isPlayingToday())
                    {

                        try {
                            Date netWorkTime = ISLMatchPage.getNetworkTime();
                            //Date netWorkTime = new Date();
                            SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
                            Log.d("JKS","Today "+df.format(todaysMatchTime) + " now = "+df.format(netWorkTime));
                            long diffTime_Match = todaysMatchTime.getTime() - netWorkTime.getTime();
                            long hours = TimeUnit.MILLISECONDS.toHours(diffTime_Match)%24;
                            long days = TimeUnit.MILLISECONDS.toDays(diffTime_Match);
                        long mins = TimeUnit.MILLISECONDS.toMinutes(diffTime_Match) %60;
                        long secs = TimeUnit.MILLISECONDS.toSeconds(diffTime_Match)%60;
                        Log.d("JKS","days =" +days + "hours ="+hours +" mins = "+ mins +"seconds = "+secs);
                            Log.d("JKS", "more than one hours for the match " +diffTime_Match);
                            if(isMatchNotStarted(diffTime_Match))
                            {
                                if(days == 0 && hours == 0 && mins == 0) {
                                    if(cbRemoved == 1) {
                                        Log.d("JKS", "seconds left = " + secs);

                                        diffTime = secs;
                                        handler.postDelayed(updateTimer, 0);
                                        cbRemoved =0;
                                    }
                                }
                            }
                            else
                            {
                                Log.d("JKS","Match stared, check for aarpo sync");

                            }

                        }
                        catch(IOException ex)
                        {

                        }

                    }
                    else Log.d("JKS","Blasters not playing today");

                    Log.d("JKS","inside service handler while loop");

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
        return 120;
    }

    private boolean isMatchNotStarted(long matchDiffTime)
    {
        boolean result = false;

        if(matchDiffTime < 0)
        {
            result = true;
        }

        long hours = TimeUnit.MILLISECONDS.toHours(matchDiffTime)%24;
        long days = TimeUnit.MILLISECONDS.toDays(matchDiffTime);
        long mins = TimeUnit.MILLISECONDS.toMinutes(matchDiffTime) %60;
        long secs = TimeUnit.MILLISECONDS.toSeconds(matchDiffTime)%60;

        return result;
    }
    private boolean isPlayingToday()
    {
        boolean result = false;

        AarpoDb db = new AarpoDb();
        db.openConnection();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        Date dt = new Date();
        String todaysDate = df.format(dt);

        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);
        cal.add(Calendar.DATE, +1);
        Date dayAfter = cal.getTime();
        cal.add(Calendar.DATE, -2);
        Date dayBefore = cal.getTime();

        Log.d("JKS","Todays date = "+todaysDate +" after "+ df.format(dayAfter) + " before " +df.format(dayBefore));
        String query = "select * from tbl_schedule WHERE date_time<'"+df.format(dayAfter) + "' AND date_time>'"+df.format(dayBefore)+"' AND (team1=2 OR team2=2)" ;
        Cursor c1 = db.selectData(query);
        if(c1 != null)
        {
            while(c1.moveToNext()) {
                try {
                    todaysGameId = c1.getInt(0);
                    todaysTeam1 = c1.getInt(3);
                    todaysTeam2 = c1.getInt(4);
                    SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    todaysMatchTime = dateformat.parse(c1.getString(1));

                } catch (ParseException ex) {

                }
            }

        }
        Log.d("JKS","query ="+query +" got data="+c1.getCount());

        if(c1.getCount() > 0) result = true;
        db.closeConnection();

        return result;

    }

    private int getNextMatch()
    {
        int matchId = 0;
        AarpoDb db = new AarpoDb();
        db.openConnection();

        String query = "select * from tbl_schedule";
        Cursor c1 = db.selectData(query);
        while(c1.moveToNext())
        {

        }
        db.closeConnection();

        return  matchId;

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
