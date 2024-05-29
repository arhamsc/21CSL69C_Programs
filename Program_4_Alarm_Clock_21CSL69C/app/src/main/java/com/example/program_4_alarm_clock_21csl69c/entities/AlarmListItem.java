package com.example.program_4_alarm_clock_21csl69c.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "alarm_list_items")
public class AlarmListItem {

    @PrimaryKey(autoGenerate = true)
    private int uid;

    @ColumnInfo(name = "title")
    private String title;

    @ColumnInfo(name = "hour")
    private int hour;

    @ColumnInfo(name = "minutes")
    private int minutes;

    @ColumnInfo(name = "interval")
    private int interval;

    @ColumnInfo(name = "isActive")
    private boolean isActive;

    @ColumnInfo(name = "snoozeTime")
    private int snoozeTime;


    public AlarmListItem(String title, int hour, int minutes, int interval, boolean isActive) {
        this.title = title;
        this.hour = hour;
        this.minutes = minutes;
        this.interval = interval;
        this.isActive = isActive;
        this.snoozeTime = 15;
    }

    public int getUid() {
        return uid;
    }

    public String getTitle() {
        return title;
    }

    public int getHour() {
        return hour;
    }

    public int getMinutes() {
        return minutes;
    }

    public int getInterval() {
        return interval;
    }

    public boolean isActive() {
        return isActive;
    }

    public int getSnoozeTime() {
        return snoozeTime;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    public void setSnoozeTime(int snoozeTime) {
        this.snoozeTime = snoozeTime;
    }
}
