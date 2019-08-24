package com.example.balagnese.testapp.AuthVP;

import com.example.balagnese.testapp.DataTypes.Client;
import com.example.balagnese.testapp.Models.ClientModel;

public class AuthPresenter {

    private AuthView av;
    private ClientModel cm = new ClientModel();

    public AuthPresenter(AuthView authView){
        this.av = authView;
    }

    public void logIn(String login, String password){
        cm.signIn(login, password, new ClientModel.Callback() {
            @Override
            public void onClientReceive(Client client) {
                av.navigateToClientActivity();
            }

            @Override
            public void onClientReceiveFailure(Exception e) {

            }
        });
    }
}
