package com.example.sony.testapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class addressdetailsusingintent extends AppCompatActivity implements View.OnClickListener{


    EditText edt_name;
    EditText edt_age;
    EditText edt_address;
    EditText edt_mobno;
    EditText edt_email;
    Button btn_sv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addressdetailsusingintent);

        edt_name= (EditText) findViewById(R.id.et_name);
        edt_age= (EditText) findViewById(R.id.et_age);
        edt_address= (EditText) findViewById(R.id.et_address);
        edt_mobno= (EditText) findViewById(R.id.et_mobno);
        edt_email= (EditText) findViewById(R.id.et_emailno);
        btn_sv= (Button) findViewById(R.id.bt_submit);
        btn_sv.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {

        String a=edt_name.getText().toString();
        String b=edt_age.getText().toString();
        String c=edt_address.getText().toString();
        String d=edt_mobno.getText().toString();
        String e=edt_email.getText().toString();

        edt_name.setText("");
        edt_age.setText("");
        edt_address.setText("");
        edt_mobno.setText("");
        edt_email.setText("");

        if (v==btn_sv)
        {

            Intent i=new Intent(addressdetailsusingintent.this,viewintent.class);
            //i.putExtra("print",a+b+c+d+e);
            i.putExtra("name",a);
            i.putExtra("age",b);
            i.putExtra("Address",c);
            i.putExtra("mobno",d);
            i.putExtra("email",e);
            startActivity(i);
        }
    }
}
