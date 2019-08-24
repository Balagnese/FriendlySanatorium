package com.example.balagnese.testapp.DataTypes;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DailyMenu {
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("diet_id")
    @Expose
    private int diet_id;
    @SerializedName("breakfast")
    @Expose
    private List<DishesGroup> breakfast;
    @SerializedName("lunch")
    @Expose
    private List<DishesGroup> lunch;
    @SerializedName("supper")
    @Expose
    private List<DishesGroup> supper;

    public int getId() {
        return id;
    }

    public int getDiet_id() {
        return diet_id;
    }

    public List<DishesGroup> getBreakfast() {
        return breakfast;
    }

    public List<DishesGroup> getLunch() {
        return lunch;
    }

    public List<DishesGroup> getSupper() {
        return supper;
    }

    public String getDate() {
        return date;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setBreakfast(List<DishesGroup> breakfast) {
        this.breakfast = breakfast;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setDiet_id(int diet_id) {
        this.diet_id = diet_id;
    }

    public void setLunch(List<DishesGroup> lunch) {
        this.lunch = lunch;
    }

    public void setSupper(List<DishesGroup> supper) {
        this.supper = supper;
    }
}
