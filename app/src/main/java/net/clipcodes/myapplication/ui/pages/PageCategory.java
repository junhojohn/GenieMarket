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
        CategoryItemListAdapter categoryItemListAdapter = new CategoryItemListAdapter(getDummyDataToPass());
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
        childDataItems.add(new ChildCategoryItem("상의"));
        childDataItems.add(new ChildCategoryItem("하의"));
        childDataItems.add(new ChildCategoryItem("코트"));
        childDataItems.add(new ChildCategoryItem("모자"));
        childDataItems.add(new ChildCategoryItem("양말"));
        childDataItems.add(new ChildCategoryItem("장갑"));
        childDataItems.add(new ChildCategoryItem("기타의류"));
        arrDummyData.add(new ParentCategoryItem("의류", childDataItems));
        /////////
        childDataItems = new ArrayList<>();
        childDataItems.add(new ChildCategoryItem("구두"));
        childDataItems.add(new ChildCategoryItem("워커"));
        childDataItems.add(new ChildCategoryItem("단화"));
        childDataItems.add(new ChildCategoryItem("운동화"));
        childDataItems.add(new ChildCategoryItem("슬리퍼"));
        childDataItems.add(new ChildCategoryItem("어린이신발"));
        childDataItems.add(new ChildCategoryItem("기타신발"));
        arrDummyData.add(new ParentCategoryItem("신발", childDataItems));
        /////////
        childDataItems = new ArrayList<>();
        childDataItems.add(new ChildCategoryItem("토드백"));
        childDataItems.add(new ChildCategoryItem("지갑"));
        childDataItems.add(new ChildCategoryItem("클러치백"));
        childDataItems.add(new ChildCategoryItem("백팩"));
        childDataItems.add(new ChildCategoryItem("동전지갑"));
        arrDummyData.add(new ParentCategoryItem("잡화", childDataItems));
        /////////
        childDataItems = new ArrayList<>();
        childDataItems.add(new ChildCategoryItem("손목시계"));
        childDataItems.add(new ChildCategoryItem("팔찌"));
        childDataItems.add(new ChildCategoryItem("기타악세서리"));
        arrDummyData.add(new ParentCategoryItem("악세서리", childDataItems));
        /////////
        childDataItems = new ArrayList<>();
        childDataItems.add(new ChildCategoryItem("인형"));
        childDataItems.add(new ChildCategoryItem("블럭"));
        childDataItems.add(new ChildCategoryItem("기타장난감"));
        arrDummyData.add(new ParentCategoryItem("장난감", childDataItems));
        /////////
        childDataItems = new ArrayList<>();
        childDataItems.add(new ChildCategoryItem("휴대폰주변기기"));
        childDataItems.add(new ChildCategoryItem("기타전자제품"));
        arrDummyData.add(new ParentCategoryItem("전자제품", childDataItems));

        return arrDummyData;
    }
}