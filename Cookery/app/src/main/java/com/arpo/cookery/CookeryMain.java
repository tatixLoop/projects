package com.arpo.cookery;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

public class CookeryMain extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cookery_main);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        RelativeLayout relBreakfast = findViewById(R.id.rel_breakfastRec);
        RelativeLayout relLunch = findViewById(R.id.rel_lunchRec);
        RelativeLayout snckRel = findViewById(R.id.rel_snacksRec);

        String title_image = Globals.host + Globals.appdir + Globals.img_path + "/" +
                "title" + "/1.jpg";

        Runnable imgFetch1 = new DishImageFetcher(title_image, relBreakfast, this);
        new Thread(imgFetch1).start();

        title_image = Globals.host + Globals.appdir + Globals.img_path + "/" +
                "title" + "/2.jpg";

        Runnable imgFetch2 = new DishImageFetcher(title_image, relLunch, this);
        new Thread(imgFetch2).start();

        title_image = Globals.host + Globals.appdir + Globals.img_path + "/" +
                "title" + "/3.jpg";

        Runnable imgFetch3 = new DishImageFetcher(title_image, snckRel, this);
        new Thread(imgFetch3).start();

        relBreakfast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cookeryListPage = new Intent(getApplicationContext(), CoockeryListPage.class);
                cookeryListPage.putExtra("type",1);
                startActivity(cookeryListPage);
            }
        });

        relLunch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cookeryListPage = new Intent(getApplicationContext(), CoockeryListPage.class);
                cookeryListPage.putExtra("type",2);
                startActivity(cookeryListPage);
            }
        });

        snckRel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cookeryListPage = new Intent(getApplicationContext(), CoockeryListPage.class);
                cookeryListPage.putExtra("type",3);
                startActivity(cookeryListPage);
            }
        });
    }
}
