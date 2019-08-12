package net.clipcodes.myapplication.ui.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import net.clipcodes.myapplication.R;

import java.util.List;

public class ProductImageSliderAdapter extends PagerAdapter {
    private ImageView imageView;
    private Context context;
    private List<Bitmap> imageList;

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
        imageView.setImageBitmap(this.imageList.get(position));

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

    public void setImageList(List<Bitmap> imageList){
        this.imageList = imageList;
    }
}