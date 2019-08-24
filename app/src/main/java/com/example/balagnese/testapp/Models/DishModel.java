package com.example.balagnese.testapp.Models;

import com.example.balagnese.testapp.ClientInfo;
import com.example.balagnese.testapp.DataTypes.ClientSelectedDishModel;
import com.example.balagnese.testapp.Network.NetworkService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DishModel {

    public interface Callback{
        void onDishesReceive(ClientSelectedDishModel menu);
        void onDishesReceiveFailure(Exception e);
    }

    public void getClientMenu(String date, final Callback callback){
        NetworkService
                .getInstance()
                .getJSONApi()
                .getClientMenu(ClientInfo.getToken(), date)
                .enqueue(new retrofit2.Callback<ClientSelectedDishModel>() {
                    @Override
                    public void onResponse(Call<ClientSelectedDishModel> call, Response<ClientSelectedDishModel> response) {
                        if (response.isSuccessful())
                            callback.onDishesReceive(response.body());
                        else
                            callback.onDishesReceiveFailure(new Exception());
                    }

                    @Override
                    public void onFailure(Call<ClientSelectedDishModel> call, Throwable t) {
                        callback.onDishesReceiveFailure(new Exception());
                    }
                });
    }

    public void GetChildMenu(String date, String publicId, final Callback callback){
        NetworkService
                .getInstance()
                .getJSONApi()
                .getChildMenu(ClientInfo.getToken(), date, publicId)
                .enqueue(new retrofit2.Callback<ClientSelectedDishModel>() {
                    @Override
                    public void onResponse(Call<ClientSelectedDishModel> call, Response<ClientSelectedDishModel> response) {
                        if (response.isSuccessful()){
                            callback.onDishesReceive(response.body());
                        }
                        else
                            callback.onDishesReceiveFailure(new Exception());
                    }

                    @Override
                    public void onFailure(Call<ClientSelectedDishModel> call, Throwable t) {
                        callback.onDishesReceiveFailure(new Exception());
                    }
                });
    }

    public void selectClientDish(int menuId, String mealTag, int dishes_group_id, int dish_id){

    }
}
