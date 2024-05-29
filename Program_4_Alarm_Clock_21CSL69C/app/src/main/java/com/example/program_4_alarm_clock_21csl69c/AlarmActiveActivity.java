package com.example.program_4_alarm_clock_21csl69c;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.program_4_alarm_clock_21csl69c.database.AppDatabase;
import com.example.program_4_alarm_clock_21csl69c.entities.AlarmItemDao;
import com.example.program_4_alarm_clock_21csl69c.entities.AlarmListItem;
import com.example.program_4_alarm_clock_21csl69c.singleton.AlarmSetter;

public class AlarmActiveActivity extends AppCompatActivity {

    int intentDataId;
    AlarmItemDao dao;

    AlarmListItem item;

    TextView alarmTitle;

    TextView alarmHour, alarmMinute;

    Button snoozeBtn, dismissBtn;

    AlarmSetter alarmSetter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_alarm_active);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        dao = AppDatabase.getDatabase(this).AlarmDAO();

        alarmSetter = AlarmSetter.getInstance(this);

        alarmTitle = (TextView) findViewById(R.id.alarm_title);
        alarmHour = (TextView) findViewById(R.id.hour);
        alarmMinute = (TextView) findViewById(R.id.minute);
        snoozeBtn = (Button) findViewById(R.id.snooze_btn);
        dismissBtn = (Button) findViewById(R.id.dismiss_btn);

        intentDataId = getIntent().getIntExtra("id", 0);

        item = dao.getAlarm(intentDataId);

        alarmTitle.setText(item.getTitle());
        alarmHour.setText(String.valueOf(item.getHour()));
        alarmMinute.setText(String.valueOf(item.getMinutes()));

        if (item.getInterval() > 0) {
            // Repeating
            snoozeBtn.setVisibility(View.INVISIBLE);
            dismissBtn.setOnClickListener(v -> {
                closeIntent();
            });
        } else {
            // Not Repeating
            snoozeBtn.setVisibility(View.VISIBLE);
            snoozeBtn.setOnClickListener(v -> snoozeAlarm());
            dismissBtn.setOnClickListener(v -> cancelAlarm());
        }
    }

    void cancelAlarm() {
        this.alarmSetter.cancelAlarm(item);
        item.setIsActive(false);
        dao.update(item);
        closeIntent();
    }

    void closeIntent() {
        final Intent intent1 = new Intent(this, MainActivity.class);
        this.alarmSetter.stopAlarmSound();
        finish();
        startActivity(intent1);
    }

    void snoozeAlarm() {
        this.alarmSetter.snoozeAlarm(item);

        closeIntent();
    }
}