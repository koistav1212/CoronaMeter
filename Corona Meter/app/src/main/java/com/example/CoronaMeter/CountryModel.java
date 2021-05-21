package com.example.CoronaMeter;

public class CountryModel {
    private String country,statename;
    long cases,todayCases,deaths,todayDeaths,recovered,todayrecovered,active;

    public CountryModel() {
    }

    public CountryModel(String country, String statename,long cases, long todayCases, long deaths, long todayDeaths, long recovered, long todayrecovered,long active) {

        this.country = country;
        this.cases = cases;
        this.todayCases = todayCases;
        this.deaths = deaths;
        this.todayDeaths = todayDeaths;
        this.recovered = recovered;
        this.active = active;
this.statename=statename;
        this.todayrecovered=todayrecovered;
    }


    public String getCountry() {
        return country;
    }

    public String getStatename() {
        return statename;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public long getCases() {
        return cases;
    }

    public void setCases(long cases) {
        this.cases = cases;
    }

    public long getTodayCases() {
        return todayCases;
    }

    public void setTodayCases(long todayCases) {
        this.todayCases = todayCases;
    }

    public long getDeaths() {
        return deaths;
    }

    public void setDeaths(long deaths) {
        this.deaths = deaths;
    }

    public long getTodayDeaths() {
        return todayDeaths;
    }
    public long getTodayRecovered() {
        return todayrecovered;
    }
    public void setTodayDeaths(long todayDeaths) {
        this.todayDeaths = todayDeaths;
    }

    public long getRecovered() {
        return recovered;
    }

    public void setRecovered(long recovered) {
        this.recovered = recovered;
    }

    public long getActive() {
        return active;
    }

    public void setActive(long active) {
        this.active = active;
    }

}