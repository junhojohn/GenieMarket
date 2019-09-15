package net.clipcodes.myapplication.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.clipcodes.myapplication.R;
import net.clipcodes.myapplication.models.AdditionalProductInfo;
import net.clipcodes.myapplication.ui.adapters.BestProductListAdapter;

import java.util.ArrayList;
import java.util.List;


public class CheapProductFragment extends Fragment {
    RecyclerView mRecyclerView;
    View buyer;
    private List<AdditionalProductInfo> cheapProductList = null;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        buyer = inflater.inflate(R.layout.tab_cheap_product, container, false);
        mRecyclerView = buyer.findViewById(R.id.recyclerviewForCheapProduct);
        GridLayoutManager mGridLayoutManager = new GridLayoutManager(getActivity(), 2);
        mRecyclerView.setLayoutManager(mGridLayoutManager);

        cheapProductList = (ArrayList<AdditionalProductInfo>)getArguments().getSerializable("cheapProductList");
        showList(cheapProductList);

        return buyer;
    }

    protected  void showList(List<AdditionalProductInfo> productItemList){
        BestProductListAdapter bestProductListAdapter = new BestProductListAdapter(getActivity(), productItemList);
        mRecyclerView.setAdapter(bestProductListAdapter);
    }
}

