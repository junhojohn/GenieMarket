package kr.co.geniemarket.models;

import java.io.Serializable;

/**
 * <b></b>
 * <p>This class is used to </p>
 * Created by Rohit.
 */
public class ChildCategoryItem implements Serializable {
    private String childName;

    public ChildCategoryItem(String childName) {
        this.childName = childName;
    }

    public String getChildName() {
        return childName;
    }

    public void setChildName(String childName) {
        this.childName = childName;
    }
}