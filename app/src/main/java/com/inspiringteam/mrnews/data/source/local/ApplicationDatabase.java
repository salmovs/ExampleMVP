package com.inspiringteam.mrnews.data.source.local;

import android.bluetooth.BluetoothClass;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.inspiringteam.mrnews.data.models.Devices;
import com.inspiringteam.mrnews.data.models.News;
import com.inspiringteam.mrnews.data.source.local.DevicesDao;
import com.inspiringteam.mrnews.data.source.local.NewsDao;

/**
 * The Room Database that contains the News table.
 */

@Database(entities = {News.class, Devices.class}, version = 2, exportSchema = false)
public abstract class ApplicationDatabase extends RoomDatabase {
    public abstract NewsDao newsDao();
    public abstract DevicesDao devicesDao();

}
