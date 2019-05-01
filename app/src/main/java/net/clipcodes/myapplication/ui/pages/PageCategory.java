package net.clipcodes.myapplication.ui.pages;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.clipcodes.myapplication.models.DummyChildDataItem;
import net.clipcodes.myapplication.models.DummyParentDataItem;
import net.clipcodes.myapplication.ui.adapters.RecyclerDataAdapter;
import net.clipcodes.myapplication.R;

import java.util.ArrayList;

public class PageCategory extends Fragment {
    private RecyclerView mRecyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragment_one = inflater.inflate(R.layout.fragment_category, container, false);
        mRecyclerView = fragment_one.findViewById(R.id.recyclerView);
        RecyclerDataAdapter recyclerDataAdapter = new RecyclerDataAdapter(getDummyDataToPass());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(recyclerDataAdapter);
        mRecyclerView.setHasFixedSize(true);
        return fragment_one;
    }

    private ArrayList<DummyParentDataItem> getDummyDataToPass() {
        ArrayList<DummyParentDataItem> arrDummyData = new ArrayList<>();
        ArrayList<DummyChildDataItem> childDataItems;
        /////////
        childDataItems = new ArrayList<>();
        childDataItems.add(new DummyChildDataItem("상의"));
        childDataItems.add(new DummyChildDataItem("하의"));
        childDataItems.add(new DummyChildDataItem("코트"));
        childDataItems.add(new DummyChildDataItem("모자"));
        childDataItems.add(new DummyChildDataItem("양말"));
        childDataItems.add(new DummyChildDataItem("장갑"));
        childDataItems.add(new DummyChildDataItem("기타의류"));
        arrDummyData.add(new DummyParentDataItem("의류", childDataItems));
        /////////
        childDataItems = new ArrayList<>();
        childDataItems.add(new DummyChildDataItem("구두"));
        childDataItems.add(new DummyChildDataItem("워커"));
        childDataItems.add(new DummyChildDataItem("단화"));
        childDataItems.add(new DummyChildDataItem("운동화"));
        childDataItems.add(new DummyChildDataItem("슬리퍼"));
        childDataItems.add(new DummyChildDataItem("어린이신발"));
        childDataItems.add(new DummyChildDataItem("기타신발"));
        arrDummyData.add(new DummyParentDataItem("신발", childDataItems));
        /////////
        childDataItems = new ArrayList<>();
        childDataItems.add(new DummyChildDataItem("토드백"));
        childDataItems.add(new DummyChildDataItem("지갑"));
        childDataItems.add(new DummyChildDataItem("클러치백"));
        childDataItems.add(new DummyChildDataItem("백팩"));
        childDataItems.add(new DummyChildDataItem("동전지갑"));
        arrDummyData.add(new DummyParentDataItem("잡화", childDataItems));
        /////////
        childDataItems = new ArrayList<>();
        childDataItems.add(new DummyChildDataItem("손목시계"));
        childDataItems.add(new DummyChildDataItem("팔찌"));
        childDataItems.add(new DummyChildDataItem("기타악세서리"));
        arrDummyData.add(new DummyParentDataItem("악세서리", childDataItems));
        /////////
        childDataItems = new ArrayList<>();
        childDataItems.add(new DummyChildDataItem("인형"));
        childDataItems.add(new DummyChildDataItem("블럭"));
        childDataItems.add(new DummyChildDataItem("기타장난감"));
        arrDummyData.add(new DummyParentDataItem("장난감", childDataItems));
        /////////
        childDataItems = new ArrayList<>();
        childDataItems.add(new DummyChildDataItem("휴대폰주변기기"));
        childDataItems.add(new DummyChildDataItem("기타전자제품"));
        arrDummyData.add(new DummyParentDataItem("전자제품", childDataItems));

        return arrDummyData;
    }
}