package kr.co.geniemarket.ui.activities;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.view.WindowMetrics;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.RequestConfiguration;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import kr.co.geniemarket.BuildConfig;
import kr.co.geniemarket.R;
import kr.co.geniemarket.core.GMLog;
import kr.co.geniemarket.models.AdditionalProductInfo;
import kr.co.geniemarket.ui.pages.PageCategory;
import kr.co.geniemarket.ui.pages.PageHome;
import kr.co.geniemarket.ui.adapters.MainAdapter;

import java.util.ArrayList;
import java.util.Arrays;

public class OldMainActivity extends AppCompatActivity {

    ViewPager viewPager;
    BottomNavigationView navigation;

    private AdView adView;

    private RelativeLayout rootView;

    private static MainAdapter Adapter;

    private static ArrayList<AdditionalProductInfo> productItemList  = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GMLog.setLogLevel(GMLog.LogLevel.Verbose);
        GMLog.i("this is ui app MainActivity.");

        if(productItemList == null){
            productItemList = (ArrayList<AdditionalProductInfo>)getIntent().getSerializableExtra("productItemList");
        }

        setContentView(kr.co.geniemarket.R.layout.activity_old_main);

        rootView = findViewById(kr.co.geniemarket.R.id.rootView);
        navigation = findViewById(kr.co.geniemarket.R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        viewPager = findViewById(kr.co.geniemarket.R.id.viewpager);
        setupFm(getSupportFragmentManager(), viewPager);
        viewPager.setCurrentItem(0);
        viewPager.setOnPageChangeListener(new PageChange());

        // Initialize the Google Mobile Ads SDK on a background thread.
        MobileAds.initialize(this, initializationStatus -> {
            GMLog.i("Google admob init success.");
            registerTestDevice();
            runOnUiThread(() -> loadAdmobBanner());
        });
    }

    public static void setupFm(FragmentManager fragmentManager, ViewPager viewPager){
        Adapter = new MainAdapter(fragmentManager);
        PageHome pageHome = new PageHome();
        PageCategory pageCategory = new PageCategory();
//        PageSearch pageSearch = new PageSearch();
//        PageMyGenie pageMyGenie = new PageMyGenie();
        Bundle bundle = new Bundle();
        bundle.putSerializable("productItemList", productItemList);
//        bundle.putParcelableArrayList("", productItemList);

        pageHome.setArguments(bundle);
        pageCategory.setArguments(bundle);
//        pageSearch.setArguments(bundle);

        Adapter.add(pageHome, "Page Home");
        Adapter.add(pageCategory, "Page Category");
//        Adapter.add(pageSearch, "Page Search");
//        Adapter.add(pageMyGenie, "Page MyGenie");

        viewPager.setAdapter(Adapter);
    }

    private void registerTestDevice() {
        if(kr.co.geniemarket.BuildConfig.DEBUG){
            RequestConfiguration configuration = new RequestConfiguration.Builder()
                    .setTestDeviceIds(Arrays.asList(kr.co.geniemarket.BuildConfig.GENIEMARKET_TEST_DEVICE_ID))
                    .build();
            MobileAds.setRequestConfiguration(configuration);
        }
    }

    private void loadAdmobBanner() {
        GMLog.e("APP_ID: " + getMetaData());
        // Create a new ad view.
        adView = new AdView(this);
        adView.setAdUnitId(BuildConfig.GENIEMARKET_ADMOB_MAIN_ACTIVITY_BANNER1_AD_UNIT_ID);
        adView.setAdSize(getAdSize());
        adView.setAdListener(new AdListener() {
            @Override
            public void onAdClicked() {
                // Code to be executed when the user clicks on an ad.
                GMLog.i("AdmobAdView.onAdClicked()");
            }

            @Override
            public void onAdClosed() {
                // Code to be executed when the user is about to return
                // to the app after tapping on an ad.
                GMLog.i("AdmobAdView.onAdClosed()");
            }

            @Override
            public void onAdFailedToLoad(LoadAdError adError) {
                // Code to be executed when an ad request fails.
                GMLog.e("AdmobAdView.onAdFailed(" + adError.getCode() + ", " + adError.getMessage() + ", " + adError.getCause() + ")");
            }
            @Override
            public void onAdImpression() {
                // Code to be executed when an impression is recorded
                // for an ad.
                GMLog.i("AdmobAdView.onAdImpression()");
            }

            @Override
            public void onAdLoaded() {
                // Code to be executed when an ad finishes loading.
                GMLog.i("AdmobAdView.onAdLoaded()");
            }

            @Override
            public void onAdOpened() {
                // Code to be executed when an ad opens an overlay that
                // covers the screen.
                GMLog.i("AdmobAdView.onAdOpened()");
            }

            @Override
            public void onAdSwipeGestureClicked() {
                GMLog.i("AdmobAdView.onAdSwipeGestureClicked()");
            }
        });

        // 예시 : 화면 하단에 배너 부착
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.CENTER_HORIZONTAL);
        rootView.addView(adView, params);

        // Start loading the ad in the background.
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
    }

    private String getMetaData() {
        try{
            // net.adwhale.sdk.mediation.PUBLISHER_UID를 name 속성값으로 갖는 <meta-data> value를 가져온다.
            ApplicationInfo applicationInfo = getApplicationContext().getPackageManager().getApplicationInfo(getApplicationContext().getPackageName(), PackageManager.GET_META_DATA);
            Bundle bundle = applicationInfo.metaData;
            return String.valueOf(bundle.get("com.google.android.gms.ads.APPLICATION_ID"));
        } catch (PackageManager.NameNotFoundException e) {
            return "";
        }
    }

    // Get the ad size with screen width.
    public AdSize getAdSize() {
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        int adWidthPixels = displayMetrics.widthPixels;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            WindowMetrics windowMetrics = this.getWindowManager().getCurrentWindowMetrics();
            adWidthPixels = windowMetrics.getBounds().width();
        }

        float density = displayMetrics.density;
        int adWidth = (int) (adWidthPixels / density);
        return AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(this, adWidth);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case kr.co.geniemarket.R.id.navigation_home:
                    viewPager.setCurrentItem(0);
                    ((MainAdapter)viewPager.getAdapter()).notifyDataSetChanged();
                    return true;
                case kr.co.geniemarket.R.id.navigation_category:
                    viewPager.setCurrentItem(1);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("productItemList", productItemList);
                    ((MainAdapter)viewPager.getAdapter()).getItem(1).setArguments(bundle);
                    ((MainAdapter)viewPager.getAdapter()).notifyDataSetChanged();
                    return true;
//                case R.id.navigation_search:
//                    viewPager.setCurrentItem(2);
//                    ((MainAdapter)viewPager.getAdapter()).notifyDataSetChanged();
//                    return true;
//                case R.id.navigation_mygenie:
//                    viewPager.setCurrentItem(3);
//                    ((MainAdapter)viewPager.getAdapter()).notifyDataSetChanged();
//                    return true;
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
                    navigation.setSelectedItemId(kr.co.geniemarket.R.id.navigation_home);
                    break;
                case 1:
                    navigation.setSelectedItemId(R.id.navigation_category);
                    break;
//                case 2:
//                    navigation.setSelectedItemId(R.id.navigation_search);
//                    break;
//                case 3:
//                    navigation.setSelectedItemId(R.id.navigation_mygenie);
//                    break;
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }


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
    protected void onDestroy() {
        super.onDestroy();

        if(adView != null) {
            adView.removeAllViews();
            adView.destroy();
        }
    }
}