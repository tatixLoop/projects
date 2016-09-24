package com.example.sony.pushupchallenge;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class PushupList extends AppCompatActivity implements AdapterView.OnItemClickListener {


    ListView lv_pushup;
    List<pushupc> list1;


    Databasepushup db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pushup_list);
        lv_pushup = (ListView) findViewById(R.id.lv_excersises);
        list1 = new ArrayList<>();

        db = new Databasepushup(this);
        db.openConnection();
    }


    @Override
    protected void onStart() {
        super.onStart();
        getpushuplist();
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


        String fid = list1.get(position).getId();

        Intent i = new Intent(PushupList.this, PushupDetails.class);
        i.putExtra("fid", fid);
        startActivity(i);

    }


    private void getpushuplist() {

        String se = "select * from tb_pushupdetails";

        Cursor c = db.selectData(se);
        if (c != null) {
            while (c.moveToNext()) {
                Toast.makeText(PushupList.this, "inserted", Toast.LENGTH_SHORT).show();
                String id = c.getString(0);
                String n = c.getString(1);
                int img = c.getInt(2);
                pushupc p1 = new pushupc();
                p1.setName(n);
                p1.setId(id);
                p1.setImg(img);
                list1.add(p1);

            }
        }

        Adapterpushuplist a = new Adapterpushuplist(this, list1);
        lv_pushup.setAdapter(a);
        lv_pushup.setOnItemClickListener(this);

    }







}

