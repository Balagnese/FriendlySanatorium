package com.example.balagnese.testapp.DataTypes;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ClientSelectedDish {
    @SerializedName("dish")
    @Expose
    private Dish dish;
    @SerializedName("dishes_group")
    @Expose
    private DishesGroup dishes_group;

    public Dish getDish() {
        return dish;
    }

    public DishesGroup getDishes_group() {
        return dishes_group;
    }

    public void setDish(Dish dish) {
        this.dish = dish;
    }

    public void setDishes_group(DishesGroup dishes_group) {
        this.dishes_group = dishes_group;
    }
}
