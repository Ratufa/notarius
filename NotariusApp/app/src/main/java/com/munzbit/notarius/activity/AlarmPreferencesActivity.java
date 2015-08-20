package com.munzbit.notarius.activity;

import java.util.Calendar;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.HapticFeedbackConstants;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TimePicker;

import com.munzbit.notarius.R;
import com.munzbit.notarius.adapter.AlarmPreferenceListAdapter;
import com.munzbit.notarius.adapter.PopupAdapter;
import com.munzbit.notarius.datamanager.Database;
import com.munzbit.notarius.datamanager.SharedPrefrnceNotarius;
import com.munzbit.notarius.modal.Alarm;

public class AlarmPreferencesActivity extends BaseActivity {

    //private ImageButton deleteButton;

    //private TextView okButton;

    //private TextView cancelButton;

    private Alarm alarm;

    private MediaPlayer mediaPlayer;

    //private ToggleButton toggleButton;

    private ListAdapter listAdapter;

    private ListView listView;

    private Bundle bundle;

    private AlarmPreferenceListAdapter alarmPreferenceListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alarm_preferences);
        Database.init(AlarmPreferencesActivity.this);
        alarm = new Alarm();
        alarm.setAlarmActive(true);

        Calendar newAlarmTime = Calendar.getInstance();
        newAlarmTime.set(Calendar.HOUR_OF_DAY, 20);
        newAlarmTime.set(Calendar.MINUTE, 0);
        newAlarmTime.set(Calendar.SECOND, 0);

        bundle = getIntent().getExtras();
        if (bundle != null && bundle.containsKey("alarm")) {
            alarm = (Alarm) bundle.getSerializable("alarm");
            setMathAlarm((Alarm) bundle.getSerializable("alarm"));
        } else {
            alarm.setAlarmTime(newAlarmTime);
            setMathAlarm(alarm);
        }

        if (bundle != null && bundle.containsKey("adapter")) {
            setListAdapter((AlarmPreferenceListAdapter) bundle.getSerializable("adapter"));
        } else {
            setListAdapter(new AlarmPreferenceListAdapter(this, getMathAlarm()));

        }

        getListView().setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> l, View v, int position, long id) {

                alarmPreferenceListAdapter = (AlarmPreferenceListAdapter) getListAdapter();

                final AlarmPreference alarmPreference = (AlarmPreference) alarmPreferenceListAdapter.getItem(position);
                //AlertDialog.Builder alert;
                v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                switch (alarmPreference.getType()) {
                    case MULTIPLE_LIST:
                        //alert = new AlertDialog.Builder(AlarmPreferencesActivity.this);
                        //alert.setTitle(alarmPreference.getTitle());
                        CharSequence[] multiListItems = null;
                        boolean[] checkedItems = null;

                        multiListItems = new CharSequence[alarmPreference.getOptions().length];
                        checkedItems = new boolean[multiListItems.length];

                        for (int i = 0; i < multiListItems.length; i++) {
                            multiListItems[i] = alarmPreference.getOptions()[i];
                            checkedItems[i] = false;
                        }

                        if (!SharedPrefrnceNotarius.getBoolSharedPrefData(AlarmPreferencesActivity.this, "never")) {
                            for (Alarm.Day day : getMathAlarm().getDays()) {
                                checkedItems[day.ordinal()] = true;
                            }
                        }

                        showFrequencyPopup(alarmPreference, multiListItems, checkedItems, alarmPreferenceListAdapter);

//                        alert.setMultiChoiceItems(multiListItems, checkedItems, new OnMultiChoiceClickListener() {
//
//                            @Override
//                            public void onClick(final DialogInterface dialog, int which, boolean isChecked) {
//
//                                Alarm.Day thisDay = Alarm.Day.values()[which];
//
//                                if (isChecked) {
//                                    alarm.addDay(thisDay);
//                                }
//
//                                if (!isChecked) {
//                                    if (alarm.getDays().length > 1) {
//                                        alarm.removeDay(thisDay);
//                                    }
//                                }
//
//                            }
//                        });

//                        alert.setOnCancelListener(new OnCancelListener() {
//                            @Override
//                            public void onCancel(DialogInterface dialog) {
//                                Log.e("GET DAYS", alarm.getDays().length + ">>");
//                                Database.init(AlarmPreferencesActivity.this);
//
//                                if (alarm.getDays().length == 0) {
//                                    SharedPrefrnceNotarius.setBoolInSharedPrefs(AlarmPreferencesActivity.this, "never", true);
//                                    Database.deleteAll();
//                                    alarmPreferenceListAdapter.setMathAlarm(getMathAlarm());
//                                } else {
//                                    SharedPrefrnceNotarius.setBoolInSharedPrefs(AlarmPreferencesActivity.this, "never", false);
//                                    alarmPreferenceListAdapter.setMathAlarm(getMathAlarm());
//                                }
//
//                                alarmPreferenceListAdapter.notifyDataSetChanged();
//
//                            }
//                        });
//                        alert.show();
                        break;
                    case TIME:
                        TimePickerDialog timePickerDialog = new TimePickerDialog(AlarmPreferencesActivity.this, new OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker timePicker, int hours, int minutes) {
                                Calendar newAlarmTime = Calendar.getInstance();
                                newAlarmTime.set(Calendar.HOUR_OF_DAY, hours);
                                newAlarmTime.set(Calendar.MINUTE, minutes);
                                newAlarmTime.set(Calendar.SECOND, 0);
                                alarm.setAlarmTime(newAlarmTime);
                                alarmPreferenceListAdapter.setMathAlarm(getMathAlarm());
                                alarmPreferenceListAdapter.notifyDataSetChanged();
                            }
                        }, alarm.getAlarmTime().get(Calendar.HOUR_OF_DAY), alarm.getAlarmTime().get(Calendar.MINUTE), true);

                        timePickerDialog.setTitle(alarmPreference.getTitle());

                        timePickerDialog.show();

                    default:
                        break;
                }
            }
        });
    }


    public void updateDays(int which, boolean check) {

        Alarm.Day thisDay = Alarm.Day.values()[which];
        if (check) {
            alarm.addDay(thisDay);
        }

        if (!check) {
            if (alarm.getDays().length > 1) {
                alarm.removeDay(thisDay);
            }
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        boolean result = super.onCreateOptionsMenu(menu);
        return result;
    }

    public void showFrequencyPopup(final AlarmPreference alarmPreference, CharSequence[] multiListItems, boolean[] checkedItems, final AlarmPreferenceListAdapter alarmPreferenceListAdapter) {

        final Dialog dialog = new Dialog(this);
        dialog.setTitle(alarmPreference.getTitle());
        dialog.setContentView(R.layout.popup_box);

        listView = (ListView) dialog.findViewById(R.id.listView);

        listView.setAdapter(new PopupAdapter(this, multiListItems, checkedItems));

        Button okBtn = (Button) dialog.findViewById(R.id.dismissBtn);

        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                try {
                    if (alarm.getDays().length == 0) {
                        SharedPrefrnceNotarius.setBoolInSharedPrefs(AlarmPreferencesActivity.this, "never", true);
                        Database.deleteAll();
                        alarmPreferenceListAdapter.setMathAlarm(getMathAlarm());
                        stopMathAlarmScheduleService();

                    } else {
                        alarmPreferenceListAdapter.setMathAlarm(getMathAlarm());
                    }
                }catch (Exception e){
                    SharedPrefrnceNotarius.setBoolInSharedPrefs(AlarmPreferencesActivity.this, "never", true);
                    Database.deleteAll();
                    alarmPreferenceListAdapter.setMathAlarm(getMathAlarm());
                    stopMathAlarmScheduleService();
                }

                alarmPreferenceListAdapter.notifyDataSetChanged();
            }
        });

        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {

                try {
                    if (alarm.getDays().length == 0) {
                        SharedPrefrnceNotarius.setBoolInSharedPrefs(AlarmPreferencesActivity.this, "never", true);
                        Database.deleteAll();
                        alarmPreferenceListAdapter.setMathAlarm(getMathAlarm());
                        stopMathAlarmScheduleService();

                    }else{

                        SharedPrefrnceNotarius.setBoolInSharedPrefs(AlarmPreferencesActivity.this, "never", false);
                        alarmPreferenceListAdapter.setMathAlarm(getMathAlarm());

                    }

                }catch (Exception e){
                    SharedPrefrnceNotarius.setBoolInSharedPrefs(AlarmPreferencesActivity.this, "never", true);
                    Database.deleteAll();
                    alarm = null;
                    alarmPreferenceListAdapter.setMathAlarm(getMathAlarm());

                    stopMathAlarmScheduleService();
                }

               alarmPreferenceListAdapter.notifyDataSetChanged();

            }
        });

        dialog.show();

    }


    @Override
    protected void onPause() {
        super.onPause();
        Log.e("onPause>>", "true");
        if (alarm != null) {
            if (getMathAlarm().getId() < 1) {
                Database.create(getMathAlarm());
            } else {
                Database.update(getMathAlarm());
            }
        } else {

            Database.deleteAll();
        }

        callMathAlarmScheduleService();

        try {
            if (mediaPlayer != null)
                mediaPlayer.release();
        } catch (Exception e) {
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    public Alarm getMathAlarm() {
        return alarm;
    }

    public void setMathAlarm(Alarm alarm) {
        this.alarm = alarm;
    }

    public ListAdapter getListAdapter() {
        return listAdapter;
    }

    public void setListAdapter(ListAdapter listAdapter) {
        this.listAdapter = listAdapter;
        getListView().setAdapter(listAdapter);
    }

    public ListView getListView() {
        if (listView == null)
            listView = (ListView) findViewById(android.R.id.list);
        return listView;
    }

    public void setListView(ListView listView) {
        this.listView = listView;
    }

    @Override
    public void onClick(View v) {

    }
}