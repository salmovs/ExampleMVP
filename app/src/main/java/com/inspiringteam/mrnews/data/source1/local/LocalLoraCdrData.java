package com.inspiringteam.mrnews.data.source1.local;

import com.inspiringteam.mrnews.data.models.LoraCDR;
import com.inspiringteam.mrnews.data.source1.DataSource;
import com.inspiringteam.mrnews.data.source1.local.db.AppDatabase;

import javax.inject.Inject;

public class LocalLoraCdrData implements DataSource<LoraCDR> {

   private AppDatabase database;

   @Inject
    public LocalLoraCdrData(AppDatabase database){ this.database = database; }


    @Override
    public void addItem(LoraCDR item) {

    }
}
