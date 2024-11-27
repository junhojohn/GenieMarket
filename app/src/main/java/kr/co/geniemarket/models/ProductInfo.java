package kr.co.geniemarket.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import kr.co.geniemarket.Const;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ProductInfo {

    @SerializedName(Const.KEY_ADDITIONAL_PRODUCT_INFO)
    private List<AdditionalProductInfo> additionalProductInfo;

}
