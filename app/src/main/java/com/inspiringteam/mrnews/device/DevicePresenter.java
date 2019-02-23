package com.inspiringteam.mrnews.device;

import android.util.Log;

import com.inspiringteam.mrnews.data.models.News;
import com.inspiringteam.mrnews.data.source.NewsDataSource;
import com.inspiringteam.mrnews.data.source.NewsRepository;
import com.inspiringteam.mrnews.di.scopes.ActivityScoped;
import com.inspiringteam.mrnews.mvp.BasePresenter;
import com.inspiringteam.mrnews.news.NewsContract;
import com.inspiringteam.mrnews.util.ChromeTabsUtils.ChromeTabsWrapper;
import com.inspiringteam.mrnews.util.EspressoIdlingResource;
import com.inspiringteam.mrnews.util.SortUtils;
import com.squareup.picasso.Picasso;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

@ActivityScoped
public class DevicePresenter extends BasePresenter<DeviceContract.View> implements DeviceContract.Presenter {

    private static final String TAG = "DevicePresenter";

    private final NewsRepository mNewsRepository;
    private CompositeDisposable disposables;
    private final ChromeTabsWrapper mTabsWrapper;


    @Inject
    DevicePresenter(NewsRepository newsRepository, CompositeDisposable disposable,
                    ChromeTabsWrapper tabsWrapper){
        disposables = disposable;
        mNewsRepository = newsRepository;
        mTabsWrapper = tabsWrapper;
    }


    // inject separately ImageLoader client so that tests do not have to care about it
    @Inject
    Picasso mPicasso;


    /***
     * Adding new device in local db
     */
    @Override
    public void addNewDevice() {
        Log.d(TAG,"Starting add  device in local db");

        // The request might be handled in a different thread so make sure Espresso knows
        // that the app is busy until the response is handled.
        notifyEspressoAppIsBusy();



    }

    /***
     * For show lora device and show add new device
     *
     */
    @Override
    public void loadDevices() {


        //
        processEmptyDeviceList();
    }

    /***
     * Show no find Device
     *
     */

    private void processEmptyDeviceList(){
        if (view == null) return;
         //Contract view show no device
           view.showNoDevice();


    };

    /**
     * retrieve all unarchived news (items) from repository
     */
    @Override
    public void loadNews(String category) {
        // The request might be handled in a different thread so make sure Espresso knows
        // that the app is busy until the response is handled.
        notifyEspressoAppIsBusy();

        mNewsRepository.getNews(category, new NewsDataSource.LoadNewsCallback() {
            @Override
            public void onDisposableAcquired(Disposable disposable) {
                addDisposable(disposable);
            }

            @Override
            public void onNewsLoaded(List<News> news) {
                notifyEspressoAppIsIdle();
                processDataToBeDisplayed(news);
            }

            @Override
            public void onDataNotAvailable() {
                notifyEspressoAppIsIdle();
                processEmptyDataList();
            }
        });
    }




    private void processEmptyDataList() {
        if (view == null) return;
        view.showNoNews();
    }

    private void processDataToBeDisplayed(List<News> news) {
        if (news.isEmpty()) {
            processEmptyDataList();
        } else {
            view.getImageLoaderService(mPicasso);
            view.showNews(SortUtils.orderNewsByNewest(news));
        }

    }
    private void notifyEspressoAppIsIdle() {
        // let's make sure the app is still marked as busy then decrement
        if (!EspressoIdlingResource.getIdlingResource().isIdleNow()) {
            EspressoIdlingResource.decrement(); // Set app as idle.
        }
    }

    private void notifyEspressoAppIsBusy() {
        EspressoIdlingResource.increment(); // App is busy until further notice
    }


    private void addDisposable(Disposable disposable) {
        disposables.add(disposable);
    }




}
