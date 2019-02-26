package com.inspiringteam.mrnews.data.source1.local;

import com.inspiringteam.mrnews.data.models.LoraCDR;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface LoraCdrDao {

    /***
     * select last 10  cdr
     */
    @Query("SELECT * FROM lora_cdr limit 10")
    List<LoraCDR> getLoraCDR();


    @Query("SELECT* FROM lora_cdr WHERE cdr_id= :cdrId")
    LoraCDR getLoraCdrById(int cdrId);

    /**
     * Insert cdr in the database. If the news (item) already exists, replace it.
     * @param
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertLoraCdr(LoraCDR loraCDR);


    /**
     * Delete all cdr.
     */
    @Query("DELETE FROM lora_cdr")
    void deleteLoraCdr();



}



