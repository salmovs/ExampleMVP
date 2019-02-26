package com.inspiringteam.mrnews.data.source1.repository;

import com.inspiringteam.mrnews.data.source1.local.LocalDevicesData;
import com.inspiringteam.mrnews.data.source1.local.LocalLoraCdrData;

public interface Repository {

    LocalDevicesData deviceData();

    LocalLoraCdrData loraCdrData();

}
