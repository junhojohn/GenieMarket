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
    List<BasicProductInfo> productItemList = new ArrayList<>();
    BasicProductInfo productItem;
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

        getData("http://geniemarketdb.cafe24.com/ProductSearch.php");


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

                productItem = new BasicProductInfo(productName, productDesc);
                productItem.getImageList().add(R.drawable.rose);
                productItem.getImageList().add(R.drawable.rose);
                productItem.getImageList().add(R.drawable.rose);
                productItemList.add(productItem);
            }

            productItem = new BasicProductInfo("Rose", getString(R.string.description_flower_rose));
            productItem.getImageList().add(R.drawable.rose);
            productItem.getImageList().add(R.drawable.rose);
            productItem.getImageList().add(R.drawable.rose);
            productItemList.add(productItem);
            productItem = new BasicProductInfo("Carnation", getString(R.string.description_flower_carnation));
            productItem.getImageList().add(R.drawable.carnation);
            productItem.getImageList().add(R.drawable.carnation);
            productItem.getImageList().add(R.drawable.carnation);
            productItemList.add(productItem);
            productItem = new BasicProductInfo("Tulip", getString(R.string.description_flower_tulip));
            productItem.getImageList().add(R.drawable.tulip);
            productItem.getImageList().add(R.drawable.tulip);
            productItem.getImageList().add(R.drawable.tulip);
            productItemList.add(productItem);
            productItem = new BasicProductInfo("Daisy", getString(R.string.description_flower_daisy));
            productItem.getImageList().add(R.drawable.daisy);
            productItem.getImageList().add(R.drawable.daisy);
            productItem.getImageList().add(R.drawable.daisy);
            productItemList.add(productItem);
            productItem = new BasicProductInfo("Sunflower", getString(R.string.description_flower_sunflower));
            productItem.getImageList().add(R.drawable.sunflower);
            productItem.getImageList().add(R.drawable.sunflower);
            productItem.getImageList().add(R.drawable.sunflower);
            productItemList.add(productItem);
            productItem = new BasicProductInfo("Daffodil", getString(R.string.description_flower_daffodil));
            productItem.getImageList().add(R.drawable.daffodil);
            productItem.getImageList().add(R.drawable.daffodil);
            productItem.getImageList().add(R.drawable.daffodil);
            productItemList.add(productItem);
            productItem = new BasicProductInfo("Gerbera", getString(R.string.description_flower_gerbera));
            productItem.getImageList().add(R.drawable.gerbera);
            productItem.getImageList().add(R.drawable.gerbera);
            productItem.getImageList().add(R.drawable.gerbera);
            productItemList.add(productItem);
            productItem = new BasicProductInfo("Orchid", getString(R.string.description_flower_orchid));
            productItem.getImageList().add(R.drawable.orchid);
            productItem.getImageList().add(R.drawable.orchid);
            productItem.getImageList().add(R.drawable.orchid);
            productItemList.add(productItem);
            productItem = new BasicProductInfo("Iris", getString(R.string.description_flower_iris));
            productItem.getImageList().add(R.drawable.iris);
            productItem.getImageList().add(R.drawable.iris);
            productItem.getImageList().add(R.drawable.iris);
            productItemList.add(productItem);
            productItem = new BasicProductInfo("Lilac", getString(R.string.description_flower_lilac));
            productItem.getImageList().add(R.drawable.lilac);
            productItem.getImageList().add(R.drawable.lilac);
            productItem.getImageList().add(R.drawable.lilac);
            productItemList.add(productItem);

            BestProductListAdapter bestProductListAdapter = new BestProductListAdapter(getActivity(), productItemList);
            mRecyclerView.setAdapter(bestProductListAdapter);
        }catch (JSONException e){
            e.printStackTrace();;
        }
    }
}




















