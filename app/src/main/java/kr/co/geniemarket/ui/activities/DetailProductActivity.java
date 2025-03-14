package kr.co.geniemarket.ui.activities;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowMetrics;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.RequestConfiguration;
import com.google.android.material.tabs.TabLayout;
//import com.kakao.auth.Session;

import kr.co.geniemarket.BuildConfig;
import kr.co.geniemarket.R;
import kr.co.geniemarket.core.GMLog;
import kr.co.geniemarket.ui.LOGIN_AFTER_REDIR_PAGE_ENUM;
import kr.co.geniemarket.ui.adapters.ProductImageSliderAdapter;
import kr.co.geniemarket.utils.Libraries;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class DetailProductActivity extends AppCompatActivity implements Serializable {
    private AdView adView;

    private RelativeLayout rootView;
    ViewPager viewPager;
    TabLayout indicator;
//    Button btnPurchase;
    ImageView mFlower;
    TextView mDescription;
    TextView tvSellerName;
    TextView tvSellingUnit;
    TextView tvPriceTag;
    TextView tvProductName;
    ArrayList<String> imageURLPathList;
//    ImageButton btn_contact_kakaoTalk;
    ImageButton btn_contact_phone_number;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

//        btnPurchase = findViewById(R.id.btnPurchase);
//        btnPurchase.setOnClickListener(clik);
        rootView = findViewById(kr.co.geniemarket.R.id.rootView);
        viewPager = findViewById(R.id.product_image_slider);
        indicator = findViewById(R.id.product_image_indicator);
        indicator.setupWithViewPager(viewPager, true);
        mFlower = findViewById(R.id.ivImage);
        mDescription                    = findViewById(R.id.tvDescription);
        tvSellerName =  findViewById(R.id.tvSellerName);
        tvSellingUnit = findViewById(R.id.tvSellingUnit);
        tvPriceTag = findViewById(R.id.tvPriceTag);
        tvProductName = findViewById(R.id.tvProductName);
//        btn_contact_kakaoTalk       = findViewById(R.id.seller_contact_kakaotalk);
//        btn_contact_kakaoTalk.setOnClickListener(clik);
        btn_contact_phone_number    = findViewById(R.id.seller_contact_phone_number);
        btn_contact_phone_number.setOnClickListener(clik);


        ProductImageSliderAdapter adapter = new ProductImageSliderAdapter(this);
        Bundle mBundle = getIntent().getExtras();

        if (mBundle != null) {
            imageURLPathList = mBundle.getStringArrayList("ImageURLPathList");
            adapter.setImageList(imageURLPathList);

            viewPager.setAdapter(adapter);
            tvProductName.setText(mBundle.getString("Title"));
            mDescription.setText(mBundle.getString("Description"));
            tvSellerName.setText(mBundle.getString("SellerName"));
            tvSellingUnit.setText(mBundle.getString("ItemCnt"));
            tvPriceTag.setText(mBundle.getString("Price"));
            Timer timer = new Timer();
            timer.scheduleAtFixedRate(new SliderTimer(), 4000, 6000);
        }

        // Initialize the Google Mobile Ads SDK on a background thread.
        MobileAds.initialize(this, initializationStatus -> {
            GMLog.i("Google admob init success.");
            registerTestDevice();
            runOnUiThread(() -> loadAdmobBanner());
        });
    }

    // previouse code.
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_detail);
//
//        mFlower = findViewById(R.id.ivImage);
//        mDescription = findViewById(R.id.tvDescription);
//
//        Bundle mBundle = getIntent().getExtras();
//        if (mBundle != null) {
////            mFlower.setImageResource(mBundle.getInt("Image"));
//            imageList = mBundle.getIntegerArrayList("ImageList");
//            mFlower.setImageResource(imageList.get(0));
//            mDescription.setText(mBundle.getString("Description"));
//        }
//    }

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

    private class SliderTimer extends TimerTask {

        @Override
        public void run() {
            DetailProductActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (viewPager.getCurrentItem() < imageURLPathList.size() - 1) {
                        viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
                    } else {
                        viewPager.setCurrentItem(0);
                    }
                }
            });
        }
    }

    public View.OnClickListener clik = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
//                case R.id.btnPurchase:
//                    if(Session.getCurrentSession().checkAndImplicitOpen()){
//                        Intent mIntent = new Intent(DetailProductActivity.this, PurchaseActivity.class);
//                        startActivity(mIntent);
//
//                    }else{
//                        Intent loginIntent = new Intent(DetailProductActivity.this, LoginActivity.class);
//                        loginIntent.putExtra("nextActivityToMove", LOGIN_AFTER_REDIR_PAGE_ENUM.PURCHASE_ACTIVITY.getActivityName());
//                        startActivity(loginIntent);
//                    }
//
//                    break;
//                case R.id.seller_contact_kakaotalk:
//                    break;
                case R.id.seller_contact_phone_number:
                    Intent dialIntent = new Intent("android.intent.action.DIAL");
                    dialIntent.setData(Uri.parse("tel:01012345678"));
                    startActivity(dialIntent);
                    break;
            }

        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if(adView != null) {
            adView.removeAllViews();
            adView.destroy();
        }
    }
}