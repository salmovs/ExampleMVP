package com.inspiringteam.mrnews.device;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.inspiringteam.mrnews.R;
import com.inspiringteam.mrnews.data.models.News;
import com.inspiringteam.mrnews.di.scopes.ActivityScoped;
import com.inspiringteam.mrnews.news.NewsAdapter;
import com.inspiringteam.mrnews.util.ConnectivityUtils.OnlineChecker;
import com.inspiringteam.mrnews.util.Constants;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import androidx.annotation.Nullable;
import dagger.android.DaggerFragment;



/**
 * News Screen {@link DeviceContract.View}
 */
@ActivityScoped
public class DeviceFragment extends DaggerFragment implements DeviceContract.View {

    private static final String TAG = "DeviceFragment";

    private DeviceAdapter mDeviceAdapter;
    private ListView mListViewNews;
    private TextView mTextViewNews;

    private TextView mTextViewAddDevice;
    private ImageView mImageViewImageAddDevice;

    @Inject
    DevicePresenter mDevicePresenter;

    @Inject
    OnlineChecker mOnlineChecker;


    @Inject
    public DeviceFragment(){
        // Required empty public constructor
    }


    /**
     * No initialize
     */
    //  DeviceItemListener mNewsItemListener = new DeviceItemListener();

    /**
     * Through this interface we establish a communication channel between
     * view (fragment) and its adapter
     */
    public interface DeviceItemListener {
      //  void onNewsClick(News clickedNews);
      //onAddDeviceClick(News toSaveNews);

      //  void onArchiveNewsClick(News toSaveNews);
    }


    @Override
    public void getImageLoaderService(Picasso picasso) {
        mDeviceAdapter.setImageLoaderService(picasso);
    }

    @Override
    public void showNoNews() {

        mDeviceAdapter.notifyDataSetInvalidated();
        mDeviceAdapter.clearContent();

        mTextViewNews.setVisibility(View.VISIBLE);

         }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Need  initialise adapter



        // adapter view click
       // mDeviceAdapter = new DeviceAdapter(new ArrayList<News>(0), )
        //adapter = new NewsAdapter(new ArrayList<News>(0), mNewsItemListener);
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_devices, container, false);
        mListViewNews = root.findViewById(R.id.list_devices);
        mTextViewNews = root.findViewById(R.id.no_device_tv);
        mTextViewNews.setVisibility(View.GONE);

        View rowView = inflater.inflate(R.layout.row_device_add, container, true);
        mTextViewAddDevice = rowView.findViewById(R.id.device_add_title);
        mImageViewImageAddDevice = rowView.findViewById(R.id.image_new_device_add);
        mTextViewAddDevice.setVisibility(View.VISIBLE);

        mImageViewImageAddDevice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClik Add New Device");
                mDevicePresenter.addNewDevice();
            }
        });



        mListViewNews.setAdapter(mDeviceAdapter);

        mDevicePresenter.loadDevices();

       // mDevicePresenter.loadNews(Constants.NEWS_CATEGORY_LATEST);

        return root;

    }

    @Override
    public void showNoDevice() {
       // mDeviceAdapter.notifyDataSetInvalidated();
      //  mDeviceAdapter.clearContent();

      //  mListViewNews.setVisibility(View.VISIBLE);

        mDeviceAdapter.notifyDataSetInvalidated();
        mDeviceAdapter.clearContent();
        mTextViewNews.setVisibility(View.VISIBLE);
    }

    @Override
    public void showNews(List<News> news) {
        mDeviceAdapter.replaceData(news);
        mListViewNews.setSelectionAfterHeaderView();
        mTextViewNews.setVisibility(View.GONE);
    }
}
