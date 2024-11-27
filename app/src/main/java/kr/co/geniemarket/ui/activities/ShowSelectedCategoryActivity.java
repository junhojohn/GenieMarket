package kr.co.geniemarket.ui.activities;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import kr.co.geniemarket.R;
import kr.co.geniemarket.models.AdditionalProductInfo;
import kr.co.geniemarket.ui.adapters.BestProductListAdapter;
import kr.co.geniemarket.ui.adapters.ShowSelectedCategoryProductListAdapter;

import java.util.ArrayList;
import java.util.List;

public class ShowSelectedCategoryActivity extends AppCompatActivity {
    RecyclerView mRecyclerView;
    private static ArrayList<AdditionalProductInfo> productItemList  = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        productItemList = (ArrayList<AdditionalProductInfo>)getIntent().getSerializableExtra("productItemList");
        setContentView(R.layout.activity_show_selected_category);
        mRecyclerView = (RecyclerView)findViewById(R.id.recyclerviewForSelectedCategory) ;

        GridLayoutManager mGridLayoutManager = new GridLayoutManager(this, 2);
        mRecyclerView.setLayoutManager(mGridLayoutManager);
        showList(productItemList);
    }

    protected  void showList(List<AdditionalProductInfo> productItemList){
        ShowSelectedCategoryProductListAdapter showSelectedCategoryProductListAdapter = new ShowSelectedCategoryProductListAdapter(this, productItemList);
        mRecyclerView.setAdapter(showSelectedCategoryProductListAdapter);
    }
}
