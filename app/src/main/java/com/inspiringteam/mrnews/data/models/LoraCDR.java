package com.inspiringteam.mrnews.data.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "lora_cdr")
public class LoraCDR {

    @PrimaryKey
    @ColumnInfo(name = "cdr_id")
    private int id;

    @SerializedName("deveui")
    @Expose
    @ColumnInfo(name = "deveui")
    private String deveui;

    public LoraCDR(int id, String deveui) {
        this.id = id;
        this.deveui = deveui;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDeveui() {
        return deveui;
    }

    public void setDeveui(String deveui) {
        this.deveui = deveui;
    }
}
