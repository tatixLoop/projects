package com.arpo.cookery;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;

/**
 * Created by jithin on 10/9/18.
 */

public class ListItemDishes implements Serializable {
    int id;
    int type;
    String name;
    String img_path;
    int cooktimeinsec;
    int serveCount;
    int calory;
    int rating;
    String author;
    String box_preview;
    byte[] byteArray;
    int previewSet;


    public int getPreviewSet() {
        return previewSet;
    }

    public void setPreviewSet(int previewSet) {
        this.previewSet = previewSet;
    }

    public Bitmap getPreviewImg() {
        return BitmapFactory.decodeByteArray(this.byteArray , 0, this.byteArray.length);
    }

    public void setPreviewImg(Bitmap previewImg) {

        //this.previewImg = previewImg;
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        previewImg.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        this.byteArray = stream.toByteArray();
    }

    public ListItemDishes(int id, int type, String name, String img_path, int cooktimeinsec, int serveCount, int calory, int rating, String author) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.img_path = img_path;
        this.cooktimeinsec = cooktimeinsec;
        this.serveCount = serveCount;
        this.calory = calory;
        this.rating = rating;
        this.author = author;
        this.previewSet = 0;
    }



    public ListItemDishes(int id, int type, String name, String img_path) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.img_path = img_path;
    }

    public int getCooktimeinsec() {
        return cooktimeinsec;
    }

    public void setCooktimeinsec(int cooktimeinsec) {
        this.cooktimeinsec = cooktimeinsec;
    }

    public int getServeCount() {
        return serveCount;
    }

    public void setServeCount(int serveCount) {
        this.serveCount = serveCount;
    }

    public int getCalory() {
        return calory;
    }

    public void setCalory(int calory) {
        this.calory = calory;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImg_path() {
        return img_path;
    }

    public void setImg_path(String img_path) {
        this.img_path = img_path;
    }

    public String getBox_preview() {
        return box_preview;
    }

    public void setBox_preview(String box_preview) {
        this.box_preview = box_preview;
    }
}
