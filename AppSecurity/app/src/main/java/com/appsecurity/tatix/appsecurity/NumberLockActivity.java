package com.appsecurity.tatix.appsecurity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.PorterDuff;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class NumberLockActivity extends AppCompatActivity {

    private int mSetLockStage;
    private int mPassCode;
    private boolean updatePasscode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_number_lock);
        TextView txt_title = (TextView)findViewById(R.id.txt_title);
        Button btn_setPin = (Button)findViewById(R.id.btn_setPassCode);

        updatePasscode = false;
        Intent intent = getIntent();
        if(intent.getStringExtra("key").equals("setKey"))
        {
            txt_title.setText("Set Passcode:");
            Cursor c3= MainActivityLockScreen.mdb.rawQuery("SELECT * FROM unlockKey", null);
            if(c3.getCount() != 0)
            {
                while (c3.moveToNext()) {
                    Log.d("JKS","id = "+c3.getInt(0) + " passcode = "+ c3.getInt(1));
                }
                txt_title.setText("Enter Current Passcode:");
                updatePasscode = true;
            }
        }
        else {
            txt_title.setText("");
            btn_setPin.setVisibility(View.GONE);
        }

        mSetLockStage = 0;
        mPassCode = 1234;
        assert btn_setPin != null;
        btn_setPin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                if(mSetLockStage == 0)
                {
                    TextView txt_title = (TextView)findViewById(R.id.txt_title);
                    txt_title.setText("Confirm Passcode:");
                    mSetLockStage = 1;
                    if(updatePasscode == true)
                    {
                        mSetLockStage = 0;
                        txt_title.setText("Set New Passcode:");
                        MainActivityLockScreen.mdb.execSQL("DELETE FROM unlockKey WHERE lockId=0");
                        updatePasscode = false;
                    }
                }
                else if (mSetLockStage == 1)
                {
                    MainActivityLockScreen.mdb.execSQL("UPDATE lockType SET enabled=1 WHERE lockId=0");
                    MainActivityLockScreen.mdb.execSQL("UPDATE lockType SET enabled=0 WHERE lockId<>0");
                    MainActivityLockScreen.mdb.execSQL("DELETE FROM unlockKey WHERE lockId<>0");
                    MainActivityLockScreen.mdb.execSQL("INSERT INTO unlockKey VALUES('0','"+mPassCode+"')");
                    Log.d("JKS","Passcode is updated as "+mPassCode);
                    finish();
                }
            }
        });
        Button btn_1 = (Button) findViewById(R.id.btn_1);
        Button btn_2 = (Button) findViewById(R.id.btn_2);
        Button btn_3 = (Button) findViewById(R.id.btn_3);
        Button btn_4 = (Button) findViewById(R.id.btn_4);
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
