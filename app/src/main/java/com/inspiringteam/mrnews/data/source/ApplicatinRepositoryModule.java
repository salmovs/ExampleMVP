package com.inspiringteam.mrnews.data.source;

//import com.inspiringteam.mrnews.data.source.local.DevicesDao;
//import com.inspiringteam.mrnews.data.source.local.DevicesDao_Impl;
//import com.inspiringteam.mrnews.data.source.local.DevicesLocalDataSource;

import com.inspiringteam.mrnews.data.source.local.DevicesDao;
import com.inspiringteam.mrnews.data.source.local.NewsDao;
import com.inspiringteam.mrnews.data.source.local.ApplicationLocalDataModule;
import com.inspiringteam.mrnews.data.source.local.NewsLocalDataSource;
import com.inspiringteam.mrnews.data.source.remote.ApplicationRemoteDataSource;
import com.inspiringteam.mrnews.data.source.remote.NewsRemoteDataModule;
import com.inspiringteam.mrnews.data.source.remote.NewsService;
import com.inspiringteam.mrnews.data.source.scopes.Local;
import com.inspiringteam.mrnews.data.source.scopes.Remote;
import com.inspiringteam.mrnews.di.scopes.AppScoped;
import com.inspiringteam.mrnews.util.ExecutorUtils.AppExecutors;

import androidx.annotation.NonNull;
import dagger.Module;
import dagger.Provides;

/**
 * ApplicatinRepositoryModule contains both Local And Remote Data Sources modules
 */

@Module(includes = {NewsRemoteDataModule.class, ApplicationLocalDataModule.class})
public class ApplicatinRepositoryModule {

    @Provides
    @Local
    @AppScoped
    ApplicationDataSource provideNewsLocalDataSource(AppExecutors appExecutors, NewsDao newsDao, DevicesDao devicesDao) {
        return new NewsLocalDataSource(appExecutors, newsDao, devicesDao);
    }


/*
    @Provides
    @AppScoped
    ApplicationDataSource provideDevicesLocalDataSource(AppExecutors appExecutors, DevicesDao devicesDao) {
        return new DevicesLocalDataSource( appExecutors, devicesDao);
    }
*/

    @Provides
    @Remote
    @AppScoped
    ApplicationDataSource provideNewsRemoteDataSource(NewsService newsService) {
        return new ApplicationRemoteDataSource(newsService);
    }



}
