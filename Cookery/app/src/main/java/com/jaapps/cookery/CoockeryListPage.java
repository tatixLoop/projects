package com.jaapps.cookery;

import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class CoockeryListPage extends AppCompatActivity implements AdapterDishGridView.ItemListener {

    void print(String str)
    {
        Log.d("JKS",str);
    }


    int gloadType = 0;

    List<ListItemDishes> listOfDishes;
    AdapterDishGridView  adapterDishList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        int gType = 0;
        int gIndexFromType;
        AdView mAdView;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coockery_list_page);

        mAdView = findViewById(R.id.adViewmiddle);
        AdRequest adRequestBanner = new AdRequest.Builder()
        //        .addTestDevice("C9DCF6327A4B5E68DCC320AC2E54036C")
                .build();
        mAdView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
            }

            @Override
            public void onAdClosed() {
                print("Ad is closed!");
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                print( "Ad failed to load! error code: " + errorCode);
            }

            @Override
            public void onAdLeftApplication() {
                print( "Ad left application!");
            }

            @Override
            public void onAdOpened() {
                super.onAdOpened();
            }
        });

        mAdView.loadAd(adRequestBanner);


        // initialize the AdMob app
        MobileAds.initialize(this, getString(R.string.admob_app_id));


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

        Window vindow = this.getWindow();
        vindow.setStatusBarColor(Color.BLACK);



        Display display = getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();

        display.getMetrics(outMetrics);
        float density = getResources().getDisplayMetrics().density;
        float width = outMetrics.widthPixels/density ;

        gloadType = getIntent().getIntExtra("loadtype",-1);
        if(gloadType == 0) {
            gType = getIntent().getIntExtra("type", -1);

            gIndexFromType = gType;

            gType = 1 << (gType - 1);

            print("Type is " + gType);
            //txt_dishType.setText(Globals.dishName[gType]);

            getSupportActionBar().setTitle(getIntent().getStringExtra("title"));

            String title_image = getIntent().getStringExtra("url");

            //get screen width
            float dpWidth = getResources().getDisplayMetrics().widthPixels / getResources().getDisplayMetrics().density;

            RelativeLayout layout = findViewById(R.id.img_tltle_layout);
            DishImageFetcher imgFetch = new DishImageFetcher(Globals.FETCHTYPE_DISHCATAGORY, gIndexFromType, title_image, layout, this, true);

            imgFetch.setWidth((int)dpWidth);
            imgFetch.setImgListPageLayoutGrad((ImageView)findViewById(R.id.img_title), (CollapsingToolbarLayout) findViewById(R.id.colLayOut));

            new Thread(imgFetch).start();

            Globals.sqlData.getDishOfType(gType, listOfDishes);
            Collections.shuffle(listOfDishes);

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
        else if (gloadType == 3)
        {
            int subtype = getIntent().getIntExtra("subtype", 0);
            String title_image = getIntent().getStringExtra("url");
            gType = getIntent().getIntExtra("type", -1);
            getSupportActionBar().setTitle( getIntent().getStringExtra("title"));

            float dpWidth = getResources().getDisplayMetrics().widthPixels / getResources().getDisplayMetrics().density;

            RelativeLayout layout = findViewById(R.id.img_tltle_layout);
            DishImageFetcher imgFetch = new DishImageFetcher(Globals.FETCHTYPE_DISHSUBCATAGORY, subtype, title_image, layout, this, true);

            imgFetch.setWidth((int)dpWidth);
            imgFetch.setImgListPageLayoutGrad((ImageView)findViewById(R.id.img_title), (CollapsingToolbarLayout) findViewById(R.id.colLayOut));

            new Thread(imgFetch).start();


            Globals.sqlData.getDishOfTypeSubType(gType, subtype, listOfDishes);
            Collections.shuffle(listOfDishes);

        }


        adapterDishList = new AdapterDishGridView(this, listOfDishes, this, width);

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