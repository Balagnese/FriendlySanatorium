package com.example.balagnese.testapp.TomorrowDishesVP;

import android.os.Handler;
import android.os.Looper;

import com.example.balagnese.testapp.DataTypes.ClientSelectedDishModel;
import com.example.balagnese.testapp.Models.DishModel;

import org.joda.time.DateTime;

public class TomorrowDishesPresenter {
    
    private TomorrowDishesView tdv;
    private DishModel dm = new DishModel();
    private String publicId;
    private Handler handler;
    
    public TomorrowDishesPresenter(TomorrowDishesView tomorrowDishesView, String id){
        tdv = tomorrowDishesView;
        publicId = id;
        handler = new android.os.Handler(Looper.getMainLooper());
        loadMenu();
    }
    
    public void loadMenu(){

//        DateTime dateTime = DateTime.now();
//        dateTime.plusDays(1);
//        StringBuilder stringBuilder = new StringBuilder();
//        String year = String.valueOf(dateTime.year().get());
//        String month = String.valueOf(dateTime.monthOfYear().get());
//        String day = String.valueOf(dateTime.dayOfMonth().get());
//        if (month.length() < 2)
//            month = "0".concat(month);
//        if (day.length() < 2)
//            day = "0".concat(day);
//        stringBuilder.append(year).append("-").append(month).append("-").append(day);
//
//        String date = stringBuilder.toString();
        String date = "2019-08-21";
        if (publicId.equals("")){
            dm.getClientMenu(date, new DishModel.Callback() {
                @Override
                public void onDishesReceive(final ClientSelectedDishModel menu) {
                    Runnable runnable = new Runnable() {
                        @Override
                        public void run() {
                            tdv.showClientMenu(menu);
                            int i = 0;
                        }
                    };
                    handler.post(runnable);
                }

                @Override
                public void onDishesReceiveFailure(Exception e) {
                    tdv.navigateToClientActivity();
                }
            });
        }
        else {
            dm.GetChildMenu(date, publicId, new DishModel.Callback() {
                @Override
                public void onDishesReceive(final ClientSelectedDishModel menu) {
                    Runnable runnable = new Runnable() {
                        @Override
                        public void run() {
                            tdv.showClientMenu(menu);
                        }
                    };
                    handler.post(runnable);
                }

                @Override
                public void onDishesReceiveFailure(Exception e) {
                    tdv.navigateToClientActivity();
                }
            });
        }
    }


}
