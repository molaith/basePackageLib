package com.example.basepackagelib.tools;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import java.util.Map;
import java.util.Set;

/**
 * Created by daiji on 2016/7/14.
 *SharedPreferences工具类
 */
public class SPUtil {
    private  static  final String default_name="SharedPreferences";
    static Context mContext;

    public static void init (Context context){
        mContext = context.getApplicationContext();
    }

    /**
     * @param name  name为null时从默认的文件里取值
     */
    public static String getString(String name,String key, String defaultValue) {
        if(TextUtils.isEmpty(name))return getSharedPreferences(mContext).getString(key, defaultValue);
        else return getSharedPreferences(mContext,name).getString(key, defaultValue);
    }

    public static int getInt(String name,String key, int defaultValue) {
        if(TextUtils.isEmpty(name))return getSharedPreferences(mContext).getInt(key, defaultValue);
        else return getSharedPreferences(mContext,name).getInt(key, defaultValue);
    }

    public static boolean getBoolean(String name,String key, boolean defaultValue) {
        if(TextUtils.isEmpty(name))return getSharedPreferences(mContext).getBoolean(key, defaultValue);
        else return getSharedPreferences(mContext,name).getBoolean(key, defaultValue);
    }

    public static float getFloat(String name,String key, float defaultValue) {
        if(TextUtils.isEmpty(name))return getSharedPreferences(mContext).getFloat(key, defaultValue);
        else return getSharedPreferences(mContext,name).getFloat(key, defaultValue);
    }

    public static long getLong(String name,String key, long defaultValue) {
        if(TextUtils.isEmpty(name))return getSharedPreferences(mContext).getLong(key, defaultValue);
        else return getSharedPreferences(mContext,name).getLong(key, defaultValue);
    }

    public static Set<String> getStringSet(String name,String key) {
        if(TextUtils.isEmpty(name))return getSharedPreferences(mContext).getStringSet(key, null);
        else return getSharedPreferences(mContext,name).getStringSet(key, null);
    }


    /**
     * Preference自定义保存文件
     * @param name name为文件名，name为null时保存在默认的文件里
     * @param key
     * @param value
     */
    public static void savePreference(String name, String key, Object value) {
        SharedPreferences.Editor edit;
        if(TextUtils.isEmpty(name))edit = getSharedPreferences(mContext).edit();
        else edit =getSharedPreferences(mContext,name).edit();
        if (value == null) edit.remove(key);
        if (value instanceof Integer) edit.putInt(key, (Integer) value);
        else if (value instanceof Boolean) edit.putBoolean(key, (Boolean) value);
        else if (value instanceof Long) edit.putLong(key, (Long) value);
        else if (value instanceof Number) edit.putFloat(key, ((Number)value).floatValue());
        else if (value instanceof Set) edit.putStringSet(key,(Set<String>) value);
        else edit.putString(key, value+"");
        edit.commit();
    }

    /**
     * 移除Preference文件的值
     * @param name 文件名  为null时从默认的文件里移除值
     * @param key 要移除的key值
     */
    public static void removePreference(String name,String key){
        SharedPreferences.Editor edit;
        if(TextUtils.isEmpty(name))edit = getSharedPreferences(mContext).edit();
        else edit =getSharedPreferences(mContext,name).edit();
        edit.remove(key);
        edit.commit();
    }

    /**
     * 清除所有数据
     * @param name 文件名  为null时清空默认的文件
     */
    public static void clearPreference(String name){
        SharedPreferences.Editor edit;
        if(TextUtils.isEmpty(name))edit = getSharedPreferences(mContext).edit();
        else edit =getSharedPreferences(mContext,name).edit();
        edit.clear();
        edit.commit();
    }

	/**
     * 获取所有的key
     * @param name 文件名  可为null
     */
    public static Set<String> getAllkeys(String name){
        SharedPreferences sp;
        if(TextUtils.isEmpty(name))sp = getSharedPreferences(mContext);
        else sp =getSharedPreferences(mContext,name);
        Map<String, ?> all = sp.getAll();
        return all.keySet();
    }

    private static SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences(default_name, Application.MODE_PRIVATE);
    }

    private static SharedPreferences getSharedPreferences(Context context,String name) {
        return context.getSharedPreferences(name, Application.MODE_PRIVATE);
    }
}
