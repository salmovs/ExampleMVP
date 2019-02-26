package com.inspiringteam.mrnews.data.source1.local;

import android.bluetooth.BluetoothClass;

import com.inspiringteam.mrnews.data.models.Devices;
import com.inspiringteam.mrnews.data.source1.DataSource;
import com.inspiringteam.mrnews.data.source1.local.db.AppDatabase;

import javax.inject.Inject;

public class LocalDevicesData implements DataSource<Devices> {

    private AppDatabase database;

    @Inject
    public LocalDevicesData (AppDatabase database) { this.database = database; }


    @Override
    public void addItem(Devices item) {

    }
}
