package net.clipcodes.myapplication.ui.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import net.clipcodes.myapplication.R;
import net.clipcodes.myapplication.ui.adapters.ProductImageSliderAdapter;
import net.clipcodes.myapplication.utils.Libraries;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class DetailProductActivity extends AppCompatActivity {
    ViewPager viewPager;
    TabLayout indicator;
    Button btnPurchase;
    ImageView mFlower;
    TextView mDescription;
    TextView tvSellerName;
    TextView tvSellingUnit;
    TextView tvPriceTag;
    TextView tvProductName;
    List<String> imageURLPathList;
    ImageButton btn_contact_kakaoTalk;
    ImageButton btn_contact_phone_number;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        btnPurchase = findViewById(R.id.btnPurchase);
        btnPurchase.setOnClickListener(clik);
        viewPager = findViewById(R.id.product_image_slider);
        indicator = findViewById(R.id.product_image_indicator);
        indicator.setupWithViewPager(viewPager, true);
        mFlower = findViewById(R.id.ivImage);
        mDescription                    = findViewById(R.id.tvDescription);
        tvSellerName =  findViewById(R.id.tvSellerName);
        tvSellingUnit = findViewById(R.id.tvSellingUnit);
        tvPriceTag = findViewById(R.id.tvPriceTag);
        tvProductName = findViewById(R.id.tvProductName);
        btn_contact_kakaoTalk       = findViewById(R.id.seller_contact_kakaotalk);
        btn_contact_kakaoTalk.setOnClickListener(clik);
        btn_contact_phone_number    = findViewById(R.id.seller_contact_phone_number);
        btn_contact_phone_number.setOnClickListener(clik);


        ProductImageSliderAdapter adapter = new ProductImageSliderAdapter(this);
        Bundle mBundle = getIntent().getExtras();

        if (mBundle != null) {
            imageURLPathList = mBundle.getStringArrayList("ImageURLPathList");
            adapter.setImageList(Libraries.getBitmapsFromCacheDir(getCacheDir().toString(), imageURLPathList));

            viewPager.setAdapter(adapter);
            tvProductName.setText(mBundle.getString("Title"));
            mDescription.setText(mBundle.getString("Description"));
            tvSellerName.setText(mBundle.getString("SellerName"));
            tvSellingUnit.setText(mBundle.getString("ItemCnt"));
            tvPriceTag.setText(mBundle.getString("Price"));
            Timer timer = new Timer();
            timer.scheduleAtFixedRate(new SliderTimer(), 4000, 6000);
        }
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
                case R.id.btnPurchase:
                    Intent loginIntent = new Intent(DetailProductActivity.this, LoginActivity.class);
                    startActivity(loginIntent);
                    break;
                case R.id.seller_contact_kakaotalk:
                    break;
                case R.id.seller_contact_phone_number:
                    Intent dialIntent = new Intent("android.intent.action.DIAL");
                    dialIntent.setData(Uri.parse("tel:01072377056"));
                    startActivity(dialIntent);
                    break;
            }

        }
    };
}