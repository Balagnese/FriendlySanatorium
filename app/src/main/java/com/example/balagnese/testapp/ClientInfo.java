package com.example.balagnese.testapp;


import android.util.Log;

import com.example.balagnese.testapp.DataTypes.Client;

import java.util.ArrayList;
import java.util.List;

public class ClientInfo {

    public static void setToken(String token){
        Preferences.addProperty("token", token);
    }

    public static String getToken() {
        return Preferences.getProperty("token");

    }

    public static void clearToken(){
        Preferences.editProperty("token", "");
    }

    private Client user;

    private List<Client> children;

    private static ClientInfo instance;

    public static ClientInfo getInstance(){
        if (instance == null){
            instance = new ClientInfo();
        }

        return instance;
    }

    private ClientInfo(){
        children = new ArrayList<>();
    }


    public void setChildren(List<Client> children) {
        if (children == null)
            this.children = new ArrayList<>();
        else
            this.children = children;
    }

    public void setUser(Client user){
        this.user = user;
    }

    public List<Client> getChildren() {
        return children;
    }

    public Client getUser() {
        return user;
    }

    public Client getChildByPublicId(String publicId){
        for (Client child: children) {
            if (child.getPublic_id().equals(publicId))
                return child;
        }
        return null;
    }

    public String getFIO(Client client) {
        try {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(client.getSurname()).append(" ").append(client.getName());
            if (client.getPatronymic() != null)
                stringBuilder.append(" ").append(client.getPatronymic());
            return stringBuilder.toString();
        } catch (Exception e) {
            Log.v("getFIO", "Exception");
        }
        return "";
    }
}
