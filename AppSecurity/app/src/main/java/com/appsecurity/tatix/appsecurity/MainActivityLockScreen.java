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
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
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

        mContext = this;
        mActivity = MainActivityLockScreen.this;

        setContentView(R.layout.activity_main_activity_lock_screen);
        setTitle("Tatix App Security");

        /* adding button listners */
        Button btnLockSel = (Button) findViewById(R.id.btn_selectLock);
        Button btnappsel = (Button) findViewById(R.id.btn_selectApps);
        Button btntoggle = (Button) findViewById(R.id.btn_securityToggle);

        if(appCount == 0) {
            try{

                mdb = openOrCreateDatabase("tileLockDB", Context.MODE_PRIVATE, null);
                mdb.execSQL("CREATE TABLE IF NOT EXISTS tileLockApps(package VARCHAR);");
                mdb.execSQL("CREATE TABLE IF NOT EXISTS lockStatus(status int);");
                mdb.execSQL("CREATE TABLE IF NOT EXISTS lockType(lockId int,enabled int);");
                mdb.execSQL("CREATE TABLE IF NOT EXISTS unlockKey(lockId int,key int);");
                mdb.execSQL("INSERT INTO lockType VALUES('0','0')");
                Cursor c2=mdb.rawQuery("SELECT * FROM lockStatus", null);
                if(c2.getCount() == 0) {
                    mdb.execSQL("INSERT INTO lockStatus VALUES('0')");
                    Log.d("JKS","Inserting first element");
                }
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
            Log.d("JKS", "total list item count = " + appCount);


        }


        ImageView lockStatImage = (ImageView) findViewById(R.id.img_status);
        TextView txtAppStat = (TextView) findViewById(R.id.txtView_secureStat);
        if (getSecurityStatus() == true) {

            btntoggle.setText("Disable app security");
            lockStatImage.setImageResource(R.drawable.applocksecure);
            txtAppStat.setText("Your apps are secure");
        } else {
            btntoggle.setText("Enable App Security");
            txtAppStat.setText("Your Apps are not secure");
            lockStatImage.setImageResource(R.drawable.applocknotsecure);
        }


        btntoggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v("JKS", "Onclick enable or disabled lock security");
                ImageView lockStatImage = (ImageView) findViewById(R.id.img_status);
                Button btnLockSel = (Button) findViewById(R.id.btn_securityToggle);
                TextView txtAppStat = (TextView) findViewById(R.id.txtView_secureStat);
                if (getSecurityStatus() == false) {
                    setSecurityStatus(true);

                    btnLockSel.setText("Disable app security");
                    lockStatImage.setImageResource(R.drawable.applocksecure);
                    txtAppStat.setText("Your apps are secure");
                } else {
                    btnLockSel.setText("Enable App Security");
                    txtAppStat.setText("Your Apps are not secure");
                    setSecurityStatus(false);
                    lockStatImage.setImageResource(R.drawable.applocknotsecure);
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

                        if (getSecurityStatus() == false) {
                            setSecurityStatus(true);

                            //GIVE PERMISSIONS

                            //Check if permission enabled for usage stats
                            if (UStats.getUsageStatsList(mContext).isEmpty()){
                                Intent intent = new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS);
                                startActivity(intent);
                            }


                            if(isAccessibilityEnabled())
                            {
                                Log.d("JKS","Accessibility enabled for LockEngine");
                            }else {
                                Intent intent = new Intent(android.provider.Settings.ACTION_ACCESSIBILITY_SETTINGS);
                                startActivityForResult(intent, 0);
                            }
                            btnLockSel.setText("Disable app security");
                            lockStatImage.setImageResource(R.drawable.applocksecure);
                            txtAppStat.setText("Your apps are secure");
                        } else {
                            btnLockSel.setText("Enable App Security");
                            txtAppStat.setText("Your Apps are not secure");
                            setSecurityStatus(false);
                            lockStatImage.setImageResource(R.drawable.applocknotsecure);
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
                        Intent intent = new Intent(MainActivityLockScreen.this, LockSelectionActivity.class);
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
    }

    public static boolean getSecurityStatus()
    {
        Cursor c=mdb.rawQuery("SELECT * FROM lockStatus", null);

        if(c.getCount() == 0)
        {
            return false;
        }
        else {
            while (c.moveToNext()) {
                if(c.getInt(0) == 1)
                {
                    return true;
                }
                else if(c.getInt(0) == 0)
                {
                    return false;
                }
            }
        }

        return false;
    }

    public boolean setSecurityStatus(boolean value)
    {
        if(value == true)
        {
            mdb.execSQL("UPDATE lockStatus SET status=1;");
        }
        if(value == false)
        {
            mdb.execSQL("UPDATE lockStatus SET status=0;");
        }
        return true;
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

    public static boolean checkLockedList(String appName)
    {
        // get the list of application to be locked
        //return true if the appName is present in the list
        Log.d("JKS","Check if " + appName + " has to be locked");

        for(int i = 0; i < lockAppIndex; i++)
        {

            if(appName.equals((lockedApps[i])))
            {

                long timeDiff = (System.currentTimeMillis()  - appCheckTime[i]);

                appCheckTime[i] = System.currentTimeMillis();

                if(timeDiff < 15000) {

                    Log.d("JKS","Recently "+ appName +" app was running ("+timeDiff +")");
                    return false;
                }

                if(isLocked[i] == 0) {

                    Log.d("JKS",appName + " has to be locked");
                    return true;
                }
                else {
                    Log.d("JKS",appName +" is already locked so not locking");
                    return false;
                }
            }
        }



        return false;
    }
    public static void launchLockScreen(String appName) {
        Log.d("JKS", "Launch lock screen from here");
        Intent myIntent = new Intent(mContext, FullscreenLockActivity.class);
        myIntent.putExtra("key",appName); //Optional parameters
        myIntent.setFlags(Intent.FLAG_ACTIVITY_NO_USER_ACTION |
                        Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS |
                        Intent.FLAG_ACTIVITY_NO_HISTORY |
                        Intent.FLAG_ACTIVITY_NEW_TASK
        );
        mContext.startActivity(myIntent);
        //lockScreen();

    }
    public static void setAppCtx(Context appCtx)
    {
        mContext = appCtx;
    }

    public static  void unlockApp(String appName)
    {

        for(int i = 0; i < lockAppIndex; i++)
        {

            if(appName.equals((lockedApps[i])))
            {
                isLocked[i] = 0;
                appCheckTime[i]= System.currentTimeMillis();
                Log.d("JKS","Unlock "+appName);
                return;
            }
        }
    }

    public static  void lockApp(String appName)
    {

        for(int i = 0; i < lockAppIndex; i++)
        {

            if(appName.equals((lockedApps[i])))
            {
                isLocked[i] = 1;
                Log.d("JKS","lock "+appName);
                return;
            }
        }
    }

    public static  boolean getAppStatus(String appName)
    {

        for(int i = 0; i < lockAppIndex; i++)
        {

            if(appName.equals((lockedApps[i])))
            {
                if(isLocked[i] == 0)
                    return false;
                else
                    return true;
            }
        }
        return false;

    }
    public boolean isAccessibilityEnabled(){
        int accessibilityEnabled = 0;
        final String LIGHTFLOW_ACCESSIBILITY_SERVICE = "com.jaapps.lockengine/com.jaapps.lockengine.MyAccessibilityService";
        boolean accessibilityFound = false;
        try {
            accessibilityEnabled = Settings.Secure.getInt(this.getContentResolver(),android.provider.Settings.Secure.ACCESSIBILITY_ENABLED);

        } catch (Settings.SettingNotFoundException e) {
            Log.d("JKS", "Error finding setting, default accessibility to not found: " + e.getMessage());
        }

        TextUtils.SimpleStringSplitter mStringColonSplitter = new TextUtils.SimpleStringSplitter(':');

        if (accessibilityEnabled==1){

            String settingValue = Settings.Secure.getString(getContentResolver(), Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES);

            if (settingValue != null) {
                TextUtils.SimpleStringSplitter splitter = mStringColonSplitter;
                splitter.setString(settingValue);
                while (splitter.hasNext()) {
                    String accessabilityService = splitter.next();
                    Log.d("JKS", "Setting: " + accessabilityService);
                    if (accessabilityService.equalsIgnoreCase(LIGHTFLOW_ACCESSIBILITY_SERVICE)){
                        return true;
                    }
                }
            }
        }
        return accessibilityFound;
    }
}
