package com.example.balagnese.testapp.TodayDishesVP;

import android.os.Handler;
import android.os.Looper;

import com.example.balagnese.testapp.DataTypes.ClientSelectedDishModel;
import com.example.balagnese.testapp.Models.DishModel;

import org.joda.time.DateTime;

public class TodayDishesPresenter {

    private TodayDishesView cdv;
    private DishModel dm = new DishModel();
    private String publicId;
    private Handler handler;

    public TodayDishesPresenter(TodayDishesView clientDishesView, String publicId){
        cdv = clientDishesView;
        this.publicId = publicId;
        handler = new android.os.Handler(Looper.getMainLooper());
        loadClientDishes();
    }

    public void loadClientDishes(){

        DateTime dateTime = DateTime.now();
        StringBuilder stringBuilder = new StringBuilder();
        String year = String.valueOf(dateTime.year().get());
        String month = String.valueOf(dateTime.monthOfYear().get());
        DateTime m = DateTime.now();

        String day = String.valueOf(dateTime.dayOfMonth().get());
        if (month.length() < 2)
            month = "0".concat(month);
        if (day.length() < 2)
            day = "0".concat(day);
        stringBuilder.append(year).append("-").append(month).append("-").append(day);

        String date = stringBuilder.toString();
//        String date = "2019-08-21";
        if (publicId.equals("")){
            dm.getClientMenu(date, new DishModel.Callback() {
                @Override
                public void onDishesReceive(final ClientSelectedDishModel menu) {
                    Runnable runnable = new Runnable() {
                        @Override
                        public void run() {
                            cdv.showClientMenu(menu);
                            int i = 0;
                        }
                    };
                    handler.post(runnable);
                }

                @Override
                public void onDishesReceiveFailure(Exception e) {
                    cdv.navigateToClientActivity();
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
                            cdv.showClientMenu(menu);
                        }
                    };
                    handler.post(runnable);
                }

                @Override
                public void onDishesReceiveFailure(Exception e) {
                    cdv.navigateToClientActivity();
                }
            });
        }


    }
}
