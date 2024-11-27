package kr.co.geniemarket.utils;
import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import kr.co.geniemarket.ui.ConnectionConst;
import java.util.LinkedHashMap;
import java.util.Map;

public class RegisterRequest extends StringRequest {

    private Map<String, String> parameters = new LinkedHashMap<String, String>();

    public RegisterRequest(String productImage1, String productName, String sellerID, Response.Listener<String> listener){
        super(Method.POST, ConnectionConst.IMAGE_REGISTER_SERVER_URL, listener, null);
        parameters.clear();
        parameters.put(ConnectionConst.PARAM_JSON_PRODUCT_IMAGE1, productImage1);
        parameters.put(ConnectionConst.PARAM_JSON_PRODUCT_NAME,  productName);
        parameters.put(ConnectionConst.PARAM_JSON_SELLER_ID, sellerID);
    }

    public RegisterRequest(String productName, int productPrice, int productItemCnt, String productDescription, String sellerID, Response.Listener<String> listener){
        super(Method.POST, ConnectionConst.PRODUCT_REGISTER_SERVER_URL, listener, null);
        parameters.clear();
        parameters.put(ConnectionConst.PARAM_JSON_PRODUCT_NAME,  productName);
        parameters.put(ConnectionConst.PARAM_JSON_PRODUCT_PRICE, productPrice+"");
        parameters.put(ConnectionConst.PARAM_JSON_PRODUCT_ITEM_CNT, productItemCnt+"");
        parameters.put(ConnectionConst.PARAM_JSON_PRODUCT_DESC, productDescription);
        parameters.put(ConnectionConst.PARAM_JSON_SELLER_ID, sellerID);

    }

    public RegisterRequest(String productName, int productPrice, int productItemCnt, String productDescription, String sellerID, String productImage1, Response.Listener<String> listener){
        super(Method.POST, ConnectionConst.PRODUCT_REGISTER_SERVER_URL, listener, null);
        parameters.clear();
        parameters.put(ConnectionConst.PARAM_JSON_PRODUCT_NAME,  productName);
        parameters.put(ConnectionConst.PARAM_JSON_PRODUCT_PRICE, productPrice+"");
        parameters.put(ConnectionConst.PARAM_JSON_PRODUCT_ITEM_CNT, productItemCnt+"");
        parameters.put(ConnectionConst.PARAM_JSON_PRODUCT_DESC, productDescription);
        parameters.put(ConnectionConst.PARAM_JSON_SELLER_ID, sellerID);
        parameters.put(ConnectionConst.PARAM_JSON_PRODUCT_IMAGE1, productImage1);

    }

    public RegisterRequest(String productName, int productPrice, int productItemCnt, String productDescription, String bigCategory, String sellerID, String productImage1, Response.Listener<String> listener){
        super(Method.POST, ConnectionConst.PRODUCT_REGISTER_SERVER_URL, listener, null);
        parameters.clear();
        parameters.put(ConnectionConst.PARAM_JSON_PRODUCT_NAME,  productName);
        parameters.put(ConnectionConst.PARAM_JSON_PRODUCT_PRICE, productPrice+"");
        parameters.put(ConnectionConst.PARAM_JSON_PRODUCT_ITEM_CNT, productItemCnt+"");
        parameters.put(ConnectionConst.PARAM_JSON_PRODUCT_DESC, productDescription);
        parameters.put(ConnectionConst.PARAM_JSON_BIG_CATEGORY, bigCategory);
        parameters.put(ConnectionConst.PARAM_JSON_SELLER_ID, sellerID);
        parameters.put(ConnectionConst.PARAM_JSON_PRODUCT_IMAGE1, productImage1);

    }

    public RegisterRequest(String productName, int productPrice, int productItemCnt, String productDescription, String bigCategory, String sellerID, Map<String, String> productImageMap, Response.Listener<String> listener){
        super(Method.POST, ConnectionConst.PRODUCT_REGISTER_SERVER_URL, listener, null);
        parameters.clear();
        parameters.put(ConnectionConst.PARAM_JSON_PRODUCT_NAME,  productName);
        parameters.put(ConnectionConst.PARAM_JSON_PRODUCT_PRICE, productPrice+"");
        parameters.put(ConnectionConst.PARAM_JSON_PRODUCT_ITEM_CNT, productItemCnt+"");
        parameters.put(ConnectionConst.PARAM_JSON_PRODUCT_DESC, productDescription);
        parameters.put(ConnectionConst.PARAM_JSON_BIG_CATEGORY, bigCategory);
        parameters.put(ConnectionConst.PARAM_JSON_SELLER_ID, sellerID);
        for(String key : productImageMap.keySet()){
            parameters.put(key, productImageMap.get(key));
        }

    }

    public RegisterRequest(String productName, int productPrice, int productItemCnt, String productDescription, String bigCategory, String midCategory, String smallCategory, String sellerID, Map<String, String> productImageMap, Response.Listener<String> listener){
        super(Method.POST, ConnectionConst.PRODUCT_REGISTER_SERVER_URL, listener, null);
        parameters.clear();
        parameters.put(ConnectionConst.PARAM_JSON_PRODUCT_NAME,  productName);
        parameters.put(ConnectionConst.PARAM_JSON_PRODUCT_PRICE, productPrice+"");
        parameters.put(ConnectionConst.PARAM_JSON_PRODUCT_ITEM_CNT, productItemCnt+"");
        parameters.put(ConnectionConst.PARAM_JSON_PRODUCT_DESC, productDescription);
        parameters.put(ConnectionConst.PARAM_JSON_BIG_CATEGORY, bigCategory);
        parameters.put(ConnectionConst.PARAM_JSON_MID_CATEGORY, midCategory);
        parameters.put(ConnectionConst.PARAM_JSON_SMALL_CATEGORY, smallCategory);
        parameters.put(ConnectionConst.PARAM_JSON_SELLER_ID, sellerID);
        for(String key : productImageMap.keySet()){
            parameters.put(key, productImageMap.get(key));
        }

    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return parameters;
    }

}
