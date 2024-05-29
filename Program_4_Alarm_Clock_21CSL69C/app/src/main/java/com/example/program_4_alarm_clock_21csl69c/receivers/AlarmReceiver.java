package com.example.program_4_alarm_clock_21csl69c.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.example.program_4_alarm_clock_21csl69c.AlarmActiveActivity;
import com.example.program_4_alarm_clock_21csl69c.singleton.AlarmSetter;

public class AlarmReceiver extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "Alarm! Wake up! Wake up!", Toast.LENGTH_LONG).show();

        AlarmSetter.getInstance(context).startAlarmSound();

        final int uid = intent.getIntExtra("id", 0);

        final Intent alarmActiveIntent = new Intent(context, AlarmActiveActivity.class);
        alarmActiveIntent.putExtra("id", uid);
        alarmActiveIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        context.startActivity(alarmActiveIntent);
    }


}
