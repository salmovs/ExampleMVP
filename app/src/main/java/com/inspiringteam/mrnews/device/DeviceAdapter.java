package com.inspiringteam.mrnews.device;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.inspiringteam.mrnews.R;
import com.inspiringteam.mrnews.data.models.Devices;
import com.inspiringteam.mrnews.data.models.News;
import com.inspiringteam.mrnews.util.SortUtils;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

public class DeviceAdapter extends BaseAdapter {

    private List<News> mNewsList = new ArrayList<>();
    private List<Devices> mDevices = new ArrayList<>();


    private Picasso mPicasso;
    private boolean isInternetAccess;

    private DeviceFragment.DeviceItemListener  mItemListener;



    public void onDeviceAdd (){

    }

    public DeviceAdapter(List<Devices> devices, DeviceFragment.DeviceItemListener itemListener) {
        setList(devices);
        mItemListener = itemListener;
    }



    @Override
    public int getCount() {
        return mDevices.size();
    }

    @Override
    public Devices getItem(int position) {
        return mDevices.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        View rowView = view;
        if (rowView == null){
            LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
            rowView = inflater.inflate(R.layout.row_news_item, viewGroup, false);
        }

        final Devices currentDevice = getItem(i);

        //final ImageView newsImageView = rowView.findViewById(R.id.news_image_id);
        final TextView newsTitleTextView = rowView.findViewById(R.id.news_title_view);
        final TextView sourceTextView = rowView.findViewById(R.id.news_source_view);
        //final ImageButton archiveImagebutton = rowView.findViewById(R.id.archive_news_ib);
        //final TextView newsDateTextView = rowView.findViewById(R.id.news_date_view);
        //archiveImagebutton.setVisibility(View.VISIBLE);

        newsTitleTextView.setText(currentDevice.getDeveui());

        sourceTextView.setText(currentDevice.getDeveui());



        /*
        if (currentNews.isArchived()) {
            archiveImagebutton.setVisibility(View.GONE);
        }
        */
/*
        if (mPicasso != null) {
            if (isInternetAccess) {
                mPicasso.load(currentNews.getUrlToImage()).networkPolicy(NetworkPolicy.NO_CACHE)
                        .fit().into(newsImageView);
                newsTitleTextView.setText(currentNews.getTitle());
            } else {
                mPicasso.load(currentNews.getUrlToImage()).networkPolicy(NetworkPolicy.OFFLINE)
                        .fit().into(newsImageView);
                newsTitleTextView.setText(currentNews.getTitle());
            }
        }

        // let's check if the current item has been retrieved from remote source
        if(currentNews.getSourceData() != null) {
            sourceTextView.setText(currentNews.getSourceData().getName());
        } else {
            // it is being fetched locally, so let's get the assigned field for this case
            sourceTextView.setText(currentNews.getSourceDataString());
        }

        newsDateTextView.setText(SortUtils.getDateDisplayString(currentNews.getPublishedAt()));
*/

        // Need Onclick


        return rowView;
    }

    public void replaceData(List<Devices> devices) {
        setList(devices);
        notifyDataSetChanged();
    }

    public void setList(List<Devices> devices) {
        mDevices = checkNotNull(devices);
    }

    public void clearContent() {
        mDevices.clear();
    }

    public void setImageLoaderService(Picasso picasso) {
        mPicasso = picasso;
    }

}
