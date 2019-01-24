package com.example.a13608.tab01.entity;

/**
 * Created by 13608 on 2018/6/6.
 */

public class Room {
    public int img;
    public String name;

    public Room(int img, String name) {
        this.img = img;
        this.name = name;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
