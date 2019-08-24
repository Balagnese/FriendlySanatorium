package com.example.balagnese.testapp.ClientVP;

import com.example.balagnese.testapp.DataTypes.Client;
import com.example.balagnese.testapp.Models.ClientModel;
import com.example.balagnese.testapp.Models.DietModel;

import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import java.util.List;

public class ClientPresenter {
    private ClientModel cm = new ClientModel();
    private DietModel dm = new DietModel();
    private ClientView cv;
    private Handler handler;

    public ClientPresenter(ClientView clientView){
        cv = clientView;
        handler = new Handler(Looper.getMainLooper());
        loadCurrentClientInformation();

    }

    public void loadCurrentClientInformation(){
        cm.getCurrentUser(new ClientModel.Callback() {
            @Override
            public void onClientReceive(Client client) {
                final Client c = client;
                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        //cv.showClientData(c);
                    }
                };
                loadCurrentClientChildrenList();
                handler.post(runnable);
            }
            @Override
            public void onClientReceiveFailure(Exception e) {
                cv.navigateToAuthActivity();
            }
        });
    }


    public void loadCurrentClientChildrenList(){
        cm.getCurrentClientChildren(new ClientModel.ChildrenCallback() {
            @Override
            public void onClientChildrenReceive(List<Client> children) {
                final List<Client> c = children;
                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        cv.showClientChildrenList(c);
                    }
                };
                handler.post(runnable);
            }

            @Override
            public void onClientChildrenReceiveFailure(Exception e) {

            }
        });
    }

    public void logOut(){
        cm.logOut(new ClientModel.LogOutCallback() {
            @Override
            public void onLogOutSuccess(String status) {
                cv.navigateToAuthActivity();
            }

            @Override
            public void onLogOutNotSuccess(String status) {

            }

            @Override
            public void onLogOutFailure(Exception e) {

            }
        });
    }

    public void navigateToChildActivity(Client child){
        cv.navigateToChildActivity(child);
    }

    public void navigateToClientProceduresActivity(){
        cv.navigateToClientProceduresActivity();
    }

    public void navigateToClientDishesActivity(){
        cv.navigateToDishesActivity();
    }

}
