package com.bignerdranch.android.mad_project;

import java.util.Date;

public class UserAccount {

 private String name, password, email, dob;

    public UserAccount() {
    }

    public UserAccount(String name, String password, String email, String dob) {
        this.name = name;
        this.password = password;
        this.email = email;
        this.dob = dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getDob() {
        return dob;
    }
}
