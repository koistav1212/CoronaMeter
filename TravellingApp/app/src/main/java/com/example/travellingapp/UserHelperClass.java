package com.example.travellingapp;

public class UserHelperClass {
    String name,username,phn_no,password,email;

    public UserHelperClass() {

    }

    public UserHelperClass(String name, String username, String phn_no, String password, String email) {
        this.name = name;
        this.username = username;
        this.phn_no = phn_no;
        this.password = password;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhn_no() {
        return phn_no;
    }

    public void setPhn_no(String phn_no) {
        this.phn_no = phn_no;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
