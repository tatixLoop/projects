package com.example.sony.testapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class dbinsertion extends AppCompatActivity implements View.OnClickListener {
//// database insertion activity
    DBconnection db;
    EditText edt_name;
    EditText edt_mobno;
    EditText edt_email;
    Button btn_save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dbinsertion);
        edt_name= (EditText) findViewById(R.id.et_name);
        edt_mobno= (EditText) findViewById(R.id.et_mob);
        edt_email= (EditText) findViewById(R.id.et_email);
        btn_save= (Button) findViewById(R.id.bt_save);
        btn_save.setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        db= new DBconnection(dbinsertion.this);
    }

    @Override
    public void onClick(View v) {

        if (v==btn_save)
        {
            String a=edt_name.getText().toString();
            String b=edt_mobno.getText().toString();
            String c=edt_email.getText().toString();

            edt_name.setText("");
            edt_mobno.setText("");
            edt_email.setText("");

            db.openConnection();
            String st="insert into tb_details(dname,mob,email)values('"+a+"','"+b+"','"+c+"')";
           boolean a1= db.insertData(st);
            if (a1)
            {
                Toast.makeText(this, "Inserted", Toast.LENGTH_SHORT).show();
            }
else
                Toast.makeText(this, "notInserted", Toast.LENGTH_SHORT).show();

        }

    }

    @Override
    protected void onStop() {
        super.onStop();
        db.closeConnection();
    }
}
