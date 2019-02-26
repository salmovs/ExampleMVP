package com.inspiringteam.mrnews.data.source.local;

import android.util.Log;

import androidx.annotation.NonNull;

import com.inspiringteam.mrnews.data.models.Devices;
import com.inspiringteam.mrnews.data.models.News;
import com.inspiringteam.mrnews.data.source.ApplicationDataSource;
import com.inspiringteam.mrnews.di.scopes.AppScoped;
import com.inspiringteam.mrnews.util.ExecutorUtils.AppExecutors;

import java.util.List;

import javax.inject.Inject;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Local data Source implementation
 */

@AppScoped
public class NewsLocalDataSource implements ApplicationDataSource {
    private static final String TAG ="NewsLocalDataSource";

    private final NewsDao mNewsDao;
    private final DevicesDao mDevicesDao;
    private final AppExecutors mAppExecutors;

    @Inject
    public NewsLocalDataSource(@NonNull AppExecutors executors, @NonNull NewsDao newsDao, @NonNull DevicesDao devicesDao) {
        mDevicesDao = devicesDao;
        mNewsDao = newsDao;
        mAppExecutors = executors;
    }

    /**
     *
     * insert at table device new device
     */
    @Override
    public void insertDevice(@NonNull final Devices devices) {

        // let's fail fast
        checkNotNull(devices);
        Log.d(TAG,"Failt Null Device");

        Runnable saveRunnable = new Runnable() {
            @Override
            public void run() {
                mDevicesDao.insertDevices(devices);
            }
        };
        mAppExecutors.diskIO().execute(saveRunnable);

    }

    @Override
    public void getDevices(@NonNull final LoadDevicesCallback callback) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                final List<Devices> tasks = mDevicesDao.getDevices();
                mAppExecutors.mainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        if (tasks.isEmpty()) {
                            // This will be called if the table is new or just empty.
                            callback.onDataNotAvailable();
                        } else {
                            callback.onDevicesLoaded(tasks);
                        }
                    }
                });
            }
        };
        mAppExecutors.diskIO().execute(runnable);
    }

    /**
     * Simply gets unarchived news (items) from DB
     */
    @Override
    public void getNews(final String category, @NonNull final LoadNewsCallback callback) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                final List<News> tasks = mNewsDao.getNews(category);
                mAppExecutors.mainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        if (tasks.isEmpty()) {
                            // This will be called if the table is new or just empty.
                            callback.onDataNotAvailable();
                        } else {
                            callback.onNewsLoaded(tasks);
                        }
                    }
                });
            }
        };
        mAppExecutors.diskIO().execute(runnable);
    }

    /**
     * Simply adds a news (item) to Room DB
     */
    @Override
    public void insertNews(@NonNull final News news) {
        // let's fail fast
        checkNotNull(news);

        Runnable saveRunnable = new Runnable() {
            @Override
            public void run() {
                mNewsDao.insertNews(news);
            }
        };
        mAppExecutors.diskIO().execute(saveRunnable);
    }

    /**
     * Simply updates a news (item) in Room DB
     */
    @Override
    public void updateNews(@NonNull final News news) {
        // let's fail fast
        checkNotNull(news);

        Runnable updateRunnable = new Runnable() {
            @Override
            public void run() {
                mNewsDao.updateNews(news);
            }
        };
        mAppExecutors.diskIO().execute(updateRunnable);
    }

    /**
     * deleteNews() deletes all news (with no exception) from the Room database
     */
    @Override
    public void deleteNews() {
        Runnable deleteRunnable = new Runnable() {
            @Override
            public void run() {
                mNewsDao.deleteNews();
            }
        };
        mAppExecutors.diskIO().execute(deleteRunnable);
    }


    /**
     * deletes all news (excepting saved ones) from the Room database
     * in order to make space for fresh news
     */
    @Override
    public void refreshNews(final String category) {
        Runnable refreshRunnable = new Runnable() {
            @Override
            public void run() {
                mNewsDao.refreshNews(category);
            }
        };
        mAppExecutors.diskIO().execute(refreshRunnable);
    }

    /**
     * retrieves archived news (items) only
     */
    public void getArchivedNews(@NonNull final LoadSavedNewsCallback callback) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                final List<News> tasks = mNewsDao.getArchivedNews();
                mAppExecutors.mainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        if (tasks.isEmpty()) {
                            // This will be called if the table is new or just empty.
                            callback.onDataNotAvailable();
                        } else {
                            callback.onNewsLoaded(tasks);
                        }
                    }
                });
            }
        };
        mAppExecutors.diskIO().execute(runnable);
    }
}
