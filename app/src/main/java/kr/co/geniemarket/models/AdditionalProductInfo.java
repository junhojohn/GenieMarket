package kr.co.geniemarket.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import kr.co.geniemarket.core.Const;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
public class AdditionalProductInfo extends BasicProductInfo implements Serializable {

    @SerializedName(Const.KEY_PRODUCT_PRICE)
    @Expose
    private int price = -1;

    @SerializedName(Const.KEY_PRODUCT_ITEM_CNT)
    @Expose
    private int itemCount = -1;

    @SerializedName(Const.KEY_SELLER_ID)
    @Expose
    private String sellerName;

    @SerializedName(Const.KEY_PRODUCT_BIG_CATEGORY)
    @Expose
    private String bigCategory;

    @SerializedName(Const.KEY_PRODUCT_MID_CATEGORY)
    @Expose
    private String midCategory;

    @SerializedName(Const.KEY_PRODUCT_SMALL_CATEGORY)
    @Expose
    private String smallCategory;

}
