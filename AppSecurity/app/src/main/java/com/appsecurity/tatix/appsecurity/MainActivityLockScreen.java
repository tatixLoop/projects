package com.appsecurity.tatix.appsecurity;

import android.graphics.PorterDuff;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivityLockScreen extends AppCompatActivity {

    public boolean appSecurity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activity_lock_screen);
        setTitle("Tatix App Security");

        /* get the status from database TODO*/
        appSecurity = false;
        /* adding button listners */
        Button btnLockSel = (Button) findViewById(R.id.btn_selectLock);
        Button btnappsel = (Button) findViewById(R.id.btn_selectApps);
        Button btntoggle = (Button) findViewById(R.id.btn_securityToggle);
        btntoggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v("JKS", "Onclick enable or disabled lock security");
                ImageView lockStatImage = (ImageView) findViewById(R.id.img_status);
                Button btnLockSel = (Button) findViewById(R.id.btn_securityToggle);
                TextView txtAppStat = (TextView) findViewById(R.id.txtView_secureStat);
                if(appSecurity == false)
                {
                    appSecurity = true;

                    btnLockSel.setText("Disable app security");
                    lockStatImage.setImageResource(R.drawable.applocksecure);
                    txtAppStat.setText("Your apps are secure");
                    /* TODO update database */
                }
                else
                {
                    btnLockSel.setText("Enable App Security");
                    txtAppStat.setText("Your Apps are not secure");
                    appSecurity = false;
                    lockStatImage.setImageResource(R.drawable.applocknotsecure);
                    /* TODO update database */
                }
            }
        });

        btntoggle.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        Button view = (Button) v;
                        view.getBackground().setColorFilter(0x77000000, PorterDuff.Mode.SRC_ATOP);
                        v.invalidate();
                        break;
                    }
                    case MotionEvent.ACTION_UP:
                        // Your action here on button click
                        ImageView lockStatImage = (ImageView) findViewById(R.id.img_status);
                        Button btnLockSel = (Button) findViewById(R.id.btn_securityToggle);
                        TextView txtAppStat = (TextView) findViewById(R.id.txtView_secureStat);
                        if (appSecurity == false) {
                            appSecurity = true;

                            btnLockSel.setText("Disable app security");
                            lockStatImage.setImageResource(R.drawable.applocksecure);
                            txtAppStat.setText("Your apps are secure");
                    /* TODO update database */
                        } else {
                            btnLockSel.setText("Enable App Security");
                            txtAppStat.setText("Your Apps are not secure");
                            appSecurity = false;
                            lockStatImage.setImageResource(R.drawable.applocknotsecure);
                    /* TODO update database */
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
        btnappsel.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        Button view = (Button) v;
                        view.getBackground().setColorFilter(0x77000000, PorterDuff.Mode.SRC_ATOP);
                        v.invalidate();
                        break;
                    }
                    case MotionEvent.ACTION_UP:
                        // Your action here on button click
                        Toast.makeText(getApplicationContext(), "App selection is yet to implement",
                                Toast.LENGTH_SHORT).show();
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

        btnLockSel.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        Button view = (Button) v;
                        view.getBackground().setColorFilter(0x77000000, PorterDuff.Mode.SRC_ATOP);
                        v.invalidate();
                        break;
                    }
                    case MotionEvent.ACTION_UP:
                        // Your action here on button click
                        Toast.makeText(getApplicationContext(), "lock selection is yet to implement",
                                Toast.LENGTH_SHORT).show();
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
