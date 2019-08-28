package com.jaapps.drinkrecipes;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.ads.MobileAds;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;


public class CookeryMain extends AppCompatActivity {

    List<ListItemDishes>  listOfDishesForSearch;
    JSONParser            jParser = new JSONParser();
    JSONArray             dishSubTypeArray;
    SearchView            searchView;
    MenuItem              item;
    boolean               doubleBackToExitPressedOnce = false;
    long                  cmtype;
    List<ListSubCatagory> subCataList;
    AdapterCatagoryList   adaptorCata;


    ///////////     navigation Drawer
    private ActionBarDrawerToggle mToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        
        DrawerLayout mdrawerLayout;
        
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navigationdrawer);
        
        subCataList = new ArrayList<>();

        cmtype = getIntent().getLongExtra("type", -1);
        GetSubCatagory thread = new GetSubCatagory();
        new Thread(thread).start();


        ListView lv_subCata = findViewById(R.id.listSubCata);
        adaptorCata         = new AdapterCatagoryList(this, subCataList);
        lv_subCata.setAdapter(adaptorCata);

        lv_subCata.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                Intent cookeryListPage = new Intent(getApplicationContext(), CoockeryListPage.class);
                cookeryListPage.putExtra("type", Globals.g_type);
                cookeryListPage.putExtra("subtype", subCataList.get(position).getSubcatagory());
                cookeryListPage.putExtra("loadtype", 3);
                cookeryListPage.putExtra("url", subCataList.get(position).getBgUrl());
                cookeryListPage.putExtra("title", subCataList.get(position).getText());

                startActivity(cookeryListPage);
            }
        });

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        // initialize the AdMob app
        MobileAds.initialize(this, getString(R.string.admob_app_id));
        ///Navigation Drawer


        mdrawerLayout = findViewById(R.id.drawer_layout);
        mToggle       = new ActionBarDrawerToggle(this, mdrawerLayout, R.string.open, R.string.close);
        mdrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        isNetworkConnected();  //tocheck internet is available or not

        // Fill in search data
        listOfDishesForSearch = new ArrayList<>();

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
        TextView versionname = header.findViewById(R.id.nav_version);
        String version = "Version :"+ versionName;

        versionname.setText(version);

        navigation.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                boolean ret = false;
                if (menuItem != null) {
                    int id = menuItem.getItemId();
                    switch (id) {
                        case R.id.fav:
                            Intent cookeryListPage = new Intent(getApplicationContext(), CoockeryListPage.class);
                            cookeryListPage.putExtra("loadtype", 1);
                            startActivity(cookeryListPage);
                            menuItem.setChecked(false);
                            ret = true;
                            break;
                        case R.id.shopping:
                            ret = true;
                            Intent list = new Intent(getApplicationContext(), CookeryShopList.class);
                            startActivity(list);
                            break;
                        case R.id.Shareapp:
                            ret = false;
                            Intent intentShare = new Intent();
                            intentShare.setAction(Intent.ACTION_SEND);
                            intentShare.putExtra(Intent.EXTRA_TEXT, "I suggest this app for you : https://play.google.com/store/apps/details?id=com.jaapps.cookery");
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
                            Intent licence = new Intent(getApplicationContext(), CookeryPrivPolicyLicence.class);
                            licence.putExtra("type", 1);
                            startActivity(licence);
                            break;
                        case R.id.privpolicy:
                            Intent privPolicy = new Intent(getApplicationContext(), CookeryPrivPolicyLicence.class);
                            privPolicy.putExtra("type", 2);
                            startActivity(privPolicy);
                            ret = false;
                            break;
                        default:
                            ret = false;
                    }
                }
                return ret;
            }
        });
    }
    
    // Search bar Mateerial Design
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search, menu);
        item = menu.findItem(R.id.menusearch);

        searchView = (SearchView) item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                Intent cookeryListPage = new Intent(getApplicationContext(), CoockeryListPage.class);
                cookeryListPage.putExtra("type", 0);
                cookeryListPage.putExtra("loadtype", 2); // search
                cookeryListPage.putExtra("search", s); // search
                startActivity(cookeryListPage);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }
        //Network Connection  alert box


    private void isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm.getActiveNetworkInfo() == null) {
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
            Globals.runFlag = false;
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }

    //Navigation Drawer
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void hideSoftKeyboard() {
        View view = getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    void print(String str) {
        Log.d("JKS", str);
    }

    class GetSubCatagory implements Runnable {

        @Override
        public void run() {
            String apiname;
            String param;
            apiname      = "getSubTypes.php";
            param        = "type="+cmtype;

            JSONObject json = jParser.makeHttpRequest(Globals.host+Globals.appdir+Globals.apipath+apiname,
                    "GET", param);

            if(json != null) {
                try {
                    int success = json.getInt("success");
                    if (success == 1) {
                        dishSubTypeArray = json.getJSONArray("dishsubtypes");
                        for (int i = 0; i < dishSubTypeArray.length(); i++) {

                            JSONObject c = dishSubTypeArray.getJSONObject(i);
                            int subtype = c.getInt("subtype");
                            String subTypeName = c.getString("subtypename");
                            if (!checksubTypePresent(subCataList, subtype)) {
                                String imgUrl = Globals.host + Globals.appdir + Globals.img_path + "/a.subtype/"+Globals.g_subcata_img_dir+"/" + subtype + ".jpg";
                                ListSubCatagory subItem = new ListSubCatagory(
                                        cmtype, subtype, imgUrl, subTypeName);
                                subItem.setType(0);
                                subCataList.add(subItem);

                                Globals.sqlData.instertIntoTypes(cmtype, subtype, "", subTypeName);
                            }
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

            runOnUiThread(new Runnable() {
                public void run() {
                    adaptorCata.notifyDataSetChanged();
                }
            });
        }
    }

    public boolean checksubTypePresent(List<ListSubCatagory> list, int subtype)
    {
        for (ListSubCatagory item: list
             ) {
            if( item.getSubcatagory() == subtype)
            {
                return  true;
            }
        }
        return  false;
    }
}

