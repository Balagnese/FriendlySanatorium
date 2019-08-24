package com.example.balagnese.testapp.ClientVP;

import com.example.balagnese.testapp.DataTypes.Client;
import com.example.balagnese.testapp.DataTypes.Diet;

import java.util.List;

public interface ClientView {

    void showClientData(Client client);
    void showClientChildrenList(List<Client> children);
    void navigateToChildActivity(Client child);
    void navigateToAuthActivity();
    void navigateToClientProceduresActivity();
    void navigateToDishesActivity();
}
