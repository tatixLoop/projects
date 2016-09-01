package com.example.sony.samplenotificationservice;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.widget.Toast;

import java.util.Timer;

public class NotificationService extends Service {
    NotificationManager mManager;
    Notification myNotication;
    Handler handler=new Handler();
    Timer t;
    public NotificationService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
//        t=new Timer();
//        t.scheduleAtFixedRate(new MyTimer(),0,100);

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        //code for notification
        Toast.makeText(NotificationService.this, "from service", Toast.LENGTH_SHORT).show();

        mManager = (NotificationManager) this.getApplicationContext().getSystemService(this.getApplicationContext().NOTIFICATION_SERVICE);
        Intent intent1 = new Intent(this.getApplicationContext(),AddMessageActivity.class);

        intent1.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP| Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingNotificationIntent = PendingIntent.getActivity( this.getApplicationContext(),0, intent1,PendingIntent.FLAG_UPDATE_CURRENT);
        Notification.Builder builder = new Notification.Builder(this);

        builder.setAutoCancel(true);
        builder.setTicker("Push up reminder ").setContentTitle("FitnessOn");
        builder.setContentText("ITS U R WORK OUT TIME");
        builder.setSmallIcon(R.drawable.bg);
        builder.setContentIntent(pendingNotificationIntent);
        // builder.setOngoing(false);
        // builder.setSubText("This is subtext...");   //API level 16
        // builder.setNumber(100);
        builder.build();

        myNotication = builder.build();
        mManager.notify(11, myNotication);








        return super.onStartCommand(intent, flags, startId);
    }

    //    public class MyTimer extends TimerTask{
//
//        @Override
//        public void run() {
//            handler.post(new Runnable() {
//                @Override
//                public void run() {
//                    DBConnection db=new DBConnection(NotificationService.this);
//                    db.openConnection();
//                    String s="select Time, Message from tb_time";
//                    Cursor c=db.selectData(s);
//                    if(c!=null){
//                        if(c.moveToNext()){
//
//                            Toast.makeText(NotificationService.this,"hi "+c.getString(0),Toast.LENGTH_SHORT).show();
//                        }
//                    }
////                    Toast.makeText(NotificationService.this,"hi",Toast.LENGTH_SHORT).show();
//
//             }
//            });
//        }
//    }
}
