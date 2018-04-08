package com.mydways.aadabhyd.latestscrolling.view;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;

import com.mydways.aadabhyd.R;
import com.mydways.aadabhyd.latestscrolling.adapter.NavigationDrawerItemAdapter;
import com.mydways.aadabhyd.latestscrolling.event.GetLatestNews;
import com.mydways.aadabhyd.latestscrolling.event.OnNewsCategoryClicked;
import com.mydways.aadabhyd.latestscrolling.model.DisplayViewType;
import com.mydways.aadabhyd.latestscrolling.model.DrawerItem;
import com.mydways.aadabhyd.latestscrolling.model.NavigationItemViewType;
import com.mydways.aadabhyd.utilities.AppConstant;
import com.mydways.aadabhyd.utilities.AppNavigator;
import com.mydways.aadabhyd.application.BaseActivity;
import com.mydways.aadabhyd.latestscrolling.adapter.LatestNewsAdapter;
import com.mydways.aadabhyd.latestscrolling.event.AdapterItemClickEvent;
import com.mydways.aadabhyd.latestscrolling.model.LatestNewsItem;
import com.mydways.aadabhyd.network.AppAPIManager;
import com.mydways.aadabhyd.network.BaseAPIResponse;
import com.mydways.aadabhyd.network.OnAPIResponseError;
import com.mydways.aadabhyd.network.OnAPIResponseSuccess;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;

public class LatestNewsActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    public static final String mTAG = LatestNewsActivity.class.getSimpleName();

    private LatestNewsAdapter mAdapter;
    private NavigationDrawerItemAdapter mNavigationAdapter;
    private RecyclerView mNavigationRV;
    private RecyclerView mLatestNewsRecyclerView;
    private ArrayList<LatestNewsItem> items;
    private SwipeRefreshLayout refreshLayout;
    private ArrayList<DrawerItem> navigationItems;
    private DisplayViewType mType = DisplayViewType.LATEST_NEWS;
    private DrawerItem drawerItem;
    private DrawerLayout drawer;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        toolbar = findViewById(R.id.toolbar);
        mLatestNewsRecyclerView = findViewById(R.id.recyclerview_home_news);
        mNavigationAdapter = new NavigationDrawerItemAdapter();
        mNavigationAdapter.setEventBus(mEventBus);
        mNavigationRV = findViewById(R.id.recyclerview_navigation_drawer);
        mNavigationRV.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager
                .VERTICAL, false));
        mNavigationRV.setAdapter(mNavigationAdapter);
        mAdapter = new LatestNewsAdapter(mEventBus);
        mLatestNewsRecyclerView.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false));
        mLatestNewsRecyclerView.setAdapter(mAdapter);
        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        toolbar.setTitle("Home");
        toolbar.setTitleTextColor(ContextCompat.getColor(this, android.R.color.black));
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        refreshLayout = findViewById(R.id.refreshlayout);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshLayout.setRefreshing(true);
                if (mType == DisplayViewType.LATEST_NEWS) {
                    getLatestNews(new GetLatestNews());
                } else {
                    onNewsCategoryClicked(new OnNewsCategoryClicked(drawerItem));
                }

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        doAPICall();
        refreshLayout.setRefreshing(true);
    }

    @Override
    public void doAPICall() {
        apiManager = new AppAPIManager<BaseAPIResponse<ArrayList<LatestNewsItem>>>();
        apiManager.enqueue(this, apiManager.getApiServices().fetchLatestScrolling(), mEventBus,
                AppConstant.API_FETCH_LATEST_NEWS);
        AppAPIManager navigation = new AppAPIManager<BaseAPIResponse<ArrayList<DrawerItem>>>();
        navigation.enqueue(this, navigation.getApiServices().getMenuItems(), mEventBus,
                AppConstant.API_NAVIGATION_MENU);
    }

    @Subscribe
    public void onAPIResponseSuccess(OnAPIResponseSuccess response) {
        Log.e(mTAG, "API Success");
        switch (response.apiId) {
            case AppConstant.API_FETCH_LATEST_NEWS:
                refreshLayout.setRefreshing(false);
                BaseAPIResponse<ArrayList<LatestNewsItem>> list;
                list = (BaseAPIResponse<ArrayList<LatestNewsItem>>) response.response;
                items = list.responseResult;
                mAdapter.setAdapterData(items);
                break;
            case AppConstant.API_NAVIGATION_MENU:
                BaseAPIResponse<ArrayList<DrawerItem>> arrayResponse;
                arrayResponse = (BaseAPIResponse<ArrayList<DrawerItem>>) response.response;
                navigationItems = new ArrayList<>();
                DrawerItem headerItem = new DrawerItem();
                headerItem.viewType = NavigationItemViewType.VIEW_TYPE_HEADER.value;
                headerItem.category_name = new String();
                navigationItems = arrayResponse.responseResult;
                for (int i = 0; i < navigationItems.size(); i++) {
                    navigationItems.get(i).viewType = NavigationItemViewType.VIEW_TYPE_CATEGORY
                            .value;
                }
                navigationItems.add(0, headerItem);
                mNavigationAdapter.setNavigationItems(navigationItems);
                break;
            case AppConstant.API_CATEGORY_NEWS:
                refreshLayout.setRefreshing(false);
                list = (BaseAPIResponse<ArrayList<LatestNewsItem>>) response.response;
                items = list.responseResult;
                mAdapter.setAdapterData(items);
                break;
        }

    }

    @Subscribe
    public void onAPIResponseError(OnAPIResponseError error) {
        Log.e(mTAG, "API Error " + error.exception.getErrorMessage());
        refreshLayout.setRefreshing(false);
        showSnackbar(findViewById(R.id.drawer_layout), error.exception.getErrorMessage());
    }

    @Subscribe
    public void onAdapterItemClicked(AdapterItemClickEvent event) {
        new AppNavigator().navigateToNewsDetails(this, items.get(event.position));
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
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
            finish();
            return true;
        } else {
            drawer.openDrawer(Gravity.START);
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Subscribe
    public void onNewsCategoryClicked(OnNewsCategoryClicked event) {
        refreshLayout.setRefreshing(true);
        drawer.closeDrawer(GravityCompat.START);
        mType = DisplayViewType.CATEGORY_NEWS;
        drawerItem = event.getItem();
        toolbar.setTitle(drawerItem.category_name);
        apiManager = new AppAPIManager<BaseAPIResponse<ArrayList<LatestNewsItem>>>();
        apiManager.enqueue(this, apiManager.getApiServices().getNewsByCategory(Integer
                .parseInt(drawerItem.category_id)), mEventBus, AppConstant.API_CATEGORY_NEWS);
    }

    @Subscribe
    public void getLatestNews(GetLatestNews event) {
        refreshLayout.setRefreshing(true);
        drawer.closeDrawer(GravityCompat.START);
        mType = DisplayViewType.LATEST_NEWS;
        toolbar.setTitle("Home");
        apiManager = new AppAPIManager<BaseAPIResponse<ArrayList<LatestNewsItem>>>();
        apiManager.enqueue(this, apiManager.getApiServices().fetchLatestScrolling(), mEventBus,
                AppConstant.API_FETCH_LATEST_NEWS);
    }
}
