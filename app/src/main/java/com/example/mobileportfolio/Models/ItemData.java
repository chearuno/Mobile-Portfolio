package com.example.mobileportfolio.Models;

public class ItemData {
    private String name,catid;

    public ItemData() {
    }



    public ItemData(String name,String catid) {
        this.name = name;
        this.catid = catid;

    }

    public String getname() {
        return name;
    }

    public void setname(String name) {
        this.name = name;
    }
    public String getcatid() {
        return catid;
    }

    public void setCatid(String catid) {
        this.catid = catid;
    }

}
