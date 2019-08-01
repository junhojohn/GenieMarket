package net.clipcodes.myapplication.models;

import java.io.Serializable;

public class AdditionalSellerInfo extends BasicSellerInfo implements Serializable {
    private String sellerPWD;

    public AdditionalSellerInfo(){

    }

    public AdditionalSellerInfo(String sellerID, String sellerName, String sellerDescription){
        super(sellerID, sellerName, sellerDescription);
    }

    public String getSellerPWD() {
        return sellerPWD;
    }

    public void setSellerPWD(String sellerPWD) {
        this.sellerPWD = sellerPWD;
    }
}
