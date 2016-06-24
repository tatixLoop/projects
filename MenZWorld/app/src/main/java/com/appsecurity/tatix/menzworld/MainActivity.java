package com.appsecurity.tatix.menzworld;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class MainActivity extends AppCompatActivity {

    static SQLiteDatabase mdb;

    public static int SHIRT = 0;
    public static int JEANS = 1;
    public static int PANTS = 3;
    public static int TSHIRTS = 4;
    public static int BELTS = 5;
    public static int INNERWAER = 6;
    public static int SHORTS = 7;
    public static int WALLET = 8;
    public static int OTHERS = 9;

    public static int SZ_SMALL = 0;
    public static int SZ_MEDIUM = 1;
    public static int SZ_LARGE = 2;
    public static int SZ_XLARGE = 3;
    public static int SZ_XXLARGE= 4;


    public static int serialNumber=0000;
    public static int billRefId = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);

        Cursor c = mdb.rawQuery("SELECT MAX(serialNumber) FROM stockData", null);
        if(c.getCount() == 1) {
            c.moveToNext();

            serialNumber = c.getInt(0);
        }
        else serialNumber = 999;
        if(serialNumber <= 999) serialNumber = 999;

        Cursor c2 = mdb.rawQuery("SELECT MAX(refId) FROM billTable", null);
        if(c2.getCount() == 1) {
            c2.moveToNext();

            billRefId = c2.getInt(0) + 1;
        }
        else billRefId = 1;

        Button btn_scan =(Button)findViewById(R.id.btn_scan);

        btn_scan.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent = new Intent(MainActivity.this, MakeBillActivity.class);
                        startActivity(intent);
                    }
                });

        Button btn_add = (Button)findViewById(R.id.btn_addItem);
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, AddItemActivity.class);
                startActivity(intent);
            }
        });

        Button btn_db = (Button)findViewById(R.id.btn_database);
        btn_db.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, ListAllItems.class);
                startActivity(intent);
            }
        });

        Button btn_bkup = (Button)findViewById(R.id.btn_bkup);
        btn_bkup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, BackUpRestore.class);
                startActivity(intent);
            }
        });

        Button btn_viewBill = (Button)findViewById(R.id.btn_viewBill);
        btn_viewBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, ViewBillActivity.class);
                startActivity(intent);
            }
        });

        Button btn_reports = (Button)findViewById(R.id.btn_reports);
        btn_reports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, ReportActivity.class);
                startActivity(intent);
            }
        });

        Button btn_change_pass = (Button)findViewById(R.id.btn_change_password);
        btn_change_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, ActivityChangePassword.class);
                startActivity(intent);
            }
        });

    }
}
