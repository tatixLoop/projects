package com.example.sony.testapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Databaseeg2 extends AppCompatActivity implements View.OnClickListener{
    EditText et_num1;
    EditText et_num2;
    TextView tv_res;
    Button bt_Next;
    Button bt_Add;
    Button bt_SAve;
DBconnection obj1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_databaseeg2);
       et_num1= (EditText) findViewById(R.id.et_num1);
        et_num2= (EditText) findViewById(R.id.et_num2);
        tv_res= (TextView) findViewById(R.id.tv_res);
        bt_Add= (Button) findViewById(R.id.bt_ADD);
        bt_Next= (Button) findViewById(R.id.bt_next);
        bt_SAve= (Button) findViewById(R.id.bt_SAVE);
        bt_Add.setOnClickListener(this);
        bt_Next.setOnClickListener(this);
        bt_SAve.setOnClickListener(this);

    }

    @Override
    protected void onStart() {
        super.onStart();
        obj1=new DBconnection(Databaseeg2.this);
        obj1.openConnection();
    }

    @Override
    public void onClick(View v) {
        String a=et_num1.getText().toString();
        String b=et_num2.getText().toString();
        int c=0,d=0;
        if(a.equals("")&&b.equals("")) {
             c = Integer.parseInt(a);
             d = Integer.parseInt(b);
        }
        int e=c+d;




        if (v==bt_Add)
        {

            tv_res.setText(e+"");
        }
        else if (v==bt_Next)

        {
            createNotificn();
            ///// dialogue box
            new AlertDialog.Builder(Databaseeg2.this)
                    .setTitle("Delete entry")
                    .setMessage("Are you sure you want to delete this entry?")
                    .setPositiveButton("Go", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Intent i=new Intent(Databaseeg2.this,Dtabaseeg3.class);
                            startActivity(i);
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // do nothing
                            dialog.cancel();
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();




        }
        else if(v==bt_SAve)
        {


            String str="insert into tb_sum(no1,no2,result)values('"+c+"','"+d+"','"+e+"')";
            boolean h=obj1.insertData(str);
            if(h==true) {
                Toast.makeText(Databaseeg2.this, "inserted",Toast.LENGTH_SHORT ).show();
            }
                else
                Toast.makeText(Databaseeg2.this,"not iinserted",Toast.LENGTH_SHORT).show();
        }
    }
//////   for notification
    public  void createNotificn(){
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle("My notification")
                        .setContentText("Hello World!")
                .setAutoCancel(true);
        NotificationManager nmanr= (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        nmanr.notify(777,mBuilder.build());
    }




}
