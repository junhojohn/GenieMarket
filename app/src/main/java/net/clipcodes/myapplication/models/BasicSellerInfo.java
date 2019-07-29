package net.clipcodes.myapplication.models;

import java.io.Serializable;

public class BasicSellerInfo implements Serializable {
    private String sellerName;
    private String sellerDescription;


    public BasicSellerInfo(){

    }

    public BasicSellerInfo(String sellerName, String sellerDescription){
        this.sellerName = sellerName;
        this.sellerDescription = sellerDescription;
    }

    public String getSellerName() {
        return sellerName;
    }

    public String getSellerDescription() {
        return sellerDescription;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    public void setSellerDescription(String sellerDescription) {
        this.sellerDescription = sellerDescription;
    }

}
