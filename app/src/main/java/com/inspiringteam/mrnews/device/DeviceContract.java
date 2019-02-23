package com.inspiringteam.mrnews.device;

import com.inspiringteam.mrnews.data.models.News;
import com.inspiringteam.mrnews.mvp.BaseView;
import com.inspiringteam.mrnews.news.NewsContract;
import com.squareup.picasso.Picasso;

import java.util.List;

public interface DeviceContract{

    interface View extends BaseView<Presenter>{
        ///void showNews(List<News> news);

        void showNews(List<News> news);

        void showNoNews();

        void getImageLoaderService(Picasso picasso);

        void showNoDevice();


    }

    interface Presenter {

        void addNewDevice();

        void loadDevices();

        void loadNews(String category);

    }
}
