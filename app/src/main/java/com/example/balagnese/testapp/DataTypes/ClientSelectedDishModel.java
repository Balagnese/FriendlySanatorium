package com.example.balagnese.testapp.DataTypes;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ClientSelectedDishModel {
    @SerializedName("daily_menu_id")
    @Expose
    private int daily_menu_id;
    @SerializedName("breakfast")
    @Expose
    private ClientMealSelection breakfast;
    @SerializedName("lunch")
    @Expose
    private ClientMealSelection lunch;
    @SerializedName("supper")
    @Expose
    private ClientMealSelection supper;

    public ClientMealSelection getBreakfast() {
        return breakfast;
    }

    public ClientMealSelection getLunch() {
        return lunch;
    }

    public ClientMealSelection getSupper() {
        return supper;
    }

    public int getDaily_menu_id() {
        return daily_menu_id;
    }

    public void setSupper(ClientMealSelection supper) {
        this.supper = supper;
    }

    public void setLunch(ClientMealSelection lunch) {
        this.lunch = lunch;
    }

    public void setDaily_menu_id(int daily_menu_id) {
        this.daily_menu_id = daily_menu_id;
    }

    public void setBreakfast(ClientMealSelection breakfast) {
        this.breakfast = breakfast;
    }
}



