package com.example.balagnese.testapp.Models;

import com.example.balagnese.testapp.DataTypes.Diet;
import com.example.balagnese.testapp.Network.NetworkService;

import java.util.List;

public class DietModel {

    public interface Callback{
        void onDietReceive(Diet diet);
        void onDietReceiveFailure(Exception e);
    }

    public void getCurrentClientDient(final Callback callback){

    }

    public void getDietByClientId(int id){

    }

    public List<Diet> getAllDiets(){
        return null;
    }
}
