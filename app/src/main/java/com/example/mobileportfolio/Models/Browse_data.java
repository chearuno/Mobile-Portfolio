package com.example.mobileportfolio.Models;

public class Browse_data {

    private String title, category, image, docid,discrip;

    public Browse_data() {
    }

    public Browse_data(String title, String category, String image, String docid,String discrip) {
        this.title = title;
        this.category = category;
        this.image = image;
        this.docid = docid;
        this.discrip = discrip;

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String name) {
        this.title = name;
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
}
