package com.example.yyl.myapplication0.recylerview;

public class Item {
    private String name;
    private int imageId;

    public Item(String name, int imageId) {
        this.name = name;
        this.imageId = imageId;
    }

    public String getName(){
        return this.name;
    }

    public int getImageId(){
        return this.imageId;
    }

}
