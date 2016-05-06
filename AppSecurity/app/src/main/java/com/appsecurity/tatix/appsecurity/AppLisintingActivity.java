package com.appsecurity.tatix.appsecurity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class AppLisintingActivity extends AppCompatActivity {


    private static Context mContextApplist;
    private static Activity mActivityApplist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_lisinting);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mContextApplist = this;
        mActivityApplist = AppLisintingActivity.this;
        MainActivityLockScreen.appList = (ListView)findViewById(R.id.list_tmp);
        loadAppList();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
/*                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
                finish();
            }
        });
    }
    public static void loadAppList() {

        Log.d("JKS", "for Loading apList appCount = " + MainActivityLockScreen.appCount);

        customList adapter = new
                customList(mActivityApplist,
                            MainActivityLockScreen.mappName,
                            MainActivityLockScreen.imageId,
                            MainActivityLockScreen.drwArray,
                            MainActivityLockScreen.lockStatus,
                            MainActivityLockScreen.appCount);


        MainActivityLockScreen.appList.setAdapter(adapter);
        MainActivityLockScreen.appList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                TextView txV = (TextView) view.findViewById(R.id.txt);
                ImageView img_lock = (ImageView) view.findViewById(R.id.img_lock_state);


                if (!MainActivityLockScreen.isAppLocked(MainActivityLockScreen.mPackageName[+position])) {
                    MainActivityLockScreen.mdb.execSQL("INSERT INTO tileLockApps VALUES('" + MainActivityLockScreen.mPackageName[+position] + "')");
                    img_lock.setImageResource(R.drawable.lock_state);
                    MainActivityLockScreen.addAppToLockedList(MainActivityLockScreen.mPackageName[+position]);
                    MainActivityLockScreen.lockStatus[+position] = 1;
                } else {
                    MainActivityLockScreen.mdb.execSQL("DELETE FROM tileLockApps WHERE package ='" + MainActivityLockScreen.mPackageName[+position] + "'");
                    img_lock.setImageResource(R.drawable.unlock_state);
                    MainActivityLockScreen.removeAppFromLockedList(MainActivityLockScreen.mPackageName[+position]);
                    MainActivityLockScreen.lockStatus[+position] = 0;
                }

            }
        });
    }
}
