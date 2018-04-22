package com.example.mehrnaz.androidtask.Model;

/**
 * Created by Mehrnaz on 4/22/2018.
 */
public class Country {

    String iso;
    String name;
    String phone;

    public Country(String iso, String name, String phone) {
        this.iso = iso;
        this.name = name;
        this.phone = phone;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIso() {
        return iso;
    }

    public void setIso(String iso) {
        this.iso = iso;
    }

}
