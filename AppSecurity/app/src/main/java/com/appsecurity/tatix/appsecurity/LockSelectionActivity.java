package com.appsecurity.tatix.appsecurity;

import android.database.Cursor;
import android.graphics.PorterDuff;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

public class LockSelectionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lock_selection);
        setTitle("Tatix App Security");

        Button btn_numLock = (Button)findViewById(R.id.btn_lockNumber);
        Button btn_tilesLock = (Button)findViewById(R.id.btn_lockTiles);

        Cursor c3 = MainActivityLockScreen.mdb.rawQuery("SELECT * FROM lockType", null);
        if(c3.getCount() != 0)
        {
            while (c3.moveToNext()) {
                Log.d("JKS", "lock id = " + c3.getInt(0) + " enabled = " + c3.getInt(1));
                switch(c3.getInt(0))
                {
                    case 0:
                        if(c3.getInt(1) == 1)
                            btn_numLock.setBackgroundResource(R.drawable.selectednumlock);
                        break;
                    case 1:
                        if(c3.getInt(1) == 1)
                            btn_tilesLock.setBackgroundResource(R.drawable.selectedtilelock);
                        break;
                }
            }
        }

        btn_numLock.setOnTouchListener(new View.OnTouchListener() {

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
                        view.setBackgroundResource(R.drawable.selectednumlock);
                        Button btn_tiles = (Button) findViewById(R.id.btn_lockTiles);
                        btn_tiles.setBackgroundResource(R.drawable.invisible_tiles);
                        MainActivityLockScreen.mdb.execSQL("UPDATE lockType SET enabled=1 WHERE lockId=0");
                        MainActivityLockScreen.mdb.execSQL("UPDATE lockType SET enabled=0 WHERE lockId<>0");
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

        btn_tilesLock.setOnTouchListener(new View.OnTouchListener() {

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
                        view.setBackgroundResource(R.drawable.selectedtilelock);
                        Button btn_tiles = (Button) findViewById(R.id.btn_lockNumber);
                        btn_tiles.setBackgroundResource(R.drawable.numlock);
                        MainActivityLockScreen.mdb.execSQL("UPDATE lockType SET enabled=1 WHERE lockId=1");
                        MainActivityLockScreen.mdb.execSQL("UPDATE lockType SET enabled=0 WHERE lockId<>1");
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
