package com.example.program_4_alarm_clock_21csl69c.entities;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface AlarmItemDao {
    @Insert
    long insert(AlarmListItem alarmListItem);

    @Delete
    void delete(AlarmListItem alarmListItem);

    @Query("SELECT * FROM alarm_list_items ORDER BY uid desc")
    List<AlarmListItem> getAll();

    @Query("SELECT * FROM alarm_list_items WHERE uid = :uid")
    AlarmListItem getAlarm(int uid);

    @Update
    void update(AlarmListItem alarmListItem);
}
