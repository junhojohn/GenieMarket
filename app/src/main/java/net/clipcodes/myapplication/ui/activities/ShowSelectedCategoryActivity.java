package net.clipcodes.myapplication.ui.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import net.clipcodes.myapplication.R;
import net.clipcodes.myapplication.models.AdditionalProductInfo;
import net.clipcodes.myapplication.ui.adapters.BestProductListAdapter;
import net.clipcodes.myapplication.ui.adapters.ShowSelectedCategoryProductListAdapter;

import java.util.List;

public class ShowSelectedCategoryActivity extends AppCompatActivity {
    RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("testParam", getIntent().getStringExtra("testParam"));
        setContentView(R.layout.activity_show_selected_category);
        mRecyclerView = (RecyclerView)findViewById(R.id.recyclerviewForSelectedCategory) ;

        GridLayoutManager mGridLayoutManager = new GridLayoutManager(this, 2);
        mRecyclerView.setLayoutManager(mGridLayoutManager);
//        showList();
    }

    protected  void showList(List<AdditionalProductInfo> productItemList){
        ShowSelectedCategoryProductListAdapter showSelectedCategoryProductListAdapter = new ShowSelectedCategoryProductListAdapter(this, productItemList);
        mRecyclerView.setAdapter(showSelectedCategoryProductListAdapter);
    }
}
