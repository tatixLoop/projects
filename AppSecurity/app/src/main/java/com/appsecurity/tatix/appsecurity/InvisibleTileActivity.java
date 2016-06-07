package com.appsecurity.tatix.appsecurity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class InvisibleTileActivity extends AppCompatActivity {


    public String passCheck="";
    public  int mSetLockStage = 0;
    public  boolean updatePasscode = false;
    public String gName;
    public String lockedApp ;
    Button btn_change;
    public String enteredPascode="";
    public String newPass="";

    public static int clicks =6;
    public int clickCount = 0;


    @Override
    public void onDestroy() {
        if (updatePasscode == false) {
            MainActivityLockScreen.unlockApp(lockedApp);
        }

        super.onDestroy();
    }
    @Override
    protected void onStop(){
        super.onStop();

        if(updatePasscode == false && MainActivityLockScreen.getAppStatus(lockedApp)) {


            Intent intent = new Intent(this, InvisibleTileActivity.class);
            Log.d("JKS", "Reloading lockscreen for " + gName);
            intent.putExtra("key", gName); //Optional parameters
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_USER_ACTION |
                    Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS |
                    Intent.FLAG_ACTIVITY_NO_HISTORY);

            startActivity(intent);
        }

    }
    @Override
    public void onBackPressed() {

        Log.d("JKS","back button pressed and nothing happend");
        return;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invisible_tile);

        //hide the top action bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        TextView txt_title = (TextView)findViewById(R.id.txt_title);
        btn_change = (Button)findViewById(R.id.btn_ok);
        clickCount = 0;

        //GET THE DEFAULT PASSCODE
        Cursor c3= MainActivityLockScreen.mdb.rawQuery("SELECT * FROM unlockKey WHERE lockId='1'", null);
        if(c3.getCount() != 0)
        {
            while (c3.moveToNext()) {
                Log.d("JKS", "id = " + c3.getInt(0) + " passcode = " + c3.getString(1));
                passCheck = c3.getString(1);

            }
        }


        // PARSE INTENT TO FINDOUT WHAT TO DO ; lock screen or change passcode
        Intent intent = getIntent();
        if(intent.getStringExtra("key").equals("setKey"))
        {
            txt_title.setText("Enter Current Passcode:");
            if(intent.getStringExtra("isFirstSetup").equals("yes"))
            {
                mSetLockStage = 1;
                txt_title.setText("Set Passcode:");
            }
            else mSetLockStage = 0;
            updatePasscode = true;
        }
        else {

            updatePasscode = false;

            btn_change.setVisibility(View.GONE);

            String name = intent.getStringExtra("key");
            gName = name;
            Log.d("JKS", "app data received is " + name);
            txt_title.setText(gName +" is locked");
            MainActivityLockScreen.lockApp(name);
            try {
                final PackageManager pm = getPackageManager();
                Drawable icon = pm.getApplicationIcon(name);
                ImageView img_icon = (ImageView) findViewById(R.id.img_icon_inTIleLock);
                img_icon.setImageDrawable(icon);
            } catch (Exception ex) {
                Log.d("JKS", "Exceptions CAUGHT in setting app image");
            }

            lockedApp = name;
        }

        // BUTTON OK CODE
        btn_change.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                clickCount = 0;
                if (mSetLockStage == 0) {
                    Log.d("JKS", "Stage " + mSetLockStage);

                    if (passCheck.matches(enteredPascode) && updatePasscode == true) {
                        TextView txt_title = (TextView) findViewById(R.id.txt_title);
                        mSetLockStage = 1;
                        txt_title.setText("Set New Passcode:");

                        enteredPascode = "";
                    } else {
                        enteredPascode = "";
                        Toast.makeText(getBaseContext(), "Enter the Correct passcode", Toast.LENGTH_SHORT).show();
                    }
                } else if (mSetLockStage == 1) {
                    TextView txt_title = (TextView) findViewById(R.id.txt_title);
                    Log.d("JKS", "Stage " + mSetLockStage);
                    txt_title.setText("Confirm Passcode:");
                    newPass = enteredPascode;
                    enteredPascode = "";
                    mSetLockStage = 2;
                } else if (mSetLockStage == 2) {
                    Log.d("JKS", "Stage " + mSetLockStage);
                    if (newPass.matches(enteredPascode)) {
                        MainActivityLockScreen.mdb.execSQL("UPDATE lockType SET enabled=1 WHERE lockId=1");
                        MainActivityLockScreen.mdb.execSQL("UPDATE lockType SET enabled=0 WHERE lockId<>1");
                        MainActivityLockScreen.mdb.execSQL("UPDATE  unlockKey SET key='" + enteredPascode + "' WHERE lockId=1");
                        Log.d("JKS", "Passcode is updated as " + enteredPascode);
                        finish();
                    } else {
                        Toast.makeText(getBaseContext(), "Wrong passcodes try again", Toast.LENGTH_SHORT).show();
                        TextView txt_title = (TextView) findViewById(R.id.txt_title);
                        mSetLockStage = 0;
                        txt_title.setText("Enter Current Passcode:");
                        enteredPascode = "";

                    }
                }
            }
        });

        Button btn_1 = (Button)findViewById(R.id.btninT_1);
        Button btn_2 = (Button)findViewById(R.id.btninT_2);
        Button btn_3 = (Button)findViewById(R.id.btninT_3);
        Button btn_4 = (Button)findViewById(R.id.btninT_4);

        btn_1.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        Button view = (Button) v;
                        view.getBackground().setColorFilter(0x77000000, PorterDuff.Mode.SRC_ATOP);
                        v.invalidate();
                        break;
                    }
                    case MotionEvent.ACTION_UP: {
                        Button view = (Button) v;
                        enteredPascode = enteredPascode +"1";
                        Log.d("JKS","entered = "+enteredPascode);
                        view.getBackground().clearColorFilter();
                        if(clicks == clickCount)
                        {
                            clickCount = 0;
                            Toast.makeText(getBaseContext(), "Invalid Pattern Try again", Toast.LENGTH_SHORT).show();
                            enteredPascode="";
                            break;
                        }
                        clickCount++;
                        if(!updatePasscode && passCheck.matches(enteredPascode))
                        {
                            finish();
                        }
                        break;
                    }
                    case MotionEvent.ACTION_CANCEL: {
                        Button view = (Button) v;
                        view.getBackground().clearColorFilter();
                        view.invalidate();
                        break;
                    }
                }
                return true;
            }
        });

        btn_2.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        Button view = (Button) v;
                        view.getBackground().setColorFilter(0x77000000, PorterDuff.Mode.SRC_ATOP);
                        v.invalidate();
                        break;
                    }
                    case MotionEvent.ACTION_UP: {
                        enteredPascode = enteredPascode +"2";
                        Log.d("JKS","entered = "+enteredPascode);
                        Button view = (Button) v;
                        view.getBackground().clearColorFilter();
                        if(clicks == clickCount)
                        {
                            clickCount = 0;
                            Toast.makeText(getBaseContext(), "Invalid Pattern Try again", Toast.LENGTH_SHORT).show();
                            enteredPascode="";
                            break;
                        }
                        clickCount++;
                        if(!updatePasscode && passCheck.matches(enteredPascode))
                        {
                            finish();
                        }
                        break;
                    }
                    case MotionEvent.ACTION_CANCEL: {
                        Button view = (Button) v;
                        view.getBackground().clearColorFilter();
                        view.invalidate();
                        break;
                    }
                }
                return true;
            }
        });

        btn_3.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        Button view = (Button) v;
                        view.getBackground().setColorFilter(0x77000000, PorterDuff.Mode.SRC_ATOP);
                        v.invalidate();
                        break;
                    }
                    case MotionEvent.ACTION_UP: {
                        Button view = (Button) v;
                        enteredPascode = enteredPascode +"3";
                        Log.d("JKS","entered = "+enteredPascode);
                        view.getBackground().clearColorFilter();
                        if(clicks == clickCount)
                        {
                            clickCount = 0;
                            Toast.makeText(getBaseContext(), "Invalid Pattern Try again", Toast.LENGTH_SHORT).show();
                            enteredPascode="";
                            break;
                        }
                        clickCount++;
                        if(!updatePasscode && passCheck.matches(enteredPascode))
                        {
                            finish();
                        }
                        break;
                    }
                    case MotionEvent.ACTION_CANCEL: {
                        Button view = (Button) v;
                        view.getBackground().clearColorFilter();
                        view.invalidate();
                        break;
                    }
                }
                return true;
            }
        });

        btn_4.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        Button view = (Button) v;
                        view.getBackground().setColorFilter(0x77000000, PorterDuff.Mode.SRC_ATOP);
                        v.invalidate();
                        break;
                    }
                    case MotionEvent.ACTION_UP: {
                        Button view = (Button) v;
                        enteredPascode = enteredPascode + "4";
                        Log.d("JKS","entered = "+enteredPascode);
                        view.getBackground().clearColorFilter();
                        if(clicks == clickCount)
                        {
                            clickCount = 0;
                            Toast.makeText(getBaseContext(), "Invalid Pattern Try again", Toast.LENGTH_SHORT).show();
                            enteredPascode="";
                            break;
                        }
                        if(!updatePasscode && passCheck.matches(enteredPascode))
                        {
                            finish();
                        }
                        break;
                    }
                    case MotionEvent.ACTION_CANCEL: {
                        Button view = (Button) v;
                        view.getBackground().clearColorFilter();
                        view.invalidate();
                        break;
                    }
                }
                return true;
            }
        });

    }
}
