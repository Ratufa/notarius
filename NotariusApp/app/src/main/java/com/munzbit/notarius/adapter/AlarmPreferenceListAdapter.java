package com.munzbit.notarius.adapter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckedTextView;
import android.widget.TextView;

import com.munzbit.notarius.activity.AlarmPreference;
import com.munzbit.notarius.modal.Alarm;


@SuppressLint({ "ViewHolder", "InflateParams", "CutPasteId" })
public class AlarmPreferenceListAdapter extends BaseAdapter implements Serializable {

  
	private static final long serialVersionUID = 1L;
	private Context context;
    private Alarm alarm;
    private List<AlarmPreference> preferences = new ArrayList<AlarmPreference>();
    private final String[] repeatDays = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
    private final String[] alarmDifficulties = {"Easy", "Medium", "Hard"};

    private String[] alarmTones;
    private String[] alarmTonePaths;


    public AlarmPreferenceListAdapter(Context context, Alarm alarm) {
        setContext(context);
        setMathAlarm(alarm);
    }

    @Override
    public int getCount() {
        return preferences.size();
    }

    @Override
    public Object getItem(int position) {
        return preferences.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint({ "ViewHolder", "InflateParams" })
	@Override
    public View getView(int position, View convertView, ViewGroup parent) {

        AlarmPreference alarmPreference = (AlarmPreference) getItem(position);

        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        switch (alarmPreference.getType()) {
            case BOOLEAN:
              
                convertView = layoutInflater.inflate(android.R.layout.simple_list_item_checked, null);
                CheckedTextView checkedTextView = (CheckedTextView) convertView.findViewById(android.R.id.text1);
                checkedTextView.setText(alarmPreference.getTitle());
                checkedTextView.setChecked((Boolean) alarmPreference.getValue());
                break;
            case INTEGER:
            case STRING:
            case LIST:
            case MULTIPLE_LIST:
            case TIME:
            default:
              
                convertView = layoutInflater.inflate(android.R.layout.simple_list_item_2, null);

                TextView text1 = (TextView) convertView.findViewById(android.R.id.text1);
                text1.setTextSize(18);

                text1.setText(alarmPreference.getTitle());
                TextView text2 = (TextView) convertView.findViewById(android.R.id.text2);

                text1.setText(alarmPreference.getTitle());
                text2.setText(alarmPreference.getSummary());
               break;
        }

        return convertView;
    }

    public Alarm getMathAlarm() {
        for (AlarmPreference preference : preferences) {
            switch (preference.getKey()) {
              
                case ALARM_TIME:
                    alarm.setAlarmTime((String) preference.getValue());
                    break;
                case ALARM_REPEAT:
                    alarm.setDays((Alarm.Day[]) preference.getValue());
                    break;
			default:
				break;
            }
        }

        return alarm;
    }

    public void setMathAlarm(Alarm alarm) {
        this.alarm = alarm;
        preferences.clear();

        preferences.add(new AlarmPreference(AlarmPreference.Key.ALARM_REPEAT, "Repeat", alarm.getRepeatDaysString(), repeatDays, alarm.getDays(), AlarmPreference.Type.MULTIPLE_LIST));
        preferences.add(new AlarmPreference(AlarmPreference.Key.ALARM_TIME, "Time of Day", alarm.getAlarmTimeString(), null, alarm.getAlarmTime(), AlarmPreference.Type.TIME));

        Log.e("preferences size", preferences.size() + "---");
    }


    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public String[] getRepeatDays() {
        return repeatDays;
    }

    public String[] getAlarmDifficulties() {
        return alarmDifficulties;
    }

    public String[] getAlarmTones() {
        return alarmTones;
    }

    public String[] getAlarmTonePaths() {
        return alarmTonePaths;
    }

}
