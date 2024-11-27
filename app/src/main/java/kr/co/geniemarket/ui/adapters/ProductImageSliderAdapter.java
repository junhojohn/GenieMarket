package kr.co.geniemarket.ui.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;

import kr.co.geniemarket.R;

import java.util.ArrayList;
import java.util.List;

public class ProductImageSliderAdapter extends PagerAdapter {
    private ImageView imageView;
    private Context context;
    private ArrayList<String> imageList;

    public ProductImageSliderAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return imageList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_product_image_slider, null);

        imageView = view.findViewById(R.id.ivImage);
        Glide.with(context)
                .load(this.imageList.get(position))
                .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                .into(imageView);

        ViewPager viewPager = (ViewPager) container;
        viewPager.addView(view, 0);

        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ViewPager viewPager = (ViewPager) container;
        View view = (View) object;
        viewPager.removeView(view);
    }

    public void setImageList(ArrayList<String> imageList){
        this.imageList = imageList;
    }
}