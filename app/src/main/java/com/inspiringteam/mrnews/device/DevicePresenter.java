package com.inspiringteam.mrnews.device;

import android.bluetooth.BluetoothClass;
import android.content.Context;
import android.util.Log;

import com.inspiringteam.mrnews.data.models.Devices;
import com.inspiringteam.mrnews.data.models.News;
import com.inspiringteam.mrnews.data.source.ApplicationDataSource;
import com.inspiringteam.mrnews.data.source.ApplicationRepository;
import com.inspiringteam.mrnews.data.source1.repository.Repository;
import com.inspiringteam.mrnews.di.scopes.ActivityScoped;
import com.inspiringteam.mrnews.mvp.BasePresenter;
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

    private Devices mDevices;

     Repository mRepository;
    private Context mContext;

    private final ApplicationRepository mApplicationRepository;
        //private final ApplicationRepository mNewsRepository;
    private CompositeDisposable disposables;
    private final ChromeTabsWrapper mTabsWrapper;


    @Inject
    DevicePresenter(ApplicationRepository applicationRepository, CompositeDisposable disposable,
                    ChromeTabsWrapper tabsWrapper){
        disposables = disposable;
        mApplicationRepository = applicationRepository;
                    //  mNewsRepository = newsRepository;
        mTabsWrapper = tabsWrapper;
    }


    // inject separately ImageLoader client so that tests do not have to care about it
    @Inject
    Picasso mPicasso;


/*    @Inject
    DevicePresenter(Context context, Repository repository){
        this.mContext = context;
        this.mRepository = repository;
    }
*/

    /***
     * Adding new device in local db
     */
    @Override
    public void addNewDevice() {

        Log.d(TAG,"Starting add  device in local db");

        // The request might be handled in a different thread so make sure Espresso knows
        // that the app is busy until the response is handled.
        notifyEspressoAppIsBusy();



        mDevices = new Devices("AABBCCFFF","SAEBIS15");
        mApplicationRepository.insertDevice(mDevices);
        //mApplicationRepository.getDevices();


        //Log.d(TAG, "SAEBIS"+ mDevices.toString());

        //mApplicationRepository.inserDevices();
        //mApplicationRepository.insertDevices();



    }

    /***
     * For show lora device and show add new device
     *
     */
    @Override
    public void loadDevices() {

        mApplicationRepository.getDevices(new ApplicationDataSource.LoadDevicesCallback() {
            @Override
            public void onDisposableAcquired(Disposable disposable) {
                addDisposable(disposable);
            }

            @Override
            public void onDevicesLoaded(List<Devices> devices) {

                processDataToBeDisplayed(devices);

            }

            @Override
            public void onDataNotAvailable() {
                processEmptyDeviceList();
            }
        });


        //
       //processEmptyDeviceList();
    }


    private void processDataToBeDisplayed(List<Devices> devices) {


        if (devices.isEmpty()) {
            processEmptyDeviceList();
            Log.d(TAG, "No found devices local base: "+ devices);
        } else {

            Log.d(TAG, "Found devices local base: ");
            for (int i=0; i<devices.size(); i++){
                devices.get(i);
                Log.d(TAG, "Device id: "+ i);
                Log.d(TAG, "Found devices local base: "
                        +" Id: "+devices.get(i).getId()
                        +" DevEui: "+ devices.get(i).getDeveui()
                        +" AppEui: "+ devices.get(i).getAppeui()) ;
            }
            view.showDevices(devices);
            //processEmptyDeviceList();

            //view.getImageLoaderService(mPicasso);
           // view.showNews(SortUtils.orderNewsByNewest(devices));
        }
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

    }


    /*    @Override
    public void loadNews(String category) {
        // The request might be handled in a different thread so make sure Espresso knows
        // that the app is busy until the response is handled.
        notifyEspressoAppIsBusy();

        //mNewsRepository.getNews(category, new ApplicationDataSource.LoadNewsCallback() {
        mApplicationRepository.getNews(category, new ApplicationDataSource.LoadNewsCallback() {
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

*/


    private void processEmptyDataList() {
        if (view == null) return;
        view.showNoNews();
    }
/*
    private void processDataToBeDisplayed(List<News> news) {
        if (news.isEmpty()) {
            processEmptyDataList();
        } else {
            view.getImageLoaderService(mPicasso);
            view.showNews(SortUtils.orderNewsByNewest(news));
        }

    }
    */
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
