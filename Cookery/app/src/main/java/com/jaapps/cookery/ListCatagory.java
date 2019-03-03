package com.jaapps.cookery;

import android.view.View;

public class ListCatagory {
    String bgUrl;
    String text;
    int type;
    int viewType;
    View convertView;

    public int getViewType() {
        return viewType;
    }

    public void setViewType(int viewType) {
        this.viewType = viewType;
    }

    public ListCatagory(int type, String bgUrl, String text) {
        this.bgUrl = bgUrl;
        this.text = text;
        this.type = type;
        viewType = 0;
    }

    public View getConvertView() {
        return convertView;
    }

    public void setConvertView(View convertView) {
        this.convertView = convertView;
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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
