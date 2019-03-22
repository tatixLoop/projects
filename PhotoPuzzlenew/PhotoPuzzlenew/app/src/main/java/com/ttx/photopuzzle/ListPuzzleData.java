package com.ttx.photopuzzle;

import android.graphics.Bitmap;

/**
 * Created by jithin suresh on 03-06-2017.
 */

public class ListPuzzleData {
    int imageId; // to check if puzzle is solved
    String text; // to check whether puzzle is solved
    Bitmap img;   // to display image
    int width;
    int height;

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public Bitmap getImg() {
        return img;
    }

    public void setImg(Bitmap img) {
        this.img = img;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
