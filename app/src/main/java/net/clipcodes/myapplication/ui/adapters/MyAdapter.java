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

import net.clipcodes.myapplication.ui.activities.DetailProductActivity;
import net.clipcodes.myapplication.models.BasicProductInfo;
import net.clipcodes.myapplication.R;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter< FlowerViewHolder > {

    private Context mContext;
    private List<BasicProductInfo> mFlowerList;

    public MyAdapter(Context mContext, List<BasicProductInfo> mFlowerList) {
        this.mContext = mContext;
        this.mFlowerList = mFlowerList;
    }

    @Override
    public FlowerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product_list, parent, false);
        return new FlowerViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(final FlowerViewHolder holder, int position) {
        holder.mImage.setImageResource(mFlowerList.get(position).getImageList().get(0));
        holder.mTitle.setText(mFlowerList.get(position).getName());
        holder.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mIntent = new Intent(mContext, DetailProductActivity.class);
                mIntent.putExtra("Title", mFlowerList.get(holder.getAdapterPosition()).getName());
                mIntent.putExtra("Description", mFlowerList.get(holder.getAdapterPosition()).getDescription());
                mIntent.putIntegerArrayListExtra("ImageList", mFlowerList.get(holder.getAdapterPosition()).getImageList());
                mContext.startActivity(mIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mFlowerList.size();
    }
}

class FlowerViewHolder extends RecyclerView.ViewHolder {
    CardView mCardView;
    ImageView mImage;
    TextView mTitle;

    FlowerViewHolder(View itemView) {
        super(itemView);

        mImage = itemView.findViewById(R.id.ivImage);
        mTitle = itemView.findViewById(R.id.tvTitle);
        mCardView = itemView.findViewById(R.id.cardview);
    }
}