package com.inspiringteam.mrnews.data.source.local;

import com.inspiringteam.mrnews.data.models.Devices;
import com.inspiringteam.mrnews.di.scopes.AppScoped;
import com.inspiringteam.mrnews.util.ExecutorUtils.AppExecutors;

import javax.inject.Inject;

import androidx.annotation.NonNull;

import static com.google.common.base.Preconditions.checkNotNull;

@AppScoped
public class DevicesLocalDataSource  {

    private final DevicesDao mDevicesDao;
    private final AppExecutors mAppExecutors;

    @Inject
    public DevicesLocalDataSource (@NonNull AppExecutors executors, @NonNull DevicesDao devicesDao){
        mAppExecutors = executors;
        mDevicesDao = devicesDao;
    }
/*
    @Override
    public void insertDevices(@NonNull final Devices devices) {
        // let's fail fast
        checkNotNull(devices);

        Runnable saveRunnable = new Runnable() {
            @Override
            public void run() {
                mDevicesDao.insertDevices(devices);
            }
        };
        mAppExecutors.diskIO().execute(saveRunnable);
    }
    */
}
