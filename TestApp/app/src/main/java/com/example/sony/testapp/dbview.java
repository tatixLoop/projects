package com.example.sony.testapp;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

//// from dbinsertion page
public class dbview extends AppCompatActivity {

    TextView tv;
    DBconnection db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dbview);
        tv=(TextView)findViewById(R.id.tv_details);


    }

    @Override
    protected void onStart() {
        super.onStart();
        db=new DBconnection(dbview.this);
        db.openConnection();
        String s="select * from tb_details where did=(select max(did) from tb_details)";
        Cursor c=db.selectData(s);
        if(c!=null){
            if(c.moveToNext()){
                String name=c.getString(1);
                String mob=c.getString(2);
                String em=c.getString(3);
                tv.setText(name+"  "+mob+"  "+em);

            }
        }
    }
}
