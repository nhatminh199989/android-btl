package com.example.myapplication;

import java.io.Serializable;

public class Item implements Serializable {
    private int id;
    private String title;
    private String date;
    private String desc;
    private String category;

    public Item() {
    }

    public Item(int id, String title, String date, String desc, String category) {
        this.id = id;
        this.title = title;
        this.date = date;
        this.desc = desc;
        this.category = category;
    }

    public Item(String title, String date, String desc, String category) {
        this.title = title;
        this.date = date;
        this.desc = desc;
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
