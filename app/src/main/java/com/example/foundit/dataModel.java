package com.example.foundit;

public class dataModel {
    String list_title;
    String list_title_sub;
    int list_img;
    String list_stamp_cnt;

    public dataModel(String list_title, String list_title_sub, int list_img, String list_stamp_cnt){
        this.list_title = list_title;
        this.list_title_sub = list_title_sub;
        this.list_img = list_img;
        this.list_stamp_cnt = list_stamp_cnt;
    }
    public void setList_title(String list_title) {
        this.list_title = list_title;
    }

    public void setList_title_sub(String list_title_sub) {
        this.list_title_sub = list_title_sub;
    }

    public void setList_img(int list_img) {
        this.list_img = list_img;
    }

    public void setList_stamp_cnt(String list_stamp_cnt) {
        this.list_stamp_cnt = list_stamp_cnt;
    }



    public String getList_title() {
        return list_title;
    }

    public String getList_title_sub() {
        return list_title_sub;
    }

    public int getList_img() {
        return list_img;
    }

    public String getList_stamp_cnt() {
        return list_stamp_cnt;
    }

}
