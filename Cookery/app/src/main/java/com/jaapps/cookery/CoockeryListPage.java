package com.jaapps.cookery;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.json.JSONArray;

import java.util.ArrayList;
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
    int gIndexFromType = 0;
    int gloadType = 0;
    int gCount = 0;
    List<ListItemDishes> listOfDishes;
    AdapterDishGridView  adapterDishList;

    private static final String TAG_SUCCESS = "success";
    private static final String TAG_DISH = "dishes";
    private static final String TAG_TYPE = "type";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coockery_list_page);

/*        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();*/

        android.support.v7.widget.Toolbar tb = findViewById(R.id.toolBar_title);
        setSupportActionBar(tb);
        if (getSupportActionBar() != null)
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        tb.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //onBackPressed()
                finish();
            }
        });

        //TextView txt_dishType = findViewById(R.id.txt_dishType);
        //RelativeLayout title = findViewById(R.id.rel_dishlist);
        RecyclerView rv_dishes = findViewById(R.id.recyclerView);
        listOfDishes = new ArrayList<>();

        Typeface typeface = Typeface.createFromAsset(getApplicationContext().getAssets(),
                                    String.format(Locale.US, "fonts/%s", "font.ttf"));
        Window vindow = this.getWindow();
        vindow.setStatusBarColor(Color.BLACK);

        // txt_dishType.setTypeface(typeface);
        for(int i = 0; i < tb.getChildCount(); i ++)
        {
            View v = tb.getChildAt(i);
            if (v instanceof TextView)
            {
                Typeface Boldtypeface = Typeface.createFromAsset(getApplicationContext().getAssets(),
                        String.format(Locale.US, "fonts/%s", "fontfront.ttf"));
                TextView txt = (TextView)v;

            }
        }



        gloadType = getIntent().getIntExtra("loadtype",-1);
        if(gloadType == 0) {
            gType = getIntent().getIntExtra("type", -1);

            gIndexFromType = gType;

            gType = 1 << (gType - 1);

            print("Type is " + gType);
            //txt_dishType.setText(Globals.dishName[gType]);

            getSupportActionBar().setTitle(Globals.dishName[gIndexFromType]);


            String title_image = Globals.host + Globals.appdir + Globals.img_path + "/" +
                    "title" + "/" + gIndexFromType + ".jpg";

            //get screen width
            float dpWidth = getResources().getDisplayMetrics().widthPixels / getResources().getDisplayMetrics().density;

            RelativeLayout layout = findViewById(R.id.img_tltle_layout);
            DishImageFetcher imgFetch = new DishImageFetcher(Globals.FETCHTYPE_DISHCATAGORY, gIndexFromType, title_image, layout, this, true);

            imgFetch.setWidth((int)dpWidth);
            imgFetch.setImgListPageLayoutGrad((ImageView)findViewById(R.id.img_title), (CollapsingToolbarLayout) findViewById(R.id.colLayOut));

            new Thread(imgFetch).start();

            Globals.sqlData.getDishOfType(gType, listOfDishes);

        }
        else if (gloadType == 1)
        {
            getSupportActionBar().setTitle("Favourites");
            /*getSupportActionBar().setTitle(" ");*/
            String title_image = Globals.host + Globals.appdir + Globals.img_path + "/" +
                    "title/fav.jpg";
            //get screen width
            float dpWidth = getResources().getDisplayMetrics().widthPixels / getResources().getDisplayMetrics().density;

            RelativeLayout layout = findViewById(R.id.img_tltle_layout);
            DishImageFetcher imgFetch = new DishImageFetcher(Globals.FETCHTYPE_DISHCATAGORY, gType, title_image, layout, this, true);

            imgFetch.setWidth((int)dpWidth);
            imgFetch.setImgListPageLayoutGrad((ImageView)findViewById(R.id.img_title), (CollapsingToolbarLayout) findViewById(R.id.colLayOut));

            new Thread(imgFetch).start();

            Globals.sqlData.getFavList(listOfDishes);

        }

        else if (gloadType == 2)
        {
            getSupportActionBar().setTitle("Search Results");
            String title_image = Globals.host + Globals.appdir + Globals.img_path + "/" +
                    "title/fav.jpg";
            //get screen width
            float dpWidth = getResources().getDisplayMetrics().widthPixels / getResources().getDisplayMetrics().density;

            RelativeLayout layout = findViewById(R.id.img_tltle_layout);
            DishImageFetcher imgFetch = new DishImageFetcher(Globals.FETCHTYPE_DISHCATAGORY, gType, title_image, layout, this, true);

            imgFetch.setWidth((int)dpWidth);
            imgFetch.setImgListPageLayoutGrad((ImageView)findViewById(R.id.img_title), (CollapsingToolbarLayout) findViewById(R.id.colLayOut));

            new Thread(imgFetch).start();

            //Globals.sqlData.getFavList(listOfDishes);
            Globals.sqlData.getSearchData(listOfDishes, getIntent().getStringExtra("search"));

        }


        adapterDishList = new AdapterDishGridView(this, listOfDishes, this);

        rv_dishes.setAdapter(adapterDishList);
        StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        rv_dishes.setLayoutManager(manager);

    }
    @Override
    public void onItemClick(ListItemDishes item) {
        Intent preparationPage = new Intent(CoockeryListPage.this, CoockeryPreparation.class);
        preparationPage.putExtra("data",item );
        startActivityForResult(preparationPage, 100);
    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {


        if  (gloadType == 1) {
            listOfDishes.clear();

            Globals.sqlData.getFavList(listOfDishes);
            adapterDishList.notifyDataSetChanged();
        }
    }
    }