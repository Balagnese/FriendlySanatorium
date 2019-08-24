package com.example.balagnese.testapp.DishesMenuVP;

public class DishesMenuPresenter {

    private DishesMenuView dmv;
    private String publicId;

    public DishesMenuPresenter(DishesMenuView menuDishesView, String id){
        dmv = menuDishesView;
        publicId = id;
    }

    public void navigateToTomorrowDishesActivity(){
        dmv.navigateToTomorrowDishesActivity(publicId);
    }

    public void navigateTodayDishesActivity(){
        dmv.navigateTodayDishesActivity(publicId);
    }
}
