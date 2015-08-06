package com.munzbit.notarius.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.munzbit.notarius.R;
import com.munzbit.notarius.datamanager.DataManager;


/**
 * Created by Ratufa.Manish on 8/4/2015.
 */
public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, MainScreenActivity.class);
                startActivity(intent);
                finish();
            }
        }, 3000);
    }
}
