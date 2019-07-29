package net.clipcodes.myapplication.models;

import java.io.Serializable;

public class AdditionalSellerInfo extends BasicSellerInfo implements Serializable {
    private String sellerID;
    private String sellerPWD;

    public AdditionalSellerInfo(){

    }

    public AdditionalSellerInfo(String name, String description){
        super(name, description);
    }

    public String getSellerID() {
        return sellerID;
    }

    public String getSellerPWD() {
        return sellerPWD;
    }

    public void setSellerID(String sellerID) {
        this.sellerID = sellerID;
    }

    public void setSellerPWD(String sellerPWD) {
        this.sellerPWD = sellerPWD;
    }
}
