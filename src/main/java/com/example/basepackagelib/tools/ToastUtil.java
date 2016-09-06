package com.example.basepackagelib.tools;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.Toast;

/**
 * Created by daiji on 2016/7/18.
 */
public class ToastUtil {

    private static Context mContext = null;

    /**
     * init in Application
     * @param context
     */
    public static void init(@NonNull Context context) {
        mContext = context.getApplicationContext();
    }

    /**
     * 短时间显示Toast
     * @param message
     */
    public static void showShort(CharSequence message) {
        Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
    }

    /**
     * 短时间显示Toast,位置默认
     * @param message
     */
    public static void showShort( int message) {
            Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
    }
    /**
     * 短时间显示Toast,位置自定
     * @param message
     */
    public static void showShort(CharSequence message, int gravity){
        Toast toast = Toast.makeText(mContext, message, Toast.LENGTH_SHORT);
        toast.setGravity(gravity, 0, 0);
        toast.show();
    }

    /**
     * 长时间显示Toast,位置默认
     *
     * @param message
     */
    public static void showLong(CharSequence message) {
        Toast.makeText(mContext, message, Toast.LENGTH_LONG).show();
    }

    /**
     * 长时间显示Toast,位置自定
     *
     * @param message
     */
    public static void showLong(CharSequence message, int gravity){
        Toast toast = Toast.makeText(mContext, message, Toast.LENGTH_LONG);
        toast.setGravity(gravity, 0, 0);
        toast.show();
    }

    /**
     * 长时间显示Toast
     *
     * @param message
     */
    public static void showLong(int message) {
        Toast.makeText(mContext, message, Toast.LENGTH_LONG).show();
    }

}
