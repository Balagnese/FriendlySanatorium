package com.example.balagnese.testapp.DataTypes;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Procedure {
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("places")
    @Expose
    private List<String> places;
    @SerializedName("duration")
    @Expose
    private int duration;


    public void setId(int id){
        this.id = id;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public void setPlaces(List<String> places){
        this.places = places;
    }

    public int getId(){
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public List<String> getPlaces() {
        return places;
    }

    public int getDuration() {
        return duration;
    }
}
