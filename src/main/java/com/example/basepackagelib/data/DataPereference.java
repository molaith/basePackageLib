package com.example.basepackagelib.data;

import java.util.Set;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class DataPereference {
	private Context context;
	private Editor editor;
	private SharedPreferences preferences;
	
	public DataPereference(Context context) {
		preferences = context.getSharedPreferences("baselib.SharedPreferences",
				Context.MODE_APPEND);
		editor = preferences.edit();
		this.context=context;
	}

	public void putStringData(String key,String value){
		editor.putString(key, value);
		editor.commit();
	}
	
	public String getStringData(String key){
		return preferences.getString(key, "");
	}
	
	public void putBooleanData(String key,boolean value){
		editor.putBoolean(key, value);
		editor.commit();
	}
	
	public boolean getBooleanData(String key){
		return preferences.getBoolean(key, false);
	}
	
	public void putIntData(String key,int value){
		editor.putInt(key, value);
		editor.commit();
	}
	
	public int getIntData(String key){
		return preferences.getInt(key, 0);
	}
	
	public void putFloatData(String key,float value){
		editor.putFloat(key, value);
		editor.commit();
	}
	
	public float getFloatData(String key){
		return preferences.getFloat(key, 0);
	}
	
	public void putStringSetData(String key,Set<String> value){
		editor.putStringSet(key, value);
		editor.commit();
	}
	
	public Set<String> getStringSetData(String key){
		return preferences.getStringSet(key, null);
	}
	
}
