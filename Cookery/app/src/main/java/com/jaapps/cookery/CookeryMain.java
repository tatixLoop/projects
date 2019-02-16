package com.jaapps.cookery;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.os.Handler;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class CookeryMain extends AppCompatActivity {


    AdapterListSearch searchAdapter;

    List<ListItemDishes> listOfDishesForSearch;
    ListView lv_searchData;
    RelativeLayout rel_search;

    ScrollView scrolllayout;


    ///////////     navigation Drawer

    private DrawerLayout mdrawerLayout;
    private ActionBarDrawerToggle mtoggle;

    SearchView searchView;
    MenuItem item;

    boolean doubleBackToExitPressedOnce= false;

    private AdView mAdView;
    private AdView mAdView2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navigationdrawer);


        // initialize the AdMob app
        MobileAds.initialize(this, getString(R.string.admob_app_id));
        ///Navigation Drawer

        mdrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mtoggle= new ActionBarDrawerToggle(this,mdrawerLayout,R.string.open,R.string.close);
        mdrawerLayout.addDrawerListener(mtoggle);
        mtoggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        /*ActionBar actionBar = getSupportActionBar();
        actionBar.hide();*/


        isNetworkConnected();  //tocheck internet is available or not

        RelativeLayout relAdView = findViewById(R.id.rel_adview);
        RelativeLayout relAdView2 = findViewById(R.id.rel_adview2);


        //get screen width
        float dpWidth = getResources().getDisplayMetrics().widthPixels / getResources().getDisplayMetrics().density;

        mAdView = new AdView(this);
        relAdView.addView(mAdView);
        mAdView.setAdUnitId(getResources().getString(R.string.banner_home_menu1));

        AdSize customSize = new AdSize((int)dpWidth, 200);
        mAdView.setAdSize(customSize);

        AdRequest adRequestBanner = new AdRequest.Builder()
                //.addTestDevice("C9DCF6327A4B5E68DCC320AC2E54036C")
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

        mAdView2 = new AdView(this);
        relAdView2.addView(mAdView2);
        mAdView2.setAdUnitId(getResources().getString(R.string.banner_home_menu2));

        mAdView2.setAdSize(customSize);

        AdRequest adRequestBanner2 = new AdRequest.Builder()
                //.addTestDevice("C9DCF6327A4B5E68DCC320AC2E54036C")
                .build();
        mAdView2.setAdListener(new AdListener() {
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

        mAdView2.loadAd(adRequestBanner);




        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        Typeface typeface = Typeface.createFromAsset(getApplicationContext().getAssets(),
                String.format(Locale.US, "fonts/%s", "fontfront.ttf"));


        ((TextView) findViewById(R.id.main_txtbreakfast)).setTypeface(typeface);
        ((TextView) findViewById(R.id.main_txtlunch)).setTypeface(typeface);
        ((TextView) findViewById(R.id.main_txtsnx)).setTypeface(typeface);
        ((TextView) findViewById(R.id.main_txtjuice)).setTypeface(typeface);
        ((TextView) findViewById(R.id.main_txtCookie)).setTypeface(typeface);

        ((TextView) findViewById(R.id.main_txttodaySpecial)).setTypeface(typeface);
        ((TextView) findViewById(R.id.main_txtomlets)).setTypeface(typeface);
        ((TextView) findViewById(R.id.main_txteggDishes)).setTypeface(typeface);
        ((TextView) findViewById(R.id.main_txtchicken)).setTypeface(typeface);
        ((TextView) findViewById(R.id.main_txtcoffee)).setTypeface(typeface);
        ((TextView) findViewById(R.id.main_txtdeserts)).setTypeface(typeface);
        ((TextView) findViewById(R.id.main_txthealthy)).setTypeface(typeface);
        ((TextView) findViewById(R.id.main_txticecream)).setTypeface(typeface);
        ((TextView) findViewById(R.id.main_txtpizza)).setTypeface(typeface);
        ((TextView) findViewById(R.id.main_txtsalads)).setTypeface(typeface);
        ((TextView) findViewById(R.id.main_txtseafood)).setTypeface(typeface);
        ((TextView) findViewById(R.id.main_txtindian)).setTypeface(typeface);
        ((TextView) findViewById(R.id.main_txtchinesene)).setTypeface(typeface);
        ((TextView) findViewById(R.id.main_txtamerican)).setTypeface(typeface);

        ((TextView) findViewById(R.id.main_txtcake)).setTypeface(typeface);
        ((TextView) findViewById(R.id.main_txtbread)).setTypeface(typeface);
        ((TextView) findViewById(R.id.main_txtpasta)).setTypeface(typeface);
        ((TextView) findViewById(R.id.main_txtvegan)).setTypeface(typeface);
        ((TextView) findViewById(R.id.main_txtsoup)).setTypeface(typeface);
        ((TextView) findViewById(R.id.main_txtrice)).setTypeface(typeface);



        final RelativeLayout relBreakfast = findViewById(R.id.rel_breakfastRec);
        RelativeLayout relLunch = findViewById(R.id.rel_lunchRec);
        RelativeLayout snckRel = findViewById(R.id.rel_snacksRec);
        RelativeLayout snckJuice = findViewById(R.id.rel_juice);
        RelativeLayout snckCookie = findViewById(R.id.rel_snacksCookie);
        RelativeLayout todaySpecial = findViewById(R.id.rel_todaySpecial);
        RelativeLayout omlet = findViewById(R.id.rel_omlets);
        RelativeLayout chicken = findViewById(R.id.rel_chicken);
        RelativeLayout coffee = findViewById(R.id.rel_coffee);
        RelativeLayout desert = findViewById(R.id.rel_desert);
        RelativeLayout healty = findViewById(R.id.rel_healthy);
        RelativeLayout icecream = findViewById(R.id.rel_icecream);
        RelativeLayout pizza = findViewById(R.id.rel_pizza);
        RelativeLayout salad = findViewById(R.id.rel_salad);
        RelativeLayout seafood = findViewById(R.id.rel_seafood);
        RelativeLayout indian = findViewById(R.id.rel_indian);
        RelativeLayout chinese = findViewById(R.id.rel_chinese);
        RelativeLayout american = findViewById(R.id.rel_american);
        RelativeLayout eggRecipes = findViewById(R.id.rel_eggDishes);

        RelativeLayout cake = findViewById(R.id.rel_cake);
        RelativeLayout bread = findViewById(R.id.rel_bread);
        RelativeLayout pasta = findViewById(R.id.rel_pasta);
        RelativeLayout vegan = findViewById(R.id.rel_vegan);
        RelativeLayout soup = findViewById(R.id.rel_soup);
        RelativeLayout rice = findViewById(R.id.rel_rice);

       // float dpWidth = getResources().getDisplayMetrics().widthPixels / getResources().getDisplayMetrics().density;


        relBreakfast.requestFocus();

        String title_image = Globals.host + Globals.appdir + Globals.img_path + "/" +
                "title" + "/1.jpg";

        DishImageFetcher imgFetch1 = new DishImageFetcher(Globals.FETCHTYPE_DISHCATAGORY, 1, title_image, relBreakfast, this, true);
        imgFetch1.setWidth((int)dpWidth);
        imgFetch1.setLayoutFront(((TextView) findViewById(R.id.main_txtbreakfast)));
        new Thread(imgFetch1).start();

        title_image = Globals.host + Globals.appdir + Globals.img_path + "/" +
                "title" + "/2.jpg";

        DishImageFetcher imgFetch2 = new DishImageFetcher(Globals.FETCHTYPE_DISHCATAGORY, 2, title_image, relLunch, this, true);
        imgFetch2.setWidth((int)dpWidth);
        imgFetch2.setLayoutFront(((TextView) findViewById(R.id.main_txtlunch)));
        new Thread(imgFetch2).start();

        title_image = Globals.host + Globals.appdir + Globals.img_path + "/" +
                "title" + "/3.jpg";

        DishImageFetcher imgFetch3 = new DishImageFetcher(Globals.FETCHTYPE_DISHCATAGORY, 3, title_image, snckRel, this, true);
        imgFetch3.setWidth((int)dpWidth);
        imgFetch3.setLayoutFront(((TextView) findViewById(R.id.main_txtsnx)));
        new Thread(imgFetch3).start();

        title_image = Globals.host + Globals.appdir + Globals.img_path + "/" +
                "title" + "/4.jpg";

        DishImageFetcher imgFetch4 = new DishImageFetcher(Globals.FETCHTYPE_DISHCATAGORY, 4,  title_image, snckJuice, this, true);
        imgFetch4.setWidth((int)dpWidth);
        imgFetch4.setLayoutFront(((TextView) findViewById(R.id.main_txtjuice)));
        new Thread(imgFetch4).start();

        title_image = Globals.host + Globals.appdir + Globals.img_path + "/" +
                "title" + "/5.jpg";

        DishImageFetcher imgFetch5 = new DishImageFetcher(Globals.FETCHTYPE_DISHCATAGORY, 5, title_image, snckCookie, this, true);
        imgFetch5.setWidth((int)dpWidth);
        imgFetch5.setLayoutFront(((TextView) findViewById(R.id.main_txtCookie)));
        new Thread(imgFetch5).start();


        DishImageFetcher imgFetch6 = new DishImageFetcher(Globals.FETCHTYPE_DISHCATAGORY, 6,  Globals.host + Globals.appdir + Globals.img_path + "/" +
                "title" + "/6.jpg", todaySpecial, this, true);
        imgFetch6.setWidth((int)dpWidth);
        imgFetch6.setLayoutFront(((TextView) findViewById(R.id.main_txttodaySpecial)));
        new Thread(imgFetch6).start();
        DishImageFetcher imgFetch7 = new DishImageFetcher(Globals.FETCHTYPE_DISHCATAGORY, 7, Globals.host + Globals.appdir + Globals.img_path + "/" +
                "title" + "/7.jpg", omlet, this, true);
        imgFetch7.setWidth((int)dpWidth);
        imgFetch7.setLayoutFront(((TextView) findViewById(R.id.main_txtomlets)));
        new Thread(imgFetch7).start();
        DishImageFetcher imgFetch8 = new DishImageFetcher(Globals.FETCHTYPE_DISHCATAGORY, 8, Globals.host + Globals.appdir + Globals.img_path + "/" +
                "title" + "/8.jpg", eggRecipes, this, true);
        imgFetch8.setWidth((int)dpWidth);
        imgFetch8.setLayoutFront(((TextView) findViewById(R.id.main_txteggDishes)));
        new Thread(imgFetch8).start();

        DishImageFetcher imgFetch9 = new DishImageFetcher(Globals.FETCHTYPE_DISHCATAGORY, 9, Globals.host + Globals.appdir + Globals.img_path + "/" +
                "title" + "/9.jpg", chicken, this, true);
        imgFetch9.setWidth((int)dpWidth);
        imgFetch9.setLayoutFront(((TextView) findViewById(R.id.main_txtchicken)));
        new Thread(imgFetch9).start();
        DishImageFetcher imgFetch10 = new DishImageFetcher(Globals.FETCHTYPE_DISHCATAGORY, 10, Globals.host + Globals.appdir + Globals.img_path + "/" +
                "title" + "/10.jpg", coffee, this, true);
        imgFetch10.setWidth((int)dpWidth);
        imgFetch10.setLayoutFront(((TextView) findViewById(R.id.main_txtcoffee)));
        new Thread(imgFetch10).start();
        DishImageFetcher imgFetch11 = new DishImageFetcher(Globals.FETCHTYPE_DISHCATAGORY, 11, Globals.host + Globals.appdir + Globals.img_path + "/" +
                "title" + "/11.jpg", desert, this, true);
        imgFetch11.setWidth((int)dpWidth);
        imgFetch11.setLayoutFront(((TextView) findViewById(R.id.main_txtdeserts)));
        new Thread(imgFetch11).start();
        DishImageFetcher imgFetch12 = new DishImageFetcher(Globals.FETCHTYPE_DISHCATAGORY, 12, Globals.host + Globals.appdir + Globals.img_path + "/" +
                "title" + "/12.jpg", healty, this, true);
        imgFetch12.setWidth((int)dpWidth);
        imgFetch12.setLayoutFront(((TextView) findViewById(R.id.main_txthealthy)));
        new Thread(imgFetch12).start();
        DishImageFetcher imgFetch13 = new DishImageFetcher(Globals.FETCHTYPE_DISHCATAGORY, 13, Globals.host + Globals.appdir + Globals.img_path + "/" +
                "title" + "/13.jpg", icecream, this, true);
        imgFetch13.setWidth((int)dpWidth);
        imgFetch13.setLayoutFront(((TextView) findViewById(R.id.main_txticecream)));
        new Thread(imgFetch13).start();
        DishImageFetcher imgFetch14 = new DishImageFetcher(Globals.FETCHTYPE_DISHCATAGORY, 14,  Globals.host + Globals.appdir + Globals.img_path + "/" +
                "title" + "/14.jpg", pizza, this, true);
        imgFetch14.setWidth((int)dpWidth);
        imgFetch14.setLayoutFront(((TextView) findViewById(R.id.main_txtpizza)));
        new Thread(imgFetch14).start();
        DishImageFetcher imgFetch15 = new DishImageFetcher(Globals.FETCHTYPE_DISHCATAGORY, 15, Globals.host + Globals.appdir + Globals.img_path + "/" +
                "title" + "/15.jpg", seafood, this, true);
        imgFetch15.setWidth((int)dpWidth);
        imgFetch15.setLayoutFront(((TextView) findViewById(R.id.main_txtseafood)));
        new Thread(imgFetch15).start();
        DishImageFetcher imgFetch16 = new DishImageFetcher(Globals.FETCHTYPE_DISHCATAGORY, 16, Globals.host + Globals.appdir + Globals.img_path + "/" +
                "title" + "/16.jpg", indian, this, true);
        imgFetch16.setWidth((int)dpWidth);
        imgFetch16.setLayoutFront(((TextView) findViewById(R.id.main_txtindian)));
        new Thread(imgFetch16).start();
        DishImageFetcher imgFetch17 = new DishImageFetcher(Globals.FETCHTYPE_DISHCATAGORY, 17, Globals.host + Globals.appdir + Globals.img_path + "/" +
                "title" + "/17.jpg", chinese, this, true);
        imgFetch17.setWidth((int)dpWidth);
        imgFetch17.setLayoutFront(((TextView) findViewById(R.id.main_txtchinesene)));
        new Thread(imgFetch17).start();
        DishImageFetcher imgFetch18 = new DishImageFetcher(Globals.FETCHTYPE_DISHCATAGORY, 18, Globals.host + Globals.appdir + Globals.img_path + "/" +
                "title" + "/18.jpg", american, this, true);
        imgFetch18.setWidth((int)dpWidth);
        imgFetch18.setLayoutFront(((TextView) findViewById(R.id.main_txtamerican)));
        new Thread(imgFetch18).start();
        DishImageFetcher imgFetch19 = new DishImageFetcher(Globals.FETCHTYPE_DISHCATAGORY, 19, Globals.host + Globals.appdir + Globals.img_path + "/" +
                "title" + "/19.jpg", salad, this, true);
        imgFetch19.setWidth((int)dpWidth);
        imgFetch19.setLayoutFront(((TextView) findViewById(R.id.main_txtsalads)));
        new Thread(imgFetch19).start();

        DishImageFetcher imgFetch20 = new DishImageFetcher(Globals.FETCHTYPE_DISHCATAGORY, 20, Globals.host + Globals.appdir + Globals.img_path + "/" +
                "title" + "/20.jpg", cake, this, true);
        imgFetch20.setWidth((int)dpWidth);
        imgFetch20.setLayoutFront(((TextView) findViewById(R.id.main_txtcake)));
        new Thread(imgFetch20).start();

        DishImageFetcher imgFetch21 = new DishImageFetcher(Globals.FETCHTYPE_DISHCATAGORY, 21, Globals.host + Globals.appdir + Globals.img_path + "/" +
                "title" + "/21.jpg", bread, this, true);
        imgFetch21.setWidth((int)dpWidth);
        imgFetch21.setLayoutFront(((TextView) findViewById(R.id.main_txtbread)));
        new Thread(imgFetch21).start();

        DishImageFetcher imgFetch22 = new DishImageFetcher(Globals.FETCHTYPE_DISHCATAGORY, 22, Globals.host + Globals.appdir + Globals.img_path + "/" +
                "title" + "/22.jpg", pasta, this, true);
        imgFetch22.setWidth((int)dpWidth);
        imgFetch22.setLayoutFront(((TextView) findViewById(R.id.main_txtpasta)));
        new Thread(imgFetch22).start();

        DishImageFetcher imgFetch23 = new DishImageFetcher(Globals.FETCHTYPE_DISHCATAGORY, 23, Globals.host + Globals.appdir + Globals.img_path + "/" +
                "title" + "/23.jpg", vegan, this, true);
        imgFetch23.setWidth((int)dpWidth);
        imgFetch23.setLayoutFront(((TextView) findViewById(R.id.main_txtvegan)));
        new Thread(imgFetch23).start();

        DishImageFetcher imgFetch24 = new DishImageFetcher(Globals.FETCHTYPE_DISHCATAGORY, 24, Globals.host + Globals.appdir + Globals.img_path + "/" +
                "title" + "/24.jpg", soup, this, true);
        imgFetch24.setWidth((int)dpWidth);
        imgFetch24.setLayoutFront(((TextView) findViewById(R.id.main_txtsoup)));
        new Thread(imgFetch24).start();

        DishImageFetcher imgFetch25 = new DishImageFetcher(Globals.FETCHTYPE_DISHCATAGORY, 25, Globals.host + Globals.appdir + Globals.img_path + "/" +
                "title" + "/25.jpg", rice, this, true);
        imgFetch25.setWidth((int)dpWidth);
        imgFetch25.setLayoutFront(((TextView) findViewById(R.id.main_txtrice)));
        new Thread(imgFetch25).start();

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

        snckJuice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cookeryListPage = new Intent(getApplicationContext(), CoockeryListPage.class);
                cookeryListPage.putExtra("type",4);
                cookeryListPage.putExtra("loadtype",0);
                startActivity(cookeryListPage);
            }
        });

        snckCookie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cookeryListPage = new Intent(getApplicationContext(), CoockeryListPage.class);
                cookeryListPage.putExtra("type",5);
                cookeryListPage.putExtra("loadtype",0);
                startActivity(cookeryListPage);
            }
        });

        salad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cookeryListPage = new Intent(getApplicationContext(), CoockeryListPage.class);
                cookeryListPage.putExtra("type",19);
                cookeryListPage.putExtra("loadtype",0);
                startActivity(cookeryListPage);
            }
        });

        todaySpecial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cookeryListPage = new Intent(getApplicationContext(), CoockeryListPage.class);
                cookeryListPage.putExtra("type",6);
                cookeryListPage.putExtra("loadtype",0);
                startActivity(cookeryListPage);
            }
        });

        omlet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cookeryListPage = new Intent(getApplicationContext(), CoockeryListPage.class);
                cookeryListPage.putExtra("type",7);
                cookeryListPage.putExtra("loadtype",0);
                startActivity(cookeryListPage);
            }
        });

        eggRecipes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cookeryListPage = new Intent(getApplicationContext(), CoockeryListPage.class);
                cookeryListPage.putExtra("type",8);
                cookeryListPage.putExtra("loadtype",0);
                startActivity(cookeryListPage);
            }
        });

        chicken.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cookeryListPage = new Intent(getApplicationContext(), CoockeryListPage.class);
                cookeryListPage.putExtra("type",9);
                cookeryListPage.putExtra("loadtype",0);
                startActivity(cookeryListPage);
            }
        });

        coffee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cookeryListPage = new Intent(getApplicationContext(), CoockeryListPage.class);
                cookeryListPage.putExtra("type",10);
                cookeryListPage.putExtra("loadtype",0);
                startActivity(cookeryListPage);
            }
        });

        desert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cookeryListPage = new Intent(getApplicationContext(), CoockeryListPage.class);
                cookeryListPage.putExtra("type",11);
                cookeryListPage.putExtra("loadtype",0);
                startActivity(cookeryListPage);
            }
        });

        healty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cookeryListPage = new Intent(getApplicationContext(), CoockeryListPage.class);
                cookeryListPage.putExtra("type",12);
                cookeryListPage.putExtra("loadtype",0);
                startActivity(cookeryListPage);
            }
        });

        icecream.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cookeryListPage = new Intent(getApplicationContext(), CoockeryListPage.class);
                cookeryListPage.putExtra("type",13);
                cookeryListPage.putExtra("loadtype",0);
                startActivity(cookeryListPage);
            }
        });

        pizza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cookeryListPage = new Intent(getApplicationContext(), CoockeryListPage.class);
                cookeryListPage.putExtra("type",14);
                cookeryListPage.putExtra("loadtype",0);
                startActivity(cookeryListPage);
            }
        });

        seafood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cookeryListPage = new Intent(getApplicationContext(), CoockeryListPage.class);
                cookeryListPage.putExtra("type",15);
                cookeryListPage.putExtra("loadtype",0);
                startActivity(cookeryListPage);
            }
        });

        indian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cookeryListPage = new Intent(getApplicationContext(), CoockeryListPage.class);
                cookeryListPage.putExtra("type",16);
                cookeryListPage.putExtra("loadtype",0);
                startActivity(cookeryListPage);
            }
        });

        chinese.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cookeryListPage = new Intent(getApplicationContext(), CoockeryListPage.class);
                cookeryListPage.putExtra("type",17);
                cookeryListPage.putExtra("loadtype",0);
                startActivity(cookeryListPage);
            }
        });

        american.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cookeryListPage = new Intent(getApplicationContext(), CoockeryListPage.class);
                cookeryListPage.putExtra("type",18);
                cookeryListPage.putExtra("loadtype",0);
                startActivity(cookeryListPage);
            }
        });

        cake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cookeryListPage = new Intent(getApplicationContext(), CoockeryListPage.class);
                cookeryListPage.putExtra("type",20);
                cookeryListPage.putExtra("loadtype",0);
                startActivity(cookeryListPage);
            }
        });

        bread.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cookeryListPage = new Intent(getApplicationContext(), CoockeryListPage.class);
                cookeryListPage.putExtra("type",21);
                cookeryListPage.putExtra("loadtype",0);
                startActivity(cookeryListPage);
            }
        });

        pasta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cookeryListPage = new Intent(getApplicationContext(), CoockeryListPage.class);
                cookeryListPage.putExtra("type",22);
                cookeryListPage.putExtra("loadtype",0);
                startActivity(cookeryListPage);
            }
        });

        vegan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cookeryListPage = new Intent(getApplicationContext(), CoockeryListPage.class);
                cookeryListPage.putExtra("type",23);
                cookeryListPage.putExtra("loadtype",0);
                startActivity(cookeryListPage);
            }
        });

        soup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cookeryListPage = new Intent(getApplicationContext(), CoockeryListPage.class);
                cookeryListPage.putExtra("type",24);
                cookeryListPage.putExtra("loadtype",0);
                startActivity(cookeryListPage);
            }
        });

        rice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cookeryListPage = new Intent(getApplicationContext(), CoockeryListPage.class);
                cookeryListPage.putExtra("type",25);
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
        searchAdapter.updateOriginals(listOfDishesForSearch);
        searchAdapter.clearData();
        searchAdapter.notifyDataSetChanged();
        setRelativeLayoutHeightBasedOnChildren(rel_search, 0,lv_searchData);
        lv_searchData.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                Intent preparationPage = new Intent(CookeryMain.this, CoockeryPreparation.class);
                ListItemDishes data = (ListItemDishes) lv_searchData.getItemAtPosition(position);
                preparationPage.putExtra("data", data );
                startActivity(preparationPage);
                //EditText searchText = findViewById(R.id.search_query);
                //searchText.setText("");
                searchView.clearFocus();
                searchView.setQuery("", false);
                item.collapseActionView();
            }
        });


        relBreakfast.requestFocus();
        hideSoftKeyboard();


        NavigationView navigation;
        navigation = findViewById(R.id.nav_view);
         View header = navigation.getHeaderView(0);
        // Get Version
        String versionName = "";
        try {
            versionName = getApplicationContext().getPackageManager().getPackageInfo(getApplicationContext().getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        TextView versionname = (TextView) header.findViewById(R.id.nav_version);
        versionname.setText("Version:"+versionName);

        navigation.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                int id = menuItem.getItemId();
                boolean ret;
                switch (id) {
                    case R.id.fav:Intent cookeryListPage = new Intent(getApplicationContext(), CoockeryListPage.class);
                        cookeryListPage.putExtra("loadtype",1);
                        startActivity(cookeryListPage);
                        menuItem.setChecked(false);
                        ret = true;
                        break;
                    case R.id.shopping:
                        ret = true;
                        Intent list = new Intent(getApplicationContext(), CookeryShopList.class );
                        startActivity(list);
                        break;
                    case R.id.Shareapp:
                        ret = false;
                        Intent intentShare = new Intent();
                        intentShare.setAction(Intent.ACTION_SEND);
                        intentShare.putExtra(Intent.EXTRA_TEXT,"I suggest this app for you : https://play.google.com/store/apps/details?id=com.jaapps.cookery");
                        intentShare.setType("text/plain");
                        startActivity(intentShare);
                        break;
                    case R.id.feed:
                        ret = false;
                        Intent intent = new Intent(Intent.ACTION_SEND);
                        intent.setType("text/plain");
                        intent.putExtra(Intent.EXTRA_EMAIL, "arpoapps@gmail.com");
                        intent.putExtra(Intent.EXTRA_SUBJECT, "Feed back For Cookery App");
                        intent.putExtra(Intent.EXTRA_TEXT, "Hello Developer, \n\n\n\n Thanks & Regards,\n");

                        startActivity(Intent.createChooser(intent, "Select Email App"));
                        break;
                    case R.id.licence:
                        ret = false;
                        Intent licence = new Intent(getApplicationContext(), CookeryPrivPolicyLicence.class );
                        licence.putExtra("type",1);
                        startActivity(licence);
                        break;
                    case R.id.privpolicy:
                        Intent privPolicy = new Intent(getApplicationContext(), CookeryPrivPolicyLicence.class);
                        privPolicy.putExtra("type",2);
                        startActivity(privPolicy);
                        ret = false;
                        break;
                    default:
                            ret = false;

                }

                return ret;
            }
        });
    }


    // Search bar Mateerial Design


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater =getMenuInflater();
        inflater.inflate(R.menu.menu_search,menu);
        item=menu.findItem(R.id.menusearch);

         searchView=(SearchView) item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                Intent cookeryListPage = new Intent(getApplicationContext(), CoockeryListPage.class);
                cookeryListPage.putExtra("type",0);
                cookeryListPage.putExtra("loadtype",2); // search
                cookeryListPage.putExtra("search",s); // search
                startActivity(cookeryListPage);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
