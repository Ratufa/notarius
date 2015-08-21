package com.munzbit.notarius.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.munzbit.notarius.R;
import com.munzbit.notarius.custom_views.CircularSeekBar;
import com.munzbit.notarius.utility.Methods;

/**
 * Created by Ratufa.Aditya on 8/20/2015.
 */
public class MainActivity extends Activity {


    private CircularSeekBar circularSeekBar;

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.workout_details_new);
        circularSeekBar = (CircularSeekBar)findViewById(R.id.circularSeekBar1);
        textView = (TextView)findViewById(R.id.seekValue);

        circularSeekBar.setMax(60);

        circularSeekBar.setOnSeekBarChangeListener(new CircularSeekBar.OnCircularSeekBarChangeListener() {
            @Override
            public void onProgressChanged(CircularSeekBar circularSeekBar, int progress, boolean fromUser) {

                if(progress==circularSeekBar.getMax()){
                    circularSeekBar.setProgress(60);
                    circularSeekBar.setMax(circularSeekBar.getMax()+60);
                }

                int hour = progress /4;
                int minute = (progress % 4) * 15;
                textView.setText(Methods.pad(hour)+"h "+Methods.pad(minute)+"min");
            }

            @Override
            public void onStopTrackingTouch(CircularSeekBar seekBar) {

            }

            @Override
            public void onStartTrackingTouch(CircularSeekBar seekBar) {

            }
        });
    }
}
