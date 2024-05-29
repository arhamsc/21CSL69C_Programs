package com.example.program_4_alarm_clock_21csl69c.singleton;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.provider.Settings;
import android.widget.Toast;

import com.example.program_4_alarm_clock_21csl69c.entities.AlarmListItem;
import com.example.program_4_alarm_clock_21csl69c.receivers.AlarmReceiver;

import java.util.Calendar;

public class AlarmSetter {
    private static AlarmSetter instance = null;
    Context context;
    AlarmManager alarmManager;
    PendingIntent pendingIntent;

    private MediaPlayer mediaPlayer;

    private AlarmSetter(Context context) {
        this.context = context.getApplicationContext();
        alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
    }

    public static AlarmSetter getInstance(Context context) {
        if (instance == null) {
            instance = new AlarmSetter(context);
        }
        return instance;
    }

    public void startAlarmSound() {
        mediaPlayer = MediaPlayer.create(context, Settings.System.DEFAULT_ALARM_ALERT_URI);
        mediaPlayer.start();
    }

    public void stopAlarmSound() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    public void setAlarm(AlarmListItem alarm) {
        final Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, alarm.getHour());
        calendar.set(Calendar.MINUTE, alarm.getMinutes());

        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.putExtra("id", alarm.getUid());

        pendingIntent = PendingIntent.getBroadcast(context, alarm.getUid(), intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        if (alarm.getInterval() != 0) {
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), (long) alarm.getInterval() * 1000 * 60, pendingIntent);
        } else {
            alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        }
    }

    public void cancelAlarm(AlarmListItem alarm) {
        Intent alrmRecevier = new Intent(context, AlarmReceiver.class);

        pendingIntent = PendingIntent.getBroadcast(context, alarm.getUid(), alrmRecevier, PendingIntent.FLAG_IMMUTABLE | PendingIntent.FLAG_UPDATE_CURRENT);
        if (pendingIntent != null) {
            alarmManager.cancel(pendingIntent);
            pendingIntent.cancel();
        }
    }

    public void snoozeAlarm(AlarmListItem alarm) {
        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.putExtra("id", alarm.getUid());
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, alarm.getUid(), intent, PendingIntent.FLAG_IMMUTABLE | PendingIntent.FLAG_UPDATE_CURRENT);
        // Set the snooze duration (10 minutes in this example)
        long snoozeDurationMillis = 10 * 60 * 1000; // 10 minutes in milliseconds

        // Set the alarm to go off after the snooze duration
        alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + snoozeDurationMillis, pendingIntent);
        Toast.makeText(context, "Alarm snoozed for 10 minutes", Toast.LENGTH_SHORT).show();
    }
}
