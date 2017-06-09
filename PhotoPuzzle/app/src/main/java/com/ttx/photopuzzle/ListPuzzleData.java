package com.ttx.photopuzzle;

import android.graphics.Bitmap;

/**
 * Created by jithin suresh on 03-06-2017.
 */

public class ListPuzzleData {
    String text;
    Bitmap img;

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
