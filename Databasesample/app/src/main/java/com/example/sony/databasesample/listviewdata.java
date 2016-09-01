package com.example.sony.databasesample;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class listviewdata extends AppCompatActivity  {
    TextView tv;
    TextView tv1;
    dbconnection db;
    EditText ed_data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_listviewdata);
        tv = (TextView) findViewById(R.id.textView);
        tv1 = (TextView) findViewById(R.id.textView3);
        ed_data= (EditText) findViewById(R.id.tv_enter);

    }
    @Override
protected void onStart(){
super.onStart();
    db = new dbconnection(listviewdata.this);
    db.openConnection();
    String s = "select * from tb_food where food_id=(select max(food_id) from tb_food)";
    Cursor c = db.selectData(s);

    if (c != null) {
        if (c.moveToNext()) {
            String food = c.getString(1);
            String type = c.getString(2);

            tv.setText(food + "  ");
            tv1.setText(type + "  ");

        }

}



    }
}

