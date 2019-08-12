package net.clipcodes.myapplication.models;

import android.graphics.Bitmap;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class BasicProductInfo implements Serializable {

    private String name;
    private String description;
    private ArrayList<String> imageURLPathList;

    public BasicProductInfo() {

    }

    public BasicProductInfo(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void setName(String name) { this.name = name; }

    public void setDescription(String description) { this.description = description; }

    public ArrayList<String> getImageURLPathList() {
        if(imageURLPathList == null){
            imageURLPathList = new ArrayList<String>();
        }
        return imageURLPathList;
    }

}