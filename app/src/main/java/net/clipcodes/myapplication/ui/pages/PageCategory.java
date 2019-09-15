package net.clipcodes.myapplication.ui.pages;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.clipcodes.myapplication.models.AdditionalProductInfo;
import net.clipcodes.myapplication.models.ChildCategoryItem;
import net.clipcodes.myapplication.models.ParentCategoryItem;
import net.clipcodes.myapplication.ui.CategoryUIConst;
import net.clipcodes.myapplication.ui.adapters.CategoryItemListAdapter;
import net.clipcodes.myapplication.R;

import java.util.ArrayList;

public class PageCategory extends Fragment {
    private RecyclerView mRecyclerView;
    private ArrayList<AdditionalProductInfo> productItemList  = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragment_one = inflater.inflate(R.layout.fragment_category, container, false);
        productItemList = (ArrayList<AdditionalProductInfo>)getArguments().getSerializable("productItemList");
        mRecyclerView = fragment_one.findViewById(R.id.recyclerView);

        CategoryItemListAdapter categoryItemListAdapter = new CategoryItemListAdapter(getContext(), getDummyDataToPass(), productItemList);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(categoryItemListAdapter);
        mRecyclerView.setHasFixedSize(true);
        return fragment_one;
    }

    private ArrayList<ParentCategoryItem> getDummyDataToPass() {
        ArrayList<ParentCategoryItem> arrDummyData = new ArrayList<>();
        ArrayList<ChildCategoryItem> childDataItems;
        /////////

        childDataItems = new ArrayList<>();
        childDataItems.add(new ChildCategoryItem(getString(R.string.title_category_shirt)));
        childDataItems.add(new ChildCategoryItem(getString(R.string.title_category_pants)));
        childDataItems.add(new ChildCategoryItem(getString(R.string.title_category_coat)));
        childDataItems.add(new ChildCategoryItem(getString(R.string.title_category_hat)));
        childDataItems.add(new ChildCategoryItem(getString(R.string.title_category_socks)));
        childDataItems.add(new ChildCategoryItem(getString(R.string.title_category_glove)));
        childDataItems.add(new ChildCategoryItem(getString(R.string.title_category_etc_clothes)));
        arrDummyData.add(new ParentCategoryItem(getString(R.string.title_category_clothes), childDataItems));
        /////////
        childDataItems = new ArrayList<>();
        childDataItems.add(new ChildCategoryItem(getString(R.string.title_category_high_heels)));
        childDataItems.add(new ChildCategoryItem(getString(R.string.title_category_walker)));
        childDataItems.add(new ChildCategoryItem(getString(R.string.title_category_flat_shoes)));
        childDataItems.add(new ChildCategoryItem(getString(R.string.title_category_sneakers)));
        childDataItems.add(new ChildCategoryItem(getString(R.string.title_category_slippers)));
        childDataItems.add(new ChildCategoryItem(getString(R.string.title_category_kids_shoes)));
        childDataItems.add(new ChildCategoryItem(getString(R.string.title_category_etc_shoes)));
        arrDummyData.add(new ParentCategoryItem(getString(R.string.title_category_shoes), childDataItems));
        /////////
        childDataItems = new ArrayList<>();
        childDataItems.add(new ChildCategoryItem(getString(R.string.title_category_todback)));
        childDataItems.add(new ChildCategoryItem(getString(R.string.title_category_wallet)));
        childDataItems.add(new ChildCategoryItem(getString(R.string.title_category_clutch_bags)));
        childDataItems.add(new ChildCategoryItem(getString(R.string.title_category_backpack)));
        childDataItems.add(new ChildCategoryItem(getString(R.string.title_category_coin_wallet)));
        arrDummyData.add(new ParentCategoryItem(getString(R.string.title_category_merchandise), childDataItems));
        /////////
        childDataItems = new ArrayList<>();
        childDataItems.add(new ChildCategoryItem(getString(R.string.title_category_watch)));
        childDataItems.add(new ChildCategoryItem(getString(R.string.title_category_bracelet)));
        childDataItems.add(new ChildCategoryItem(getString(R.string.title_category_etc_accessaries)));
        arrDummyData.add(new ParentCategoryItem(getString(R.string.title_category_accessaries), childDataItems));
        /////////
        childDataItems = new ArrayList<>();
        childDataItems.add(new ChildCategoryItem(getString(R.string.title_category_doll)));
        childDataItems.add(new ChildCategoryItem(getString(R.string.title_category_blocks)));
        childDataItems.add(new ChildCategoryItem(getString(R.string.title_category_etc_toys)));
        arrDummyData.add(new ParentCategoryItem(getString(R.string.title_category_toys), childDataItems));
        /////////
        childDataItems = new ArrayList<>();
        childDataItems.add(new ChildCategoryItem(getString(R.string.title_category_cellphone_accessaries)));
        childDataItems.add(new ChildCategoryItem(getString(R.string.title_category_etc_electronics)));
        arrDummyData.add(new ParentCategoryItem(getString(R.string.title_category_electronics), childDataItems));

        return arrDummyData;
    }
}