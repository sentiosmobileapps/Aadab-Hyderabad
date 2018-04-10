package com.mydways.aadabhyd.onboarding;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RelativeLayout;

import com.mydways.aadabhyd.R;
import com.mydways.aadabhyd.utilities.AppNavigator;

/**
 * @author Kishore Adda on 31/3/18.
 */
public class HomeScreenActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        RelativeLayout mNewsLayout = findViewById(R.id.rlayout_news);
        RelativeLayout mEpaperLayout = findViewById(R.id.rlayout_epaper);
        RelativeLayout mPollingLayout = findViewById(R.id.rlayout_polling);
        RelativeLayout mQuizLayout = findViewById(R.id.rlayout_quiz);
        RelativeLayout mPunchLayout = findViewById(R.id.rlayout_punches);
        RelativeLayout mGalleryLayout = findViewById(R.id.rlayout_gallery);
        mNewsLayout.setOnClickListener(this);
        mEpaperLayout.setOnClickListener(this);
        mPollingLayout.setOnClickListener(this);
        mQuizLayout.setOnClickListener(this);
        mPunchLayout.setOnClickListener(this);
        mGalleryLayout.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        AppNavigator navigator = new AppNavigator();
        switch (v.getId()) {
            case R.id.rlayout_news:
                navigator.navigateToLatestScrollingNews(this);
                break;
            case R.id.rlayout_epaper:
                navigator.navigateToEPaper(this);
                break;
            case R.id.rlayout_polling:
                navigator.navigateToPollingPage(this);
                break;
            case R.id.rlayout_quiz:
                navigator.navigateToQuizPage(this);
                break;
            case R.id.rlayout_punches:
                navigator.navigateToLatestPunch(this);
                break;
            case R.id.rlayout_gallery:
               // navigator.navigateToLatestPunch(this);
                break;
        }
    }
}
