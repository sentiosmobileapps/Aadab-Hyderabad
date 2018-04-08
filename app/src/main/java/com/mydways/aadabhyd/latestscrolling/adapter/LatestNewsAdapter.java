package com.mydways.aadabhyd.latestscrolling.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.mydways.aadabhyd.R;
import com.mydways.aadabhyd.latestscrolling.event.AdapterItemClickEvent;
import com.mydways.aadabhyd.latestscrolling.model.LatestNewsItem;
import com.squareup.otto.Bus;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Kishore Adda on 31/3/18.
 */
public class LatestNewsAdapter extends RecyclerView.Adapter<LatestNewsAdapter.LatestNewsViewHolder> {

    private Context mContext;
    private ArrayList<LatestNewsItem> items = new ArrayList<>();
    private Bus bus;

    public LatestNewsAdapter(Bus bus) {
        this.bus = bus;
    }

    @NonNull
    @Override
    public LatestNewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout
                .item_latest_news_view, parent, false);
        mContext = parent.getContext();
        LatestNewsViewHolder viewHolder = new LatestNewsViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull LatestNewsViewHolder holder, int position) {
        LatestNewsItem item = getItem(position);
        holder.textViewHeader.setText(item.title);
        holder.textViewSubTitle.setText(item.description);
        Glide.with(mContext).load(item.img)
                .apply(new RequestOptions().centerCrop())
                .apply(new RequestOptions().placeholder(R.mipmap.ic_launcher))
                .into(holder.imageViewPic);
    }

    private LatestNewsItem getItem(int position) {
        return items.get(position);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setAdapterData(ArrayList<LatestNewsItem> list) {
        items = list;
        notifyDataSetChanged();
    }

    class LatestNewsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        AppCompatTextView textViewHeader;
        AppCompatTextView textViewSubTitle;
        AppCompatImageView imageViewPic;

        public LatestNewsViewHolder(View itemView) {
            super(itemView);
            textViewHeader = itemView.findViewById(R.id.textview_news_title);
            textViewSubTitle = itemView.findViewById(R.id.textview_news_short_desc);
            imageViewPic = itemView.findViewById(R.id.imageview_news_image);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(bus!=null){
                bus.post(new AdapterItemClickEvent(getAdapterPosition()));
            }
        }
    }

}
