package com.mydways.aadabhyd.latestscrolling.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mydways.aadabhyd.R;
import com.mydways.aadabhyd.latestscrolling.event.GetLatestNews;
import com.mydways.aadabhyd.latestscrolling.event.OnNewsCategoryClicked;
import com.mydways.aadabhyd.latestscrolling.model.DrawerItem;
import com.mydways.aadabhyd.latestscrolling.model.NavigationItemViewType;
import com.squareup.otto.Bus;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 * @author Kishore Adda on 3/4/18.
 */
public class NavigationDrawerItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<DrawerItem> list = new ArrayList<>();

    public void setEventBus(Bus mEventBus) {
        this.mEventBus = mEventBus;
    }

    private Bus mEventBus;

    public void setNavigationItems(ArrayList<DrawerItem> items) {
        list = items;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        return list.get(position).viewType;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        if (viewType == NavigationItemViewType.VIEW_TYPE_HEADER.value) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_header_view,
                    parent, false);
            viewHolder = new HeaderViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout
                    .item_news_category_view, parent, false);
            viewHolder = new CategoryViewHolder(view);
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        DrawerItem item = getItem(position);
        Log.e("ViewType",item.viewType+" "+item.category_name);
        if (item.viewType == NavigationItemViewType.VIEW_TYPE_CATEGORY.value) {
            CategoryViewHolder categoryViewHolder = (CategoryViewHolder) holder;
            categoryViewHolder.textViewCategory.setText(item.category_name);
        } else {
            HeaderViewHolder headerViewHolder = (HeaderViewHolder) holder;
            SimpleDateFormat format = new SimpleDateFormat("d-MMM-yyyy", Locale.getDefault());
            headerViewHolder.textView.setText(format.format(new Date()));
        }
    }

    DrawerItem getItem(int index) {
        return list.get(index);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        AppCompatTextView textViewCategory;

        public CategoryViewHolder(View itemView) {
            super(itemView);
            textViewCategory = itemView.findViewById(R.id.textview_category_name);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mEventBus.post(new OnNewsCategoryClicked(list.get(getAdapterPosition())));
        }
    }

    public class HeaderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        AppCompatTextView textView;

        public HeaderViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textview_date_display);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mEventBus.post(new GetLatestNews());
        }
    }
}
