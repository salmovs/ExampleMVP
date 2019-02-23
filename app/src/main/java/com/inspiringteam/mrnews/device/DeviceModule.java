package com.inspiringteam.mrnews.device;

import com.inspiringteam.mrnews.di.scopes.ActivityScoped;
import com.inspiringteam.mrnews.di.scopes.FragmentScoped;

import com.inspiringteam.mrnews.util.ChromeTabsUtils.ChromeTabsWrapper;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.android.ContributesAndroidInjector;
import io.reactivex.disposables.CompositeDisposable;


@Module(includes = DeviceModule.DeviceAbstractModule.class)
public class DeviceModule {
    @ActivityScoped
    @Provides
    CompositeDisposable provideCompositeDisposable() {
        return new CompositeDisposable();
    }


    @ActivityScoped
    @Provides
    ChromeTabsWrapper providesChromeTabsWrapper(DeviceActivity context) {
        return new ChromeTabsWrapper(context);
    }



    @Module
    public interface DeviceAbstractModule {
        @ActivityScoped
        @Binds
        DeviceContract.Presenter devicePresenter(DevicePresenter presenter);


        @FragmentScoped
        @ContributesAndroidInjector
        DeviceFragment deviceFragment();
    }

}
