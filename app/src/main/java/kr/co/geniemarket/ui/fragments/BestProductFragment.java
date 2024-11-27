package kr.co.geniemarket.ui.fragments;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import kr.co.geniemarket.models.AdditionalProductInfo;
import kr.co.geniemarket.ui.adapters.BestProductListAdapter;
import kr.co.geniemarket.R;

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