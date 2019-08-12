package net.clipcodes.myapplication.ui.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import net.clipcodes.myapplication.models.AdditionalProductInfo;
import net.clipcodes.myapplication.ui.activities.DetailProductActivity;
import net.clipcodes.myapplication.models.BasicProductInfo;
import net.clipcodes.myapplication.R;
import net.clipcodes.myapplication.utils.Libraries;

import java.io.File;
import java.util.List;

public class BestProductListAdapter extends RecyclerView.Adapter<BestProductListViewHolder> {

    private Context mContext;
    private List<AdditionalProductInfo> productList;

    public BestProductListAdapter(Context mContext, List<AdditionalProductInfo> productList) {
        this.mContext = mContext;
        this.productList = productList;
    }

    @Override
    public BestProductListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product_list, parent, false);
        return new BestProductListViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(final BestProductListViewHolder holder, int position) {

        holder.mImage.setImageBitmap(Libraries.getBitmapFromFile(new File(productList.get(position).getImageURLPathList().get(0))));
        holder.mTitle.setText(productList.get(position).getName());
        holder.tvPriceTag.setText(String.valueOf(productList.get(position).getPrice()));
        holder.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
        Intent mIntent = new Intent(mContext, DetailProductActivity.class);
        mIntent.putExtra("Title", productList.get(holder.getAdapterPosition()).getName());
        mIntent.putExtra("Description", productList.get(holder.getAdapterPosition()).getDescription());
        mIntent.putExtra("Price", String.valueOf(productList.get(holder.getAdapterPosition()).getPrice()));
        mIntent.putExtra("ItemCnt", String.valueOf(productList.get(holder.getAdapterPosition()).getItemCount()));
        mIntent.putExtra("SellerName", productList.get(holder.getAdapterPosition()).getSellerName());
        mIntent.putStringArrayListExtra("ImageURLPathList", productList.get(holder.getAdapterPosition()).getImageURLPathList());
        mContext.startActivity(mIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }
}

class BestProductListViewHolder extends RecyclerView.ViewHolder {
    CardView mCardView;
    ImageView mImage;
    TextView mTitle;
    TextView tvPriceTag;
    BestProductListViewHolder(View itemView) {
        super(itemView);

        mImage = itemView.findViewById(R.id.ivImage);
        mTitle = itemView.findViewById(R.id.tvTitle);
        mCardView = itemView.findViewById(R.id.cardview);
        tvPriceTag = itemView.findViewById(R.id.tvPriceTag);

    }
}