//public class BestProductFragment extends Fragment {
//    RecyclerView mRecyclerView;
//    List<BasicProductInfo> productItemList = new ArrayList<>();
//    BasicProductInfo productItem;
//    phpDown task;
//    View seller;
//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//
//
//    }
//
//    @Nullable
//    @Override
//    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        seller = inflater.inflate(R.layout.tab_best_product, container, false);
//        mRecyclerView = seller.findViewById(R.id.recyclerview);
//        GridLayoutManager mGridLayoutManager = new GridLayoutManager(getActivity(), 2);
//        mRecyclerView.setLayoutManager(mGridLayoutManager);
//
//        task = new phpDown();
//        task.execute();
//
//
//        return seller;
//    }
//
//    private class phpDown extends AsyncTask<String, Integer,String> {
//
//
//
//        @Override
//        protected String doInBackground(String... urls) {
//            StringBuilder jsonHtml = new StringBuilder();
//            String boundary = "*****";
//            try{
//                // 연결 url 설정
//                URL url = new URL(ConnectionConst.PRODUCT_INFO_SEARCH_SERVER_URL);
//                // 커넥션 객체 생성
//                HttpURLConnection conn = (HttpURLConnection)url.openConnection();
//                // 연결되었으면.
//                if(conn != null){
//                    conn.setConnectTimeout(10000);
//                    conn.setDoInput(true);
//                    conn.setDoOutput(true);
//                    conn.setUseCaches(false);
//                    conn.setRequestProperty("Connection", "Keep-Alive");
//                    conn.setRequestProperty("ENCTYPE", "multipart/form-data");
//                    conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
//                    // 연결되었음 코드가 리턴되면.
//
//                    if(conn.getResponseCode() == HttpURLConnection.HTTP_OK){
//                        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf8"));
//                        for(;;){
//                            // 웹상에 보여지는 텍스트를 라인단위로 읽어 저장.
//                            String line = br.readLine();
//                            if(line == null) break;
//                            // 저장된 텍스트 라인을 jsonHtml에 붙여넣음
//                            jsonHtml.append(line + "\n");
//                        }
//                        br.close();
//                    }else{
//                        InputStreamReader isr = new InputStreamReader(conn.getErrorStream());
//                        BufferedReader br = new BufferedReader(isr);
//                        String str = null;
//                        while ((str = br.readLine()) != null) {
//                            System.out.println(str);
//                        }
//                        br.close();
//                        isr.close();;
//                    }
//                    conn.disconnect();
//                }
//            } catch(Exception ex){
//                ex.printStackTrace();
//            }
//            return jsonHtml.toString();
//
//        }
//
//        protected void onPostExecute(String str){
//
//            String imgurl;
//            String txt1;
//            String txt2;
//            try{
//
//                JSONObject root = new JSONObject(str);
////                JSONArray ja = root.getJSONArray("results");
////                for(int i=0; i<ja.length(); i++){
////                    JSONObject jo = ja.getJSONObject(i);
////                    imgurl = jo.getString("imgurl");
////                    txt1 = jo.getString("txt1");
////                    txt2 = jo.getString("txt2");
////
////                }
//            }catch(JSONException e){
//                Log.e("error:", e.getMessage());
//            }finally {
//                productItem = new BasicProductInfo("Rose", getString(R.string.description_flower_rose));
//                productItem.getImageList().add(R.drawable.rose);
//                productItem.getImageList().add(R.drawable.rose);
//                productItem.getImageList().add(R.drawable.rose);
//                productItemList.add(productItem);
//                productItem = new BasicProductInfo("Carnation", getString(R.string.description_flower_carnation));
//                productItem.getImageList().add(R.drawable.carnation);
//                productItem.getImageList().add(R.drawable.carnation);
//                productItem.getImageList().add(R.drawable.carnation);
//                productItemList.add(productItem);
//                productItem = new BasicProductInfo("Tulip", getString(R.string.description_flower_tulip));
//                productItem.getImageList().add(R.drawable.tulip);
//                productItem.getImageList().add(R.drawable.tulip);
//                productItem.getImageList().add(R.drawable.tulip);
//                productItemList.add(productItem);
//                productItem = new BasicProductInfo("Daisy", getString(R.string.description_flower_daisy));
//                productItem.getImageList().add(R.drawable.daisy);
//                productItem.getImageList().add(R.drawable.daisy);
//                productItem.getImageList().add(R.drawable.daisy);
//                productItemList.add(productItem);
//                productItem = new BasicProductInfo("Sunflower", getString(R.string.description_flower_sunflower));
//                productItem.getImageList().add(R.drawable.sunflower);
//                productItem.getImageList().add(R.drawable.sunflower);
//                productItem.getImageList().add(R.drawable.sunflower);
//                productItemList.add(productItem);
//                productItem = new BasicProductInfo("Daffodil", getString(R.string.description_flower_daffodil));
//                productItem.getImageList().add(R.drawable.daffodil);
//                productItem.getImageList().add(R.drawable.daffodil);
//                productItem.getImageList().add(R.drawable.daffodil);
//                productItemList.add(productItem);
//                productItem = new BasicProductInfo("Gerbera", getString(R.string.description_flower_gerbera));
//                productItem.getImageList().add(R.drawable.gerbera);
//                productItem.getImageList().add(R.drawable.gerbera);
//                productItem.getImageList().add(R.drawable.gerbera);
//                productItemList.add(productItem);
//                productItem = new BasicProductInfo("Orchid", getString(R.string.description_flower_orchid));
//                productItem.getImageList().add(R.drawable.orchid);
//                productItem.getImageList().add(R.drawable.orchid);
//                productItem.getImageList().add(R.drawable.orchid);
//                productItemList.add(productItem);
//                productItem = new BasicProductInfo("Iris", getString(R.string.description_flower_iris));
//                productItem.getImageList().add(R.drawable.iris);
//                productItem.getImageList().add(R.drawable.iris);
//                productItem.getImageList().add(R.drawable.iris);
//                productItemList.add(productItem);
//                productItem = new BasicProductInfo("Lilac", getString(R.string.description_flower_lilac));
//                productItem.getImageList().add(R.drawable.lilac);
//                productItem.getImageList().add(R.drawable.lilac);
//                productItem.getImageList().add(R.drawable.lilac);
//                productItemList.add(productItem);
//
//                BestProductListAdapter bestProductListAdapter = new BestProductListAdapter(getActivity(), productItemList);
//                mRecyclerView.setAdapter(bestProductListAdapter);
//            }
//        }
//
//    }
//}