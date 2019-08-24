package com.example.balagnese.testapp.TodayDishesVP;

import com.example.balagnese.testapp.DataTypes.ClientSelectedDishModel;

public interface TodayDishesView {
    void showClientMenu(ClientSelectedDishModel menu);
    void navigateToClientActivity();
}
