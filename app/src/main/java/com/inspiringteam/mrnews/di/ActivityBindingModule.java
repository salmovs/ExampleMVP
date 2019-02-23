package com.inspiringteam.mrnews.di;


import com.inspiringteam.mrnews.device.DeviceActivity;
import com.inspiringteam.mrnews.device.DeviceModule;
import com.inspiringteam.mrnews.di.scopes.ActivityScoped;
import com.inspiringteam.mrnews.news.NewsActivity;
import com.inspiringteam.mrnews.news.NewsModule;


import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * We want Dagger.Android to create a Subcomponent which has a parent Component of
 * whichever module ActivityBindingModule is on (AppComponent, here).
 * we never need to tell AppComponent that it is going to have all or any of these subcomponents
 * nor do we need to tell these subcomponents that AppComponent exists.
 * We are also telling Dagger.Android that this generated SubComponent needs to include the specified modules and
 * be aware of a scope annotation @ActivityScoped
 * In this case, when Dagger.Android annotation processor runs it will create 1 subcomponent for us
 */
@Module
public abstract class ActivityBindingModule {
    @ActivityScoped
    @ContributesAndroidInjector(modules = NewsModule.class )
    abstract NewsActivity newsActivity();
    //abstract DeviceActivity deviceActivity();

    @ActivityScoped
    @ContributesAndroidInjector(modules = DeviceModule.class)
    abstract DeviceActivity deviceActivity();

}
