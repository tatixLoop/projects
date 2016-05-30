package com.appsecurity.tatix.menzworld;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ActivityChangePassword extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_change_password);

        Button btnChange = (Button)findViewById(R.id.btn_change_password);
        btnChange.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EditText password = (EditText)findViewById(R.id.edit_passWord);
                        EditText newPassword = (EditText)findViewById(R.id.edit_newPass);
                        EditText confirm = (EditText)findViewById(R.id.edit_confirm);

                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(password.getWindowToken(), 0);
                        imm.hideSoftInputFromWindow(newPassword.getWindowToken(), 0);
                        imm.hideSoftInputFromWindow(confirm.getWindowToken(), 0);

                        if(password.getText().toString().matches("") ||
                                newPassword.getText().toString().matches("")||
                                confirm.getText().toString().matches(""))
                        {
                            Toast.makeText(getBaseContext(),"Fill all the feilds",Toast.LENGTH_SHORT).show();
                            return;
                        }

                        int menzworldPassword = -1;
                        Cursor c4 = MainActivity.mdb.rawQuery("SELECT password FROM security WHERE userId=0", null);
                        if(c4.getCount() == 1) {
                            c4.moveToNext();
                            menzworldPassword = c4.getInt(0);
                        }

                        if(menzworldPassword != Integer.parseInt(password.getText().toString()))
                        {
                            Toast.makeText(getBaseContext(),"Incorrect password entered",Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if(!(newPassword.getText().toString().matches(confirm.getText().toString())))
                        {
                            Toast.makeText(getBaseContext(),"Entered Passwords doesn't match ",Toast.LENGTH_SHORT).show();
                            return;
                        }
                        MainActivity.mdb.execSQL("UPDATE security SET password="+password.getText().toString()+"  WHERE userId=0");
                    }
                });
    }
}
