package net.clipcodes.myapplication.ui.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import net.clipcodes.myapplication.models.AdditionalProductInfo;
import net.clipcodes.myapplication.models.ParentCategoryItem;
import net.clipcodes.myapplication.R;
import net.clipcodes.myapplication.ui.CategoryUIConst;
import net.clipcodes.myapplication.ui.activities.DetailProductActivity;
import net.clipcodes.myapplication.ui.activities.ShowSelectedCategoryActivity;

import java.util.ArrayList;


public class CategoryItemListAdapter extends RecyclerView.Adapter<CategoryItemListAdapter.CategoryItemListViewHolder> {
    private ArrayList<ParentCategoryItem> parentCategoryItems;
    private Context mContext;
    private ArrayList<AdditionalProductInfo> productItemList  = null;

    public CategoryItemListAdapter(Context mContext, ArrayList<ParentCategoryItem> parentCategoryItems, ArrayList<AdditionalProductInfo> productItemList) {
        this.mContext = mContext;
        this.parentCategoryItems = parentCategoryItems;
        this.productItemList = productItemList;
    }

    @Override
    public CategoryItemListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category_list, parent, false);
        return new CategoryItemListViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CategoryItemListViewHolder holder, int position) {
        ParentCategoryItem parentCategoryItem = parentCategoryItems.get(position);
        switch (position){
            case 0:
                holder.img_category.setImageResource(R.drawable.ic_category_clothes_48dp);
                break;
            case 1:
                holder.img_category.setImageResource(R.drawable.ic_category_shoes_48dp);
                break;
            case 2:
                holder.img_category.setImageResource(R.drawable.ic_category_bags_48dp);
                break;
            case 3:
                holder.img_category.setImageResource(R.drawable.ic_category_accessories_48dp);
                break;
            case 4:
                holder.img_category.setImageResource(R.drawable.ic_category_toys_48dp);
                break;
            case 5:
                holder.img_category.setImageResource(R.drawable.ic_category_electronics_48dp);
                break;
        }
        holder.textView_parentName.setText(parentCategoryItem.getParentName());
        //
        int noOfChildTextViews = holder.linearLayout_childItems.getChildCount();
        for (int index = 0; index < noOfChildTextViews; index++) {
            TextView currentTextView = (TextView) holder.linearLayout_childItems.getChildAt(index);
            currentTextView.setVisibility(View.VISIBLE);
        }

        int noOfChild = parentCategoryItem.getChildDataItems().size();
        if (noOfChild < noOfChildTextViews) {
            for (int index = noOfChild; index < noOfChildTextViews; index++) {
                TextView currentTextView = (TextView) holder.linearLayout_childItems.getChildAt(index);
                currentTextView.setVisibility(View.GONE);
            }
        }
        for (int textViewIndex = 0; textViewIndex < noOfChild; textViewIndex++) {
            TextView currentTextView = (TextView) holder.linearLayout_childItems.getChildAt(textViewIndex);
            currentTextView.setText(parentCategoryItem.getChildDataItems().get(textViewIndex).getChildName());
                /*currentTextView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(mContext, "" + ((TextView) view).getText().toString(), Toast.LENGTH_SHORT).show();
                    }
                });*/
        }
    }

    @Override
    public int getItemCount() {
        return parentCategoryItems.size();
    }

    class CategoryItemListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private Context context;
        private TextView textView_parentName;
        private ImageView img_category;
        private ImageView img_arrow;
        private LinearLayout linearLayout_childItems;

        CategoryItemListViewHolder(View itemView) {
            super(itemView);
            context = itemView.getContext();
            textView_parentName = itemView.findViewById(R.id.tv_bigCategoryName);
            img_category = itemView.findViewById(R.id.img_category);
            img_arrow = itemView.findViewById(R.id.img_arrow);
            linearLayout_childItems = itemView.findViewById(R.id.ll_child_items);
            linearLayout_childItems.setVisibility(View.GONE);
            int intMaxNoOfChild = 0;
            for (int index = 0; index < parentCategoryItems.size(); index++) {
                int intMaxSizeTemp = parentCategoryItems.get(index).getChildDataItems().size();
                if (intMaxSizeTemp > intMaxNoOfChild) intMaxNoOfChild = intMaxSizeTemp;
            }
            for (int indexView = 0; indexView < intMaxNoOfChild; indexView++) {
                TextView textView = new TextView(context);
                textView.setId(indexView);
                textView.setPadding(0, 20, 0, 20);
                textView.setGravity(Gravity.CENTER);
                textView.setTextSize(25);
                textView.setBackground(ContextCompat.getDrawable(context, R.drawable.background_sub_module_text));
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                textView.setOnClickListener(this);
                linearLayout_childItems.addView(textView, layoutParams);
            }
            img_category.setOnClickListener(this);
            textView_parentName.setOnClickListener(this);
            img_arrow.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (view.getId() == R.id.tv_bigCategoryName ||
                    view.getId() == R.id.img_category ||
                    view.getId() == R.id.img_arrow) {
                if (linearLayout_childItems.getVisibility() == View.VISIBLE) {
                    linearLayout_childItems.setVisibility(View.GONE);
                } else {
                    linearLayout_childItems.setVisibility(View.VISIBLE);
                }
            } else {
                TextView textViewClicked = (TextView) view;
                Toast.makeText(context, "" + textViewClicked.getText().toString(), Toast.LENGTH_SHORT).show();

                Intent mIntent = new Intent(mContext, ShowSelectedCategoryActivity.class);
//                mIntent.putStringArrayListExtra("productList", productList);
                mIntent.putExtra("productItemList", productItemList);

                mContext.startActivity(mIntent);

//                if(textViewClicked.getText().equals(CategoryUIConst.CATEGORY_PANTS)){
//
//                }else if(textViewClicked.getText().equals(getString(R.string.title_best_product))){
//
//                }else if(textViewClicked.getText().equals(CategoryUIConst.CATEGORY_COAT)){
//
//                }else if(textViewClicked.getText().equals(CategoryUIConst.CATEGORY_HAT)){
//
//                }else if(textViewClicked.getText().equals(CategoryUIConst.CATEGORY_SOCKS)){
//
//                }else if(textViewClicked.getText().equals(CategoryUIConst.CATEGORY_GLOVE)){
//
//                }else if(textViewClicked.getText().equals(CategoryUIConst.CATEGORY_ETC_CLOTHES)){
//
//                }else if(textViewClicked.getText().equals(CategoryUIConst.CATEGORY_CLOTHES)){
//
//                }else if(textViewClicked.getText().equals(CategoryUIConst.CATEGORY_HIGH_HEELS)){
//
//                }else if(textViewClicked.getText().equals(CategoryUIConst.CATEGORY_WALKER)){
//
//                }else if(textViewClicked.getText().equals(CategoryUIConst.CATEGORY_FLAT_SHOES)){
//
//                }else if(textViewClicked.getText().equals(CategoryUIConst.CATEGORY_SNEAKERS)){
//
//                }else if(textViewClicked.getText().equals(CategoryUIConst.CATEGORY_SLIPPERS)){
//
//                }else if(textViewClicked.getText().equals(CategoryUIConst.CATEGORY_KIDS_SHOES)){
//
//                }else if(textViewClicked.getText().equals(CategoryUIConst.CATEGORY_ETC_SHOES)){
//
//                }else if(textViewClicked.getText().equals(CategoryUIConst.CATEGORY_SHOES)){
//
//                }else if(textViewClicked.getText().equals(CategoryUIConst.CATEGORY_TODBACK)){
//
//                }else if(textViewClicked.getText().equals(CategoryUIConst.CATEGORY_WALLET)){
//
//                }else if(textViewClicked.getText().equals(CategoryUIConst.CATEGORY_CLUTCH_BAGS)){
//
//                }else if(textViewClicked.getText().equals(CategoryUIConst.CATEGORY_BACKPACK)){
//
//                }else if(textViewClicked.getText().equals(CategoryUIConst.CATEGORY_COIN_WALLET)){
//
//                }else if(textViewClicked.getText().equals(CategoryUIConst.CATEGORY_MERCHANDISE)){
//
//                }else if(textViewClicked.getText().equals(CategoryUIConst.CATEGORY_WATCH)){
//
//                }else if(textViewClicked.getText().equals(CategoryUIConst.CATEGORY_BRACELET)){
//
//                }else if(textViewClicked.getText().equals(CategoryUIConst.CATEGORY_ETC_ACCESSARIES)){
//
//                }else if(textViewClicked.getText().equals(CategoryUIConst.CATEGORY_ACCESSARIES)){
//
//                }else if(textViewClicked.getText().equals(CategoryUIConst.CATEGORY_DOLL)){
//
//                }else if(textViewClicked.getText().equals(CategoryUIConst.CATEGORY_BLOCKS)){
//
//                }else if(textViewClicked.getText().equals(CategoryUIConst.CATEGORY_ETC_TOYS)){
//
//                }else if(textViewClicked.getText().equals(CategoryUIConst.CATEGORY_TOYS)){
//
//                }else if(textViewClicked.getText().equals(CategoryUIConst.CATEGORY_CELLPHONE_ACCESSARIES)){
//
//                }else if(textViewClicked.getText().equals(CategoryUIConst.CATEGORY_ETC_ELECTRONICS)){
//
//                }else if(textViewClicked.getText().equals(CategoryUIConst.CATEGORY_ELECTRONICS)){
//
//                }
            }
        }
    }
}