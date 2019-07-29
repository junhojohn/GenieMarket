package net.clipcodes.myapplication.models;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * <b></b>
 * <p>This class is used to </p>
 * Created by Rohit.
 */
public class ParentCategoryItem implements Serializable {
    private String parentName;
    private ArrayList<ChildCategoryItem> childDataItems;

    public ParentCategoryItem(String parentName, ArrayList<ChildCategoryItem> childDataItems) {
        this.parentName = parentName;
        this.childDataItems = childDataItems;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public ArrayList<ChildCategoryItem> getChildDataItems() {
        return childDataItems;
    }

    public void setChildDataItems(ArrayList<ChildCategoryItem> childDataItems) {
        this.childDataItems = childDataItems;
    }
}