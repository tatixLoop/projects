package com.appsecurity.tatix.menzworld;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BackUpRestore extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_back_up_restore);

        isStoragePermissionGranted();

        Button btn_bkup = (Button)findViewById(R.id.btn_bkup);
        btn_bkup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File dbFile = getDatabasePath("menZworldDB");
                try {
                    FileInputStream fis = new FileInputStream(dbFile);
                    SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy_HHmmss");
                    String currentDateandTime = sdf.format(new Date());

                    String outFileName = Environment.getExternalStorageDirectory()+"/MenzWorldBkup/menZWorldDBbkup"+currentDateandTime+
                            ".db";



                    File file = new File(Environment.getExternalStorageDirectory()+"/MenzWorldBkup");
                    Log.d("JKS","Directory path is "+Environment.getExternalStorageDirectory()+"/MenzWorldBkup");
                    Log.d("JKS","outFile name is "+outFileName);
                    if (!file.exists()) {
                        if (!file.mkdirs()) {
                            Log.d("JKS","Nake directory failed");
                        }


                    }
                    // Open the empty db as the output stream
                    OutputStream output = new FileOutputStream(outFileName);


                    // Transfer bytes from the inputfile to the outputfile
                    byte[] buffer = new byte[1024];
                    int length;
                    while ((length = fis.read(buffer))>0){
                        output.write(buffer, 0, length);
                    }

                    // Close the streams
                    output.flush();
                    output.close();
                    fis.close();

                    TextView txt_msg = (TextView)findViewById(R.id.txt_msg);
                    txt_msg.setText("back up done for current data to "+outFileName);
                }
                catch (FileNotFoundException ex) {

                    try {
                        Log.d("JKS", "File not found exception");
                        FileInputStream fis = new FileInputStream(dbFile);
                        SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy_HHmmss");
                        String currentDateandTime = sdf.format(new Date());

                        String outFileName = "/storage/sdcard0/MenzWorldBkup/menZWorldDBbkup" + currentDateandTime +
                                ".db";


                        File file = new File("/storage/sdcard0/MenzWorldBkup");
                        if (!file.exists()) {
                            if (!file.mkdirs()) {
                                Log.d("JKS", "Nake directory failed");
                            }

                        }
                        // Open the empty db as the output stream
                        OutputStream output = new FileOutputStream(outFileName);


                        // Transfer bytes from the inputfile to the outputfile
                        byte[] buffer = new byte[1024];
                        int length;
                        while ((length = fis.read(buffer)) > 0) {
                            output.write(buffer, 0, length);
                        }

                        // Close the streams
                        output.flush();
                        output.close();
                        fis.close();

                        TextView txt_msg = (TextView) findViewById(R.id.txt_msg);
                        txt_msg.setText("back up done for current data to " + outFileName);
                    }
                    catch (FileNotFoundException ex2)
                    {

                        Log.d("JKS","Again file didnt find ");
                    }
                    catch (IOException ex2)
                    {
                        Log.d("JKS", "IO Exception");
                    }
                }
                catch (IOException ex)
                {
                    Log.d("JKS", "IO Exception");
                }
                Log.i("JKS", "DB path = " + dbFile.getAbsolutePath());

            }
            });

        Button btn_restore = (Button)findViewById(R.id.btn_restore);
        btn_restore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                          Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                           intent.setType("*/*");
                           intent.addCategory(Intent.CATEGORY_OPENABLE);

                           try {
                               startActivityForResult(
                                     Intent.createChooser(intent, "Select a file back up path"),
                                       0);
                          } catch (android.content.ActivityNotFoundException ex) {
                               // Potentially direct the user to the Market with a Dialog
                              Log.d("JKS", "Please install a File Manager.");
                          }

            }
        });
    }

    public  boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v("JKS","Permission is granted");
                return true;
            } else {

                Log.v("JKS","Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            Log.v("JKS","Permission is granted");
            return true;
        }


    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if(resultCode==RESULT_CANCELED)
        {
            // action cancelled
        }
        if(resultCode==RESULT_OK)
        {
            Uri dataVal = data.getData();
            File dbFile = new File(dataVal.getPath());
            MainActivity.mdb.close();

            try {
                FileInputStream fis = new FileInputStream(dbFile);
                File outFile = getDatabasePath("menZworldDB");
                OutputStream output = new FileOutputStream(outFile);


                // Transfer bytes from the inputfile to the outputfile
                byte[] buffer = new byte[1024];
                int length;
                while ((length = fis.read(buffer))>0){
                    output.write(buffer, 0, length);
                }

                // Close the streams
                output.flush();
                output.close();
                fis.close();
                Log.d("JKS", "BackUp restored");
                TextView txt_msg = (TextView)findViewById(R.id.txt_msg);
                txt_msg.setText("Restored database from "+data.getDataString());
                Log.i("JKS", "DB path for restore= " + outFile.getAbsolutePath());
            }
            catch (FileNotFoundException ex) {
                Log.d("JKS","File not found exception");
            }
            catch (IOException ex)
            {
                Log.d("JKS","IO Exception");
            }


            MainActivity.mdb = openOrCreateDatabase("menZworldDB", Context.MODE_PRIVATE, null);
        }
    }
}
