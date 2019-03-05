package com.jaapps.veganrecipes;

import android.view.View;

public class ListSubCatagory {
    int catagory;
    int subcatagory;
    String bgUrl;
    String text;
    int type;
    View convertView;

    public View getConvertView() {
        return convertView;
    }

    public void setConvertView(View convertView) {
        this.convertView = convertView;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public ListSubCatagory(int catagory, int subcatagory, String bgUrl, String text) {
        this.catagory = catagory;
        this.subcatagory = subcatagory;
        this.bgUrl = bgUrl;
        this.text = text;
    }

    public int getCatagory() {
        return catagory;
    }

    public void setCatagory(int catagory) {
        this.catagory = catagory;
    }

    public int getSubcatagory() {
        return subcatagory;
    }

    public void setSubcatagory(int subcatagory) {
        this.subcatagory = subcatagory;
    }

    public String getBgUrl() {
        return bgUrl;
    }

    public void setBgUrl(String bgUrl) {
        this.bgUrl = bgUrl;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
