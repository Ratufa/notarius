package com.munzbit.notarius.activity;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.munzbit.notarius.Notification.ScheduleClient;
import com.munzbit.notarius.R;
import com.munzbit.notarius.adapter.PopupAdapter;
import com.munzbit.notarius.datamanager.SharedPrefrnceNotarius;
import com.munzbit.notarius.modal.TimerModal;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Ratufa.Manish on 8/6/2015.
 */
public class SettingsActivity extends Activity implements View.OnClickListener {

    private ToggleButton toggleNotification;

    private TextView frequencyTv;

    private ArrayList<TimerModal> frequencyTimes;

    private String repeatTime = "Everyday";

    private ScheduleClient scheduleClient;

    private Calendar c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);
        frequencyTimes = new ArrayList<TimerModal>();
        scheduleClient = new ScheduleClient(this);
        scheduleClient.doBindService();
        c = Calendar.getInstance();

        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        c.set(year, month + 1, day);
        c.set(Calendar.HOUR_OF_DAY, 20);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);

        TimerModal timerModal1 = new TimerModal();
        timerModal1.setIsSelected(false);
        timerModal1.setTimerType("Repeat");

        TimerModal timerModal2 = new TimerModal();
        timerModal2.setIsSelected(false);
        timerModal2.setTimerType("Every Monday");

        TimerModal timerModal3 = new TimerModal();
        timerModal3.setIsSelected(false);
        timerModal3.setTimerType("Every Tuesday");

        TimerModal timerModal4 = new TimerModal();
        timerModal4.setIsSelected(false);
        timerModal4.setTimerType("Every Wednesday");

        TimerModal timerModal5 = new TimerModal();
        timerModal5.setIsSelected(false);
        timerModal5.setTimerType("Every Thursday");

        TimerModal timerModal6 = new TimerModal();
        timerModal6.setIsSelected(false);
        timerModal6.setTimerType("Every Friday");

        TimerModal timerModal7 = new TimerModal();
        timerModal7.setIsSelected(false);
        timerModal7.setTimerType("Every Saturday");

        frequencyTimes.add(timerModal1);
        frequencyTimes.add(timerModal2);
        frequencyTimes.add(timerModal3);
        frequencyTimes.add(timerModal4);
        frequencyTimes.add(timerModal5);
        frequencyTimes.add(timerModal6);
        frequencyTimes.add(timerModal7);

        toggleNotification = (ToggleButton) findViewById(R.id.toggleButton);

        frequencyTv = (TextView) findViewById(R.id.frequencySpinner);

        frequencyTv.setOnClickListener(this);

        toggleNotification.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    //SharedPrefrnceNotarius.setBoolInSharedPrefs(SettingsActivity.this, "show_notification", true);

                    scheduleClient.setAlarmForNotification(c);
                }

                if (!isChecked) {
                    //SharedPrefrnceNotarius.setBoolInSharedPrefs(SettingsActivity.this, "show_notification", false);
                    scheduleClient.doUnbindService();
                }

            }
        });

    }

    public void getSelectedItems(ArrayList<TimerModal> data) {

        repeatTime="";

        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).getIsSelected())
                repeatTime = repeatTime.concat(data.get(i).getTimerType());
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.frequencySpinner:
                showFrequencyPopup();
                break;
        }
    }

    public void showFrequencyPopup() {

        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.popup_box);
        dialog.setCancelable(false);
        ListView listView = (ListView) dialog.findViewById(R.id.listView);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                dialog.dismiss();
                if (position == 0) {
                    repeatTime = "Everyday";
                }
            }
        });

        listView.setAdapter(new PopupAdapter(this, frequencyTimes));

        Button okBtn = (Button) dialog.findViewById(R.id.dismissBtn);
        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                frequencyTv.setText(repeatTime);

            }
        });

        dialog.show();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
