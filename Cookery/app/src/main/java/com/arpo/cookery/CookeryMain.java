package com.arpo.cookery;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SearchView;
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

public class CookeryMain extends AppCompatActivity implements SearchView.OnQueryTextListener {


    AdapterListSearch searchAdapter;

    List<ListItemDishes> listOfDishesForSearch;
    ListView lv_searchData;
    RelativeLayout rel_search;

    JSONParser jParser = new JSONParser();
    JSONArray dishlist = null;

    private static final String TAG_SUCCESS = "success";
    private static final String TAG_DISH = "dishes";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cookery_main);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        Typeface typeface = Typeface.createFromAsset(getApplicationContext().getAssets(),
                String.format(Locale.US, "fonts/%s", "font.ttf"));

        ((TextView) findViewById(R.id.main_txtbreakfast)).setTypeface(typeface);
        ((TextView) findViewById(R.id.main_txtlunch)).setTypeface(typeface);
        ((TextView) findViewById(R.id.main_txtsnx)).setTypeface(typeface);


        //SearchView searchRecipe = findViewById(R.id.search_query);
        //searchRecipe.setOnQueryTextListener(this);

        EditText searchText = findViewById(R.id.search_query);
        searchText.setTypeface(typeface);
        searchText.clearFocus();
        searchText.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String text = s.toString();

                print(s.toString());
                lv_searchData.setAdapter(null);
                int countS = searchAdapter.filter(s.toString());
                lv_searchData.setAdapter(searchAdapter);
                setRelativeLayoutHeightBasedOnChildren(rel_search, countS ,lv_searchData);
            }
        });

        RelativeLayout relBreakfast = findViewById(R.id.rel_breakfastRec);
        RelativeLayout relLunch = findViewById(R.id.rel_lunchRec);
        RelativeLayout snckRel = findViewById(R.id.rel_snacksRec);

        relBreakfast.requestFocus();

        String title_image = Globals.host + Globals.appdir + Globals.img_path + "/" +
                "title" + "/1.jpg";

        Runnable imgFetch1 = new DishImageFetcher(title_image, relBreakfast, this);
        new Thread(imgFetch1).start();

        title_image = Globals.host + Globals.appdir + Globals.img_path + "/" +
                "title" + "/2.jpg";

        Runnable imgFetch2 = new DishImageFetcher(title_image, relLunch, this);
        new Thread(imgFetch2).start();

        title_image = Globals.host + Globals.appdir + Globals.img_path + "/" +
                "title" + "/3.jpg";

        Runnable imgFetch3 = new DishImageFetcher(title_image, snckRel, this);
        new Thread(imgFetch3).start();

        relBreakfast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cookeryListPage = new Intent(getApplicationContext(), CoockeryListPage.class);
                cookeryListPage.putExtra("type",1);
                cookeryListPage.putExtra("loadtype",0);
                startActivity(cookeryListPage);
            }
        });

        relLunch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cookeryListPage = new Intent(getApplicationContext(), CoockeryListPage.class);
                cookeryListPage.putExtra("type",2);
                cookeryListPage.putExtra("loadtype",0);
                startActivity(cookeryListPage);
            }
        });

        snckRel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cookeryListPage = new Intent(getApplicationContext(), CoockeryListPage.class);
                cookeryListPage.putExtra("type",3);
                cookeryListPage.putExtra("loadtype",0);
                startActivity(cookeryListPage);
            }
        });

        // Fill in search data
        listOfDishesForSearch = new ArrayList<>();


        searchAdapter = new AdapterListSearch(this, listOfDishesForSearch);

        lv_searchData = findViewById(R.id.lv_searchData);
        lv_searchData.setAdapter(searchAdapter);

        rel_search = findViewById(R.id.rel_search);
        searchAdapter.clearData();
        setRelativeLayoutHeightBasedOnChildren(rel_search, 0,lv_searchData);
        lv_searchData.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                Intent preparationPage = new Intent(CookeryMain.this, CoockeryPreparation.class);
                ListItemDishes data = (ListItemDishes) lv_searchData.getItemAtPosition(position);
                preparationPage.putExtra("data", data );
                startActivity(preparationPage);
                EditText searchText = findViewById(R.id.search_query);
                searchText.setText("");
            }
        });


        new GetDishesForSearch().execute();

        relBreakfast.requestFocus();
        hideSoftKeyboard();

    }
    private void hideSoftKeyboard(){
        View view = getCurrentFocus ();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService (Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow (view.getWindowToken (), 0);
        }
    }
    void print(String str)
    {
        //Log.d("JKS",str);
    }
    @Override
    public boolean onQueryTextSubmit(String query) {

        print("Search for "+query);

        Intent cookeryListPage = new Intent(getApplicationContext(), CoockeryListPage.class);

        cookeryListPage.putExtra("type",1);
        cookeryListPage.putExtra("loadtype",1);
        //cookeryListPage.put("list",listOfDishesForSearch);
       // startActivity(cookeryListPage);

        for (ListItemDishes dish: listOfDishesForSearch
             ) {
            print("Pass "+dish.getName());
        }
        print("JKS");

        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        String text = newText;

        print(newText);
        lv_searchData.setAdapter(null);
        int count = searchAdapter.filter(newText);
        lv_searchData.setAdapter(searchAdapter);
        setRelativeLayoutHeightBasedOnChildren(rel_search, count,lv_searchData);
        return false;
    }

    public static void setRelativeLayoutHeightBasedOnChildren(RelativeLayout layout_rel, int Count, ListView lv) {
        ListAdapter listAdapter = lv.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, lv);
            listItem.measure(0, 0);
            //Log.d("JKS","Measured height = "+listItem.getMeasuredHeight());
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = layout_rel.getLayoutParams();
        params.height = totalHeight + (lv.getDividerHeight() * (listAdapter.getCount() - 1));
        //Log.d("JKS","height="+params.height);
        layout_rel.setLayoutParams(params);
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

        /**
         * getting All dishlist from url
         * */
        protected String doInBackground(String... args) {
            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();

            // getting JSON string from URL
            String apiname = "";
            apiname ="getAllDishes.php";

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
            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String file_url) {
            // dismiss the dialog after getting all dishlist
            //pDialog.dismiss();

            searchAdapter.updateOriginals(listOfDishesForSearch);
            searchAdapter.clearData();
            searchAdapter.notifyDataSetChanged();
        }
    }
}

