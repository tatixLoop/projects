package com.jaapps.ricerecipes;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.ads.MobileAds;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class Splashscreen extends AppCompatActivity {

    private static final String TAG_SUCCESS = "success";
    private static final String TAG_DISH = "dishes";
    JSONParser jParser = new JSONParser();
    JSONParser jParser2 = new JSONParser();
    JSONArray dishlist = null;
    JSONArray maxCountList = null;
    JSONArray flagList = null;


    int lastLocalId;
    int lastRemoteId;
    int updateFlag = -1;
    int updateTag;
    boolean doUpdate = false;
    ProgressBar progressBar;



    List<ListItemDishes> DishList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);


        // initialize the AdMob app
        MobileAds.initialize(this, getString(R.string.admob_app_id));

        updateFlag = -1;
        //ProgressBar
        progressBar = findViewById(R.id.splashprogressBar);


        // Get Version
        String versionName = "";
        try {
            versionName = getApplicationContext().getPackageManager().getPackageInfo(getApplicationContext().getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        TextView versionname = findViewById(R.id.txt_version);
        String versionText = "Version:"+versionName;
        versionname.setText(versionText);

        // Fill in search data
        DishList = new ArrayList<>();

        //new GetDishesForSearch().execute();

        Globals.gContext = getApplicationContext();
        Globals.sqlData = new CookeryData(this);
        Globals.sqlData.openConnection();

        lastLocalId = Globals.sqlData.getLastId();

        Typeface typeface = Typeface.createFromAsset(getApplicationContext().getAssets(),
                String.format(Locale.US, "fonts/%s", "font.ttf"));

        ((TextView) findViewById(R.id.txt_launchstat)).setTypeface(typeface);

        String updateCheckString = "Checking for updates";
        ((TextView) findViewById(R.id.txt_launchstat)).setText(updateCheckString);

        GetMaxCountThread threadGetCount = new GetMaxCountThread();
        new Thread(threadGetCount).start();

    }


    class GetMaxCountThread implements Runnable
    {

        private int getUpdateTag()
        {
            // check if image is present in shared preference cache
            String sharedPrefKey = "SHCache_updateTag";
            SharedPreferences preferences = getSharedPreferences(sharedPrefKey, Context.MODE_PRIVATE);
            return preferences.getInt(sharedPrefKey, -1);
        }

        private void setUpdateTag(int tag)
        {
            // check if image is present in shared preference cache
            String sharedPrefKey = "SHCache_updateTag";
            SharedPreferences preferences = getSharedPreferences(sharedPrefKey, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putInt(sharedPrefKey, tag);
            editor.apply();
        }

        public void run()
        {
            //pre execute
            //execute

            // getting JSON string from URL
            String apiname;
            apiname ="getUpdate.php";

            JSONObject json = jParser2.makeHttpRequest(Globals.host+Globals.appdir+Globals.apipath+apiname,
                    "GET", "");

            // Check your log cat for JSON reponse
            if(json != null) {
                //print( json.toString());
                try {
                    // Checking for SUCCESS TAG
                    int success = json.getInt(TAG_SUCCESS);

                    if (success == 1) {
                        maxCountList = json.getJSONArray("maxcount");

                        for (int i = 0; i < maxCountList.length(); i++) {

                            JSONObject c = maxCountList.getJSONObject(i);
                            lastRemoteId = c.getInt("maxid");
                        }

                        flagList = json.getJSONArray("flag");

                        for (int i = 0; i < flagList.length(); i++) {

                            JSONObject c = flagList.getJSONObject(i);
                            updateFlag = c.getInt("flag");
                        }
                    } else {
                        print("No maxId found");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
            else
            {
                print("Error in making http request");
            }

            //post execute
            print("JKS last remote Id is "+lastRemoteId);
            print("JKS last local Id" + lastLocalId);
            doUpdate = false;
            updateTag = updateFlag >> 8;

            if ( updateTag > getUpdateTag() ) {

                if ((updateFlag & 0x4) != 0) {
                    print("UPDATE THE IMAGES");

                    for (int i = 1; i < 26; i++) {
                        String sharedPrefKey = "SHCache_" + Globals.FETCHTYPE_DISHCATAGORY + "_" + i;
                        SharedPreferences preferences = getSharedPreferences(sharedPrefKey, Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString(sharedPrefKey, "defaultValue");
                        editor.apply();
                    }
                    setUpdateTag(updateTag);

                    if (lastLocalId < lastRemoteId) {
                        print("do sync");
                        fetchData();
                    }
                    else {
                        //Globals.sqlData.getDishList(Globals.FullDishList);

                        Intent mainIntent = new Intent(Splashscreen.this, CookeryMain.class);
                        mainIntent.putExtra("type", Globals.g_type);
                        Splashscreen.this.startActivity(mainIntent);
                        finish();
                    }
                } else if ((updateFlag & 0x2) != 0) {
                    doUpdate = true;
                    print("UPDATE NEW DISHES ");
                    print("do sync");

                    //Globals.sqlData.getDishList(Globals.FullDishList);
                    fetchData();

                } else if ((updateFlag & 0x1) != 0) {
                    doUpdate = true;
                    print("UPDATE ENTIRE DATABASE");
                    lastLocalId = 0;

                    Globals.sqlData.clearDB();
                    fetchData();
                }
            }
            else
            {
                if (lastLocalId < lastRemoteId) {
                    print("do sync");
                    fetchData();
                }
                else {
                    print("update tag is same ; no update");
                    //Globals.sqlData.getDishList(Globals.FullDishList);

                    Intent mainIntent = new Intent(Splashscreen.this, CookeryMain.class);
                    mainIntent.putExtra("type", Globals.g_type);
                    Splashscreen.this.startActivity(mainIntent);
                    finish();
                }
            }

        }



        private void fetchData()
        {
            boolean errorSet = false;
            int subTypes[];
            int subTypeIndex = 0;
            //pre execute

            subTypes = new int[50];
            //execute
            runOnUiThread(new Runnable() {
                public void run() {
                    String updateText = "Update in progress. Please wait...";
                    ((TextView) findViewById(R.id.txt_launchstat)).setText(updateText);
                    //finish();maxCountList
                }
            });

            long startTime = Calendar.getInstance().getTimeInMillis();

            // getting JSON string from URL
            String apiname;
            apiname ="getDishesByType.php";
            String param = "type="+Globals.g_type+"&lastid="+lastLocalId;

            JSONObject json = jParser.makeHttpRequest(Globals.host+Globals.appdir+Globals.apipath+apiname,
                    "GET", param);

            // Check your log cat for JSON reponse
            if(json != null) {
                //print( json.toString());
                try {
                    // Checking for SUCCESS TAG
                    int success = json.getInt(TAG_SUCCESS);

                    if (success == 1) {
                        dishlist = json.getJSONArray(TAG_DISH);

                        for (int i = 0; i < dishlist.length(); i++) {
                            JSONObject c = dishlist.getJSONObject(i);

                            int subT = c.getInt("subtype");
                            boolean present = false;
                            for (int q = 0 ; q <subTypeIndex; q++)
                            {
                                if ( subTypes[q] == subT)
                                {
                                    present = true;
                                    break;
                                }
                            }

                            if ( present == false)
                            {
                                subTypes[subTypeIndex] = subT;
                                subTypeIndex++;
                            }
                            //print("Fetching dish "+c.getInt("id"));
                            ListItemDishes dish = new ListItemDishes(
                                    c.getInt("id"),
                                    c.getInt("type"),
                                    c.getString("dishname"),
                                    c.getString("img_path"),
                                    c.getInt("cooktimeinsec"),
                                    c.getInt("serves"),
                                    c.getInt("calory"),
                                    c.getInt("rating"),
                                    c.getInt("numRating"),
                                    c.getString("author"),
                                    c.getString("cuktime"),
                                    subT
                            );
                            DishList.add(dish);

                        }
                    } else {
                        print("No dishes found");
                        errorSet = true;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
                long endTime = Calendar.getInstance().getTimeInMillis();
                print("JKS took "+(endTime - startTime)+" ms to fetch data");
            }
            else
            {
                print("Error in making http request");
            }

            //post execute

            if (!errorSet) {
                Globals.sqlData.syncDB(DishList);

            }
            if (doUpdate){
                setUpdateTag(updateTag);
            }
            Intent mainIntent = new Intent(Splashscreen.this, CookeryMain.class);
            mainIntent.putExtra("loadType", 1);
            mainIntent.putExtra("numCata", subTypeIndex);
            mainIntent.putExtra("type", Globals.g_type);

            Splashscreen.this.startActivity(mainIntent);

            print("Size of Items fetched : "+DishList.size());
            finish();
        }
    }

    void print(String str)
    {
        Log.d("JKS",str);
    }

    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub

        super.onBackPressed();
        finish();

    }
}
