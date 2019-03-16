package com.ttx.photopuzzle;

import android.content.Context;
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

import static java.lang.System.exit;
import static java.security.AccessController.getContext;

public class PotoPuzzle_selectlevel extends AppCompatActivity {


    ImageView imv_two,imv_three,imv_four,imv_five;
    Button bt_gameselect;
    Button bt_exitapp;
    Button bt_grade;
    Button bt_startgame;
    int selectedgrid = 0;
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
        bt_exitapp=findViewById(R.id.btn_exit);
        bt_grade=findViewById(R.id.btn_trophy);

        bt_gameselect= (Button) findViewById(R.id.btn_gallery);
        bt_gameselect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(PotoPuzzle_selectlevel.this,PhotoPuzzle_CustomPuzzle.class);
                startActivity(i);

            }
        });

        bt_startgame=(Button) findViewById(R.id.btn_start);
        bt_startgame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selectedgrid==0){
                    Toast.makeText(getApplicationContext(), "Select the puzzle level", Toast.LENGTH_SHORT).show();
                }
                else if(selectedgrid==2){

                    Intent i=new Intent(PotoPuzzle_selectlevel.this,PhotoPuzzle_Selected.class);
                    i.putExtra("puzzleselected",2);
                    startActivity(i);
                }
                else if(selectedgrid==3){

                    Intent i=new Intent(PotoPuzzle_selectlevel.this,PhotoPuzzle_Selected.class);
                    i.putExtra("puzzleselected",3);
                    startActivity(i);
                }

                else if(selectedgrid==4){

                    Intent i=new Intent(PotoPuzzle_selectlevel.this,PhotoPuzzle_Selected.class);
                    i.putExtra("puzzleselected",4);
                    startActivity(i);
                }
                else if(selectedgrid==5){

                    Intent i=new Intent(PotoPuzzle_selectlevel.this,PhotoPuzzle_Selected.class);
                    i.putExtra("puzzleselected",5);
                    startActivity(i);
                }
            }
        });


        imv_two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                selectedgrid = 2;

                imv_two.setImageDrawable(getResources().getDrawable(R.drawable.twosquare_selected));
                imv_three.setImageDrawable(getResources().getDrawable(R.drawable.threegamepuzzle));
                imv_four.setImageDrawable(getResources().getDrawable(R.drawable.fourgamepuzzle));
                imv_five.setImageDrawable(getResources().getDrawable(R.drawable.fivegamepuzzle));

            }
        });imv_three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedgrid = 3;

                imv_two.setImageDrawable(getResources().getDrawable(R.drawable.twogamepuzzle));
                imv_three.setImageDrawable(getResources().getDrawable(R.drawable.threequare_selected));
                imv_four.setImageDrawable(getResources().getDrawable(R.drawable.fourgamepuzzle));
                imv_five.setImageDrawable(getResources().getDrawable(R.drawable.fivegamepuzzle));
            }
        });
        imv_four.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedgrid = 4;
                imv_two.setImageDrawable(getResources().getDrawable(R.drawable.twogamepuzzle));
                imv_three.setImageDrawable(getResources().getDrawable(R.drawable.threegamepuzzle));
                imv_four.setImageDrawable(getResources().getDrawable(R.drawable.foursquare_selected));
                imv_five.setImageDrawable(getResources().getDrawable(R.drawable.fivegamepuzzle));
            }
        });
        imv_five.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedgrid = 5;
                imv_two.setImageDrawable(getResources().getDrawable(R.drawable.twogamepuzzle));
                imv_three.setImageDrawable(getResources().getDrawable(R.drawable.threegamepuzzle));
                imv_four.setImageDrawable(getResources().getDrawable(R.drawable.fourgamepuzzle));
                imv_five.setImageDrawable(getResources().getDrawable(R.drawable.fivesquare_selected));
            }
        });

        bt_grade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getApplicationContext(), "your grade is nill", Toast.LENGTH_SHORT).show();
            }
        });

        bt_exitapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
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
