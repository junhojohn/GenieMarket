package net.clipcodes.myapplication.ui.fragments;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.clipcodes.myapplication.models.AdditionalProductInfo;
import net.clipcodes.myapplication.ui.ConnectionConst;
import net.clipcodes.myapplication.ui.adapters.BestProductListAdapter;
import net.clipcodes.myapplication.R;
import net.clipcodes.myapplication.utils.Libraries;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class BestProductFragment extends Fragment {
    RecyclerView mRecyclerView;




    View seller;
    private static final String TAG_RESULTS             = "result";
    private static final String TAG_PRODUCT_NAME        = "productName";
    private static final String TAG_SELLER_ID           = "sellerID";
    private static final String TAG_PRODUCT_ITEM_CNT    = "productItemCnt";
    private static final String TAG_PRODUCT_PRICE       = "productPrice";
    private static final String TAG_PRODUCT_DESC        = "productDescription";
    private static final String TAG_PRODUCT_BIG_CATEGORY= "bigCategory";
    private static final String TAG_PRODUCT_IMG         = "imageFilePath";
    private List<AdditionalProductInfo> productItemList  = null;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        seller = inflater.inflate(R.layout.tab_best_product, container, false);
        mRecyclerView = seller.findViewById(R.id.recyclerviewForBestProduct);
        GridLayoutManager mGridLayoutManager = new GridLayoutManager(getActivity(), 2);
        mRecyclerView.setLayoutManager(mGridLayoutManager);

        getData();

        return seller;
    }

    public void getData(){

        class GetDataJSON extends AsyncTask<String, Void, List> {

            @Override
            protected List<AdditionalProductInfo> doInBackground(String... params) {
                if(productItemList == null || productItemList.size() == 0){
                    String myJSON = getHttpJSONData(ConnectionConst.PRODUCT_INFO_SEARCH_SERVER_URL);
                    productItemList = setJSONDataToProductItem(myJSON);
                }

                return productItemList;
            }

            @Override
            protected void onPostExecute(List productItemList) {
                showList(productItemList);
            }
        }

        GetDataJSON getDataJSON = new GetDataJSON();
        getDataJSON.execute();

    }

    protected String getHttpJSONData(String url){
        long currTime = System.currentTimeMillis();
        String result = null;
        DefaultHttpClient httpClient = new DefaultHttpClient(new BasicHttpParams());
        HttpPost httpPost = new HttpPost(url);

        httpPost.setHeader("Content-type", "application/json");

        InputStream inputStream = null;
        try{
            HttpResponse response = httpClient.execute(httpPost);
            HttpEntity entity = response.getEntity();

            inputStream = entity.getContent();
            // json is UTF-8 by default
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "utf8"), 8);
            StringBuilder sb = new StringBuilder();

            String line = null;
            while ((line = reader.readLine()) != null)
            {
                sb.append(line + "\n");
            }
            result = sb.toString();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try{if(inputStream != null)inputStream.close();}catch(Exception squish){}
        }
        System.out.println("getHttpJSONData: " + (System.currentTimeMillis() - currTime));
        return result;
    }

    protected List<AdditionalProductInfo> setJSONDataToProductItem(String myJSON){
        long currTime = System.currentTimeMillis();
        List<AdditionalProductInfo> productItemList = new ArrayList<>();
        AdditionalProductInfo productItem = null;
        JSONArray peoples = null;

        try{
            JSONObject jsonObj = new JSONObject(myJSON);
            peoples = jsonObj.getJSONArray(TAG_RESULTS);
            for(int i = 0 ; i < peoples.length() ; i ++){
                JSONObject productJsonData = peoples.getJSONObject(i);
                String productName      = productJsonData.getString(TAG_PRODUCT_NAME);
                String sellerID         = productJsonData.getString(TAG_SELLER_ID);
                String productPrice     = productJsonData.getString(TAG_PRODUCT_PRICE);
                String productItemCnt   = productJsonData.getString(TAG_PRODUCT_ITEM_CNT);
                String productDesc      = productJsonData.getString(TAG_PRODUCT_DESC);
                String bigCategory      = productJsonData.getString(TAG_PRODUCT_BIG_CATEGORY);
                String productImg       = productJsonData.getString(TAG_PRODUCT_IMG);

                if(bigCategory.equals(getString(R.string.title_best_product))){
                    productItem = getContainObject(productItemList, productName);
                    if(productItem == null){
                        productItem = new AdditionalProductInfo(productName, productDesc);
                        productItem.setSellerName(sellerID);
                        productItem.setItemCount(Integer.parseInt(productItemCnt));
                        productItem.setPrice(Integer.parseInt(productPrice));
                        productItem.setBigCategory(bigCategory);
                        productItemList.add(productItem);
                    }

                    Bitmap bitmap = getProductImages(ConnectionConst.IMAGE_DOWNLOAD_SERVER_URL + productImg);
                    Libraries.saveBitmapToJpeg(bitmap, this.getActivity().getCacheDir().toString(), productImg);
                    productItem.getImageURLPathList().add(this.getActivity().getCacheDir().toString() + "/" + productImg);
                    bitmap = null;


                }
            }
            System.out.println("setJSONDataToProductItem: " + (System.currentTimeMillis() - currTime));
        }catch (JSONException e){
            e.printStackTrace();
        }
        return productItemList;
    }

    public AdditionalProductInfo getContainObject(List<AdditionalProductInfo> productItemList, String productName){
        for(AdditionalProductInfo productInfo : productItemList){
            if(productInfo.getName().equals(productName)){
                return productInfo;
            }
        }
        return null;
    }

    protected Bitmap getProductImages(String imageFileUrl){
        Bitmap bmImg = null;
        try{
            URL myFileUrl = new URL(imageFileUrl);
            HttpURLConnection conn = (HttpURLConnection)myFileUrl.openConnection();
            conn.setDoInput(true);
            conn.connect();

            InputStream is = conn.getInputStream();

            bmImg = BitmapFactory.decodeStream(is);


        }catch(IOException e){
            e.printStackTrace();
        }
        return  bmImg;
    }

    protected  void showList(List<AdditionalProductInfo> productItemList){
        BestProductListAdapter bestProductListAdapter = new BestProductListAdapter(getActivity(), productItemList);
        mRecyclerView.setAdapter(bestProductListAdapter);
    }
}