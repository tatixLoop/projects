package com.ttx.photopuzzle;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;

import static java.security.AccessController.getContext;

public class PotoPuzzle_selectlevel extends AppCompatActivity {


    ImageView imv_two,imv_three,imv_four,imv_five;
    Button bt_gameselect;
  // PhotoPuzzle_Data db;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poto_puzzle_selectlevel);

        /////    full screen  page with transparent status bar

        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        getWindow().setStatusBarColor(Color.TRANSPARENT);

imv_two= (ImageView) findViewById(R.id.two);
        imv_three= (ImageView) findViewById(R.id.three);
        imv_four= (ImageView) findViewById(R.id.four);
        imv_five= (ImageView) findViewById(R.id.five);

        bt_gameselect= (Button) findViewById(R.id.btn_selectlevel);
        bt_gameselect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(PotoPuzzle_selectlevel.this,PhotoPuzzle_CustomPuzzle.class);
                startActivity(i);

            }
        });

        imv_two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i= new Intent(PotoPuzzle_selectlevel.this,PhotoPuzzle_Selected.class);
               // i.putExtra("puzzleselected","1");
                i.putExtra("puzzleselected",2);
                startActivity(i);

            }
        });imv_three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(PotoPuzzle_selectlevel.this,PhotoPuzzle_Selected.class);
                i.putExtra("puzzleselected",3);
                startActivity(i);
            }
        });
        imv_four.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(PotoPuzzle_selectlevel.this,PhotoPuzzle_Selected.class);
                i.putExtra("puzzleselected",4);
                startActivity(i);
            }
        });
        imv_five.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(PotoPuzzle_selectlevel.this,PhotoPuzzle_Selected.class);
                i.putExtra("puzzleselected",5);
                startActivity(i);
            }
        });

    }


   


    //// press bouble tap to exit  from app

    boolean doubleBackToExitPressedOnce = false;
    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new android.os.Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }

}
