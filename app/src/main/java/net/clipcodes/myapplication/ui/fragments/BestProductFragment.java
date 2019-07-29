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
import net.clipcodes.myapplication.ui.adapters.MyAdapter;
import net.clipcodes.myapplication.R;

import java.util.ArrayList;
import java.util.List;


public class BestProductFragment extends Fragment {
    RecyclerView mRecyclerView;
    List<BasicProductInfo> mFlowerList;
    BasicProductInfo mFlowerData;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View seller = inflater.inflate(R.layout.tab_best_product, container, false);

        mFlowerList = new ArrayList<>();
        mFlowerData = new BasicProductInfo("Rose", getString(R.string.description_flower_rose));
        mFlowerData.getImageList().add(R.drawable.rose);
        mFlowerData.getImageList().add(R.drawable.rose);
        mFlowerData.getImageList().add(R.drawable.rose);
        mFlowerList.add(mFlowerData);
        mFlowerData = new BasicProductInfo("Carnation", getString(R.string.description_flower_carnation));
        mFlowerData.getImageList().add(R.drawable.carnation);
        mFlowerData.getImageList().add(R.drawable.carnation);
        mFlowerData.getImageList().add(R.drawable.carnation);
        mFlowerList.add(mFlowerData);
        mFlowerData = new BasicProductInfo("Tulip", getString(R.string.description_flower_tulip));
        mFlowerData.getImageList().add(R.drawable.tulip);
        mFlowerData.getImageList().add(R.drawable.tulip);
        mFlowerData.getImageList().add(R.drawable.tulip);
        mFlowerList.add(mFlowerData);
        mFlowerData = new BasicProductInfo("Daisy", getString(R.string.description_flower_daisy));
        mFlowerData.getImageList().add(R.drawable.daisy);
        mFlowerData.getImageList().add(R.drawable.daisy);
        mFlowerData.getImageList().add(R.drawable.daisy);
        mFlowerList.add(mFlowerData);
        mFlowerData = new BasicProductInfo("Sunflower", getString(R.string.description_flower_sunflower));
        mFlowerData.getImageList().add(R.drawable.sunflower);
        mFlowerData.getImageList().add(R.drawable.sunflower);
        mFlowerData.getImageList().add(R.drawable.sunflower);
        mFlowerList.add(mFlowerData);
        mFlowerData = new BasicProductInfo("Daffodil", getString(R.string.description_flower_daffodil));
        mFlowerData.getImageList().add(R.drawable.daffodil);
        mFlowerData.getImageList().add(R.drawable.daffodil);
        mFlowerData.getImageList().add(R.drawable.daffodil);
        mFlowerList.add(mFlowerData);
        mFlowerData = new BasicProductInfo("Gerbera", getString(R.string.description_flower_gerbera));
        mFlowerData.getImageList().add(R.drawable.gerbera);
        mFlowerData.getImageList().add(R.drawable.gerbera);
        mFlowerData.getImageList().add(R.drawable.gerbera);
        mFlowerList.add(mFlowerData);
        mFlowerData = new BasicProductInfo("Orchid", getString(R.string.description_flower_orchid));
        mFlowerData.getImageList().add(R.drawable.orchid);
        mFlowerData.getImageList().add(R.drawable.orchid);
        mFlowerData.getImageList().add(R.drawable.orchid);
        mFlowerList.add(mFlowerData);
        mFlowerData = new BasicProductInfo("Iris", getString(R.string.description_flower_iris));
        mFlowerData.getImageList().add(R.drawable.iris);
        mFlowerData.getImageList().add(R.drawable.iris);
        mFlowerData.getImageList().add(R.drawable.iris);
        mFlowerList.add(mFlowerData);
        mFlowerData = new BasicProductInfo("Lilac", getString(R.string.description_flower_lilac));
        mFlowerData.getImageList().add(R.drawable.lilac);
        mFlowerData.getImageList().add(R.drawable.lilac);
        mFlowerData.getImageList().add(R.drawable.lilac);
        mFlowerList.add(mFlowerData);

        mRecyclerView = seller.findViewById(R.id.recyclerview);
        GridLayoutManager mGridLayoutManager = new GridLayoutManager(getActivity(), 2);
        mRecyclerView.setLayoutManager(mGridLayoutManager);

        MyAdapter myAdapter = new MyAdapter(getActivity(), mFlowerList);
        mRecyclerView.setAdapter(myAdapter);
        return seller;
    }
}