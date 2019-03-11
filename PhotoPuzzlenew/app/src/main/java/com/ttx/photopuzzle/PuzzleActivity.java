package com.ttx.photopuzzle;

import android.content.ClipData;
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
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class    PuzzleActivity extends AppCompatActivity implements View.OnDragListener {

    PuzzleGridData dataAdapter;
    List<ListPuzzleData> itemList;
    GridView gridPuzzle;

    PuzzleGridData dataAdapter2;
    List<ListPuzzleData> itemList2;
    GridView gridPuzzle2;

    boolean Eventregisterd;

    void print(String str)
    {
        Log.d("JKS",str);
    }

    @Override
    public boolean onDrag(View view, DragEvent dragEvent) {
        switch (dragEvent.getAction()) {
            case DragEvent.ACTION_DRAG_STARTED:
                print("Drag started");
                // Ignore this event
                return true;
            case DragEvent.ACTION_DRAG_ENTERED:
                //print("Drage entered");
                // Ignore this event
                return true;
            case DragEvent.ACTION_DRAG_EXITED:
                //print("Drage exited");
                // Ignore this event
                return true;
            case DragEvent.ACTION_DRAG_LOCATION:
                //print("Drag location");
                // Ignore this event
                return true;
            case DragEvent.ACTION_DROP:
                print("Drag drop");
                // Dropped inside a new view\
              //  adapter.notifyDataSetChanged();
               // ImageView v2 = (ImageView)view.getParent();
              //  final int position1 = gridView.getPositionForView(v2);
              //  if (position1 >= 0)
                {
               //     final long droppedIndex = gridView.getAdapter().getItemId(position1);
                }
                //Object item1 = gridView.getAdapter().getItem(draggedIndex);
                //Object item2 = gridView.getAdapter().getItem(droppedIndex);
               // drawables.remove(draggedIndex);
               // drawables.remove(droppedIndex);
               // drawables.add(droppedIndex,item1);
               // drawables.add(draggedIndex,item2);
               // draggedIndex = -1;
               // droppedIndex = -1;
               // adapter.notifyDataSetChanged();
                return true;
            case DragEvent.ACTION_DRAG_ENDED:
                //print("Drag ened");
                final int position1 = gridPuzzle.getPositionForView(view);
                print("pos = "+position1);
                //
                view.setOnDragListener(null);
                view.setVisibility(View.VISIBLE);
                return true;
            default: print("ERRROR");
        }
        return false;
    }

    boolean firstClick = false;
    int firstIndex = 0;

    boolean isSolved(List<ListPuzzleData> list)
    {
        int size = list.size();
        for(int i = 0 ; i < size; i++)
        {
            print("Check "+list.get(i).getText() + " and " + (i+1));

            if(list.get(i).getImageId() != i) {
                return false;
            }
            /*
            if(!(list.get(i).getText().equals(""+(i+1))))
                return false;
*/
        }
        return true;
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puzzle);

        /////    full screen  page with transparent status bar

        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        getWindow().setStatusBarColor(Color.TRANSPARENT);


        print("App start");
        int numElements = 9;

        Eventregisterd = false;

        gridPuzzle2 = (GridView)findViewById(R.id.gridPuzzle2);
        gridPuzzle2.setNumColumns(3);

        itemList2 = new ArrayList<>();
        dataAdapter2 = new PuzzleGridData(this, itemList2);



        for(int i = 0; i < numElements; i++)
        {
            ListPuzzleData item = new ListPuzzleData();
            item.setText(""+(i+1));

            item.setImageId(i);

            itemList2.add(item);
        }

        //shuffle data
        long seed = System.nanoTime();
        Collections.shuffle(itemList2, new Random(seed));

        gridPuzzle2.setAdapter(dataAdapter2);

        gridPuzzle2.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {

                if(isSolved(itemList2))
                {
                    Toast.makeText(PuzzleActivity.this, "Puzzle solved", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(firstClick == false)
                {
                    firstClick = true;
                    firstIndex = gridPuzzle2.getPositionForView(v);
                    v.setBackgroundColor(Color.parseColor("#772544"));
                    print("Clicked on "+firstIndex);
                }
                else
                {
                    firstClick = false;
                    int secondIndex = gridPuzzle2.getPositionForView(v);
                    print("Swap "+firstIndex + " And "+secondIndex);
                    Collections.swap(itemList2,firstIndex, secondIndex);
                    v.setBackgroundColor(Color.parseColor("#25776a"));
                    dataAdapter2.notifyDataSetChanged();
                }
                if(isSolved(itemList2))
                {
                    Toast.makeText(PuzzleActivity.this, "Puzzle solved", Toast.LENGTH_SHORT).show();
                }
            }
        });

        Button buttonLoadImage = (Button) findViewById(R.id.buttonLoadPicture);
        buttonLoadImage.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Intent i = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                startActivityForResult(i, 101);
            }
        });

/*
        gridPuzzle.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            public boolean onItemLongClick(AdapterView<?> arg0, View view, int position, long arg3) {

                ClipData data = ClipData.newPlainText("", "");
                view.setOnDragListener(PuzzleActivity.this);
                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
                view.startDrag(data, shadowBuilder, view, 0);
                //print("start Drag");
                //view.startDrag(data, new View.DragShadowBuilder(view), null, 0);
                //view.setVisibility(View.GONE);
                return true;
            }
        });*/
     /* */
    }

    public void createBitmaps(Bitmap source)
    {
        Bitmap bmp ;
        int k=0;
        int i,j;
        int width=source.getWidth();
        int height=source.getHeight();

        for(i=0;i<3;i++){
            for(j=0;j<3;j++){

                bmp= Bitmap.createBitmap(source,(width*j)/3,(i*height)/3,width/3,height/3);
                itemList2.get(k).setImg(bmp);
                itemList2.get(k).setImageId(k);
                k++;

            }
        }
        //shuffle data
        long seed = System.nanoTime();
        Collections.shuffle(itemList2, new Random(seed));
        dataAdapter2.notifyDataSetChanged();

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 101 && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };

            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();

            Bitmap image = BitmapFactory.decodeFile(picturePath);
            Log.d("JKS","width ="+image.getWidth() + " heigh = "+ image.getHeight());
            int width = image.getWidth();
            int height = image.getHeight();
            if(width!= height)
            {
                int lesser;
                if(width <height)
                    lesser = width;
                else
                    lesser = height;
                image = Bitmap.createBitmap(image,0,0,lesser,lesser);
            }
            createBitmaps(image);
            ImageView imageView = (ImageView) findViewById(R.id.imgView);
            imageView.setImageBitmap(image);

        }


    }


        //// press bouble tap to exit  from app



    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new android.os.Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }

}
