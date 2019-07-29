package net.clipcodes.myapplication.models;

import java.io.Serializable;

public class SellerInfo implements Serializable {
    private String sellerName;
    private String sellerID;
    private String sellerPWD;

    public SellerInfo(){

    }

    public String getSellerName() {
        return sellerName;
    }

    public String getSellerID() {
        return sellerID;
    }

    public String getSellerPWD() {
        return sellerPWD;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    public void setSellerID(String sellerID) {
        this.sellerID = sellerID;
    }

    public void setSellerPWD(String sellerPWD) {
        this.sellerPWD = sellerPWD;
    }
}
