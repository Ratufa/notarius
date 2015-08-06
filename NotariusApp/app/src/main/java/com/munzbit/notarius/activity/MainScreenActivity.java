package com.munzbit.notarius.activity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;

import com.munzbit.notarius.R;
import com.munzbit.notarius.datamanager.SharedPrefrnceNotarius;

import java.util.Calendar;

/**
 * Created by Ratufa.Manish on 8/4/2015.
 */
public class MainScreenActivity extends FragmentActivity implements View.OnClickListener {


    private Button todayBtn;

    private Button olderbtn;

    private Button historyBtn;

    public static Activity mainScreen;

    private ImageView settingsImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_screen);
        mainScreen = this;

        olderbtn = (Button) findViewById(R.id.olderBtn);

        todayBtn = (Button) findViewById(R.id.todayBtn);

        historyBtn = (Button) findViewById(R.id.workOutHistoryBtn);

        settingsImage = (ImageView) findViewById(R.id.settingImage);

        olderbtn.setOnClickListener(this);
        todayBtn.setOnClickListener(this);
        historyBtn.setOnClickListener(this);
        settingsImage.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.olderBtn:
                DialogFragment newFragment = new DatePickerFragment();
                newFragment.show(getSupportFragmentManager(), "datePicker");
                break;

            case R.id.todayBtn:
                Calendar calendar = Calendar.getInstance();
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                SharedPrefrnceNotarius.setDataInSharedPrefrence(this,"work_date",day+"/"+(month+1)+"/"+year);
                Intent intent1 = new Intent(this, WorkOutActivity.class);
                startActivity(intent1);
                break;

            case R.id.workOutHistoryBtn:
                Intent intent2 = new Intent(this, WorkOutHistory.class);
                intent2.putExtra("history",true);
                startActivity(intent2);
                finish();
                break;

            case R.id.settingImage:
                Intent intent3 = new Intent(this, SettingsActivity.class);
                startActivity(intent3);

                break;

        }

    }


    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {

            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog picker = new DatePickerDialog(getActivity(), this,
                    year, month, day);

            if (Build.VERSION.SDK_INT >= 11) {
                picker.getDatePicker().setMaxDate(c.getTime().getTime());
            }

            return picker;
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {

            SharedPrefrnceNotarius.setDataInSharedPrefrence(getActivity(),"work_date",day+"/"+(month+1)+"/"+year);
            Intent intent = new Intent(getActivity(), WorkOutActivity.class);
            startActivity(intent);
        }
    }
}
