package com.mydways.aadabhyd.application;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;

import com.mydways.aadabhyd.R;
import com.mydways.aadabhyd.network.AppAPIManager;
import com.squareup.otto.Bus;

/**
 * @author Kishore Adda on 31/3/18.
 */
public abstract class BaseActivity extends AppCompatActivity {

    protected AlertDialog mLoader = null;
    protected Bus mEventBus;
    protected AppAPIManager apiManager;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLoader = getLoader();
        mEventBus = new Bus();
        mEventBus.register(this);
    }



    @Override
    protected void onDestroy() {
        mEventBus.unregister(this);
        super.onDestroy();

    }

    protected AlertDialog getLoader() {
        if (mLoader == null) {
            AlertDialog.Builder builder
                    = new AlertDialog.Builder(this);
            View view = LayoutInflater.from(this).inflate(R.layout.layout_progress_dailog, null);
            builder.setView(view);
            mLoader = builder.create();
        }
        return mLoader;
    }

    public abstract void doAPICall();

    public void showSnackbar(View view,String message){
        Snackbar.make(view,message,Snackbar.LENGTH_SHORT).show();
    }

}
