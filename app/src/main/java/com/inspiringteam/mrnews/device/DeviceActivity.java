package com.inspiringteam.mrnews.device;



import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;
import com.inspiringteam.mrnews.R;
import com.inspiringteam.mrnews.news.NewsFragment;
import com.inspiringteam.mrnews.util.ActivityUtils;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import dagger.android.support.DaggerAppCompatActivity;

public class DeviceActivity extends DaggerAppCompatActivity {

    @Inject
    DevicePresenter mDevicePresenter;

    @Inject
    DeviceFragment mDeviceFragment;


    private DrawerLayout mDrawerLayout;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_devices);

        // Set up the toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setUpActionBar(toolbar);


        // Set up the navigation drawer.
        mDrawerLayout = findViewById(R.id.drawer_layout_device);
        mDrawerLayout.setStatusBarBackground(R.color.colorPrimaryDark);
        NavigationView navigationView = findViewById(R.id.nav_view_device);
        if (navigationView != null) {
            setupDrawerContent(navigationView);
        }

        /*
        // Set up fragment
        DeviceFragment fragment = (DeviceFragment) getFragmentManager().findFragmentById(R.id.contentFrame_device);
        if (fragment == null) {
            fragment = mDeviceFragment;
            ActivityUtils.addFragmentToActivity(getFragmentManager(), fragment, R.id.contentFrame_device);
        }
        */

        DeviceFragment fragment = (DeviceFragment) getFragmentManager().findFragmentById(R.id.contentFrame_device);
        if (fragment== null) {
            fragment = mDeviceFragment;
            ActivityUtils.addFragmentToActivity(getFragmentManager(), fragment, R.id.contentFrame_device);
        }

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                //// Open the navigation drawer when the home icon is selected from the toolbar.
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setCheckedItem(R.id.top_navigation_menu_item);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                        // Close the navigation drawer when an item is selected.
                        menuItem.setChecked(true);
                        mDrawerLayout.closeDrawers();

                        switch (menuItem.getItemId()) {

                            case R.id.saved_navigation_menu_item:
                                //mNewsPresenter.loadSavedNews();
                                break;

                            default:
                                break;
                        }
                        return true;
                    }
                }
        );
    }


    private void setUpActionBar(Toolbar toolbar) {
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setHomeAsUpIndicator(R.drawable.ic_menu);
            ab.setDisplayHomeAsUpEnabled(true);
        }
    }
}
