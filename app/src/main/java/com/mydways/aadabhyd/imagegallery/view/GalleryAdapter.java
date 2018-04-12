package com.mydways.aadabhyd.imagegallery.view;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.mydways.aadabhyd.R;
import com.mydways.aadabhyd.imagegallery.model.GalleryModel;
import com.mydways.aadabhyd.latestscrolling.event.AdapterItemClickEvent;
import com.squareup.otto.Bus;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.GalleryViewHolder> {

    private Context mContext;
    private ArrayList<GalleryModel> items = new ArrayList<>();
    private Bus bus;

    public GalleryAdapter(Bus bus,Context context) {
        this.bus = bus;
        this.mContext=context;
    }

    @NonNull
    @Override
    public GalleryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout
                .gallery_card, parent, false);
        mContext = parent.getContext();
        GalleryViewHolder viewHolder = new GalleryViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull GalleryViewHolder holder, int position) {
        GalleryModel item = getItem(position);

        /*Glide.with(mContext).load(item.getImage_url())
                .apply(new RequestOptions().centerCrop())
                .apply(new RequestOptions().placeholder(R.mipmap.ic_launcher))
                .into(holder.imageViewPic);*/
        if(item!=null)

        Picasso.with(mContext).load(item.default_src).fit()
                .centerCrop().into(holder.imageViewPic);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    //Intent intent = new Intent(android.content.Intent.ACTION_VIEW);
                    Uri data = Uri.parse(items.get(position).default_src);
                    //intent.setDataAndType(data, "image/*");
                    Intent viewImageIntent = new Intent(android.content.Intent.ACTION_VIEW, data);
                    mContext.startActivity(viewImageIntent);
                }catch (Exception e){

                }

            }
        });

    }


    private GalleryModel getItem(int position) {
        return items.get(position);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setAdapterData(ArrayList<GalleryModel> list) {
        items = list;
        notifyDataSetChanged();
    }

    class GalleryViewHolder extends RecyclerView.ViewHolder{

        /*AppCompatTextView textViewHeader;
        AppCompatTextView textViewSubTitle;*/
        ImageView imageViewPic;

        public GalleryViewHolder(View itemView) {
            super(itemView);
       /*     textViewHeader = itemView.findViewById(R.id.textview_news_title);
            textViewSubTitle = itemView.findViewById(R.id.textview_news_short_desc);*/
            imageViewPic = itemView.findViewById(R.id.img_android);

        }

    }

}
