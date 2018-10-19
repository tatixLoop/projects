package com.arpo.cookery;

/**
 * Created by jithin on 11/9/18.
 */

public class ListItemIngredients {
    int qty;
    int selected;
    String ingredient;

    public ListItemIngredients(int qty, String ingredient) {
        this.qty = qty;
        this.ingredient = ingredient;
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
