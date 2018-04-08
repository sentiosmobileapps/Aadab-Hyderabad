package com.mydways.aadabhyd.onboarding;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.mydways.aadabhyd.R;
import com.mydways.aadabhyd.utilities.AppNavigator;

/**
 * @author Kishore Adda on 31/3/18.
 */
public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);
    }

    @Override
    protected void onStart() {
        super.onStart();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                new AppNavigator().navigateToHomeScreen(SplashScreenActivity.this);
                finish();
            }
        }, 3000);
    }
}
