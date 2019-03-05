package com.jaapps.veganrecipes;

/**
 * Created by jithin on 11/9/18.
 */

public class ListItemIngredients {
    int qty;
    int selected;
    String ingredient;
    String dishName;

    public String getDishName() {
        return dishName;
    }

    public void setDishName(String dishName) {
        this.dishName = dishName;
    }

    int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ListItemIngredients(int qty, String ingredient) {
        this.qty = qty;
        this.ingredient = ingredient;
        selected = 0;
    }
    public ListItemIngredients(int qty, String ingredient, String dishName, int id) {
        this.qty = qty;
        this.ingredient = ingredient;
        this.id = id;
        this.dishName = dishName;
        selected = 0;
    }
    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public String getIngredient() {
        return ingredient;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }
}
