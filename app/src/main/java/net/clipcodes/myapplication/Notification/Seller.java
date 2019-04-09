package net.clipcodes.myapplication.Notification;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.clipcodes.myapplication.R;


public class Seller extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View seller = inflater.inflate(R.layout.tab_seller, container, false);

        return seller;
    }
}