package com.appsecurity.tatix.menzworld;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().hide();

        setContentView(R.layout.activity_login);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }

        MainActivity.mdb = openOrCreateDatabase("menZworldDB", Context.MODE_PRIVATE, null);

        if(MainActivity.mdb == null)
        Log.d("JKS ","open dbfailed");
        else
            Log.d("JKS ", "open db success :)");
        MainActivity.mdb.execSQL("CREATE TABLE IF NOT EXISTS stockData(stockId INTEGER PRIMARY KEY AUTOINCREMENT,"+
                " serialNumber INTEGER NOT NULL,"+
                " barcodeId bigint,"+
                " item INTEGER NOT NULL,"+
                " price INTEGER NOT NULL,"+
                " selling_price INTEGER NOT NULL,"+
                " noOfItems INTEGER NOT NULL,"+
                " itemsSold INTEGER,"+
                " stockDate DATETIME NOT NULL);");

/*        mdb.execSQL("CREATE TABLE IF NOT EXISTS billData(refId INTEGER REFERENCES billData(refId) ,"+
                                                        " stockId INTEGER REFERENCES stockData(stockId));");*/

        MainActivity.mdb.execSQL("CREATE TABLE IF NOT EXISTS billData(billrefId INTEGER ," +
                " stockRefId INTEGER);");

        MainActivity.mdb.execSQL("CREATE TABLE IF NOT EXISTS billTable(billId INTEGER PRIMARY KEY AUTOINCREMENT," +
                " refId INTEGER NOT NULL," +
                " discount INTEGER," +
                " billDate DATETIME NOT NULL);");

        MainActivity.mdb.execSQL("CREATE TABLE IF NOT EXISTS security (userId INTEGER NOT NULL, password INTEGER NOT NULL);");

        Cursor c3 = MainActivity.mdb.rawQuery("SELECT * FROM security", null);
        if(c3.getCount() == 0) {
            MainActivity.mdb.execSQL("INSERT INTO security VALUES('0','12345')");
        }

        Button btn_password = (Button)findViewById(R.id.btn_enter);

        btn_password.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        EditText edPass = (EditText)findViewById(R.id.txt_password);
                        int menzworldPassword = -1;
                        if(edPass.getText().toString().matches(""))
                        {
                            Toast.makeText(getBaseContext(),"Please enter password",Toast.LENGTH_SHORT).show();
                            return;
                        }

                        Cursor c4 = MainActivity.mdb.rawQuery("SELECT password FROM security WHERE userId=0", null);
                        if(c4.getCount() == 1) {
                            c4.moveToNext();
                            menzworldPassword = c4.getInt(0);
                        }

                        int enteredPassword = Integer.parseInt(edPass.getText().toString());
                        if(enteredPassword != menzworldPassword)
                        {
                            Toast.makeText(getBaseContext(),"Invalid password !!!",Toast.LENGTH_SHORT).show();
                            edPass.setText("");
                            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(edPass.getWindowToken(), 0);
                            return;
                        }

                        Intent intent = new Intent(login.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });

    }
}
