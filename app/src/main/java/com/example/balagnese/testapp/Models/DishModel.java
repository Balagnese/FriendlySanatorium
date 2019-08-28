package com.example.balagnese.testapp.Models;

import com.example.balagnese.testapp.ClientInfo;
import com.example.balagnese.testapp.DataTypes.ClientSelectedDishModel;
import com.example.balagnese.testapp.DataTypes.PostDish;
import com.example.balagnese.testapp.DataTypes.PostResponse;
import com.example.balagnese.testapp.Network.NetworkService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DishModel {

    public interface Callback{
        void onDishesReceive(ClientSelectedDishModel menu);
        void onDishesReceiveFailure(Exception e);
    }

    public interface PostCallback{
        void onDishesPost(PostResponse postResponse);
        void onDishesPostFailure(Exception e);
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

    public void selectClientDishes(PostDish postDish, final PostCallback callback){
        NetworkService
                .getInstance()
                .getJSONApi()
                .selectClientDish(ClientInfo.getToken(), postDish)
                .enqueue(new retrofit2.Callback<PostResponse>() {
                    @Override
                    public void onResponse(Call<PostResponse> call, Response<PostResponse> response) {
                        if (response.isSuccessful())
                            callback.onDishesPost(response.body());
                        else
                            callback.onDishesPostFailure(new Exception());
                    }

                    @Override
                    public void onFailure(Call<PostResponse> call, Throwable t) {
                        callback.onDishesPostFailure(new Exception());
                    }
                });
    }

    public void selectChildDishes(PostDish postDish, String public_id, final PostCallback callback){
        NetworkService
                .getInstance()
                .getJSONApi()
                .selectChildDish(ClientInfo.getToken(), public_id, postDish)
                .enqueue(new retrofit2.Callback<PostResponse>() {
                    @Override
                    public void onResponse(Call<PostResponse> call, Response<PostResponse> response) {
                        if (response.isSuccessful())
                            callback.onDishesPost(response.body());
                        else
                            callback.onDishesPostFailure(new Exception());
                    }

                    @Override
                    public void onFailure(Call<PostResponse> call, Throwable t) {
                        callback.onDishesPostFailure(new Exception());
                    }
                });
    }
}
