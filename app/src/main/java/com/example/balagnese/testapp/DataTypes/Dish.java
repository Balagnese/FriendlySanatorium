package com.example.balagnese.testapp.DataTypes;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Dish {
    @SerializedName("dish_id")
    @Expose
    private int dish_id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("description")
    @Expose
    private String description;

    private boolean isSelected;

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public boolean isSelected(){
        return isSelected;
    }

    public int getDish_id() {
        return dish_id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDish_id(int dish_id) {
        this.dish_id = dish_id;
    }

    public void select(){
        isSelected = true;
    }
}
