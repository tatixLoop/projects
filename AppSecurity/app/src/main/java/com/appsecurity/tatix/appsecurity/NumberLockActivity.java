package com.appsecurity.tatix.appsecurity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class NumberLockActivity extends AppCompatActivity {

    private int mSetLockStage;
    private boolean updatePasscode;
    String enteredPascode="";
    String newPass = "";
    String passCheck ="";
    TextView txt_enteredPassCode;
    public  String gName;
    public static  String lockedApp ;


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (updatePasscode == false) {
            MainActivityLockScreen.unlockApp(lockedApp);
        }
    }
    @Override
    protected void onStop(){
        super.onStop();


        if(updatePasscode == false && MainActivityLockScreen.getAppStatus(lockedApp)) {

            Intent intent = new Intent(this, NumberLockActivity.class);
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
        setContentView(R.layout.activity_number_lock);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        TextView txt_title = (TextView)findViewById(R.id.txt_title);
        txt_enteredPassCode = (TextView)findViewById(R.id.txtEnteredPasscode);

        Cursor c3= MainActivityLockScreen.mdb.rawQuery("SELECT * FROM unlockKey WHERE lockId='0'", null);

        if(c3.getCount() != 0)
        {
            while (c3.moveToNext()) {
                Log.d("JKS", "id = " + c3.getInt(0) + " passcode = " + c3.getString(1));
                passCheck = c3.getString(1);

            }
        }

        txt_enteredPassCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                Log.d("JKS","Passcode entered is " +enteredPascode);
                if(updatePasscode == false && passCheck.matches(enteredPascode))
                {
                    finish();
                    MainActivityLockScreen.unlockApp(lockedApp);
                }
            }
        });


        Button btn_setPin = (Button)findViewById(R.id.btn_setPassCode);
        Button btn_clr = (Button)findViewById(R.id.btn_clr);
        btn_clr.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                enteredPascode = "";
                txt_enteredPassCode.setText("");
            }
        });

        txt_enteredPassCode.setText("");
        updatePasscode = false;
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

            btn_setPin.setVisibility(View.GONE);

            String name = intent.getStringExtra("key");
            gName = name;
            Log.d("JKS", "app data received is " + name);
            txt_title.setText(gName +" is locked");
            MainActivityLockScreen.lockApp(name);
            try {
                final PackageManager pm = getPackageManager();
                Drawable icon = pm.getApplicationIcon(name);
                ImageView img_icon = (ImageView) findViewById(R.id.img_icon_numLock);
                img_icon.setImageDrawable(icon);
            } catch (Exception ex) {
                Log.d("JKS", "Exceptions CAUGHT in setting app image");
            }

            lockedApp = name;
        }


        assert btn_setPin != null;
        btn_setPin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                if(mSetLockStage == 0)
                {
                    Log.d("JKS","Stage "+mSetLockStage);

                    Cursor c3= MainActivityLockScreen.mdb.rawQuery("SELECT * FROM unlockKey WHERE lockId='0'", null);
                    String passcode ="";
                    if(c3.getCount() != 0)
                    {
                        while (c3.moveToNext()) {
                            Log.d("JKS", "id = " + c3.getInt(0) + " passcode = " + c3.getString(1));
                            passcode = c3.getString(1);

                        }
                    }
                    Log.d("JKS","passcode ="+enteredPascode+" enterefd= "+passcode);
                    if(passcode.matches(enteredPascode) && updatePasscode == true)
                    {
                        TextView txt_title = (TextView)findViewById(R.id.txt_title);
                        mSetLockStage = 1;
                        txt_title.setText("Set New Passcode:");

                        enteredPascode="";
                        txt_enteredPassCode.setText("");
                    }
                    else {
                        enteredPascode="";
                        txt_enteredPassCode.setText("");
                        Log.d("JKS","passcodex ="+enteredPascode+" enterefd= "+passcode);
                        Toast.makeText(getBaseContext(), "Enter the Correct passcode", Toast.LENGTH_SHORT).show();
                    }
                }
                else if(mSetLockStage == 1)
                {
                    TextView txt_title = (TextView)findViewById(R.id.txt_title);
                    Log.d("JKS", "Stage " + mSetLockStage);
                    txt_title.setText("Confirm Passcode:");
                    newPass = enteredPascode;
                    enteredPascode="";
                    txt_enteredPassCode.setText("");
                    mSetLockStage = 2;
                }
                else if (mSetLockStage == 2)
                {
                    Log.d("JKS","Stage "+mSetLockStage);
                    if(newPass.matches(enteredPascode)) {
                        MainActivityLockScreen.mdb.execSQL("UPDATE lockType SET enabled=1 WHERE lockId=0");
                        MainActivityLockScreen.mdb.execSQL("UPDATE lockType SET enabled=0 WHERE lockId<>0");
                        MainActivityLockScreen.mdb.execSQL("UPDATE  unlockKey SET key='" + enteredPascode + "' WHERE lockId=0");
                        Log.d("JKS", "Passcode is updated as " + enteredPascode);
                        finish();
                    }
                    else
                    {
                        Toast.makeText(getBaseContext(), "Wrong passcodes try again", Toast.LENGTH_SHORT).show();
                        TextView txt_title = (TextView)findViewById(R.id.txt_title);
                        mSetLockStage = 0;
                        txt_title.setText("Enter Current Passcode:");
                        enteredPascode="";
                        txt_enteredPassCode.setText("");

                    }
                }
            }
        });
        Button btn_1 = (Button) findViewById(R.id.btninT_1);
        Button btn_2 = (Button) findViewById(R.id.btninT_2);
        Button btn_3 = (Button) findViewById(R.id.btninT_3);
        Button btn_4 = (Button) findViewById(R.id.btninT_4);
        Button btn_5 = (Button) findViewById(R.id.btn_5);
        Button btn_6 = (Button) findViewById(R.id.btn_6);
        Button btn_7 = (Button) findViewById(R.id.btn_7);
        Button btn_8 = (Button) findViewById(R.id.btn_8);
        Button btn_9 = (Button) findViewById(R.id.btn_9);
        Button btn_0 = (Button) findViewById(R.id.btn_0);

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
                        txt_enteredPassCode.setText(txt_enteredPassCode.getText()+"*");
                        view.getBackground().clearColorFilter();
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
                        txt_enteredPassCode.setText(txt_enteredPassCode.getText()+"*");
                        Button view = (Button) v;
                        view.getBackground().clearColorFilter();
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
                        txt_enteredPassCode.setText(txt_enteredPassCode.getText()+"*");
                        view.getBackground().clearColorFilter();
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
                        enteredPascode = enteredPascode +"4";
                        txt_enteredPassCode.setText(txt_enteredPassCode.getText()+"*");
                        view.getBackground().clearColorFilter();
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

        btn_5.setOnTouchListener(new View.OnTouchListener() {

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
                        view.getBackground().clearColorFilter();
                        enteredPascode = enteredPascode +"5";
                        txt_enteredPassCode.setText(txt_enteredPassCode.getText() + "*");
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

        btn_6.setOnTouchListener(new View.OnTouchListener() {

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
                        view.getBackground().clearColorFilter();
                        enteredPascode = enteredPascode +"6";
                        txt_enteredPassCode.setText(txt_enteredPassCode.getText() + "*");
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

        btn_7.setOnTouchListener(new View.OnTouchListener() {

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
                        enteredPascode = enteredPascode +"7";
                        txt_enteredPassCode.setText(txt_enteredPassCode.getText()+"*");
                        Button view = (Button) v;
                        view.getBackground().clearColorFilter();
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

        btn_8.setOnTouchListener(new View.OnTouchListener() {

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
                        view.getBackground().clearColorFilter();
                        enteredPascode = enteredPascode +"8";
                        txt_enteredPassCode.setText(txt_enteredPassCode.getText() + "*");
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

        btn_9.setOnTouchListener(new View.OnTouchListener() {

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
                        enteredPascode = enteredPascode +"9";
                        txt_enteredPassCode.setText(txt_enteredPassCode.getText()+"*");
                        view.getBackground().clearColorFilter();
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

        btn_0.setOnTouchListener(new View.OnTouchListener() {

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
                        enteredPascode = enteredPascode +"0";
                        txt_enteredPassCode.setText(txt_enteredPassCode.getText()+"*");
                        Button view = (Button) v;
                        view.getBackground().clearColorFilter();
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
