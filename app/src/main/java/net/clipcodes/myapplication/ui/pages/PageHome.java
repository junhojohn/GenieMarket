package net.clipcodes.myapplication.ui.pages;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import net.clipcodes.myapplication.ui.fragments.CheapProductFragment;
import net.clipcodes.myapplication.ui.fragments.BestProductFragment;
import net.clipcodes.myapplication.R;

import java.util.Timer;
import java.util.TimerTask;

public class PageHome extends Fragment {

    FrameLayout bestFragment, cheapFragment;
    View bestProductView, cheapProductView;
    TextView textBest, textCheap;
    int NUM_PAGES = 2;
    int currentPage = 0;
    boolean touched = false;
    Handler handler = new Handler();
    Runnable update;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragment_three = inflater.inflate(R.layout.fragment_home, container, false);

        //INIT VIEWS
        init(fragment_three);

        //SET TABS ONCLICK
        bestFragment.setOnClickListener(clik);
        cheapFragment.setOnClickListener(clik);

        //LOAD PAGE FOR FIRST
        loadPage(new BestProductFragment());
        textBest.setTextColor(getActivity().getResources().getColor(R.color.gradStop));
//        startPagerAutoSwipe();
        return fragment_three;
    }

    public void init(View v){
        bestFragment = v.findViewById(R.id.best_fragment);
        cheapFragment = v.findViewById(R.id.cheap_fragment);
        bestProductView = v.findViewById(R.id.bestProductView);
        cheapProductView = v.findViewById(R.id.cheapProductView);
        textBest = v.findViewById(R.id.text_best);
        textCheap = v.findViewById(R.id.text_cheap);
    }

    View.OnTouchListener autoMove = new View.OnTouchListener(){
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch(event.getAction()){
                case MotionEvent.ACTION_DOWN:
                    touched = true;
                    return true;

                case MotionEvent.ACTION_UP:
                    touched = false;
                    return true;
            }
            return false;
        }
    };
    //ONCLICK LISTENER
    public View.OnClickListener clik = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.best_fragment:
                    //ONSELLER CLICK
                    //LOAD SELLER FRAGMENT CLASS
                    loadPage(new BestProductFragment());

                    //WHEN CLICK TEXT COLOR CHANGED
                    textBest.setTextColor(getActivity().getResources().getColor(R.color.gradStop));
                    textCheap.setTextColor(getActivity().getResources().getColor(R.color.grey));

                    //VIEW VISIBILITY WHEN CLICKED
                    bestProductView.setVisibility(View.VISIBLE);
                    cheapProductView.setVisibility(View.INVISIBLE);
                    break;
                case R.id.cheap_fragment:
                    //ONBUYER CLICK
                    //LOAD BUYER FRAGMENT CLASS
                    loadPage(new CheapProductFragment());

                    //WHEN CLICK TEXT COLOR CHANGED
                    textBest.setTextColor(getActivity().getResources().getColor(R.color.grey));
                    textCheap.setTextColor(getActivity().getResources().getColor(R.color.gradStop));

                    //VIEW VISIBILITY WHEN CLICKED
                    bestProductView.setVisibility(View.INVISIBLE);
                    cheapProductView.setVisibility(View.VISIBLE);
                    break;
            }
        }
    };

    //LOAD PAGE FRAGMENT METHOD
    private boolean loadPage(Fragment fragment) {

        if (fragment != null) {
            getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.containerpage, fragment)
                    .commit();
            return true;
        }
        return false;
    }

    private void startPagerAutoSwipe() {
        update = new Runnable() {
            public void run() {
                if(!touched){
                    if (currentPage == 0) {
                        loadPage(new BestProductFragment());

                        //WHEN CLICK TEXT COLOR CHANGED
                        textBest.setTextColor(getActivity().getResources().getColor(R.color.gradStop));
                        textCheap.setTextColor(getActivity().getResources().getColor(R.color.grey));

                        //VIEW VISIBILITY WHEN CLICKED
                        bestProductView.setVisibility(View.VISIBLE);
                        cheapProductView.setVisibility(View.INVISIBLE);
                        currentPage++;
                    }else if(currentPage == 1){
                        currentPage = 0;
                        loadPage(new CheapProductFragment());

                        //WHEN CLICK TEXT COLOR CHANGED
                        textBest.setTextColor(getActivity().getResources().getColor(R.color.grey));
                        textCheap.setTextColor(getActivity().getResources().getColor(R.color.gradStop));

                        //VIEW VISIBILITY WHEN CLICKED
                        bestProductView.setVisibility(View.INVISIBLE);
                        cheapProductView.setVisibility(View.VISIBLE);
                    }
                }
            }
        };
        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(update);
            }
        }, 5000, 5000);
        swipeTimer.cancel();
    }
}