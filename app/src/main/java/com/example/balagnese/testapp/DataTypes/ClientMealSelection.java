package com.example.balagnese.testapp.DataTypes;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ClientMealSelection {
    @SerializedName("not_selected")
    @Expose
    private List<DishesGroup> not_selected;
    @SerializedName("selected")
    @Expose
    private List<ClientSelectedDish> selected;

    public List<ClientSelectedDish> getSelected() {
        return selected;
    }

    public List<DishesGroup> getNot_selected() {
        return not_selected;
    }

    public void setNot_selected(List<DishesGroup> not_selected) {
        this.not_selected = not_selected;
    }

    public void setSelected(List<ClientSelectedDish> selected) {
        this.selected = selected;
    }
}
