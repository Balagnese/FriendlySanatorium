package com.example.balagnese.testapp.DataTypes;

import android.support.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.joda.time.DateTime;

public class ClientProcedure implements Comparable<ClientProcedure>{
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("client_public_id")
    @Expose
    private String client_public_id;
    @SerializedName("procedure_id")
    @Expose
    private int procedure_id;
    @SerializedName("time")
    @Expose
    private String time;
    @SerializedName("place")
    @Expose
    private String place;
    @SerializedName("procedure")
    @Expose
    private Procedure procedure;


    public int getId() {
        return id;
    }

    public int getProcedure_id() {
        return procedure_id;
    }

    public String getClient_public_id() {
        return client_public_id;
    }

    public String getPlace() {
        return place;
    }

    public String getTime() {
        return time;
    }

    public Procedure getProcedure() {
        return procedure;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setClient_public_id(String client_public_id) {
        this.client_public_id = client_public_id;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public void setProcedure_id(int procedure_id) {
        this.procedure_id = procedure_id;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setProcedure(Procedure procedure) {
        this.procedure = procedure;
    }

    public DateTime getDateTime(){
        return new DateTime(getTime());
    }

    @Override
    public int compareTo(@NonNull ClientProcedure o) {
        return getDateTime().compareTo(o.getDateTime());
    }

    public String getDayMonthForPrint(){
        StringBuilder stringBuilder = new StringBuilder();
        DateTime dateTime = getDateTime();
        stringBuilder.append(dateTime.year().get()).append("/").append(dateTime.monthOfYear().get()).append("/").append(dateTime.dayOfMonth().get());
        return stringBuilder.toString();
    }

    public String getTimeForPrint(){
        StringBuilder stringBuilder = new StringBuilder();
        DateTime dateTime = getDateTime();
        stringBuilder.append(dateTime.hourOfDay().get()).append(":").append(dateTime.minuteOfHour().get());
        return stringBuilder.toString();
    }
}
