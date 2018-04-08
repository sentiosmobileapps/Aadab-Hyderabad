package com.mydways.aadabhyd.punches.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.mydways.aadabhyd.R;
import com.mydways.aadabhyd.application.BaseActivity;
import com.mydways.aadabhyd.network.AppAPIManager;
import com.mydways.aadabhyd.network.BaseAPIResponse;
import com.mydways.aadabhyd.network.OnAPIResponseError;
import com.mydways.aadabhyd.network.OnAPIResponseSuccess;
import com.mydways.aadabhyd.punches.adapter.PunchGalleryAdapter;
import com.mydways.aadabhyd.punches.model.PunchItem;
import com.mydways.aadabhyd.utilities.AppConstant;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;

/**
 * @author Kishore Adda on 1/4/18.
 */
public class PunchGaleryActivity extends BaseActivity {
    private ArrayList<PunchItem> list = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private PunchGalleryAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_punch_gallery);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Aadab Punches");
        toolbar.setTitleTextColor(ContextCompat.getColor(this, android.R.color.black));

        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(ContextCompat.getDrawable(this, R.drawable
                .ic_arrow_back_black_24dp));
        mRecyclerView = findViewById(R.id.recyclerview_punch_gallery);
        mAdapter = new PunchGalleryAdapter();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager
                .HORIZONTAL, false));
        PagerSnapHelper helper = new PagerSnapHelper();
        helper.attachToRecyclerView(mRecyclerView);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
        doAPICall();
    }

    @Override
    public void doAPICall() {
        apiManager = new AppAPIManager<BaseAPIResponse<ArrayList<PunchItem>>>();
        apiManager.enqueue(this, apiManager.getApiServices().getAadabPunches(), mEventBus,
                AppConstant.API_FETCH_PUNCHES);
    }

    @Subscribe
    public void onAPIResponseSuccess(OnAPIResponseSuccess response) {
        switch (response.apiId) {
            case AppConstant.API_FETCH_PUNCHES:

                BaseAPIResponse<ArrayList<PunchItem>> punchItem;
                punchItem = (BaseAPIResponse<ArrayList<PunchItem>>) response.response;
                list = punchItem.responseResult;
                Log.e("Punches",new Gson().toJson(list));
                mAdapter.setData(list);
                break;
        }
    }

    @Subscribe
    public void onAPIResponseError(OnAPIResponseError error) {

        showSnackbar(findViewById(R.id.layout), error.exception.getErrorMessage());
    }
}
