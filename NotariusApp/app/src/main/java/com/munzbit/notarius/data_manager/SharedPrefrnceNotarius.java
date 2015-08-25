package com.munzbit.notarius.data_manager;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SharedPrefrnceNotarius {

	public static final String PREFRENCE_NAME = "NotariusPreferences";

	public static String getSharedPrefData(Context activity, String key) {
		SharedPreferences prefs = activity.getSharedPreferences(PREFRENCE_NAME,
				Context.MODE_PRIVATE);
		String value = null;

		if (prefs != null && prefs.contains(key)) {
			value = prefs.getString(key, "null");
		}

		return value;
	}

	public static boolean getBoolSharedPrefData(Activity activity, String key) {
		SharedPreferences prefs = activity.getSharedPreferences(PREFRENCE_NAME,
				Context.MODE_PRIVATE);
		boolean value = false;

		if (prefs != null && prefs.contains(key)) {
			value = prefs.getBoolean(key, false);
		}

		return value;
	}

	public static int getIntSharedPrefData(Context activity, String key) {
		SharedPreferences prefs = activity.getSharedPreferences(PREFRENCE_NAME,
				Context.MODE_PRIVATE);
		int value = 0;

		if (prefs != null && prefs.contains(key)) {
			value = prefs.getInt(key, 0);
		}

		return value;
	}

	public static void writeArraylist(Context activity, String key,
									  List<String> arryid) {

		SharedPreferences prefs = activity.getSharedPreferences(PREFRENCE_NAME,
				Context.MODE_PRIVATE);
		Set<String> set = new HashSet<String>(arryid);
		SharedPreferences.Editor editor = prefs.edit();
		editor.putStringSet(key, set).commit();

	}

	public static List<String> ReadArraylist(Context activity, String key) {
		SharedPreferences prefs = activity.getSharedPreferences(PREFRENCE_NAME,
				Context.MODE_PRIVATE);
		Set<String> stock_Set = prefs.getStringSet(key,
				new HashSet<String>());
		List<String> demo = new ArrayList<String>(stock_Set);
		return demo;
	}

	public static void setDataInSharedPrefrence(Context activity, String key,
			String value) {
		SharedPreferences prefs = activity.getSharedPreferences(PREFRENCE_NAME,
				Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = prefs.edit();
		editor.putString(key, value);
		editor.commit();
	}

	public static void setBoolInSharedPrefs(Activity activity, String key,
												boolean value) {
		SharedPreferences prefs = activity.getSharedPreferences(PREFRENCE_NAME,
				Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = prefs.edit();
		editor.putBoolean(key, value);
		editor.commit();
	}

	public static void setIntInSharedPrefs(Context activity, String key,
											int value) {
		SharedPreferences prefs = activity.getSharedPreferences(PREFRENCE_NAME,
				Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = prefs.edit();
		editor.putInt(key, value);
		editor.commit();
	}

	public static void deletePrefrenceData(Activity activity) {
		SharedPreferences prefs = activity.getSharedPreferences(PREFRENCE_NAME,
				Context.MODE_PRIVATE);
		prefs.edit().clear().commit();
	}



	public static void deleteKey(Activity activity,String key){
		SharedPreferences prefs = activity.getSharedPreferences(PREFRENCE_NAME,
				Context.MODE_PRIVATE);
		prefs.edit().remove(key).commit();
	}
}
