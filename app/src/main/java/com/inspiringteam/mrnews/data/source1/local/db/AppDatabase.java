package com.inspiringteam.mrnews.data.source1.local.db;



import com.inspiringteam.mrnews.data.models.Devices;
import com.inspiringteam.mrnews.data.models.LoraCDR;
import com.inspiringteam.mrnews.data.source.local.DevicesDao;
import com.inspiringteam.mrnews.data.source1.local.LoraCdrDao;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {LoraCDR.class, Devices.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public abstract DevicesDao deviceObject();
    public abstract LoraCdrDao loraCdrObject();

}
