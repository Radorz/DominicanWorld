package com.example.dominicanworld;

public class item {

    String birdListName;
    int birdListImage;

    public item(String birdName,int birdImage)
    {
        this.birdListImage=birdImage;
        this.birdListName=birdName;
    }
    public String getbirdName()
    {
        return birdListName;
    }
    public int getbirdImage()
    {
        return birdListImage;
    }
}