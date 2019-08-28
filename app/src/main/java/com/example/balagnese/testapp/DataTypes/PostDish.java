package com.example.balagnese.testapp.DataTypes;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PostDish {
    @SerializedName("daily_menu_id")
    @Expose
    private int daily_menu_id;
    @SerializedName("meal_tag")
    @Expose
    private String meal_tag;
    @SerializedName("dishes_group_id")
    @Expose
    private int dishes_group_id;
    @SerializedName("dish_id")
    @Expose
    private int dish_id;

    public PostDish(int daily_menu_id, String meal_tag, int dishes_group_id, int dish_id){
        this.daily_menu_id = daily_menu_id;
        this.meal_tag = meal_tag;
        this.dishes_group_id = dishes_group_id;
        this.dish_id = dish_id;
    }

    public int getDaily_menu_id() {
        return daily_menu_id;
    }

    public int getDishes_group_id() {
        return dishes_group_id;
    }

    public int getDish_id() {
        return dish_id;
    }

    public String getMeal_tag() {
        return meal_tag;
    }

    public void setDaily_menu_id(int daily_menu_id) {
        this.daily_menu_id = daily_menu_id;
    }

    public void setDishes_group_id(int dishes_group_id) {
        this.dishes_group_id = dishes_group_id;
    }

    public void setDish_id(int dish_id) {
        this.dish_id = dish_id;
    }

    public void setMeal_tag(String meal_tag) {
        this.meal_tag = meal_tag;
    }
}
