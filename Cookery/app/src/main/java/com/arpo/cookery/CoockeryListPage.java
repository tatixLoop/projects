package com.arpo.cookery;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;


public class CoockeryListPage extends AppCompatActivity implements AdapterDishGridView.ItemListener {

    void print(String str)
    {
        Log.d("JKS",str);
    }

    private ProgressDialog pDialog;
    JSONParser jParser = new JSONParser();
    JSONArray dishlist = null;

    int gType = 0;
    int gloadType = 0;
    int gCount = 0;
    List<ListItemDishes> listOfDishes;
    AdapterDishGridView  adapterDishList;

    private static final String TAG_SUCCESS = "success";
    private static final String TAG_DISH = "dishes";
    private static final String TAG_TYPE = "type";

    GetDishesList asyncFetch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coockery_list_page);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        TextView txt_dishType = findViewById(R.id.txt_dishType);
        RelativeLayout title = findViewById(R.id.rel_dishlist);
        RecyclerView rv_dishes = findViewById(R.id.recyclerView);
        listOfDishes = new ArrayList<>();

        Typeface typeface = Typeface.createFromAsset(getApplicationContext().getAssets(),
                String.format(Locale.US, "fonts/%s", "font.ttf"));

        txt_dishType.setTypeface(typeface);

        gloadType = getIntent().getIntExtra("loadtype",-1);
        if(gloadType == 0) {
            gType = getIntent().getIntExtra("type", -1);
            print("Type is " + gType);

            txt_dishType.setText(Globals.dishName[gType]);

            String title_image = Globals.host + Globals.appdir + Globals.img_path + "/" +
                    "title" + "/" + gType + ".jpg";

            Runnable imgFetch = new DishImageFetcher(Globals.FETCHTYPE_DISHCATAGORY, gType, title_image, title, this);
            new Thread(imgFetch).start();

            asyncFetch =  new GetDishesList();
            asyncFetch.execute();

            // GridView gv_dishes = findViewById(R.id.gv_dishlist);

            adapterDishList = new AdapterDishGridView(this, listOfDishes, this);

            rv_dishes.setAdapter(adapterDishList);
            StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
            rv_dishes.setLayoutManager(manager);
        }
        else if (gloadType == 1)
        {

        }

    }
    @Override
    public void onItemClick(ListItemDishes item) {
        Intent preparationPage = new Intent(CoockeryListPage.this, CoockeryPreparation.class);
        preparationPage.putExtra("data",item );
        startActivity(preparationPage);
    }

    @Override
    public void onBackPressed()
    {
        print("On back pressed");

        super.onBackPressed();
    }
    /**
     * Background Async Task to Load all Dishes by making HTTP Request
     * */
    class GetDishesList extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = ProgressDialog.show(
                    CoockeryListPage.this,
                    "Please wait...",
                    "Loading "+Globals.dishName[gType],
                    false,
                    true,
                    new DialogInterface.OnCancelListener(){
                        @Override
                        public void onCancel(DialogInterface dialog) {
                            //GetPookkalamList.this.cancel(true);
                        }
                    }
            );
            pDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialogInterface) {
                    print("Cancel pressed");
                    if (asyncFetch != null)
                    {
                        print("Async fetch is not null cancelling");
                        asyncFetch.cancel(true);
                        jParser.cancelReq();
                        finish();
                    }
                }
            });
        }

        /**
         * getting All dishlist from url
         * */
        protected String doInBackground(String... args) {
            // Building Parameters
            boolean b_exit = false;
            gCount = 0;
            do {


                List<NameValuePair> params = new ArrayList<NameValuePair>();

                params.add(new BasicNameValuePair(TAG_TYPE, gType + ""));
                params.add(new BasicNameValuePair("limit", (gCount * 20 ) + ""));
                gCount ++;
                // getting JSON string from URL
                String apiname = "";
                apiname = "getDishesFromType.php";

                JSONObject json = jParser.makeHttpRequest(Globals.host + Globals.appdir + Globals.apipath + apiname,
                        "GET", params);

                // Check your log cat for JSON reponse
                if (json != null) {
                    //print( json.toString());
                    try {
                        // Checking for SUCCESS TAG
                        int success = json.getInt(TAG_SUCCESS);

                        if (success == 1) {
                            dishlist = json.getJSONArray(TAG_DISH);

                            for (int i = 0; i < dishlist.length(); i++) {
                                JSONObject c = dishlist.getJSONObject(i);

                                //print("Id="+c.getInt("id")+ " DishName = "+c.getString("dishname")+ " img_path="+c.getString("img_path"));

                            /*ListItemDishes dish = new ListItemDishes(
                                    c.getInt("id"),
                                    c.getInt("type"),
                                    c.getString("dishname"),
                                    c.getString("img_path")
                                    );*/
                                print("Adding " + c.getString("dishname"));
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
                                listOfDishes.add(dish);
                            }
                            //Collections.shuffle(listOfDishes);
                        } else {
                            b_exit = true;
                            print("No dishes found");
                        }
                    } catch (JSONException e) {
                        b_exit = true;
                        e.printStackTrace();
                    } catch (Exception e) {
                        b_exit = true;
                        e.printStackTrace();
                    }
                } else {
                    b_exit = true;
                    print("Error in making http request");
                }
            } while (b_exit == false);
            print("Got all the dishes info");
            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String file_url) {
            // dismiss the dialog after getting all dishlist
            pDialog.dismiss();
            adapterDishList.notifyDataSetChanged();
        }
    }
}