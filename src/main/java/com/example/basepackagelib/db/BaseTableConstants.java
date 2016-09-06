package com.example.basepackagelib.db;

/**
 * Created by molaith on 2016/7/21.
 */
public class BaseTableConstants {
   public static final String TABLE_NAME;

    static {
        TABLE_NAME=getInstance().getClass().getSimpleName();
    }

    public static BaseTableConstants getInstance(){
        return  new BaseTableConstants();
    }
}
