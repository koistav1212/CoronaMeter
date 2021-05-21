package com.example.CoronaMeter;

public class HospitalModel {
    private String name,district,availWithoutVentilator,availWithoutOXY,availWithOXY,availWithVentilator;
    long totalbeds,withoutOXY,withOXY,withoutVentilator,withVentilator;

    public HospitalModel() {
    }

    public HospitalModel(String name,String district, long withoutOXY, long withOXY, long withoutVentilator, long withVentilator, String availWithoutOXY, String availWithOXY,String availWithoutVentilator,String availWithVentilator,long totalbeds) {

        this.name = name;
        this.district = district;
        this.withoutOXY = withoutOXY;
        this.withOXY = withOXY;
        this.withoutVentilator = withoutVentilator;
        this.withVentilator = withVentilator;
        this.totalbeds = totalbeds;

        this.availWithoutOXY=availWithoutOXY;
        this.availWithOXY=availWithOXY;
        this.availWithoutVentilator=availWithoutVentilator;
        this.availWithVentilator=availWithVentilator;
    }


    public String getName() {
        return name;
    }


    public String getDistrict() {
        return district;
    }


    public long getTotalbeds() {
        return totalbeds;
    }


    public long getWithoutOXY() {
        return withoutOXY;
    }

    public long getWithOXY() {
        return withOXY;
    }
    public String getAvailWithoutVentilator() {
        return availWithoutVentilator;
    }

    public long getWithoutVentilator() {
        return withoutVentilator;
    }


    public long getWithVentilator() {
        return withVentilator;
    }

    public String getAvailWithoutOXY() {
        return availWithoutOXY;
    }

    public String getAvailWithOXY() {
        return availWithOXY;
    }

    public String getAvailWithVentilator() {
        return availWithVentilator;
    }


}