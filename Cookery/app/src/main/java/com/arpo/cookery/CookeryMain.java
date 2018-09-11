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
