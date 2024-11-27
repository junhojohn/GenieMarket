package kr.co.geniemarket.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import kr.co.geniemarket.R;
import kr.co.geniemarket.models.AdditionalProductInfo;
import kr.co.geniemarket.ui.adapters.BestProductListAdapter;

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

