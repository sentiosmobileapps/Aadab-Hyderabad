package com.mydways.aadabhyd.punches.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.mydways.aadabhyd.R;
import com.mydways.aadabhyd.punches.model.PunchItem;

import java.util.ArrayList;

/**
 * @author Kishore Adda on 1/4/18.
 */
public class PunchGalleryAdapter extends RecyclerView.Adapter<PunchGalleryAdapter.PunchViewHolder>{

    private Context mContext;
    private ArrayList<PunchItem> items = new ArrayList<>();
    @NonNull
    @Override
    public PunchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_punch,parent,false);
        return new PunchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PunchViewHolder holder, int position) {
        PunchItem item = items.get(position);
        holder.mTextView.setText(item.name);
        Glide.with(mContext).load(item.img_path)
                .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL))
                .apply(new RequestOptions().placeholder(R.drawable.ic_logo))
                .into(holder
                .mImageView);
    }

    public void setData(ArrayList<PunchItem> data){
        items = data;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class PunchViewHolder extends RecyclerView.ViewHolder{
        AppCompatTextView mTextView;
        AppCompatImageView mImageView;
        public PunchViewHolder(View itemView) {
            super(itemView);
            mTextView = itemView.findViewById(R.id.textview_punch_title);
            mImageView = itemView.findViewById(R.id.imageview_punch);
        }
    }

}
