package net.clipcodes.myapplication.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.clipcodes.myapplication.models.BasicProductInfo;
import net.clipcodes.myapplication.ui.adapters.BestProductListAdapter;
import net.clipcodes.myapplication.R;

import java.util.ArrayList;
import java.util.List;


public class BestProductFragment extends Fragment {
    RecyclerView mRecyclerView;
    List<BasicProductInfo> productItemList;
    BasicProductInfo productItem;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View seller = inflater.inflate(R.layout.tab_best_product, container, false);

        productItemList = new ArrayList<>();
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

        mRecyclerView = seller.findViewById(R.id.recyclerview);
        GridLayoutManager mGridLayoutManager = new GridLayoutManager(getActivity(), 2);
        mRecyclerView.setLayoutManager(mGridLayoutManager);

        BestProductListAdapter bestProductListAdapter = new BestProductListAdapter(getActivity(), productItemList);
        mRecyclerView.setAdapter(bestProductListAdapter);
        return seller;
    }
}