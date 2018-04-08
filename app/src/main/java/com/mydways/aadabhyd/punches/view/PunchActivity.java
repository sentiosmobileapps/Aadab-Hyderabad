package com.mydways.aadabhyd.punches.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.bumptech.glide.Glide;
import com.mydways.aadabhyd.R;
import com.mydways.aadabhyd.application.BaseActivity;
import com.mydways.aadabhyd.network.AppAPIManager;
import com.mydways.aadabhyd.network.BaseAPIResponse;
import com.mydways.aadabhyd.network.OnAPIResponseError;
import com.mydways.aadabhyd.network.OnAPIResponseSuccess;
import com.mydways.aadabhyd.punches.model.PunchItem;
import com.mydways.aadabhyd.utilities.AppConstant;
import com.mydways.aadabhyd.utilities.AppNavigator;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;

/**
 * @author Kishore Adda on 1/4/18.
 */
public class PunchActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_latest_punch);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Aadab Punch");
        toolbar.setTitleTextColor(ContextCompat.getColor(this, android.R.color.black));

        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(ContextCompat.getDrawable(this, R.drawable
                .ic_arrow_back_black_24dp));
        AppCompatButton button = findViewById(R.id.button_view_all);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AppNavigator().navigateToPunchGalleryActivity(PunchActivity.this);
            }
        });
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
        apiManager.enqueue(this, apiManager.getApiServices().getAadabPunch(), mEventBus,
                AppConstant.API_FETCH_PUNCH);
    }

    @Subscribe
    public void onAPIResponseSuccess(OnAPIResponseSuccess response) {
        switch (response.apiId) {
            case AppConstant.API_FETCH_PUNCH:

                BaseAPIResponse<ArrayList<PunchItem>> punchItem;
                punchItem = (BaseAPIResponse<ArrayList<PunchItem>>) response.response;
                ((AppCompatTextView) findViewById(R.id.textview_punch_title)).setText(punchItem
                        .responseResult.get(0).name);
                AppCompatImageView imageView = findViewById(R.id.imageview_punch);
                Glide.with(this).load(punchItem.responseResult.get(0).img_path).into(imageView);
                break;
        }
    }

    @Subscribe
    public void onAPIResponseError(OnAPIResponseError error) {

        showSnackbar(findViewById(R.id.layout), error.exception.getErrorMessage());
    }
}
