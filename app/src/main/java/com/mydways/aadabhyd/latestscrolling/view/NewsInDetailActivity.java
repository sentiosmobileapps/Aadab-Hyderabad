package com.mydways.aadabhyd.latestscrolling.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mydways.aadabhyd.R;
import com.mydways.aadabhyd.latestscrolling.model.LatestNewsItem;
import com.mydways.aadabhyd.network.AppAPIManager;
import com.mydways.aadabhyd.onboarding.HomeScreenActivity;
import com.mydways.aadabhyd.utilities.AppConstant;
import com.mydways.aadabhyd.utilities.AppUtils;

/**
 * @author Kishore Adda on 31/3/18.
 */
public class NewsInDetailActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_in_detail);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Full News");
        toolbar.setTitleTextColor(ContextCompat.getColor(this,android.R.color.black));

        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(ContextCompat.getDrawable(this,R.drawable
                .ic_arrow_back_black_24dp));
        LatestNewsItem item = new Gson().fromJson(getIntent().getStringExtra("data"),
                new TypeToken<LatestNewsItem>() {
                }.getType());
        AppCompatTextView textViewContent = findViewById(R.id.textview_news_detail);
        AppCompatTextView textViewTitle = findViewById(R.id.textview_news_title);
        AppCompatImageView imageView = findViewById(R.id.imageview_news_image);
        textViewContent.setText(item.description);
        textViewTitle.setText(item.title);
        Glide.with(this).load(item.img).apply(new RequestOptions().centerInside()).into(imageView);
        FloatingActionButton fab= findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                StringBuilder builder = new StringBuilder();

                builder.append(AppUtils.BASE_URL);
                builder.append("/article/");
                builder.append(String.valueOf(item.id));

                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, builder.toString());
                sendIntent.setType("text/plain");
                startActivity(sendIntent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.news, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        Log.e("Latest News", "onOptionsItemSelected");
        //noinspection SimplifiableIfStatement
        if (id == R.id.item_home) {
            Intent intent = new Intent(this,HomeScreenActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
            return true;
        } else {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }


}