class AdapterListSearch extends BaseAdapter {
    List<ListItemDishes> list;
    List<ListItemDishes> origianlList;
    Context context;
    RelativeLayout layout;

    AdapterListSearch(Context ctx, List<ListItemDishes> l) {
        list = l;
        origianlList = new ArrayList<>();

        for (ListItemDishes data : l) {
            origianlList.add(data);
        }

        context = ctx;
    }
    void clearData()
    {
        list.clear();
    }

    void updateOriginals(List<ListItemDishes> Originals)
    {
        origianlList.clear();
        for (ListItemDishes data : Originals) {
            origianlList.add(data);
        }
    }


    @Override
    public int getCount() {
        return list.size(); //returns total of items in the list
    }

    @Override
    public Object getItem(int position) {
        return list.get(position); //returns list item at the specified position
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).
                    inflate(R.layout.searchdata, parent, false);


            TextView search = convertView.findViewById(R.id.txt_searchQuery);
            search.setText("");

            search.setText(list.get(position).getName());
            Typeface typeface = Typeface.createFromAsset(context.getAssets(),
                    String.format(Locale.US, "fonts/%s", "font.ttf"));

            search.setTypeface(typeface);

        }
        return convertView;
    }


    // Filter Class
    public int filter(String charText) {
        int count = 0;
        charText = charText.toLowerCase(Locale.getDefault());

        list.clear();
       // print("JKS list item size "+list.size());



        notifyDataSetChanged();

        if (charText.length() == 0) {
            list.clear();
            notifyDataSetChanged();
        }
        else {
            for (int i = 0; i < origianlList.size(); i++) {
                ListItemDishes data = origianlList.get(i);
                if (data.getName().toLowerCase(Locale.getDefault()).contains(charText)) {
                    list.add(data);

                    notifyDataSetChanged();
                    //print("JKS list contains "+data.getName());
                    count++;
                }
                else
                {
                 //   print("skip "+data.getName());
                }

            }
        }

        return  count;
    }

    void print(String str)
    {
        Log.d("JKS",str);
    }
}

