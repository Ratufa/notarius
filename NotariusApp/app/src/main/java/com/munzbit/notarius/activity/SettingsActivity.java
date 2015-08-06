package com.munzbit.notarius.activity;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.munzbit.notarius.R;
import com.munzbit.notarius.datamanager.SharedPrefrnceNotarius;

/**
 * Created by Ratufa.Manish on 8/6/2015.
 */
public class SettingsActivity extends Activity implements View.OnClickListener {

    private ToggleButton toggleNotification;

    private TextView frequencyTv;

    private String[] frequency = new String[]{"Everyday,After 1 day,After 2 day"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);

        toggleNotification = (ToggleButton) findViewById(R.id.toggleButton);

        frequencyTv = (TextView) findViewById(R.id.frequencySpinner);

        toggleNotification.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    SharedPrefrnceNotarius.setBoolInSharedPrefs(SettingsActivity.this, "show_notification", true);
                }

                if (!isChecked) {
                    SharedPrefrnceNotarius.setBoolInSharedPrefs(SettingsActivity.this, "show_notification", false);
                }

            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.frequencySpinner:
                showFrequencyPopup();
                break;
        }
    }

    public void showFrequencyPopup(){


        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.popup_box);
        

    }
}
