package com.appsecurity.tatix.appsecurity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MainActivityLockScreen extends AppCompatActivity {

    public boolean appSecurity;

    public static Context mContext;
    public static Activity mActivity;
    static String[] mappName;
    static String[] mPackageName;
    static String[] lockedApps;
    static int []isLocked;
    static long [] appCheckTime;
    static int lockAppIndex;
    static Integer[] imageId;
    static Integer[] lockStatus;
    static Drawable[] drwArray;
    static int appCount = 0;
    static SQLiteDatabase mdb;
    static ListView appList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activity_lock_screen);
        setTitle("Tatix App Security");

        /* get the status from database TODO*/
        appSecurity = false;
        /* adding button listners */
        Button btnLockSel = (Button) findViewById(R.id.btn_selectLock);
        Button btnappsel = (Button) findViewById(R.id.btn_selectApps);
        Button btntoggle = (Button) findViewById(R.id.btn_securityToggle);

        if(appCount == 0) {
            try{

                mdb = openOrCreateDatabase("tileLockDB", Context.MODE_PRIVATE, null);
                mdb.execSQL("CREATE TABLE IF NOT EXISTS tileLockApps(package VARCHAR);");
                Cursor c=mdb.rawQuery("SELECT * FROM tileLockApps", null);
                lockAppIndex = 0;
                int size = 0;
                if(c.getCount() == 0)
                    size = 100;
                else
                    size = c.getCount() * 50;

                lockedApps = new String[size];
                isLocked  = new int[size];
                appCheckTime = new long[size];

                if(c.getCount()==0)
                {
                    Log.d("JKS", "No apps are marked for locking");
                }
                else {
                    Log.d("JKS", "got " + c.getCount() + " entries in tileLockDatabase");
                    while (c.moveToNext()) {
                        lockedApps[lockAppIndex] = c.getString(0);
                        isLocked[lockAppIndex] = 0;
                        appCheckTime[lockAppIndex] = System.currentTimeMillis();
                        lockAppIndex++;
                    }
                }

            }
            catch (Exception ex) {
                Log.d("JKS ","Caught exception in sql code");
            }

            final PackageManager pm = getPackageManager();

            //get a list of installed apps.
            List<ApplicationInfo> packages = pm.getInstalledApplications(PackageManager.GET_META_DATA);
            int appReadyToLock = 0;
            for (ApplicationInfo packageInfo : packages) {
                if ((packageInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0)
                    appReadyToLock++;
            }

            mappName     = new String[appReadyToLock];
            imageId      = new Integer[appReadyToLock];
            lockStatus   = new Integer[appReadyToLock];
            drwArray     = new Drawable[appReadyToLock];
            appCount     = 0;
            mPackageName = new String[appReadyToLock];

            for (ApplicationInfo packageInfo : packages) {
                // Log.d("JKS", "Installed package :" + packageInfo.packageName);
                // Log.d("JKS", "Source dir : " + packageInfo.sourceDir);
                // Log.d("JKS", "Launch Activity :" + pm.getLaunchIntentForPackage(packageInfo.packageName));
                if ((packageInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0)
                {
                    Drawable icon          = pm.getApplicationIcon(packageInfo);
                    mappName[appCount]     = pm.getApplicationLabel(packageInfo).toString();
                    mPackageName[appCount] = packageInfo.packageName;
                    imageId[appCount]      = appCount;
                    lockStatus[appCount]   = 0;
                    drwArray[appCount]     = icon;

                    for (int i = 0; i < lockAppIndex; i++) {

                        if (lockedApps[i].equals(packageInfo.packageName)) {
                            lockStatus[appCount] = 1;
                            break;
                        }
                    }

                    appCount++;
                }
            }
            Log.d("JKS","total list item count = "+appCount);


        }

        btntoggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v("JKS", "Onclick enable or disabled lock security");
                ImageView lockStatImage = (ImageView) findViewById(R.id.img_status);
                Button btnLockSel = (Button) findViewById(R.id.btn_securityToggle);
                TextView txtAppStat = (TextView) findViewById(R.id.txtView_secureStat);
                if (appSecurity == false) {
                    appSecurity = true;

                    btnLockSel.setText("Disable app security");
                    lockStatImage.setImageResource(R.drawable.applocksecure);
                    txtAppStat.setText("Your apps are secure");
                    /* TODO update database */
                } else {
                    btnLockSel.setText("Enable App Security");
                    txtAppStat.setText("Your Apps are not secure");
                    appSecurity = false;
                    lockStatImage.setImageResource(R.drawable.applocknotsecure);
                    /* TODO update database */
                }
            }
        });

        btntoggle.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        Button view = (Button) v;
                        view.getBackground().setColorFilter(0x77000000, PorterDuff.Mode.SRC_ATOP);
                        v.invalidate();
                        break;
                    }
                    case MotionEvent.ACTION_UP:
                        // Your action here on button click
                        ImageView lockStatImage = (ImageView) findViewById(R.id.img_status);
                        Button btnLockSel = (Button) findViewById(R.id.btn_securityToggle);
                        TextView txtAppStat = (TextView) findViewById(R.id.txtView_secureStat);
                        if (appSecurity == false) {
                            appSecurity = true;

                            btnLockSel.setText("Disable app security");
                            lockStatImage.setImageResource(R.drawable.applocksecure);
                            txtAppStat.setText("Your apps are secure");
                    /* TODO update database */
                        } else {
                            btnLockSel.setText("Enable App Security");
                            txtAppStat.setText("Your Apps are not secure");
                            appSecurity = false;
                            lockStatImage.setImageResource(R.drawable.applocknotsecure);
                    /* TODO update database */
                        }
                    case MotionEvent.ACTION_CANCEL: {
                        Button view = (Button) v;
                        view.getBackground().clearColorFilter();
                        view.invalidate();
                        break;
                    }
                }
                return true;
            }
        });
        btnappsel.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        Button view = (Button) v;
                        view.getBackground().setColorFilter(0x77000000, PorterDuff.Mode.SRC_ATOP);
                        v.invalidate();
                        break;
                    }
                    case MotionEvent.ACTION_UP:
                        // Your action here on button click
                        Toast.makeText(getApplicationContext(), "App selection is yet to implement",
                                Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(MainActivityLockScreen.this, AppLisintingActivity.class);

                        startActivity(intent);
                    case MotionEvent.ACTION_CANCEL: {
                        Button view = (Button) v;
                        view.getBackground().clearColorFilter();
                        view.invalidate();
                        break;
                    }
                }
                return true;
            }
        });

        btnLockSel.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        Button view = (Button) v;
                        view.getBackground().setColorFilter(0x77000000, PorterDuff.Mode.SRC_ATOP);
                        v.invalidate();
                        break;
                    }
                    case MotionEvent.ACTION_UP:
                        // Your action here on button click
                        Toast.makeText(getApplicationContext(), "lock selection is yet to implement",
                                Toast.LENGTH_SHORT).show();
                    case MotionEvent.ACTION_CANCEL: {
                        Button view = (Button) v;
                        view.getBackground().clearColorFilter();
                        view.invalidate();
                        break;
                    }
                }
                return true;
            }
        });
    }

    public static void loadAllApplications(PackageManager pm,SQLiteDatabase db, Cursor c)
    {

        //final PackageManager pm = getPackageManager();
        mdb = db;

        if(appCount==0) {

            try {


                lockAppIndex = 0;
                int size = 0;
                if (c.getCount() == 0)
                    size = 100;
                else
                    size = c.getCount() * 50;

                lockedApps = new String[size];
                isLocked = new int[size];
                appCheckTime = new long[size];

                if (c.getCount() == 0) {
                    Log.d("JKS", "No apps are marked for locking");
                } else {
                    Log.d("JKS", "got " + c.getCount() + " entries in tileLockDatabase");
                    while (c.moveToNext()) {
                        lockedApps[lockAppIndex] = c.getString(0);
                        isLocked[lockAppIndex] = 0;
                        appCheckTime[lockAppIndex] = System.currentTimeMillis();
                        lockAppIndex++;
                    }
                }

            } catch (Exception ex) {
                Log.d("JKS ", "Caught exception in sql code");
            }


            //get a list of installed apps.
            List<ApplicationInfo> packages = pm.getInstalledApplications(PackageManager.GET_META_DATA);
            int appReadyToLock = 0;
            for (ApplicationInfo packageInfo : packages) {
                if ((packageInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0)
                    appReadyToLock++;
            }

            mappName = new String[appReadyToLock];
            imageId = new Integer[appReadyToLock];
            lockStatus = new Integer[appReadyToLock];
            drwArray = new Drawable[appReadyToLock];
            appCount = 0;
            mPackageName = new String[appReadyToLock];

            for (ApplicationInfo packageInfo : packages) {

                if ((packageInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {
                    Drawable icon = pm.getApplicationIcon(packageInfo);
                    mappName[appCount] = pm.getApplicationLabel(packageInfo).toString();
                    mPackageName[appCount] = packageInfo.packageName;
                    imageId[appCount] = appCount;
                    lockStatus[appCount] = 0;
                    drwArray[appCount] = icon;

                    for (int i = 0; i < lockAppIndex; i++) {

                        if (lockedApps[i].equals(packageInfo.packageName)) {
                            lockStatus[appCount] = 1;
                            break;
                        }
                    }

                    appCount++;
                }
            }
        }
        Log.d("JKS", "total list item count = " + appCount);
    }
    public static boolean isAppLocked(String appName)
    {
        for(int i = 0;i<lockAppIndex;i++) {
            if(lockedApps[i].equals(appName))
            {
                return true;
            }
        }

        return false;
    }

    public static boolean addAppToLockedList(String appName)
    {
        lockedApps[lockAppIndex] = appName;
        lockAppIndex++;
        return true;
    }

    public static boolean removeAppFromLockedList(String appName)
    {
        for(int i = 0;i<lockAppIndex;i++) {
            if(lockedApps[i].equals(appName))
            {
                lockedApps[i] = "REMOVED APPLICATION";
                return true;
            }
        }
        return true;

    }
}
