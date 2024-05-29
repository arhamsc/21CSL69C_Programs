package com.example.program_4_alarm_clock_21csl69c;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TimePicker;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.program_4_alarm_clock_21csl69c.database.AppDatabase;
import com.example.program_4_alarm_clock_21csl69c.entities.AlarmItemDao;
import com.example.program_4_alarm_clock_21csl69c.entities.AlarmListItem;
import com.example.program_4_alarm_clock_21csl69c.singleton.AlarmSetter;

public class AddAlarmActivity extends AppCompatActivity {
    AlarmItemDao dao;
    EditText alarmTitle;
    CheckBox alarmRepeat;
    EditText alarmInterval;
    Button addAlarmBtn;

    TimePicker timePicker;

    AlarmSetter alarmSetter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_alarm);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        dao = AppDatabase.getDatabase(this).AlarmDAO();
        alarmSetter = AlarmSetter.getInstance(this);
        alarmTitle = (EditText) findViewById(R.id.alarmTitle);
        alarmRepeat = (CheckBox) findViewById(R.id.alarmRepeat);
        alarmInterval = (EditText) findViewById(R.id.alarmInterval);
        addAlarmBtn = (Button) findViewById(R.id.addAlarmBtn);
        timePicker = (TimePicker) findViewById(R.id.timePicker);
        timePicker.setIs24HourView(true);


        addAlarmBtn.setOnClickListener(v -> saveAlarm());

        alarmRepeat.setOnCheckedChangeListener((buttonView, isChecked) -> {
            alarmInterval.setEnabled(isChecked);
        });
    }

    void saveAlarm() {
        final String title = alarmTitle.getText().toString();
        final int hour = timePicker.getHour();
        final int minutes = timePicker.getMinute();
        final int interval = Integer.parseInt(alarmRepeat.isChecked() ? alarmInterval.getText().toString() : "0");

        final AlarmListItem alarm = new AlarmListItem(title, hour, minutes, interval, true);
        Log.d("Alarm values", alarm.getUid() + " -- " + title + " " + hour + " " + minutes + " " + interval);

        final long alarmId = dao.insert(alarm);

        alarm.setUid((int) alarmId);

        alarmSetter.setAlarm(alarm);

        Log.d("Alarm values", alarmId + " " + hour + " " + minutes + " " + interval);

        finish();
        Intent mainIntent = new Intent(this, MainActivity.class);
        startActivity(mainIntent);
    }
}