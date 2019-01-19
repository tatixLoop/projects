package com.example.sony.pushupchallenge;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

public class Pushupchallenge_home extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    Button btnchallenge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_pushupchallenge_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        btnchallenge  =(Button) findViewById(R.id.btn_gotochallenge);

        btnchallenge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(Pushupchallenge_home.this,Pushupchallenge_challenge.class);
                startActivity(i);


            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

            }
        });


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


    }



    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.pushupchallenge_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_workoutchart) {
            // Handle the camera action
            Intent workoutchart=new Intent(this,Pushupchallenge_workoutchart.class);
            startActivity(workoutchart);

        } else if (id == R.id.nav_howtotakepushup)
        {
            Intent howtotakepushup=new Intent(this,Pushupchallenge_howtotakepushup.class);
            startActivity(howtotakepushup);

        } else if (id == R.id.nav_challengestatus)
        {
            Intent challengestatus=new Intent(this,Pushupchallenge_challengestatus.class);
            startActivity(challengestatus);

        } else if (id == R.id.nav_invitechallenge) {
            Intent intentShare = new Intent();
            intentShare.setAction(Intent.ACTION_SEND);
            intentShare.putExtra(Intent.EXTRA_TEXT,"I suggest this app for you : https://play.google.com/store/apps/details?id=com.android.chrome");
            intentShare.setType("text/plain");
            startActivity(intentShare);

        } else if (id == R.id.nav_feedback) {
            Intent intentfeedback = new Intent();
            intentfeedback.setAction(Intent.ACTION_SEND);
            intentfeedback.putExtra(Intent.EXTRA_TEXT,"I suggest this app for you : https://play.google.com/store/apps/details?id=com.android.chrome");
            intentfeedback.setType("text/plain");
            startActivity(intentfeedback);

        } else if (id == R.id.nav_policy) {
            Intent privacypolicy =new Intent(this,Pushupchallenge_privacy.class);
            startActivity(privacypolicy);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
