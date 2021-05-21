package com.example.CoronaMeter;

public class newsModel {
    private String headline,content,date,imgurl,source,description;

    public newsModel() {
    }

    public newsModel(String headline, String content,String date, String imgurl, String source, String description) {

        this.headline = headline;
        this.content = content;
        this.date = date;
        this.imgurl = imgurl;
        this.source = source;
        this.description = description;
    }


    public String getHeadline() {
        return headline;
    }

    public String getContent() {
        return content;
    }


    public String getDate() {
        return date;
    }


    public String getImgurl() {
        return imgurl;
    }


    public String  getSource() {
        return source;
    }
    public String  getDescription() {
        return description;
    }

}