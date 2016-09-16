package com.example.sony.pushupchallenge;

import android.content.Context;
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
    // List<Integer> img;
    Databasepushup db;
    //Context con;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pushup_list);
        lv_pushup = (ListView) findViewById(R.id.lv_excersises);
        list1 = new ArrayList<>();
        String sel = "select excersisename from tb_pushupdetails";
        db = new Databasepushup(this);
        db.openConnection();

        Cursor c = db.selectData(sel);
        if (c != null) {
            while (c.moveToNext()) {
                Toast.makeText(PushupList.this, "inserted", Toast.LENGTH_SHORT).show();
                String id = c.getString(0);
                String n = c.getString(1);
                pushupc p1 = new pushupc();
                p1.setName(n);
                p1.setId(id);
                list1.add(p1);

            }

            Adapterpushuplist a = new Adapterpushuplist(this, list1);
            lv_pushup.setAdapter(a);
            lv_pushup.setOnItemClickListener(this);


        }

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}
