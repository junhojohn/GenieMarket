package net.clipcodes.myapplication.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.clipcodes.myapplication.models.AdditionalProductInfo;
import net.clipcodes.myapplication.ui.adapters.BestProductListAdapter;
import net.clipcodes.myapplication.R;

import java.util.ArrayList;
import java.util.List;


public class BestProductFragment extends Fragment {
    RecyclerView mRecyclerView;
    View seller;
    private List<AdditionalProductInfo> bestProductList = null;

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

        bestProductList = (ArrayList<AdditionalProductInfo>)getArguments().getSerializable("bestProductList");
        showList(bestProductList);

        return seller;
    }

    protected  void showList(List<AdditionalProductInfo> productItemList){
        BestProductListAdapter bestProductListAdapter = new BestProductListAdapter(getActivity(), productItemList);
        mRecyclerView.setAdapter(bestProductListAdapter);
    }
}