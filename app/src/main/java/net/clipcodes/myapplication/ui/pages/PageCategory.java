package net.clipcodes.myapplication.ui.pages;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.clipcodes.myapplication.models.ChildCategoryItem;
import net.clipcodes.myapplication.models.ParentCategoryItem;
import net.clipcodes.myapplication.ui.CategoryUIConst;
import net.clipcodes.myapplication.ui.adapters.CategoryItemListAdapter;
import net.clipcodes.myapplication.R;

import java.util.ArrayList;

public class PageCategory extends Fragment {
    private RecyclerView mRecyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragment_one = inflater.inflate(R.layout.fragment_category, container, false);
        mRecyclerView = fragment_one.findViewById(R.id.recyclerView);
        String testParam = null;
        if(getArguments() != null){
            testParam = getArguments().getString("testParam");
        }
        CategoryItemListAdapter categoryItemListAdapter = new CategoryItemListAdapter(getContext(), getDummyDataToPass(), testParam);
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
        childDataItems.add(new ChildCategoryItem(CategoryUIConst.CATEGORY_SHIRT));
        childDataItems.add(new ChildCategoryItem(CategoryUIConst.CATEGORY_PANTS));
        childDataItems.add(new ChildCategoryItem(CategoryUIConst.CATEGORY_COAT));
        childDataItems.add(new ChildCategoryItem(CategoryUIConst.CATEGORY_HAT));
        childDataItems.add(new ChildCategoryItem(CategoryUIConst.CATEGORY_SOCKS));
        childDataItems.add(new ChildCategoryItem(CategoryUIConst.CATEGORY_GLOVE));
        childDataItems.add(new ChildCategoryItem(CategoryUIConst.CATEGORY_ETC_CLOTHES));
        arrDummyData.add(new ParentCategoryItem(CategoryUIConst.CATEGORY_CLOTHES, childDataItems));
        /////////
        childDataItems = new ArrayList<>();
        childDataItems.add(new ChildCategoryItem(CategoryUIConst.CATEGORY_HIGH_HEELS));
        childDataItems.add(new ChildCategoryItem(CategoryUIConst.CATEGORY_WALKER));
        childDataItems.add(new ChildCategoryItem(CategoryUIConst.CATEGORY_FLAT_SHOES));
        childDataItems.add(new ChildCategoryItem(CategoryUIConst.CATEGORY_SNEAKERS));
        childDataItems.add(new ChildCategoryItem(CategoryUIConst.CATEGORY_SLIPPERS));
        childDataItems.add(new ChildCategoryItem(CategoryUIConst.CATEGORY_KIDS_SHOES));
        childDataItems.add(new ChildCategoryItem(CategoryUIConst.CATEGORY_ETC_SHOES));
        arrDummyData.add(new ParentCategoryItem(CategoryUIConst.CATEGORY_SHOES, childDataItems));
        /////////
        childDataItems = new ArrayList<>();
        childDataItems.add(new ChildCategoryItem(CategoryUIConst.CATEGORY_TODBACK));
        childDataItems.add(new ChildCategoryItem(CategoryUIConst.CATEGORY_WALLET));
        childDataItems.add(new ChildCategoryItem(CategoryUIConst.CATEGORY_CLUTCH_BAGS));
        childDataItems.add(new ChildCategoryItem(CategoryUIConst.CATEGORY_BACKPACK));
        childDataItems.add(new ChildCategoryItem(CategoryUIConst.CATEGORY_COIN_WALLET));
        arrDummyData.add(new ParentCategoryItem(CategoryUIConst.CATEGORY_MERCHANDISE, childDataItems));
        /////////
        childDataItems = new ArrayList<>();
        childDataItems.add(new ChildCategoryItem(CategoryUIConst.CATEGORY_WATCH));
        childDataItems.add(new ChildCategoryItem(CategoryUIConst.CATEGORY_BRACELET));
        childDataItems.add(new ChildCategoryItem(CategoryUIConst.CATEGORY_ETC_ACCESSARIES));
        arrDummyData.add(new ParentCategoryItem(CategoryUIConst.CATEGORY_ACCESSARIES, childDataItems));
        /////////
        childDataItems = new ArrayList<>();
        childDataItems.add(new ChildCategoryItem(CategoryUIConst.CATEGORY_DOLL));
        childDataItems.add(new ChildCategoryItem(CategoryUIConst.CATEGORY_BLOCKS));
        childDataItems.add(new ChildCategoryItem(CategoryUIConst.CATEGORY_ETC_TOYS));
        arrDummyData.add(new ParentCategoryItem(CategoryUIConst.CATEGORY_TOYS, childDataItems));
        /////////
        childDataItems = new ArrayList<>();
        childDataItems.add(new ChildCategoryItem(CategoryUIConst.CATEGORY_CELLPHONE_ACCESSARIES));
        childDataItems.add(new ChildCategoryItem(CategoryUIConst.CATEGORY_ETC_ELECTRONICS));
        arrDummyData.add(new ParentCategoryItem(CategoryUIConst.CATEGORY_ELECTRONICS, childDataItems));

        return arrDummyData;
    }
}