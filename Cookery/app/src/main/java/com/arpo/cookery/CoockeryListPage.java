package com.arpo.cookery;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
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

    List<ListItemDishes> listOfDishes;
    AdapterDishGridView  adapterDishList;

    private static final String TAG_SUCCESS = "success";
    private static final String TAG_DISH = "dishes";
    private static final String TAG_TYPE = "type";

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

        gloadType = getIntent().getIntExtra("loadtype",-1);
        if(gloadType == 0) {
            gType = getIntent().getIntExtra("type", -1);
            print("Type is " + gType);

            txt_dishType.setText(Globals.dishName[gType]);

            String title_image = Globals.host + Globals.appdir + Globals.img_path + "/" +
                    "title" + "/" + gType + ".jpg";

            Runnable imgFetch = new DishImageFetcher(title_image, title, this);
            new Thread(imgFetch).start();

            new GetDishesList().execute();

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
                    false,
                    new DialogInterface.OnCancelListener(){
                        @Override
                        public void onCancel(DialogInterface dialog) {
                            //GetPookkalamList.this.cancel(true);
                        }
                    }
            );
        }

        /**
         * getting All dishlist from url
         * */
        protected String doInBackground(String... args) {
            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();

            params.add(new BasicNameValuePair(TAG_TYPE, gType+""));
            // getting JSON string from URL
            String apiname = "";
            apiname ="getDishesFromType.php";

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
                                    c.getString("author")
                            );
                            listOfDishes.add(dish);
                        }
                        Collections.shuffle(listOfDishes);
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