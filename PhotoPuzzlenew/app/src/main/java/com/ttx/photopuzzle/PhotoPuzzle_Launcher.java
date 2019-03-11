package com.ttx.photopuzzle;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class PhotoPuzzle_Launcher extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_puzzle__launcher);

        /////    full screen  page with transparent status bar

        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        getWindow().setStatusBarColor(Color.TRANSPARENT);

        /// splash screen code

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent i = new Intent(PhotoPuzzle_Launcher.this, PotoPuzzle_selectlevel.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(i);

            }
        }, 1000);

    }


    @Override
    protected void onStop() {
        super.onStop();
        this.finish();


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        System.exit(0);

    }


}
