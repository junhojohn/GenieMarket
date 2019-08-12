package net.clipcodes.myapplication.ui.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.clipcodes.myapplication.models.AdditionalProductInfo;
import net.clipcodes.myapplication.models.BasicProductInfo;
import net.clipcodes.myapplication.ui.ConnectionConst;
import net.clipcodes.myapplication.ui.adapters.BestProductListAdapter;
import net.clipcodes.myapplication.R;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.List;


public class BestProductFragment extends Fragment {
    RecyclerView mRecyclerView;
    List<AdditionalProductInfo> productItemList = new ArrayList<>();
    AdditionalProductInfo productItem;
    String myJSON;
    JSONArray peoples = null;
    View seller;
    private static final String TAG_RESULTS             = "result";
    private static final String TAG_PRODUCT_NAME        = "productName";
    private static final String TAG_SELLER_ID           = "sellerID";
    private static final String TAG_PRODUCT_ITEM_CNT    = "productItemCnt";
    private static final String TAG_PRODUCT_PRICE       = "productPrice";
    private static final String TAG_PRODUCT_DESC        = "productDescription";
    private static final String TAG_PRODUCT_IMG         = "imageFilePath";
    private static final int IDX_TAG_PRODUCT_NAME        = 0;
    private static final int IDX_TAG_SELLER_ID           = 1;
    private static final int IDX_TAG_PRODUCT_ITEM_CNT    = 2;
    private static final int IDX_TAG_PRODUCT_PRICE       = 3;
    private static final int IDX_TAG_PRODUCT_DESC        = 4;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        seller = inflater.inflate(R.layout.tab_best_product, container, false);
        mRecyclerView = seller.findViewById(R.id.recyclerview);
        GridLayoutManager mGridLayoutManager = new GridLayoutManager(getActivity(), 2);
        mRecyclerView.setLayoutManager(mGridLayoutManager);

        getData(ConnectionConst.PRODUCT_INFO_SEARCH_SERVER_URL);


        return seller;
    }

    public void getData(String url){

        class GetDataJSON extends AsyncTask<String, Void, String> {

            @Override
            protected String doInBackground(String... params) {
                String result = null;
                DefaultHttpClient httpClient = new DefaultHttpClient(new BasicHttpParams());
                HttpPost httpPost = new HttpPost(ConnectionConst.PRODUCT_INFO_SEARCH_SERVER_URL);

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

                return result;
            }

            @Override
            protected void onPostExecute(String result) {
                myJSON = result;
                showList();
            }
        }

        GetDataJSON getDataJSON = new GetDataJSON();
        getDataJSON.execute(url);
    }

    protected  void showList(){
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
                String productImg       = productJsonData.getString(TAG_PRODUCT_IMG);

                productItem = new AdditionalProductInfo(productName, productDesc);
                productItem.getImageList().add(R.drawable.rose);
                productItem.getImageList().add(R.drawable.rose);
                productItem.getImageList().add(R.drawable.rose);
                productItem.setSellerName(sellerID);
                productItem.setItemCount(Integer.parseInt(productItemCnt));
                productItem.setPrice(Integer.parseInt(productPrice));
                productItemList.add(productItem);
            }

            BestProductListAdapter bestProductListAdapter = new BestProductListAdapter(getActivity(), productItemList);
            mRecyclerView.setAdapter(bestProductListAdapter);
        }catch (JSONException e){
            e.printStackTrace();
        }
    }
}