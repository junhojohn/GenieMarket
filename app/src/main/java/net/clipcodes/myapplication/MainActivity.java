package net.clipcodes.myapplication;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import net.clipcodes.myapplication.Page.PageOne;
import net.clipcodes.myapplication.Page.PageTree;
import net.clipcodes.myapplication.Page.PageTwo;
import net.clipcodes.myapplication.PageAdapter.FragmentAdapter;

public class MainActivity extends AppCompatActivity {

    ViewPager viewPager;
    BottomNavigationView navigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        viewPager = findViewById(R.id.viewpager);
        setupFm(getSupportFragmentManager(), viewPager);
        viewPager.setCurrentItem(0);
        viewPager.setOnPageChangeListener(new PageChange());
    }


    public static void setupFm(FragmentManager fragmentManager, ViewPager viewPager){
        FragmentAdapter Adapter = new FragmentAdapter(fragmentManager);
        Adapter.add(new PageTree(), "Page Three");
        Adapter.add(new PageOne(), "Page One");
        Adapter.add(new PageTwo(), "Page Two");


        viewPager.setAdapter(Adapter);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    viewPager.setCurrentItem(1);
                    return true;
                case R.id.navigation_dashboard:
                    viewPager.setCurrentItem(2);
                    return true;
                case R.id.navigation_notifications:
                    viewPager.setCurrentItem(0);
                    return true;
            }
            return false;
        }
    };


    public class PageChange implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        @Override
        public void onPageSelected(int position) {

            switch (position) {
                case 1:
                    navigation.setSelectedItemId(R.id.navigation_home);
                    break;
                case 2:
                    navigation.setSelectedItemId(R.id.navigation_dashboard);
                    break;
                case 0:
                    navigation.setSelectedItemId(R.id.navigation_notifications);
                    break;
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

}