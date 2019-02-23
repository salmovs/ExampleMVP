package com.inspiringteam.mrnews.data.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "devices")
public final class Devices {



    @PrimaryKey
    @ColumnInfo(name = "entryid")
    private int id;

    @SerializedName("deveui")
    @Expose
    @ColumnInfo(name = "deveui")
    private String deveui;

    @SerializedName("appeui")
    @Expose
    @ColumnInfo(name = "appeui")
    private String appeui;


    public Devices(int id, String deveui, String appeui) {
        this.id = id;
        this.deveui = deveui;
        this.appeui = appeui;
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

    public String getAppeui() {
        return appeui;
    }

    public void setAppeui(String appeui) {
        this.appeui = appeui;
    }
}
