package kr.co.geniemarket.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

import kr.co.geniemarket.core.Const;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BasicProductInfo implements Serializable {

    @SerializedName(Const.KEY_PRODUCT_NAME)
    @Expose
    private String name;

    @SerializedName(Const.KEY_PRODUCT_DESC)
    @Expose
    private String description;

    @SerializedName(Const.KEY_PRODUCT_IMG)
    @Expose
    private String productImg;
    
    private ArrayList<String> imageURLPathList;

}