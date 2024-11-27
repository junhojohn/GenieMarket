package kr.co.geniemarket.ui.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;

import kr.co.geniemarket.R;
import kr.co.geniemarket.models.AdditionalProductInfo;
import kr.co.geniemarket.ui.activities.DetailProductActivity;
import kr.co.geniemarket.utils.Libraries;

import java.io.File;
import java.util.List;

public class ShowSelectedCategoryProductListAdapter extends RecyclerView.Adapter<ShowSelectedCategortProductListHolder> {

    private Context mContext;
    private List<AdditionalProductInfo> productList;

    public ShowSelectedCategoryProductListAdapter(Context mContext, List<AdditionalProductInfo> productList) {
        this.mContext = mContext;
        this.productList = productList;
    }

    @Override
    public ShowSelectedCategortProductListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product_list, parent, false);
        return new ShowSelectedCategortProductListHolder(mView);
    }

    @Override
    public void onBindViewHolder(final ShowSelectedCategortProductListHolder holder, int position) {
        long currTime = System.currentTimeMillis();
        Glide.with(mContext)
                .load(productList.get(position).getImageURLPathList().get(0))
                .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                .into(holder.mImage);
        holder.mTitle.setText(productList.get(position).getName());
        holder.tvPriceTag.setText(String.valueOf(productList.get(position).getPrice()));
        holder.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mIntent = new Intent(mContext, DetailProductActivity.class);
                mIntent.putExtra("Title", productList.get(holder.getPosition()).getName());
                mIntent.putExtra("Description", productList.get(holder.getPosition()).getDescription());
                mIntent.putExtra("Price", String.valueOf(productList.get(holder.getPosition()).getPrice()));
                mIntent.putExtra("ItemCnt", String.valueOf(productList.get(holder.getPosition()).getItemCount()));
                mIntent.putExtra("SellerName", productList.get(holder.getPosition()).getSellerName());
                mIntent.putStringArrayListExtra("ImageURLPathList", productList.get(holder.getPosition()).getImageURLPathList());
                mContext.startActivity(mIntent);
            }
        });
        System.out.println("onBindViewHolder: " + (System.currentTimeMillis() - currTime));
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }
}

class ShowSelectedCategortProductListHolder extends RecyclerView.ViewHolder {
    CardView mCardView;
    ImageView mImage;
    TextView mTitle;
    TextView tvPriceTag;
    ShowSelectedCategortProductListHolder(View itemView) {
        super(itemView);

        mImage = itemView.findViewById(R.id.ivImage);
        mTitle = itemView.findViewById(R.id.tvTitle);
        mCardView = itemView.findViewById(R.id.cardview);
        tvPriceTag = itemView.findViewById(R.id.tvPriceTag);

    }
}