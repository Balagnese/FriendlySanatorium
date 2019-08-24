package com.example.balagnese.testapp.ChildVP;

import com.example.balagnese.testapp.DataTypes.Client;

public interface ChildView {
    void showChildData(Client client);
    void navigateToChildProceduresActivity(Client client);
    void navigateToClientActivity();
    void navigateToChildMenuActivity(Client client);

}
