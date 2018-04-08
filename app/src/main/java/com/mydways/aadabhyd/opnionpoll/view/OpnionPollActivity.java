package com.mydways.aadabhyd.opnionpoll.view;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatRadioButton;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RadioGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.mydways.aadabhyd.R;
import com.mydways.aadabhyd.application.BaseActivity;
import com.mydways.aadabhyd.network.AppAPIManager;
import com.mydways.aadabhyd.network.BaseAPIResponse;
import com.mydways.aadabhyd.network.OnAPIResponseError;
import com.mydways.aadabhyd.network.OnAPIResponseSuccess;
import com.mydways.aadabhyd.opnionpoll.model.ViwerPolling;
import com.mydways.aadabhyd.utilities.AppConstant;
import com.squareup.otto.Subscribe;

import static com.mydways.aadabhyd.latestscrolling.view.LatestNewsActivity.mTAG;

public class OpnionPollActivity extends BaseActivity {

    private AppCompatImageView imageView;
    private AppCompatTextView textview;
    private RadioGroup radioGroup;
    private AppCompatRadioButton opt1;
    private AppCompatRadioButton opt2;
    private AppCompatRadioButton opt3;
    private AppCompatRadioButton opt4;
    private ViwerPolling polling;

    private AppCompatTextView opt1Result;
    private ProgressBar mProgressOpt1;
    private AppCompatTextView opt2Result;
    private ProgressBar mProgressOpt2;
    private AppCompatTextView opt3Result;
    private ProgressBar mProgressOpt3;
    private AppCompatTextView opt4Result;
    private ProgressBar mProgressOpt4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opnion_poll);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(ContextCompat.getColor(this, android.R.color.black));
        toolbar.setTitle("Poll Question");
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        imageView = findViewById(R.id.imageview_polling);
        textview = findViewById(R.id.textview_polling_question);
        radioGroup = findViewById(R.id.radiogroup_polling_options);
        opt1 = findViewById(R.id.option1);
        opt2 = findViewById(R.id.option2);
        opt3 = findViewById(R.id.option3);
        opt4 = findViewById(R.id.option4);
        opt1Result = findViewById(R.id.textview_result_opt1);
        mProgressOpt1 = findViewById(R.id.progress_opt1);
        opt2Result = findViewById(R.id.textview_result_opt2);
        mProgressOpt2 = findViewById(R.id.progress_opt2);
        opt3Result = findViewById(R.id.textview_result_opt3);
        mProgressOpt3 = findViewById(R.id.progress_opt3);
        opt4Result = findViewById(R.id.textview_result_opt4);
        mProgressOpt4 = findViewById(R.id.progress_opt4);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                String optionSelected = "";
                switch (checkedId) {
                    case R.id.option1:
                        optionSelected = "opt1";
                        break;
                    case R.id.option2:
                        optionSelected = "opt2";
                        break;
                    case R.id.option3:
                        optionSelected = "opt3";
                        break;
                    case R.id.option4:
                        optionSelected = "opt4";
                        break;
                }
                if (!TextUtils.isEmpty(optionSelected)) {
                    postPolling(optionSelected);
                }
            }
        });
    }

    private void postPolling(String option) {
        apiManager = new AppAPIManager<BaseAPIResponse<ViwerPolling>>();
        apiManager.enqueue(this, apiManager.getApiServices().postViewerPolling(Integer.parseInt(polling.poll_id),
                option), mEventBus, AppConstant.API_POST_POLLING_OPTION);
    }

    @Override
    protected void onStart() {
        super.onStart();
        doAPICall();
    }

    @Override
    public void doAPICall() {
        apiManager = new AppAPIManager<BaseAPIResponse<ViwerPolling>>();
        apiManager.enqueue(this, apiManager.getApiServices().getLatestPollingList(), mEventBus,
                AppConstant.API_GET_POLLING_OPTION);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Subscribe
    public void onAPIResponseSuccess(OnAPIResponseSuccess response) {
        Log.e(mTAG, "API Success");
        switch (response.apiId) {
            case AppConstant.API_GET_POLLING_OPTION:
                BaseAPIResponse<ViwerPolling> list;
                list = (BaseAPIResponse<ViwerPolling>) response.response;
                polling = list.responseResult;
                textview.setText(polling.poll_question);
                Glide.with(OpnionPollActivity.this)
                        .load(polling.img)
                        .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL))
                        .into(imageView);
                opt1.setText(polling.opt1);
                opt2.setText(polling.opt2);
                opt3.setText(polling.opt3);
                break;
            case AppConstant.API_POST_POLLING_OPTION:
                BaseAPIResponse<ViwerPolling> list1;
                list1 = (BaseAPIResponse<ViwerPolling>) response.response;
                polling = list1.responseResult;
                int opt1Vote = Integer.parseInt(polling.opt1_votes);
                int opt2Vote = Integer.parseInt(polling.opt2_votes);
                int opt3Vote = Integer.parseInt(polling.opt3_votes);
                int total = opt1Vote + opt2Vote + opt3Vote;
                radioGroup.setVisibility(View.GONE);
                //opt1Result.setText(polling.opt1 + "    " + polling.opt1_votes + " Votes (" +(total / opt1Vote) + "%)");
                opt1Result.setText(polling.opt1 + "    " + polling.opt1_votes + " Votes");
                opt1Result.setVisibility(View.VISIBLE);
//                opt2Result.setText(polling.opt2 + "    " + polling.opt2_votes + " Votes (" + (total / opt2Vote) + "%)");
                opt2Result.setText(polling.opt2 + "    " + polling.opt2_votes + " Votes");
                opt2Result.setVisibility(View.VISIBLE);
//                opt3Result.setText(polling.opt3 + "    " + polling.opt3_votes + " Votes (" + (total / opt3Vote) + "%)");
                opt3Result.setText(polling.opt3 + "    " + polling.opt3_votes + " Votes");
                opt3Result.setVisibility(View.VISIBLE);
                mProgressOpt1.setVisibility(View.GONE);
                mProgressOpt2.setVisibility(View.GONE);
                mProgressOpt3.setVisibility(View.GONE);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    if (opt1Vote > 0) {
                        mProgressOpt1.setProgress((total / opt1Vote), true);
                    } else {
                        mProgressOpt1.setProgress(0, true);
                    }
                    if (opt2Vote > 0) {
                        mProgressOpt2.setProgress((total / opt2Vote), true);
                    } else {
                        mProgressOpt2.setProgress(0, true);
                    }
                    if (opt3Vote > 0) {
                        mProgressOpt3.setProgress((total / opt3Vote), true);
                    } else {
                        mProgressOpt3.setProgress(0, true);
                    }
                } else {
                    if (opt1Vote > 0) {
                        mProgressOpt1.setProgress(total / opt1Vote);
                    } else {
                        mProgressOpt1.setProgress(0);
                    }
                    if (opt2Vote > 0) {
                        mProgressOpt2.setProgress(total / opt2Vote);
                    } else {
                        mProgressOpt2.setProgress(0);
                    }
                    if (opt3Vote > 0) {
                        mProgressOpt3.setProgress(total / opt3Vote);
                    } else {
                        mProgressOpt3.setProgress(0);
                    }
                }
                break;
        }

    }

    @Subscribe
    public void onAPIResponseError(OnAPIResponseError error) {
        Log.e(mTAG, "API Error " + error.exception.getErrorMessage());
        showSnackbar(findViewById(R.id.drawer_layout), error.exception.getErrorMessage());
    }
}
