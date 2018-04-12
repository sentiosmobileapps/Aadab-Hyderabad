package com.mydways.aadabhyd.imagegallery.view;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.mydways.aadabhyd.R;
import com.mydways.aadabhyd.application.BaseActivity;
import com.mydways.aadabhyd.imagegallery.model.GalleryModel;
import com.mydways.aadabhyd.latestscrolling.view.LatestNewsActivity;
import com.mydways.aadabhyd.network.AppAPIManager;
import com.mydways.aadabhyd.network.BaseAPIResponse;
import com.mydways.aadabhyd.network.OnAPIResponseError;
import com.mydways.aadabhyd.network.OnAPIResponseSuccess;
import com.mydways.aadabhyd.utilities.AppConstant;
import com.squareup.otto.Subscribe;


import java.util.ArrayList;

public class GalleryViewActivity extends BaseActivity {

    public static final String mTAG = LatestNewsActivity.class.getSimpleName();


    private ArrayList<GalleryModel> items;
    RecyclerView recyclerView;
    GalleryAdapter adapter;

   // private SwipeRefreshLayout refreshLayout;
    private Toolbar toolbar;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);


        toolbar = findViewById(R.id.toolbar);
        recyclerView = findViewById(R.id.image_recycler_view);
        adapter = new GalleryAdapter(mEventBus,getApplicationContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false));

        recyclerView.setHasFixedSize(true);

        //RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(),2);
       // recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        toolbar.setTitle("Gallery");
        toolbar.setTitleTextColor(ContextCompat.getColor(this, android.R.color.black));
        setSupportActionBar(toolbar);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);


 /*       refreshLayout = findViewById(R.id.refreshlayout);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshLayout.setRefreshing(true);
                toolbar.setTitle("Gallery");
                apiManager = new AppAPIManager<BaseAPIResponse<ArrayList<LatestNewsItem>>>();
                apiManager.enqueue(getApplicationContext(), apiManager.getApiServices().fetchLatestScrolling(), mEventBus,
                        AppConstant.API_GALLERY);
            }
        });*/
    }

    @Override
    public void doAPICall() {
        apiManager = new AppAPIManager<BaseAPIResponse<ArrayList<GalleryModel>>>();
        apiManager.enqueue(this, apiManager.getApiServices().getAadabGallery(), mEventBus,
                AppConstant.API_GALLERY);

    }

    @Subscribe
    public void onAPIResponseSuccess(OnAPIResponseSuccess response) {
        Log.e(mTAG, "API Success");
        switch (response.apiId) {
            case AppConstant.API_GALLERY:
                BaseAPIResponse<ArrayList<GalleryModel>> list;
                list = (BaseAPIResponse<ArrayList<GalleryModel>>) response.response;
                items = list.responseResult;
                adapter.setAdapterData(items);
                break;

        }

    }

    @Subscribe
    public void onAPIResponseError(OnAPIResponseError error) {
        Log.e(mTAG, "API Error " + error.exception.getErrorMessage());
        showSnackbar(findViewById(R.id.drawer_layout), error.exception.getErrorMessage());
    }

    @Override
    protected void onStart() {
        super.onStart();
        doAPICall();
    }  @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

}
