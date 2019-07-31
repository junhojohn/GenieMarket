package net.clipcodes.myapplication.utils;

import android.graphics.Bitmap;
import android.util.Base64;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.io.ByteArrayOutputStream;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class RegisterRequest extends StringRequest {
    final static private String URL = "http://geniemarketdb.cafe24.com/Register.php";
    private Map<String, String> parameters = new LinkedHashMap<String, String>();

    public RegisterRequest(String productName, int productPrice, int productItemCnt, String productDescription, Response.Listener<String> listener){
        super(Method.POST, URL, listener, null);
        parameters.put("productName",  productName);
        parameters.put("productPrice", productPrice+"");
        parameters.put("productItemCnt", productItemCnt+"");
        parameters.put("productDescription", productDescription);
    }

    public RegisterRequest(String productName, int productPrice, int productItemCnt, String productDescription, String productImage1, Response.Listener<String> listener){
        super(Method.POST, URL, listener, null);
        parameters.put("productName",  productName);
        parameters.put("productPrice", productPrice+"");
        parameters.put("productItemCnt", productItemCnt+"");
        parameters.put("productDescription", productDescription);
        parameters.put("productImage1", productImage1);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return parameters;
    }

    public String BitMapToString(Bitmap bitmap){
        ByteArrayOutputStream baos=new  ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100, baos);    //bitmap compress
        byte [] arr=baos.toByteArray();
        String image= Base64.encodeToString(arr, Base64.DEFAULT);
        String temp="";
        try{
            temp="&imagedevice="+ URLEncoder.encode(image,"utf-8");
        }catch (Exception e){
            Log.e("exception",e.toString());
        }
        return temp;
    }

}