/*
                String text = s;

                //print(s);
                if( s.length() >= 3 ) {
                    lv_searchData.setAdapter(null);
                    int count = searchAdapter.filter(s);
                    lv_searchData.setAdapter(searchAdapter);
                    setRelativeLayoutHeightBasedOnChildren(rel_search, count, lv_searchData);
                }
                else
                {
                    searchAdapter.clearDataSet();
                    setRelativeLayoutHeightBasedOnChildren(rel_search, 0, lv_searchData);
                }*/
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    //  snack bar
    public static void setSnackBar(View root, String snackTitle) {
        Snackbar snackbar = Snackbar.make(root, snackTitle, BaseTransientBottomBar.LENGTH_SHORT);
        snackbar.show();
        View view = snackbar.getView();
        TextView txtv = (TextView) view.findViewById(android.support.design.R.id.snackbar_text);
        txtv.setGravity(Gravity.CENTER_HORIZONTAL);
    }


    //Network Connection  alert box


    private void isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
if (cm.getActiveNetworkInfo()==null)
{
    AlertDialog.Builder builder = new AlertDialog.Builder(this);

    builder.setTitle("No Internet Connetion");
    builder.setMessage("Please Check Your Internet Connectivity");

    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

        public void onClick(DialogInterface dialog, int which) {
            // Do nothing but close the dialog

            dialog.dismiss();
        }
    });


    AlertDialog alert = builder.create();
    alert.show();
}

    }


    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }

    //Navigation Drawer
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(mtoggle.onOptionsItemSelected(item))
        {
            return true;
        }
        return super.onOptionsItemSelected(item);
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
        Log.d("JKS",str);
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

    public void clearDataSet()
    {
        list.clear();
        notifyDataSetChanged();
    }

    // Filter Class
    public int filter(String charText) {

        list.clear();
        return Globals.sqlData.getSearchData(list, charText);
        /*
        int count = 0;
        charText = charText.toLowerCase(Locale.getDefault());

        long millisStart = Calendar.getInstance().getTimeInMillis();
        if (charText.length() == 3)
        {
            list.clear();
            // print("JKS list item size "+list.size());
            notifyDataSetChanged();
            print("Size = "+origianlList.size());


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
        else
        {
            for (int i = 0; i < list.size(); i++) {
                ListItemDishes data = list.get(i);
                if (!data.getName().toLowerCase(Locale.getDefault()).contains(charText)) {
                    list.remove(data);

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
        long millisEnd = Calendar.getInstance().getTimeInMillis();

        print("Took "+(millisEnd - millisStart) + " milliseconds");

        return  count;*/
    }



    void print(String str)
    {
        Log.d("JKS",str);
    }
}

