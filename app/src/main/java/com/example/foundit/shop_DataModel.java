package com.example.foundit;

public class shop_DataModel {
    String shop_content_stamp_cont;
    int shop_content_img;
    String shop_content_name;

    public shop_DataModel(String shop_content_stamp_cont, int shop_content_img, String shop_content_name) {
        this.shop_content_stamp_cont = shop_content_stamp_cont;
        this.shop_content_img = shop_content_img;
        this.shop_content_name = shop_content_name;
    }
    public String getShop_content_stamp_cont() {
        return shop_content_stamp_cont;
    }

    public void setShop_content_stamp_cont(String shop_content_stamp_cont) {
        this.shop_content_stamp_cont = shop_content_stamp_cont;
    }

    public int getShop_content_img() {
        return shop_content_img;
    }

    public void setShop_content_img(int shop_content_img) {
        this.shop_content_img = shop_content_img;
    }

    public String getShop_content_name() {
        return shop_content_name;
    }

    public void setShop_content_name(String shop_content_name) {
        this.shop_content_name = shop_content_name;
    }





}
