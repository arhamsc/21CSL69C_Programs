package com.example.program_4_alarm_clock_21csl69c;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.room.Room;

import com.example.program_4_alarm_clock_21csl69c.adapters.CustomAlarmListAdapter;
import com.example.program_4_alarm_clock_21csl69c.database.AppDatabase;
import com.example.program_4_alarm_clock_21csl69c.entities.AlarmItemDao;
import com.example.program_4_alarm_clock_21csl69c.entities.AlarmListItem;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    ListView alarmListView;
    ImageButton addAlarmButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        addAlarmButton = (ImageButton) findViewById(R.id.addAlarmButton);

        addAlarmButton.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, AddAlarmActivity.class))
        );

        alarmListView = (ListView) findViewById(R.id.alarmList);
        CustomAlarmListAdapter customAlarmListAdapter = new CustomAlarmListAdapter(this, (ArrayList<AlarmListItem>) getAlarms());
        alarmListView.setAdapter(customAlarmListAdapter);
    }

    private List<AlarmListItem> getAlarms() {
        AlarmItemDao dao = AppDatabase.getDatabase(this).AlarmDAO();
        return dao.getAll();
    }
}