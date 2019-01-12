package com.arpo.cookery;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.TextView;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import javax.microedition.khronos.opengles.GL;

public class Splashscreen extends AppCompatActivity {
    private final int SPLASH_DISPLAY_LENGTH = 1000;


    private static final String TAG_SUCCESS = "success";
    private static final String TAG_DISH = "dishes";
    JSONParser jParser = new JSONParser();
    JSONParser jParser2 = new JSONParser();
    JSONArray dishlist = null;
    JSONArray maxCountList = null;
    static List<ListItemDishes> listOfDishesForSearch;

    int lCount = 0;

    int lastLocalId;
    int lastRemoteId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);

        // Get Version
        String versionName = "";
        try {
            versionName = getApplicationContext().getPackageManager().getPackageInfo(getApplicationContext().getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        TextView versionname = (TextView) findViewById(R.id.txt_version);
        versionname.setText("Version:"+versionName);

        // Fill in search data
        listOfDishesForSearch = new ArrayList<>();
        Globals.FullDishList = new ArrayList<>();

        //new GetDishesForSearch().execute();

        Globals.gContext = getApplicationContext();
        Globals.sqlData = new CookeryData(this);
        Globals.sqlData.openConnection();

        lastLocalId = Globals.sqlData.getLastId();
        new GetMaxCount().execute();

    }

    void print(String str)
    {
        Log.d("JKS",str);
    }

    /**
     *  Background async task to get the last ID
     */
    class GetMaxCount extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        protected String doInBackground(String... args) {
            // Building Parameters

            List<NameValuePair> params = new ArrayList<NameValuePair>();

            // getting JSON string from URL
            String apiname = "";
            apiname ="getMaxCount.php";

            JSONObject json = jParser2.makeHttpRequest(Globals.host+Globals.appdir+Globals.apipath+apiname,
                    "GET", params);

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

            //} while (b_exit == false);
            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String file_url) {
            print("JKS last remote Id is "+lastRemoteId);
            print("JKS last local Id" + lastLocalId);
            if (lastLocalId < lastRemoteId)
            {
                print("do sync");
                new GetDishesForSearch().execute();
            }
            else
            {
                Globals.sqlData.getDishList(Globals.FullDishList);

                Intent mainIntent = new Intent(Splashscreen.this, CookeryMain.class);
                Splashscreen.this.startActivity(mainIntent);
                finish();
            }
        }


    }
    /**
     * Background Async Task to Load all Dishes by making HTTP Request
     * */
    class GetDishesForSearch extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        protected int getCount()
        {
            int nCount = 1600;
            return nCount;
        }
        /**
         * getting All dishlist from url
         * */
        protected String doInBackground(String... args) {
            // Building Parameters


            final int total = getCount();



                List<NameValuePair> params = new ArrayList<NameValuePair>();

                // getting JSON string from URL
                String apiname = "";
                apiname ="getAllDishes.php";
                params.add(new BasicNameValuePair("lastid", lastLocalId+ ""));
                //params.add(new BasicNameValuePair("limit", (lCount * 40 ) + ""));
                lCount++;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        int percent;
                        TextView txtLoading = findViewById(R.id.txt_loading);
                        Typeface typeface = Typeface.createFromAsset(getApplicationContext().getAssets(),
                                String.format(Locale.US, "fonts/%s", "font.ttf"));
                        txtLoading.setTypeface(typeface);
                        percent = lCount * 40 * 100 / total ;

                        txtLoading.setText("Loading Data    "+percent+ " %");
                    }
                });
                JSONObject json = jParser.makeHttpRequest(Globals.host+Globals.appdir+Globals.apipath+apiname,
                        "GET", params);

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
                                        c.getString("author")
                                );
                                listOfDishesForSearch.add(dish);
                                Globals.FullDishList.add(dish);

                                if(isFavorite(c.getInt("id")))
                                {
                                    dish.setFav(true);
                                }
                                else
                                {
                                    dish.setFav(false);
                                }
                            }
                            Collections.shuffle(listOfDishesForSearch);
                        } else {
                            print("No dishes found");
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

            //} while (b_exit == false);
            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String file_url) {
            // dismiss the dialog after getting all dishlist
            //pDialog.dismiss();

            Globals.sqlData.syncDB(Globals.FullDishList);

            Intent mainIntent = new Intent(Splashscreen.this, CookeryMain.class);
            //mainIntent.putExtra("list",(Serializable)listOfDishesForSearch);
            Splashscreen.this.startActivity(mainIntent);


            TextView txtLoading = findViewById(R.id.txt_loading);
            txtLoading.setText("Loading Data    "+100+ " %");
            print("Size of Items SPLASH SCREEN : "+Globals.FullDishList.size());
            finish();
        }
        boolean isFavorite(int id)
        {
            String sharedPrefKey = "FAV_PREF"+id;
            SharedPreferences preferences = getSharedPreferences(sharedPrefKey, Context.MODE_PRIVATE);
            int value = preferences.getInt(sharedPrefKey, -1);

            if( value == 1 )
            {
                return true;
            }
            else
            {
                return false;
            }
        }
    }

    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub

        super.onBackPressed();
        finish();

    }
}