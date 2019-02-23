package com.inspiringteam.mrnews.data.source.local;


import com.inspiringteam.mrnews.data.models.Devices;

import java.util.List;



import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

/**
 * Room Dao interface
 */

@Dao
public interface DevicesDao {

    /***
     * select all device
     */
    @Query("SELECT * FROM devices")
    List<Devices> getDevices();


    @Query("SELECT* FROM devices WHERE entryid= :deviceId")
    Devices getDevicesById(int deviceId);

    /**
     * Insert device (item) in the database. If the news (item) already exists, replace it.
     * @param devices
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertDevices(Devices devices);


    /**
     * Delete a device  by id.
     *
     */
    @Query("DELETE FROM devices WHERE entryid= :deviceId")
    int deleteDeviceById(int deviceId);


    /**
     * Delete all device.
     */
    @Query("DELETE FROM devices")
    void deleteDevices();


}
