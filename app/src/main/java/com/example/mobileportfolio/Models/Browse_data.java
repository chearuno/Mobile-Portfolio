package com.example.mobileportfolio.Models;

public class Browse_data {

    private String title, category, image, docid, discrip, imageuri;

    public Browse_data() {
    }



    public Browse_data(String title, String category, String image, String docid,String discrip,String imageuri) {
        this.title = title;
        this.category = category;
        this.image = image;
        this.docid = docid;
        this.discrip = discrip;
        this.imageuri = imageuri;

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getcategory() {
        return category;
    }

    public void setcategory(String category) {
        this.category = category;
    }

    public String getimage() {
        return image;
    }

    public void setimage(String image) {
        this.image = image;
    }

    public String getdocid() {
        return docid;
    }

    public void setdiscrip(String docid) {
        this.docid = docid;
    }

    public String getdiscrip() {
        return discrip;
    }

    public void setdocid(String discrip) {
        this.docid = discrip;
    }

    public String geturi() {
        return imageuri;
    }

    public void seturi(String imageuri) {
        this.imageuri = imageuri;
    }
}
