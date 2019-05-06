package net.clipcodes.myapplication.ui.activities;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import net.clipcodes.myapplication.R;
import net.clipcodes.myapplication.ui.adapters.ProductImageSliderAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class DetailProductActivity extends AppCompatActivity {
    ViewPager viewPager;
    TabLayout indicator;
    ImageView mFlower;
    TextView mDescription;
    List<Integer> imageList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        viewPager = findViewById(R.id.product_image_slider);
        indicator = findViewById(R.id.product_image_indicator);
        indicator.setupWithViewPager(viewPager, true);
        mFlower = findViewById(R.id.ivImage);
        mDescription = findViewById(R.id.tvDescription);

        ProductImageSliderAdapter adapter = new ProductImageSliderAdapter(this);
        Bundle mBundle = getIntent().getExtras();
        if (mBundle != null) {
            imageList = mBundle.getIntegerArrayList("ImageList");
            adapter.setImageList(imageList);
            viewPager.setAdapter(adapter);
            mDescription.setText(mBundle.getString("Description") + "\na\na\na\na\na\na\na\na\na\na\na\na\na\na\na");

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
                    if (viewPager.getCurrentItem() < imageList.size() - 1) {
                        viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
                    } else {
                        viewPager.setCurrentItem(0);
                    }
                }
            });
        }
    }
}