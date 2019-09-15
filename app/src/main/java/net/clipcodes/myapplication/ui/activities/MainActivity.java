package net.clipcodes.myapplication.ui.activities;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;

import net.clipcodes.myapplication.R;
import net.clipcodes.myapplication.models.AdditionalProductInfo;
import net.clipcodes.myapplication.ui.pages.PageCategory;
import net.clipcodes.myapplication.ui.pages.PageHome;
import net.clipcodes.myapplication.ui.pages.PageMyGenie;
import net.clipcodes.myapplication.ui.pages.PageSearch;
import net.clipcodes.myapplication.ui.adapters.MainAdapter;
import net.clipcodes.myapplication.utils.Libraries;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ViewPager viewPager;
    BottomNavigationView navigation;
    private static MainAdapter Adapter;
    private static ArrayList<AdditionalProductInfo> productItemList  = null;

    @Override
    protected void onResume() {
        super.onResume();
        refresh();
    }

    public void refresh(){
        if(Adapter != null){
            Adapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 앱 해시키 얻기
//        getHashKey(getApplicationContext());
        if(productItemList == null){
            productItemList = (ArrayList<AdditionalProductInfo>)getIntent().getSerializableExtra("productItemList");
        }

        setContentView(R.layout.activity_main);

        navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


        viewPager = findViewById(R.id.viewpager);
        setupFm(getSupportFragmentManager(), viewPager);
        viewPager.setCurrentItem(0);
        viewPager.setOnPageChangeListener(new PageChange());

    }

    @Nullable
    public static String getHashKey(Context context) {
        final String TAG = "KeyHash";
        String keyHash = null;
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), PackageManager.GET_SIGNATURES);

            for (Signature signature : info.signatures) {

                MessageDigest md;
                md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                keyHash = new String(Base64.encode(md.digest(), 0));
                Log.d(TAG, keyHash);
            }
        } catch (Exception e) {
            Log.e("name not found", e.toString());
        }

        if (keyHash != null) {
            return keyHash;
        } else {
            return null;
        }
    }

    public static void setupFm(FragmentManager fragmentManager, ViewPager viewPager){
        Adapter = new MainAdapter(fragmentManager);
        PageHome pageHome = new PageHome();
        PageCategory pageCategory = new PageCategory();
        PageSearch pageSearch = new PageSearch();

        Bundle bundle = new Bundle();
        bundle.putSerializable("productItemList", productItemList);
//        bundle.putParcelableArrayList("", productItemList);

        pageHome.setArguments(bundle);
        pageCategory.setArguments(bundle);
        pageSearch.setArguments(bundle);

        Adapter.add(pageHome, "Page Home");
        Adapter.add(pageCategory, "Page Category");
        Adapter.add(pageSearch, "Page Search");
        Adapter.add(new PageMyGenie(), "Page MyGenie");

        viewPager.setAdapter(Adapter);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    viewPager.setCurrentItem(0);
                    ((MainAdapter)viewPager.getAdapter()).notifyDataSetChanged();
                    return true;
                case R.id.navigation_category:
                    viewPager.setCurrentItem(1);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("productItemList", productItemList);
                    ((MainAdapter)viewPager.getAdapter()).getItem(1).setArguments(bundle);
                    ((MainAdapter)viewPager.getAdapter()).notifyDataSetChanged();
                    return true;
                case R.id.navigation_search:
                    viewPager.setCurrentItem(2);
                    ((MainAdapter)viewPager.getAdapter()).notifyDataSetChanged();
                    return true;
                case R.id.navigation_mygenie:
                    viewPager.setCurrentItem(3);
                    ((MainAdapter)viewPager.getAdapter()).notifyDataSetChanged();
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
                case 0:
                    navigation.setSelectedItemId(R.id.navigation_home);
                    break;
                case 1:
                    navigation.setSelectedItemId(R.id.navigation_category);
                    break;
                case 2:
                    navigation.setSelectedItemId(R.id.navigation_search);
                    break;
                case 3:
                    navigation.setSelectedItemId(R.id.navigation_mygenie);
                    break;
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

}