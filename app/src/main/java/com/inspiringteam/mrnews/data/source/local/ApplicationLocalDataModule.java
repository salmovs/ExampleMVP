package com.inspiringteam.mrnews.data.source.local;

import android.app.Application;
import androidx.room.Room;

import com.inspiringteam.mrnews.di.scopes.AppScoped;
import com.inspiringteam.mrnews.util.Constants;
import com.inspiringteam.mrnews.util.ExecutorUtils.AppExecutors;
import com.inspiringteam.mrnews.util.ExecutorUtils.DiskIOThreadExecutor;

import java.util.concurrent.Executors;

import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationLocalDataModule {
    private static final int THREAD_COUNT = 3;

    @AppScoped
    @Provides
    ApplicationDatabase provideDb(Application context) {
        return Room.databaseBuilder(context.getApplicationContext(),
                ApplicationDatabase.class, Constants.NEWS_ROOM_DB_STRING)
                .build();
    }

    @AppScoped
    @Provides
    NewsDao provideNewsDao(ApplicationDatabase db) {
        return db.newsDao();
    }


    @AppScoped
    @Provides
    DevicesDao provideDevicesDao(ApplicationDatabase db){ return db.devicesDao(); }




    @AppScoped
    @Provides
    AppExecutors provideAppExecutors() {
        return new AppExecutors(new DiskIOThreadExecutor(),
                Executors.newFixedThreadPool(THREAD_COUNT),
                new AppExecutors.MainThreadExecutor());
    }
}
