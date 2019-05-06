package net.clipcodes.myapplication.models;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class FlowerData {

    private String flowerName;
    private String flowerDescription;
    private ArrayList<Integer> flowerImageList;

    public FlowerData(String flowerName, String flowerDescription) {
        this.flowerName = flowerName;
        this.flowerDescription = flowerDescription;
    }

    public String getFlowerName() {
        return flowerName;
    }

    public String getFlowerDescription() {
        return flowerDescription;
    }

    public ArrayList<Integer> getFlowerImageList() {
        if(flowerImageList == null){
            flowerImageList = new ArrayList<Integer>();
        }
        return flowerImageList;
    }
}