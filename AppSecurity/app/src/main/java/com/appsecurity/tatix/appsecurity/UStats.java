package com.appsecurity.tatix.appsecurity;


import android.app.usage.UsageEvents;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

/**
 * Created by jithin on 4/1/16.
 */


public class UStats {
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("M-d-yyyy HH:mm:ss");
    public static final String TAG = UStats.class.getSimpleName();
    public static long startTime = 0;
    public static int nSize= 0;
    public static String[] packages;
    public static int  allocatedSize = 0;
    @SuppressWarnings("ResourceType")
    public static void getStats(Context context){
        UsageStatsManager usm = (UsageStatsManager) context.getSystemService("usagestats");
        int interval = UsageStatsManager.INTERVAL_YEARLY;
        Calendar calendar = Calendar.getInstance();
        long endTime = calendar.getTimeInMillis();
        if(startTime == 0)
            startTime = calendar.getTimeInMillis();

        Log.d("JKS", "Range start:" + dateFormat.format(startTime) );
        Log.d("JKS", "Range end:" + dateFormat.format(endTime));

        UsageEvents uEvents = usm.queryEvents(startTime, endTime);
        while (uEvents.hasNextEvent()){
            UsageEvents.Event e = new UsageEvents.Event();
            uEvents.getNextEvent(e);

            if (e != null){
                Log.d("JKS", "Event: " + e.getPackageName() + "\t" +  e.getTimeStamp());
            }
        }
    }

    public static List<UsageStats> getUsageStatsList(Context context){
        UsageStatsManager usm = getUsageStatsManager(context);
        Calendar calendar = Calendar.getInstance();
        long endTime = calendar.getTimeInMillis();

        if(startTime == 0)
            startTime = calendar.getTimeInMillis();

        //  Log.d("JKS", "Range start:" + dateFormat.format(startTime) );
        //  Log.d("JKS", "Range end:" + dateFormat.format(endTime));

        List<UsageStats> usageStatsList = usm.queryUsageStats(UsageStatsManager.INTERVAL_DAILY,startTime,endTime);
        return usageStatsList;
    }

    public static void printUsageStats(List<UsageStats> usageStatsList){

        for (UsageStats u : usageStatsList) {
            String name = u.getPackageName();
            long timeDiff = (System.currentTimeMillis() - u.getLastTimeUsed());
            //if( )< 1000 ) {
            if((timeDiff)< 3000 ) {
                Log.d("JKS", "PACKAGE NAME = " + name +
                        " LastUsed " + u.getLastTimeUsed() +
                        " CUrrent time = " + System.currentTimeMillis() +
                        " TIme difference = "+timeDiff);
                if(MainActivityLockScreen.checkLockedList(name)  ) {
                    Log.d("JKS","lock result is true; launch lock screen");
                    MainActivityLockScreen.launchLockScreen(name);
                }
            }
            //   else
            //       Log.d("JKS","app "+name + " timediff "+timeDiff );

        }
        Log.d("JKS","       ");
        if(allocatedSize == 0) {
            allocatedSize = 1000;
            packages = new String[1000];
        }

        int itemsToAdd = 0;
        if(nSize < usageStatsList.size())
        {

            itemsToAdd = usageStatsList.size() - nSize;

            int j = nSize;



            for (UsageStats u : usageStatsList){
                String name = u.getPackageName();

                boolean listHasPackage = false;

                for(int i = 0; i< nSize; i++)
                {
                    if(name.equals(packages[i]))
                    {

                        listHasPackage = true;
                        break;
                    }
                }

                if(listHasPackage == false)
                {
                    packages[nSize] = name;
                    Log.d("JKS","NEW APP LAUNCHED IS " + name);
                    if(MainActivityLockScreen.checkLockedList(name)) {
                        Log.d("JKS","lock result is true; launch lock screen");
                        MainActivityLockScreen.launchLockScreen(name);
                    }
                    nSize++;
                }
            }
        }
        nSize = usageStatsList.size();
    }

    public static void printCurrentUsageStatus(Context context){
        printUsageStats(getUsageStatsList(context));
    }
    @SuppressWarnings("ResourceType")
    private static UsageStatsManager getUsageStatsManager(Context context){
        UsageStatsManager usm = (UsageStatsManager) context.getSystemService("usagestats");
        return usm;
    }
}
