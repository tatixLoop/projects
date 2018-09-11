package com.arpo.cookery;

import java.io.Serializable;

/**
 * Created by jithin on 10/9/18.
 */

public class ListItemDishes implements Serializable {
    int id;
    int type;
    String name;
    String img_path;

    String box_preview;

    public ListItemDishes(int id, int type, String name, String img_path) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.img_path = img_path;
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
