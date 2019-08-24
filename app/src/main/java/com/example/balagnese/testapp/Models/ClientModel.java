package com.example.balagnese.testapp.Models;

import android.util.Log;

import com.example.balagnese.testapp.DataTypes.Auth;
import com.example.balagnese.testapp.DataTypes.AuthResponse;
import com.example.balagnese.testapp.DataTypes.Client;
import com.example.balagnese.testapp.DataTypes.PostResponse;
import com.example.balagnese.testapp.Network.NetworkService;
import com.example.balagnese.testapp.ClientInfo;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class ClientModel {

    public interface Callback{
        void onClientReceive(Client client);
        void onClientReceiveFailure(Exception e);
    }

    public interface ChildrenCallback{
        void onClientChildrenReceive(List<Client> children);
        void onClientChildrenReceiveFailure(Exception e);
    }

    public interface LogOutCallback{
        void onLogOutSuccess(String status);
        void onLogOutNotSuccess(String status);
        void onLogOutFailure(Exception e);
    }

    class TokenFailure extends Exception{

    }

    public void getCurrentUser(final Callback callback) {
        try{
            if (!ClientInfo.getToken().equals("")){
                if (!(ClientInfo.getInstance().getUser() == null))
                    callback.onClientReceive(ClientInfo.getInstance().getUser());
                else
                    logInByToken(callback);
            }
            else
                callback.onClientReceiveFailure(new Exception());
        }
        catch (Exception e) {
            callback.onClientReceiveFailure(new Exception());
        }

    }

    public void signIn(String login, String password, final Callback callback){
        NetworkService
                .getInstance()
                .getJSONApi()
                .logIn(new Auth(login, password))
                .enqueue(new retrofit2.Callback<AuthResponse>() {
                    @Override
                    public void onResponse(Call<AuthResponse> call, Response<AuthResponse> response) {
                        if (response.isSuccessful()){
                            ClientInfo.setToken("Bearer " + response.body().getToken());
                            logInByToken(new Callback() {
                                @Override
                                public void onClientReceive(Client client) {
                                    callback.onClientReceive(client);
                                }

                                @Override
                                public void onClientReceiveFailure(Exception e) {
                                    ClientInfo.getInstance().setUser(null);
                                    callback.onClientReceiveFailure(new TokenFailure());
                                }
                            });
                        }
                        else{
                            callback.onClientReceiveFailure(new Exception());
                        }


                    }
                    @Override
                    public void onFailure(Call<AuthResponse> call, Throwable t) {
                        Log.v("signIn", "onFailure");
                        ClientInfo.getInstance().setUser(null);
                        callback.onClientReceiveFailure(new Exception());
                    }
                });
    }

    public void logOut(final LogOutCallback callback){
        NetworkService
                .getInstance()
                .getJSONApi()
                .logOut(ClientInfo.getToken())
                .enqueue(new retrofit2.Callback<PostResponse>() {
                    @Override
                    public void onResponse(Call<PostResponse> call, Response<PostResponse> response) {
                        if (response.isSuccessful()) {
                            callback.onLogOutSuccess(response.body().getStatus());
                            ClientInfo.clearToken();
                            ClientInfo.getInstance().setUser(null);
                        }
                        else
                            callback.onLogOutNotSuccess(response.body().getStatus());
                    }

                    @Override
                    public void onFailure(Call<PostResponse> call, Throwable t) {
                        callback.onLogOutFailure(new Exception());
                    }
                });
    }

    private void logInByToken(final Callback callback){
        NetworkService
                .getInstance()
                .getJSONApi()
                .getCurrentClient(ClientInfo.getToken())
                .enqueue(new retrofit2.Callback<Client>() {
                    @Override
                    public void onResponse(Call<Client> call, Response<Client> response) {
                        if (response.isSuccessful()){
                            callback.onClientReceive(response.body());
                        }
                        else {
                            Log.v("logInByAccessToken", "response is not successfull");
                            ClientInfo.clearToken();
                            callback.onClientReceiveFailure(new TokenFailure());
                        }
                    }
                    @Override
                    public void onFailure(Call<Client> call, Throwable t) {
                        Log.v("logInByAccessToken", "onFailure");
                        ClientInfo.clearToken();
                        callback.onClientReceiveFailure(new TokenFailure());
                    }
                });
    }

//    public void getClientData(int clientId, final Callback callback){
//        NetworkService
//                .getInstance()
//                .getJSONApi()
//                .getClientById(clientId)
//                .enqueue(new retrofit2.Callback<Client>() {
//                    @Override
//                    public void onResponse(Call<Client> call, Response<Client> response) {
//                        if (response.isSuccessful()){
//                            callback.onClientReceive(response.body());
//                        }
//                        else {
//                            Log.v("logInByAccessToken", "response is not successfull");
//                            callback.onClientReceiveFailure(new TokenFailure());
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Call<Client> call, Throwable t) {
//                        Log.v("logInByAccessToken", "onFailure");
//                        callback.onClientReceiveFailure(new TokenFailure());
//                    }
//                });
//    }

    public void getCurrentClientChildren(final ChildrenCallback callback){
        NetworkService
                .getInstance()
                .getJSONApi()
                .getCurrentClientChildren(ClientInfo.getToken())
                .enqueue(new retrofit2.Callback<List<Client>>() {
                    @Override
                    public void onResponse(Call<List<Client>> call, Response<List<Client>> response) {
                        if (response.isSuccessful()){
                            List<Client> children = response.body();
                            ClientInfo.getInstance().setChildren(children);
                            callback.onClientChildrenReceive(children);
                        }
                        else{
                            callback.onClientChildrenReceiveFailure(new Exception());
                        }

                    }

                    @Override
                    public void onFailure(Call<List<Client>> call, Throwable t) {
                        callback.onClientChildrenReceiveFailure(new Exception());
                    }
                });
    }


}
