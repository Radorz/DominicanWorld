package com.example.dominicanworld;

public class modelo {
    String name;
    int image;
    int id;
    String stringimg;
    public modelo(String name, int image, int id,  String stringimg) {
        this.name = name;
        this.image = image;
        this.id = id;
        this.stringimg=stringimg;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public String getStringimg() {
        return stringimg;
    }

    public void setStringimg(String stringimg) {
        this.stringimg = stringimg;
    }

}