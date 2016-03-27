package com.jaapps.lockengine;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Looper;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
   
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.AdapterView;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import android.widget.RelativeLayout.LayoutParams;



public class MainActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */

    private static Context mContext;
    private  static Activity mActivity;
    static String[] mappName;
    static String[] mPackageName;
    static String[] lockedApps;
    static int []isLocked;
    static long [] appCheckTime;
    static int lockAppIndex;
    static Integer[] imageId;
    static Integer[] lockStatus;
    static Drawable[] drwArray;
    static int appCount;
    static SQLiteDatabase db;

    static ListView appList;
    public static void launchLockScreen(String appName) {
        Log.d("JKS", "Launch lock screen from here");
        Intent myIntent = new Intent(mContext, FullscreenLockActivity.class);
        myIntent.putExtra("key",appName); //Optional parameters
        myIntent.setFlags(Intent.FLAG_ACTIVITY_NO_USER_ACTION |
                Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS |
                Intent.FLAG_ACTIVITY_NO_HISTORY );
        mContext.startActivity(myIntent);
        //lockScreen();

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

    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = this;
        mActivity = MainActivity.this;

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);


        //Check if permission enabled for usage stats
        if (UStats.getUsageStatsList(this).isEmpty()){
            Intent intent = new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS);
            startActivity(intent);
        }

        try{

            db = openOrCreateDatabase("tileLockDB", Context.MODE_PRIVATE, null);
            db.execSQL("CREATE TABLE IF NOT EXISTS tileLockApps(package VARCHAR);");
            Cursor c=db.rawQuery("SELECT * FROM tileLockApps", null);
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

        if(isAccessibilityEnabled())
        {
            Log.d("JKS","Accessibility enabled for LockEngine");
        }else {
            Intent intent = new Intent(android.provider.Settings.ACTION_ACCESSIBILITY_SETTINGS);
            startActivityForResult(intent, 0);
        }

        /* thread to update the context to accessibility class */
        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    boolean run = true;
                    do {
                        if(isAccessibilityEnabled()) {
                            MyAccessibilityService.setMyContext(MainActivity.this);
                            run = false;
                            MyAccessibilityService.startLockingThread();
                        }
                        sleep(1000);

                    }while(run);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (RuntimeException r){
                    r.printStackTrace();
                }
                Log.d("JKS","Thread exits");
            }
        };

        thread.start();

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

    public static void loadAppList() {

        customList adapter = new
                customList(mActivity, mappName, imageId,drwArray,lockStatus,appCount);

        appList.setAdapter(adapter);
        appList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                TextView txV = (TextView)view.findViewById(R.id.txt);
                ImageView img_lock = (ImageView)view.findViewById(R.id.img_lock_state);


                if(!isAppLocked(mPackageName[+ position])) {
                    db.execSQL("INSERT INTO tileLockApps VALUES('" + mPackageName[+position] + "')");
                    img_lock.setImageResource(R.drawable.lock_state);
                    addAppToLockedList( mPackageName[+position]);
                    lockStatus[+position] = 1;
                }
                else{
                    db.execSQL("DELETE FROM tileLockApps WHERE package ='"+ mPackageName[+position]+"'");
                    img_lock.setImageResource(R.drawable.unlock_state);
                    removeAppFromLockedList( mPackageName[+position]);
                    lockStatus[+position] = 0;
                }

            }
        });
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

    public static boolean addAppToLockedList(String appName)
    {
        lockedApps[lockAppIndex] = appName;
        lockAppIndex++;
        return true;
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

    String[] getActivePackagesCompat() {
        ActivityManager am = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        final List<ActivityManager.RunningTaskInfo> taskInfo = am.getRunningTasks(1);
        final ComponentName componentName = taskInfo.get(0).topActivity;
        final String[] activePackages = new String[1];
        activePackages[0] = componentName.getPackageName();
        return activePackages;
    }

    String[] getActivePackages() {
        final Set<String> activePackages = new HashSet<String>();
        ActivityManager am = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        final List<ActivityManager.RunningAppProcessInfo> processInfos = am.getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo processInfo : processInfos) {
      //      if (processInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                activePackages.addAll(Arrays.asList(processInfo.pkgList));
        //    }
        }
        return activePackages.toArray(new String[activePackages.size()]);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    
  

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "SECTION 1";
                case 1:
                    return "SECTION 2";
                case 2:
                    return "SECTION 3";
            }
            return null;
        }
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {

            View rootView = null;

            if(getArguments().getInt(ARG_SECTION_NUMBER) == 1)
            {
                rootView = inflater.inflate(R.layout.applist, container, false);
                MainActivity.appList = (ListView)rootView.findViewById(R.id.list_tmp);

                MainActivity.loadAppList();

            }
            else if(getArguments().getInt(ARG_SECTION_NUMBER) == 2) {
                rootView = inflater.inflate(R.layout.fragment_main, container, false);
                TextView textView = (TextView) rootView.findViewById(R.id.section_label);

                textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
                textView.setText("SETTINGS");
            }

            return rootView;
        }
    }
}
