package com.example.program_4_alarm_clock_21csl69c.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.widget.SwitchCompat;

import com.example.program_4_alarm_clock_21csl69c.singleton.AlarmSetter;
import com.example.program_4_alarm_clock_21csl69c.R;
import com.example.program_4_alarm_clock_21csl69c.database.AppDatabase;
import com.example.program_4_alarm_clock_21csl69c.entities.AlarmItemDao;
import com.example.program_4_alarm_clock_21csl69c.entities.AlarmListItem;

import java.util.ArrayList;

public class CustomAlarmListAdapter extends BaseAdapter {
    AlarmItemDao dao;
    final ArrayList<AlarmListItem> alarms;
    final LayoutInflater inflater;

    final Context context;

    AlarmSetter alarmSetter;

    public CustomAlarmListAdapter(Context context, ArrayList<AlarmListItem> alarms) {
        this.alarms = alarms;
        this.inflater = LayoutInflater.from(context);
        this.context = context;
        this.dao = AppDatabase.getDatabase(context).AlarmDAO();
        this.alarmSetter = AlarmSetter.getInstance(context);
    }

    @Override
    public int getCount() {
        return alarms.size();
    }

    @Override
    public Object getItem(int position) {
        return alarms.get(position);
    }

    @Override
    public long getItemId(int position) {
        return alarms.get(position).getUid();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.activity_alarm_list_view, parent, false);
            holder = new ViewHolder();
            holder.title = convertView.findViewById(R.id.title);
            holder.date = convertView.findViewById(R.id.date);
            holder.alarmToggle = convertView.findViewById(R.id.alarmToggle);
            holder.delete = convertView.findViewById(R.id.alarmDelete);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        AlarmListItem alarm = alarms.get(position);

        final String time = alarm.getHour() + ":" + alarm.getMinutes();

        holder.title.setText(alarm.getTitle());

        holder.date.setText(time);

        holder.alarmToggle.setChecked(alarm.isActive());

        holder.alarmToggle.setOnCheckedChangeListener((buttonView, isChecked) -> {
            alarm.setIsActive(isChecked);
            dao.update(alarm);
            if (isChecked) {
                this.alarmSetter.setAlarm(alarm);
            } else {
                this.alarmSetter.cancelAlarm(alarm);
            }
        });

        holder.delete.setOnClickListener(v -> {
            if (alarm.isActive()) {
                this.alarmSetter.cancelAlarm(alarm);
            }
            dao.delete(alarm);
            alarms.remove(alarm);
            notifyDataSetChanged();
        });

        return convertView;
    }

    static class ViewHolder {
        TextView title;
        TextView date;
        SwitchCompat alarmToggle;
        ImageButton delete;
    }
}
