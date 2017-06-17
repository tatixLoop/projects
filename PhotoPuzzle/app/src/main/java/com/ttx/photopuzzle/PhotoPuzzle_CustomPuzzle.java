package com.ttx.photopuzzle;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class PhotoPuzzle_CustomPuzzle extends AppCompatActivity {

    Button btn_imagechoose;
    Button btn_startgame;
    ImageView imv_two_custom;
    ImageView imv_three_custom;
    ImageView imv_four_custom;
    ImageView imv_five_custom;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_puzzle__custom_puzzle);

        /////    full screen  page with transparent status bar

        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        getWindow().setStatusBarColor(Color.TRANSPARENT);

        btn_imagechoose = (Button) findViewById(R.id.btn_chooseimage);

        ///   go to gallery to choose image

        btn_imagechoose.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                chooseimage=1;
                Intent i = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                startActivityForResult(i, 101);
            }
        });
        btn_startgame= (Button) findViewById(R.id.btn_start);



        btn_startgame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (selectedgrid==0)
                {
                    Toast.makeText(PhotoPuzzle_CustomPuzzle.this,"Select the puzzle level", Toast.LENGTH_SHORT).show();
                }
                else if(chooseimage==0)
                {
                    Toast.makeText(PhotoPuzzle_CustomPuzzle.this,"Please An Choose Image", Toast.LENGTH_SHORT).show();

                }
                else if(selectedpath==null)
                {
                    Toast.makeText(PhotoPuzzle_CustomPuzzle.this,"Nothing Selected Try Again", Toast.LENGTH_SHORT).show();
                }
                else {
                    Log.d("PS","launching activity with selected path ="+selectedpath + " selected grid="+selectedgrid);
                    Intent i = new Intent(PhotoPuzzle_CustomPuzzle.this, PhotoPuzzle_Game.class);
                    i.putExtra("imageselscted", selectedpath);
                    i.putExtra("gridselected",selectedgrid);
                    startActivity(i);
                }

            }
        });


        //// initialising button views


        imv_two_custom= (ImageView) findViewById(R.id.imv_game2);
        imv_three_custom= (ImageView) findViewById(R.id.imv_game3);
        imv_four_custom= (ImageView) findViewById(R.id.imv_game4);
        imv_five_custom= (ImageView) findViewById(R.id.imv_game5);
        imv_two_custom.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {


                /* if (isPressed)
                {
                    imv_two_custom.setBackgroundResource(R.drawable.twosquare_selected);

                }
                else
                imv_two_custom.setBackgroundResource(R.drawable.twogamepuzzle);*/
                   /* isPressed=!isPressed;*/


                imv_two_custom.setBackgroundDrawable(getResources().getDrawable(R.drawable.twogamepuzzle));
                imv_two_custom.setClickable(false);
                imv_two_custom.setBackgroundDrawable(getResources().getDrawable(R.drawable.twosquare_selected));
                imv_two_custom.setClickable(true);
                selectedgrid=2;


            }
        });

        imv_three_custom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                selectedgrid=3;
                imv_three_custom.setBackgroundDrawable(getResources().getDrawable(R.drawable.threequare_selected));


            }
        });

        imv_four_custom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                selectedgrid=4;
                imv_four_custom.setBackgroundDrawable(getResources().getDrawable(R.drawable.foursquare_selected));

            }
        });
        imv_five_custom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                selectedgrid=5;
                imv_five_custom.setBackgroundDrawable(getResources().getDrawable(R.drawable.fivesquare_selected));

            }
        });

    }
    boolean isPressed=false;
    int chooseimage=0;
    int selectedgrid=0;

    String selectedpath;

    /// result pic get from gallery and save to image view

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 101 && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();

            Bitmap image = BitmapFactory.decodeFile(picturePath);
            Log.d("JKS", "width =" + image.getWidth() + " heigh = " + image.getHeight());
            int width = image.getWidth();
            int height = image.getHeight();
            if (width != height) {
                int lesser;
                if (width < height)
                    lesser = width;
                else
                    lesser = height;
                image = Bitmap.createBitmap(image, 0, 0, lesser, lesser);
            }

            ImageView imageView = (ImageView) findViewById(R.id.imv_choosenimage);
            imageView.setImageBitmap(image);
            selectedpath=picturePath;
        }


    }
}
