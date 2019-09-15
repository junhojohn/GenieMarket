package net.clipcodes.myapplication.models;

import java.io.Serializable;

public class AdditionalProductInfo extends BasicProductInfo implements Serializable {
    private int price = -1;
    private int itemCount = -1;
    private String sellerName;
    private String bigCategory;
    private String midCategory;
    private String smallCategory;

    public AdditionalProductInfo(){

    }

    public AdditionalProductInfo(String name, String description){
        super(name, description);
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getItemCount() {
        return itemCount;
    }

    public void setItemCount(int itemCount) {
        this.itemCount = itemCount;
    }

    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    public String getBigCategory() { return bigCategory; }

    public void setBigCategory(String bigCategory) { this.bigCategory = bigCategory; }

    public String getMidCategory() { return midCategory; }

    public void setMidCategory(String midCategory) { this.midCategory = midCategory; }

    public String getSmallCategory() { return smallCategory; }

    public void setSmallCategory(String smallCategory) { this.smallCategory = smallCategory; }
}
