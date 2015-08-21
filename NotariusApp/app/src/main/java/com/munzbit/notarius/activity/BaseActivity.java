package com.munzbit.notarius.activity;

import java.lang.reflect.Field;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewConfiguration;

import com.munzbit.notarius.alarm_manager_updated.AlarmService;


public abstract class BaseActivity  extends Activity implements android.view.View.OnClickListener{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		try {
	        ViewConfiguration config = ViewConfiguration.get(this);	        
	        Field menuKeyField = ViewConfiguration.class.getDeclaredField("sHasPermanentMenuKey");
	        if(menuKeyField != null) {
	            menuKeyField.setAccessible(true);
	            menuKeyField.setBoolean(config, false);
	        }
	    } catch (Exception ex) {
	        // Ignore
	    }
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {		
		// Inflate the menu items for use in the action bar
	    //MenuInflater inflater = getMenuInflater();
	    //inflater.inflate(R.menu.menu, menu);
	    return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		return true;
	}

	protected void callMathAlarmScheduleService() {
		Intent serviceIntent = new Intent(this, AlarmService.class);
		startService(serviceIntent);
		Log.e("service started","ok>>>");

	}
	protected void stopMathAlarmScheduleService() {
		Intent serviceIntent = new Intent(this, AlarmService.class);
		stopService(serviceIntent);
		Log.e("service stopped","ok>>>");

	}
}
