package com.example.balagnese.testapp.Models;

import android.accounts.NetworkErrorException;

import com.example.balagnese.testapp.DataTypes.ClientProcedure;
import com.example.balagnese.testapp.DataTypes.Procedure;
import com.example.balagnese.testapp.DataTypes.ShowProcedure;
import com.example.balagnese.testapp.Network.NetworkService;
import com.example.balagnese.testapp.ClientInfo;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProcedureModel {

    public interface Callback{
        void onClientProceduresReceive(List<ClientProcedure> procedures);
        void onClientProceduresReceiveFailure(Exception e);
    }

    public interface ProcedureCallback{
        void onProcedureReceive(Procedure procedure);
        void onProcedureReceiveFailure(Exception e);
    }

    public void getCurrentClientProcedures(final Callback callback) {
        NetworkService
                .getInstance()
                .getJSONApi()
                .getCurrentClientProcedures(ClientInfo.getToken())
                .enqueue(new retrofit2.Callback<List<ClientProcedure>>() {
                    @Override
                    public void onResponse(Call<List<ClientProcedure>> call, Response<List<ClientProcedure>> response) {
                        if (response.isSuccessful()) {
                            callback.onClientProceduresReceive(response.body());

                        } else {
                            callback.onClientProceduresReceiveFailure(new Exception());
                        }
                    }

                    @Override
                    public void onFailure(Call<List<ClientProcedure>> call, Throwable t) {
                        callback.onClientProceduresReceiveFailure(new Exception());
                    }
                });
    }

    public void getProcedure(int id, final ProcedureCallback callback){
        int i = id;
        NetworkService
                .getInstance()
                .getJSONApi()
                .getProcedure(id)
                .enqueue(new retrofit2.Callback<Procedure>() {
                    @Override
                    public void onResponse(Call<Procedure> call, Response<Procedure> response) {
                        if (response.isSuccessful())
                            callback.onProcedureReceive(response.body());
                        else
                            callback.onProcedureReceiveFailure(new Exception());
                    }

                    @Override
                    public void onFailure(Call<Procedure> call, Throwable t) {
                        callback.onProcedureReceiveFailure(new Exception());
                    }
                });
    }

    public void getChildProcedures(String public_id, final Callback callback){
        NetworkService
                .getInstance()
                .getJSONApi()
                .getChildProcedures(ClientInfo.getToken(), public_id)
                .enqueue(new retrofit2.Callback<List<ClientProcedure>>() {
                    @Override
                    public void onResponse(Call<List<ClientProcedure>> call, Response<List<ClientProcedure>> response) {
                        if (response.isSuccessful()){
                            callback.onClientProceduresReceive(response.body());
                        }
                        else
                            callback.onClientProceduresReceiveFailure(new Exception());

                    }

                    @Override
                    public void onFailure(Call<List<ClientProcedure>> call, Throwable t) {
                        callback.onClientProceduresReceiveFailure(new Exception());
                    }
                });
    }


}
