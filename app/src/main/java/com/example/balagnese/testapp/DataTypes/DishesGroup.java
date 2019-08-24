package com.example.balagnese.testapp.DataTypes;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DishesGroup {
    @SerializedName("dishes_group_id")
    @Expose
    private int dishes_group_id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("dishes")
    @Expose
    private List<Dish> dishes;

    public String getName() {
        return name;
    }

    public int getDishes_group_id() {
        return dishes_group_id;
    }

    public List<Dish> getDishes() {
        return dishes;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDishes(List<Dish> dishes) {
        this.dishes = dishes;
    }

    public void setDishes_group_id(int dishes_group_id) {
        this.dishes_group_id = dishes_group_id;
    }
}
