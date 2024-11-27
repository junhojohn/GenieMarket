package kr.co.geniemarket.models;

import java.io.Serializable;

public class BasicSellerInfo implements Serializable {
    private String sellerID;
    private String sellerName;
    private String sellerDescription;
    private String sellerKakaoAccount;
    private String sellerPhoneNumber;


    public BasicSellerInfo(){

    }

    public BasicSellerInfo(String sellerID, String sellerName, String sellerDescription){
        this.sellerID = sellerID;
        this.sellerName = sellerName;
        this.sellerDescription = sellerDescription;
    }

    public String getSellerID() {
        return sellerID;
    }

    public void setSellerID(String sellerID) {
        this.sellerID = sellerID;
    }

    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    public String getSellerDescription() {
        return sellerDescription;
    }

    public void setSellerDescription(String sellerDescription) {
        this.sellerDescription = sellerDescription;
    }

    public String getSellerKakaoAccount() {
        return sellerKakaoAccount;
    }

    public void setSellerKakaoAccount(String sellerKakaoAccount) {
        this.sellerKakaoAccount = sellerKakaoAccount;
    }

    public String getSellerPhoneNumber() {
        return sellerPhoneNumber;
    }

    public void setSellerPhoneNumber(String sellerPhoneNumber) {
        this.sellerPhoneNumber = sellerPhoneNumber;
    }
}
