package com.example.balagnese.testapp.DataTypes;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
public class Client {
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("surname")
    @Expose
    private String surname;
    @SerializedName("patronymic")
    @Expose
    private String patronymic;
    @SerializedName("public_id")
    @Expose
    private String public_id;
    @SerializedName("is_child")
    @Expose
    private boolean is_child;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("time_arrival")
    @Expose
    private String time_arrival;
    @SerializedName("time_departure")
    @Expose
    private String time_departure;
    @SerializedName("diet_id")
    @Expose
    private int diet_id;

    public String getName() {
        return name;
    }

    public int getDiet_id() {
        return diet_id;
    }

    public String getGender() {
        return gender;
    }

    public boolean getIs_child() {
        return is_child;
    }

    public String getPublic_id() {
        return public_id;
    }

    public String getTime_arrival() {
        return time_arrival;
    }

    public String getTime_departure() {
        return time_departure;
    }

    public String getPatronymic() {return patronymic; }

    public String getSurname() { return surname; }

}
