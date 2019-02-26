package com.inspiringteam.mrnews.data.source;

import android.util.Log;

import androidx.annotation.NonNull;

import com.inspiringteam.mrnews.data.models.Devices;
import com.inspiringteam.mrnews.data.models.News;
import com.inspiringteam.mrnews.data.source.scopes.Local;
import com.inspiringteam.mrnews.data.source.scopes.Remote;
import com.inspiringteam.mrnews.di.scopes.AppScoped;
import com.inspiringteam.mrnews.util.ConnectivityUtils.OnlineChecker;
import com.inspiringteam.mrnews.util.SortUtils;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

/**
 * News repository which handles all data retrieval logic
 */
@AppScoped
public class ApplicationRepository implements ApplicationDataSource {
    private static final String TAG = "ApplicationRepository";

    private final ApplicationDataSource mNewsRemoteDataSource;
    private final ApplicationDataSource mApplicationLocalDataSource;

    private final ApplicationDataSource mDevicesDataSource;
    private final OnlineChecker mOnlineChecker;

    @Inject
    ApplicationRepository(@Remote ApplicationDataSource newsRemoteDataSource,
                          @Local ApplicationDataSource applicationLocalDataSource,
                          @Local ApplicationDataSource devicesDataSource,
                          OnlineChecker onlineChecker) {
        mNewsRemoteDataSource = newsRemoteDataSource;
        //mNewsLocalDataSource = newsLocalDataSource;
        mApplicationLocalDataSource = applicationLocalDataSource;
        mDevicesDataSource = devicesDataSource;
        mOnlineChecker = onlineChecker;
    }

    /***
     * insert table devices
     */

    @Override
    public void insertDevice(Devices devices) {
        Log.d(TAG, "Start insertDEvice Application Repository");
        mApplicationLocalDataSource.insertDevice(devices);

    }

    /**
     * Show table devices
     * @param callback
     */

    @Override
    public void getDevices(@NonNull LoadDevicesCallback callback){

        Log.d(TAG, "Start GetDevice Application Repository");

        getDevicesFromLocalDataSource(callback);

        Log.d(TAG,"Start select local base device");
    }






    /**
     * Online First Scenario
     * First try accessing remote API if there is an active internet connection
     * further if remote DataSource fails, local API is queried
     * if offline, query directly local DataSource
     */
    @Override
    public void getNews(final String category, @NonNull final LoadNewsCallback callback) {
        // let's check for internet connection availability
        if (mOnlineChecker.isOnline()) {
            mNewsRemoteDataSource.getNews(category, new LoadNewsCallback() {
                @Override
                public void onDisposableAcquired(Disposable disposable) {
                    callback.onDisposableAcquired(disposable);
                }

                @Override
                public void onNewsLoaded(List<News> news) {
                    callback.onNewsLoaded(news);

                    // let's refresh repository
                    refreshNews(category, news);
                }

                @Override
                public void onDataNotAvailable() {
                    // even with internet connection we couldn't provide data online
                    // let's try from local Room DB
                    getNewsFromLocalDataSource(category, callback);
                }
            });
        } else {
            // if offline, retrieve data from local data source
            getNewsFromLocalDataSource(category, callback);
        }
    }




    /**
     * Offline First Scenario
     * First retrieve only saved news (items) from local DataSource
     * if local API fails, query Remote DataSource only if there is an active internet connection
     */
    @Override
    public void getArchivedNews(@NonNull final LoadSavedNewsCallback callback) {
       // mNewsLocalDataSource.getArchivedNews(new LoadSavedNewsCallback() {
        mApplicationLocalDataSource.getArchivedNews(new LoadSavedNewsCallback() {
            @Override
            public void onNewsLoaded(List<News> news) {
                callback.onNewsLoaded(news);
            }

            @Override
            public void onDataNotAvailable() {
                if (mOnlineChecker.isOnline())
                    getSavedNewsFromRemoteDataSource(callback);
                else callback.onDataNotAvailable();
            }

        });
    }

    private void getSavedNewsFromRemoteDataSource(@NonNull final LoadSavedNewsCallback callback) {
        mNewsRemoteDataSource.getArchivedNews(new LoadSavedNewsCallback() {
            @Override
            public void onNewsLoaded(List<News> news) {
                callback.onNewsLoaded(news);
            }

            @Override
            public void onDataNotAvailable() {
                callback.onDataNotAvailable();
            }
        });
    }

    private void getDevicesFromLocalDataSource(@NonNull final LoadDevicesCallback callback){
        mApplicationLocalDataSource.getDevices(new LoadDevicesCallback() {
            @Override
            public void onDisposableAcquired(Disposable disposable) {
                callback.onDisposableAcquired(disposable);
            }

            @Override
            public void onDevicesLoaded(List<Devices> devices) {
                callback.onDevicesLoaded(devices);
            }

            @Override
            public void onDataNotAvailable() {
                callback.onDataNotAvailable();

            }
        });
    }

    private void getNewsFromLocalDataSource(String category, @NonNull final LoadNewsCallback callback) {
        //mNewsLocalDataSource.getNews(category, new LoadNewsCallback() {
        mApplicationLocalDataSource.getNews(category, new LoadNewsCallback() {
            @Override
            public void onDisposableAcquired(Disposable disposable) {
                callback.onDisposableAcquired(disposable);
            }

            @Override
            public void onNewsLoaded(List<News> news) {
                callback.onNewsLoaded(news);
            }

            @Override
            public void onDataNotAvailable() {
                callback.onDataNotAvailable();
            }
        });
    }

    /**
     * this method refreshes repository data
     * 1st phase -  deleting all unsaved News (items) to make space for others
     * 2nd phase - replenishing repository with fresh news (items)
     */
    private void refreshNews(String category, List<News> news) {
        // 1st phase
        refreshNews(category);

        // 2nd phase
        for (News newsItem : news) {
            setNewsItemContent(newsItem, category);
            insertNews(newsItem);
        }
    }

    /**
     * just save news (item) to both remote and local data sources
     */
    @Override
    public void insertNews(News news) {
        //mNewsLocalDataSource.insertNews(news);
        mApplicationLocalDataSource.insertNews(news);
        mNewsRemoteDataSource.insertNews(news);
    }

    /**
     * just update news (item) to both remote and local data sources
     */
    @Override
    public void updateNews(News news) {
        //mNewsLocalDataSource.updateNews(news);
        mApplicationLocalDataSource.updateNews(news);
        mNewsRemoteDataSource.updateNews(news);
    }

    /**
     * deletes all news (excepting saved ones) from the repository
     * in order to make space for fresh items
     */
    @Override
    public void refreshNews(String category) {
        mNewsRemoteDataSource.refreshNews(category);
        mApplicationLocalDataSource.refreshNews(category);
        //mNewsLocalDataSource.refreshNews(category);
    }

    /**
     * complete deletion of all records in repository (with no exception)
     */
    @Override
    public void deleteNews() {
        //mNewsLocalDataSource.deleteNews();
        mApplicationLocalDataSource.deleteNews();
        mNewsRemoteDataSource.deleteNews();
    }


    private void setNewsItemContent(News newsItem, String category){
        // set unique id, category and source for current item before inserting it into repository
        newsItem.setId(SortUtils.hashCode(newsItem.getTitle()));
        newsItem.setCategory(category);
        newsItem.setSourceDataString(newsItem.getSourceData().getName());
    }
}