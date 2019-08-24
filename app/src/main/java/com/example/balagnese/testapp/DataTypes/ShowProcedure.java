package com.example.balagnese.testapp.DataTypes;

public class ShowProcedure {
    private int procedure_id;
    private String time;
    private String place;
    private String name;
    private String description;

    public ShowProcedure(ClientProcedure cp, Procedure p){
        setName(p.getName());
        setPlace(cp.getPlace());
        setTime(cp.getTime());
        setDescription(p.getDescription());
    }

    public String getTime() {
        return time;
    }

    public String getPlace() {
        return place;
    }

    public int getProcedure_id() {
        return procedure_id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setProcedure_id(int procedure_id) {
        this.procedure_id = procedure_id;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
